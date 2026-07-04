package model;

import exception.ErrorType;

public class RigaOrdine {
    private Prodotto prodotto;
    private int quantita;
    private double prezzo_totale;

    private Ordine ordine;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public RigaOrdine(Prodotto prodotto,int quantita){
        this.prodotto = prodotto;
        set_quantita(quantita);
        this.prezzo_totale = prodotto.get_prezzo_unitario() * quantita;
    }

    public RigaOrdine(Prodotto prodotto, int quantita,Ordine ordine) {
        this.prodotto = prodotto;
        set_quantita(quantita);
        this.prezzo_totale = prodotto.get_prezzo_unitario() * quantita;
        this.ordine = ordine;
    }

    //________________________________________________________________________________________________________________________________________________
    // Override

    @Override
    public String toString(){
        String string = prodotto.get_nome() + " " + get_prezzo_totale() + " " + get_quantita();
        return string;
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(o == null || o.getClass() == this.getClass()) return false;

        RigaOrdine riga_ordine = (RigaOrdine) o;
        return (riga_ordine.get_ordine().equals(this.get_ordine()) && riga_ordine.get_prodotto().equals(this.get_prodotto())); //Stesso Ordine e Stesso Prodotto == True
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione riga Ordine

    protected void calcola_prezzo_totale(){
        this.prezzo_totale = this.prodotto.get_prezzo_unitario() * this.quantita;
    }

    public void aumenta_quantita(){
        this.quantita += 1;
        calcola_prezzo_totale();
    }

    public ErrorType diminuisci_quantita(){
        if (this.quantita > 1) {
            this.quantita -= 1;
            calcola_prezzo_totale();
            return ErrorType.NESSUN_ERRORE;
        }
        return ErrorType.INPUT_NULL;
    }

    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public Prodotto get_prodotto() {
        return prodotto;
    }
    public void set_prodotto(Prodotto prodotto) {
        this.prodotto = prodotto;
        calcola_prezzo_totale();
    }

    public int get_quantita() {
        return quantita;
    }
    public void set_quantita(int quantita) {
        if (quantita > 0) {
            this.quantita = quantita;
            calcola_prezzo_totale();
        }
    }

    public double get_prezzo_totale() {
        return prezzo_totale;
    }
    public void set_prezzo_totale(double prezzo_totale) {
        this.prezzo_totale = prezzo_totale;
    }

    public Ordine get_ordine(){return ordine;}
    public void set_ordine(Ordine ordine){this.ordine = ordine;}
}