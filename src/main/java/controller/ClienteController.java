package controller;

import ImplementazioniDAO.ClienteImpDAO;
import ImplementazioniDAO.OrdiniImpDAO;
import ImplementazioniDAO.RistoranteImpDAO;
import ImplementazioniDAO.Utils.EntityWitchId;
import controller.Utils.SessionManager;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class ClienteController{
    private Cliente cliente;
    private ClienteImpDAO clienteDB = new ClienteImpDAO();

    private ArrayList<Ordine> ordini = new ArrayList<Ordine>();
    private ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();

    private HashMap<Menu,Integer> id_menu = new HashMap<Menu,Integer>();
    private HashMap<String,Integer> id_prodotto = new HashMap<String, Integer>();

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public ClienteController( ){
        Optional<Cliente> optionalCliente = SessionManager.instance.get_utente().get_ruolo_utente(Cliente.class);
        if(optionalCliente.isPresent())
            this.cliente = optionalCliente.get();
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione Ordine

    private void gestisci_collissioni_codice_ordine(Ordine ordine){
        while(clienteDB.codice_ordine_esiste(ordine.get_codice_ordine()))
            ordine.set_codice_ordine(Ristorante.genera_codice_univoco());
    }

    public void crea_ordine(String indirizzo, Ristorante ristorante) {
        Ordine ordine = cliente.crea_ordine(indirizzo, ristorante);
        gestisci_collissioni_codice_ordine(ordine);

        clienteDB.crea_ordine(ordine.get_codice_ordine(), ordine.get_costo(),ordine.get_stato_ordine(),ordine.get_indirizzo(),ordine.get_data(),ristorante.get_codice_ristorante());
    }

    public void annulla_ordine(Ordine ordine) {
        cliente.annulla_ordine(ordine);
        clienteDB.annulla_ordine(ordine.get_codice_ordine());
    }

    public void conferma_creazione_ordine(Ordine ordine) {
        cliente.conferma_creazione_ordine(ordine);
        clienteDB.conferma_creazione_ordine(ordine.get_codice_ordine());
    }

    public void conferma_consegna_ordine(Ordine ordine) {
        cliente.conferma_consegna_ordine(ordine);
        clienteDB.conferma_creazione_ordine(ordine.get_codice_ordine());
    }

    public void applica_sconto(Ordine ordine, int punti_fedelta) {
        cliente.applica_sconto(punti_fedelta,ordine);
        clienteDB.applica_sconto(ordine.get_codice_ordine(),ordine.get_costo());
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione RigaOrdine

    public void aggiungi_riga(Ordine ordine, Prodotto prodotto) {
        ordine.aggiungi_riga(prodotto,RigaOrdine.QUANTITA_CREAZIONE_RIGA_ORDINE,ordine);
        clienteDB.aggiungi_riga(ordine.get_codice_ordine(),id_prodotto.get(prodotto.get_nome()),RigaOrdine.QUANTITA_CREAZIONE_RIGA_ORDINE);
    }

    public void rimuovi_riga(Ordine ordine, Prodotto prodotto) {
         ordine.rimuovi_riga(prodotto);
         clienteDB.rimuovi_riga(ordine.get_codice_ordine(),id_prodotto.get(prodotto.get_nome()));
    }

    public void aggiorna_quantita_rigaOrdine(Ordine ordine,RigaOrdine riga_ordine,int quantita){
        riga_ordine.aggiorna_quantita(quantita);
        clienteDB.aggiorna_quantita_rigaOrdine(ordine.get_codice_ordine(),id_prodotto.get(riga_ordine.get_prodotto().get_nome()),riga_ordine.get_quantita());
    }

    //________________________________________________________________________________________________________________________________________________
    //Get and Set

    public Cliente get_cliente() {
        return cliente;
    }

    public ArrayList<Ordine> get_ordini(){
        ordini = clienteDB.get_ordini_cliente(cliente.get_nickname());

        for(Ordine ordine : ordini){
            ordine.set_righe_ordine(OrdiniImpDAO.get_righeOrdine(ordine.get_codice_ordine()).entitys);
        }

        return ordini;
    }

    public ArrayList<Ristorante> get_ristoranti(String search_nome_o_indirizzo){
        return clienteDB.get_ristoranti(cliente.get_nickname(),search_nome_o_indirizzo);
    }

    public ArrayList<Menu> get_menu(Ristorante ristorante){
        id_menu = new HashMap<Menu,Integer>();
        EntityWitchId<Menu,Integer> menuMap = RistoranteImpDAO.get_menu(ristorante.get_codice_ristorante());

        for(int i=0;i<menuMap.entitys.size();i++){
            id_menu.put(menuMap.entitys.get(i),menuMap.ids.get(i));
            menuMap.entitys.get(i).set_ristorante(ristorante);
        }

        return menuMap.entitys;
    }

    public ArrayList<Prodotto> get_prodotti(Menu menu){
        EntityWitchId<Prodotto,Integer> prodottiMap =  RistoranteImpDAO.get_prodotti(id_menu.get(menu));
        if(prodottiMap == null) return null;

        for(int i=0;i<prodottiMap.entitys.size();i++){
            id_prodotto.put(prodottiMap.entitys.get(i).get_nome(),prodottiMap.ids.get(i));
            prodottiMap.entitys.get(i).set_menu(menu);
        }
        this.prodotti = prodottiMap.entitys;
        return prodotti;
    }
}
