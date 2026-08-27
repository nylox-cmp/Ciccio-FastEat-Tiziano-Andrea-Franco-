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

    public Menu(String nome,Ristorante ristorante) {
        this.nome = nome;
        this.ristorante = ristorante;
    }

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

    protected boolean esiste_prodotto_stesso_nome(String nome){
        for(Prodotto prodotto : get_prodotti()){
            if(prodotto.get_nome().equals(nome)) return true;
        }
        return false;
    }

    public void crea_prodotto(String nome, double prezzo_unitario) {
        if(prezzo_unitario < 0) throw  new BusinessError(ErrorType.INPUT_NUMERICO_NEGATIVO);
        if(esiste_prodotto_stesso_nome(nome)) throw new BusinessError(ErrorType.INPUT_NON_UNIVOCO);

        Prodotto prodotto = new Prodotto(nome,prezzo_unitario,this);
        prodotti.add(prodotto);
    }

    public void cancella_prodotto(Prodotto prodotto){
        prodotti.remove(prodotto);
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Menu

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

