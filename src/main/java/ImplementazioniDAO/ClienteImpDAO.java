package ImplementazioniDAO;

import ImplementazioniDAO.Utils.EntityWitchId;
import ImplementazioniDAO.Utils.ResultSetMapper;
import controller.Utils.SessionManager;
import dao.ClienteDAO;
import database.ConnessioneDatabase;
import exception.BusinessError;
import exception.ErrorType;
import model.Ordine;
import model.RigaOrdine;
import model.Ristorante;
import model.StatoOrdine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ClienteImpDAO implements ClienteDAO {
    private Connection connection;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public ClienteImpDAO(){
        try{
            connection = ConnessioneDatabase.getInstance().connection;
        } catch(SQLException ex){
            ex.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    //  Operazione Gestione Ordini


    public boolean codice_ordine_esiste(String codice_ordine){
        String sql = "SELECT * FROM Ordine WHERE codice_ordine = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1,codice_ordine);

            try(ResultSet result = query.executeQuery()){
                if(result.next()) return true;
            }
            return false;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public void crea_ordine(String codice_ordine, double costo, StatoOrdine stato_ordine, String indirizzo, LocalDate date, String codice_ristorante){
        String sql = "INSERT INTO Ordine(codice_ordine,costo,stato,indirizzo,data_ordine,nickname_cliente,codice_ristorante) VALUES(?,?,?,?,?,?,?);";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,codice_ordine);
            query.setDouble(2,costo);
            query.setInt(3,stato_ordine.ordinal());
            query.setString(4,indirizzo);
            query.setDate(5, java.sql.Date.valueOf(date));
            query.setString(6,SessionManager.instance.get_utente().get_nickname());
            query.setString(7,codice_ristorante);
            query.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public void annulla_ordine(String codice_ordine){
        String sql = "UPDATE Ordine SET stato = ? WHERE codice_ordine = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setInt(1,StatoOrdine.ANNULLATO.ordinal());
            query.setString(2,codice_ordine);
            query.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public void conferma_creazione_ordine(String codice_ordine){
        String sql = "UPDATE Ordine SET stato = ? WHERE codice_ordine = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setInt(1,StatoOrdine.PREPARAZIONE.ordinal());
            query.setString(2,codice_ordine);
            query.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            if (e.getMessage() != null && e.getMessage().contains("BEC0")) throw new BusinessError(ErrorType.ORDINE_VUOTO);
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public void conferma_consegna_ordine(String codice_ordine,StatoOrdine stato){
        String sql = "UPDATE Ordine SET stato = ? WHERE codice_ordine = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setInt(1,stato.ordinal());
            query.setString(2,codice_ordine);
            query.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public void applica_sconto(String codice_ordine,double costo){
        String sql = "UPDATE Ordine SET costo = ? WHERE codice_ordine = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setDouble(1,costo);
            query.setString(2,codice_ordine);
            query.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Gestione RigheOrdine

    @Override
    public void aggiungi_riga(String codice_ordine,int id_prodotto,int quantita){
        String sql = "INSERT INTO RigaOrdine(id_prodotto,codice_ordine,quantita) VALUES(?,?,?);";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setInt(1,id_prodotto);
            query.setString(2,codice_ordine);
            query.setInt(3,quantita);
            query.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public void rimuovi_riga(String codice_ordine,int id_prodotto){
        String sql = "DELETE FROM RigaOrdine WHERE codice_ordine = ? AND id_prodotto = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,codice_ordine);
            query.setInt(2,id_prodotto);
            query.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public void aggiorna_quantita_rigaOrdine(String codice_ordine,int id_prodotto,int quantita){
        String sql = "UPDATE RigaOrdine SET quantita = ? WHERE codice_ordine = ? AND id_prodotto = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setInt(1,quantita);
            query.setString(2,codice_ordine);
            query.setInt(3,id_prodotto);
            query.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodi Get

    @Override
    public ArrayList<Ristorante> get_ristoranti(String nickname,String search_nome_o_indirizzo){
        ArrayList<Ristorante> ristoranti = new ArrayList<Ristorante>();
        String sql = "SELECT DISTINCT r.* FROM Ristorante r JOIN Menu m ON r.codice_ristorante = m.codice_ristorante JOIN Prodotto p ON p.id_menu = m.id_menu " +
                     "WHERE r.codice_ristorante NOT IN (SELECT codice_ristorante FROM Dipendente WHERE nickname = ?) AND  (r.nome LIKE ? OR r.indirizzo LIKE ?);";

        search_nome_o_indirizzo = "%" + search_nome_o_indirizzo + "%";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,nickname);
            query.setString(2,search_nome_o_indirizzo);
            query.setString(3,search_nome_o_indirizzo);

            try(ResultSet result = query.executeQuery()){
                while(result.next()){
                    ristoranti.add(ResultSetMapper.converti_result_into_ristorante(result));
                }
            }
            return ristoranti;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public ArrayList<Ordine> get_ordini_cliente(String nickname){
        ArrayList<Ordine> ordini = new ArrayList<Ordine>();
        String sql = "SELECT * FROM Ordine o JOIN Ristorante r ON o.codice_ristorante = r.codice_ristorante WHERE nickname_cliente = ? ORDER BY stato ASC;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,nickname);

            try(ResultSet result = query.executeQuery()){
                while(result.next()){
                    ordini.add(ResultSetMapper.converti_reuslt_into_ordine(result));
                }
            }
            return ordini;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }
}