package ImplementazioniDAO;

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

    private Utente converti_result_into_utente(ResultSet result) throws SQLException{
        return new Utente(result.getString("email"), result.getString("password"), result.getString("nickname"), result.getString("nome"), result.getString("cognome"));
    }

    private Rider converti_result_into_rider(ResultSet result) throws SQLException{
        return new Rider(converti_result_into_utente(result),result.getString("mezzo_trasporto"));
    }

    private Cliente converti_result_into_cliente(ResultSet result) throws SQLException{
        return new Cliente(converti_result_into_utente(result),result.getInt("punti_fedelta"));
    }

    private Dipendente converti_result_into_dipedente(ResultSet resultSet) throws SQLException{
        return null; //--------------------------------------------------------------------------------------------------------------------------------------------------
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazioni Utente


    @Override
    public void sign_in(String email, String password, String nickname, String nome, String cognome) {
        String sql = "INSERT INTO Utente(nickname, email, password, nome, cognome) VALUES (?, ?, ?, ?, ?)";

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
            throw new BusinessError(ErrorType.INPUT_NON_UNIVOCO);
        }
    }

    @Override
    public Utente login(String email, String password) {
        String sql = "SELECT * FROM Utente WHERE email = ? AND password = ?";

        try (PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1, email);
            query.setString(2, password);

            try (ResultSet result = query.executeQuery()) {
                if (result.next()) {
                    return converti_result_into_utente(result);
                }
                throw new BusinessError(ErrorType.CREDENZIALI_NON_VALIDE);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }



    public void cancella_account(String nickname) {
        String sql = "DELETE FROM Utente WHERE nickname = ?";
        try(PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1,nickname);
            query.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();

        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazioni di Ruolo

    @Override
    public void registrazione_rider(String nickname,String mezzo_trasporto){
        String sql = "INSERT INTO Rider(nickname,mezzo_trasporto) VALUES (?,?)";
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

    @Override
    public Rider get_rider(String nickname){
        String sql = "SELECT * FROM Rider WHERE nickname = ?";

        try(PreparedStatement query = connection.prepareStatement(sql)){

            query.setString(1,nickname);
            try (ResultSet result = query.executeQuery()) {
                if (result.next()) {
                    return converti_result_into_rider(result);
                }
                throw new BusinessError(ErrorType.CREDENZIALI_NON_VALIDE);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }


    //________________________________________________________________________________________________________________________________________________
    // Get From database

    @Override
    public void crea_ristorante(String nome,String indirizzo,String codice_ristorante,String nickname,Ruolo ruolo){
        String sql = """
                    INSERT INTO Ristorante(codice_ristorante,nome,indirizzo) VALUES(?,?,?);
                    INSERT INTO Dipedente(nickname,ruolo) VALUES(?,?);
                    """;

        try(PreparedStatement query = connection.prepareStatement(sql)){
           query.setString(1,codice_ristorante);
           query.setString(2,nome);
           query.setString(3,indirizzo);

        }
        catch (SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public void registrazione_dipedente_ristorante(String codice_ristorante){

    }

    @Override
    public Dipendente get_dipedente(String nickname){
        return null;
    }

}
