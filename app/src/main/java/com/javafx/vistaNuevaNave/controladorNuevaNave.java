package com.javafx.vistaNuevaNave;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.javafx.bbdd.BBDD;
import com.javafx.modelos.PiezasNaves;
import com.javafx.modelos.Sesion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class controladorNuevaNave implements Initializable{

    Connection conexion;
    Statement st;
    ResultSet rs;
    String imagenActualBase64;

    @FXML
    private Label aparienciaNave;

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

    @FXML
    void botonNuevaNave(ActionEvent event) {
        ComboBox<String>[] armamentos = new ComboBox[]{
            armamento1, armamento2, armamento3, armamento4,
            armamento5, armamento6, armamento7, armamento8
        };

        boolean camposValidos = PiezasNaves.validarCamposObligatorios(
                armadura, propulsion, sistemaDefensivo, escudo, nombreNave, armamentos);

        boolean imagenValida = imagenActualBase64 != null && !imagenActualBase64.isEmpty();

        // Validar imagen
        if (!imagenValida) {
            aparienciaNave.getStyleClass().add("label-error");
        } else {
            aparienciaNave.getStyleClass().remove("label-error"); // Reset al color original
        }

        // Solo insertar si todos los campos y la imagen son válidos
        if (camposValidos && imagenValida) {
            insertarNave();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void botonInsertarImagen(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );

        File archivo = fc.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (archivo != null) {
            try {
                Image scaledImage = new Image(
                        archivo.toURI().toString(),
                        230,
                        100,
                        false,
                        true
                );

                preview.setImage(scaledImage);

                BufferedImage buffered = SwingFXUtils.fromFXImage(scaledImage, null);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(buffered, "png", baos);
                byte[] imageBytes = baos.toByteArray();

                imagenActualBase64 = Base64.getEncoder().encodeToString(imageBytes);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String obtenerTipoSeleccionado() {
        return ((RadioButton) naveSeleccionada.getSelectedToggle()).getText();
    }

    private String obtenerArmamento() {
        StringBuilder armamento = new StringBuilder();

        ComboBox<String>[] armamentos = new ComboBox[]{
            armamento1, armamento2, armamento3, armamento4,
            armamento5, armamento6, armamento7, armamento8
        };

        for (ComboBox<String> cb : armamentos) {
            if (cb.getValue() != null && !cb.getValue().isEmpty()) {
                if (armamento.length() > 0) armamento.append("-");
                armamento.append(cb.getValue());
            }
        }

        return armamento.toString();
    }

    private void insertarNave() {
        String sql = "INSERT INTO nave (id_usuario, nombre, tipo, propulsion, sistema_defensivo, armadura, escudo, armamento, imagen) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {

            pst.setInt(1, Sesion.getUsuario().getId_usuario());
            pst.setString(2, nombreNave.getText());
            pst.setString(3, obtenerTipoSeleccionado());
            pst.setString(4, propulsion.getValue());
            pst.setString(5, sistemaDefensivo.getValue());
            pst.setString(6, armadura.getValue());
            pst.setString(7, escudo.getValue());
            pst.setString(8, obtenerArmamento());
            pst.setString(9, imagenActualBase64);

            pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println("ERROR INSERTANDO NAVE: " + e.getMessage());
        }
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

        ObservableList<String> armaduras = FXCollections.observableArrayList(PiezasNaves.getArmaduras());
        armadura.setItems(armaduras);
        PiezasNaves.enableAutoComplete(armadura, armaduras);
        PiezasNaves.validateComboBoxValue(armadura, armaduras, true);
        PiezasNaves.quitarErrorAlEscribir(armadura);


        ComboBox<String>[] armamentos = new ComboBox[]{
            armamento1, armamento2, armamento3, armamento4,
            armamento5, armamento6, armamento7, armamento8
        };

        ObservableList<String> armamentosItems = FXCollections.observableArrayList(PiezasNaves.getArmamento());
        for (int i = 0; i < armamentos.length; i++) {
            ComboBox<String> cb = armamentos[i];
            cb.setItems(armamentosItems);
            PiezasNaves.enableAutoComplete(cb, armamentosItems);
            PiezasNaves.validateComboBoxValue(cb, armamentosItems, i == 0); // solo armamento1 obligatorio
            PiezasNaves.quitarErrorAlEscribir(cb);
        }

        ObservableList<String> escudos = FXCollections.observableArrayList(PiezasNaves.getEscudos());
        escudo.setItems(escudos);
        PiezasNaves.enableAutoComplete(escudo, escudos);
        PiezasNaves.validateComboBoxValue(escudo, escudos, true);
        PiezasNaves.quitarErrorAlEscribir(escudo);

        ObservableList<String> propulsiones = FXCollections.observableArrayList(PiezasNaves.getPropulsiones());
        propulsion.setItems(propulsiones);
        PiezasNaves.enableAutoComplete(propulsion, propulsiones);
        PiezasNaves.validateComboBoxValue(propulsion, propulsiones, true);
        PiezasNaves.quitarErrorAlEscribir(propulsion);

        ObservableList<String> sistemas = FXCollections.observableArrayList(PiezasNaves.getSistemaDefensivo());
        sistemaDefensivo.setItems(sistemas);
        PiezasNaves.enableAutoComplete(sistemaDefensivo, sistemas);
        PiezasNaves.validateComboBoxValue(sistemaDefensivo, sistemas, true);
        PiezasNaves.quitarErrorAlEscribir(sistemaDefensivo);

        PiezasNaves.quitarErrorAlEscribir(nombreNave);
    }
}
