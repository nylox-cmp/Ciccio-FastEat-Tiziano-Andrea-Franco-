package dao;


import model.*;

import java.time.LocalDate;
import java.util.ArrayList;

public interface ClienteDAO {

    public void crea_ordine(String codice_ordine, double costo, StatoOrdine stato_ordine, String indirizzo, LocalDate date, String codice_ristorante);
    public void aggiorna_costo_ordine(String codice_ordine, double costo);
    public void applica_sconto(String ordine,double costo);
    public void salva_punti_fedelta_cliente(String nickname,int punti_fedelta);

    public void aggiungi_riga(String codice_ordine,int id_prodotto,int quantita);
    public void rimuovi_riga(String codice_ordine,int id_prodotto);
    public void aggiorna_quantita_rigaOrdine(String codice_ordine,int id_prodotto,int quantita);

    public ArrayList<Ristorante> get_ristoranti(String nickname,String search_nome_o_indrizzo);
    public ArrayList<Ordine> get_ordini_cliente_ristorante(String nickname, String codice_ristorante);
    public ArrayList<Ordine> get_ordini_cliente(String  nickname);
    public Integer get_punti_fedelta_cliente(String nickname);
 }