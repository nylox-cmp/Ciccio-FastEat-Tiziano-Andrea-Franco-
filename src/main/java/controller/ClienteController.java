package controller;

import exception.ErrorType;
import model.*;

public class ClienteController {
    private Cliente cliente;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public ClienteController( ){

    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Cliente

    public void crea_ordine(String indirizzo, Ristorante ristorante) {
        cliente.crea_ordine(indirizzo, ristorante);
    }

    public void annulla_ordine(Ordine ordine) {
        cliente.annulla_ordine(ordine);
    }

    public void conferma_creazione_ordine(Ordine ordine) {
        cliente.conferma_creazione_ordine(ordine);
    }

    public void conferma_consegna_ordine(Ordine ordine) {
        cliente.conferma_consegna_ordine(ordine);
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione Ordine

    public ErrorType applica_sconto(Ordine ordine, int punti_fedelta) {
        return ordine.applica_sconto(punti_fedelta);
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione RigaOrdine

    public ErrorType aggiungi_riga(Ordine ordine, Prodotto prodotto) {
        ErrorType error = ordine.aggiungi_riga(prodotto, 1, ordine);
        return error;
    }

    public ErrorType rimuovi_riga(Ordine ordine, RigaOrdine riga_ordine) {
        return ordine.rimuovi_riga(riga_ordine);
    }

    public void aumenta_quantita_prodotto(RigaOrdine riga_ordine) {
        riga_ordine.aumenta_quantita();
    }

    public ErrorType diminuisci_quantita_prodotto(RigaOrdine rigaOrdine) {
        return rigaOrdine.diminuisci_quantita();
    }

    //________________________________________________________________________________________________________________________________________________
    //Get and Set


    public Cliente get_cliente() {
        return cliente;
    }

    public void set_cliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
