package ImplementazioniDAO;

import ImplementazioniDAO.Utils.EntityWitchId;
import ImplementazioniDAO.Utils.ResultSetMapper;
import database.ConnessioneDatabase;
import exception.BusinessError;
import exception.ErrorType;
import model.RigaOrdine;
import model.StatoOrdine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class OrdiniImpDAO {
    private Connection connection;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    /**
     * @author Tiziano
     */
    public OrdiniImpDAO(){
        try{
            connection = ConnessioneDatabase.getInstance().connection;
        } catch(SQLException ex){
            ex.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazioni Ordine

    /**
     * @author Tiziano
     *
     * @param codice_ordine the codice ordine
     * @return
     */
    public static EntityWitchId<RigaOrdine,Integer> get_righeOrdine(String codice_ordine){
        EntityWitchId<RigaOrdine,Integer> righeOrdineMap = new EntityWitchId<RigaOrdine,Integer>();
        String sql = "SELECT * FROM RigaOrdine r JOIN Prodotto p ON r.id_prodotto = p.id_prodotto " +
                     "WHERE r.codice_ordine = ?;";

        Connection con = null;
        try{
            con = ConnessioneDatabase.getInstance().connection;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }

        try(PreparedStatement query = con.prepareStatement(sql)){
            query.setString(1,codice_ordine);

            try(ResultSet result = query.executeQuery()){
                while(result.next()){
                    righeOrdineMap.entitys.add(ResultSetMapper.converti_result_into_rigaOrdine(result));
                    righeOrdineMap.ids.add(Integer.valueOf(result.getInt("id_prodotto")));
                }
            }
            return righeOrdineMap;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    /**
     * @author Tiziano
     * Metodo dell'aggiornameto dello stato dell'ordine
     *
     * @param codice_ordine the codice ordine
     * @param stato         the stato
     */
    public static  void aggiorna_stato_ordine(String codice_ordine, StatoOrdine stato){
        Connection con = null;
        String sql = "UPDATE Ordine SET stato = ? WHERE codice_ordine = ?;";

        try{
            con = ConnessioneDatabase.getInstance().connection;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }

        try(PreparedStatement query = con.prepareStatement(sql)){
            query.setInt(1,stato.ordinal());
            query.setString(2,codice_ordine);
            query.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }


}
