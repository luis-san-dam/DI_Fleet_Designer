package com.javafx.vistaOpciones;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.javafx.bbdd.BBDD;
import com.javafx.modelos.Sesion;
import com.javafx.modelos.Usuario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class controladorOpciones implements Initializable {

    Connection conexion;
    Statement st;
    ResultSet rs;

    @FXML
    private TextField datoContraseniaRepetida;

    @FXML
    private TextField datoEmailUsuario;

    @FXML
    private TextField datoNombreUsuario;

    @FXML
    private TextField datoNuevaContrasenia;

    @FXML
    void botonCancelar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private Stage stagePrincipal;

    public void setStagePrincipal(Stage stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
    }

    @FXML
    void botonCerrarSesion(ActionEvent event) {

        Sesion.cerrarSesion();

        Stage modal = (Stage) ((Node) event.getSource()).getScene().getWindow();
        modal.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaLogin.fxml"));
            Parent root = loader.load();

            stagePrincipal.setScene(new Scene(root));
            stagePrincipal.setTitle("Fleet Designer");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void botonEditar(ActionEvent event) {

    }

    @FXML
    void cambiarColor(ActionEvent event) {

    }

    private void cargarDatosUsuario() {
        Usuario u = Sesion.getUsuario();

        if (u != null) {
            datoNombreUsuario.setText(u.getNombre_usuario());
            datoEmailUsuario.setText(u.getEmail());
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
        cargarDatosUsuario();
    }

}
