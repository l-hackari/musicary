package musicary.model.server;

import java.sql.*;

public class DBserver {

    private static Connection connection = null;

    String serverName = "localhost";
    String mydatabase = "musicary";
    String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

    String username = "root";
    String password = "";

    public void connect(){

        String driverName = "com.mysql.jdbc.Driver";

        try {
            Class.forName(driverName);
            System.out.println("Driver caricati correttamente ");
        } catch (ClassNotFoundException e) {
            System.err.println("Errore nel caricamento dei driver ");
        }

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("connessione al db stabilita con successo ");

        } catch (SQLException e) {
            System.err.println("Errore connessione al db ");
        }
    }

    public PreparedStatement statement (String query) {
       PreparedStatement statement = null;
        try {
           statement = connection.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Errore nella creazione dello statement ");
        }

        return statement;

    }




}
