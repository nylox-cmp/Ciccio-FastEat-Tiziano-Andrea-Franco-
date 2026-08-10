------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Appunti 

--Corrisponedenti numerici Enum Java StatOrdine e Ruolo:
--BOZZA 0,PREPARAZIONE 1,PRONTO_RITIRO_RIDER 2,IN_CONSEGNA 3,CONFERMA_CONSEGNA_RIDER 4,CONFERMA_CONSEGNA_CLIENTE 5,CONSEGNATO 6,ANNULLATO 7
--BASE 0,GESTIONALE 2,MANAGER 3

--BEC (Buisness Error Code) errori generati da un trigger,da intercettare in java tramite i seguenti codici univoci:
--BEC0 controlla_ordine_vuoto_stato_preparazione_trigger
--BEC1 controlla_numero_ordini_trasportati_rider_trigger
--BEC2 impedisci_cancellazione_utente_trigger
--BEC3 impedisci_cancellazione_ristorante_trigger

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---Trigger gestione della transazione dello statoOrdine Bozza -> Preparazione, che non permette la transizione allo stato Preparazione se l'ordine non contiene RigheOrdine

CREATE OR REPLACE FUNCTION controlla_ordine_vuoto_stato_preparazione()
RETURNS TRIGGER LANGUAGE PLPGSQL
AS $$

DECLARE NumeroRigheOrdine INT;
BEGIN
	IF OLD.stato = 0 AND NEW.stato = 1 THEN
		SELECT count(*) INTO NumeroRigheOrdine FROM RigaOrdine WHERE codice_ordine = OLD.codice_ordine;

		IF NumeroRigheOrdine < 1 THEN
			RAISE EXCEPTION 'BEC0: l''ordine deve contente almento un prodotto';
		END IF;
	END IF;

	RETURN NEW;
END
$$;

CREATE TRIGGER controlla_ordine_vuoto_stato_preparazione_trigger
BEFORE UPDATE ON Ordine
FOR EACH ROW
EXECUTE FUNCTION controlla_ordine_vuoto_stato_preparazione();

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---Trigger che non permette a un rider di prendere in carico più di 3 ordini contemporanamente

CREATE OR REPLACE FUNCTION controlla_numero_ordini_trasportati_rider()
RETURNS TRIGGER LANGUAGE PLPGSQL
AS $$
DECLARE NumeroOrdiniRider INT;
BEGIN
	SELECT count(*) INTO NumeroOrdiniRider FROM RiderPropostiConsegna rpc
	JOIN Ordine o ON rpc.codice_ordine = o.codice_ordine
	WHERE rpc.nickname_rider = NEW.nickname_rider
  	AND o.stato IN (1,2,3); --PREPARAZIONE,PRONTO_RITIRO_RIDER,IN_CONSEGNA

	IF NumeroOrdiniRider > 3 THEN
		RAISE EXCEPTION 'BEC1: un rider non può trasportare più di 3 ordini alla volta';
	END IF;

	RETURN NEW;
END
$$;


CREATE TRIGGER controlla_numero_ordini_trasportati_rider_trigger
BEFORE INSERT ON RiderPropostiConsegna
FOR EACH ROW
EXECUTE FUNCTION controlla_numero_ordini_trasportati_rider();

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---Trigger che non permette a un Rider e un Cliente la cancellazione dell'account finche tutti gli ordini non sono nello stato di BOZZA,CONSEGNATO o ANNULATO

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
    WHERE rpc.nickname_rider = OLD.nickname
    AND o.stato IN (2,3); --PRONTO_RITIRO_RIDER,IN_CONSEGNA

	SELECT count(*) INTO NOrdiniClienteSospesi
	FROM Ordine o
	WHERE o.nickname_cliente = OLD.nickname
	AND o.stato IN (2,3); --PRONTO_RITIRO_RIDER,IN_CONSEGNA

	NOrdiniSospesi := NOrdiniRiderSospesi + NOrdiniClienteSospesi;

    IF NOrdiniSospesi > 0 THEN
        RAISE EXCEPTION 'BEC2: non puoi cancellare l''Account mentre hai % ordine/i attivo/i in consegna.', NOrdiniSospesi;
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER impedisci_cancellazione_utente_trigger
BEFORE DELETE ON Rider
FOR EACH ROW
EXECUTE FUNCTION controlla_cancellazione_account();

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---Trigger che impedisce la cancellazione del Ristorante finche tutti gli ordini non sono nello stato di BOZZA,CONSEGNATO o ANNULATO

CREATE OR REPLACE FUNCTION controlla_cancellazione_ristorante()
RETURNS TRIGGER LANGUAGE PLPGSQL
AS $$
DECLARE
	NOrdiniSospesi INT;
BEGIN 

	SELECT count(*) INTO NOrdiniSospesi
	FROM Ordine o
	WHERE o.codice_ristorante = OLD.codice_ristorante;

	IF NOrdiniSospesi > 0 THEN
		RAISE EXCEPTION 'BEC3: non puoi cancellare il Ristorante mentre hai % ordine/i attivo/i in consegna.', NOrdiniSospesi;
	END IF;

	
END;
$$;

CREATE TRIGGER impedisci_cancellazione_ristorante_trigger
BEFORE DELETE ON Ristorante
FOR EACH ROW
EXECUTE FUNCTION controlla_cancellazione_ristorante();


------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---Trigger che aggiunga un punto_fedeltà al cliente, una volta che l'ordine entrato nello StatoOrdine CONSEGNATO

CREATE OR REPLACE FUNCTION aggiungi_punto_fedelta()
RETURNS TRIGGER LANGUAGE PLPGSQL
AS $$
 
BEGIN 
	
	IF NEW.costo >= 20 THEN
		UPDATE Cliente c SET punti_fedelta = punti_fedelta + 1 WHERE c.nickname = NEW.nickname_cliente;
	END IF;
	
	return NEW;
END;
$$;

CREATE TRIGGER aggiungi_punto_fedelta_trigger
AFTER UPDATE ON Ordine
FOR EACH ROW
EXECUTE FUNCTION aggiungi_punto_fedelta();
