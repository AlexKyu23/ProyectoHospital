package Datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    private static Database theInstance;
    private Connection cnx;

    // Cambia estos valores según tu instalación de MySQL
    private final String SERVER = "localhost";
    private final String PORT = "3306";
    private final String DATABASE = "HospitalProy";
    private final String USER = "root";
    private final String PASSWORD = "root"; // Cambia por tu contraseña

    private Database() {
        cnx = getConnection();
    }

    public static Database instance() {
        if (theInstance == null) {
            theInstance = new Database();
        }
        return theInstance;
    }

    private Connection getConnection() {
        try {
            String URL_conexion = "jdbc:mysql://" + SERVER + ":" + PORT + "/" +
                    DATABASE + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            return DriverManager.getConnection(URL_conexion, USER, PASSWORD);
        } catch (Exception e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            System.exit(-1);
        }
        return null;
    }


    public PreparedStatement prepareStatement(String statement) throws SQLException {
        return cnx.prepareStatement(statement);
    }

    public int executeUpdate(PreparedStatement statement) {
        try {
            statement.executeUpdate();
            return statement.getUpdateCount();
        } catch (SQLException ex) {
            System.err.println("Error en executeUpdate: " + ex.getMessage());
            return 0;
        }
    }

    public ResultSet executeQuery(PreparedStatement statement) {
        try {
            return statement.executeQuery();
        } catch (SQLException ex) {
            System.err.println("Error en executeQuery: " + ex.getMessage());
        }
        return null;
    }

    public void close() {
        try {
            if (cnx != null && !cnx.isClosed()) {
                cnx.close();
            }
        } catch (SQLException e) {
            System.err.println("Error cerrando la conexión: " + e.getMessage());
        }
    }
}