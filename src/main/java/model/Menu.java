package model;

import exception.BusinessError;
import exception.ErrorType;

import java.util.ArrayList;


public class Menu {
    private String nome;
    private ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
    private Ristorante ristorante;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    /**
     * @author Andrea
     * Costruttore utilizzato per la creazione del menu
     *
     * @param nome       the nome
     * @param ristorante the ristorante
     */
    public Menu(String nome,Ristorante ristorante) {
        this.nome = nome;
        this.ristorante = ristorante;
    }

    /**
     * @author Andrea
     * Costruttore utilizzato per caricare la classe dal database
     *
     * @param nome the nome
     */
    public Menu(String nome){
        this.nome = nome;
    }

    //________________________________________________________________________________________________________________________________________________
    // Override

    public String toString(){ return nome;}

    public boolean equals(Object o){
        if(o == this) return true;
        if(o == null || o.getClass() != this.getClass()) return false;

        Menu menu = (Menu) o;
        if(menu.get_ristorante() == null || this.get_ristorante() == null) return (menu.get_nome().equals(this.nome));
        return (menu.get_ristorante().equals(this.get_ristorante()) && menu.get_nome().equals(this.get_nome()));
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione prodotti

    /**
     * @author Andrea
     * Esiste prodotto stesso nome boolean.
     *
     * @param nome the nome
     * @return the boolean
     */
    protected boolean esiste_prodotto_stesso_nome(String nome){
        for(Prodotto prodotto : get_prodotti()){
            if(prodotto.get_nome().equals(nome)) return true;
        }
        return false;
    }

    /**
     * @author Andrea
     * Crea prodotto con un nome univoco all'interno dello stesso menu e con un prezzo > 0
     *
     * @param nome            the nome
     * @param prezzo_unitario the prezzo unitario
     * @throws BusinessError
     */
    public void crea_prodotto(String nome, double prezzo_unitario) {
        if(prezzo_unitario < 0) throw  new BusinessError(ErrorType.INPUT_NUMERICO_NEGATIVO);
        if(esiste_prodotto_stesso_nome(nome)) throw new BusinessError(ErrorType.INPUT_NON_UNIVOCO);

        Prodotto prodotto = new Prodotto(nome,prezzo_unitario,this);
        prodotti.add(prodotto);
    }

    /**
     * @author Andrea
     * Cancella prodotto.
     *
     * @param prodotto the prodotto
     */
    public void cancella_prodotto(Prodotto prodotto){
        prodotti.remove(prodotto);
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Menu

    /**
     * @author Andrea
     * Modifica menu.
     *
     * @param nome the nome
     */
    public void modifica_menu(String nome){
        if(ristorante.esiste_menu_stesso_nome(nome)) throw  new BusinessError(ErrorType.INPUT_NON_UNIVOCO);

        this.nome = nome;
    }

    //________________________________________________________________________________________________________________________________________________
    //Get and Set


    public String get_nome(){
        return nome;
    }
    public void set_nome(String nome) { this.nome = nome; }


    public Ristorante get_ristorante(){return ristorante;}
    public void set_ristorante(Ristorante ristorante){this.ristorante = ristorante;}

    public ArrayList<Prodotto> get_prodotti() { return prodotti; }
    public void set_prodotti(ArrayList<Prodotto> prodotti) { this.prodotti = prodotti; }
}

