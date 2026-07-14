package controller;

import exception.ErrorType;
import model.Dipedente;
import model.Rider;
import model.Utente;

import java.util.Optional;

public class UtenteController {
    public Utente utente;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public UtenteController(){}

    //________________________________________________________________________________________________________________________________________________
    // Operazione Utente

    public ErrorType login(String email,String password){
        //operazione di verifica e recupero dati per creare la classe utente
        return ErrorType.NESSUN_ERRORE;
    }

    public ErrorType sign_in(String email,String password,String nickname,String nome,String cognome){
        this.utente = new Utente(email,password,nickname,nome,cognome);
        SessionManager.instance.set_utente(utente);
        //operazione di registrazione
        return ErrorType.NESSUN_ERRORE;
    }

    public ErrorType cancella_account(){
        //Operazione Database cancellazione legata a due trigger Cliente/Rider
        SessionManager.instance.distruggi_sessione();
        return ErrorType.NESSUN_ERRORE;
    }

    public void logout(){
        SessionManager.instance.distruggi_sessione();
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione di Verifica del Ruolo dell'Utente

    public boolean utente_is_cliente(){
        return false;
    }

    public boolean utente_is_dipedente(){
        Optional<Dipedente> optionalDipedente = SessionManager.instance.get_utente().get_ruolo_utente(Dipedente.class);
        if(optionalDipedente.isPresent())
            return true;

        //Operazione di verifica nel database se l'utente sia un Dipedente

        return false;
    }

    public boolean utente_is_rider(){
        Optional<Rider> optionalRider = SessionManager.instance.get_utente().get_ruolo_utente(Rider.class);
        if(optionalRider.isPresent())
            return true;

        //Operazione di verifica nel database se l'utente sia un Dipedente

        return false;
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Login/Sing Rider

    public void sign_in_come_rider(String mezzo_trasporto){
        this.utente.sign_in_come_rider(mezzo_trasporto);
    }

    public void login_come_rider(){
        //operazione di login del Rider
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Login/Sing Dipedente (Piattaforma/Ristorante)

    public void crea_ristorante(String nome,String indirizzo){
        SessionManager.instance.get_utente().crea_ristorante(nome,indirizzo);
        //operazione
    }

    public ErrorType diventa_dipendente_ristorante(String codice_ristorante){
        //operazione di controllo se esiste un ristorante con quel codice e di rendere l'utente un dipedente
        return ErrorType.NESSUN_ERRORE;
    }

    public void login_come_dipedente(){
        //if NOT RiderDAO.utente_is_dipedente

    }

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public Utente get_utente() { return utente; }
    public void set_utente(Utente utente){this.utente = utente;}
}


