package dao;

import model.RigaOrdine;

import java.util.ArrayList;

public interface OrdiniDAO {

    public ArrayList<RigaOrdine> get_righeOrdine(String codice_ordine);
}
