package dao;

import model.Ordine;

import java.util.ArrayList;

public interface RiderDAO {

    public void crea_richiesta_approvazione_consegna(String nickname_rider,String codice_ordine);

    public void cancella_richiesta_approvazione_consegna(String nickname_rider,String codice_ordine);

    public ArrayList<Ordine> get_ordini_proposti(String nickname);

    public ArrayList<Ordine> get_ordini_da_consegnare(String nickname);
}
