package model;


import exception.ErrorType;

import java.util.ArrayList;

public class Rider extends Utente{
    private String mezzo_trasporto;
    private double paga;
    private ArrayList<Ordine> ordini = new ArrayList<Ordine>();

    public static int PERCENTUALE_PAGA_RIDER_PER_ORDINE = 5;
    public static int MAX_ORDINI_PER_RIDER = 3;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public Rider(Utente utente,String mezzo_trasporto) {
        super(utente.get_email(), utente.get_password(), utente.get_nickname(), utente.get_nome(), utente.get_cognome());
        this.mezzo_trasporto = mezzo_trasporto;
        this.paga = 0.0;
    }

    public Rider(Utente utente,double paga,String mezzo_trasporto,ArrayList<Ordine> ordini){
        super(utente.get_email(), utente.get_password(), utente.get_nickname(), utente.get_nome(), utente.get_cognome());
        this.paga = paga;
        this.mezzo_trasporto = mezzo_trasporto;
        this.ordini = ordini;
    }

    //________________________________________________________________________________________________________________________________________________
    // Override

    @Override
    public String toString() {
        String string = get_nickname() + " " + get_mezzo_trasporto();
        return string;
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazioni su Ordine

    public ErrorType richiedi_approvazione_consegna(Ordine ordine){
        if(ordine.get_stato_ordine().ordinal() < StatoOrdine.IN_CONSEGNA.ordinal()){
            if(ordini.size() >= MAX_ORDINI_PER_RIDER) return ErrorType.RIDER_SUPERA_MAX_NUM_ORDINI;
            ordine.get_rider_proposti().add(this);
        }
        return ErrorType.NESSUN_ERRORE;
    }

    public void paga_rider(Ordine ordine){
        if(ordine.get_stato_ordine() != StatoOrdine.CONSEGNATO) return;

        this.paga = (ordine.get_costo() * PERCENTUALE_PAGA_RIDER_PER_ORDINE) / 100;
    }

    public void conferma_cosegna_ordine(Ordine ordine){
        if (ordine.get_stato_ordine() == StatoOrdine.IN_CONSEGNA)
            ordine.set_stato_ordine(StatoOrdine.CONFERMA_CONSEGNA_RIDER);

        if(ordine.get_stato_ordine() == StatoOrdine.CONFERMA_CONSEGNA_CLIENTE){
            ordine.set_stato_ordine(StatoOrdine.CONSEGNATO);
            paga_rider(ordine);
        }
    }


    //________________________________________________________________________________________________________________________________________________
    //Get and Set

    public String get_mezzo_trasporto() { return mezzo_trasporto; }
    public void set_mezzo_trasporto(String mezzo_trasporto) { this.mezzo_trasporto = mezzo_trasporto; }

    public ArrayList<Ordine> get_ordini() { return ordini; }
    public void set_ordini(ArrayList<Ordine> ordini) { this.ordini = ordini; }

    public double get_paga(){return paga;}
    public void set_paga(){this.paga = paga;}
}
