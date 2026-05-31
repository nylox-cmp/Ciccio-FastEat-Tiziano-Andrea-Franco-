package controller;

import exception.*;
import model.*;

import java.util.ArrayList;


public class UtenteController {
    private Utente utente;
    public static DatiUtente dati_utente;

    public UtenteController(){
        this.dati_utente = new DatiUtente();
    }

    //________________________________________________________________________________________________________________________________________________
    //

    public ErrorType login(String email,String password){
        // ricupera tutti i dati del utente come (cliente,rider,dipente), all'interno del database
        return ErrorType.NESSUN_ERRORE;
    }

    public ErrorType sing_in(String email,String password,String nickname,String nome,String cognome){
        this.utente = new Utente(email,password,nickname,nome,cognome);
        return ErrorType.NESSUN_ERRORE;
    }

    public void logout(){
        this.utente = null;
        this.dati_utente.clear();
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public Utente get_utente() { return utente; }
}


