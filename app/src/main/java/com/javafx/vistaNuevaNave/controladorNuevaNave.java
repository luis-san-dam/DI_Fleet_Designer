package com.javafx.vistaNuevaNave;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class controladorNuevaNave implements Initializable{

    Connection conexion;
    Statement st;
    ResultSet rs;

    @FXML
    private ComboBox<String> armadura;

    @FXML
    private ComboBox<String> armamento1;

    @FXML
    private ComboBox<String> armamento2;

    @FXML
    private ComboBox<String> armamento3;

    @FXML
    private ComboBox<String> armamento4;

    @FXML
    private ComboBox<String> armamento5;

    @FXML
    private ComboBox<String> armamento6;

    @FXML
    private ComboBox<String> armamento7;

    @FXML
    private ComboBox<String> armamento8;

    @FXML
    private ComboBox<String> escudo;

    @FXML
    private ToggleGroup naveSeleccionada;

    @FXML
    private TextField nombreNave;

    @FXML
    private ImageView preview;

    @FXML
    private ComboBox<String> propulsion;

    @FXML
    private RadioButton radioAcorazado;

    @FXML
    private RadioButton radioColoso;

    @FXML
    private RadioButton radioCorveta;

    @FXML
    private RadioButton radioCrucero;

    @FXML
    private RadioButton radioDestructor;

    @FXML
    private RadioButton radioFragata;

    @FXML
    private RadioButton radioInsignia;

    @FXML
    private RadioButton radioTitan;

    @FXML
    private ComboBox<String> sistemaDefensivo;

    @FXML
    void botonCancelar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML //TODO
    void botonEditar(ActionEvent event) {

    }

    @FXML //TODO
    void botonInsertarImagen(ActionEvent event) {

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
