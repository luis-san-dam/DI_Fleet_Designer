package com.javafx.vistaRegistro;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.javafx.bbdd.BBDD;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class controladorRegistro implements Initializable {

    Connection conexion;
    Statement st;
    ResultSet rs;

    @FXML
    private TextField datoContrasenia;

    @FXML
    private TextField datoContraseniaRepetida;

    @FXML
    private TextField datoEmailUsuario;

    @FXML
    private TextField datoNombreUsuario;

    @FXML
    void botonCancelar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void botonRegistrarse(ActionEvent event) { //TODO

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
                conexion = BBDD.getInstance().getConnection();
                if (conexion != null) {
                    st = conexion.createStatement();
                }
            } catch (SQLException var4) {
                
            }
    }
    
}
