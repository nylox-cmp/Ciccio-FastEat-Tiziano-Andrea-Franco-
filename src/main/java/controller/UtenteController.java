package controller;

import ImplementazioniDAO.UtenteImpDAO;
import controller.Utils.SessionManager;
import model.*;

import java.util.Optional;

public class UtenteController {
    private Utente utente;
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
    // Operazione Login/Sign Rider

    public void registra_rider(String mezzo_trasporto){
        this.utente.registra_rider(mezzo_trasporto);
        utenteDB.registra_rider(utente.get_nickname(),mezzo_trasporto);
    }

    public void load_dati_rider(){
        Rider rider = utenteDB.get_rider(utente.get_nickname());
        if(rider != null){
            SessionManager.instance.get_utente().aggiungi_ruolo_utente(rider);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Login/Sign Cliente

    public void registra_cliente(){
        utente.registra_cliente();
        utenteDB.registra_cliente(utente.get_nickname(),Cliente.PUNTI_FEDELTA_REGISTRAZIONE);
    }

    public void load_dati_cliente(){
        Cliente cliente = utenteDB.get_cliente(utente.get_nickname());
        if(cliente != null){
            SessionManager.instance.get_utente().aggiungi_ruolo_utente(cliente);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Login/Sing Dipendente (Piattaforma/Ristorante)

    private void gestisci_collissioni_codice_ristorante(Ristorante ristorante){
        while(utenteDB.codice_ristorante_esiste(ristorante.get_codice_ristorante())){
            ristorante.set_codice_ristorante(Ristorante.genera_codice_univoco());
        }
    }

    public void crea_ristorante(String nome,String indirizzo){
        utente.crea_ristorante(nome,indirizzo);

       Optional<Dipendente> optionalDipendente = SessionManager.instance.get_utente().get_ruolo_utente(Dipendente.class);
       if(optionalDipendente.isPresent()){
           Dipendente dipendente = optionalDipendente.get();

           Ristorante ristorante = dipendente.get_ristorante();
           gestisci_collissioni_codice_ristorante(ristorante);

           utenteDB.crea_ristorante(nome,indirizzo,ristorante.get_codice_ristorante());
           utenteDB.registra_dipedente_creatore_ristorante(dipendente.get_nickname(),dipendente.get_ruolo(),ristorante.get_codice_ristorante());
       }
    }

    public void registrazione_dipedente_ristorante(String codice_ristorante){
        utenteDB.registra_dipedente_ristorante(codice_ristorante,utente.get_nickname(),Dipendente.RUOLO_DIPEDENTE_RISTORANTE);
        utente.aggiungi_ruolo_utente(utenteDB.get_dipedente(utente.get_nickname()));
    }

    public void load_dati_dipedente(){
        Dipendente dipendente = utenteDB.get_dipedente(utente.get_nickname());
        if(dipendente != null){
            SessionManager.instance.get_utente().aggiungi_ruolo_utente(dipendente);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public Utente get_utente() { return utente; }
    public void set_utente(Utente utente){this.utente = utente;}
}


