package controller;

import exception.ErrorType;
import model.*;

import java.util.Optional;

public class DipedenteController {
    private Dipedente dipedente;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public DipedenteController(){
        Optional<Dipedente> optionalDipedente = SessionManager.instance.get_utente().get_ruolo_utente(Dipedente.class);
        if(optionalDipedente.isPresent())
            this.dipedente = optionalDipedente.get();
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Dipedente

    public ErrorType licenziati(){
        ErrorType error =  dipedente.licenziati();
        if(error != ErrorType.NESSUN_ERRORE) return error;

        this.dipedente = null;
        return ErrorType.NESSUN_ERRORE;
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione Rider

    public void accetta_rider(Ordine ordine,Rider rider){
        dipedente.accetta_rider(ordine,rider);
    }

    public void rifiuta_rider(Ordine ordine,Rider rider){
        dipedente.rifiuta_rider(ordine,rider);
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione Ordini

    public ErrorType segnala_ordine_pronto_ritiro(Ordine ordine){
        return dipedente.segnala_ordine_pronto_ritiro(ordine);
    }

    public void annulla_ordine(Ordine ordine){
        dipedente.annulla_ordine(ordine);
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Gestione Dipedenti Subordinati

    public ErrorType modifica_ruolo_dipedente(Dipedente dipedente,Ruolo ruolo){
        return dipedente.modifica_ruolo_dipendente(dipedente,ruolo);
    }

    public ErrorType licenzia_dipedente(Dipedente dipedente){
        return dipedente.licenzia_dipedente(dipedente);
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public Dipedente get_dipedente(){return dipedente;}

    public void set_dipedente(Dipedente dipedente){this.dipedente = dipedente;}

}