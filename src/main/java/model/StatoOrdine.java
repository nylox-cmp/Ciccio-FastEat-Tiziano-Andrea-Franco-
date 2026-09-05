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

    /**
     * @author Tiziano
     *
     * @param stato
     * @return
     */
    public static String converti_stato_to_string(StatoOrdine stato){
        String string = "";

        switch (stato){
            case BOZZA:
                string = "BOZZA";
                break;
            case PREPARAZIONE:
                string = "PREPARAZIONE";
                break;
            case PRONTO_RITIRO_RIDER:
                string = "PRONTO-RITIRO-RIDER";
                break;
            case IN_CONSEGNA:
                string = "IN-CONSEGNA";
                break;
            case CONFERMA_CONSEGNA_RIDER:
                string = "CONFERMA-CONSEGNA-RIDER";
                break;
            case CONFERMA_CONSEGNA_CLIENTE:
                string = "CONFERMA-CONSEGNA-CLIENTE";
                break;
            case CONSEGNATO:
                string = "CONSEGNATO";
                break;
            case ANNULLATO:
                string = "ANNULATO";
                break;
        }

        return string;
    }

}
