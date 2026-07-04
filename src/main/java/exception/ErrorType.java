package exception;

import model.Ordine;
import model.Rider;

public enum ErrorType {
    NESSUN_ERRORE,
    CREDENZIALI_NON_VALIDE,
    INPUT_NULL,
    INPUT_NON_NUMERICO,
    INPUT_NON_UNIVOCO,
    ELEMENTO_SELEZIONATO_NULL,
    PERMESSI_NON_SUFFICIENTI,
    ORDINE_POSSIEDE_RIGAORDINE_CON_STESSO_PRODOTTO,
    PUNTI_FEDLELTA_SUPERANO_MAX,
    RIDER_SUPERA_MAX_NUM_ORDINI;

    public static String converti_error_to_message(ErrorType error){
        String messaggio = "";
        switch (error){
            case NESSUN_ERRORE:
                break;
            case CREDENZIALI_NON_VALIDE:
                messaggio = "email o password errata ";
                break;
            case INPUT_NULL:
                messaggio = "compila tutti i campi richiesti dall'operazione";
                break;
            case INPUT_NON_NUMERICO:
                messaggio = "l'input all'interno dei campi deve essere numerico";
            case INPUT_NON_UNIVOCO:
                messaggio = "l'input deve essere univoco per essere accettabile ";
                break;
            case ELEMENTO_SELEZIONATO_NULL:
                messaggio = "l'operazione selezionata richiede che tu selezioni un elemento da una lista su cui eseguire l'operazione ";
                break;
            case PERMESSI_NON_SUFFICIENTI:
                messaggio = "l'operazione selezionata non è eseguibile, con i permessi di questo ruolo";
                break;
            case ORDINE_POSSIEDE_RIGAORDINE_CON_STESSO_PRODOTTO:
                messaggio = "l'ordine possiede già una RigaOrdine con lo stesso identico Prdotto";
                break;
            case PUNTI_FEDLELTA_SUPERANO_MAX:
                messaggio = "il massimo di punti fedelta applicabili su un ordine è di " + Ordine.MAX_PUNTI_FEDELTA_SCONTO;
                break;
            case RIDER_SUPERA_MAX_NUM_ORDINI:
                messaggio = "il rider può trasportare in una singola volta un massimo di " + Rider.MAX_ORDINI_PER_RIDER + " ordini";
                break;
        }
        return messaggio;
    }
}
