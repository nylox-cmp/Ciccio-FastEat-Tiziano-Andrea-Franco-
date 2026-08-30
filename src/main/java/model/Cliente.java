package model;

import exception.BusinessError;
import exception.ErrorType;

import java.util.ArrayList;

public class Cliente extends Utente{
    private int punti_fedelta;
    private ArrayList<Ordine> ordini = new ArrayList<Ordine>();

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

    public void applica_sconto(int punti_fedelta,Ordine ordine) {
        if (this.punti_fedelta < punti_fedelta)  throw new BusinessError(ErrorType.PUNTI_FEDLELTA_NON_SUFFICIENTI);
        if (punti_fedelta > Ordine.MAX_PUNTI_FEDELTA_SCONTO) throw new BusinessError(ErrorType.PUNTI_FEDLELTA_SUPERANO_MAX);

        ordine.set_costo(ordine.get_costo() - ((ordine.get_costo() * punti_fedelta) / 100));
        this.punti_fedelta -= punti_fedelta;
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione StatoOrdine

    public void conferma_creazione_ordine(Ordine ordine){
        if(ordine.get_stato_ordine() != StatoOrdine.BOZZA)
            throw new BusinessError(ErrorType.IMPOSSIBBILE_CAMBIARE_STATO_AL_ORDINE);

        if(ordini.contains(ordine))
            ordine.set_stato_ordine(StatoOrdine.PREPARAZIONE);
    }

    public void annulla_ordine(Ordine ordine){
        if(ordine.get_stato_ordine() != StatoOrdine.BOZZA && ordine.get_stato_ordine() != StatoOrdine.PREPARAZIONE)
            throw new BusinessError(ErrorType.IMPOSSIBBILE_CAMBIARE_STATO_AL_ORDINE);

        if (ordini.contains(ordine))
            ordine.set_stato_ordine(StatoOrdine.ANNULLATO);
    }

    public void conferma_consegna_ordine(Ordine ordine){
        if(ordine.get_stato_ordine() != StatoOrdine.IN_CONSEGNA && ordine.get_stato_ordine() != StatoOrdine.CONFERMA_CONSEGNA_RIDER)
            throw new BusinessError(ErrorType.IMPOSSIBBILE_CAMBIARE_STATO_AL_ORDINE);

        if (ordine.get_stato_ordine() == StatoOrdine.IN_CONSEGNA && ordini.contains(ordine))
            ordine.set_stato_ordine(StatoOrdine.CONFERMA_CONSEGNA_CLIENTE);

        if(ordine.get_stato_ordine() == StatoOrdine.CONFERMA_CONSEGNA_RIDER && ordini.contains(ordine))
            ordine.set_stato_ordine(StatoOrdine.CONSEGNATO);
    }

    //________________________________________________________________________________________________________________________________________________
    //Get and Set

    public void set_punti_fedelta(int punti_fedelta){ this.punti_fedelta = punti_fedelta; }
    public int get_punti_fedelta(){ return punti_fedelta; }

    public ArrayList<Ordine> get_ordini() { return ordini; }
    public void set_ordini(ArrayList<Ordine> ordini) { this.ordini = ordini; }
}
