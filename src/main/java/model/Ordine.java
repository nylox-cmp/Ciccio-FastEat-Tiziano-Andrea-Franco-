package model;

import exception.*;
import java.time.*;
import java.util.ArrayList;

public class Ordine {
    private String codice_ordine; //Unique ReadOnly
    private double costo;
    private StatoOrdine stato_ordine;
    private String indirizzo;
    private LocalDate data; //ReadOnly

    private ArrayList<Rider> rider_proposti;
    private Rider rider;
    private ArrayList<RigaOrdine> righe_ordine;

    public static final double MIN_COSTO_ORDINE_PER_PUNTI = 20.0;
    public static final int MAX_PUNTI_FEDELTA_SCONTO = 15;

    public Ordine(String indirizzo){
        this.codice_ordine = Ristorante.genera_codice_univoco();
        this.data = LocalDate.now();
        this.stato_ordine = StatoOrdine.PREPARAZIONE;
        this.costo = 0.0;
        this.indirizzo = indirizzo;
        this.rider_proposti = new ArrayList<>();
        this.righe_ordine = new ArrayList<>();
    }

    //________________________________________________________________________________________________________________________________________________
    // Override

    @Override
    public String toString(){
        String identificativo = get_codice_ordine() + " " + get_costo() + " " + get_data();
        return identificativo;
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione Riga ordine

    public void aggiungi_riga(Prodotto prodotto, int quantita) {
        this.righe_ordine.add(new RigaOrdine(prodotto, quantita));
        calcola_costo_ordine();
    }

    public void rimuovi_riga(RigaOrdine riga_ordine) {
        righe_ordine.remove(riga_ordine);
        calcola_costo_ordine();
    }

    public double calcola_costo_ordine() {
        double totale = 0.0;
        for (RigaOrdine riga : this.righe_ordine) {
            totale += riga.get_prezzo_totale();
        }
        this.costo = totale;
        return totale;
    }

    public ErrorType applica_sconto(int punti_fedelta) {
        if(punti_fedelta <= MAX_PUNTI_FEDELTA_SCONTO){
            this.costo = this.costo - ((this.costo * punti_fedelta) / 100);
            return ErrorType.NESSUN_ERRORE;
        }
        return ErrorType.INPUT_NON_VALIDO;
    }


    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public double get_costo() {
        return costo;
    }
    public void set_costo(double costo) {
        this.costo = costo;
    }

    public StatoOrdine get_stato_ordine(){
        return stato_ordine;
    }
    public void set_stato_ordine(StatoOrdine stato_ordine){
        this.stato_ordine = stato_ordine;
    }

    public String get_indirizzo() {
        return indirizzo;
    }
    public void set_indirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public ArrayList<Rider> get_rider_proposti() {
        return rider_proposti;
    }
    public void set_rider_proposti(ArrayList<Rider> rider_proposti) {
        this.rider_proposti = rider_proposti;
    }

    public Rider get_rider() {
        return rider;
    }
    public void set_rider(Rider rider) {
        this.rider = rider;
    }

    public ArrayList<RigaOrdine> get_rige_ordine() {
        return righe_ordine;
    }
    public void set_rige_ordine(ArrayList<RigaOrdine> rige_ordine) {
        this.righe_ordine = rige_ordine;
        calcola_costo_ordine();
    }

    public LocalDate get_data() {
        return data;
    }
    public String get_codice_ordine() {
        return codice_ordine;
    }
}