package model;

import exception.BusinessError;
import exception.ErrorType;

public class Prodotto {
    private String nome;
    private double prezzo_unitario;
    private Menu menu;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    /**
     * @author Franco
     *
     * @param nome            the nome
     * @param prezzo_unitario the prezzo unitario
     * @param menu            the menu
     */
    public Prodotto(String nome, double prezzo_unitario,Menu menu) {
        this.nome = nome;
        set_prezzo_unitario(prezzo_unitario);
        this.menu = menu;
    }

    /**
     * @author Franco
     *
     * @param nome            the nome
     * @param prezzo_unitario the prezzo unitario
     */
    public Prodotto(String nome, double prezzo_unitario) {
        this.nome = nome;
        set_prezzo_unitario(prezzo_unitario);
    }

    //________________________________________________________________________________________________________________________________________________
    // Override

    @Override
    public String toString(){
        String string = get_nome() + " " + get_prezzo_unitario();
        return  string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prodotto)) return false;

        Prodotto prodotto = (Prodotto) o;
        if (this.menu == null || prodotto.menu == null) return prodotto.get_nome().equals(this.get_nome());
        return prodotto.get_nome().equals(this.get_nome()) && prodotto.get_menu().equals(this.get_menu());
    }

    //________________________________________________________________________________________________________________________________________________
    // Prodotto

    /**
     * @author Franco
     * Modifica prodotto.
     *
     * @param nome            the nome
     * @param prezzo_unitario the prezzo unitario
     */
    public void modifica_prodotto(String nome,double prezzo_unitario){
        for(Prodotto prodotto : menu.get_prodotti()){
            if(prodotto.equals(this) == false && prodotto.get_nome().equals(nome)) throw new BusinessError(ErrorType.INPUT_NON_UNIVOCO);
        }

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

    /**
     * @author Franco
     * Set prezzo unitario.
     *
     * @param prezzo_unitario the prezzo unitario
     */
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