package model;

public enum StatoOrdine{
    BOZZA,
    PREPARAZIONE,
    PRONTO_RITIRO_RIDER,
    IN_CONSEGNA,
    CONFERMA_CONSEGNA_RIDER,
    CONFERMA_CONSEGNA_CLIENTE,
    CONSEGNATO,
    ANNULLATO;

    public String converti_statoOrdine_to_string(StatoOrdine statoOrdine){
        String stato_ordine = " ";

        switch (statoOrdine){
            case BOZZA:
                stato_ordine = "Bozza";
                break;
            case PREPARAZIONE:
                stato_ordine = "Preparazione";
                break;
            case PRONTO_RITIRO_RIDER:
                stato_ordine = "Pronto Ritiro Rider";
                break;
            case IN_CONSEGNA:
                stato_ordine = "In Consegna";
                break;
            case CONFERMA_CONSEGNA_RIDER:
                stato_ordine = "Segnalato come Consegnato Rider";
                break;
            case CONFERMA_CONSEGNA_CLIENTE:
                stato_ordine = "Segnalato come Consegnato Cliente";
                break;
            case CONSEGNATO:
                stato_ordine = "Consegnato";
                break;
            case ANNULLATO:
                stato_ordine = "Annulato";
                break;
        }

        return stato_ordine;
    }
}
