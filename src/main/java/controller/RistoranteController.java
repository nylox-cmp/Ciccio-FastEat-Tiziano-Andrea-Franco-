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

    /**
     * @author Tiziano
     *
     * @param dipendenteController the dipendente controller
     */
    public RistoranteController(DipendenteController dipendenteController){
        this.dipendente = dipendenteController.get_dipendente();
        this.ristorante = dipendente.get_ristorante();
    }

    //________________________________________________________________________________________________________________________________________________
    // Ristorante

    /**
     * @author Tiziano
     * Cancella ristorante.
     */
    public void cancella_ristorante(){
        dipendente.cancella_ristorante();
        ristoranteDB.cancella_ristorante(ristorante.get_codice_ristorante());
        this.dipendente = null;
    }

    /**
     * @author Tiziano
     * Modifica ristorante.
     *
     * @param nome      the nome
     * @param indirizzo the indirizzo
     * @throws BusinessError (PERMESSI_NON_SUFFICIENTI)
     */
    public void modifica_ristorante(String nome,String indirizzo){
        if(dipendente.puo_eseguire(Ruolo.SUPERVISORE) == false) throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        ristorante.modifica_ristorante(nome,indirizzo);
        ristoranteDB.modifica_ristorante(nome,indirizzo,ristorante.get_codice_ristorante());
    }

    //________________________________________________________________________________________________________________________________________________
    // Menu

    /**
     * @author Tiziano
     * Crea menu.
     *
     * @param nome the nome
     * @throws BusinessError (PERMESSI_NON_SUFFICIENTI)
     */
    public void crea_menu(String nome){
        if(dipendente.puo_eseguire(Ruolo.SUPERVISORE) == false) throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        ristorante.crea_menu(nome);
        ristoranteDB.crea_menu(nome,get_ristorante().get_codice_ristorante());
    }

    /**
     * @author Tiziano
     * Modifica menu.
     *
     * @param menu the menu
     * @param nome the nome
     * @throws BusinessError (PERMESSI_NON_SUFFICIENTI)
     */
    public void modifica_menu(Menu menu,String nome){
        if(dipendente.puo_eseguire(Ruolo.SUPERVISORE) == false) throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        menu.modifica_menu(nome);
        ristoranteDB.modifica_menu(nome,id_menu.get(menu));
    }

    /**
     * @author Tiziano
     * Cancella menu.
     *
     * @param menu the menu
     * @throws BusinessError (PERMESSI_NON_SUFFICIENTI)
     */
    public void cancella_menu(Menu menu){
        if(dipendente.puo_eseguire(Ruolo.SUPERVISORE) == false) throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        ristorante.cancella_menu(menu);
        ristoranteDB.cancella_menu(id_menu.get(menu));
    }

    //________________________________________________________________________________________________________________________________________________
    // Prodotto

    /**
     * @author Tiziano
     * Crea prodotto.
     *
     * @param menu            the menu
     * @param nome            the nome
     * @param prezzo_unitario the prezzo unitario
     * @throws BusinessError (PERMESSI_NON_SUFFICIENTI)
     */
    public void crea_prodotto(Menu menu,String nome,double prezzo_unitario){
        if(dipendente.puo_eseguire(Ruolo.SUPERVISORE) == false) throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        menu.crea_prodotto(nome,prezzo_unitario);
        ristoranteDB.crea_prodotto(nome,prezzo_unitario,id_menu.get(menu));
    }

    /**
     * @author Tiziano
     * Modifica prodotto.
     *
     * @param prodotto        the prodotto
     * @param nome            the nome
     * @param prezzo_unitario the prezzo unitario
     */
    public void modifica_prodotto(Prodotto prodotto,String nome,double prezzo_unitario){
        if(dipendente.puo_eseguire(Ruolo.SUPERVISORE) == false) throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        prodotto.modifica_prodotto(nome,prezzo_unitario);
        ristoranteDB.modifica_prodotto(nome,prezzo_unitario,id_prodotti.get(prodotto));
    }

    /**
     * @author Tiziano
     * Cancella prodotto.
     *
     * @param menu     the menu
     * @param prodotto the prodotto
     */
    public void cancella_prodotto(Menu menu,Prodotto prodotto){
        if(dipendente.puo_eseguire(Ruolo.SUPERVISORE) == false) throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        menu.cancella_prodotto(prodotto);
        ristoranteDB.cancella_prodotto(id_prodotti.get(prodotto));
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and Set


    public Dipendente get_dipendente(){ return dipendente;}


    public Ristorante get_ristorante(){ return ristorante;}

    /**
     * @author Tiziano
     * Meotodo che assoccia tramite hash map un menu al poprio id_menu
     *
     * @return the array list
     */
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


    /**
     * @author Tiziano
     * Metodo che associa tramite hahs map un prodotto al proprio id_prodotto
     *
     * @param menu the menu
     * @return the array list
     */
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
