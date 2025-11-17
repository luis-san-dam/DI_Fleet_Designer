package com.javafx.bbdd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class BBDD {

//Connection con = BBDD.getInstance().getConnection();

    private static BBDD instance;

    private Connection connection;

    private BBDD() {
        try {
            this.connection = createConnection();
        } catch (IOException e) {
            System.out.println("Error al crear la conexión: " + e.getMessage());
        }
    }

    public static synchronized BBDD getInstance() {
        if (instance == null) {
            instance = new BBDD();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private Connection createConnection() throws IOException {
        Properties properties = new Properties();
        String IP;

        try (InputStream input_ip = new FileInputStream("ip.properties")) {
            properties.load(input_ip);
            IP = properties.getProperty("IP", "localhost");
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo encontrar ip.properties, se usa localhost por defecto");
            IP = "localhost";
        }

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("bbdd.properties")) {
            if (input == null) {
                System.out.println("No se pudo encontrar bbdd.properties");
                return null;
            }

            properties.load(input);

            String PORT = properties.getProperty("PORT");
            String BBDD = properties.getProperty("BBDD");
            String USER = properties.getProperty("USER");
            String PWD = properties.getProperty("PWD");

            String url = "jdbc:mariadb://" + IP + ":" + PORT + "/" + BBDD;
            System.out.println("Conectando a: " + url);

            return DriverManager.getConnection(url, USER, PWD);
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Ha ocurrido un error de conexión");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            System.exit(0);
            return null;
        }
    }
}
