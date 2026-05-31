package model;

import exception.*;
import java.util.ArrayList;

public class Menu {
    private String nome;
    private ArrayList<Prodotto> prodotti;
    private Ristorante ristorante;

    public Menu(String nome) {
        this.nome = nome;
        this.prodotti = new ArrayList<Prodotto>();
    }

    //____________________________________________________________________________________
    // Overidde

    public String toString(){ return nome;}


    //____________________________________________________________________________________
    //Gestione prodotti

    public void crea_prodotto(String nome, double prezzo_unitario) {
        Prodotto prodotto = new Prodotto(nome,prezzo_unitario);
        prodotti.add(prodotto);
    }

    public void cancella_prodotto(Prodotto prodoto){
        prodotti.remove(prodoto);
    }

    //____________________________________________________________________________________
    //Gestione Menu

    public void modifica_menu(String nome){
        this.nome = nome;
    }

    //____________________________________________________________________________________
    //Get and Set

    public String get_nome(){
        return nome;
    }
    public void set_nome(String nome) { this.nome = nome; }

    public ArrayList<Prodotto> get_prodotti() { return prodotti; }
    public void set_prodotti(ArrayList<Prodotto> prodotti) { this.prodotti = prodotti; }
}

