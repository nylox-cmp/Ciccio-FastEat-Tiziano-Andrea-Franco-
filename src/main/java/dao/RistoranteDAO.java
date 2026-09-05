package dao;

public interface RistoranteDAO  {

    public void cancella_ristorante(String codice_ristorante);

    public void modifica_ristorante(String nome,String indirizzo,String codice_ristorante);

    public void crea_menu(String nome,String codice_ristorante);

    public void modifica_menu(String nome,int id_menu);

    public void cancella_menu(int id_menu);

    public void crea_prodotto(String nome,double prezzo_unitario,int id_menu);

    public void modifica_prodotto(String nome,double prezzo_unitario,int id_prodotto);

    public void cancella_prodotto(int id_prodotto);
}
