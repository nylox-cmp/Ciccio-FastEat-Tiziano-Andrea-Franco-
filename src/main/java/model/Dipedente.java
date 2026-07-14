package model;


import exception.ErrorType;

import java.util.ArrayList;

public class Dipedente extends Utente{
    private ArrayList<Dipedente> subordinati = new ArrayList<Dipedente>();
    private ArrayList<Dipedente> superiori = new ArrayList<Dipedente>();
    private Ruolo ruolo;
    private Ristorante ristorante;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public Dipedente(Utente utente, Ruolo ruolo,Ristorante ristorante) {
        super(utente.get_email(), utente.get_password(), utente.get_nickname(), utente.get_nome(), utente.get_cognome());
        this.ruolo = ruolo;
        this.ristorante = ristorante;
    }

    //________________________________________________________________________________________________________________________________________________
    // Override

    @Override
    public String toString(){
        String string = super.get_nickname() + " " + Ruolo.converti_ruolo_to_string(ruolo);
        return string;
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Dipedente

    public ErrorType licenziati(){
        if(ruolo == Ruolo.MANAGER){
            ErrorType error = cancella_ristorante(ristorante);
            if(error != ErrorType.NESSUN_ERRORE) return error;
        }
        ristorante = null;
        return ErrorType.NESSUN_ERRORE;
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Dipedenti

    public boolean puo_eseguire(Ruolo ruolo_richiesto){
        return (ruolo.ordinal() <= ruolo_richiesto.ordinal());
    }


    public ErrorType licenzia_dipedente(Dipedente dipedente){
        if(ruolo.ordinal() <= Ruolo.GESTIONALE.ordinal())
            return ErrorType.PERMESSI_NON_SUFFICIENTI;

        if((dipedente.equals(dipedente) == false) && (puo_eseguire(Ruolo.GESTIONALE))){
            if(subordinati.contains(dipedente))
                subordinati.remove(dipedente);
        }
        return ErrorType.NESSUN_ERRORE;
    }

    public ErrorType modifica_ruolo_dipendente(Dipedente dipedente,Ruolo ruolo){
        if(ruolo.ordinal() < Ruolo.MANAGER.ordinal())
            return ErrorType.PERMESSI_NON_SUFFICIENTI;

        if((dipedente.equals(dipedente) == false) && (puo_eseguire(Ruolo.MANAGER))){
            if(subordinati.contains(dipedente))
                dipedente.ruolo = ruolo;
        }
        return ErrorType.NESSUN_ERRORE;
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Ristorante

    public ErrorType cancella_ristorante(Ristorante ristorante){
        if(ruolo != Ruolo.MANAGER && this.ristorante != ristorante) return  ErrorType.PERMESSI_NON_SUFFICIENTI;

        for(int i=0;i<subordinati.size();i += 1){
            licenzia_dipedente(subordinati.get(i));
        }
        ristorante = null;

        return ErrorType.NESSUN_ERRORE;
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Rider

    public void accetta_rider(Ordine ordine,Rider rider){
        if(ristorante.get_ordini().contains(ordine) && ordine.get_rider_proposti().contains(rider) && ordine.get_rider() != null) {
            ordine.set_rider(rider);
            ordine.get_rider_proposti().remove(rider);
        }
    }

    public void rifiuta_rider(Ordine ordine,Rider rider){
        if(ristorante.get_ordini().contains(ordine)){
            ordine.get_rider_proposti().remove(rider);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Ordine

    public ErrorType segnala_ordine_pronto_ritiro(Ordine ordine){
        if((ordine.get_stato_ordine() != StatoOrdine.PREPARAZIONE) && (ristorante.get_ordini().contains(ordine) == false))
            return ErrorType.ORDINE_NON_PUO_ESSERE_MODIFICATO_IN_QUESTO_STATO;

        ordine.set_stato_ordine(StatoOrdine.PRONTO_RITIRO_RIDER);
        return ErrorType.NESSUN_ERRORE;
    }

    public void annulla_ordine(Ordine ordine) {
        if (ristorante.get_ordini().contains(ordine) && ordine.get_stato_ordine() == StatoOrdine.PREPARAZIONE)
            ordine.set_stato_ordine(StatoOrdine.ANNULLATO);
    }

    //________________________________________________________________________________________________________________________________________________
    //Get and Set

    public ArrayList<Dipedente> get_subordinati() { return subordinati; }
    public void set_subordinati(ArrayList<Dipedente> subordinati) { this.subordinati = subordinati; }

    public ArrayList<Dipedente> get_superiori() { return superiori; }
    public void set_superiori(ArrayList<Dipedente> superiori) { this.superiori = superiori; }

    public Ruolo get_ruolo() { return ruolo; }
    public void set_ruolo(Ruolo ruolo) { this.ruolo = ruolo; }

    public Ristorante get_ristorante() { return ristorante; }
    public void set_ristorante(Ristorante ristorante) { this.ristorante = ristorante; }


}
