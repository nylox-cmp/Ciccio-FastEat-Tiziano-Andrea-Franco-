package dao;

import model.*;

public interface UtenteDAO {


    public void sign_in(String email,String password,String nickname,String nome,String cognome);
    public Utente login(String email, String password);
    public void cancella_account(String nickname);

    public void registrazione_rider(String nickname,String mezzo_trasporto);
    public Rider get_rider(String nickname);

    public void crea_ristorante(String nome,String indirizzo,String codice_ristorante,String nickname,Ruolo ruolo);
    public void registrazione_dipedente_ristorante(String codice_ristorante);
    public Dipendente get_dipedente(String nickname);

}
