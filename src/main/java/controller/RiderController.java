package controller;

import exception.ErrorType;
import model.Ordine;
import model.Rider;

import java.util.Optional;


public class RiderController {
    private Rider rider; 

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public RiderController(UtenteController utente_controller){
        Optional<Rider> optionalRider = SessionManager.instance.get_utente().get_ruolo_utente(Rider.class);
        if(optionalRider.isPresent())
            this.rider = optionalRider.get();
    }

    //________________________________________________________________________________________________________________________________________________
    // operazioni Rider

    public ErrorType richiedi_approvazione_consegna(Ordine ordine){
       ErrorType error = rider.richiedi_approvazione_consegna(ordine);
       return error;
    }

    public void conferma_consegna_ordine(Ordine ordine){
      rider.conferma_consegna_ordine(ordine);
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and set

    public Rider get_rider() { return rider; }
    public void set_rider(Rider rider){ this.rider = rider;}

}
