package com.javafx.vistaEditarNave;

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
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.javafx.bbdd.BBDD;
import com.javafx.modelos.Nave;
import com.javafx.modelos.PiezasNaves;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class controladorEditarNave implements Initializable {

    Connection conexion;
    Statement st;
    Nave nave;
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
    private TextField nombreNave;

    @FXML
    private Label nombreValidador;

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

    @FXML
    void botonEditar(ActionEvent event) {
        ComboBox<String>[] armamentos = new ComboBox[] {
                armamento1, armamento2, armamento3, armamento4,
                armamento5, armamento6, armamento7, armamento8
        };

        boolean camposValidos = PiezasNaves.validarCamposObligatorios(
                armadura, propulsion, sistemaDefensivo, escudo, nombreNave, armamentos);

        boolean imagenValida = imagenActualBase64 != null && !imagenActualBase64.isEmpty();

        boolean nombreValido = nombreNaveDisponible();

        if (!nombreValido) {
            nombreNave.getStyleClass().add("text-field-error");
            nombreValidador.setText("Nombre de nave ya existe");
            nombreValidador.getStyleClass().add("label-error");
        } else {
            nombreNave.getStyleClass().removeAll("text-field-error");
            nombreValidador.setText("Nombre:");
            nombreValidador.getStyleClass().removeAll("label-error");
        }

        if (!imagenValida) {
            aparienciaNave.getStyleClass().add("label-error");
        } else {
            aparienciaNave.getStyleClass().remove("label-error");
        }

        if (!nombreValido || !imagenValida || !camposValidos) {
            return;
        }

        StringBuilder armamento = new StringBuilder();
        for (ComboBox<String> cb : armamentos) {
            if (cb.getValue() != null && !cb.getValue().isEmpty()) {
                if (armamento.length() > 0)
                    armamento.append("-");
                armamento.append(cb.getValue());
            }
        }

        String sql = "UPDATE nave SET nombre=?, propulsion=?, sistema_defensivo=?, armadura=?, escudo=?, armamento=?, imagen=? WHERE id_nave=?";
        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, nombreNave.getText());
            pst.setString(2, propulsion.getValue());
            pst.setString(3, sistemaDefensivo.getValue());
            pst.setString(4, armadura.getValue());
            pst.setString(5, escudo.getValue());
            pst.setString(6, armamento.toString());
            pst.setString(7, imagenActualBase64);
            pst.setInt(8, nave.getId_nave());

            pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println("ERROR EDITANDO NAVE: " + e.getMessage());
        }

        ((Stage) nombreNave.getScene().getWindow()).close();
    }

    @FXML
    void botonInsertarImagen(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        File archivo = fc.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (archivo != null) {
            try {
                Image scaledImage = new Image(archivo.toURI().toString(), 230, 100, false, true);
                preview.setImage(scaledImage);

                BufferedImage buffered = SwingFXUtils.fromFXImage(scaledImage, null);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(buffered, "png", baos);
                imagenActualBase64 = Base64.getEncoder().encodeToString(baos.toByteArray());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void cargarNave(Nave nave) {
        this.nave = nave;

        nombreNave.setText(nave.getNombre());
        propulsion.setValue(nave.getPropulsion());
        sistemaDefensivo.setValue(nave.getSistema_defensivo());
        armadura.setValue(nave.getArmadura());
        escudo.setValue(nave.getEscudo());
        imagenActualBase64 = nave.getImagen();

        if (imagenActualBase64 != null && !imagenActualBase64.isEmpty()) {
            byte[] imageBytes = Base64.getDecoder().decode(imagenActualBase64);
            Image img = new Image(new java.io.ByteArrayInputStream(imageBytes));
            preview.setImage(img);
        }

        String[] armamentos = nave.getArmamento().split("-");

        List<ComboBox<String>> combos = Arrays.asList(
                armamento1, armamento2, armamento3, armamento4,
                armamento5, armamento6, armamento7, armamento8);

        for (int i = 0; i < combos.size(); i++) {
            if (i < armamentos.length) {
                combos.get(i).setValue(armamentos[i]);
            } else {
                combos.get(i).setValue(null);
            }
        }
    }

    private boolean nombreNaveDisponible() {
        String sql = "SELECT COUNT(*) AS total FROM nave WHERE nombre=? AND id_nave<>?";
        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, nombreNave.getText().trim());
            pst.setInt(2, nave.getId_nave());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("total") == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

        nombreNave.textProperty().addListener((obs, oldText, newText) -> {
            nombreNave.getStyleClass().remove("text-field-error");
            nombreValidador.setText("Nombre:");
            nombreValidador.getStyleClass().removeAll("label-error");
        });

        ObservableList<String> armamentosItems = FXCollections.observableArrayList(PiezasNaves.getArmamento());
        ComboBox<String>[] armamentos = new ComboBox[] {
                armamento1, armamento2, armamento3, armamento4,
                armamento5, armamento6, armamento7, armamento8
        };

        for (int i = 0; i < armamentos.length; i++) {
            armamentos[i].setItems(armamentosItems);
            PiezasNaves.enableAutoComplete(armamentos[i], armamentosItems);
            PiezasNaves.validateComboBoxValue(armamentos[i], armamentosItems, i == 0);
            PiezasNaves.quitarErrorAlEscribir(armamentos[i]);
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