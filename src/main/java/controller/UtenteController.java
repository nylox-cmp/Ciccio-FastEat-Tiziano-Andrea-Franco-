package controller;

import exception.*;
import model.*;

import java.util.ArrayList;


public class UtenteController {
    private Utente utente;
    private DatiUtente dati_utente;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public UtenteController(){
        this.dati_utente = new DatiUtente();
    }

    //________________________________________________________________________________________________________________________________________________
    //

    public ErrorType login(String email,String password){
        // ricupera tutti i dati del utente come (cliente,rider,dipente), all'interno del database
        return ErrorType.NESSUN_ERRORE;
    }

    public ErrorType sign_in(String email,String password,String nickname,String nome,String cognome){
        this.utente = new Utente(email,password,nickname,nome,cognome);
        return ErrorType.NESSUN_ERRORE;
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazioni per diventare Dipdente

    public void crea_ristorante(String nome,String indirizzo,String numero_telefono){
        Ristorante ristorante = new Ristorante(nome,indirizzo,numero_telefono);
        dati_utente.set_dipendente(get_utente(),Ruolo.MANAGER,ristorante);
    }

    public ErrorType richiesta_assunzione_ristorante(String codice_ristorante){
        // operazione data base per verificare se ci siano ristoranti con sto codice
        return ErrorType.NESSUN_ERRORE;
    }

    public void sing_in_rider(String mezzo_trasporto){
        dati_utente.set_rider(get_utente(),mezzo_trasporto);
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazioni di verifica se l'utente sia Dipedente o Rider

    public boolean utente_is_dipedente(){ return (dati_utente.get_dipedente() != null); }

    public boolean utente_is_rider(){ return (dati_utente.get_rider() != null);}


    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public Utente get_utente() { return utente; }
    public void set_utente(Utente utente){this.utente = utente;}

    public DatiUtente get_dati_utente(){return dati_utente;}
    public void set_dati_utente(DatiUtente dati_utente){this.dati_utente = dati_utente;}
}


