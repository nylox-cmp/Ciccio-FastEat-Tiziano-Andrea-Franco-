package model;

import exception.ErrorType;

public class Prodotto {
    private String nome;
    private double prezzo_unitario;
    private Menu menu;

    //____________________________________________________________________________________
    //Costruttore

    public Prodotto(String nome, double prezzo_unitario,Menu menu) {
        this.nome = nome;
        set_prezzo_unitario(prezzo_unitario);
        this.menu = menu;
    }

    //____________________________________________________________________________________
    // Override

    @Override
    public String toString(){
        String string = get_nome() + " " + get_prezzo_unitario();
        return  string;
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(o == null || o.getClass() != this.getClass()) return false;

        Prodotto prodotto = (Prodotto) o;
        return (prodotto.get_menu().equals(menu) && prodotto.get_nome().equals(this.get_nome()));
    }


    //____________________________________________________________________________________
    //

    public ErrorType modifica_prodotto(String nome,double prezzo_unitario){
        this.nome = nome;

        if(menu.esiste_prodotto_stesso_nome(nome)) return ErrorType.INPUT_NON_UNIVOCO;
        return set_prezzo_unitario(prezzo_unitario);
    }

    //____________________________________________________________________________________
    //Get and Set

    public String get_nome(){
        return nome;
    }
    public void set_nome(String nome){
        this.nome = nome;
    }

    public double get_prezzo_unitario(){
        return prezzo_unitario;
    }
    public ErrorType set_prezzo_unitario(double prezzo_unitario){
        if(prezzo_unitario > 0) {
            this.prezzo_unitario = prezzo_unitario;
            return ErrorType.NESSUN_ERRORE;
        }
        return ErrorType.INPUT_NULL;
    }


    public Menu get_menu(){return menu;}
    public void set_menu(Menu menu){this.menu = menu;}
}