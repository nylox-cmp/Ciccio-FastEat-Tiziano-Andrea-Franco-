package database;

import exception.BusinessError;
import exception.ErrorType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnessioneDatabase {
    private static ConnessioneDatabase instance;
    public Connection connection = null;
    private String nome = "postgres";
    private String password = "root";
    private String url = "jdbc:postgresql://localhost:5432/FoodDelivery";
    private String driver = "org.postgresql.Driver";

    private ConnessioneDatabase() throws SQLException {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, nome, password);

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new BusinessError(ErrorType.IMPOSSIBILE_CONETTERSI_DATABASE);
        }

    }

    /**
     * @author Proffessore (che mette 30 a tutto il gruppo)
     * Gets instance.
     *
     * @return the instance
     * @throws SQLException the sql exception
     */
    public static ConnessioneDatabase getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnessioneDatabase();
        } else if (instance.connection.isClosed()) {
            instance = new ConnessioneDatabase();
        }
        return instance;
    }

}