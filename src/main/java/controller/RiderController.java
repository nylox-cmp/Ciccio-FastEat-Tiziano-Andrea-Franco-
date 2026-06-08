package controller;

import model.*;
import controller.*;

import java.util.ArrayList;


public class RiderController {
    private Rider rider; 
    private UtenteController utente_controller;


    public RiderController(UtenteController utente_controller){
        this.utente_controller = utente_controller;
    }

    //________________________________________________________________________________________________________________________________________________
    // operazioni Rider

    public void richiedi_approvazzione_consegna(Ordine ordine){
        rider.richiedi_approvazione_consegna(ordine);
    }

    public void conferma_consegna_ordine(Ordine ordine){
        rider.richiedi_approvazione_consegna(ordine);
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and set

    public Rider get_rider() { return rider; }
    public void set_rider(Rider rider){ this.rider = rider;}

    public UtenteController get_utente_controller(){ return utente_controller;}
    public void set_utente_controller(UtenteController utente_controller){ this.utente_controller = utente_controller; }
}
