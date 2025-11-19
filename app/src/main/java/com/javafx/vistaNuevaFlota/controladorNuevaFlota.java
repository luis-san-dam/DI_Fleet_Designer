package com.javafx.vistaNuevaFlota;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.javafx.bbdd.BBDD;
import com.javafx.modelos.Sesion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class controladorNuevaFlota implements Initializable {

    Connection conexion;
    Statement st;
    ResultSet rs;

    @FXML
    private TextField cantidadnaves;

    @FXML
    private VBox contenedorNaves;

    @FXML
    private HBox fila;

    @FXML
    private ToggleGroup flotaSeleccionada;

    @FXML
    private ComboBox<String> naveEscogida;

    @FXML
    private TextField nombreFlota;

    @FXML
    private RadioButton radioImperio;

    @FXML
    private RadioButton radioMercenarios;

    @FXML
    private RadioButton radioPiratas;

    @FXML
    private RadioButton radioRebeldes;

    @FXML
    private RadioButton radioRepublica;

    @FXML
    private RadioButton radioSeparatistas;

    @FXML
    void botonCancelar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public ObservableList<String> obtenerNavesUsuario(int idUsuario) {
        ObservableList<String> naves = FXCollections.observableArrayList();

        String query = "SELECT nombre FROM flota WHERE id_usuario = ?";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                naves.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return naves;
    }

    private void crearNuevaFila(ComboBox<String> comboAnterior, ObservableList<String> opcionesTotales) {
        String seleccion = comboAnterior.getValue();
        if (seleccion == null || seleccion.isEmpty())
            return;

        ObservableList<String> nuevasOpciones = FXCollections.observableArrayList(opcionesTotales);

        if (nuevasOpciones.isEmpty())
            return;

        HBox nuevaFila = new HBox(10);
        ComboBox<String> nuevoCombo = new ComboBox<>(nuevasOpciones);
        TextField nuevaCantidad = new TextField();
        nuevaFila.getChildren().addAll(nuevoCombo, nuevaCantidad);
        contenedorNaves.getChildren().add(nuevaFila);
        for (Node node : contenedorNaves.getChildren()) {
            if (node instanceof HBox) {
                HBox hbox = (HBox) node;
                for (Node n : hbox.getChildren()) {
                    if (n instanceof ComboBox) {
                        ComboBox<String> cb = (ComboBox<String>) n;
                        if (cb.getValue() != null) {
                            nuevasOpciones.remove(cb.getValue());
                        }
                    }
                }
            }
        }
        nuevoCombo.setOnAction(e -> crearNuevaFila(nuevoCombo, opcionesTotales));
    }

    @FXML
    void botonNuevaFlota(ActionEvent event) {

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
        int idUsuario = Sesion.getUsuario().getId_usuario();
        ObservableList<String> navesUsuario = obtenerNavesUsuario(idUsuario);
        naveEscogida.setItems(FXCollections.observableArrayList(navesUsuario));

        naveEscogida.setOnAction(e -> crearNuevaFila(naveEscogida, navesUsuario));
    }

}
