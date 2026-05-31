package model;

import exception.*;

public class Prodotto {
    private String nome;
    private double prezzo_unitario;

    private Menu menu;

    public Prodotto(String nome, double prezzo_unitario) {
        this.nome = nome;
        set_prezzo_unitario(prezzo_unitario);
    }

    //____________________________________________________________________________________
    // Override

    public String toString(){
        String identificativo = get_nome() + " " + get_prezzo_unitario();
        return  identificativo;
    }

    //____________________________________________________________________________________
    //

    public void modica_prodotto(String nome,double prezzo_unitario){
        this.nome = nome;
        this.prezzo_unitario = prezzo_unitario;
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
        return ErrorType.INPUT_NON_VALIDO;
    }

}