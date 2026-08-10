package model;


import exception.BusinessError;
import exception.ErrorType;

import java.util.ArrayList;

public class Dipendente extends Utente{
    private ArrayList<Dipendente> subordinati = new ArrayList<Dipendente>();
    private ArrayList<Dipendente> superiori = new ArrayList<Dipendente>();
    private Ruolo ruolo;
    private Ristorante ristorante;

    public static final Ruolo RUOLO_DIPEDENTE_CREATORE_RISTORANTE = Ruolo.MANAGER;
    public static final Ruolo RUOLO_DIPEDENTE_RISTORANTE = Ruolo.BASE;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public Dipendente(Utente utente, Ruolo ruolo, Ristorante ristorante) {
        super(utente.get_email(), utente.get_password(), utente.get_nickname(), utente.get_nome(), utente.get_cognome());
        this.ruolo = ruolo;
        this.ristorante = ristorante;
    }

    public Dipendente(Utente utente,Ruolo ruolo){
        super(utente.get_email(), utente.get_password(), utente.get_nickname(), utente.get_nome(), utente.get_cognome());
        this.ruolo = ruolo;
    }

    //________________________________________________________________________________________________________________________________________________
    // Override

    @Override
    public String toString(){
        String string = super.get_nickname() + " " + Ruolo.converti_ruolo_to_string(ruolo);
        return string;
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Dipendente

    public void licenziati(){
        if(ruolo == Ruolo.MANAGER){
            cancella_ristorante(ristorante);
        }
        ristorante = null;
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Permessi

    public boolean puo_eseguire(Ruolo ruolo_richiesto){
        return (ruolo.ordinal() <= ruolo_richiesto.ordinal());
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Dipedenti

    public void licenzia_dipendente(Dipendente dipendente){
        if(puo_eseguire(Ruolo.GESTIONALE))
            throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        if((dipendente.equals(dipendente) == false) && (puo_eseguire(Ruolo.GESTIONALE))){
            if(subordinati.contains(dipendente))
                subordinati.remove(dipendente);
        }
    }

    public void modifica_ruolo_dipendente(Dipendente dipendente, Ruolo ruolo){
        if(puo_eseguire(Ruolo.MANAGER))
            throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        if((dipendente.equals(dipendente) == false) && (puo_eseguire(Ruolo.MANAGER))){
            if(subordinati.contains(dipendente))
                dipendente.ruolo = ruolo;
        }
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Ristorante

    public void cancella_ristorante(Ristorante ristorante){
        if(puo_eseguire(Ruolo.MANAGER))
            throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        for(int i=0;i<subordinati.size();i += 1){
            licenzia_dipendente(subordinati.get(i));
        }
        ristorante = null;
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

    public void segnala_ordine_pronto_ritiro(Ordine ordine){
        if((ordine.get_stato_ordine() != StatoOrdine.PREPARAZIONE) && (ristorante.get_ordini().contains(ordine) == false))
            throw new BusinessError(ErrorType.ORDINE_NON_PUO_ESSERE_MODIFICATO_IN_QUESTO_STATO);

        ordine.set_stato_ordine(StatoOrdine.PRONTO_RITIRO_RIDER);
    }

    public void annulla_ordine(Ordine ordine) {
        if (ristorante.get_ordini().contains(ordine) && ordine.get_stato_ordine() == StatoOrdine.PREPARAZIONE)
            ordine.set_stato_ordine(StatoOrdine.ANNULLATO);
    }

    //________________________________________________________________________________________________________________________________________________
    //Get and Set

    public ArrayList<Dipendente> get_subordinati() { return subordinati; }
    public void set_subordinati(ArrayList<Dipendente> subordinati) { this.subordinati = subordinati; }

    public ArrayList<Dipendente> get_superiori() { return superiori; }
    public void set_superiori(ArrayList<Dipendente> superiori) { this.superiori = superiori; }

    public Ruolo get_ruolo() { return ruolo; }
    public void set_ruolo(Ruolo ruolo) { this.ruolo = ruolo; }

    public Ristorante get_ristorante() { return ristorante; }
    public void set_ristorante(Ristorante ristorante) { this.ristorante = ristorante; }


}
