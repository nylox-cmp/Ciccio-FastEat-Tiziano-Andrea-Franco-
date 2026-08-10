package model;

import exception.BusinessError;
import exception.ErrorType;

public class Prodotto {
    private String nome;
    private double prezzo_unitario;
    private Menu menu;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public Prodotto(String nome, double prezzo_unitario,Menu menu) {
        this.nome = nome;
        set_prezzo_unitario(prezzo_unitario);
        this.menu = menu;
    }

    public Prodotto(String nome, double prezzo_unitario) {
        this.nome = nome;
        set_prezzo_unitario(prezzo_unitario);
        this.menu = menu;
    }

    //________________________________________________________________________________________________________________________________________________
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

    //________________________________________________________________________________________________________________________________________________
    // Prodotto

    public void modifica_prodotto(String nome,double prezzo_unitario){
        if(menu.esiste_prodotto_stesso_nome(nome))
            throw new BusinessError(ErrorType.INPUT_NON_UNIVOCO);

        this.nome = nome;
        set_prezzo_unitario(prezzo_unitario);
    }

    //____________________________________________________________________________________
    // Metodi Get and Set

    public String get_nome(){
        return nome;
    }
    public void set_nome(String nome){
        this.nome = nome;
    }

    public double get_prezzo_unitario(){
        return prezzo_unitario;
    }
    public void set_prezzo_unitario(double prezzo_unitario){
        if(prezzo_unitario > 0) {
            this.prezzo_unitario = prezzo_unitario;
            return;
        }
        throw new BusinessError(ErrorType.INPUT_NUMERICO_NEGATIVO);
    }


    public Menu get_menu(){return menu;}
    public void set_menu(Menu menu){this.menu = menu;}
}