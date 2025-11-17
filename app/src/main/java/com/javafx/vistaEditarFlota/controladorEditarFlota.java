package com.javafx.vistaEditarFlota;

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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class controladorEditarFlota implements Initializable {

    Connection conexion;
    Statement st;
    ResultSet rs;
    ObservableList<String> opcionesUsuario;

    @FXML
    private TextField cantidadnaves;

    @FXML
    private VBox contenedorNaves;

    @FXML
    private HBox fila;

    @FXML
    private ComboBox<String> naveEscogida;

    @FXML
    private TextField nombreFlota;

    @FXML
    void botonCancelar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML // TODO
    void botonEditarFlota(ActionEvent event) {

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

    public ObservableList<Flota> cargarFlota(Flota flota) {
        ObservableList<Flota> navesDeLaFlota = FXCollections.observableArrayList();
        if (conexion == null)
            return navesDeLaFlota;

        String query = "SELECT f.*, n.nombre AS nombre_nave " +
                "FROM flota f " +
                "JOIN nave n ON f.id_nave = n.id_nave " +
                "WHERE f.id_flota = ? AND f.id_usuario = ?";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, flota.getId_flota());
            ps.setInt(2, Sesion.getUsuario().getId_usuario());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Flota f = new Flota(
                        rs.getInt("id_flota"),
                        rs.getInt("id_nave"),
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("faccion"),
                        rs.getInt("cantidad"));
                String nombreNave = rs.getString("nombre_nave"); // ESTE ES EL NOMBRE QUE PONE EN EL COMBOBOX

                HBox fila = new HBox(10);
                ComboBox<String> combo = new ComboBox<>(FXCollections.observableArrayList(opcionesUsuario));
                combo.setValue(nombreNave); // ahora sí aparece la nave correcta
                TextField cantidad = new TextField(String.valueOf(f.getCantidad()));
                fila.getChildren().addAll(combo, cantidad);
                contenedorNaves.getChildren().add(fila);

                opcionesUsuario.remove(nombreNave);

                combo.setOnAction(e -> crearNuevaFila(combo, opcionesUsuario));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Creamos los HBox con ComboBox + TextField para cada nave de la flota
        for (Flota f : navesDeLaFlota) {
            HBox fila = new HBox(10);
            ComboBox<String> combo = new ComboBox<>(FXCollections.observableArrayList(opcionesUsuario));
            combo.setValue(f.getNombre());
            TextField cantidad = new TextField(String.valueOf(f.getCantidad()));
            fila.getChildren().addAll(combo, cantidad);
            contenedorNaves.getChildren().add(fila);

            // Quitamos la nave seleccionada de las opciones disponibles
            opcionesUsuario.remove(f.getNombre());

            // Listener recursivo
            combo.setOnAction(e -> crearNuevaFila(combo, opcionesUsuario));
        }

        return navesDeLaFlota;
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

    public void cargarFlotaExistente(int idFlota) {
        ObservableList<Flota> navesDeLaFlota = obtenerNavesFlota(idFlota);

        int idUsuario = Sesion.getUsuario().getId_usuario();
        ObservableList<String> opcionesUsuario = obtenerNavesUsuario(idUsuario);

        for (Flota f : navesDeLaFlota) {
            HBox fila = new HBox(10);
            ComboBox<String> combo = new ComboBox<>(FXCollections.observableArrayList(opcionesUsuario));
            combo.setValue(f.getNombre()); // selecciona la nave actual
            TextField cantidad = new TextField(String.valueOf(f.getCantidad())); // cantidad actual

            fila.getChildren().addAll(combo, cantidad);
            contenedorNaves.getChildren().add(fila);

            opcionesUsuario.remove(f.getNombre());
        }
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

        // Actualizamos las opciones quitando las ya seleccionadas
        for (Node node : contenedorNaves.getChildren()) {
            if (node instanceof HBox hbox) {
                for (Node n : hbox.getChildren()) {
                    if (n instanceof ComboBox<?> cb) {
                        if (cb.getValue() != null) {
                            nuevasOpciones.remove(cb.getValue());
                        }
                    }
                }
            }
        }
        nuevoCombo.setOnAction(e -> crearNuevaFila(nuevoCombo, opcionesTotales));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.conexion = BBDD.getInstance().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int idUsuario = Sesion.getUsuario().getId_usuario();
        opcionesUsuario = obtenerNavesUsuario(idUsuario);

        naveEscogida.setItems(FXCollections.observableArrayList(opcionesUsuario));
        naveEscogida.setOnAction(e -> crearNuevaFila(naveEscogida, opcionesUsuario));
    }
}
