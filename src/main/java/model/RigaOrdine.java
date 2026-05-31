package model;

import exception.ErrorType;

public class RigaOrdine {
    private Prodotto prodotto;
    private int quantita;
    private double prezzo_totale;

    public RigaOrdine(Prodotto prodotto, int quantita) {
        this.prodotto = prodotto;
        this.quantita = quantita;
        this.prezzo_totale = prodotto.get_prezzo_unitario() * quantita;
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
        return ErrorType.INPUT_NON_VALIDO;
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
}