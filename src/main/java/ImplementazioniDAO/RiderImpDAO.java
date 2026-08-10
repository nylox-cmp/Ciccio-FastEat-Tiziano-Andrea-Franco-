package ImplementazioniDAO;

import dao.RiderDAO;
import database.ConnessioneDatabase;
import exception.BusinessError;
import exception.ErrorType;
import model.Ordine;
import model.StatoOrdine;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        //operazione che deve aggiornare lo StatoOrdine del Ordine
    }

    @Override
    public void cancella_richiesta_approvazione_consegna(String nickname_rider,String codice_ordine){
        //operazione che deve cancellare la richiesta del rider
    }


    //________________________________________________________________________________________________________________________________________________
    // Metodi Get

    @Override
    public ArrayList<Ordine> get_ordini_proposti(){
        //metodo che deve prendere tutti gli ordini presenti nello StatoOrdine.PREPARAZIONE è ritornarli
        //utilizzare la classe ResultSetMapper per la conversione dei risulati in classi
        return null;
    }

    @Override
    public ArrayList<Ordine> get_ordini_da_consegnare(String nickname){
        //metodo che deve prendere tutti gli ordini RiderPropostiConsegna in cui ordine_preso_a_carico = true con
        //l'ordine presente negli StatiOrdine Preparazione,PRONTO_RITIRO_RIDER,IN_CONSEGNA,CONFERMA_CONSEGNA_RIDER,CONFERMA_CONSEGNA_CLIENTE
        //ordinandoli in ordine crescente in base al numero dello stato del Ordine
        return null;
    }
}
