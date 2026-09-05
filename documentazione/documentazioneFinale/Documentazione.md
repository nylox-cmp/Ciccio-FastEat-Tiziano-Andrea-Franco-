Il dominio scelto è quello della **ristorazione e della consegna a domicilio**. L'applicazione si
pone come intermediaria tra i dipendente del ristorante e il cliente,fornendo la possibilità agli utenti registrati di poter diventare rider consegnare gli ordini effettuati sulla piattaforma dai clienti 

l'applicazzione è stata disegnata tenendo in mette i tre tipi di utenti finali che poppolerano l'applicazzione **Cliente**,**Dipendente** e i **Rider** implementando un sistema renda impossibile comportamenti fraudolenti da parte di ogni ruolo tramite lo stato dell'ordine , fornendo la possibilita all'utente di potere assumere tutti e tre **ruoli** se lo desidera.

### Ruoli Utente 

il **CLIENTE** sarà il ruolo di default che verra assegnato alla registrazione all'utente, quest'ultimo potra guadagnare punti_fedelta effettuando degli ordini costo >= 20,
ricevendo un punto fedelta per ogni ordine Consegnato.

il ruolo **RIDER** sarà assumibile da un utente registrando il poprio mezzo di trasporto,potrà  richiedere di prendere in carico un'ordine a un Ristorante una volta accetta la sua richiesta, non appena l'ordine verra segnalato come pronto per il ritiro del rider.

l'utente potra assumere il ruolo di **DIPENDENTE** registrando il poprio ristorante all'interno della piattaforma o tramite il codice_ristorante fornito da un dipendente.il ruolo di dipendente può assumere a sua volta tre ruoli Direttore,Supervisore o Operatore ogni uno con dei permessi diversi 

(schema dei permessi in base al ruolo)
(ruolo ↓ )(operazione  →)

| RUOLO       | Come si Ottiene il ruolo                                         | Aggiornare lo stato dell'Ordine | Accettare e Rifitutare dei Rider che si sono Proposti alla consegna dell'Ordine | Creare/Modificare/Cancellare  Ristorante,Menu e Prodotto | Modificare il ruolo dei Dipendenti               |
| :---------- | ---------------------------------------------------------------- | ------------------------------- | ------------------------------------------------------------------------------- | -------------------------------------------------------- | ------------------------------------------------ |
| Operatore   | Diventando dipendente di un ristorante tramite codice_ristorante | SI                              | SI                                                                              | NO                                                       | NO                                               |
| Supervisore | tramite promozzione da parte del Direttore                       | SI                              | SI                                                                              | SI tutto tranne che cancellare il ristorante             | NO                                               |
| Direttore   | tramite registrazione del ristorante                             | SI                              | SI                                                                              | SI                                                       | SI assegnandoli dei ruoli inferiori al Direttore |

come già accennato precendemente l'applicazzione implementa un sistema "anti comportamenti fradulenti" da parte degli utenti, tramite un sistema di doppia verifica della consegna, impedendo determinate operazioni agli utenti in base allo StatoOrdine.

(tabella di transizione degli stati dell'ordine)

| StatoOrdine               | Descrizione                                                                                                                                                                        | Transizione di Stato                                                                                                                                                                                            |
| :------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Bozza                     | alla creazione dell'Ordine da parte del cliente entrera automaticamente in questo stato in cui potra essere modificato dal cliente                                                 | →**Preparazione** : il cliente conferma la creazione dell'ordine <br><br>→**Annulato** : il cliente annula l'ordine                                                                                             |
| Preparazione              | l'Ordine è in fase di preparazione, è può essere ancora annulato da parte dei Dipendenti del Ristorante o dal Cliente se ha Confermato la sua creazione per errore                 | →**Pronto Ritiro Rider** : i dipendenti segnalano che l'ordine sia pronto e possa essere ritirato dal rider scelto dai dipedenti<br><br>→**Annulato** : i dipedenti del ristorante o il cliente annula l'ordine |
| Pronto Ritiro Rider       | L'Ordine è pronto e in attesa che un rider si presenti per il ritiro presso il ristorante.<br><br>il rider accettato dal ristorante per la consegna dell'ordine.                   | →**In Consegna** : il rider conferma il ritiro dell'ordine <br>                                                                                                                                                 |
| In Consegna               | L'ordine è stato ritirato ed è in transito verso l'indirizzo di consegna indicato nell'ordine.                                                                                     | ->**Conferma Consegna Rider** : il rider conferma la consegna presso l'indirizzo indicato nell'ordine<br><br>→**Conferma Consegna Cliente** : il cliente conferma la consegna dell'ordine da parte del rider    |
| Conferma Consegna Rider   | il **Rider** conferma la consegna dell'ordine al cliente rimanendo in attessa della conferma del **Cliente**                                                                       | →**Consegnato** : il cliente conferma la consegna dell'ordine                                                                                                                                                   |
| Conferma Consegna Cliente | il **Cliente** conferma la consegna dell'ordine al cliente rimanendo in attessa della conferma del **Rider**                                                                       | →**Consegnato** : il rider conferma la consega dell'ordine                                                                                                                                                      |
| Consegnato                | l'Ordine è stato consegnato con successo al Cliente.<br><br>I punti fedeltà vengono accreditati al cliente al raggiungimento di questo stato (se rispettano le condizioni neccessarie) | Stato finale — nessuna transizione ulteriore possibile.                                                                                                                                                         |
| Annullato                 | l'Ordine è stato Annullato da parte del Cliente o dei Dipendenti del Ristorante.                                                                                                   | Stato finale — nessuna transizione ulteriore possibile.                                                                                                                                                         |

### Modifiche Effettuate
1. eliminizanazione degli attributi e dei metodi di nota_ordine e categoria eliminiate per   semplificare il dominio scelto
2. modifica delle relazione ricorsiva di dipendente , in 0..* a 0..* rispetto alla precendete 1 a 0..*, che non prendeva in considerazione che il manager non abbia dipedenti superiori , mentre i dipedenti con il ruolo base abbiano più superiori  ovvero i dipedenti con il ruolo gestionale e manager.
3. modifica delle relazione tra Ristorante , Menu e Prdotto in composizione,in modo da permette una cancellazione a "cascata" dei Menu e dei Prodotto al momento delle cancellazione del ristorante.
4. aggiunta della relazione ricorsiva su utente contente i ruoli dell'utente (Cliente,Dipendente e Rider), semplificando la gestione delle sottoclassi di utente da parte dei controller.
5. modifica dei modifiicatori di visibilita impostati tutti su privato, per protteggere tutti gli attributi non solo quelli più sensibili o quelli che richiedono metodi set che lancino dell'eccezzioni.
