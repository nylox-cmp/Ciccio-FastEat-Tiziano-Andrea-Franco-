package ImplementazioniDAO.Utils;

import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Result set mapper.
 */
public class ResultSetMapper{

    //________________________________________________________________________________________________________________________________________________
    // Operazione Conversione ResultSet into Utente e i suoi Ruoli

    /**
     * @author Tiziano
     * Converti result into utente utente.
     *
     * @param result the result
     * @return the utente
     * @throws SQLException the sql exception
     */
    public static Utente converti_result_into_utente(ResultSet result) throws SQLException {
        return new Utente(result.getString("email"), result.getString("password"), result.getString("nickname"), result.getString("nome"), result.getString("cognome"));
    }

    /**
     * @author Tiziano
     * Converti result into rider rider.
     *
     * @param result the result
     * @return the rider
     * @throws SQLException the sql exception
     */
    public static Rider converti_result_into_rider(ResultSet result) throws SQLException{
        return new Rider(converti_result_into_utente(result),result.getString("mezzo_trasporto"));
    }

    /**
     * @author Tiziano
     * Converti result into cliente cliente.
     *
     * @param result the result
     * @return the cliente
     * @throws SQLException the sql exception
     */
    public static Cliente converti_result_into_cliente(ResultSet result) throws SQLException{
        return new Cliente(converti_result_into_utente(result),result.getInt("punti_fedelta"));
    }

    /**
     * @author Tiziano
     * Converti result into dipedente dipendente.
     *
     * @param result the result
     * @return the dipendente
     * @throws SQLException the sql exception
     */
    public static Dipendente converti_result_into_dipedente(ResultSet result) throws SQLException{
        return new Dipendente(converti_result_into_utente(result), Ruolo.values()[result.getInt("ruolo")]);
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Conversione ResultSet into Ristorante,Menu e Prodotto

    /**
     * @author Tiziano
     * Converti result into ristorante ristorante.
     *
     * @param result the result
     * @return the ristorante
     * @throws SQLException the sql exception
     */
    public static Ristorante converti_result_into_ristorante(ResultSet result) throws SQLException{
        return new Ristorante(result.getString("codice_ristorante"),result.getString("nome"),result.getString("indirizzo"));
    }

    /**
     * @author Tiziano
     * Converti result into menu menu.
     *
     * @param result the result
     * @return the menu
     * @throws SQLException the sql exception
     */
    public static Menu converti_result_into_menu(ResultSet result) throws SQLException{
        return new Menu(result.getString("nome"));
    }

    /**
     * @author Tiziano
     * Converti result into prodotto prodotto.
     *
     * @param result the result
     * @return the prodotto
     * @throws SQLException the sql exception
     */
    public  static Prodotto converti_result_into_prodotto(ResultSet result) throws SQLException {
        return new Prodotto(result.getString("nome"),result.getDouble("prezzo_unitario"));
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Conversione ResultSet into Ordine e RigaOrdine

    /**
     * @author Tiziano
     * Converti reuslt into ordine ordine.
     *
     * @param result the result
     * @return the ordine
     * @throws SQLException the sql exception
     */
    public static Ordine converti_reuslt_into_ordine(ResultSet result) throws SQLException{
        Ordine ordine = null;

        try {
            if (result.getString("mezzo_trasporto") != null)
                ordine =  new Ordine(result.getString("codice_ordine"), result.getDouble("costo"), StatoOrdine.values()[result.getInt("stato")], result.getString("indirizzo_consegna"), result.getDate("data_ordine").toLocalDate(), converti_result_into_ristorante(result),converti_result_into_rider(result));
        }
        catch (Exception e){ }

        if(ordine == null)
            ordine = new Ordine(result.getString("codice_ordine"), result.getDouble("costo"), StatoOrdine.values()[result.getInt("stato")], result.getString("indirizzo_consegna"), result.getDate("data_ordine").toLocalDate(), converti_result_into_ristorante(result));

        return ordine;
    }

    /**
     * @author Tiziano
     * Converti result into riga ordine riga ordine.
     *
     * @param result the result
     * @return the riga ordine
     * @throws SQLException the sql exception
     */
    public static RigaOrdine converti_result_into_rigaOrdine(ResultSet result) throws  SQLException{
        return new RigaOrdine(converti_result_into_prodotto(result),result.getInt("quantita"));
    }
}
