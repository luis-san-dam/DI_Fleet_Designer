package com.javafx.vistaLogin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Luis Fernando Sánchez Chaves
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primeraEscena) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ventanaLogin.fxml"));

        Scene scene = new Scene(root);
        primeraEscena.setScene(scene);
        primeraEscena.setTitle("Fleet Designer");
        primeraEscena.show();
        
        
    }
}