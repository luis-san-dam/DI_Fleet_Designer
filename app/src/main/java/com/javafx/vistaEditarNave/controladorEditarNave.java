package com.javafx.vistaEditarNave;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.javafx.bbdd.BBDD;
import com.javafx.modelos.Flota;
import com.javafx.modelos.Nave;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class controladorEditarNave implements Initializable {

    Connection conexion;
    Statement st;
    ResultSet rs;
    Nave nave;
    Flota flota;

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
    private TextField nombreNave;

    @FXML
    private ImageView preview;

    @FXML
    private ComboBox<String> propulsion;

    @FXML
    private ComboBox<String> sistemaDefensivo;

    @FXML
    void botonCancelar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML //TODO
    void botonEditar(ActionEvent event) {
        nave.setNombre(nombreNave.getText());
        nave.setPropulsion(propulsion.getPromptText());
        nave.setSistema_defensivo(sistemaDefensivo.getPromptText());
        nave.setArmadura(armadura.getPromptText());
        nave.setEscudo(escudo.getPromptText());

        String armamentos = armamento1 + "-" + armamento2 + "-" + armamento3 + "-" + armamento4
         + "-" + armamento5 + "-" + armamento6 + "-" + armamento7 + "-" + armamento8;

        nave.setArmamento(armamentos);
        //nave.setImagen(preview.getText()); //TODO

        ((Stage) nombreNave.getScene().getWindow()).close();
    }

    @FXML //TODO
    void botonInsertarImagen(ActionEvent event) {

    }

    public void cargarNave(Nave nave) {
        this.nave = nave;

        nombreNave.setText(nave.getNombre());
        propulsion.setValue(nave.getPropulsion());
        sistemaDefensivo.setValue(nave.getSistema_defensivo());
        armadura.setValue(nave.getArmadura());
        escudo.setValue(nave.getEscudo());

        String[] armamentos = nave.getArmamento().split("-");

        List<ComboBox<String>> combos = Arrays.asList(
            armamento1, armamento2, armamento3, armamento4,
            armamento5, armamento6, armamento7, armamento8
        );

        for (int i = 0; i < combos.size(); i++) {
            if (i < armamentos.length) {
                combos.get(i).setValue(armamentos[i]);
            } else {
                combos.get(i).setValue(null);
            }
        }
        
        //preview.setValue(nave.getImagen()); //TODO
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