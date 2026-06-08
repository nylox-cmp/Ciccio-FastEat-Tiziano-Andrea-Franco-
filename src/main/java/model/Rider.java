package model;


import java.util.ArrayList;

public class Rider extends Utente{
    public String mezzo_trasporto;
    public ArrayList<Ordine> ordini = new ArrayList<Ordine>();

    public Rider(Utente utente,String mezzo_trasporto) {
        super(utente.get_email(), utente.get_password(), utente.get_nickname(), utente.get_nome(), utente.get_cognome());
        this.mezzo_trasporto = mezzo_trasporto;
        this.ordini = ordini;
    }

    //________________________________________________________________________________________________________________________________________________
    // Override

    @Override
    public String toString() {
        String identificativo = get_nickname() + " " + get_mezzo_trasporto();
        return identificativo;
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazioni su Ordine

    public void richiedi_approvazione_consegna(Ordine ordine){
        if(ordine.get_stato_ordine().ordinal() < StatoOrdine.IN_CONSEGNA.ordinal()){
            ordine.get_rider_proposti().add(this);
        }
    }

    public void conferma_cosegna_ordine(Ordine ordine){
        if (ordine.get_stato_ordine() == StatoOrdine.IN_CONSEGNA)
            ordine.set_stato_ordine(StatoOrdine.CONSEGNATO);
    }

    //________________________________________________________________________________________________________________________________________________
    //Get and Set

    public String get_mezzo_trasporto() { return mezzo_trasporto; }
    public void set_mezzo_trasporto(String mezzo_trasporto) { this.mezzo_trasporto = mezzo_trasporto; }

    public ArrayList<Ordine> get_ordini() { return ordini; }
    public void set_ordini(ArrayList<Ordine> ordini) { this.ordini = ordini; }
}
