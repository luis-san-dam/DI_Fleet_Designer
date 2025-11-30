package com.javafx.vistaEditarFlota;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.javafx.bbdd.BBDD;
import com.javafx.modelos.Flota;
import com.javafx.modelos.Sesion;
import com.javafx.vistaEliminar.controladorEliminar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class controladorEditarFlota implements Initializable {

    Connection conexion;
    Statement st;
    ResultSet rs;
    ObservableList<String> opcionesUsuario;
    private Flota flotaOriginal;

    @FXML
    private TextField cantidadnaves;

    @FXML
    private VBox contenedorNaves;

    @FXML
    private HBox fila;

    @FXML
    private Label labelNombreFlota;

    @FXML
    private ComboBox<String> naveEscogida;

    @FXML
    private TextField nombreFlota;

    @FXML
    void botonCancelar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void botonEditarFlota(ActionEvent event) {
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
                        "SELECT COUNT(*) AS total FROM flota WHERE nombre = ? AND id_flota != ?");
                pst.setString(1, nombre);
                pst.setInt(2, flotaOriginal.getId_flota());
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

                    if (cant > 0)
                        hayNaveValida = true;
                }
            }
        }

        if (!valido)
            return;

        if (!hayNaveValida) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaEliminar.fxml"));
                Parent root = loader.load();
                controladorEliminar ctrlEliminar = loader.getController();
                ctrlEliminar.cargar(flotaOriginal);

                Stage stageEliminar = new Stage();
                stageEliminar.setScene(new Scene(root));
                stageEliminar.initModality(Modality.APPLICATION_MODAL);
                stageEliminar.showAndWait();

                ObservableList<Flota> flotaActualizada = obtenerNavesFlota(flotaOriginal.getId_flota());
                if (flotaActualizada.isEmpty()) {
                    Stage stageEditar = (Stage) nombreFlota.getScene().getWindow();
                    stageEditar.close();

                }

                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        try {
            int idFlota = flotaOriginal.getId_flota();
            String faccion = flotaOriginal.getFaccion();

            PreparedStatement del = conexion.prepareStatement("DELETE FROM flota WHERE id_flota = ?");
            del.setInt(1, idFlota);
            del.executeUpdate();

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
                        int cant = tf.getText().trim().isEmpty() ? 0 : Integer.parseInt(tf.getText().trim());
                        if (cant > 0) {
                            PreparedStatement pst = conexion.prepareStatement(
                                    "INSERT INTO flota (id_flota, id_nave, id_usuario, nombre, faccion, cantidad) " +
                                            "VALUES (?, (SELECT id_nave FROM nave WHERE nombre = ?), ?, ?, ?, ?)");
                            pst.setInt(1, idFlota);
                            pst.setString(2, cb.getValue());
                            pst.setInt(3, Sesion.getUsuario().getId_usuario());
                            pst.setString(4, nombre);
                            pst.setString(5, faccion);
                            pst.setInt(6, cant);
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

        HBox fila = new HBox();
        fila.setMinWidth(600);
        fila.setMinHeight(35);
        fila.setPrefHeight(50);
        fila.setMaxHeight(35);

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
        nuevaCantidad.textProperty().addListener((obs, oldV, newV) -> {
            if (!newV.matches("\\d*"))
                nuevaCantidad.setText(newV.replaceAll("[^\\d]", ""));
            limpiarError(nuevaCantidad, "text-field-error");
        });

        fila.getChildren().addAll(labelNave, nuevoCombo, labelCantidad, nuevaCantidad);
        contenedorNaves.getChildren().add(fila);

        nuevoCombo.setOnAction(e -> {
            if (nuevoCombo.getValue() != null && !nuevoCombo.getValue().isEmpty()) {
                crearNuevaFila(nuevoCombo, opcionesTotales);
            }
        });
    }

    private void marcarError(Node node, String styleClass) {
        if (!node.getStyleClass().contains(styleClass))
            node.getStyleClass().add(styleClass);
    }

    private void limpiarError(Node node, String styleClass) {
        node.getStyleClass().remove(styleClass);
    }

    public ObservableList<String> obtenerNavesUsuario(int idUsuario) {
        ObservableList<String> naves = FXCollections.observableArrayList();
        String query = "SELECT n.nombre " +
                "FROM nave n " +
                "JOIN flota f ON n.id_nave = f.id_nave " +
                "WHERE f.id_usuario = ?";
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

    public ObservableList<Flota> obtenerNavesFlota(int idFlota) {
        ObservableList<Flota> navesDeLaFlota = FXCollections.observableArrayList();

        String query = "SELECT * FROM flota WHERE id_flota = ?";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, idFlota);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Flota f = new Flota(
                        rs.getInt("id_flota"),
                        rs.getInt("id_nave"),
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("faccion"),
                        rs.getInt("cantidad"));
                navesDeLaFlota.add(f);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return navesDeLaFlota;
    }

    public void cargarFlotaExistente(Flota fOriginal) {
        this.flotaOriginal = fOriginal;
        nombreFlota.setText(fOriginal.getNombre());

        opcionesUsuario = obtenerNavesUsuario(Sesion.getUsuario().getId_usuario());

        String query = "SELECT f.*, n.nombre AS nombre_nave " +
                "FROM flota f " +
                "JOIN nave n ON f.id_nave = n.id_nave " +
                "WHERE f.id_flota = ? AND f.id_usuario = ?";

        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, fOriginal.getId_flota());
            ps.setInt(2, Sesion.getUsuario().getId_usuario());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String nombreNave = rs.getString("nombre_nave");
                int cantidad = rs.getInt("cantidad");

                crearFilaExistente(nombreNave, cantidad);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        naveEscogida.setItems(FXCollections.observableArrayList(opcionesUsuario));
        naveEscogida.setOnAction(e -> crearNuevaFila(naveEscogida, opcionesUsuario));
    }

    private void crearFilaExistente(String nombreNave, int cantidad) {
        HBox fila = new HBox();
        fila.setMinWidth(600);
        fila.setMinHeight(35);
        fila.setPrefHeight(50);
        fila.setMaxHeight(35);

        Label labelNave = new Label("Naves:");
        HBox.setMargin(labelNave, new Insets(0, 0, 0, 50));
        labelNave.setMinWidth(100);

        ComboBox<String> combo = new ComboBox<>(FXCollections.observableArrayList(opcionesUsuario));
        combo.setValue(nombreNave);
        combo.setDisable(true);
        combo.setMinWidth(150);

        Label labelCantidad = new Label("Cantidad:");
        HBox.setMargin(labelCantidad, new Insets(0, 0, 0, 20));
        labelCantidad.setMinWidth(80);

        TextField cantidadTF = new TextField(String.valueOf(cantidad));
        cantidadTF.setMinWidth(150);
        cantidadTF.textProperty().addListener((obs, oldV, newV) -> limpiarError(cantidadTF, "text-field-error"));

        fila.getChildren().addAll(labelNave, combo, labelCantidad, cantidadTF);
        contenedorNaves.getChildren().add(fila);

        opcionesUsuario.remove(nombreNave);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            conexion = BBDD.getInstance().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int idUsuario = Sesion.getUsuario().getId_usuario();
        opcionesUsuario = obtenerNavesUsuario(idUsuario);
    }
}
