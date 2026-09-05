package model;


import exception.BusinessError;
import exception.ErrorType;

import java.util.ArrayList;

public class Dipendente extends Utente{
    private ArrayList<Dipendente> subordinati = new ArrayList<Dipendente>();
    private ArrayList<Dipendente> superiori = new ArrayList<Dipendente>();
    private Ruolo ruolo;
    private Ristorante ristorante;

    public static final Ruolo RUOLO_DIPEDENTE_CREATORE_RISTORANTE = Ruolo.DIRETTORE;
    public static final Ruolo RUOLO_DIPEDENTE_RISTORANTE = Ruolo.OPERATORE;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    /**
     * @author Tiziano
     *
     * @param utente     the utente
     * @param ruolo      the ruolo
     * @param ristorante the ristorante
     */
    public Dipendente(Utente utente, Ruolo ruolo, Ristorante ristorante) {
        super(utente.get_email(), utente.get_password(), utente.get_nickname(), utente.get_nome(), utente.get_cognome());
        this.ruolo = ruolo;
        this.ristorante = ristorante;
    }

    /**
     * @author Tiziano
     * Costruttore utilizzato per costruire il dipendente dai dati dal database , molto utilie per caricare i dipendenti subordinati
     * senza dover caricare il Ristorante (inutile per un subordinato), viene usato anche per il dipendente "normale"
     *
     * @param utente the utente
     * @param ruolo  the ruolo
     */
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

    /**
     * @author Tiziano
     * Metodo che fa licenzia il dipendente , se manager il licenzimento comportera alla cancellazione del ristorante
     */
    public void licenziati(){
        if(ruolo.equals(Ruolo.DIRETTORE)){
            cancella_ristorante();
        }
        ristorante = null;
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Permessi

    /**
     * @author Tiziano
     * Puo eseguire boolean, un metodo che verifica se il dipendente possega un ruolo maggiore o uguale del ruolo_richiesto per eseguire l'operazione
     *
     * @param ruolo_richiesto  ruolo richiesto
     * @return the boolean
     */
    public boolean puo_eseguire(Ruolo ruolo_richiesto){
        return (ruolo.ordinal() >= ruolo_richiesto.ordinal());
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Ristorante

    /**
     * @author Tizano
     * Metodo che cancella il ristorante è lancia un eccezzione se non si possega il ruolo per poterlo cancellare
     * @throws BusinessError (PERMESSI_NON_SUFFICIENTI)
     */
    public void cancella_ristorante(){
        if(puo_eseguire(Ruolo.DIRETTORE) == false) throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        for(int i=0;i<subordinati.size();i += 1){
            licenzia_dipendente(subordinati.get(i));
        }
        ristorante = null;
    }

    public boolean codice_ristorante_is_visible(){
        return puo_eseguire(Ruolo.SUPERVISORE);
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Dipedenti

    /**
     * @author Tiziano
     * Metodo che licenzia il dipendente se si possega il ruolo per poterlo fare
     *
     * @param dipendente  dipendente
     * @throws BusinessError (PERMESSI_NON_SUFFICIENTI)
     */
    public void licenzia_dipendente(Dipendente dipendente){
        if(puo_eseguire(Ruolo.SUPERVISORE) == false) throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        for(Dipendente subordinato : subordinati){
            if(subordinato.equals(dipendente)){
                subordinato.set_ristorante(null);
                subordinati.remove(subordinato);
                break;
            }
        }
    }

    /**
     * @author Tiziano
     * Modifica ruolo dipendente se si possrga il ruolo per poterlo fare
     *
     * @param dipendente  dipendente
     * @param ruolo       ruolo
     * @throws BusinessError (PERMESSI_NON_SUFFICIENTI)
     */
    public void modifica_ruolo_dipendente(Dipendente dipendente, Ruolo ruolo){
        if(puo_eseguire(Ruolo.DIRETTORE) == false) throw new BusinessError(ErrorType.PERMESSI_NON_SUFFICIENTI);

        if((dipendente.equals(dipendente) == false)){
            if(subordinati.contains(dipendente))
                dipendente.ruolo = ruolo;
        }
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Rider

    /**
     * @author Tiziano
     * Accetta rider proposto alla consegna dell'ordine
     *
     * @param ordine  ordine
     * @param rider   rider
     * @throws BusinessError (ORDINE_GIA_POSSIEDE_RIDER_ACCETTATO)
     */
    public void accetta_rider_proposto_consegna(Ordine ordine, Rider rider){
        if(ordine.get_rider() != null) throw new BusinessError(ErrorType.ORDINE_GIA_POSSIEDE_RIDER_ACCETTATO);

        ordine.set_rider(rider);
        ordine.get_rider_proposti().remove(rider);
    }

    /**
     * @author Tiziano
     * Rifiuta rider proposto alla consegna dell'ordine
     *
     * @param ordine  ordine
     * @param rider   rider
     * @throws BusinessError (IMPOSSIBBILE_RIFIUTARE_UN_RIDER_DOPO_AVERLO_ACCETTATO)
     */
    public void rifiuta_rider_propsto_consegna(Ordine ordine, Rider rider){
        if(ordine.get_rider().equals(rider)) throw new BusinessError(ErrorType.IMPOSSIBBILE_RIFIUTARE_UN_RIDER_DOPO_AVERLO_ACCETTATO);

        ordine.get_rider_proposti().remove(rider);
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Ordine

    /**
     * @author Tiziano
     * Metodo che segnala ordine pronto ritiro se è in stato Preparazione
     *
     * @param ordine ordine
     * @throws BusinessError (IMPOSSIBBILE_CAMBIARE_STATO_AL_ORDINE,PRONTO_RITIRO_RIDER)
     */
    public void segnala_ordine_pronto_ritiro(Ordine ordine){
        if((ordine.get_stato_ordine() != StatoOrdine.PREPARAZIONE) && (ristorante.get_ordini().contains(ordine) == false))
            throw new BusinessError(ErrorType.IMPOSSIBBILE_CAMBIARE_STATO_AL_ORDINE);

        if(ristorante.get_ordini().contains(ordine))
            ordine.set_stato_ordine(StatoOrdine.PRONTO_RITIRO_RIDER);
    }

    /**
     * @author Tiziano
     * Metodo che annula un'ordine se è in stato Preparazione
     *
     * @param ordine the ordine
     * @throws BusinessError (IMPOSSIBBILE_CAMBIARE_STATO_AL_ORDINE)
     */
    public void annulla_ordine(Ordine ordine) {
        if(ordine.get_stato_ordine() != StatoOrdine.PREPARAZIONE)
            throw new BusinessError(ErrorType.IMPOSSIBBILE_CAMBIARE_STATO_AL_ORDINE);

        if(ordine.get_stato_ordine() == StatoOrdine.PREPARAZIONE && ristorante.get_ordini().contains(ordine))
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
