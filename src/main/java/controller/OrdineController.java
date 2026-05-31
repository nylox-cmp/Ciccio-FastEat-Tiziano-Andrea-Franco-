package controller;

import exception.*;
import jdk.management.jfr.RecordingInfo;
import model.*;

import java.util.ArrayList;

public class OrdineController {


    //________________________________________________________________________________________________________________________________________________
    // Gestione Ordine

    public double calcola_costo_totale(Ordine ordine){
       return ordine.calcola_costo_ordine();
    }

    public ErrorType applica_sconto(Ordine ordine,int punti_fedelta){
        return ordine.applica_sconto(punti_fedelta);
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione RigaOrdine

    public void aggiungi_riga(Ordine ordine,Prodotto prodotto){
        ordine.aggiungi_riga(prodotto,1);
    }

    public void rimuovi_riga(Ordine ordine,RigaOrdine riga_ordine){
        ordine.rimuovi_riga(riga_ordine);
    }

    public void aumenta_quantita_prodotto(RigaOrdine riga_ordine){
        riga_ordine.aumenta_quantita();
    }

    public ErrorType diminuisci_quanita_prodotto(RigaOrdine rigaOrdine){
        return rigaOrdine.diminuisci_quantita();
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

}
