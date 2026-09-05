package model;


import exception.BusinessError;
import exception.ErrorType;

import java.util.ArrayList;


public class Rider extends Utente{
    private String mezzo_trasporto;
    private ArrayList<Ordine> ordini_da_consegnare = new ArrayList<Ordine>();

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    /**
     * @author Tiziano
     *
     * @param utente           utente
     * @param mezzo_trasporto  mezzo trasporto
     */
    public Rider(Utente utente,String mezzo_trasporto) {
        super(utente.get_email(), utente.get_password(), utente.get_nickname(), utente.get_nome(), utente.get_cognome());
        this.mezzo_trasporto = mezzo_trasporto;
    }

    //________________________________________________________________________________________________________________________________________________
    // Override

    @Override
    public String toString() {
        String string = get_nickname() + " " + get_mezzo_trasporto();
        return string;
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione Richieste Ordine

    /**
     * @author Tiziano
     * Metodo che crea aggiunge il rider all'interno della lista di rider che hanno fatto richiesta alla ocnsegna
     *
     * @param ordine ordine
     * @throws BusinessError (IMPOSSIBBILE_CAMBIARE_STATO_AL_ORDINE)
     */
    public void crea_richiesta_approvazione_consegna(Ordine ordine){
        if(ordine.get_stato_ordine() != StatoOrdine.PREPARAZIONE && ordine.get_stato_ordine() != StatoOrdine.PRONTO_RITIRO_RIDER)
            throw new BusinessError(ErrorType.IMPOSSIBBILE_CAMBIARE_STATO_AL_ORDINE);

        ordine.get_rider_proposti().add(this);
    }

    /**
     * @author Tiziano
     * Cancella richiesta approvazione consegna.
     *
     * @param ordine  ordine
     */
    public void cancella_richiesta_approvazione_consegna(Ordine ordine){
        ordine.get_rider_proposti().remove(this);
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione Ordine

    /**
     * @author Tiziano
     * Metodo che segnala l'ordine come In Consegna solo se l'ordine si trova nello stato Pronto Ritiro Rider
     *
     * @param ordine te ordine
     * @throws BusinessError (IMPOSSIBBILE_CAMBIARE_STATO_AL_ORDINE)
     */
    public void segnala_ordine_as_in_consegna(Ordine ordine){
        if(ordine.get_stato_ordine() != StatoOrdine.PRONTO_RITIRO_RIDER)
            throw new BusinessError(ErrorType.IMPOSSIBBILE_CAMBIARE_STATO_AL_ORDINE);

        if(ordini_da_consegnare.contains(ordine))
        ordine.set_stato_ordine(StatoOrdine.IN_CONSEGNA);
    }

    /**
     * @author Tiziano
     * Metodo che modifica lo StatoOrdine dell'Ordine seguendo questo schema:
     * In Consegna -> Conferma consegna cliente
     * Conferma consegna rider -> Consegnato
     *
     * @param ordine ordine
     * @throws BusinessError (IMPOSSIBBILE_CAMBIARE_STATO_AL_ORDINE)
     */
    public void conferma_consegna_ordine(Ordine ordine){
        if(ordine.get_stato_ordine() != StatoOrdine.IN_CONSEGNA && ordine.get_stato_ordine() != StatoOrdine.CONFERMA_CONSEGNA_CLIENTE)
            throw new BusinessError(ErrorType.IMPOSSIBBILE_CAMBIARE_STATO_AL_ORDINE);

        if (ordine.get_stato_ordine() == StatoOrdine.IN_CONSEGNA)
            ordine.set_stato_ordine(StatoOrdine.CONFERMA_CONSEGNA_RIDER);

        if(ordine.get_stato_ordine() == StatoOrdine.CONFERMA_CONSEGNA_CLIENTE)
            ordine.set_stato_ordine(StatoOrdine.CONSEGNATO);

    }

    //________________________________________________________________________________________________________________________________________________
    //Get and Set


    public String get_mezzo_trasporto() { return mezzo_trasporto; }
    public void set_mezzo_trasporto(String mezzo_trasporto) { this.mezzo_trasporto = mezzo_trasporto; }


    public ArrayList<Ordine> get_ordini_da_consegnare() { return ordini_da_consegnare; }
    public void set_ordini_da_consegnare(ArrayList<Ordine> ordini_da_consegnare) { this.ordini_da_consegnare = ordini_da_consegnare; }

}
