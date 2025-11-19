package com.javafx.vistaEliminar;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.javafx.bbdd.BBDD;
import com.javafx.modelos.Flota;
import com.javafx.modelos.Nave;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class controladorEliminar implements Initializable {
    
    Connection conexion;
    Statement st;
    ResultSet rs;

    private Nave nave;
    private Flota flota;

    @FXML
    private Label eliminarText;

    @FXML
    void botonCancelar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void botonEliminar(ActionEvent event) {
        if(nave != null){
            eliminarDeBDNave();
        } else if(flota != null){
            eliminarDeBDFlota();
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

    private void eliminarDeBDNave() {
        try {
            Connection con = BBDD.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM nave WHERE id_nave = ?"
            );
            ps.setInt(1, nave.getId_nave());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void eliminarDeBDFlota() {
        try {
            Connection con = BBDD.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM flota WHERE id_flota = ?"
            );
            ps.setInt(1, nave.getId_nave());
            ps.executeUpdate();
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
