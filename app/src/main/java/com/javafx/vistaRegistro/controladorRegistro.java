package com.javafx.vistaRegistro;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.javafx.bbdd.BBDD;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class controladorRegistro implements Initializable {

    Connection conexion;
    Statement st;
    ResultSet rs;

    @FXML
    private PasswordField datoContrasenia;

    @FXML
    private PasswordField datoContraseniaRepetida;

    @FXML
    private TextField datoEmailUsuario;

    @FXML
    private TextField datoNombreUsuario;

    @FXML
    private Label errorEmail;

    @FXML
    private Label errorNombre;

    @FXML
    void botonCancelar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private boolean nombreExiste(String nombre) {
        String sql = "SELECT 1 FROM usuario WHERE nombre_usuario = ? LIMIT 1";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, nombre);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean emailExiste(String email) {
        String sql = "SELECT 1 FROM usuario WHERE email = ? LIMIT 1";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean emailValido(String email) {
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

    private void limpiarErroresNombreEmail() {
        errorNombre.setText("");
        errorEmail.setText("");

        errorNombre.getStyleClass().remove("error-label");
        errorEmail.getStyleClass().remove("error-label");

        datoNombreUsuario.getStyleClass().remove("text-field-error");
        datoEmailUsuario.getStyleClass().remove("text-field-error");
    }

    @FXML
    void botonRegistrarse(ActionEvent event) {

        limpiarErroresNombreEmail();

        datoContrasenia.getStyleClass().remove("text-field-error");
        datoContraseniaRepetida.getStyleClass().remove("text-field-error");

        String nombre = datoNombreUsuario.getText().trim();
        String email = datoEmailUsuario.getText().trim();
        String pass = datoContrasenia.getText();
        String pass2 = datoContraseniaRepetida.getText();

        boolean valido = true;

        if (nombre.isEmpty()) {
            errorNombre.setText("Nombre vacío");
            errorNombre.getStyleClass().remove("error-label");
            errorNombre.getStyleClass().add("error-label");
            datoNombreUsuario.getStyleClass().add("text-field-error");
            valido = false;
        } else if (nombreExiste(nombre)) {
            errorNombre.setText("Nombre ya en uso");
            errorNombre.getStyleClass().remove("error-label");
            errorNombre.getStyleClass().add("error-label");
            datoNombreUsuario.getStyleClass().add("text-field-error");
            valido = false;
        }

        if (email.isEmpty()) {
            errorEmail.setText("Correo vacío");
            errorEmail.getStyleClass().remove("error-label");
            errorEmail.getStyleClass().add("error-label");
            datoEmailUsuario.getStyleClass().add("text-field-error");
            valido = false;
        } else if (!emailValido(email)) {
            errorEmail.setText("Correo inválido");
            errorEmail.getStyleClass().remove("error-label");
            errorEmail.getStyleClass().add("error-label");
            datoEmailUsuario.getStyleClass().add("text-field-error");
            valido = false;
        } else if (emailExiste(email)) {
            errorEmail.setText("Correo ya en uso");
            errorEmail.getStyleClass().remove("error-label");        
            errorEmail.getStyleClass().add("error-label");
            datoEmailUsuario.getStyleClass().add("text-field-error");
            valido = false;
        }

        if (pass == null || pass.isEmpty()) {
            datoContrasenia.getStyleClass().add("text-field-error");
            valido = false;
        }

        if (pass2 == null || pass2.isEmpty() || !pass2.equals(pass)) {
            datoContraseniaRepetida.getStyleClass().add("text-field-error");
            valido = false;
        }

        if (!valido) {
            return;
        }

        try {
        String sqlInsert = "INSERT INTO usuario (nombre_usuario, email, contrasenia, es_admin) VALUES (?, ?, ?, 0)";
        try (PreparedStatement pst = conexion.prepareStatement(sqlInsert)) {
            pst.setString(1, nombre);
            pst.setString(2, email);
            pst.setString(3, pass);
            pst.executeUpdate();
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    } catch (SQLException e) {
        System.out.println("ERROR insertando usuario: " + e.getMessage());
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
