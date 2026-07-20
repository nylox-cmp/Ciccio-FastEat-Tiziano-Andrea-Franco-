package controller;

import exception.ErrorType;
import model.*;

import java.util.Optional;

public class ClienteController {
    private Cliente cliente;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public ClienteController( ){
        Optional<Cliente> optionalCliente = SessionManager.instance.get_utente().get_ruolo_utente(Cliente.class);
        if(optionalCliente.isPresent())
            this.cliente = optionalCliente.get();
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

    public void applica_sconto(Ordine ordine, int punti_fedelta) {
        ordine.applica_sconto(punti_fedelta);
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione RigaOrdine

    public void aggiungi_riga(Ordine ordine, Prodotto prodotto) {
        ordine.aggiungi_riga(prodotto, 1, ordine);
    }

    public void rimuovi_riga(Ordine ordine, RigaOrdine riga_ordine) {
         ordine.rimuovi_riga(riga_ordine);
    }

    public void aumenta_quantita_prodotto(RigaOrdine riga_ordine) {
        riga_ordine.aumenta_quantita();
    }

    public void diminuisci_quantita_prodotto(RigaOrdine rigaOrdine) {
        rigaOrdine.diminuisci_quantita();
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
