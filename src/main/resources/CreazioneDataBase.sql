-- Enum
CREATE TYPE StatoOrdine AS ENUM('BOZZA','PREPARAZIONE','PRONTO_RITIRO_RIDER','IN_CONSEGNA','CONFERMA_CONSEGNA_RIDER','CONFERMA_CONSEGNA_CLIENTE','CONSEGNATO','ANNULATO');
CREATE TYPE Ruolo AS ENUM('MANAGER','GESTIONALE','BASE');

------------------------------------------------------------------------------------------------------------------------------------------
-- Utente
CREATE TABLE Utente(
    nickname varchar(32) PRIMARY KEY,
    email varchar(32) NOT NULL,
    password varchar(32) NOT NULL,
    nome varchar(32) NOT NULL,
    cognome varchar(32) NOT NULL,
	
	CONSTRAINT unique_nickname UNIQUE(nickname),
	CONSTRAINT unique_email UNIQUE(email)
);

CREATE TABLE Cliente(
    nickname varchar(32) PRIMARY KEY REFERENCES Utente(nickname) ON DELETE CASCADE,
    punti_fedelta int NOT NULL,

	CONSTRAINT punti_fedelta_positivi CHECK(punti_fedelta >= 0)
);

CREATE TABLE Rider(
    nickname varchar(32) PRIMARY KEY REFERENCES Utente(nickname) ON DELETE CASCADE,
    mezzo_trasporto varchar(64) NOT NULL
);

------------------------------------------------------------------------------------------------------------------------------------------
-- Dipendente 
CREATE TABLE Dipendente(
    nickname varchar(32) PRIMARY KEY REFERENCES Utente(nickname) ON DELETE CASCADE,
    ruolo Ruolo NOT NULL
);

CREATE TABLE GestioneDipendente(
    superiore varchar(32) NOT NULL REFERENCES Dipendente(nickname) ON DELETE CASCADE,
    subordinato varchar(32) NOT NULL REFERENCES Dipendente(nickname) ON DELETE CASCADE,
    PRIMARY KEY(superiore, subordinato)
);

------------------------------------------------------------------------------------------------------------------------------------------
-- Ristorante
CREATE TABLE Ristorante(
    codice_ristorante varchar(12) PRIMARY KEY,
    nome varchar(64) NOT NULL,
    indirizzo varchar(128) NOT NULL
);

CREATE TABLE Menu(
    id_menu serial PRIMARY KEY,
    nome varchar(64) NOT NULL,
    codice_ristorante varchar(12) NOT NULL REFERENCES Ristorante(codice_ristorante) ON DELETE CASCADE
);

CREATE TABLE Prodotto(
    id_prodotto serial PRIMARY KEY,
    nome varchar(64) NOT NULL,
    prezzo_unitario DECIMAL(10,2) NOT NULL,
    id_menu int NOT NULL REFERENCES Menu(id_menu) ON DELETE CASCADE,

	CONSTRAINT prezzo_maggiore_di_zero CHECK(prezzo_unitario > 0)
);

------------------------------------------------------------------------------------------------------------------------------------------
-- Ordine
CREATE TABLE Ordine(
    codice_ordine varchar(12) PRIMARY KEY,
    costo DECIMAL(10,2) NOT NULL,
    stato StatoOrdine NOT NULL,
    indirizzo varchar(128) NOT NULL,
    data_ordine DATE NOT NULL, 
    nickname_cliente varchar(32) NOT NULL REFERENCES Cliente(nickname) ON DELETE CASCADE,
    codice_ristorante varchar(12) NOT NULL REFERENCES Ristorante(codice_ristorante) ON DELETE CASCADE
);

CREATE TABLE RigaOrdine(
    id_prodotto int NOT NULL REFERENCES Prodotto(id_prodotto) ON DELETE CASCADE,
    codice_ordine varchar(12) NOT NULL REFERENCES Ordine(codice_ordine) ON DELETE CASCADE,
    quantita int NOT NULL,
	
    PRIMARY KEY(id_prodotto, codice_ordine),
	CONSTRAINT quantita_prodotto_maggiore_di_zero CHECK(quantita > 0)
);

CREATE TABLE RiderPropostiConsegna(
    rider_nickname varchar(32) NOT NULL REFERENCES Rider(nickname) ON DELETE CASCADE,
    codice_ordine varchar(12) NOT NULL REFERENCES Ordine(codice_ordine) ON DELETE CASCADE,
    PRIMARY KEY(rider_nickname, codice_ordine)
);

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--- Trigger gestione della transazione dello statoOrdine Bozza -> Preparazione , che non permette la transizione allo stato Preparazione se l'ordine non contiene RigheOrdine

CREATE OR REPLACE FUNCTION controlla_righeOrdine_stato_preprazione()
RETURNS TRIGGER LANGUAGE PLPGSQL
AS $$

DECLARE NumeroRigheOrdine int;
BEGIN
	IF OLD.stato = 'BOZZA'::StatoOrdine AND NEW.stato = 'PREPARAZIONE'::StatoOrdine THEN
		SELECT count(*) INTO NumeroRigheOrdine FROM RigaOrdine WHERE codice_ordine = OLD.codice_ordine;
	
		IF NumeroRigheOrdine < 1 THEN
			RAISE EXCEPTION 'l''ordine deve contente almento un prodotto';
		END IF;
	END IF;
	
	RETURN NEW;
END
$$;

CREATE TRIGGER controlla_righeOrdine_stato_preprazione_trigger
BEFORE UPDATE ON Ordine
FOR EACH ROW 
EXECUTE FUNCTION controlla_righeOrdine_stato_preprazione();

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---Trigger che non permette a un rider di prendere in carico più di 3 ordini contemporanamente

CREATE OR REPLACE FUNCTION controlla_numero_ordini_trasportati_rider()
RETURNS TRIGGER LANGUAGE PLPGSQL
AS $$
DECLARE NumeroOrdiniRider INT;
BEGIN
	SELECT count(*) INTO NumeroOrdiniRider FROM RiderPropostiConsegna rpc 
	JOIN Ordine o ON rpc.codice_ordine = o.codice_ordine
	WHERE rpc.rider_nickname = NEW.rider_nickname 
  	AND o.stato_ordine IN ('PREPARAZIONE'::StatoOrdine, 'PRONTO RITIRO'::StatoOrdine, 'IN CONSEGNA'::StatoOrdine);

	IF NumeroOrdiniRider > 3 THEN
		RAISE EXCEPTION 'un rider non può trasportare più di 3 ordini alla volta';
	END IF;

	RETURN NEW;
END
$$;


CREATE TRIGGER controlla_numero_ordini_trasportati_rider_trigger
BEFORE INSERT ON RiderPropostiConsegna
FOR EACH ROW 
EXECUTE FUNCTION controlla_numero_ordini_trasportati_rider();


CREATE TRIGGER controlla_numero_ordini_trasportati_rider_trigger
BEFORE INSERT ON RiderPropostiConsegna
FOR EACH ROW 
EXECUTE FUNCTION controlla_numero_ordini_trasportati_rider();

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---Trigger che non permette a Rider di cancellare l'account finche non consegna tutti gli ordini

CREATE OR REPLACE FUNCTION controlla_cancellazione_account()
RETURNS TRIGGER LANGUAGE PLPGSQL
AS $$
DECLARE
    NOrdiniRiderSospesi INT;
	NOrdiniClienteSospesi INT;
	NOrdiniSospesi INT;
BEGIN

    SELECT count(*) INTO NOrdiniRiderSospesi
    FROM Ordine o
    JOIN RiderPropostiConsegna rpc ON o.codice_ordine = rpc.codice_ordine
    WHERE rpc.rider_nickname = OLD.nickname
    AND o.stato_ordine IN ('PRONTO RITIRO'::StatoOrdine, 'IN CONSEGNA'::StatoOrdine);

	SELECT count(*) INTO NOrdiniClienteSospesi
	FROM Ordine o
	WHERE o.nickname_cliente = OLD.nickname
	AND o.stato_ordine IN ('PRONTO RITIRO'::StatoOrdine, 'IN CONSEGNA'::StatoOrdine);

	NOrdiniSospesi := NOrdiniRiderSospesi + NOrdiniClienteSospesi; 

    IF NOrdiniSospesi > 0 THEN
        RAISE EXCEPTION 'non puoi cancellare l''account mentre hai % ordine/i attivo/i in consegna.', NOrdiniSospesi;
    END IF;

    RETURN OLD; 
END;
$$;

CREATE TRIGGER impedisci_cancellazione_utente_trigger
BEFORE DELETE ON Rider
FOR EACH ROW
EXECUTE FUNCTION controlla_cancellazione_account();

--SELECT * FROM Utente;
--DELETE FROM Utente WHERE nickname = 'f';