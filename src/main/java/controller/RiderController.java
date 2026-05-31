package controller;

import model.*;
import controller.*;

import java.util.ArrayList;


public class RiderController {
    private Rider rider; //ReadOnly (al di fuori della classe)
    private UtenteController utente_controller;


    //________________________________________________________________________________________________________________________________________________
    //

    public boolean utente_is_rider(){
        return(utente_controller.dati_utente.get_rider() != null);
    }

    public void sing_in_rider(String mezzo_trasporto){
        this.utente_controller.dati_utente.set_rider(utente_controller.get_utente(),mezzo_trasporto);
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

    public void set_utente_controller(UtenteController utente_controller){
        this.utente_controller = utente_controller;
    }
}
