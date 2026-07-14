package model;

import exception.ErrorType;

import java.util.ArrayList;
import java.util.UUID;

public class Ristorante {
    private String codice_ristorante;
    private String nome;
    private String indirizzo;

    private ArrayList<Dipedente> dipendenti;
    private ArrayList<Menu> menu = new ArrayList<Menu>();
    private ArrayList<Ordine> ordini = new ArrayList<Ordine>();

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public Ristorante(String nome, String indirizzo) {
        this.codice_ristorante = genera_codice_univoco();
        this.nome = nome;
        this.indirizzo = indirizzo;
    }

    public Ristorante(String codice_ristorante,String nome, String indirizzo,ArrayList<Dipedente> dipendenti,ArrayList<Menu> menu){
        this.codice_ristorante = codice_ristorante;
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.dipendenti = dipendenti;
        this.menu = menu;
    }

    //________________________________________________________________________________________________________________________________________________
    // Override

    @Override
    public String toString(){
        String string = get_nome() + " " + get_indirizzo();
        return string;
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(o == null || o.getClass() != this.getClass()) return false;

        Ristorante ristorante = (Ristorante) o;
        return (ristorante.get_codice_ristorante().equals(this.get_codice_ristorante()));
    }

    //________________________________________________________________________________________________________________________________________________
    // Generazione Codice

    protected static String genera_codice_univoco(){
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Menu

    protected boolean esiste_menu_stesso_nome(String nome){
        for(Menu menu : get_menu()){
            if(menu.get_nome().equals(nome)) return true;
        }
        return false;
    }

    public ErrorType crea_menu(String nome){
        if(esiste_menu_stesso_nome(nome)) return ErrorType.INPUT_NON_UNIVOCO;

        Menu menu = new Menu(nome,this);
        this.menu.add(menu);
        return ErrorType.NESSUN_ERRORE;
    }

    public void cancella_menu(Menu menu){
        this.menu.remove(menu);
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Ristorante

    public void modifica_ristorante(String nome,String indirizzo){
        this.nome = nome;
        this.indirizzo = indirizzo;
    }

    //________________________________________________________________________________________________________________________________________________
    //Get and Set

    public String get_codice_ristorante(){return codice_ristorante;}
    public void set_codice_ristorante(String codice_ristorante){this.codice_ristorante = codice_ristorante;}

    public String get_nome(){ return nome; }
    public void set_nome(String nome){ this.nome = nome; }

    public String get_indirizzo(){ return indirizzo; }
    public void set_indirizzo(String indirizzo){ this.indirizzo = indirizzo; }

    public ArrayList<Menu> get_menu(){ return menu; }
    public void set_menu(ArrayList<Menu> menu){ this.menu = menu; }

    public ArrayList<Ordine> get_ordini() { return ordini; }
    public void set_ordini(ArrayList<Ordine> ordini) { this.ordini = ordini; }

}