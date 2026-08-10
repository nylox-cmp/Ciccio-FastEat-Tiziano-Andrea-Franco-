package ImplementazioniDAO;

import ImplementazioniDAO.Utils.EntityWitchId;
import ImplementazioniDAO.Utils.ResultSetMapper;
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
    public void crea_ordine(String codice_ordine, double costo, StatoOrdine stato_ordine, String indirizzo, LocalDate date){
        //Operazione che deve creare l'ordine, se il tipo LocalDate da problemi convertilo in un tipo compatibile per il DBMS di postgres nel fare query.set
    }

    @Override
    public void annulla_ordine(String codice_ordine){
        //operazione di UPDATE dello Ordine allo StatoOrdine.ANNULATO
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
        //operazione che deve aggiornare lo stato del ordine
    }

    @Override
    public void applica_sconto(String codice_ordine,double costo){
        //operazione che deve aggiornare il costo dell'ordine
    }

    //________________________________________________________________________________________________________________________________________________
    // Operazione Gestione RigheOrdine

    @Override
    public void aggiungi_riga(String codice_ordine,int id_prodotto,int quantita){
        //operazione in cui dovrai inserire una nuova RigaOrdine
    }

    @Override
    public void rimuovi_riga(String codice_ordine,int id_prodotto){
        //operazione in cui dovrai cancellare la RigaOrdine
    }

    @Override
    public void aggiorna_quantita_rigaOrdine(String codice_ordine,int id_prodotto,int quantita){
        //operazione in cui dovrai fare un update della quantita della RigaOrdine
    }



    //________________________________________________________________________________________________________________________________________________
    // Metodi Get

    @Override
    public ArrayList<Ristorante> get_ristoranti(){
        //operazione in cui dovrai prendere una lista di ristoranti in cui esiste almeno un prodotto
        //per la conversione del ResultSet in una Classe utilizzare il metodo presente all'interno della Classe ResultSetMapper cosi anche per l'ordine
        return null;
    }

    @Override
    public ArrayList<Ordine> get_ordini_cliente(String nickname){
        //operazione in cui dovrai prendere tutti gli ordini del Cliente (in tutti gli StatiOrdine) e ordinarli in ordine crescente
        //per la conversione del ResultSet in una Classe utilizzare il metodo presente all'interno della Classe ResultSetMapper cosi anche per l'ordine
        return null;
    }

    public EntityWitchId<RigaOrdine,ArrayList<String>> get_contenuto_ordine(String codice_ordine){
        EntityWitchId<RigaOrdine,ArrayList<String>> rigaOrdineMap = new EntityWitchId<RigaOrdine,ArrayList<String>>();

        String sql = "SELECT * FROM RigaOrdine ro JOIN Prodotto p ON ro.id_prodotto = p.id_prodotto WHERE ro.codice_ordine = ?;";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1,codice_ordine);

            try(ResultSet result = query.executeQuery()){
                while(result.next()){
                    ArrayList<String> chiave_composta = new ArrayList<String>();
                    rigaOrdineMap.entitys.add(ResultSetMapper.converti_result_into_rigaOrdine(result));

                    chiave_composta.add(result.getString("codice_ordine"));
                    chiave_composta.add(String.valueOf(result.getString("id_prodotto")));
                    rigaOrdineMap.ids.add(chiave_composta);
                }
            }
            return rigaOrdineMap;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }
    }
}
