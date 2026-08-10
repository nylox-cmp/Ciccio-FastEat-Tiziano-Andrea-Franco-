package controller;


import ImplementazioniDAO.OrdiniImpDAO;
import model.Ordine;
import model.RigaOrdine;

import java.util.ArrayList;

public class OrdiniController {
    private OrdiniImpDAO ordiniDB = new OrdiniImpDAO();

    public ArrayList<RigaOrdine> get_contenuto_ordine(Ordine ordine){
        ArrayList<RigaOrdine> righe_ordine = ordiniDB.get_righeOrdine(ordine.get_codice_ordine());
        ordine.set_rige_ordine(righe_ordine);
        return righe_ordine;
    }

}
