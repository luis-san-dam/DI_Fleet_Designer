package com.javafx.vistaLogin;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.javafx.bbdd.BBDD;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class controladorLogin implements Initializable{

    Connection conexion;
    Statement st;
    ResultSet rs;

    @FXML
    private TextField contrasenia;

    @FXML
    private TextField usuario;

    @FXML
    void botonLogin(ActionEvent event) {
        if (credencialesCorrectas()) {

        try {
            // Cargar datos del usuario desde la BD (usuario con id=1 por ejemplo)
            rs = st.executeQuery("SELECT * FROM usuario WHERE id_usuario = 1");
            if (rs.next()) {
                // Crear objeto Usuario con los datos de la BD
                com.javafx.modelos.Usuario u = new com.javafx.modelos.Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_usuario"),
                        rs.getString("email"),
                        rs.getString("contrasenia"),
                        rs.getBoolean("es_admin")
                );

                // Guardar usuario en la sesión
                com.javafx.modelos.Sesion.iniciarSesion(u);

                // Abrir la ventana principal
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaDesign.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Fleet Designer");
                stage.show();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Las credenciales no son correctas");
            alert.showAndWait();
        }
    }

    @FXML
    void botonRegistrate(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaRegistro.fxml"));
        Parent root = loader.load();

        Stage modal = new Stage();
        modal.setTitle("Fleet Designer");
        modal.setScene(new Scene(root));


        modal.initModality(Modality.APPLICATION_MODAL);

        Stage parentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        modal.initOwner(parentStage);
        modal.showAndWait();

    } catch (IOException e) {
        e.printStackTrace();
    }
}

    private boolean credencialesCorrectas() { //TODO
        return true;
    }

    @FXML
    void botonSalir(ActionEvent event) {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close(); 
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            System.exit(0);
        } catch (SQLException e) {
            System.out.println("No se ha podido cerrar la conexión a base de datos");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            try {
                this.conexion = BBDD.getInstance().getConnection();
                if (this.conexion != null) {
                    this.st = this.conexion.createStatement();
                }
            } catch (SQLException var4) {

            }
    }

}