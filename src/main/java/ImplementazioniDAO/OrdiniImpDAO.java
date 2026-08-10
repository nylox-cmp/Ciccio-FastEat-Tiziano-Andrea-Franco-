package ImplementazioniDAO;

import ImplementazioniDAO.Utils.ResultSetMapper;
import dao.OrdiniDAO;
import database.ConnessioneDatabase;
import exception.BusinessError;
import exception.ErrorType;
import model.RigaOrdine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdiniImpDAO implements OrdiniDAO {
    private Connection connection;

    public OrdiniImpDAO(){
        try{
            connection = ConnessioneDatabase.getInstance().connection;
        } catch(SQLException ex){
            ex.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    public ArrayList<RigaOrdine> get_righeOrdine(String codice_ordine){
        ArrayList<RigaOrdine> righe_ordine = new ArrayList<RigaOrdine>();
        String sql = "SELECT * FROM RigaOrdine r JOIN Prodotto p ON r.id_prodotto = p.id_prodotto WHERE r.codice_ordine = ?;";

        try(PreparedStatement query =  connection.prepareStatement(sql)){
            query.setString(1,codice_ordine);

            try(ResultSet result = query.executeQuery()){
                while(result.next()){
                    righe_ordine.add(ResultSetMapper.converti_result_into_rigaOrdine(result));
                }
            }
            return righe_ordine;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

}
