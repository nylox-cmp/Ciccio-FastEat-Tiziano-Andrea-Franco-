package ImplementazioniDAO.Utils;

import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetMapper{

    //________________________________________________________________________________________________________________________________________________
    // Operazione Conversione ResultSet into Utente e i suoi Ruoli

    public static Utente converti_result_into_utente(ResultSet result) throws SQLException {
        return new Utente(result.getString("email"), result.getString("password"), result.getString("nickname"), result.getString("nome"), result.getString("cognome"));
    }

    public static Rider converti_result_into_rider(ResultSet result) throws SQLException{
        return new Rider(converti_result_into_utente(result),result.getString("mezzo_trasporto"));
    }

    public static Cliente converti_result_into_cliente(ResultSet result) throws SQLException{
        return new Cliente(converti_result_into_utente(result),result.getInt("punti_fedelta"));
    }

    public static Dipendente converti_result_into_dipedente(ResultSet result) throws SQLException{
        return new Dipendente(converti_result_into_utente(result), Ruolo.values()[result.getInt("ruolo")]);
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Conversione ResultSet into Ristorante,Menu e Prodotto

    public static Ristorante converti_result_into_ristorante(ResultSet result) throws SQLException{
        return new Ristorante(result.getString("codice_ristorante"),result.getString("nome"),result.getString("indirizzo"));
    }

    public static Menu converti_result_into_menu(ResultSet result) throws SQLException{
        return new Menu(result.getString("nome"));
    }

    public  static Prodotto converti_result_into_prodotto(ResultSet result) throws SQLException {
        return new Prodotto(result.getString("nome"),result.getDouble("prezzo_unitario"));
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Conversione ResultSet into Ordine e RigaOrdine

    public static Ordine converti_reuslt_into_ordine(ResultSet result) throws SQLException{
        return new Ordine(result.getString("codice_ordine"),result.getDouble("costo"),StatoOrdine.values()[result.getInt("stato")],result.getString("indirizzo"),result.getDate("data_ordine").toLocalDate(),converti_result_into_ristorante(result));
    }

    public static RigaOrdine converti_result_into_rigaOrdine(ResultSet result) throws  SQLException{
        return new RigaOrdine(converti_result_into_prodotto(result),result.getInt("quantita"));
    }
}
