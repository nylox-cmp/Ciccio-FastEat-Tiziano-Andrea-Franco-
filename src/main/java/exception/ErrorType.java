package exception;

public enum ErrorType {
    NESSUN_ERRORE,
    CREDENZIALI_NON_VALIDE,
    INPUT_NON_VALIDO,
    INPUT_NON_UNIVOCO,
    ELEMENTO_SELEZIONATO_NULL,
    PERMESSI_NON_SUFFICIENTI;


    public static String converti_error_to_message(ErrorType error){
        String messaggio = "";
        switch (error){
            case NESSUN_ERRORE:
                break;
            case CREDENZIALI_NON_VALIDE:
                messaggio = "email o password errata ";
                break;
            case INPUT_NON_VALIDO:
                messaggio = "l'input inserito non è valido";
                break;
            case INPUT_NON_UNIVOCO:
                messaggio = "l'input deve essere univoco per essere accettabile ";
                break;
            case ELEMENTO_SELEZIONATO_NULL:
                messaggio = "l'operazione selezionata richiede che tu selezioni un elemento da una lista su cui eseguire l'operazione ";
                break;
            case PERMESSI_NON_SUFFICIENTI:
                messaggio = "l'operazione selezionata non è eseguibile, con i permessi di questo ruolo";
        }
        return messaggio;
    }
}
