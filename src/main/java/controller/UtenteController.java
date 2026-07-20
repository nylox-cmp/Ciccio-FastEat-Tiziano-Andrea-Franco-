package controller;

import ImplementazioniDAO.UtenteImpDAO;
import dao.UtenteDAO;
import exception.BusinessError;
import exception.ErrorType;
import model.*;

import java.util.Optional;

public class UtenteController {
    public Utente utente;
    private UtenteImpDAO utenteDB = new UtenteImpDAO();

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public UtenteController(){}

    //________________________________________________________________________________________________________________________________________________
    // Operazione Utente

    public void login(String email,String password){
       this.utente =  utenteDB.login(email, password);
       SessionManager.instance.set_utente(utente);

    }

    public void sign_in(String email,String password,String nickname,String nome,String cognome){
        this.utente = new Utente(email,password,nickname,nome,cognome);
        SessionManager.instance.set_utente(utente);

        utenteDB.sign_in(email,password,nickname,nome,cognome);
    }

    public void cancella_account(){
        utenteDB.cancella_account(this.utente.get_nickname());
        this.utente = null;

        SessionManager.instance.distruggi_sessione();
    }

    public void logout(){
        SessionManager.instance.distruggi_sessione();
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione di Verifica del Ruolo dell'Utente

    public boolean utente_is_cliente(){
        Optional<Cliente> optionalCliente = SessionManager.instance.get_utente().get_ruolo_utente(Cliente.class);
        if(optionalCliente.isPresent())
            return true;
        return false;
    }

    public boolean utente_is_rider(){
        Optional<Rider> optionalRider = SessionManager.instance.get_utente().get_ruolo_utente(Rider.class);
        if(optionalRider.isPresent())
            return true;
        return false;
    }

    public boolean utente_is_dipendente(){
        Optional<Dipendente> optionalDipendente = SessionManager.instance.get_utente().get_ruolo_utente(Dipendente.class);
        if(optionalDipendente.isPresent())
            return true;
        return false;
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Login/Sing Rider

    public void registrazione_rider(String mezzo_trasporto){
        this.utente.sign_in_come_rider(mezzo_trasporto);
    }

    public void load_dati_rider(){
        //operazione di login del Rider
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Login/Creazione Cliente

    public void registrazione_cliente(){
        SessionManager.instance.get_utente().get_profili_utente().add(new Cliente(utente));
    }

    public void load_dati_cliente(){

    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Login/Sing Dipendente (Piattaforma/Ristorante)

    public void crea_ristorante(String nome,String indirizzo){
        SessionManager.instance.get_utente().crea_ristorante(nome,indirizzo);

       Optional<Dipendente> optionalDipendente = SessionManager.instance.get_utente().get_ruolo_utente(Dipendente.class);
       if(optionalDipendente.isPresent()){
           Dipendente dipendente = optionalDipendente.get();
           utenteDB.crea_ristorante(nome,indirizzo,dipendente.get_ristorante().get_codice_ristorante(),dipendente.get_nickname(),dipendente.get_ruolo());
       }
    }

    public void registrazione_dipedente_ristorante(String codice_ristorante){
        //operazione di controllo se esiste un ristorante con quel codice e rendere l'utente un dipendente subito
    }

    public void load_dati_dipedente(){

    }

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public Utente get_utente() { return utente; }
    public void set_utente(Utente utente){this.utente = utente;}
}


