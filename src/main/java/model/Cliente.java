package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Cliente extends Utente{
    private int punti_fedelta = 0;
    private ArrayList<Ordine> ordini;

    public Cliente(Utente utente,int punti_fedelta) {
        super(utente.get_email(), utente.get_password(), utente.get_nickname(), utente.get_nome(), utente.get_cognome());
        this.punti_fedelta = 0;
        this.ordini = ordini;
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
        if (!ordini.contains(ordine)) {
            if (ordine.get_stato_ordine() == StatoOrdine.PREPARAZIONE)
                ordine.set_stato_ordine(StatoOrdine.ANNULATO);
        }
    }

    public void conferma_cosegna_ordine(Ordine ordine){
        if (ordine.get_stato_ordine() == StatoOrdine.IN_CONSEGNA)
            ordine.set_stato_ordine(StatoOrdine.CONSEGNATO);
    }

    public void aggiung_punti_fedelta(Ordine ordine){
        if (ordine.get_costo() >= Ordine.MIN_COSTO_ORDINE_PER_PUNTI)
            punti_fedelta += 1;
    }

    //________________________________________________________________________________________________________________________________________________
    //Get and Set

    public void set_punti_fedelta(int punti_fedelta){ this.punti_fedelta = punti_fedelta; }
    public int get_punti_fedelta(){ return punti_fedelta; }

    public ArrayList<Ordine> get_ordini() { return ordini; }
    public void set_ordini(ArrayList<Ordine> ordini) { this.ordini = ordini; }
}
