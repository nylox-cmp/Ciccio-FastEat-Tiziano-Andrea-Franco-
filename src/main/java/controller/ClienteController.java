package controller;

import model.Cliente;
import model.Ordine;
import model.Ristorante;

import javax.print.attribute.standard.JobOriginatingUserName;

public class ClienteController {
    private Cliente cliente;
    private UtenteController utente_controller;

    //________________________________________________________________________________________________________________________________________________
    //

    public void crea_ordine(String indirizzo,Ristorante ristorante){
        Ordine ordine = cliente.crea_ordine(indirizzo,ristorante);
    }

    public void annulla_ordine(Ordine ordine){
        cliente.annulla_ordine(ordine);
    }

    public void conferma_consegna_ordine(Ordine orine){
        cliente.conferma_cosegna_ordine(orine);
    }


    //________________________________________________________________________________________________________________________________________________
    //Get and Set

    public Cliente get_cliente() { return cliente; }

    public void set_utente_controller(UtenteController utente_controller){
        this.utente_controller = utente_controller;
    }
}
