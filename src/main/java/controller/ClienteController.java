package controller;

import exception.ErrorType;
import model.*;

import javax.print.attribute.standard.JobOriginatingUserName;

public class ClienteController {
    private Cliente cliente;
    private UtenteController utente_controller;

    //________________________________________________________________________________________________________________________________________________
    //

    public void crea_ordine(String indirizzo,Ristorante ristorante){
        cliente.crea_ordine(indirizzo,ristorante);
    }

    public void annulla_ordine(Ordine ordine){
        cliente.annulla_ordine(ordine);
    }

    public void cambia_stato_ordine_in_preparazione(Ordine ordine){
        cliente.cambia_stato_in_preparazione(ordine);
    }

    public void conferma_consegna_ordine(Ordine orine){
        cliente.conferma_cosegna_ordine(orine);
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione Ordine

    public double calcola_costo_totale(Ordine ordine){
        return ordine.calcola_costo_ordine();
    }

    public ErrorType applica_sconto(Ordine ordine, int punti_fedelta){
        return ordine.applica_sconto(punti_fedelta);
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione RigaOrdine

    public ErrorType aggiungi_riga(Ordine ordine, Prodotto prodotto){
        ErrorType error = ordine.aggiungi_riga(prodotto,1,ordine);
        return error;
    }

    public void rimuovi_riga(Ordine ordine, RigaOrdine riga_ordine){
        ordine.rimuovi_riga(riga_ordine);
    }

    public void aumenta_quantita_prodotto(RigaOrdine riga_ordine){
        riga_ordine.aumenta_quantita();
    }

    public ErrorType diminuisci_quanita_prodotto(RigaOrdine rigaOrdine){
        return rigaOrdine.diminuisci_quantita();
    }

    //________________________________________________________________________________________________________________________________________________
    //Get and Set

    public Cliente get_cliente() { return cliente; }

    public void set_utente_controller(UtenteController utente_controller){
        this.utente_controller = utente_controller;
    }
}
