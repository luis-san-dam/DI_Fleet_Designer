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
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    private Label labelNombreFlota;

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

        String query = "SELECT DISTINCT n.nombre " +
                "FROM nave n " +
                "JOIN usuario u ON n.id_usuario = u.id_usuario " +
                "WHERE n.id_usuario = ? OR u.es_admin = 1";
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

        comboAnterior.setDisable(true);

        ObservableList<String> nuevasOpciones = FXCollections.observableArrayList(opcionesTotales);

        for (Node node : contenedorNaves.getChildren()) {
            if (node instanceof HBox hbox) {
                for (Node n : hbox.getChildren()) {
                    if (n instanceof ComboBox cb && cb.getValue() != null) {
                        nuevasOpciones.remove(cb.getValue());
                    }
                }
            }
        }

        if (nuevasOpciones.isEmpty())
            return;

        HBox nuevaFila = new HBox();
        nuevaFila.setMinWidth(600);
        nuevaFila.setMinHeight(35);
        nuevaFila.setPrefHeight(50);
        nuevaFila.setMaxHeight(35);

        Label labelNave = new Label("Naves:");
        HBox.setMargin(labelNave, new Insets(0, 0, 0, 50));
        labelNave.setMinWidth(100);

        ComboBox<String> nuevoCombo = new ComboBox<>(nuevasOpciones);
        nuevoCombo.setMinWidth(150);

        Label labelCantidad = new Label("Cantidad:");
        HBox.setMargin(labelCantidad, new Insets(0, 0, 0, 20));
        labelCantidad.setMinWidth(80);

        TextField nuevaCantidad = new TextField();
        nuevaCantidad.setMinWidth(150);

        nuevaCantidad.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                nuevaCantidad.setText(newVal.replaceAll("[^\\d]", ""));
            }
        });

        nuevaFila.getChildren().addAll(labelNave, nuevoCombo, labelCantidad, nuevaCantidad);
        contenedorNaves.getChildren().add(nuevaFila);

        nuevoCombo.setOnAction(e -> {
            if (nuevoCombo.getValue() != null && !nuevoCombo.getValue().isEmpty()) {
                crearNuevaFila(nuevoCombo, opcionesTotales);
            }
        });

        nuevaCantidad.textProperty().addListener((obs, oldV, newV) -> {
            limpiarError(nuevaCantidad, "text-field-error");
        });
    }

    private void marcarError(Node node, String styleClass) {
        if (!node.getStyleClass().contains(styleClass)) {
            node.getStyleClass().add(styleClass);
        }
    }

    private void limpiarError(Node node, String styleClass) {
        node.getStyleClass().remove(styleClass);
    }

    @FXML
    void botonNuevaFlota(ActionEvent event) {

        boolean valido = true;

        String nombre = nombreFlota.getText().trim();
        boolean nombreDuplicado = false;

        labelNombreFlota.setText("Nombre:");
        limpiarError(labelNombreFlota, "label-error");

        if (nombre.isEmpty()) {
            marcarError(nombreFlota, "text-field-error");
            labelNombreFlota.setText("Nombre requerido");
            marcarError(labelNombreFlota, "label-error");

            valido = false;
        } else {
            limpiarError(nombreFlota, "text-field-error");

            try {
                PreparedStatement pst = conexion.prepareStatement(
                        "SELECT COUNT(*) AS total FROM flota WHERE nombre = ?");
                pst.setString(1, nombre);
                ResultSet rs = pst.executeQuery();

                if (rs.next() && rs.getInt("total") > 0) {
                    nombreDuplicado = true;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                valido = false;
            }

            if (nombreDuplicado) {
                marcarError(nombreFlota, "text-field-error");

                labelNombreFlota.setText("Nombre ya existente");
                marcarError(labelNombreFlota, "label-error");

                valido = false;
            }
        }

        boolean hayNaveValida = false;
        boolean haySeleccionEnAlguna = false;

        for (Node node : contenedorNaves.getChildren()) {
            if (node instanceof HBox hbox) {
                ComboBox<String> cb = null;
                TextField tf = null;

                for (Node n : hbox.getChildren()) {
                    if (n instanceof ComboBox)
                        cb = (ComboBox<String>) n;

                    if (n instanceof TextField)
                        tf = (TextField) n;
                }

                if (cb != null && cb.getValue() != null) {
                    haySeleccionEnAlguna = true;

                    int cant;
                    String txt = tf.getText().trim();

                    if (txt.isEmpty()) {
                        cant = 0;
                    } else {
                        try {
                            cant = Integer.parseInt(txt);
                        } catch (NumberFormatException ex) {
                            marcarError(tf, "text-field-error");
                            valido = false;
                            continue;
                        }
                    }

                    if (cant < 0) {
                        marcarError(tf, "text-field-error");
                        valido = false;
                    } else {
                        limpiarError(tf, "text-field-error");
                    }

                    if (cant > 0) {
                        hayNaveValida = true;
                    }
                }
            }
        }

        HBox fila0 = (HBox) contenedorNaves.getChildren().get(0);
        Label labelNave0 = (Label) fila0.getChildren().get(0);

        if (!haySeleccionEnAlguna) {
            marcarError(labelNave0, "label-error");
            valido = false;
        } else {
            limpiarError(labelNave0, "label-error");
        }

        if (haySeleccionEnAlguna && !hayNaveValida) {
            valido = false;

            for (Node node : contenedorNaves.getChildren()) {
                if (node instanceof HBox hbox) {
                    ComboBox<String> cb = null;
                    TextField tf = null;

                    for (Node n : hbox.getChildren()) {
                        if (n instanceof ComboBox cbx)
                            cb = cbx;
                        if (n instanceof TextField tfc)
                            tf = tfc;
                    }

                    if (cb != null && cb.getValue() != null) {
                        String txt = tf.getText().trim();
                        int cant = (txt.isEmpty() ? 0 : Integer.parseInt(txt));

                        if (cant == 0) {
                            marcarError(tf, "text-field-error");
                        }
                    }
                }
            }
        }

        if (!valido) {
            return;
        }

        try {
            RadioButton selectedRadio = (RadioButton) flotaSeleccionada.getSelectedToggle();
            String faccion = selectedRadio.getText();

            int nuevoIdFlota = 1;
            ResultSet rsMax = st.executeQuery("SELECT COALESCE(MAX(id_flota), 0) + 1 AS nuevo_id FROM flota");
            if (rsMax.next()) {
                nuevoIdFlota = rsMax.getInt("nuevo_id");
            }

            for (Node node : contenedorNaves.getChildren()) {
                if (node instanceof HBox hbox) {
                    ComboBox<String> cb = null;
                    TextField cantidadTF = null;

                    for (Node n : hbox.getChildren()) {
                        if (n instanceof ComboBox)
                            cb = (ComboBox<String>) n;
                        if (n instanceof TextField)
                            cantidadTF = (TextField) n;
                    }

                    if (cb != null && cantidadTF != null && cb.getValue() != null) {
                        String txt = cantidadTF.getText().trim();
                        int cantidad = txt.isEmpty() ? 0 : Integer.parseInt(txt);

                        if (cantidad > 0) {
                            PreparedStatement pst = conexion.prepareStatement(
                                    "INSERT INTO flota (id_flota, id_nave, id_usuario, nombre, faccion, cantidad) " +
                                            "VALUES (?, (SELECT id_nave FROM nave WHERE nombre = ?), ?, ?, ?, ?)");

                            pst.setInt(1, nuevoIdFlota);
                            pst.setString(2, cb.getValue());
                            pst.setInt(3, Sesion.getUsuario().getId_usuario());
                            pst.setString(4, nombre);
                            pst.setString(5, faccion);
                            pst.setInt(6, cantidad);
                            pst.executeUpdate();
                        }
                    }
                }
            }

            Stage stage = (Stage) nombreFlota.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            e.printStackTrace();
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
        int idUsuario = Sesion.getUsuario().getId_usuario();
        ObservableList<String> navesUsuario = obtenerNavesUsuario(idUsuario);
        naveEscogida.setItems(FXCollections.observableArrayList(navesUsuario));

        nombreFlota.textProperty().addListener((o, ov, nv) -> {
            limpiarError(nombreFlota, "text-field-error");
            limpiarError(labelNombreFlota, "label-error");
            labelNombreFlota.setText("Nombre:");
        });

        naveEscogida.setOnAction(e -> crearNuevaFila(naveEscogida, navesUsuario));
    }

}
