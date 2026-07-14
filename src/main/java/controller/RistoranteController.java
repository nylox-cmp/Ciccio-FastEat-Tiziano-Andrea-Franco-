package controller;

import exception.ErrorType;
import model.*;

public class RistoranteController {
    private Ristorante ristorante;
    private Menu menu;
    private Prodotto prodotto;

    private Dipedente dipedente;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public RistoranteController(DipedenteController dipedenteController){
        this.ristorante = dipedenteController.get_dipendente().get_ristorante();
        this.dipedente = dipedenteController.get_dipendente();
    }

    //________________________________________________________________________________________________________________________________________________
    // Ristorante

        public ErrorType cancella_ristorante(){
        ErrorType error = dipedente.cancella_ristorante(dipedente.get_ristorante());
        if(error != ErrorType.NESSUN_ERRORE) return error;

        this.dipedente = null;
        return ErrorType.NESSUN_ERRORE;
    }

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

        return ristorante.crea_menu(nome);
    }

    public ErrorType modifica_menu(String nome_modificato){
        if(dipedente.get_ruolo().ordinal() < Ruolo.GESTIONALE.ordinal())
            return ErrorType.PERMESSI_NON_SUFFICIENTI;

        return menu.modifica_menu(nome_modificato);
    }

    public ErrorType cancella_menu(Menu menu){
        if(dipedente.get_ruolo().ordinal() < Ruolo.GESTIONALE.ordinal())
            return ErrorType.PERMESSI_NON_SUFFICIENTI;

        ristorante.cancella_menu(menu);
        return ErrorType.NESSUN_ERRORE;
    }

    //________________________________________________________________________________________________________________________________________________
    // Prodotto

    public ErrorType crea_prodotto(String nome,double prezzo_unitario){
        if(dipedente.get_ruolo().ordinal() < Ruolo.GESTIONALE.ordinal())
            return ErrorType.PERMESSI_NON_SUFFICIENTI;

        return menu.crea_prodotto(nome,prezzo_unitario);
    }

    public ErrorType modifica_prodotto(String nome,double prezzo_unitario){
        if(dipedente.get_ruolo().ordinal() < Ruolo.GESTIONALE.ordinal())
            return ErrorType.PERMESSI_NON_SUFFICIENTI;

        return prodotto.modifica_prodotto(nome,prezzo_unitario);
    }

    public ErrorType cancella_prodotto(Prodotto prodotto){
        if(dipedente.get_ruolo().ordinal() < Ruolo.GESTIONALE.ordinal())
            return ErrorType.PERMESSI_NON_SUFFICIENTI;

        menu.cancella_prodotto(prodotto);
        return ErrorType.NESSUN_ERRORE;
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public Dipedente get_dipendente(){ return dipedente;}
    public void set_dipendente(Dipedente dipedente){ this.dipedente = dipedente; }

    public Ristorante get_ristorante(){ return ristorante;}
    public void set_ristorante(Ristorante ristorante){ this.ristorante = ristorante;}

    public Menu get_menu(){ return menu;}
    public void set_menu(Menu menu){ this.menu = menu; }

    public Prodotto get_prodotto(){return prodotto;}
    public void set_prodotto(Prodotto prodotto){this.prodotto = prodotto;}

}
