------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Appunti 

--Corrisponedenti numerici Enum Java StatOrdine e Ruolo:
--BOZZA 0,PREPARAZIONE 1,PRONTO_RITIRO_RIDER 2,IN_CONSEGNA 3,CONFERMA_CONSEGNA_RIDER 4,CONFERMA_CONSEGNA_CLIENTE 5,CONSEGNATO 6,ANNULLATO 7
--BASE 0,GESTIONALE 2,MANAGER 3

--un'OrdineInSospeso è un Ordine Presente in uno di questi StatiOrdine (PREPARAZIONE 1,PRONTO_RITIRO_RIDER 2,IN_CONSEGNA 3,CONFERMA_CONSEGNA_RIDER 4,CONFERMA_CONSEGNA_CLIENTE 5)
--OrdineNonSospeso si Presenta in uno di questi StatiOrdine (BOZZA 0,CONSEGNATO 6,ANNULLATO 7)

--BEC (Buisness Error Code) errori generati da un trigger da intercettare in java tramite i seguenti codici:
--BEC0 controlla_ordine_vuoto_stato_preparazione_trigger
--BEC1 controlla_numero_ordini_trasportati_rider_trigger
--BEC2 (gestisci_cancellazione_cliente_trigger,gestisci_cancellazione_rider_trigger)
--BEC3 gestici_cancellazione_ristorante

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
---Trigger che non permetta la Cancellazione del Cliente se e solo se siano  presenti OrdiniInSospeso
--Che siano nello StatoOrdine (PREPARAZIONE 1,PRONTO_RITIRO_RIDER 2,IN_CONSEGNA 3,CONFERMA_CONSEGNA_RIDER 4,CONFERMA_CONSEGNA_CLIENTE 5)

CREATE OR REPLACE FUNCTION gestisci_cancellazione_cliente()
RETURNS TRIGGER LANGUAGE PLPGSQL
AS $$
DECLARE
	NOrdiniSospesi INT;
BEGIN

	SELECT count(*) INTO NOrdiniSospesi
	FROM Ordine o
	WHERE o.nickname_cliente = OLD.nickname
	AND o.stato IN (1,2,3,4,5);

    IF NOrdiniSospesi > 0 THEN
        RAISE EXCEPTION 'BEC2: non puoi cancellare l''Account mentre hai % ordine/i attivo/i in consegna.', NOrdiniSospesi;
    END IF;

    RETURN OLD;
END;
$$;

CREATE TRIGGER gestisci_cancellazione_cliente_trigger
BEFORE DELETE ON Cliente
FOR EACH ROW
EXECUTE FUNCTION gestisci_cancellazione_cliente();

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---Trigger che non permetta la Cancellazione del Cliente se e solo se siano  presenti OrdiniInSospeso
--Che siano nello StatoOrdine (PREPARAZIONE 1,PRONTO_RITIRO_RIDER 2,IN_CONSEGNA 3,CONFERMA_CONSEGNA_RIDER 4,CONFERMA_CONSEGNA_CLIENTE 5)

CREATE OR REPLACE FUNCTION gestisci_cancellazione_rider()
RETURNS TRIGGER LANGUAGE PLPGSQL
AS $$
DECLARE
	NOrdiniSospesi INT;
BEGIN

    SELECT count(*) INTO NOrdiniSospesi
    FROM Ordine o
    JOIN RiderPropostiConsegna rpc ON o.codice_ordine = rpc.codice_ordine
    WHERE rpc.nickname_rider = OLD.nickname
    AND o.stato IN (1,2,3,4,5); --PRONTO_RITIRO_RIDER,IN_CONSEGNA

    IF NOrdiniSospesi > 0 THEN
        RAISE EXCEPTION 'BEC2: non puoi cancellare l''Account mentre hai % ordine/i attivo/i in consegna.', NOrdiniSospesi;
    END IF;

RETURN NEW;
END;
$$;

CREATE TRIGGER gestisci_cancellazione_rider_trigger
BEFORE DELETE ON Rider
FOR EACH ROW
EXECUTE FUNCTION gestisci_cancellazione_rider();

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Trigger che cancella il ristorante alla cancellazione del dipedente con il Ruolo "Manager" se e solo se siano presenti degli OrdiniInSospeso
--che siano nello StatoOrdine (PREPARAZIONE 1,PRONTO_RITIRO_RIDER 2,IN_CONSEGNA 3,CONFERMA_CONSEGNA_RIDER 4,CONFERMA_CONSEGNA_CLIENTE 5)

CREATE OR REPLACE FUNCTION gestisci_cancellazione_dipendente()
RETURNS TRIGGER LANGUAGE PLPGSQL
AS $$
DECLARE
    NOrdiniSospesi INT;
BEGIN
	IF OLD.ruolo = 2 THEN
        SELECT count(*) INTO NOrdiniSospesi
        FROM Ordine o
        WHERE o.codice_ristorante = OLD.codice_ristorante AND o.stato IN (1,2,3,4,5);

        IF NOrdiniSospesi > 0 THEN
                    RAISE EXCEPTION 'BEC3: non puoi cancellare il Ristorante mentre hai % ordine/i attivo/i in consegna.', NOrdiniSospesi;
        END IF;

        DELETE FROM Ristorante WHERE codice_ristorante = OLD.codice_ristorante;
    END IF;

	RETURN OLD;
END;
$$;

CREATE TRIGGER gestisci_cancellazione_dipendente_trigger
BEFORE DELETE ON Dipendente
FOR EACH ROW
EXECUTE FUNCTION gestisci_cancellazione_dipendente();

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---Trigger che aggiunga un punto_fedeltà al cliente, una volta che l'ordine entrato nello StatoOrdine CONSEGNATO

CREATE OR REPLACE FUNCTION aggiungi_punto_fedelta()
RETURNS TRIGGER LANGUAGE PLPGSQL
AS $$
BEGIN

    IF NEW.stato = 6 AND NEW.costo >= 20 THEN
        UPDATE Cliente c SET punti_fedelta = punti_fedelta + 1
        WHERE c.nickname = NEW.nickname_cliente;
    END IF;
    RETURN NEW;
	
END;
$$;

CREATE TRIGGER aggiungi_punto_fedelta_trigger
AFTER UPDATE ON Ordine
FOR EACH ROW
EXECUTE FUNCTION aggiungi_punto_fedelta();

SELECT * FROM RiderPropostiConsegna;