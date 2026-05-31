package controller;

import exception.*;
import model.*;

public class RistoranteContorller {
    private Ristorante ristorante;
    private Dipedente dipedente;


    //________________________________________________________________________________________________________________________________________________
    // Ristorante

    public ErrorType modifica_ristorante(String nome,String indirizzo){
        if(dipedente.get_ruolo().ordinal() < Ruolo.GESTIONALE.ordinal())
            return ErrorType.PERMESSI_NON_SUFFICIENTI;

        ristorante.modifica_ristorante(nome,indirizzo);
        return ErrorType.NESSUN_ERRORE;
    }

    //________________________________________________________________________________________________________________________________________________
    // Menu

    public ErrorType crea_menu(String nome){
        if(dipedente.get_ruolo().ordinal() < Ruolo.GESTIONALE.ordinal())
            return ErrorType.PERMESSI_NON_SUFFICIENTI;

         ristorante.crea_menu(nome);
         return ErrorType.NESSUN_ERRORE;
    }

    public ErrorType modifica_menu(Menu menu,String nome_modificato){
        if(dipedente.get_ruolo().ordinal() < Ruolo.GESTIONALE.ordinal())
            return ErrorType.PERMESSI_NON_SUFFICIENTI;

        menu.modifica_menu(nome_modificato);
        return ErrorType.NESSUN_ERRORE;
    }

    public ErrorType cancella_menu(Menu menu){
        if(dipedente.get_ruolo().ordinal() < Ruolo.GESTIONALE.ordinal())
            return ErrorType.PERMESSI_NON_SUFFICIENTI;


        ristorante.cancella_menu(menu);
        return ErrorType.NESSUN_ERRORE;
    }

    //________________________________________________________________________________________________________________________________________________
    // Prodotto

    public ErrorType crea_prodotto(Menu menu,String nome,double prezzo_unitario){
        if(dipedente.get_ruolo().ordinal() < Ruolo.GESTIONALE.ordinal())
            return ErrorType.PERMESSI_NON_SUFFICIENTI;

        menu.crea_prodotto(nome,prezzo_unitario);
        return ErrorType.NESSUN_ERRORE;
    }

    public ErrorType modifica_prodotto(Prodotto prodotto,String nome,double prezzo_unitario){
        if(dipedente.get_ruolo().ordinal() < Ruolo.GESTIONALE.ordinal())
            return ErrorType.PERMESSI_NON_SUFFICIENTI;

        prodotto.modica_prodotto(nome,prezzo_unitario);
        return ErrorType.NESSUN_ERRORE;
    }

    public ErrorType cancella_menu(Menu menu,Prodotto prodotto){
        if(dipedente.get_ruolo().ordinal() < Ruolo.GESTIONALE.ordinal())
            return ErrorType.PERMESSI_NON_SUFFICIENTI;

        menu.cancella_prodotto(prodotto);
        return ErrorType.NESSUN_ERRORE;
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public Ristorante get_ristorante(){ return ristorante;}

    public Dipedente get_dipednete(){ return dipedente;}
    public void set_dipendete(Dipedente dipedente){ this.dipedente = dipedente; }

}
