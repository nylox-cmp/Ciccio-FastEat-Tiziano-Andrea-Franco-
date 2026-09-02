package ImplementazioniDAO;

import ImplementazioniDAO.Utils.ResultSetMapper;
import dao.DipendenteDAO;
import database.ConnessioneDatabase;
import exception.BusinessError;
import exception.ErrorType;
import model.*;

import java.sql.*;
import java.util.ArrayList;

public class DipendenteImpDAO implements DipendenteDAO {
    private Connection connection;

    //________________________________________________________________________________________________________________________________________________
    // Costruttore

    public DipendenteImpDAO(){
        try{
            connection = ConnessioneDatabase.getInstance().connection;
        } catch(SQLException ex){
            ex.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Dipendente

    @Override
    public void licenziati(String nickname){
        String sql = "DELETE FROM Dipendente WHERE nickname = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,nickname);
            query.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione RiderPropostiOrdine

    @Override
    public void accetta_rider(String codice_ordine,String nickname_rider){
        String sql = "UPDATE RiderPropostiConsegna SET ordine_preso_a_carico = ? WHERE codice_ordine = ? AND nickname_rider = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setBoolean(1,true);
            query.setString(2,codice_ordine);
            query.setString(3,nickname_rider);
            query.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public void rifiuta_rider(String codice_ordine,String nickname_rider){
        String sql = "DELETE FROM RiderPropostiConsegna WHERE codice_ordine = ? AND nickname_rider = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,codice_ordine);
            query.setString(2,nickname_rider);
            query.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Gestione Dipendenti Subordinati

    @Override
    public void modifica_ruolo_dipedente(String nickaname_subordinato, Ruolo ruolo){
        String sql = "UPDATE Dipendente SET ruolo = ? WHERE nickname = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setInt(1,ruolo.ordinal());
            query.setString(2,nickaname_subordinato);
            query.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public void licenzia_dipedente(String nickname_subordinato){
        String sql = "DELETE FROM Dipendente WHERE nickname = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,nickname_subordinato);
            query.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    //________________________________________________________________________________________________________________________________________________
    // Metodi Get

    public Ristorante get_ristorante(String nickname){
        String codice_ristorante = get_codice_ristorante(nickname);
        String sql = "SELECT * FROM Ristorante WHERE codice_ristorante = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,codice_ristorante);
            try(ResultSet result = query.executeQuery()){
                if(result.next())
                    return ResultSetMapper.converti_result_into_ristorante(result);
            }
            return null;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    public String get_codice_ristorante(String nickname){
        String sql = "SELECT codice_ristorante FROM Dipendente d WHERE d.nickname = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,nickname);

            try (ResultSet result = query.executeQuery()){
                if(result.next())
                    return result.getString("codice_ristorante");
            }
            return null;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public ArrayList<Dipendente> get_subordinati(String codice_ristorante,Ruolo ruolo){
        ArrayList<Dipendente> subordinati = new ArrayList<Dipendente>();
        String sql = "SELECT * FROM Dipendente d JOIN Utente u ON d.nickname = u.nickname WHERE ruolo < ? AND codice_ristorante = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setInt(1,ruolo.ordinal());
            query.setString(2,codice_ristorante);

            try(ResultSet result = query.executeQuery()){
                while(result.next()){
                    subordinati.add(ResultSetMapper.converti_result_into_dipedente(result));
                }
            }
            return subordinati;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public ArrayList<Ordine> get_ordini_ristorante(String codice_ristorante){
        ArrayList<Ordine> ordini = new ArrayList<Ordine>();
        String sql = "SELECT o.*, ris.*, u.*, rid.mezzo_trasporto FROM Ordine o " +
                     "JOIN Ristorante ris ON o.codice_ristorante = ris.codice_ristorante " +
                     "LEFT JOIN RiderPropostiConsegna rpc ON o.codice_ordine = rpc.codice_ordine AND rpc.ordine_preso_a_carico = TRUE " +
                     "LEFT JOIN Utente u ON u.nickname = rpc.nickname_rider " +
                     "LEFT JOIN Rider rid ON rid.nickname = u.nickname " +
                     "WHERE o.codice_ristorante = ? AND o.stato IN (1,2,3,4,5,6,7) ORDER BY o.stato ASC;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1, codice_ristorante);

            try(ResultSet result = query.executeQuery()){
                while(result.next()){
                    ordini.add(ResultSetMapper.converti_reuslt_into_ordine(result));
                }
            }
            return ordini;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

    @Override
    public ArrayList<Rider> get_rider_proposti_consegna(String codice_ordine){
        ArrayList<Rider> rider_proposti = new ArrayList<Rider>();
        String sql = "SELECT * FROM RiderPropostiConsegna rpc JOIN Utente u ON rpc.nickname_rider = u.nickname JOIN Rider r ON r.nickname = u.nickname " +
                     "WHERE codice_ordine = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,codice_ordine);

            try(ResultSet result = query.executeQuery()){
                while(result.next()){
                    rider_proposti.add(ResultSetMapper.converti_result_into_rider(result));
                }
            }
            return rider_proposti;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }

}
