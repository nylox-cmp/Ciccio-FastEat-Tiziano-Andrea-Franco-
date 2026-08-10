package model;

import exception.BusinessError;
import exception.ErrorType;

import java.sql.BatchUpdateException;
import java.util.ArrayList;

public class Cliente extends Utente{
    private int punti_fedelta;
    private ArrayList<Ordine> ordini;

    public static final int PUNTI_FEDELTA_REGISTRAZIONE = 0;


    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public Cliente(Utente utente) {
        super(utente.get_email(), utente.get_password(), utente.get_nickname(), utente.get_nome(), utente.get_cognome());
        this.punti_fedelta = PUNTI_FEDELTA_REGISTRAZIONE;
    }

    public Cliente(Utente utente,int punti_fedelta) {
        super(utente.get_email(), utente.get_password(), utente.get_nickname(), utente.get_nome(), utente.get_cognome());
        this.punti_fedelta = punti_fedelta;
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Ordini

    public Ordine crea_ordine(String indirizzo,Ristorante ristorante){
        Ordine ordine = new Ordine(indirizzo,ristorante);
        ordine.set_indirizzo(indirizzo);
        this.ordini.add(ordine);
        return ordine;
    }

    public void annulla_ordine(Ordine ordine){
        if (ordini.contains(ordine)) {
            if (ordine.get_stato_ordine() == StatoOrdine.PREPARAZIONE)
                ordine.set_stato_ordine(StatoOrdine.ANNULLATO);
        }
    }

    public void conferma_consegna_ordine(Ordine ordine){
        if (ordine.get_stato_ordine() == StatoOrdine.IN_CONSEGNA)
            ordine.set_stato_ordine(StatoOrdine.CONFERMA_CONSEGNA_CLIENTE);

        if(ordine.get_stato_ordine() == StatoOrdine.CONFERMA_CONSEGNA_RIDER)
            ordine.set_stato_ordine(StatoOrdine.CONSEGNATO);
    }

    public void conferma_creazione_ordine(Ordine ordine){
        if(get_ordini().contains(ordine) && ordine.get_stato_ordine() == StatoOrdine.BOZZA)
            ordine.set_stato_ordine(StatoOrdine.PREPARAZIONE);
    }

    public void applica_sconto(int punti_fedelta,Ordine ordine) {
        if (this.punti_fedelta < punti_fedelta)  throw new BusinessError(ErrorType.PUNTI_FEDLELTA_NON_SUFFICIENTI);
        if (punti_fedelta > Ordine.MAX_PUNTI_FEDELTA_SCONTO) throw new BusinessError(ErrorType.PUNTI_FEDLELTA_SUPERANO_MAX);

        ordine.set_costo(ordine.get_costo() - ((ordine.get_costo() * punti_fedelta) / 100));
        this.punti_fedelta -= punti_fedelta;
    }

    //________________________________________________________________________________________________________________________________________________
    //Get and Set

    public void set_punti_fedelta(int punti_fedelta){ this.punti_fedelta = punti_fedelta; }
    public int get_punti_fedelta(){ return punti_fedelta; }

    public ArrayList<Ordine> get_ordini() { return ordini; }
    public void set_ordini(ArrayList<Ordine> ordini) { this.ordini = ordini; }
}
