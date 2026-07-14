package model;

import java.util.ArrayList;

public class Cliente extends Utente{
    private int punti_fedelta;
    private ArrayList<Ordine> ordini;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public Cliente(Utente utente) {
        super(utente.get_email(), utente.get_password(), utente.get_nickname(), utente.get_nome(), utente.get_cognome());
        this.punti_fedelta = 0;
    }

    public Cliente(Utente utente,int punti_fedelta) {
        super(utente.get_email(), utente.get_password(), utente.get_nickname(), utente.get_nome(), utente.get_cognome());
        this.punti_fedelta = punti_fedelta;
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Ordini

    public void crea_ordine(String indirizzo,Ristorante ristorante){
        Ordine ordine = new Ordine(indirizzo,ristorante);
        ordine.set_indirizzo(indirizzo);
        this.ordini.add(ordine);
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

    public void aggiungi_punti_fedelta(Ordine ordine){
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
