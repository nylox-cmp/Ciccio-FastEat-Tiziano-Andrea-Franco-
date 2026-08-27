package ImplementazioniDAO;

import ImplementazioniDAO.Utils.ResultSetMapper;
import dao.RiderDAO;
import database.ConnessioneDatabase;
import exception.BusinessError;
import exception.ErrorType;
import model.Ordine;
import model.StatoOrdine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RiderImpDAO implements RiderDAO{
    private Connection connection;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public RiderImpDAO(){
        try{
            connection = ConnessioneDatabase.getInstance().connection;
        } catch(SQLException ex){
            ex.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    //  Operazione Gestione Ordini

    @Override
    public void crea_richiesta_approvazione_consegna(String nickname_rider,String codice_ordine){
        String sql = "INSERT INTO RiderPropostiConsegna(nickname_rider,codice_ordine) VALUES(?,?);";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,nickname_rider);
            query.setString(2,codice_ordine);
            query.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            if (e.getMessage() != null && e.getMessage().contains("BEC1")) throw new BusinessError(ErrorType.RIDER_SUPERA_MAX_NUM_ORDINI);
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public void conferma_consegna_ordine(String codice_ordine, StatoOrdine stato){
        String sql = "UPDATE Ordine SET stato = ? WHERE codice = ?;";
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1, stato.name());
            query.setString(2, codice_ordine);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public void cancella_richiesta_approvazione_consegna(String nickname_rider,String codice_ordine){
        String sql = "DELETE FROM RiderPropostiConsegna WHERE nickname_rider = ? AND codice_ordine = ?;";
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1, nickname_rider);
            query.setString(2, codice_ordine);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodi Get

    @Override
    public ArrayList<Ordine> get_ordini_proposti() {
        ArrayList<Ordine> ordini = new ArrayList<>();
        String sql = "SELECT o.codice_ordine, o.costo, o.stato, o.indirizzo_consegna, o.data_ordine, " +
                "r.codice_ristorante, r.nome, r.indirizzo " +
                "FROM Ordine o JOIN Ristorante r ON o.codice_ristorante = r.codice_ristorante " +
                "WHERE o.stato = ?;";

        try (PreparedStatement query = connection.prepareStatement(sql)) {
            query.setInt(1, StatoOrdine.PREPARAZIONE.ordinal()); // intero, coerente con il DB
            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) { // <-- iterazione
                    ordini.add(ResultSetMapper.converti_reuslt_into_ordine(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
        return ordini;
    }

    @Override
    public ArrayList<Ordine> get_ordini_da_consegnare(String nickname) {
        ArrayList<Ordine> ordini = new ArrayList<>();
        String sql = "SELECT o.codice_ordine, o.costo, o.stato, o.indirizzo_consegna, o.data_ordine, " +
                    "r.codice_ristorante, r.nome, r.indirizzo " +
                    "FROM Ordine o " +
                    "JOIN RiderPropostiConsegna rp ON o.codice_ordine = rp.codice_ordine " +
                    "JOIN Ristorante r ON o.codice_ristorante = r.codice_ristorante " +
                    "WHERE rp.nickname_rider = ? AND rp.ordine_preso_a_carico = true " +
                    "AND o.stato IN (?, ?, ?, ?, ?) " +
                    "ORDER BY o.stato ASC;";

        try (PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1, nickname);
            query.setInt(2, StatoOrdine.PREPARAZIONE.ordinal());
            query.setInt(3, StatoOrdine.PRONTO_RITIRO_RIDER.ordinal());
            query.setInt(4, StatoOrdine.IN_CONSEGNA.ordinal());
            query.setInt(5, StatoOrdine.CONFERMA_CONSEGNA_RIDER.ordinal());
            query.setInt(6, StatoOrdine.CONFERMA_CONSEGNA_CLIENTE.ordinal());

            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    ordini.add(ResultSetMapper.converti_reuslt_into_ordine(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
        return ordini;
    }
}
