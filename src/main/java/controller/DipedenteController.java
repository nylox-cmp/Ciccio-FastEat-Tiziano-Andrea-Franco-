package controller;

import exception.ErrorType;
import model.*;

public class DipedenteController {
    private UtenteController utente_controller;
    private  Dipedente dipedente;

    public DipedenteController(UtenteController utenteController){
        this.utente_controller = utenteController;
        this.dipedente = utenteController.get_dati_utente().get_dipedente();
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione sul Ristorante

    public ErrorType cancella_ristorante(){
       ErrorType error = dipedente.cancella_ristorante(dipedente.get_ristorante());
       if(error != ErrorType.NESSUN_ERRORE) return error;

       utente_controller.get_dati_utente().rimuovi_dipedente();
       return ErrorType.NESSUN_ERRORE;
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione Dipedenti

    public ErrorType licenziati(){
        ErrorType error =  dipedente.licenziati();
        if(error != ErrorType.NESSUN_ERRORE) return error;
        utente_controller.get_dati_utente().rimuovi_dipedente();
        return ErrorType.NESSUN_ERRORE;
    }

    public ErrorType accetta_richiesta_assunzione(Utente utente) {
        return  dipedente.accetta_richiesta_assunzione(utente);
    }

    public ErrorType licenzia_dipedente(Dipedente dipedente){
        return dipedente.licenzia_dipedenti(dipedente);
    }

    public ErrorType rimuovi_richiesta_assunzione(Utente utente){
        return dipedente.rimuovi_richiesta_assunzione(utente);
    }

    public ErrorType modifica_ruolo_dipedente(Dipedente dipedente,Ruolo ruolo){
        return dipedente.modifica_ruolo_dipente(dipedente,ruolo);
    }

    public void accetta_rider(Ordine ordine,Rider rider){
        dipedente.accetta_rider(ordine,rider);
    }

    public void rifiuta_rider(Ordine orine,Rider rider){
        dipedente.rifiuta_rider(orine,rider);
    }

    public ErrorType segnala_ordine_pronto_ritiro(Ordine ordine){
        return dipedente.segnala_ordine_pronto_ritiro(ordine);
    }

    public void annulla_ordine(Ordine ordine){
        dipedente.annulla_ordine(ordine);
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public Dipedente get_dipedente(){return dipedente;}
    public void set_dipendete(Dipedente dipedente){this.dipedente = dipedente;}

    public void set_utente_controller(UtenteController utente_controller){
        this.utente_controller = utente_controller;
    }
}