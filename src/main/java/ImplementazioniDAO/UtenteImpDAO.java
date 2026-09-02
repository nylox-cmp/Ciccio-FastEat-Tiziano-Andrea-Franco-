package ImplementazioniDAO;

import ImplementazioniDAO.Utils.ResultSetMapper;
import dao.UtenteDAO;
import database.ConnessioneDatabase;
import model.*;

import exception.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteImpDAO implements UtenteDAO {
    private Connection connection;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public UtenteImpDAO(){
        try{
            connection = ConnessioneDatabase.getInstance().connection;
        } catch(SQLException ex){
            ex.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazioni Utente


    @Override
    public void sign_in(String email, String password, String nickname, String nome, String cognome) {
        String sql = "INSERT INTO Utente(nickname, email, password, nome, cognome) VALUES (?, ?, ?, ?, ?);";

        try (PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1, nickname);
            query.setString(2, email);
            query.setString(3, password);
            query.setString(4, nome);
            query.setString(5, cognome);
            query.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            if ("23505".equals(e.getSQLState())) throw new BusinessError(ErrorType.INPUT_NON_UNIVOCO);
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public Utente login(String email, String password) {
        String sql = "SELECT * FROM Utente WHERE email = ? AND password = ?;";

        try (PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1, email);
            query.setString(2, password);

            try(ResultSet result = query.executeQuery()) {
                if (result.next()) {
                    return ResultSetMapper.converti_result_into_utente(result);
                }
                throw new BusinessError(ErrorType.CREDENZIALI_NON_VALIDE);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public void cancella_account(String nickname) {
        String sql = "DELETE FROM Utente WHERE nickname = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1,nickname);
            query.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage() != null && e.getMessage().contains("BEC1")) throw new BusinessError(ErrorType.CANCELLAZIONE_ACCOUNT_ANNULATA_ORDINI_IN_CONSEGNA);
            if (e.getMessage() != null && e.getMessage().contains("BEC2")) throw new BusinessError(ErrorType.CANCELLAZIONE_RISTORANTE_ANNULATA_ORDINI_IN_CONSEGNA);
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Rider

    @Override
    public void registra_rider(String nickname,String mezzo_trasporto){
        String sql = "INSERT INTO Rider(nickname,mezzo_trasporto) VALUES (?,?);";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,nickname);
            query.setString(2,mezzo_trasporto);
            query.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }


    //________________________________________________________________________________________________________________________________________________
    // Operazione Cliente

    @Override
    public void registra_cliente(String nickname,int punti_fedelta){
        String sql = "INSERT INTO Cliente(nickname,punti_fedelta) VALUES(?,?);";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,nickname);
            query.setInt(2,punti_fedelta);
            query.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Dipedente

    @Override
    public boolean codice_ristorante_esiste(String codice_ristorante){
        String sql = "SELECT codice_ristorante FROM Ristorante WHERE codice_ristorante = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,codice_ristorante);

            try(ResultSet result = query.executeQuery()){
                if(result.next()) return true;
            }
            return false;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public void crea_ristorante(String nome,String indirizzo,String codice_ristorante){
        String sql = " INSERT INTO Ristorante(codice_ristorante,nome,indirizzo) VALUES(?,?,?);";

        try(PreparedStatement query = connection.prepareStatement(sql)){
           query.setString(1,codice_ristorante);
           query.setString(2,nome);
           query.setString(3,indirizzo);
           query.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public void registra_dipedente_creatore_ristorante(String nickname,Ruolo ruolo,String codice_ristorante){
        String sql = "INSERT INTO Dipendente(nickname,ruolo,codice_ristorante) values(?,?,?);";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,nickname);
            query.setInt(2,ruolo.ordinal());
            query.setString(3,codice_ristorante);
            query.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public void registra_dipedente_ristorante(String codice_ristorante,String nickname,Ruolo ruolo){
        if(codice_ristorante_esiste(codice_ristorante) == false) throw new BusinessError(ErrorType.CODICE_RISTORANTE_INESISTENTE);

        String sql = "INSERT INTO Dipendente(nickname,ruolo,codice_ristorante) values(?,?,?);";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,nickname);
            query.setInt(2,ruolo.ordinal());
            query.setString(3,codice_ristorante);
            query.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodi Get

    @Override
    public Utente get_utente(String nickname){
        String sql = "SELECT * FROM Utente u WHERE u.nickname = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,nickname);

            try(ResultSet result = query.executeQuery()){
                if(result.next())
                    return ResultSetMapper.converti_result_into_utente(result);
            }
            return null;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public Rider get_rider(String nickname){
        String sql = "SELECT * FROM Rider r JOIN Utente u ON u.nickname = r.nickname WHERE r.nickname = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){

            query.setString(1,nickname);
            try (ResultSet result = query.executeQuery()) {
                if (result.next()) {
                    return ResultSetMapper.converti_result_into_rider(result);
                }
                return null;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public Cliente get_cliente(String nickname){
        String sql = "SELECT * FROM cliente c JOIN Utente u ON u.nickname = c.nickname WHERE c.nickname = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,nickname);

            try(ResultSet result = query.executeQuery()){
                if(result.next())
                    return ResultSetMapper.converti_result_into_cliente(result);
            }
            return null;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public Dipendente get_dipedente(String nickname){
        String sql = "SELECT * FROM Dipendente d JOIN Utente u ON u.nickname = d.nickname WHERE d.nickname = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,nickname);

            try(ResultSet result = query.executeQuery()){
                if(result.next())
                    return ResultSetMapper.converti_result_into_dipedente(result);
            }
            return null;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }
}