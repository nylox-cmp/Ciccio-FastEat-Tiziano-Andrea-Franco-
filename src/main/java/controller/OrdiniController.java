package controller;


import ImplementazioniDAO.OrdiniImpDAO;
import ImplementazioniDAO.Utils.EntityWitchId;
import model.Ordine;
import model.Prodotto;
import model.RigaOrdine;

import java.util.ArrayList;
import java.util.HashMap;

public class OrdiniController {
    public OrdiniImpDAO ordiniDB = new OrdiniImpDAO();

    public ArrayList<RigaOrdine> get_contenuto_ordine(Ordine ordine){
        EntityWitchId<RigaOrdine,ArrayList<String>> righeOrdineMap = ordiniDB.get_righeOrdine(ordine.get_codice_ordine());
        if(righeOrdineMap == null) return null;

        ordine.set_righe_ordine(righeOrdineMap.entitys);
        return righeOrdineMap.entitys;
    }

}
