package controller;

import ImplementazioniDAO.UtenteImpDAO;
import controller.Utils.SessionManager;
import model.*;

import java.util.Optional;

public class UtenteController {
    private Utente utente;
    private UtenteImpDAO utenteDB = new UtenteImpDAO();

    //________________________________________________________________________________________________________________________________________________
    // Operazione Utente

    /**
     * @author Tiziano
     * Login.
     *
     * @param email    the email
     * @param password the password
     */
    public void login(String email,String password){
       this.utente =  utenteDB.login(email, password);
       SessionManager.instance.set_utente(utente);
    }

    /**
     * @author Tiziano
     * Sign in.
     *
     * @param email    the email
     * @param password the password
     * @param nickname the nickname
     * @param nome     the nome
     * @param cognome  the cognome
     */
    public void sign_in(String email,String password,String nickname,String nome,String cognome){
        this.utente = new Utente(email,password,nickname,nome,cognome);
        SessionManager.instance.set_utente(utente);

        utenteDB.sign_in(email,password,nickname,nome,cognome);
    }

    /**
     * @author Tiziano
     * Cancella account.
     */
    public void cancella_account(){
        utenteDB.cancella_account(this.utente.get_nickname());
        this.utente = null;

        SessionManager.instance.distruggi_sessione();
    }

    /**
     * @author Tiziano
     * Logout.
     */
    public void logout(){
        SessionManager.instance.distruggi_sessione();
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione di Verifica del Ruolo dell'Utente

    /**
     * @author Franco
     * Utente is cliente boolean.
     *
     * @return the boolean
     */
    public boolean utente_is_cliente(){
        Optional<Cliente> optionalCliente = SessionManager.instance.get_utente().get_ruolo_utente(Cliente.class);
        if(optionalCliente.isPresent())
            return true;
        return false;
    }

    /**
     * @author Franco
     * Utente is rider boolean.
     *
     * @return the boolean
     */
    public boolean utente_is_rider(){
        Optional<Rider> optionalRider = SessionManager.instance.get_utente().get_ruolo_utente(Rider.class);
        if(optionalRider.isPresent())
            return true;
        return false;
    }

    /**
     * @author Franco
     * Utente is dipendente boolean.
     *
     * @return the boolean
     */
    public boolean utente_is_dipendente(){
        Optional<Dipendente> optionalDipendente = SessionManager.instance.get_utente().get_ruolo_utente(Dipendente.class);
        if(optionalDipendente.isPresent())
            return true;
        return false;
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Login/Sign Rider

    /**
     * @author Tiziano
     * Registra rider.
     *
     * @param mezzo_trasporto the mezzo trasporto
     */
    public void registra_rider(String mezzo_trasporto){
        this.utente.aggiungi_ruolo_utente(new Rider(utente,mezzo_trasporto));
        utenteDB.registra_rider(utente.get_nickname(),mezzo_trasporto);
    }

    /**
     * @author Tizinao
     * Load dati rider.
     */
    public void load_dati_rider(){
        Rider rider = utenteDB.get_rider(utente.get_nickname());
        if(rider != null){
            SessionManager.instance.get_utente().aggiungi_ruolo_utente(rider);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Login/Sign Cliente

    /**
     * @author Tiziano
     * Registra cliente.
     */
    public void registra_cliente(){
        utente.aggiungi_ruolo_utente(new Cliente(utente,Cliente.PUNTI_FEDELTA_REGISTRAZIONE));
        utenteDB.registra_cliente(utente.get_nickname(),Cliente.PUNTI_FEDELTA_REGISTRAZIONE);
    }

    /**
     * @author Tiziano
     * Load dati cliente.
     */
    public void load_dati_cliente(){
        Cliente cliente = utenteDB.get_cliente(utente.get_nickname());
        if(cliente != null){
            SessionManager.instance.get_utente().aggiungi_ruolo_utente(cliente);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Login/Sing Dipendente (Piattaforma/Ristorante)


    /**
     * @author Tiziano
     * Meotodo che rigeneri ogni volta che esiste già il codice il codice del Ristorantre
     *
     * @param ristorante
     */
    private void gestisci_collissioni_codice_ristorante(Ristorante ristorante){
        while(utenteDB.codice_ristorante_esiste(ristorante.get_codice_ristorante())){
            ristorante.set_codice_ristorante(Ristorante.genera_codice_univoco());
        }
    }

    /**
     * @author Tiziano
     * Crea ristorante.
     *
     * @param nome      the nome
     * @param indirizzo the indirizzo
     */
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

    /**
     * @author Tiziano
     * Registrazione dipedente ristorante.
     *
     * @param codice_ristorante the codice ristorante
     */
    public void registrazione_dipedente_ristorante(String codice_ristorante){
        utenteDB.registra_dipedente_ristorante(codice_ristorante,utente.get_nickname(),Dipendente.RUOLO_DIPEDENTE_RISTORANTE);
        utente.aggiungi_ruolo_utente(utenteDB.get_dipedente(utente.get_nickname()));
    }

    /**
     * @author Tiziano
     * Load dati dipedente.
     */
    public void load_dati_dipedente(){
        Dipendente dipendente = utenteDB.get_dipedente(utente.get_nickname());
        if(dipendente != null){
            SessionManager.instance.get_utente().aggiungi_ruolo_utente(dipendente);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public Utente get_utente() { return utente; }

}


