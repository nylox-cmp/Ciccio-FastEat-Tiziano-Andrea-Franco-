package dao;

import model.Menu;
import model.Prodotto;

import java.util.ArrayList;

public interface RistoranteDAO {

    public void cancella_ristorante();
    public void modifica_ristorante(String nome,String indirizzo);

    public void crea_menu(String nome);
    public void modifica_menu(String nome);
    public void cancella_menu(String nome);

    public void crea_prodotto(String nome_menu,String nome,double prezzo_unitario);
    public void modifica_prodotto(String nome_menu,String nome,double prezzo_unitario);
    public void cancella_menu(String nome_prodotto,String nome_menu);

    public ArrayList<Menu> get_menu();
    public ArrayList<Prodotto> get_prodotto();

    public String get_codice_ristorante(String codice_ristorante);
    public void set_codice_ristorante(String codice_ristorante);
}
