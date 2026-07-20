package dao;

import model.*;

import java.util.ArrayList;

public interface RiderDAO {

    public void richiedi_approvazione_consegna(String codice_ordine);
    public void conferma_consegna_ordine(String codice_ordine);

    public ArrayList<Ordine> get_ordini_proposti();
    public ArrayList<Ordine> get_ordini_da_consegnare(String nickname);
}
