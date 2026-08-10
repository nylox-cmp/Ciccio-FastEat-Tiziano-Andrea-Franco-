package controller;

import ImplementazioniDAO.ClienteImpDAO;
import ImplementazioniDAO.RistoranteImpDAO;
import ImplementazioniDAO.Utils.EntityWitchId;
import controller.Utils.SessionManager;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class ClienteController {
    private Cliente cliente;
    private ClienteImpDAO clienteDB = new ClienteImpDAO();

    private ArrayList<Ordine> ordini = new ArrayList<Ordine>();
    private ArrayList<RigaOrdine> righe_ordine = new ArrayList<RigaOrdine>();

    private HashMap<Menu,Integer> id_menu = new HashMap<Menu,Integer>();
    private HashMap<RigaOrdine,ArrayList<String>> id_righeOrdine = new HashMap<RigaOrdine,ArrayList<String>>();

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

        clienteDB.crea_ordine(ordine.get_codice_ordine(),ordine.get_costo(),ordine.get_stato_ordine(),ordine.get_indirizzo(),ordine.get_data());
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

    public RigaOrdine aggiungi_riga(Ordine ordine, Prodotto prodotto) {
        RigaOrdine riga_ordine = ordine.aggiungi_riga(prodotto,RigaOrdine.QUANTITA_CREAZIONE_RIGA_ORDINE,ordine);
        clienteDB.aggiungi_riga(ordine.get_codice_ordine(),Integer.parseInt(id_righeOrdine.get(ordine).get(1)),RigaOrdine.QUANTITA_CREAZIONE_RIGA_ORDINE);
        return riga_ordine;
    }

    public void rimuovi_riga(Ordine ordine, RigaOrdine riga_ordine) {
         ordine.rimuovi_riga(riga_ordine);
         clienteDB.rimuovi_riga(ordine.get_codice_ordine(),Integer.parseInt(id_righeOrdine.get(ordine).get(1)));
    }

    public void aumenta_quantita_prodotto(Ordine ordine,RigaOrdine riga_ordine) {
        riga_ordine.aumenta_quantita();
        clienteDB.aggiorna_quantita_rigaOrdine(ordine.get_codice_ordine(),Integer.parseInt(id_righeOrdine.get(ordine).get(1)),riga_ordine.get_quantita());
    }

    public void diminuisci_quantita_prodotto(Ordine ordine,RigaOrdine riga_ordine) {
        riga_ordine.diminuisci_quantita();
        clienteDB.aggiorna_quantita_rigaOrdine(ordine.get_codice_ordine(),Integer.parseInt(id_righeOrdine.get(ordine).get(1)),riga_ordine.get_quantita());
    }

    //________________________________________________________________________________________________________________________________________________
    //Get and Set

    public Cliente get_cliente() {
        return cliente;
    }

    public ArrayList<Ordine> get_ordini(){
        ordini = clienteDB.get_ordini_cliente(cliente.get_nickname());
        return ordini;
    }

    public ArrayList<RigaOrdine>  get_righeOrdine(Ordine ordine) {
        EntityWitchId<RigaOrdine, ArrayList<String>> righeOrdineMap = clienteDB.get_contenuto_ordine(ordine.get_codice_ordine());

        righe_ordine = righeOrdineMap.entitys;

        for(int i=0;i<righeOrdineMap.ids.size();i++){
            id_righeOrdine.put(righe_ordine.get(i),righeOrdineMap.ids.get(i));
        }
        return righe_ordine;
    }

    public ArrayList<Ristorante> get_ristoranti(){
        return clienteDB.get_ristoranti();
    }

    public ArrayList<Menu> get_menu(Ristorante ristorante){
        id_menu = new HashMap<Menu,Integer>();
        EntityWitchId<Menu,Integer> menuMap = RistoranteImpDAO.get_menu(ristorante.get_codice_ristorante());

        for(int i=0;i<menuMap.entitys.size();i++){
            id_menu.put(menuMap.entitys.get(i),menuMap.ids.get(i));
        }
        return menuMap.entitys;
    }

    public ArrayList<Prodotto> get_prodotti(Menu menu){
        return RistoranteImpDAO.get_prodotti(id_menu.get(menu)).entitys;
    }
}
