package model;

import exception.BusinessError;
import exception.ErrorType;

import java.time.LocalDate;
import java.util.ArrayList;

public class Ordine {
    private String codice_ordine;
    private double costo;
    private StatoOrdine stato_ordine;
    private String indirizzo;
    private LocalDate data;

    private ArrayList<Rider> rider_proposti = new ArrayList<Rider>();
    private ArrayList<RigaOrdine> righe_ordine = new ArrayList<RigaOrdine>();
    private Ristorante ristorante;
    private Rider rider = null;
    private Cliente cliente = null;

    public static final double MIN_COSTO_ORDINE_PER_PUNTI = 20.0;
    public static final int MAX_PUNTI_FEDELTA_SCONTO = 15;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public Ordine(String indirizzo,Ristorante ristorante,Cliente cliente){
        this.codice_ordine = Ristorante.genera_codice_univoco();
        this.data = LocalDate.now();
        this.stato_ordine = StatoOrdine.BOZZA;
        this.costo = 0.0;
        this.indirizzo = indirizzo;
        this.ristorante = ristorante;
        this.cliente = cliente;
    }

    public Ordine(String codice_ordine,double costo,StatoOrdine stato_ordine,String indirizzo,LocalDate data,Ristorante ristorante){
        this.codice_ordine = codice_ordine;
        this.costo = costo;
        this.stato_ordine = stato_ordine;
        this.indirizzo = indirizzo;
        this.data = data;
        this.ristorante = ristorante;
    }

    public Ordine(String codice_ordine,double costo,StatoOrdine stato_ordine,String indirizzo,LocalDate data,Ristorante ristorante,Rider rider){
        this.codice_ordine = codice_ordine;
        this.costo = costo;
        this.stato_ordine = stato_ordine;
        this.indirizzo = indirizzo;
        this.data = data;
        this.ristorante = ristorante;
        this.rider = rider;
    }

    //________________________________________________________________________________________________________________________________________________
    // Override

    @Override
    public String toString(){
        String string = get_codice_ordine() + " " + get_costo() + " " + get_indirizzo() + " " + get_data() + " " + get_stato_ordine() + " " + ristorante.toString();
        if(rider != null)
            string = string + " " + get_rider().toString();
        return string;
    }


    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return false;

        Ordine ordine = (Ordine) o;
        return ordine.get_codice_ordine().equals(this.get_codice_ordine());
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione Riga ordine

    private boolean contiene_prodotto(Prodotto prodotto){
        for(RigaOrdine riga_ordine : righe_ordine){
            if(prodotto.equals(riga_ordine.get_prodotto())) return true;
        }
        return false;
    }

    public RigaOrdine get_riga_ordine_from_prodotto(Prodotto prodotto){
        for(RigaOrdine riga_ordine : righe_ordine){
            if(riga_ordine.get_prodotto().equals(prodotto))
                return riga_ordine;
        }
        return null;
    }

    public void aggiungi_riga(Prodotto prodotto, int quantita) {
        if(stato_ordine != StatoOrdine.BOZZA) throw new BusinessError(ErrorType.ORDINE_NON_PUO_ESSERE_MODIFICATO_IN_QUESTO_STATO);
        if(contiene_prodotto(prodotto)) throw new BusinessError(ErrorType.ORDINE_POSSIEDE_RIGAORDINE_CON_STESSO_PRODOTTO);

        RigaOrdine riga_ordine = new RigaOrdine(prodotto, quantita, this);

        this.righe_ordine.add(riga_ordine);
        calcola_costo_ordine();
    }

    public void rimuovi_riga(Prodotto prodotto) {
        if(stato_ordine != StatoOrdine.BOZZA) throw new BusinessError(ErrorType.ORDINE_NON_PUO_ESSERE_MODIFICATO_IN_QUESTO_STATO);

        RigaOrdine riga_ordine = get_riga_ordine_from_prodotto(prodotto);
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

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public String get_codice_ordine() {
        return codice_ordine;
    }
    public void set_codice_ordine(String codice_ordine){ this.codice_ordine = codice_ordine; }

    public LocalDate get_data() { return data; }
    public void set_data(LocalDate data){ this.data = data;}

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

    public Cliente get_cliente(){return cliente;}
    public void set_cliente(Cliente cliente){this.cliente = cliente;}

    public ArrayList<RigaOrdine> get_righe_ordine() {
        return righe_ordine;
    }
    public void set_righe_ordine(ArrayList<RigaOrdine> rige_ordine) {
        this.righe_ordine = rige_ordine;
    }

    public Ristorante get_ristorante(){ return ristorante; }
    public void set_ristorante(Ristorante ristorante){ this.ristorante = ristorante;}
}