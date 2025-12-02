package com.javafx.vistaOpciones;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.javafx.bbdd.BBDD;
import com.javafx.modelos.Sesion;
import com.javafx.modelos.Usuario;
import com.javafx.vistaEliminar.controladorEliminar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class controladorOpciones implements Initializable {

    Connection conexion;
    Statement st;
    ResultSet rs;

    @FXML
    private Button botonBorrarUser;

    @FXML
    private PasswordField datoContraseniaRepetida;

    @FXML
    private TextField datoEmailUsuario;

    @FXML
    private TextField datoNombreUsuario;

    @FXML
    private PasswordField datoNuevaContrasenia;

    @FXML
    private Label errorEmail;

    @FXML
    private Label errorNombre;

    @FXML
    void botonCancelar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private Stage stagePrincipal;

    public void setStagePrincipal(Stage stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
    }

    @FXML
    void botonBorrarUsuario(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaEliminar.fxml"));
            Parent root = loader.load();

            controladorEliminar ce = loader.getController();
            ce.cargarUsuarioActual();   

            Stage modal = new Stage();
            modal.setScene(new Scene(root));
            modal.initOwner(((Node) event.getSource()).getScene().getWindow());
            modal.initModality(javafx.stage.Modality.WINDOW_MODAL);
            modal.setTitle("Fleet Designer");
            modal.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!Sesion.hayUsuarioLogueado()) {

            Stage actual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            actual.close();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaLogin.fxml"));
                Parent root = loader.load();

                stagePrincipal.setScene(new Scene(root));
                stagePrincipal.setTitle("Fleet Designer");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean nombreExiste(String nombre) {
        String sql = "SELECT 1 FROM usuario WHERE nombre_usuario = ? AND id_usuario <> ? LIMIT 1";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, nombre);
            pst.setInt(2, Sesion.getUsuario().getId_usuario());
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean emailExiste(String email) {
        String sql = "SELECT 1 FROM usuario WHERE email = ? AND id_usuario <> ? LIMIT 1";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, email);
            pst.setInt(2, Sesion.getUsuario().getId_usuario());
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

    private void limpiarErrores() {
        errorNombre.setText("");
        errorEmail.setText("");

        errorNombre.getStyleClass().remove("error-label");
        errorEmail.getStyleClass().remove("error-label");

        datoNombreUsuario.getStyleClass().remove("text-field-error");
        datoEmailUsuario.getStyleClass().remove("text-field-error");
        datoNuevaContrasenia.getStyleClass().remove("text-field-error");
        datoContraseniaRepetida.getStyleClass().remove("text-field-error");
    }

    @FXML
    void botonEditar(ActionEvent event) {

        limpiarErrores();

        Usuario u = Sesion.getUsuario();

        String nuevoNombre = datoNombreUsuario.getText().trim();
        String nuevoEmail = datoEmailUsuario.getText().trim();
        String nuevaPass = datoNuevaContrasenia.getText();
        String nuevaPass2 = datoContraseniaRepetida.getText();

        boolean valido = true;

        if (nuevoNombre.isEmpty()) {
            errorNombre.setText("Nombre vacío");
            errorNombre.getStyleClass().add("error-label");
            datoNombreUsuario.getStyleClass().add("text-field-error");
            valido = false;
        } else if (!nuevoNombre.equals(u.getNombre_usuario()) && nombreExiste(nuevoNombre)) {
            errorNombre.setText("Nombre ya en uso");
            errorNombre.getStyleClass().add("error-label");
            datoNombreUsuario.getStyleClass().add("text-field-error");
            valido = false;
        }

        if (nuevoEmail.isEmpty()) {
            errorEmail.setText("Correo vacío");
            errorEmail.getStyleClass().add("error-label");
            datoEmailUsuario.getStyleClass().add("text-field-error");
            valido = false;
        } else if (!emailValido(nuevoEmail)) {
            errorEmail.setText("Correo inválido");
            errorEmail.getStyleClass().add("error-label");
            datoEmailUsuario.getStyleClass().add("text-field-error");
            valido = false;
        } else if (!nuevoEmail.equals(u.getEmail()) && emailExiste(nuevoEmail)) {
            errorEmail.setText("Correo ya en uso");
            errorEmail.getStyleClass().add("error-label");
            datoEmailUsuario.getStyleClass().add("text-field-error");
            valido = false;
        }

        boolean cambiarPass = !nuevaPass.isEmpty() || !nuevaPass2.isEmpty();

        if (cambiarPass) {

            if (nuevaPass.isEmpty()) {
                datoNuevaContrasenia.getStyleClass().add("text-field-error");
                valido = false;
            }

            if (!nuevaPass.equals(nuevaPass2)) {
                datoContraseniaRepetida.getStyleClass().add("text-field-error");
                valido = false;
            }
        }

        if (!valido) return;

        try {
            String sql = cambiarPass ?
                "UPDATE usuario SET nombre_usuario = ?, email = ?, contrasenia = ? WHERE id_usuario = ?" :
                "UPDATE usuario SET nombre_usuario = ?, email = ? WHERE id_usuario = ?";

            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setString(1, nuevoNombre);
            pst.setString(2, nuevoEmail);

            if (cambiarPass) {
                pst.setString(3, nuevaPass);
                pst.setInt(4, u.getId_usuario());
            } else {
                pst.setInt(3, u.getId_usuario());
            }

            pst.executeUpdate();

            u.setNombre_usuario(nuevoNombre);
            u.setEmail(nuevoEmail);
            if (cambiarPass) u.setContrasenia(nuevaPass);

            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alerta.setTitle("Edición realizada");
            alerta.setHeaderText(null);
            alerta.setContentText("Los cambios se han guardado correctamente.");
            alerta.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.close();
    }

    @FXML
    void cambiarColor(ActionEvent event) {

    }

    private void cargarDatosUsuario() {
        Usuario u = Sesion.getUsuario();

        if (u != null) {
            datoNombreUsuario.setText(u.getNombre_usuario());
            datoEmailUsuario.setText(u.getEmail());
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
        cargarDatosUsuario();

        if (Sesion.getUsuario().isEs_admin()) {
            botonBorrarUser.setVisible(false);
            botonBorrarUser.setManaged(false);
        }
    }

}
