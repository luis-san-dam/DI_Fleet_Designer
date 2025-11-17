package com.javafx.vistaDesign;

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
import com.javafx.modelos.Nave;
import com.javafx.modelos.Sesion;
import com.javafx.vistaEditarFlota.controladorEditarFlota;
import com.javafx.vistaEditarNave.controladorEditarNave;
import com.javafx.vistaEliminar.controladorEliminar;
import com.javafx.vistaOpciones.controladorOpciones;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class controladorDesign implements Initializable {

    Connection conexion;
    Statement st;
    ResultSet rs;

    ObservableList<Nave> listaNaves = FXCollections.observableArrayList();
    ObservableList<Flota> listaFlotas = FXCollections.observableArrayList();

    @FXML
    private Pane bannerFlotaImperio;

    @FXML
    private Pane bannerFlotaMercenarios;

    @FXML
    private Pane bannerFlotaPiratas;

    @FXML
    private Pane bannerFlotaRebeldes;

    @FXML
    private Pane bannerFlotaRepublica;

    @FXML
    private Pane bannerFlotaSeparatistas;

    @FXML
    private Pane bannerFlotas;

    @FXML
    private Pane bannerNaveAcorazado;

    @FXML
    private Pane bannerNaveColoso;

    @FXML
    private Pane bannerNaveCorveta;

    @FXML
    private Pane bannerNaveCrucero;

    @FXML
    private Pane bannerNaveDestructor;

    @FXML
    private Pane bannerNaveFragata;

    @FXML
    private Pane bannerNaveInsignia;

    @FXML
    private Pane bannerNaveTitan;

    @FXML
    private Pane bannerNaves;

    @FXML
    private TableColumn<Flota, HBox> botonesFlota;

    @FXML
    private TableColumn<Nave, HBox> botonesNave;

    @FXML
    private ImageView faccionImperio;

    @FXML
    private ImageView faccionMercenarios;

    @FXML
    private ImageView faccionPiratas;

    @FXML
    private ImageView faccionRebeldes;

    @FXML
    private ImageView faccionRepublica;

    @FXML
    private ImageView faccionSeparatistas;

    @FXML
    private TableColumn<Nave, String> fotoNave;

    @FXML
    private TableColumn<Flota, String> navesTotalesFlota;

    @FXML
    private TableColumn<Flota, String> nombreFlota;

    @FXML
    private TableColumn<Nave, String> nombreNave;

    @FXML
    private Pane paneListaFlotas;

    @FXML
    private Pane paneListaNaves;

    @FXML
    private Pane paneListaRanking;

    @FXML
    private Pane panelGlobal;

    @FXML
    private Pane panelNuevaFlota;

    @FXML
    private Pane panelNuevaNave;

    @FXML
    private Pane panelPersonal;

    @FXML
    private AnchorPane panelTablaFlotas;

    @FXML
    private AnchorPane panelTablaNaves;

    @FXML
    private TableColumn<Nave, String> potenciaNave;

    @FXML
    private Label primeroRanking;

    @FXML
    private ImageView primeroRankingFoto;

    @FXML
    private Label segundoRanking;

    @FXML
    private ImageView segundoRankingFoto;

    @FXML
    private TableView<Flota> tablaFlotas;

    @FXML
    private TableView<Nave> tablaNaves;

    @FXML
    private Label terceroRanking;

    @FXML
    private ImageView terceroRankingFoto;

    @FXML
    private TableColumn<Nave, String> tipoNave;

    @FXML
    private TableColumn<Flota, String> tiposFlota;

    @FXML // TODO
    void cerrarSesion(ActionEvent event) {

    }

    @FXML
    void listaAcorazado(ActionEvent event) {
        dameListaNaves("Acorazado");
    }

    @FXML
    void listaColoso(ActionEvent event) {
        dameListaNaves("Coloso");
    }

    @FXML
    void listaCorveta(ActionEvent event) {
        dameListaNaves("Corveta");
    }

    @FXML
    void listaCrucero(ActionEvent event) {
        dameListaNaves("Crucero");
    }

    @FXML
    void listaDestructor(ActionEvent event) {
        dameListaNaves("Destructor");
    }

    @FXML
    void listaFragata(ActionEvent event) {
        dameListaNaves("Fragata");
    }

    @FXML
    void listaGlobal(ActionEvent event) {
        paneListaRanking.toFront();
        paneListaRanking.setVisible(true);
        paneListaNaves.setVisible(false);
        paneListaFlotas.setVisible(false);
        
        bannerNaves.setVisible(false);
        bannerFlotas.setVisible(false);

        bannerFlotaImperio.setVisible(false);
        bannerFlotaMercenarios.setVisible(false);
        bannerFlotaPiratas.setVisible(false);
        bannerFlotaRebeldes.setVisible(false);
        bannerFlotaRepublica.setVisible(false);
        bannerFlotaSeparatistas.setVisible(false);

        panelNuevaNave.setVisible(false);
        panelNuevaFlota.setVisible(false);

        panelGlobal.setVisible(true);
        panelPersonal.setVisible(false);
        panelTablaNaves.setVisible(false);
        panelTablaFlotas.setVisible(false);
    }

    @FXML
    void listaImperio(ActionEvent event) {
        dameListaFlotas("Imperio");
    }

    @FXML
    void listaInsignia(ActionEvent event) {
        dameListaNaves("Insignia");
    }

    @FXML
    void listaMercenarios(ActionEvent event) {
        dameListaFlotas("Mercenarios");
    }

    @FXML
    void listaPersonal(ActionEvent event) {
        paneListaRanking.toFront();
        paneListaRanking.setVisible(true);
        paneListaNaves.setVisible(false);
        paneListaFlotas.setVisible(false);
        
        bannerNaves.setVisible(false);
        bannerFlotas.setVisible(false);

        bannerFlotaImperio.setVisible(false);
        bannerFlotaMercenarios.setVisible(false);
        bannerFlotaPiratas.setVisible(false);
        bannerFlotaRebeldes.setVisible(false);
        bannerFlotaRepublica.setVisible(false);
        bannerFlotaSeparatistas.setVisible(false);

        panelNuevaNave.setVisible(false);
        panelNuevaFlota.setVisible(false);

        panelPersonal.setVisible(true);
        panelGlobal.setVisible(false);
        panelTablaNaves.setVisible(false);
        panelTablaFlotas.setVisible(false);
    }

    @FXML
    void listaPiratas(ActionEvent event) {
        dameListaFlotas("Piratas");
    }

    @FXML
    void listaRebeldes(ActionEvent event) {
        dameListaFlotas("Rebeldes");
    }

    @FXML
    void listaRepublica(ActionEvent event) {
        dameListaFlotas("Republica");
    }

    @FXML
    void listaSeparatistas(ActionEvent event) {
        dameListaFlotas("Separatistas");
    }

    @FXML
    void listaTitan(ActionEvent event) {
        dameListaNaves("Titan");
    }

    @FXML // TODO
    void nuevaFlota(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaNuevaFlota.fxml"));
            Parent root = loader.load();

            Stage modal = new Stage();
            modal.setTitle("Fleet Designer");
            modal.setScene(new Scene(root));

            modal.initModality(Modality.APPLICATION_MODAL);

            Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modal.initOwner(parentStage);
            modal.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML // TODO
    void nuevaNave(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaNuevaNave.fxml"));
            Parent root = loader.load();

            Stage modal = new Stage();
            modal.setTitle("Fleet Designer");
            modal.setScene(new Scene(root));

            modal.initModality(Modality.APPLICATION_MODAL);

            Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modal.initOwner(parentStage);
            modal.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void opciones(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaOpciones.fxml"));
            Parent root = loader.load();

            controladorOpciones controller = loader.getController();

            Stage stagePrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();

            controller.setStagePrincipal(stagePrincipal);

            Stage modal = new Stage();
            modal.setScene(new Scene(root));
            modal.setTitle("Fleet Designer");
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(stagePrincipal);
            modal.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ventanaRanking(ActionEvent event) {
        paneListaRanking.toFront();
        paneListaRanking.setVisible(true);
        paneListaNaves.setVisible(false);
        paneListaFlotas.setVisible(false);
        
        bannerNaves.setVisible(false);
        bannerFlotas.setVisible(false);

        bannerFlotaImperio.setVisible(false);
        bannerFlotaMercenarios.setVisible(false);
        bannerFlotaPiratas.setVisible(false);
        bannerFlotaRebeldes.setVisible(false);
        bannerFlotaRepublica.setVisible(false);
        bannerFlotaSeparatistas.setVisible(false);

        panelNuevaNave.setVisible(false);
        panelNuevaFlota.setVisible(false);

        panelGlobal.setVisible(true);
        panelPersonal.setVisible(false);
        panelTablaNaves.setVisible(false);
        panelTablaFlotas.setVisible(false);
    }

    @FXML
    void ventanaFlotas(ActionEvent event) {
        paneListaFlotas.toFront();
        paneListaFlotas.setVisible(true);
        paneListaNaves.setVisible(false);
        paneListaRanking.setVisible(false);

        bannerFlotas.toFront();
        bannerFlotas.setVisible(true);
        bannerNaves.setVisible(false);
        

        bannerFlotaImperio.toFront();
        bannerFlotaImperio.setVisible(true);
        bannerFlotaMercenarios.setVisible(false);
        bannerFlotaPiratas.setVisible(false);
        bannerFlotaRebeldes.setVisible(false);
        bannerFlotaRepublica.setVisible(false);
        bannerFlotaSeparatistas.setVisible(false);

        panelNuevaFlota.toFront();
        panelNuevaFlota.setVisible(true);
        panelNuevaNave.setVisible(false);

        panelTablaFlotas.toFront();
        panelTablaFlotas.setVisible(true);
        panelTablaNaves.setVisible(false);
        panelGlobal.setVisible(false);
        panelPersonal.setVisible(false);

        mostrarFlotas("Imperio");
    }

    @FXML
    void ventanaNaves(ActionEvent event) {
        paneListaNaves.toFront();
        paneListaNaves.setVisible(true);
        paneListaFlotas.setVisible(false);
        paneListaRanking.setVisible(false);

        bannerNaves.toFront();
        bannerNaves.setVisible(true);
        bannerFlotas.setVisible(false);

        bannerNaveCorveta.toFront();
        bannerNaveCorveta.setVisible(true);
        bannerNaveFragata.setVisible(false);
        bannerNaveDestructor.setVisible(false);
        bannerNaveCrucero.setVisible(false);
        bannerNaveAcorazado.setVisible(false);
        bannerNaveTitan.setVisible(false);
        bannerNaveColoso.setVisible(false);
        bannerNaveInsignia.setVisible(false);

        panelNuevaNave.toFront();
        panelNuevaNave.setVisible(true);
        panelNuevaFlota.setVisible(false);
        

        panelTablaNaves.toFront();
        panelTablaNaves.setVisible(true);
        panelTablaFlotas.setVisible(false);
        panelGlobal.setVisible(false);
        panelPersonal.setVisible(false);

        mostrarNaves("Corveta");
    }

    public void mostrarNaves(String tipoNave) {
        this.tablaNaves.setItems(this.dameListaNaves(tipoNave));
    }

    public ObservableList<Nave> dameListaNaves(String tipoNave) {

        String query = "SELECT * FROM nave WHERE tipo = ?";

        try (PreparedStatement pst = conexion.prepareStatement(query)) {

            pst.setString(1, tipoNave);
            rs = pst.executeQuery();

            listaNaves.clear();

            while (rs.next()) {
                Nave nave = new Nave(
                    rs.getInt("id_nave"),
                    rs.getString("nombre"),
                    rs.getString("tipo"),
                    rs.getString("propulsion"),
                    rs.getString("sistema_defensivo"),
                    rs.getString("armadura"),
                    rs.getString("escudo"),
                    rs.getString("armamento"),
                    rs.getString("imagen")
                );
                listaNaves.add(nave);
            }

        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
        }
        return listaNaves;
    }

    public void mostrarFlotas(String tipoFlota) {
        this.tablaFlotas.setItems(this.dameListaFlotas(tipoFlota));
    }

    public ObservableList<Flota> dameListaFlotas(String tipoFlota) {
        if (conexion != null) {
            listaFlotas.clear();
            String query = "SELECT * FROM flota WHERE faccion = ?";

        try (PreparedStatement pst = conexion.prepareStatement(query)) {

            pst.setString(1, tipoFlota);
            rs = pst.executeQuery();

                while (this.rs.next()) {
                    Flota flota = new Flota(this.rs.getInt("id_flota"),
                            rs.getInt("id_nave"),
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("faccion"),
                            rs.getInt("cantidad"));
                    listaFlotas.add(flota);
                }
            } catch (SQLException var3) {
                System.out.println("Excepción SQL: " + var3.getMessage());
            }
            return this.listaFlotas;
        } else {
            return null;
        }
    }

    public String calcularPotenciaNave(Nave nave) {
        String potenciaNave;
        String[] armas = nave.getArmamento().split("-");
        if (armas.length <= 3) {
            potenciaNave = "Ligera";
        } else if (armas.length < 6) {
            potenciaNave = "Media";
        } else {
            potenciaNave = "Pesada";
        }
        return potenciaNave;
    }

    private void editarNave(Nave nave) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaEditarNave.fxml"));
            Parent root = loader.load();

            controladorEditarNave controller = loader.getController();

            controller.cargarNave(nave);

            Stage modal = new Stage();
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setScene(new Scene(root));
            modal.setTitle("Fleet Designer");
            
            modal.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void borrarNave(Nave nave) { //TODO
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaEliminar.fxml"));
            Parent root = loader.load();

            controladorEliminar controller = loader.getController();

            controller.cargar(nave);

            Stage modal = new Stage();
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setScene(new Scene(root));
            modal.setTitle("Fleet Designer");
            
            modal.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editarFlota(Flota flota) { //TODO
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaEditarFlota.fxml"));
            Parent root = loader.load();

            controladorEditarFlota controller = loader.getController();

            controller.cargarFlota(flota);

            Stage modal = new Stage();
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setScene(new Scene(root));
            modal.setTitle("Fleet Designer");
            
            modal.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        };
    }

    private void borrarFlota(Flota flota) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaEliminar.fxml"));
            Parent root = loader.load();

            controladorEliminar controller = loader.getController();

            controller.cargar(flota);

            Stage modal = new Stage();
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setScene(new Scene(root));
            modal.setTitle("Fleet Designer");
            
            modal.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.conexion = BBDD.getInstance().getConnection();
            if (this.conexion != null) {
                this.st = this.conexion.createStatement();
            }
        } catch (SQLException var4) {

        }
        
        listaNaves = dameListaNaves("Corveta");

        nombreNave.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tipoNave.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        potenciaNave
                .setCellValueFactory(cellData -> new SimpleStringProperty(calcularPotenciaNave(cellData.getValue())));
        fotoNave.setCellValueFactory(new PropertyValueFactory<>("imagen")); //TODO
        botonesNave.setCellFactory(col -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnBorrar = new Button("Borrar");
            private final HBox contenedor = new HBox(12, btnEditar, btnBorrar);

            {
                btnEditar.setOnAction(event -> {
                    Nave nave = getTableView().getItems().get(getIndex());
                    editarNave(nave);
                    dameListaNaves(nave.getTipo());
                });

                btnBorrar.setOnAction(event -> {
                    Nave nave = getTableView().getItems().get(getIndex());
                    borrarNave(nave);
                });

                btnEditar.setPrefHeight(30);
                btnBorrar.setPrefHeight(30);

                contenedor.setAlignment(Pos.CENTER);
            }

    @Override
    protected void updateItem(HBox item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(contenedor);
        }
    }
});
        tablaNaves.setItems(listaNaves);

        listaFlotas = dameListaFlotas("Imperio");

        nombreFlota.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        // this.tiposFlota.setCellValueFactory(cellData -> generarIconoTipoNaveFlota()); //TODO
        // this.navesTotalesFlota.setCellValueFactory(new PropertyValueFactory<>("imagen")); //TODO
        botonesFlota.setCellFactory(col -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnBorrar = new Button("Borrar");
            private final HBox contenedor = new HBox(12, btnEditar, btnBorrar);
            {
                btnEditar.setOnAction(event -> {
                    Flota flota = getTableView().getItems().get(getIndex());
                    editarFlota(flota);
                    dameListaFlotas(flota.getFaccion());
                });

                btnBorrar.setOnAction(event -> {
                    Flota flota = getTableView().getItems().get(getIndex());
                    borrarFlota(flota);
                });

                btnEditar.setPrefHeight(30);
                btnBorrar.setPrefHeight(30);

                contenedor.setAlignment(Pos.CENTER);
            }

            @Override
            protected void updateItem(HBox item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(contenedor);
                }
            }
        });
        tablaFlotas.setItems(listaFlotas);
    }
}
