package model;

import exception.BusinessError;
import exception.ErrorType;

import java.util.ArrayList;
import java.util.UUID;


public class Ristorante {
    private String codice_ristorante;
    private String nome;
    private String indirizzo;

    private ArrayList<Dipendente> dipendenti;
    private ArrayList<Menu> menu = new ArrayList<Menu>();
    private ArrayList<Ordine> ordini = new ArrayList<Ordine>();

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    /**
     * @author Andrea
     * Costruttore creazione ristorante
     *
     * @param nome       nome
     * @param indirizzo  indirizzo
     */
    public Ristorante(String nome, String indirizzo) {
        this.codice_ristorante = genera_codice_univoco();
        this.nome = nome;
        this.indirizzo = indirizzo;
    }

    /**
     * @authro Andrea
     * Costruttore utilizzato per il caricamento dei dati dal database
     *
     * @param codice_ristorante  codice ristorante
     * @param nome               nome
     * @param indirizzo          indirizzo
     */
    public Ristorante(String codice_ristorante, String nome, String indirizzo){
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

    /**
     * @author Andrea
     *
     * @return the string
     */
    public static String genera_codice_univoco(){
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Menu

    /**
     * @author Andrea
     * Esiste menu stesso nome boolean.
     *
     * @param nome  nome
     * @return the boolean
     */
    protected boolean esiste_menu_stesso_nome(String nome){
        for(Menu menu : get_menu()){
            if(menu.get_nome().equals(nome)) return true;
        }
        return false;
    }

    /**
     * @author Andrea
     * Crea menu un con un nome univoco all'interno del ristorante
     *
     * @param nome  nome
     * @throws BusinessError
     */
    public void crea_menu(String nome){
        if(esiste_menu_stesso_nome(nome)) throw new BusinessError(ErrorType.INPUT_NON_UNIVOCO);

        Menu menu = new Menu(nome,this);
        this.menu.add(menu);
    }

    /**
     * @author Andrea
     * Cancella menu.
     *
     * @param menu  menu
     */
    public void cancella_menu(Menu menu){
        this.menu.remove(menu);
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Ristorante

    /**
     * @author Andrea
     * Modifica ristorante.
     *
     * @param nome       nome
     * @param indirizzo  indirizzo
     */
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