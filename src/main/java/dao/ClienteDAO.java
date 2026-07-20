package dao;


import model.*;

import java.time.LocalDate;
import java.util.ArrayList;

public interface ClienteDAO {

    public void crea_ordine(String codice_ordine, double costo, StatoOrdine stato_ordine, String indirizzo, LocalDate date);
    public void annulla_ordine(String codice_ordine);
    public void conferma_creazione_ordine(String codice_ordine);
    public void conferma_consegna_ordine(String codice_ordine);

    public void applica_sconto(String ordine,double costo);

    public void aggiungi_riga(String codice_ordine,String codice_ristorante,String nome_prodotto);
    public void rimuovi_riga(String codice_ordine,String codice_ristorante,String nome_prodotto);

    public void aumenta_quantita_prodotto(String codice_ordine,String codice_ristorante,String nome_prodotto);
    public void diminuisci_quantita_prodotto(String codice_ordine,String codice_ristorante,String nome_prodotto);

    public ArrayList<Ristorante> get_ristoranti();
    public ArrayList<Ordine> get_ordini_cliente(String nickname);
 }