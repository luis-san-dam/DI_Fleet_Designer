package com.javafx.vistaLogin;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class controladorLogin implements Initializable{

    Connection conexion;
    ResultSet rs;

    @FXML
    private PasswordField contrasenia;

    @FXML
    private TextField usuario;

    @FXML
    void botonLogin(ActionEvent event) {

        if (credencialesCorrectas()) {

            try {
                // Ya tenemos Sesion.getUsuario() con el usuario activo
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaDesign.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Fleet Designer");
                stage.show();

            } catch (IOException e) {
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

    private boolean credencialesCorrectas() {

    String user = usuario.getText().trim();
    String pass = contrasenia.getText().trim();

    if (user.isEmpty() || pass.isEmpty()) {
        return false;
    }

    String query = "SELECT * FROM usuario WHERE nombre_usuario = ? AND contrasenia = ?";

    try (PreparedStatement pst = conexion.prepareStatement(query)) {

        pst.setString(1, user);
        pst.setString(2, pass);

        rs = pst.executeQuery();

        if (rs.next()) {
            com.javafx.modelos.Usuario u = new com.javafx.modelos.Usuario(
                rs.getInt("id_usuario"),
                rs.getString("nombre_usuario"),
                rs.getString("email"),
                rs.getString("contrasenia"),
                rs.getBoolean("es_admin")
            );

            com.javafx.modelos.Sesion.iniciarSesion(u);
            return true;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
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
        conexion = BBDD.getInstance().getConnection();

        if (conexion == null) {
            System.out.println("ERROR: No se pudo obtener la conexión a la BD.");
        }
    }

}