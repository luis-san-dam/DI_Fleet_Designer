package com.javafx.vistaLogin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        scene.getStylesheets().add(getClass().getResource("/estilos/estiloLogin.css").toExternalForm());

        if(!primeraEscena.getIcons().contains(new Image(
            getClass().getResource("/icons/IconoAPP.png").toExternalForm()
        ))){
            primeraEscena.getIcons().add(new Image(
            getClass().getResource("/icons/IconoAPP.png").toExternalForm()
        ));
        }
        

        primeraEscena.setScene(scene);
        primeraEscena.setResizable(false);
        primeraEscena.setTitle("Fleet Designer");
        primeraEscena.show();
        
        
    }
}