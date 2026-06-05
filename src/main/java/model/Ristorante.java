package model;

import exception.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.UUID;

public class Ristorante {
    private String nome;
    private String indirizzo;
    private String codice_autenticazione;
    private ArrayList<Dipedente> dipedenti;
    private ArrayList<Menu> menu;
    private ArrayList<Ordine> ordini;

    private ArrayList<Dipedente> dipendenti;
    private ArrayList<Utente> richieste_assunzione;

    public Ristorante(String nome, String indirizzo) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.codice_autenticazione = genera_codice_univoco();
    }

    public static String genera_codice_univoco(){
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }

    //________________________________________________________________________________________________________________________________________________
    // Override

    @Override
    public String toString(){
        String identificativo = get_nome() + " " + get_indirizzo();
        return identificativo;
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Menu

    public void crea_menu(String nome){
        Menu menu = new Menu(nome);
        this.menu.add(menu);
    }

    public void cancella_menu(Menu menu_eliminiare){
        menu.remove(menu_eliminiare);
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Ristorante

    public void modifica_ristorante(String nome,String indirizzo){
        this.nome = nome;
        this.indirizzo = indirizzo;
    }

    //________________________________________________________________________________________________________________________________________________
    //Get and Set

    public String get_nome(){ return nome; }
    public void set_nome(String nome){ this.nome = nome; }

    public String get_indirizzo(){ return indirizzo; }
    public void set_inidirizzo(String indirizzo){ this.indirizzo = indirizzo; }

    public ArrayList<Menu> get_menu(){ return menu; }
    public void set_menu(ArrayList<Menu> menu){ this.menu = menu; }

    public ArrayList<Ordine> get_ordini() { return ordini; }
    public void set_ordini(ArrayList<Ordine> ordini) { this.ordini = ordini; }

    public ArrayList<Utente> get_richieste_assunzioni(){ return richieste_assunzione; }
    public void set_richieste_assunzioni(ArrayList<Utente> richieste_assunzione){ this.richieste_assunzione = richieste_assunzione; }

    public String get_codice_autenticazione(){
        return codice_autenticazione;
    }
    public void set_codice_autenticazione(String codice_autenticazione){this.codice_autenticazione = codice_autenticazione;}

}