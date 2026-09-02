package controller;

import ImplementazioniDAO.RistoranteImpDAO;
import ImplementazioniDAO.Utils.EntityWitchId;
import exception.BusinessError;
import exception.ErrorType;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class RistoranteController {
    private Ristorante ristorante;
    private ArrayList<Menu> menu = new ArrayList<Menu>();
    private ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();

    private HashMap<Menu,Integer> id_menu = new HashMap<Menu,Integer>();
    private HashMap<Prodotto,Integer> id_prodotti = new HashMap<Prodotto,Integer>();

    private Dipendente dipendente;

    private RistoranteImpDAO ristoranteDB = new RistoranteImpDAO();

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public RistoranteController(DipendenteController dipendenteController){
        this.dipendente = dipendenteController.get_dipendente();
        this.ristorante = dipendente.get_ristorante();
    }

    //________________________________________________________________________________________________________________________________________________
    // Ristorante

    public void cancella_ristorante(){
        dipendente.cancella_ristorante();
        ristoranteDB.cancella_ristorante(ristorante.get_codice_ristorante());
        this.dipendente = null;
    }

    public void modifica_ristorante(String nome,String indirizzo){
        if(dipendente.puo_eseguire(Ruolo.GESTIONALE) == false) throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        ristorante.modifica_ristorante(nome,indirizzo);
        ristoranteDB.modifica_ristorante(nome,indirizzo,ristorante.get_codice_ristorante());
    }

    //________________________________________________________________________________________________________________________________________________
    // Menu

    public void crea_menu(String nome){
        if(dipendente.puo_eseguire(Ruolo.GESTIONALE) == false) throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        ristorante.crea_menu(nome);
        ristoranteDB.crea_menu(nome,get_ristorante().get_codice_ristorante());
    }

    public void modifica_menu(Menu menu,String nome){
        if(dipendente.puo_eseguire(Ruolo.GESTIONALE) == false) throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        menu.modifica_menu(nome);
        ristoranteDB.modifica_menu(nome,id_menu.get(menu));
    }

    public void cancella_menu(Menu menu){
        if(dipendente.puo_eseguire(Ruolo.GESTIONALE) == false) throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        ristorante.cancella_menu(menu);
        ristoranteDB.cancella_menu(id_menu.get(menu));
    }

    //________________________________________________________________________________________________________________________________________________
    // Prodotto

    public void crea_prodotto(Menu menu,String nome,double prezzo_unitario){
        if(dipendente.puo_eseguire(Ruolo.GESTIONALE) == false) throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        menu.crea_prodotto(nome,prezzo_unitario);
        ristoranteDB.crea_prodotto(nome,prezzo_unitario,id_menu.get(menu));
    }

    public void modifica_prodotto(Prodotto prodotto,String nome,double prezzo_unitario){
        if(dipendente.puo_eseguire(Ruolo.GESTIONALE) == false) throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        prodotto.modifica_prodotto(nome,prezzo_unitario);
        ristoranteDB.modifica_prodotto(nome,prezzo_unitario,id_prodotti.get(prodotto));
    }

    public void cancella_prodotto(Menu menu,Prodotto prodotto){
        if(dipendente.puo_eseguire(Ruolo.GESTIONALE) == false) throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        menu.cancella_prodotto(prodotto);
        ristoranteDB.cancella_prodotto(id_prodotti.get(prodotto));
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public Dipendente get_dipendente(){ return dipendente;}
    public void set_dipendente(Dipendente dipendente){ this.dipendente = dipendente; }

    public Ristorante get_ristorante(){ return ristorante;}
    public void set_ristorante(Ristorante ristorante){ this.ristorante = ristorante;}

    public ArrayList<Menu> get_menu(){
        id_menu = new HashMap<Menu,Integer>();

        EntityWitchId<Menu,Integer> menuMap = RistoranteImpDAO.get_menu(ristorante.get_codice_ristorante());

        menu = menuMap.entitys;
        ristorante.set_menu(menu);

        for(int i=0;i<menuMap.ids.size();i++){
            id_menu.put(menuMap.entitys.get(i),menuMap.ids.get(i));
            menuMap.entitys.get(i).set_ristorante(ristorante);
        }

        return menu;
    }

    public void set_menu(ArrayList<Menu> menu){this.menu = menu;}

    public ArrayList<Prodotto> get_prodotti(Menu menu){
        id_prodotti = new HashMap<Prodotto,Integer>();

        EntityWitchId<Prodotto,Integer> prodottiMap = RistoranteImpDAO.get_prodotti(id_menu.get(menu));

        prodotti = prodottiMap.entitys;
        menu.set_prodotti(prodotti);

        for(int i=0;i<prodottiMap.ids.size();i++){
            id_prodotti.put(prodottiMap.entitys.get(i),prodottiMap.ids.get(i));
            prodottiMap.entitys.get(i).set_menu(menu);
        }

        return prodotti;
    }
}
