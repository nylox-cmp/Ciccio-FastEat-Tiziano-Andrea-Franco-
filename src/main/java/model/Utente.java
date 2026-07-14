package model;

import controller.SessionManager;

import java.util.ArrayList;
import java.util.Optional;

public class Utente {
    private String email;
    private String password;
    private String nickname;
    private String nome;
    private String cognome;

    private ArrayList<Utente> profili_utente = new ArrayList<>();

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public Utente(String email, String password, String nickname, String nome, String cognome) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.nome = nome;
        this.cognome = cognome;
    }

    //________________________________________________________________________________________________________________________________________________
    // Override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Utente utente = (Utente) o;
        return utente.get_nickname().equals(this.get_nickname());
    }

    @Override
    public String toString(){
        return this.nickname;
    }


    //________________________________________________________________________________________________________________________________________________
    // Gestione Profili Utente

    public <Tipo extends Utente> Optional<Tipo> get_ruolo_utente(Class<Tipo> tipo_ruolo_cercato){
        for(Utente ruolo : profili_utente){
            if(tipo_ruolo_cercato.isInstance(ruolo))
                return Optional.of(tipo_ruolo_cercato.cast(ruolo));
        }
        return Optional.empty();
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione di Aggiunta Ruolo Utente

    // Operazione per diventare Dipedente (piattaforma)
    public void crea_ristorante(String nome,String indirizzo){
       Ristorante ristorante = new Ristorante(nome,indirizzo);
       this.profili_utente.add(new Dipedente(this,Ruolo.MANAGER,ristorante));
    }

    public void sign_in_come_rider(String mezzo_trasporto){
        SessionManager.instance.get_utente().get_profili_utente().add(new Rider(SessionManager.instance.get_utente(), mezzo_trasporto));
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public String get_email(){ return email; }
    public void set_email(String email){ this.email = email; }

    public String get_password(){ return password; }
    public void set_password(String password){ this.password = password; }

    public String get_nickname(){ return this.nickname; }
    public void set_nickname(String nickname){ this.nickname = nickname; }

    public String get_nome(){ return nome; }
    public void set_nome(String nome){ this.nome = nome; }

    public String get_cognome(){ return cognome; }
    public void set_cognome(String cognome){ this.cognome = cognome; }

    public ArrayList<Utente> get_profili_utente(){return profili_utente;}
    public void set_profili_utente(ArrayList<Utente> profili_utente){this.profili_utente = profili_utente;}
}
