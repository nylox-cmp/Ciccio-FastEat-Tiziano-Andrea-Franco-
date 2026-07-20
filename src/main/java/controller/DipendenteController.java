package controller;

import exception.ErrorType;
import model.*;

import java.util.Optional;

public class DipendenteController {
    private Dipendente dipendente;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public DipendenteController(){
        Optional<Dipendente> optionalDipendente = SessionManager.instance.get_utente().get_ruolo_utente(Dipendente.class);
        if(optionalDipendente.isPresent())
            this.dipendente = optionalDipendente.get();
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Dipendente

    public void licenziati(){
        dipendente.licenziati();

        this.dipendente = null;
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione Rider

    public void accetta_rider(Ordine ordine,Rider rider){
        dipendente.accetta_rider(ordine,rider);
    }

    public void rifiuta_rider(Ordine ordine,Rider rider){
        dipendente.rifiuta_rider(ordine,rider);
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione Ordini

    public void segnala_ordine_pronto_ritiro(Ordine ordine){
        dipendente.segnala_ordine_pronto_ritiro(ordine);
    }

    public void annulla_ordine(Ordine ordine){
        dipendente.annulla_ordine(ordine);
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Gestione Dipendenti Subordinati

    public void modifica_ruolo_dipendente(Dipendente dipendente, Ruolo ruolo){
        dipendente.modifica_ruolo_dipendente(dipendente,ruolo);
    }

    public void licenzia_dipendente(Dipendente dipendente){
        dipendente.licenzia_dipendente(dipendente);
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public Dipendente get_dipendente(){return dipendente;}
    public void set_dipendente(Dipendente dipendente){this.dipendente = dipendente;}

}