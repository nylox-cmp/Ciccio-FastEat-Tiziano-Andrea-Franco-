package dao;

import model.*;

public interface UtenteDAO {


    public void sign_in(String email,String password,String nickname,String nome,String cognome);
    public Utente login(String email, String password);
    public void cancella_account(String nickname);

    public void registra_rider(String nickname,String mezzo_trasporto);

    public void registra_cliente(String nickname,int punti_fedelta);

    public void crea_ristorante(String nome,String indirizzo,String codice_ristorante);
    public void registra_dipedente_creatore_ristorante(String nickname,Ruolo ruolo,String codice_ristorante);
    public boolean codice_ristorante_esiste(String codice_ristorante);
    public void registra_dipedente_ristorante(String codice_ristorante,String nickname,Ruolo ruolo);

    public Utente get_utente(String nickname);
    public Rider get_rider(String nickname);
    public Cliente get_cliente(String nickname);
    public Dipendente get_dipedente(String nickname);
}
