package controller;

import model.Utente;

public class SessionManager {
    public static SessionManager instance = new SessionManager();
    private Utente utente;

    //________________________________________________________________________________________________________________________________________________
    // Operazione SessionManager

    public void distruggi_sessione(){
        this.utente = null;
    }

    //________________________________________________________________________________________________________________________________________________
    // Get And Set

    public Utente get_utente(){return utente;}
    public void set_utente(Utente utente){this.utente = utente;}
}