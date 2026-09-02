package ImplementazioniDAO;

import ImplementazioniDAO.Utils.EntityWitchId;
import ImplementazioniDAO.Utils.ResultSetMapper;
import database.ConnessioneDatabase;
import exception.BusinessError;
import exception.ErrorType;
import model.RigaOrdine;
import model.StatoOrdine;
import org.postgresql.core.SqlCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdiniImpDAO {
    private Connection connection;

    public OrdiniImpDAO(){
        try{
            connection = ConnessioneDatabase.getInstance().connection;
        } catch(SQLException ex){
            ex.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    public static EntityWitchId<RigaOrdine,ArrayList<String>> get_righeOrdine(String codice_ordine){
        EntityWitchId<RigaOrdine,ArrayList<String>> righeOrdineMap = new EntityWitchId<RigaOrdine,ArrayList<String>>();
        String sql = "SELECT * FROM RigaOrdine r JOIN Prodotto p ON r.id_prodotto = p.id_prodotto WHERE r.codice_ordine = ?;";

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
                    ArrayList<String> id_riga = new ArrayList<>();
                    righeOrdineMap.entitys.add(ResultSetMapper.converti_result_into_rigaOrdine(result));

                    id_riga.add(result.getString("codice_ordine"));
                    id_riga.add(String.valueOf(result.getInt("id_prodotto")));

                    righeOrdineMap.ids.add(id_riga);
                }
            }
            return righeOrdineMap;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

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
