package controller;

import exception.BusinessError;
import exception.ErrorType;
import model.*;

public class RistoranteController {
    private Ristorante ristorante;
    private Menu menu;
    private Prodotto prodotto;

    private Dipendente dipendente;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public RistoranteController(DipendenteController dipendenteController){
        this.ristorante = dipendenteController.get_dipendente().get_ristorante();
        this.dipendente = dipendenteController.get_dipendente();
    }

    //________________________________________________________________________________________________________________________________________________
    // Ristorante

        public void cancella_ristorante(){
        dipendente.cancella_ristorante(dipendente.get_ristorante());

        this.dipendente = null;
    }

    public void modifica_ristorante(String nome,String indirizzo){
        if(dipendente.puo_eseguire(Ruolo.GESTIONALE))
            throw  new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        ristorante.modifica_ristorante(nome,indirizzo);
    }

    //________________________________________________________________________________________________________________________________________________
    // Menu

    public void crea_menu(String nome){
        if(dipendente.puo_eseguire(Ruolo.GESTIONALE))
            throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        ristorante.crea_menu(nome);
    }

    public void modifica_menu(String nome_modificato){
        if(dipendente.puo_eseguire(Ruolo.GESTIONALE))
            throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        menu.modifica_menu(nome_modificato);
    }

    public void cancella_menu(Menu menu){
        if(dipendente.puo_eseguire(Ruolo.GESTIONALE))
            throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        ristorante.cancella_menu(menu);
    }

    //________________________________________________________________________________________________________________________________________________
    // Prodotto

    public void crea_prodotto(String nome,double prezzo_unitario){
        if(dipendente.puo_eseguire(Ruolo.GESTIONALE))
            throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);


        menu.crea_prodotto(nome,prezzo_unitario);
    }

    public void modifica_prodotto(String nome,double prezzo_unitario){
        if(dipendente.puo_eseguire(Ruolo.GESTIONALE))
            throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        prodotto.modifica_prodotto(nome,prezzo_unitario);
    }

    public void cancella_prodotto(Prodotto prodotto){
        if(dipendente.puo_eseguire(Ruolo.GESTIONALE))
            throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);


        menu.cancella_prodotto(prodotto);
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public Dipendente get_dipendente(){ return dipendente;}
    public void set_dipendente(Dipendente dipendente){ this.dipendente = dipendente; }

    public Ristorante get_ristorante(){ return ristorante;}
    public void set_ristorante(Ristorante ristorante){ this.ristorante = ristorante;}

    public Menu get_menu(){ return menu;}
    public void set_menu(Menu menu){ this.menu = menu; }

    public Prodotto get_prodotto(){return prodotto;}
    public void set_prodotto(Prodotto prodotto){this.prodotto = prodotto;}

}
