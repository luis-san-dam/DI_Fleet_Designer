package com.javafx.vistaEliminar;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.javafx.bbdd.BBDD;
import com.javafx.modelos.Flota;
import com.javafx.modelos.Nave;
import com.javafx.modelos.Sesion;
import com.javafx.modelos.Usuario;
import com.javafx.vistaDesign.controladorDesign;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class controladorEliminar implements Initializable {

    Connection conexion;
    Statement st;

    private Nave nave;
    private Flota flota;
    private controladorDesign controladorPrincipal;

    @FXML
    private Label eliminarText;

    public void setControladorPrincipal(controladorDesign cp) {
        this.controladorPrincipal = cp;
    }

    @FXML
    void botonCancelar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void botonEliminar(ActionEvent event) {
        if (nave != null) {
            eliminarDeBDNave();

            if (controladorPrincipal != null) {
                controladorPrincipal.refrescarTablaNaves(nave.getTipo());
            }
        } else if (flota != null) {
            eliminarDeBDFlota();

            if (controladorPrincipal != null) {
                controladorPrincipal.mostrarFlotas(flota.getFaccion());
            }
        } else {
            eliminarUsuarioActual();
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void cargar(Nave nave) {
        this.nave = nave;
        eliminarText.setText("¿Seguro que deseas eliminar la nave \"" + nave.getNombre() + "\"?");
    }

    public void cargar(Flota flota) {
        this.flota = flota;
        eliminarText.setText("¿Seguro que deseas eliminar la flota \"" + flota.getNombre() + "\"?");
    }

    public void cargarUsuarioActual() {
        Usuario u = Sesion.getUsuario();
        eliminarText.setText("¿Seguro que deseas eliminar tu usuario \"" + u.getNombre_usuario() + "\"?");
    }

    private void eliminarDeBDNave() {
        try {
            Connection con = BBDD.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM nave WHERE id_nave = ?");
            ps.setInt(1, nave.getId_nave());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void eliminarDeBDFlota() {
        if (flota == null)
            return;

        try (PreparedStatement ps = conexion.prepareStatement(
                "DELETE FROM flota WHERE id_flota = ?")) {
            ps.setInt(1, flota.getId_flota());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void eliminarUsuarioActual() {
        Usuario u = Sesion.getUsuario();
        if (u == null)
            return;

        try {
            PreparedStatement ps = conexion.prepareStatement(
                    "DELETE FROM usuario WHERE id_usuario = ?");
            ps.setInt(1, u.getId_usuario());
            ps.executeUpdate();

            Sesion.cerrarSesion();

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
    }
}
