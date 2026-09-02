package controller;

import ImplementazioniDAO.DipendenteImpDAO;
import ImplementazioniDAO.OrdiniImpDAO;
import ImplementazioniDAO.RiderImpDAO;
import controller.Utils.SessionManager;
import model.Dipendente;
import model.Ordine;
import model.Rider;
import model.RigaOrdine;

import java.util.ArrayList;
import java.util.Optional;


public class RiderController {
    private Rider rider;
    private RiderImpDAO riderDB = new RiderImpDAO();

    private ArrayList<Ordine> ordini_proposti = new ArrayList<Ordine>();
    private ArrayList<Ordine> ordini_da_consegnare = new ArrayList<Ordine>();

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public RiderController(){
        Optional<Rider> optionalRider = SessionManager.instance.get_utente().get_ruolo_utente(Rider.class);
        if(optionalRider.isPresent())
            this.rider = optionalRider.get();
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione Richiesta Consegna

    public void crea_richiesta_approvazione_consegna(Ordine ordine){
        rider.crea_richiesta_approvazione_consegna(ordine);
        riderDB.crea_richiesta_approvazione_consegna(rider.get_nickname(),ordine.get_codice_ordine());
    }

    public void cancella_richiesta_approvazione_consegna(Ordine ordine){
        rider.cancella_richiesta_approvazione_consegna(ordine);
        riderDB.cancella_richiesta_approvazione_consegna(rider.get_nickname(),ordine.get_codice_ordine());
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione Ordine

    public void conferma_consegna_ordine(Ordine ordine){
      rider.conferma_consegna_ordine(ordine);
      OrdiniImpDAO.aggiorna_stato_ordine(ordine.get_codice_ordine(),ordine.get_stato_ordine());
    }

    public void segnala_ordine_as_in_consegna(Ordine ordine){
        rider.segnala_ordine_as_in_consegna(ordine);
        OrdiniImpDAO.aggiorna_stato_ordine(ordine.get_codice_ordine(),ordine.get_stato_ordine());
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and set

    public Rider get_rider() { return rider; }

    public ArrayList<Ordine> get_ordini_proposti(){
       this.ordini_proposti = riderDB.get_ordini_proposti(rider.get_nickname());
       return ordini_proposti;
    }

    public ArrayList<Ordine> get_ordini_da_consegnare(){
        this.ordini_da_consegnare = riderDB.get_ordini_da_consegnare(rider.get_nickname());
        rider.set_ordini_da_consegnare(ordini_da_consegnare);
        return ordini_da_consegnare;
    }

}
