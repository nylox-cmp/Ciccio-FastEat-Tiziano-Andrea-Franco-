package controller;

import exception.ErrorType;
import model.*;

public class DipedenteController {
    private Dipedente dipedente;
    private UtenteController utente_controller;


    //________________________________________________________________________________________________________________________________________________
    //

    public boolean utente_is_dipedente(){ return (utente_controller.dati_utente.get_dipedente() != null); }

    public ErrorType richiesta_assunzione_ristorante(String codice_autenticazione){
        // operazione data base per verificare se ci siano ristoranti con sto codice
        return ErrorType.NESSUN_ERRORE;
    }

    public void crea_ristorante(String nome,String indirizzo){
        Ristorante ristorante = new Ristorante(nome,indirizzo);
        utente_controller.dati_utente.set_dipendente(utente_controller.get_utente(),Ruolo.MANAGER,ristorante);
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazioni Dipedente

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
    public ErrorType segnala_ordine_pronto_ritiro(Ordine ordine){
        return dipedente.segnala_ordine_pronto_ritiro(ordine);
    }

    public void cancella_ordine(Ordine ordine){
        dipedente.cancella_ordine(ordine);
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public Dipedente get_dipedente(){return dipedente;}

    public void set_utente_controller(UtenteController utente_controller){
        this.utente_controller = utente_controller;
    }
}