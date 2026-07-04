package controller;

import exception.ErrorType;
import model.*;
import controller.*;

import java.util.ArrayList;


public class RiderController {
    private Rider rider; 
    private UtenteController utente_controller;


    public RiderController(UtenteController utente_controller){
        this.utente_controller = utente_controller;
        this.rider = this.utente_controller.get_dati_utente().get_rider();
    }

    //________________________________________________________________________________________________________________________________________________
    // operazioni Rider

    public ErrorType richiedi_approvazzione_consegna(Ordine ordine){
       ErrorType error = rider.richiedi_approvazione_consegna(ordine);
       return error;
    }

    public void conferma_consegna_ordine(Ordine ordine){
      rider.conferma_cosegna_ordine(ordine);
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and set

    public Rider get_rider() { return rider; }
    public void set_rider(Rider rider){ this.rider = rider;}

    public UtenteController get_utente_controller(){ return utente_controller;}
    public void set_utente_controller(UtenteController utente_controller){ this.utente_controller = utente_controller; }
}
