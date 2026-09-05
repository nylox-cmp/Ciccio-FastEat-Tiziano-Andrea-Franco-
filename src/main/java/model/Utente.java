package model;



import java.util.ArrayList;
import java.util.Optional;

public class Utente {
    private String email;
    private String password;
    private String nickname;
    private String nome;
    private String cognome;

    private ArrayList<Utente> ruoli_utente = new ArrayList<>();

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    /**
     * @author Tiziano
     *
     * @param email     email
     * @param password  password
     * @param nickname  nickname
     * @param nome      nome
     * @param cognome   cognome
     */
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
    // Gestione dei Ruoli dell'Utente

    /**
     * @author Tiziano
     * Metodo che aggiunge un ruolo alla Lista dei RUOLI_UTENTE
     *
     * @param ruolo the ruolo
     */
    public void aggiungi_ruolo_utente(Utente ruolo){
        get_profili_utente().add(ruolo);
    }

    /**
     * @author Tiziano
     * Cerca all'interno della lista dei ruoli dell'utente la prima istanza che corrisponde
     * al tipo di ruolo specificato.
     *
     * @param <Tipo>               Il tipo generico del ruolo da cercare; deve estendere la classe {@link Utente}.
     * @param tipo_ruolo_cercato   La classe ({@link Class}) corrispondente al tipo di ruolo da filtrare e restituire.
     * @return                     returna una sotto classe di {@link Utente} o nel caso il tipo cercano non sia trovato returna Optional.empty()
     */
    public <Tipo extends Utente> Optional<Tipo> get_ruolo_utente(Class<Tipo> tipo_ruolo_cercato){
        for(Utente ruolo : ruoli_utente){
            if(tipo_ruolo_cercato.isInstance(ruolo))
                return Optional.of(tipo_ruolo_cercato.cast(ruolo));
        }
        return Optional.empty();
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione di Aggiunta Ruolo Utente

    /**
     * @author Tiziano
     * Metodo che crea un ristorante posseduto dal ruolo utente Dipendente , che vieni aggiunto alla lista di ruoli dell'utente
     *
     * @param nome       nome
     * @param indirizzo  indirizzo
     */
    public void crea_ristorante(String nome,String indirizzo){
       Ristorante ristorante = new Ristorante(nome,indirizzo);
       Dipendente dipendente = new Dipendente(this,Dipendente.RUOLO_DIPEDENTE_CREATORE_RISTORANTE,ristorante);
       aggiungi_ruolo_utente(dipendente);
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

    public ArrayList<Utente> get_profili_utente(){return ruoli_utente;}
    public void set_profili_utente(ArrayList<Utente> profili_utente){this.ruoli_utente = profili_utente;}
}
