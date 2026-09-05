package exception;

import model.Ordine;

public enum ErrorType {
    IMPOSSIBILE_CONETTERSI_DATABASE,
    CREDENZIALI_NON_VALIDE,
    INPUT_NULL,
    INPUT_NON_NUMERICO,
    INPUT_NUMERICO_NEGATIVO,
    INPUT_NON_UNIVOCO,
    ELEMENTO_SELEZIONATO_NULL,
    PRODOTTO_NON_PRESENTE_ORDINE,
    PRODOTTO_PRESENTE_ORDINE,
    CODICE_RISTORANTE_INESISTENTE,
    PERMESSI_NON_SUFFICIENTI,
    ORDINE_GIA_POSSIEDE_RIDER_ACCETTATO,
    IMPOSSIBBILE_RIFIUTARE_UN_RIDER_DOPO_AVERLO_ACCETTATO,
    ORDINE_NON_PUO_ESSERE_MODIFICATO_IN_QUESTO_STATO,
    IMPOSSIBBILE_CAMBIARE_STATO_AL_ORDINE,
    ORDINE_POSSIEDE_RIGAORDINE_CON_STESSO_PRODOTTO,
    PUNTI_FEDLELTA_NON_SUFFICIENTI,
    PUNTI_FEDLELTA_SUPERANO_MAX,
    RICHIESTA_GIA_EFFETTUATA,
    ORDINE_VUOTO,
    CANCELLAZIONE_RISTORANTE_ANNULATA_ORDINI_IN_CONSEGNA,
    CANCELLAZIONE_ACCOUNT_ANNULATA_ORDINI_IN_CONSEGNA;


    /**
     * @author Tiziano
     *
     * @param error
     * @return
     */
    public static String converti_error_to_message(ErrorType error) {
        String messaggio = "";

        switch (error) {
            case IMPOSSIBILE_CONETTERSI_DATABASE:
                messaggio = "Database è in fase di manutenzione. Prova a conneterti più Tardi";
                break;
            case CREDENZIALI_NON_VALIDE:
                messaggio = "L'indirizzo e-mail o la password inseriti non sono corretti. Riprova.";
                break;
            case INPUT_NULL:
                messaggio = "Attenzione: uno o più campi obbligatori non sono stati compilati.";
                break;
            case INPUT_NON_NUMERICO:
                messaggio = "Il valore inserito non è valido. È richiesto un inserimento esclusivamente numerico.";
                break;
            case INPUT_NUMERICO_NEGATIVO:
                messaggio = "Il valore numerico inserito non può essere negativo o pari a zero.";
                break;
            case INPUT_NON_UNIVOCO:
                messaggio = "Il valore inserito è già presente nel sistema e non può essere duplicato.";
                break;
            case ELEMENTO_SELEZIONATO_NULL:
                messaggio = "Nessun elemento selezionato. Seleziona una voce dalla lista per procedere con l'operazione.";
                break;
            case PRODOTTO_NON_PRESENTE_ORDINE:
                messaggio = "Prodotto non presente nell'ordine. Prova add aggiungerlo all'orinde";
                break;
            case PRODOTTO_PRESENTE_ORDINE:
                messaggio = "Non puoi inserire un prodotto già presente all'interno dell'ordine";
                break;
            case CODICE_RISTORANTE_INESISTENTE:
                messaggio = "Il codice inserito è inesistente.Riprova con un'altro codice";
                break;
            case PERMESSI_NON_SUFFICIENTI:
                messaggio = "Operazione non consentita. Il tuo account non dispone dei permessi necessari per eseguire questa azione.";
                break;
            case ORDINE_GIA_POSSIEDE_RIDER_ACCETTATO:
                messaggio = "Impossibile accettare un rider dopo averne accetato un'altro :C";
                break;
            case IMPOSSIBBILE_RIFIUTARE_UN_RIDER_DOPO_AVERLO_ACCETTATO:
                messaggio = "Impossibile rifiutare un rider dopo averlo già accetato";
                break;
            case ORDINE_NON_PUO_ESSERE_MODIFICATO_IN_QUESTO_STATO:
                messaggio = "Impossibile eseguire la operazione selezionata su questo ordine in questo StatoOrdine.";
                break;
            case IMPOSSIBBILE_CAMBIARE_STATO_AL_ORDINE:
                messaggio = "Impossibile modificare lo stato dell'ordine in questo stato";
                break;
            case ORDINE_POSSIEDE_RIGAORDINE_CON_STESSO_PRODOTTO:
                messaggio = "Questo prodotto è già presente all'interno dell'ordine corrente.";
                break;
            case PUNTI_FEDLELTA_NON_SUFFICIENTI:
                messaggio = "Impossibile applicare lo sconto richiesto. Il non hia abbastanza punti fedeltà";
                break;
            case PUNTI_FEDLELTA_SUPERANO_MAX:
                messaggio = "Impossibile applicare lo sconto richiesto. Il limite massimo di punti fedeltà utilizzabili per singolo ordine è di " + Ordine.MAX_PUNTI_FEDELTA_SCONTO + " punti.";
                break;
            case ORDINE_VUOTO:
                messaggio = "Impossibile inviare l'ordine al ristorante un'ordine deve  almeno contenere al suo interno un prodotto";
                break;
            case RICHIESTA_GIA_EFFETTUATA:
                messaggio = "La richiesta è già stata effettuata,rimani in attessa di una risposta da parte dello stuff del Ristorante";
                break;
            case CANCELLAZIONE_RISTORANTE_ANNULATA_ORDINI_IN_CONSEGNA:
                messaggio = "Impossibile cancellare il Ristorante con degli ordini in consegna";
                break;
            case CANCELLAZIONE_ACCOUNT_ANNULATA_ORDINI_IN_CONSEGNA:
                messaggio = "Impossibile cancellare l'account con degli ordini in consegna come rider o come cliente";
                break;
            }
            return messaggio;
        }
}
