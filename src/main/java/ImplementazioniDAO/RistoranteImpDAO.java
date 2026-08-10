package ImplementazioniDAO;

import ImplementazioniDAO.Utils.EntityWitchId;
import ImplementazioniDAO.Utils.ResultSetMapper;
import dao.RistoranteDAO;
import database.ConnessioneDatabase;
import exception.BusinessError;
import exception.ErrorType;
import model.Menu;
import model.Prodotto;
import model.Ristorante;

import java.sql.*;
import java.util.ArrayList;

public class RistoranteImpDAO implements RistoranteDAO {
    private Connection connection;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public RistoranteImpDAO(){
        try{
            connection = ConnessioneDatabase.getInstance().connection;
        } catch(SQLException ex){
            ex.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Ristorante

    @Override
    public void cancella_ristorante(String codice_ristorante){
        String sql = "DELETE FROM Ristorante WHERE codice_ristorante = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
          query.setString(1,codice_ristorante);
          query.executeUpdate();
        }
        catch (SQLException e){
            if (e.getMessage() != null && e.getMessage().contains("BEC3")) throw new BusinessError(ErrorType.CANCELLAZIONE_RISTORANTE_ANNULATA_ORDINI_IN_CONSEGNA);
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public void modifica_ristorante(String nome,String indirizzo,String codice_ristorante){
        String sql = "UPDATE Ristorante SET nome = ?,indirizzo = ? WHERE codice_ristorante = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,nome);
            query.setString(2,indirizzo);

            query.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Menu

    @Override
    public void crea_menu(String nome,String codice_ristorante){
        String sql = "INSERT INTO Menu(nome,codice_ristorante) VALUES(?,?);";

        try(PreparedStatement query = connection.prepareStatement(sql)){
           query.setString(1,nome);
           query.setString(2,codice_ristorante);
           query.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public void modifica_menu(String nome,int id_menu){
        String sql = "UPDATE Menu SET nome = ? WHERE id_menu = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,nome);
            query.setInt(2,id_menu);
            query.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public void cancella_menu(int id_menu){
        String sql = "DELETE FROM Menu WHERE id_menu = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setInt(1,id_menu);
            query.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Prodotto

    @Override
    public void crea_prodotto(String nome,double prezzo_unitario,int id_menu){
        String sql = "INSERT INTO Prodotto(nome,prezzo_unitario,id_menu) VALUES(?,?,?);";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,nome);
            query.setDouble(2,prezzo_unitario);
            query.setInt(3,id_menu);
            query.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public void modifica_prodotto(String nome,double prezzo_unitario,int id_prodotto){
        String sql = "UPDATE Prodotto SET nome = ?,prezzo_unitario = ? WHERE id_prodotto = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,nome);
            query.setDouble(2,prezzo_unitario);
            query.setInt(3,id_prodotto);
            query.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public void cancella_prodotto(int id_prodotto){
        String sql = "DELETE FROM Prodotto WHERE id_prodotto = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setInt(1,id_prodotto);
            query.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }

    }

    //________________________________________________________________________________________________________________________________________________
    // Metodi Get

    public static EntityWitchId<Menu,Integer> get_menu(String codice_ristorante){
        EntityWitchId<Menu,Integer> menuMap = new EntityWitchId<Menu,Integer>();
        String sql = "SELECT * FROM Menu WHERE codice_ristorante = ?;";

        Connection con = null;
        try{
           con = ConnessioneDatabase.getInstance().connection;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }

        try(PreparedStatement query = con.prepareStatement(sql)){
            query.setString(1,codice_ristorante);
            try(ResultSet result = query.executeQuery()){
                while(result.next()) {
                  menuMap.entitys.add(ResultSetMapper.converti_result_into_menu(result));
                  menuMap.ids.add(result.getInt("id_menu"));
                }
            }
            return menuMap;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    public static EntityWitchId<Prodotto,Integer>  get_prodotti(int id_menu){
        EntityWitchId<Prodotto,Integer> prodottiMap = new EntityWitchId<Prodotto,Integer>();
        String sql = "SELECT * FROM Prodotto WHERE id_menu = ?;";

        Connection con = null;
        try{
            con = ConnessioneDatabase.getInstance().connection;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }

        try(PreparedStatement query = con.prepareStatement(sql)){
            query.setInt(1,id_menu);

            try(ResultSet result = query.executeQuery()){
                while(result.next()) {
                    prodottiMap.entitys.add(ResultSetMapper.converti_result_into_prodotto(result));
                    prodottiMap.ids.add((result.getInt("id_prodotto")));
                }
            }
            return prodottiMap;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }
}
