

-- =============================================================================
-- Popolamento minimo per testare i trigger con account test.unina
-- =============================================================================

-- 1. Inserimento utente e ruoli
INSERT INTO Utente (nickname, email, password, nome, cognome) VALUES
('test.unina', 'test@gmail.com', 'unina', 'test', 'unina');

INSERT INTO Cliente (nickname, punti_fedelta) VALUES
('test.unina', 0);

INSERT INTO Rider (nickname, mezzo_trasporto) VALUES
('test.unina', 'Bicicletta');

INSERT INTO Dipendente (nickname, ruolo, codice_ristorante) VALUES
('test.unina', 0, 'R001');  -- ruolo 0 = BASE

-- 2. Ristorante, Menu e Prodotto
INSERT INTO Ristorante (codice_ristorante, nome, indirizzo) VALUES
('R001', 'Ristorante Test', 'Via Test 1');

INSERT INTO Menu (nome, codice_ristorante) VALUES
('Menu Test', 'R001');

-- Recuperiamo l'id_menu appena creato (assumendo sia il primo, ma meglio usare una subquery)
INSERT INTO Prodotto (nome, prezzo_unitario, id_menu)
SELECT 'Prodotto Test', 10.00, id_menu
FROM Menu WHERE nome = 'Menu Test' AND codice_ristorante = 'R001';

-- 3. Ordini (stati: 0=BOZZA, 1=PREPARAZIONE, 2=PRONTO_RITIRO_RIDER, 3=IN_CONSEGNA, 6=CONSEGNATO)
INSERT INTO Ordine (codice_ordine, costo, stato, indirizzo, data_ordine, nickname_cliente, codice_ristorante) VALUES
('ORD1', 0,   0, 'Via Test 1', CURRENT_DATE, 'test.unina', 'R001'),  -- senza righe
('ORD2', 10,  0, 'Via Test 1', CURRENT_DATE, 'test.unina', 'R001'),  -- per test trigger 1 (successo) e trigger 5 (punto non aggiunto)
('ORD3', 20,  1, 'Via Test 1', CURRENT_DATE, 'test.unina', 'R001'),  -- per trigger 1 (già in stato 1), trigger 2 (conteggio rider), trigger 3, trigger 4, trigger 5 (punto aggiunto)
('ORD4', 30,  2, 'Via Test 1', CURRENT_DATE, 'test.unina', 'R001'),  -- per trigger 2,3,4
('ORD5', 40,  3, 'Via Test 1', CURRENT_DATE, 'test.unina', 'R001'),  -- per trigger 2,3,4
('ORD6', 10,  1, 'Via Test 1', CURRENT_DATE, 'test.unina', 'R001');  -- per test trigger 2 (inserimento rider proposto)

-- 4. RigheOrdine (collegano i prodotti agli ordini)
-- Usiamo una subquery per recuperare l'id_prodotto
INSERT INTO RigaOrdine (id_prodotto, codice_ordine, quantita)
SELECT id_prodotto, 'ORD2', 1 FROM Prodotto WHERE nome = 'Prodotto Test'
UNION ALL
SELECT id_prodotto, 'ORD3', 2 FROM Prodotto WHERE nome = 'Prodotto Test'
UNION ALL
SELECT id_prodotto, 'ORD4', 3 FROM Prodotto WHERE nome = 'Prodotto Test'
UNION ALL
SELECT id_prodotto, 'ORD5', 4 FROM Prodotto WHERE nome = 'Prodotto Test'
UNION ALL
SELECT id_prodotto, 'ORD6', 1 FROM Prodotto WHERE nome = 'Prodotto Test';

-- 5. RiderPropostiConsegna (assegnazione rider agli ordini)
-- test.unina avrà già 3 ordini in stato 1,2,3 (ORD3, ORD4, ORD5)
INSERT INTO RiderPropostiConsegna (nickname_rider, codice_ordine) VALUES
('test.unina', 'ORD3'),
('test.unina', 'ORD4'),
('test.unina', 'ORD5');

UPDATE Ordine SET stato = 1 WHERE codice_ordine = 'ORD1';
UPDATE Ordine SET stato = 1 WHERE codice_ordine = 'ORD2';

INSERT INTO RiderPropostiConsegna (nickname_rider, codice_ordine) VALUES ('test.unina', 'ORD6');

DELETE FROM Rider WHERE nickname = 'test.unina';

DELETE FROM Ristorante WHERE codice_ristorante = 'R001';

