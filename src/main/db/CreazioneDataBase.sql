
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Appunti

--Corrisponedenti numerici Enum Java StatOrdine e Ruolo:
--BOZZA 0,PREPARAZIONE 1,PRONTO_RITIRO_RIDER 2,IN_CONSEGNA 3,CONFERMA_CONSEGNA_RIDER 4,CONFERMA_CONSEGNA_CLIENTE 5,CONSEGNATO 6,ANNULLATO 7
--BASE 0,GESTIONALE 2,MANAGER 3

--Scelti di Manipolare tramite un numero Intero,poiche più facili da prendere l'output delle query in java a discapito della leggibilita dal punto di vista del DBMS
--ma migliore delle ulteriore alternativa Varchar poiche è possibile fare delle operazione di ordinamento con i numeri Interi

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
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
    nickname varchar(32) PRIMARY KEY,
    punti_fedelta int NOT NULL,

	FOREIGN KEY (nickname) REFERENCES Utente(nickname) ON DELETE CASCADE 
);

CREATE TABLE Rider(
    nickname varchar(32) PRIMARY KEY,
    mezzo_trasporto varchar(64) NOT NULL,

	FOREIGN KEY (nickname) REFERENCES Utente(nickname) ON DELETE CASCADE 
);

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Ristorante

CREATE TABLE Ristorante(
    codice_ristorante varchar(12) PRIMARY KEY,
    nome varchar(64) NOT NULL,
    indirizzo varchar(128) NOT NULL
);

CREATE TABLE Menu(
    id_menu serial PRIMARY KEY,
    nome varchar(64) NOT NULL,
    codice_ristorante varchar(12) NOT NULL,

	FOREIGN KEY (codice_ristorante) REFERENCES Ristorante(codice_ristorante) ON DELETE CASCADE
);

CREATE TABLE Prodotto(
    id_prodotto serial PRIMARY KEY,
    nome varchar(64) NOT NULL,
    prezzo_unitario DECIMAL(10,2) NOT NULL,
    id_menu int NOT NULL,

	FOREIGN KEY (id_menu) REFERENCES Menu(id_menu) ON DELETE CASCADE
);

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Dipendente 

CREATE TABLE Dipendente(
    nickname varchar(32) PRIMARY KEY,
    ruolo int NOT NULL,
	codice_ristorante varchar(12) NOT NULL,
	
	FOREIGN KEY (nickname) REFERENCES Utente(nickname) ON DELETE CASCADE 
);

CREATE TABLE GestioneDipendente(
    superiore varchar(32) NOT NULL,
    subordinato varchar(32) NOT NULL,
	
    PRIMARY KEY(superiore, subordinato),
	FOREIGN KEY (superiore) REFERENCES  Dipendente(nickname) ON DELETE CASCADE,
	FOREIGN KEY (subordinato) REFERENCES Utente(nickname) ON DELETE CASCADE
);

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Ordine

CREATE TABLE Ordine(
    codice_ordine varchar(12) PRIMARY KEY,
    costo DECIMAL(10,2) NOT NULL,
    stato int NOT NULL,
    indirizzo varchar(128) NOT NULL,
    data_ordine DATE NOT NULL, 
    nickname_cliente varchar(32) NOT NULL,
    codice_ristorante varchar(12) NOT NULL,

	FOREIGN KEY (nickname_cliente) REFERENCES Cliente(nickname) ON DELETE CASCADE,
	FOREIGN KEY (codice_ristorante) REFERENCES Ristorante(codice_ristorante) ON DELETE CASCADE
);

CREATE TABLE RigaOrdine(
    id_prodotto int NOT NULL,
    codice_ordine varchar(12) NOT NULL,
    quantita int NOT NULL,

    PRIMARY KEY(id_prodotto, codice_ordine),
	FOREIGN KEY (id_prodotto) REFERENCES Prodotto(id_prodotto) ON DELETE CASCADE,
	FOREIGN KEY (codice_ordine) REFERENCES Ordine(codice_ordine) ON DELETE CASCADE
);

CREATE TABLE RiderPropostiConsegna(
    nickname_rider varchar(32) NOT NULL,
    codice_ordine varchar(12) NOT NULL,
    ordine_preso_a_carico boolean NOT NULL DEFAULT (ordine_preso_a_carico = false),
	
    PRIMARY KEY(nickname_rider, codice_ordine),
	FOREIGN KEY (nickname_rider) REFERENCES Rider(nickname) ON DELETE CASCADE,
	FOREIGN KEY (codice_ordine) REFERENCES Ordine(codice_ordine) ON DELETE CASCADE
);

