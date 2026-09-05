package model;

import exception.BusinessError;
import exception.ErrorType;

import java.util.ArrayList;

public class Cliente extends Utente{
    private int punti_fedelta;
    private ArrayList<Ordine> ordini = new ArrayList<Ordine>();

    public static final int PUNTI_FEDELTA_REGISTRAZIONE = 0;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    /**
     * @author Tiziano
     * Costruttore utilizzato per la creazione di un nuovo cliente mai registrato all'interno del database
     *
     * @param utente  l'utente a cui verra aggiunto anche il ruolo cliente
     */
    public Cliente(Utente utente) {
        super(utente.get_email(), utente.get_password(), utente.get_nickname(), utente.get_nome(), utente.get_cognome());
        this.punti_fedelta = PUNTI_FEDELTA_REGISTRAZIONE;
    }

    /**
     * @author Tiziano
     * Costruttore utilizzato per costruire della classe cliente dai dati ricuperati dal database
     * @param utente        l'utente che possiede il ruolo del cliente
     * @param punti_fedelta  numero di punti_fedelta posseduti dal cliente
     */
    public Cliente(Utente utente,int punti_fedelta) {
        super(utente.get_email(), utente.get_password(), utente.get_nickname(), utente.get_nome(), utente.get_cognome());
        this.punti_fedelta = punti_fedelta;
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Ordini

    /**
     * @author Tiziano
     * Metodo che crea un'ordine posseduto dal cliente
     *
     * @param indirizzo  indirizzo di consegna dell'ordine creato
     * @param ristorante il ristorante a cui si effettuato l'ordine
     * @return the ordine
     */
    public Ordine crea_ordine(String indirizzo,Ristorante ristorante){
        Ordine ordine = new Ordine(indirizzo,ristorante,this);
        ordine.set_indirizzo(indirizzo);
        this.ordini.add(ordine);
        return ordine;
    }

    /**
     * @author Tiziano
     * Metodo che applica lo sconto all'ordine utillizando i punti fedelta utilizzati sottraendoli al numero di punti fedelta posseduti dal cliente
     *
     * @param punti_fedelta  punti fedelta utilizzato per calcolare lo sconto da applicare lo sconto
     * @param ordine         ordine su cui verra applicato lo sconto
     *
     * @throws BusinessError (PUNTI_FEDLELTA_NON_SUFFICIENTI,PUNTI_FEDLELTA_SUPERANO_MAX,ORDINE_NON_PUO_ESSERE_MODIFICATO_IN_QUESTO_STATO)
     */
    public void applica_sconto(int punti_fedelta,Ordine ordine) {
        if (this.punti_fedelta < punti_fedelta)  throw new BusinessError(ErrorType.PUNTI_FEDLELTA_NON_SUFFICIENTI);
        if (punti_fedelta > Ordine.MAX_PUNTI_FEDELTA_SCONTO) throw new BusinessError(ErrorType.PUNTI_FEDLELTA_SUPERANO_MAX);

        if (ordine.get_stato_ordine().equals(StatoOrdine.CONSEGNATO) || ordine.get_stato_ordine().equals(StatoOrdine.ANNULLATO))
            throw new BusinessError(ErrorType.ORDINE_NON_PUO_ESSERE_MODIFICATO_IN_QUESTO_STATO);

        ordine.set_costo(ordine.get_costo() - ((ordine.get_costo() * punti_fedelta) / 100));
        this.punti_fedelta -= punti_fedelta;
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione StatoOrdine

    /**
     * @author Tiziano
     * Metodo che modifica lo StatoOrdine dell'ordine confermandone la creazione passando lo StatoOrdine (Bozza -> Preparazione)
     *
     * @param ordine ordine a cui verra modificato lo stato
     */
    public void conferma_creazione_ordine(Ordine ordine){
        if(ordine.get_stato_ordine() != StatoOrdine.BOZZA)
            throw new BusinessError(ErrorType.IMPOSSIBBILE_CAMBIARE_STATO_AL_ORDINE);

        if(ordine.get_righe_ordine().isEmpty())
            throw new BusinessError(ErrorType.ORDINE_VUOTO);

        ordine.set_stato_ordine(StatoOrdine.PREPARAZIONE);
    }

    /**
     * @author Tiziano
     * Metodo che modifica lo StatoOrdine dell'Ordine ad annulato se solo se in (Bozza,Preparazione)
     *
     * @param ordine ordine a cui verra modificato lo stato
     */
    public void annulla_ordine(Ordine ordine){
        if(ordine.get_stato_ordine() != StatoOrdine.BOZZA && ordine.get_stato_ordine() != StatoOrdine.PREPARAZIONE)
            throw new BusinessError(ErrorType.IMPOSSIBBILE_CAMBIARE_STATO_AL_ORDINE);

        ordine.set_stato_ordine(StatoOrdine.ANNULLATO);
    }

    /**
     * @author Tiziano
     * Metodo che modifica lo StatoOrdine dell'Ordine seguendo questo schema:
     * In Consegna -> Conferma consegna cliente
     * Conferma consegna rider -> Consegnato
     *
     * @param ordine ordine a cui verra modificato lo stato
     */
    public void conferma_consegna_ordine(Ordine ordine){
        if(ordine.get_stato_ordine() != StatoOrdine.IN_CONSEGNA && ordine.get_stato_ordine() != StatoOrdine.CONFERMA_CONSEGNA_RIDER)
            throw new BusinessError(ErrorType.IMPOSSIBBILE_CAMBIARE_STATO_AL_ORDINE);

        if (ordine.get_stato_ordine() == StatoOrdine.IN_CONSEGNA)
            ordine.set_stato_ordine(StatoOrdine.CONFERMA_CONSEGNA_CLIENTE);

        if(ordine.get_stato_ordine() == StatoOrdine.CONFERMA_CONSEGNA_RIDER)
            ordine.set_stato_ordine(StatoOrdine.CONSEGNATO);
    }

    //________________________________________________________________________________________________________________________________________________
    //Get and Set


    public void set_punti_fedelta(int punti_fedelta){ this.punti_fedelta = punti_fedelta; }
    public int get_punti_fedelta(){ return punti_fedelta; }

    public ArrayList<Ordine> get_ordini() { return ordini; }
    public void set_ordini(ArrayList<Ordine> ordini) { this.ordini = ordini; }
}
