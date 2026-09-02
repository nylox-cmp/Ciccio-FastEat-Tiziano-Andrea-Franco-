package controller;

import ImplementazioniDAO.DipendenteImpDAO;
import ImplementazioniDAO.OrdiniImpDAO;
import controller.Utils.SessionManager;
import dao.DipendenteDAO;
import model.*;

import java.util.ArrayList;
import java.util.Optional;

public class DipendenteController{
    private Dipendente dipendente;
    private DipendenteImpDAO dipendenteDB = new DipendenteImpDAO();

    private ArrayList<Dipendente> subordinati = new ArrayList<Dipendente>();
    private ArrayList<Ordine> ordini = new ArrayList<Ordine>();

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public DipendenteController(){
        Optional<Dipendente> optionalDipendente = SessionManager.instance.get_utente().get_ruolo_utente(Dipendente.class);
        if(optionalDipendente.isPresent())
            this.dipendente = optionalDipendente.get();

        if(dipendente.get_ristorante() == null) dipendente.set_ristorante(dipendenteDB.get_ristorante(dipendente.get_nickname()));
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Dipendente

    public void licenziati(){
        dipendente.licenziati();
        dipendenteDB.licenziati(dipendente.get_nickname());
        this.dipendente = null;
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione Rider

    public void accetta_rider(Ordine ordine,Rider rider){
        dipendente.accetta_rider(ordine,rider);
        dipendenteDB.accetta_rider(ordine.get_codice_ordine(),rider.get_nickname());
    }

    public void rifiuta_rider(Ordine ordine,Rider rider){
        dipendente.rifiuta_rider(ordine,rider);
        dipendenteDB.rifiuta_rider(ordine.get_codice_ordine(),rider.get_nickname());
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione Ordini

    public void segnala_ordine_pronto_ritiro(Ordine ordine){
        dipendente.segnala_ordine_pronto_ritiro(ordine);
        OrdiniImpDAO.aggiorna_stato_ordine(ordine.get_codice_ordine(),ordine.get_stato_ordine());
    }

    public void annulla_ordine(Ordine ordine){
        dipendente.annulla_ordine(ordine);
        OrdiniImpDAO.aggiorna_stato_ordine(ordine.get_codice_ordine(),ordine.get_stato_ordine());
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Gestione Dipendenti Subordinati

    public void modifica_ruolo_dipendente(Dipendente subordinato, Ruolo ruolo){
        dipendente.modifica_ruolo_dipendente(subordinato,ruolo);
        dipendenteDB.modifica_ruolo_dipedente(subordinato.get_nickname(),ruolo);
    }

    public void licenzia_dipendente(Dipendente subordinato){
        dipendente.licenzia_dipendente(subordinato);
        dipendenteDB.licenzia_dipedente(subordinato.get_nickname());
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public Dipendente get_dipendente(){return dipendente;}


    public ArrayList<Dipendente> get_subordinati(){
        subordinati = dipendenteDB.get_subordinati(dipendente.get_ristorante().get_codice_ristorante(),dipendente.get_ruolo());
        dipendente.set_subordinati(subordinati);
        return subordinati;
    }

    public ArrayList<Ordine> get_ordini(){
        ordini = dipendenteDB.get_ordini_ristorante(dipendente.get_ristorante().get_codice_ristorante());
        dipendente.get_ristorante().set_ordini(ordini);
        return ordini;
    }

    public ArrayList<Rider> get_rider_proposti_consegna(Ordine ordine){
         ArrayList<Rider> rider_proposti = dipendenteDB.get_rider_proposti_consegna(ordine.get_codice_ordine());
         ordine.set_rider_proposti(rider_proposti);
         return rider_proposti;
    }
}