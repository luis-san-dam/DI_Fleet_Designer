package com.javafx.vistaDesign;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import com.javafx.bbdd.BBDD;
import com.javafx.modelos.Flota;
import com.javafx.modelos.Nave;
import com.javafx.modelos.Sesion;
import com.javafx.modelos.Usuario;
import com.javafx.vistaEditarFlota.controladorEditarFlota;
import com.javafx.vistaEditarNave.controladorEditarNave;
import com.javafx.vistaEliminar.controladorEliminar;
import com.javafx.vistaOpciones.controladorOpciones;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.skins.TileSkin;
import eu.hansolo.tilesfx.tools.Helper;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class controladorDesign implements Initializable {

    Connection conexion;
    Statement st;
    ResultSet rs;
    Usuario u = Sesion.getUsuario();

    ObservableList<Nave> listaNaves = FXCollections.observableArrayList();
    ObservableList<Flota> listaFlotas = FXCollections.observableArrayList();

    private List<String> nombres = new ArrayList<String>();
    private List<Integer> totales = new ArrayList<Integer>();
    private List<Integer> ids = new ArrayList<Integer>();

    @FXML
    private Pane bannerFlotas;

    @FXML
    private Pane bannerNaves;

    @FXML
    private TableColumn<Flota, HBox> botonesFlota;

    @FXML
    private TableColumn<Nave, HBox> botonesNave;

    @FXML
    private Button btnAcorazado;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnColoso;

    @FXML
    private Button btnCorveta;

    @FXML
    private Button btnCrucero;

    @FXML
    private Button btnDestructor;

    @FXML
    private Button btnFragata;

    @FXML
    private Button btnGlobal;

    @FXML
    private Button btnImperio;

    @FXML
    private Button btnInsignia;

    @FXML
    private Button btnMercenarios;

    @FXML
    private Button btnNuevaFlota;

    @FXML
    private Button btnNuevaNave;

    @FXML
    private Button btnOpciones;

    @FXML
    private Button btnPersonal;

    @FXML
    private Button btnPiratas;

    @FXML
    private Button btnRebeldes;

    @FXML
    private Button btnRepublica;

    @FXML
    private Button btnSeparatistas;

    @FXML
    private Button btnTitan;

    @FXML
    private Button btnVentanaFlotas;

    @FXML
    private Button btnVentanaNaves;

    @FXML
    private Button btnVentanaRanking;

    @FXML
    private TableColumn<Nave, String> fotoNave;

    @FXML
    private ImageView gifNuevaFlota;

    @FXML
    private ImageView gifNuevaNave;

    @FXML
    private ImageView iconoAcorazado;

    @FXML
    private ImageView iconoAcorazadoLateral;

    @FXML
    private ImageView iconoCerrarSesion;

    @FXML
    private ImageView iconoColoso;

    @FXML
    private ImageView iconoColosoLateral;

    @FXML
    private ImageView iconoCorveta;

    @FXML
    private ImageView iconoCorvetaLateral;

    @FXML
    private ImageView iconoCrucero;

    @FXML
    private ImageView iconoCruceroLateral;

    @FXML
    private ImageView iconoDestructor;

    @FXML
    private ImageView iconoDestructorLateral;

    @FXML
    private ImageView iconoFragata;
    
    @FXML
    private ImageView iconoFragataLateral;

    @FXML
    private ImageView iconoImperioLateral;

    @FXML
    private ImageView iconoInsignia;

    @FXML
    private ImageView iconoInsigniaLateral;

    @FXML
    private ImageView iconoMercenariosLateral;

    @FXML
    private ImageView iconoOpciones;

    @FXML
    private ImageView iconoPiratasLateral;

    @FXML
    private ImageView iconoRebeldesLateral;

    @FXML
    private ImageView iconoRepublicaLateral;

    @FXML
    private ImageView iconoSeparatistasLateral;

    @FXML
    private ImageView iconoTitan;

    @FXML
    private ImageView iconoTitanLateral;

    @FXML
    private ImageView iconoVentanaRankings;

    @FXML
    private Label listadoUser;

    @FXML
    private Label nNavesAcorazado;

    @FXML
    private Label nNavesCorveta;

    @FXML
    private Label nNavesColoso;

    @FXML
    private Label nNavesCrucero;

    @FXML
    private Label nNavesDestructor;

    @FXML
    private Label nNavesFragata;

    @FXML
    private Label nNavesInsignia;

    @FXML
    private Label nNavesTitan;

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
    private FlowPane panelTilesFX;

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
    private TableColumn<Flota, Void> tiposFlota;

    public class MiNumberTileSkin extends TileSkin {
        private Text titleText;
        private Text valueText;
        private Text unitText;
        private VBox unitBox;
        private HBox valueArea;
        private Label description;

        private static final double TITLE_FONT_SIZE = 20;
        private static final double VALUE_FONT_SIZE = 48;
        private static final double UNIT_FONT_SIZE  = 15;
        private static final double DESC_FONT_SIZE  = 16;


        public MiNumberTileSkin(final Tile TILE) {
            super(TILE);
        }

        @Override
        protected void initGraphics() {
            super.initGraphics();

            titleText = new Text(tile.getTitle());
            titleText.setFill(tile.getTitleColor());
            titleText.setFont(Font.font(TITLE_FONT_SIZE));
            Helper.enableNode(titleText, !tile.getTitle().isEmpty());

            valueText = new Text(String.format(locale, formatString, tile.getValue()));
            valueText.setFill(tile.getValueColor());
            valueText.setTextOrigin(VPos.BASELINE);
            valueText.setFont(Font.font(VALUE_FONT_SIZE));

            unitText = new Text(tile.getUnit());
            unitText.setFill(tile.getUnitColor());
            unitText.setFont(Font.font(UNIT_FONT_SIZE));
            Helper.enableNode(unitText, !tile.getUnit().isEmpty());

            unitBox = new VBox(unitText);
            unitBox.setAlignment(Pos.CENTER);

            valueArea = new HBox(valueText, unitBox);
            valueArea.setAlignment(Pos.CENTER);

            description = new Label(tile.getDescription());
            description.setTextFill(tile.getDescriptionColor());
            description.setAlignment(Pos.CENTER);
            description.setFont(Font.font(DESC_FONT_SIZE));
            description.setWrapText(true);
            Helper.enableNode(description, tile.isTextVisible());

            getPane().getChildren().addAll(titleText, valueArea, description);
        }

        @Override
        protected void resize() {
            super.resize();

            double w = width;
            double h = height;

            titleText.relocate((w - titleText.getLayoutBounds().getWidth()) / 2, h * 0.05);

            valueArea.setPrefWidth(w);
            valueArea.setLayoutX(0);
            valueArea.setLayoutY(h * 0.35);

            description.setPrefWidth(w * 0.9);
            description.relocate(w * 0.05, h * 0.7);
        }

        @Override
        protected void redraw() {
            super.redraw();

            titleText.setText(tile.getTitle());
            titleText.setFill(tile.getTitleColor());

            valueText.setText(String.format(locale, formatString, tile.getCurrentValue()));
            valueText.setFill(tile.getValueColor());

            unitText.setText(tile.getUnit());
            unitText.setFill(tile.getUnitColor());

            description.setText(tile.getDescription());
            description.setTextFill(tile.getDescriptionColor());
        }
    }

    public void refrescarTablaNaves(String tipo) {
        tablaNaves.setItems(dameListaNaves(tipo));
        tablaNaves.refresh();
    }

    public void refrescarTablaFlotas(String tipo) {
        tablaFlotas.setItems(dameListaFlotas(tipo));
        tablaFlotas.refresh();
    }

    private void mostrarRankingTop10() {

        obtenerRanking();

        panelTilesFX.getChildren().clear();

        for (int i = 0; i < Math.min(10, nombres.size()); i++) {
            Tile tile = TileBuilder.create()
                    .skinType(Tile.SkinType.CUSTOM)
                    .title(nombres.get(i))
                    .unit("naves")
                    .decimals(0)
                    .textColor(Color.BLACK)
                    .titleColor(Color.BLACK)     
                    .unitColor(Color.BLACK)
                    .valueColor(Color.BLACK)
                    .prefSize(210, 100)
                    .maxSize(210, 100)
                    .value(totales.get(i))
                    .build();
                tile.setSkin(new MiNumberTileSkin(tile));
            switch (i) {
                case 0 -> tile.setBackgroundColor(Color.web("#FFD700")); // Oro
                case 1 -> tile.setBackgroundColor(Color.web("#C0C0C0")); // Plata
                case 2 -> tile.setBackgroundColor(Color.web("#CD7F32")); // Bronce
                case 3, 4 -> tile.setBackgroundColor(Color.web("#3498DB")); // Azul
                case 5, 6, 7 -> tile.setBackgroundColor(Color.web("#50C878")); // Esmeralda
                case 8, 9 -> tile.setBackgroundColor(Color.web("#E0115F")); // Rubí
            }
            panelTilesFX.getChildren().add(tile);
        }
    }

    @FXML
    void cerrarSesion(ActionEvent event) {
        Sesion.cerrarSesion();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaLogin.fxml"));
            Parent root = loader.load();

            Stage stagePrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stagePrincipal.setScene(new Scene(root));
            stagePrincipal.setTitle("Fleet Designer");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void listaAcorazado(ActionEvent event) {
        dameListaNaves("Acorazado");
        cargarBanner(bannerNaves, "bannerAcorazado");
    }

    @FXML
    void listaColoso(ActionEvent event) {
        dameListaNaves("Coloso");
        cargarBanner(bannerNaves, "bannerColoso");
    }

    @FXML
    void listaCorveta(ActionEvent event) {
        dameListaNaves("Corveta");
        cargarBanner(bannerNaves, "bannerCorveta");
    }

    @FXML
    void listaCrucero(ActionEvent event) {
        dameListaNaves("Crucero");
        cargarBanner(bannerNaves, "bannerCrucero");
    }

    @FXML
    void listaDestructor(ActionEvent event) {
        dameListaNaves("Destructor");
        cargarBanner(bannerNaves, "bannerDestructor");
    }

    @FXML
    void listaFragata(ActionEvent event) {
        dameListaNaves("Fragata");
        cargarBanner(bannerNaves, "bannerFragata");
    }

    @FXML
    void listaGlobal(ActionEvent event) {
        paneListaRanking.toFront();
        paneListaRanking.setVisible(true);
        paneListaNaves.setVisible(false);
        paneListaFlotas.setVisible(false);

        bannerNaves.setVisible(false);
        bannerFlotas.setVisible(false);

        panelNuevaNave.setVisible(false);
        panelNuevaFlota.setVisible(false);

        panelGlobal.setVisible(true);
        panelPersonal.setVisible(false);
        panelTablaNaves.setVisible(false);
        panelTablaFlotas.setVisible(false);

        cargarRankingGlobal();
    }

    @FXML
    void listaImperio(ActionEvent event) {
        dameListaFlotas("Imperio");
        cargarBanner(bannerFlotas, "bannerImperio");
    }

    @FXML
    void listaInsignia(ActionEvent event) {
        dameListaNaves("Insignia");
        cargarBanner(bannerNaves, "bannerInsignia");
    }

    @FXML
    void listaMercenarios(ActionEvent event) {
        dameListaFlotas("Mercenarios");
        cargarBanner(bannerFlotas, "bannerMercenarios");
    }

    @FXML
    void listaPersonal(ActionEvent event) {
        paneListaRanking.toFront();
        paneListaRanking.setVisible(true);
        paneListaNaves.setVisible(false);
        paneListaFlotas.setVisible(false);

        bannerNaves.setVisible(false);
        bannerFlotas.setVisible(false);

        panelNuevaNave.setVisible(false);
        panelNuevaFlota.setVisible(false);

        panelPersonal.setVisible(true);
        panelGlobal.setVisible(false);
        panelTablaNaves.setVisible(false);
        panelTablaFlotas.setVisible(false);

        cargarPanelPersonal();
        cargarIconosTipos();
    }

    @FXML
    void listaPiratas(ActionEvent event) {
        dameListaFlotas("Piratas");
        cargarBanner(bannerFlotas, "bannerPiratas");
    }

    @FXML
    void listaRebeldes(ActionEvent event) {
        dameListaFlotas("Rebeldes");
        cargarBanner(bannerFlotas, "bannerRebeldes");
    }

    @FXML
    void listaRepublica(ActionEvent event) {
        dameListaFlotas("Republica");
        cargarBanner(bannerFlotas, "bannerRepublica");
    }

    @FXML
    void listaSeparatistas(ActionEvent event) {
        dameListaFlotas("Separatistas");
        cargarBanner(bannerFlotas, "bannerSeparatistas");
    }

    @FXML
    void listaTitan(ActionEvent event) {
        dameListaNaves("Titan");
        cargarBanner(bannerNaves, "bannerTitan");
    }

    @FXML
    void nuevaFlota(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaNuevaFlota.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/estilos/estiloValidacionNaves.css").toExternalForm());

            Stage modal = new Stage();

            if(!modal.getIcons().contains(new Image(
                getClass().getResource("/icons/IconoAPP.png").toExternalForm()
            ))){
                modal.getIcons().add(new Image(
                getClass().getResource("/icons/IconoAPP.png").toExternalForm()
            ));
            }

            modal.setTitle("Fleet Designer");
            modal.setScene(scene);
            modal.initModality(Modality.APPLICATION_MODAL);

            Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modal.initOwner(parentStage);

            modal.showAndWait();

            mostrarFlotas("Imperio");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void nuevaNave(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaNuevaNave.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/estilos/estiloValidacionNaves.css").toExternalForm());

            Stage modal = new Stage();

            if(!modal.getIcons().contains(new Image(
                getClass().getResource("/icons/IconoAPP.png").toExternalForm()
            ))){
                modal.getIcons().add(new Image(
                getClass().getResource("/icons/IconoAPP.png").toExternalForm()
            ));
            }

            modal.setTitle("Fleet Designer");
            modal.setScene(scene);

            modal.initModality(Modality.APPLICATION_MODAL);

            Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modal.initOwner(parentStage);
            modal.showAndWait();
            mostrarNaves("Corveta");

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

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/estilos/estiloValidacionNaves.css").toExternalForm());

            Stage stagePrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();

            controller.setStagePrincipal(stagePrincipal);

            Stage modal = new Stage();

            if(!modal.getIcons().contains(new Image(
                getClass().getResource("/icons/IconoAPP.png").toExternalForm()
            ))){
                modal.getIcons().add(new Image(
                getClass().getResource("/icons/IconoAPP.png").toExternalForm()
            ));
            }

            modal.setScene(scene);
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

        panelNuevaNave.setVisible(false);
        panelNuevaFlota.setVisible(false);

        panelGlobal.setVisible(true);
        panelPersonal.setVisible(false);
        panelTablaNaves.setVisible(false);
        panelTablaFlotas.setVisible(false);

        cargarRankingGlobal();
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

        panelNuevaFlota.toFront();
        panelNuevaFlota.setVisible(true);
        panelNuevaNave.setVisible(false);

        panelTablaFlotas.toFront();
        panelTablaFlotas.setVisible(true);
        panelTablaNaves.setVisible(false);
        panelGlobal.setVisible(false);
        panelPersonal.setVisible(false);

        mostrarFlotas("Imperio");
        cargarBanner(bannerFlotas, "bannerImperio");
        cargarIconosLaterales("flotas");
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

        panelNuevaNave.toFront();
        panelNuevaNave.setVisible(true);
        panelNuevaFlota.setVisible(false);

        panelTablaNaves.toFront();
        panelTablaNaves.setVisible(true);
        panelTablaFlotas.setVisible(false);
        panelGlobal.setVisible(false);
        panelPersonal.setVisible(false);

        mostrarNaves("Corveta");
        cargarBanner(bannerNaves, "bannerCorveta");
        cargarIconosLaterales("naves");
    }

    public void mostrarNaves(String tipoNave) {
        tablaNaves.setItems(this.dameListaNaves(tipoNave));
    }

    public ObservableList<Nave> dameListaNaves(String tipoNave) {

        String query = "SELECT n.* FROM nave n " +
                "JOIN usuario u_owner ON n.id_usuario = u_owner.id_usuario " +
                "WHERE n.tipo = ? " +
                "AND (n.id_usuario = ? OR u_owner.es_admin = 1)";

        try (PreparedStatement pst = conexion.prepareStatement(query)) {

            pst.setInt(2, u.getId_usuario());
            pst.setString(1, tipoNave);
            rs = pst.executeQuery();

            listaNaves.clear();

            while (rs.next()) {
                Nave nave = new Nave(
                        rs.getInt("id_nave"),
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("tipo"),
                        rs.getString("propulsion"),
                        rs.getString("sistema_defensivo"),
                        rs.getString("armadura"),
                        rs.getString("escudo"),
                        rs.getString("armamento"),
                        rs.getString("imagen"));
                listaNaves.add(nave);
            }

        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
        }
        return listaNaves;
    }

    public void mostrarFlotas(String tipoFlota) {
        tablaFlotas.setItems(this.dameListaFlotas(tipoFlota));
    }

    public ObservableList<Flota> dameListaFlotas(String tipoFlota) {
        if (conexion != null) {
            listaFlotas.clear();

            String query = "SELECT f.id_flota, f.id_usuario, f.nombre, f.faccion, SUM(f.cantidad) AS cantidad_total " +
                    "FROM flota f " +
                    "JOIN usuario u_owner ON f.id_usuario = u_owner.id_usuario " +
                    "WHERE f.faccion = ? " +
                    "  AND (f.id_usuario = ? OR u_owner.es_admin = 1) " +
                    "GROUP BY f.id_flota, f.id_usuario, f.nombre, f.faccion;";

            try (PreparedStatement pst = conexion.prepareStatement(query)) {

                pst.setString(1, tipoFlota);
                pst.setInt(2, u.getId_usuario());

                rs = pst.executeQuery();

                while (rs.next()) {
                    Flota flota = new Flota(rs.getInt("id_flota"),
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("faccion"),
                            rs.getInt("cantidad_total"));
                    listaFlotas.add(flota);
                }
            } catch (SQLException var3) {
                System.out.println("Excepción SQL: " + var3.getMessage());
            }
            return listaFlotas;
        } else {
            return null;
        }
    }

    public ObservableList<String> generarIconoTipoNaveFlota(int id_flota) {
        ObservableList<String> tipos = FXCollections.observableArrayList();
        if (conexion != null) {

            String query = "SELECT tipo FROM nave JOIN flota USING (id_nave) where id_flota=?";

            try (PreparedStatement pst = conexion.prepareStatement(query)) {

                pst.setInt(1, id_flota);
                rs = pst.executeQuery();

                while (rs.next()) {
                    String tipo = rs.getString("tipo");
                    if (!tipos.contains(tipo)) {
                        tipos.add(tipo);
                    }

                }
            } catch (SQLException e) {
                System.out.println("SQL Error: " + e.getMessage());
            }

            return tipos;
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

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/estilos/estiloValidacionNaves.css").toExternalForm());

            Stage modal = new Stage();

            if(!modal.getIcons().contains(new Image(
                getClass().getResource("/icons/IconoAPP.png").toExternalForm()
            ))){
                modal.getIcons().add(new Image(
                getClass().getResource("/icons/IconoAPP.png").toExternalForm()
            ));
            }

            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setScene(scene);
            modal.setTitle("Fleet Designer");

            modal.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void borrarNave(Nave nave) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaEliminar.fxml"));
            Parent root = loader.load();

            controladorEliminar controller = loader.getController();

            controller.cargar(nave);
            controller.setControladorPrincipal(this);

            Stage modal = new Stage();

            if(!modal.getIcons().contains(new Image(
                getClass().getResource("/icons/IconoAPP.png").toExternalForm()
            ))){
                modal.getIcons().add(new Image(
                getClass().getResource("/icons/IconoAPP.png").toExternalForm()
            ));
            }

            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setScene(new Scene(root));
            modal.setTitle("Fleet Designer");

            modal.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editarFlota(Flota flota) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaEditarFlota.fxml"));
            Parent root = loader.load();

            controladorEditarFlota controller = loader.getController();
            controller.cargarFlotaExistente(flota);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/estilos/estiloValidacionNaves.css").toExternalForm());

            Stage modal = new Stage();

            if(!modal.getIcons().contains(new Image(
                getClass().getResource("/icons/IconoAPP.png").toExternalForm()
            ))){
                modal.getIcons().add(new Image(
                getClass().getResource("/icons/IconoAPP.png").toExternalForm()
            ));
            }

            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setScene(scene);
            modal.setTitle("Fleet Designer");

            modal.showAndWait();

            refrescarTablaFlotas(flota.getFaccion());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void borrarFlota(Flota flota) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaEliminar.fxml"));
            Parent root = loader.load();

            controladorEliminar controller = loader.getController();

            controller.cargar(flota);
            controller.setControladorPrincipal(this);

            Stage modal = new Stage();

            if(!modal.getIcons().contains(new Image(
                getClass().getResource("/icons/IconoAPP.png").toExternalForm()
            ))){
                modal.getIcons().add(new Image(
                getClass().getResource("/icons/IconoAPP.png").toExternalForm()
            ));
            }

            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setScene(new Scene(root));
            modal.setTitle("Fleet Designer");

            modal.showAndWait();

            refrescarTablaFlotas(flota.getFaccion());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void obtenerRanking() {
        nombres.clear();
        totales.clear();
        ids.clear();

        String query = "SELECT u.id_usuario, u.nombre_usuario, COUNT(n.id_nave) AS total_naves " +
                "FROM usuario u " +
                "LEFT JOIN nave n ON u.id_usuario = n.id_usuario " +
                "GROUP BY u.id_usuario, u.nombre_usuario " +
                "ORDER BY total_naves DESC";

        try (PreparedStatement pst = conexion.prepareStatement(query);
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                ids.add(rs.getInt("id_usuario"));
                nombres.add(rs.getString("nombre_usuario"));
                totales.add(rs.getInt("total_naves"));
            }

        } catch (SQLException e) {
            System.out.println("ERROR RANKING: " + e.getMessage());
        }
    }

    private void cargarRankingGlobal() {

        obtenerRanking();

        primeroRankingFoto.setImage(new Image(getClass().getResourceAsStream("/icons/iconoCopaOro.png")));
        segundoRankingFoto.setImage(new Image(getClass().getResourceAsStream("/icons/iconoCopaPlata.png")));
        terceroRankingFoto.setImage(new Image(getClass().getResourceAsStream("/icons/iconoCopaBronce.png")));
        

        if (nombres.size() > 0)
            primeroRanking.setText(nombres.get(0) + " ha creado " + totales.get(0) + " naves");
        else
            primeroRanking.setText("");

        if (nombres.size() > 1)
            segundoRanking.setText(nombres.get(1) + " ha creado " + totales.get(1) + " naves");
        else
            segundoRanking.setText("");

        if (nombres.size() > 2)
            terceroRanking.setText(nombres.get(2) + " ha creado " + totales.get(2) + " naves");
        else
            terceroRanking.setText("");

        StringBuilder sb = new StringBuilder();

        for (int i = 3; i < nombres.size(); i++) {
            sb.append(nombres.get(i))
                    .append(" ha creado ")
                    .append(totales.get(i))
                    .append(" naves\n");
        }

        listadoUser.setText(sb.toString());
        mostrarRankingTop10();
    }

    private void cargarPanelPersonal() {

        String query = "SELECT tipo, COUNT(*) AS total " +
                "FROM nave " +
                "WHERE id_usuario = ? " +
                "GROUP BY tipo";

        try (PreparedStatement pst = conexion.prepareStatement(query)) {

            pst.setInt(1, u.getId_usuario());
            ResultSet rs = pst.executeQuery();

            nNavesCorveta.setText("Has creado 0 naves");
            nNavesCrucero.setText("Has creado 0 naves");
            nNavesDestructor.setText("Has creado 0 naves");
            nNavesFragata.setText("Has creado 0 naves");
            nNavesInsignia.setText("Has creado 0 naves");
            nNavesAcorazado.setText("Has creado 0 naves");
            nNavesTitan.setText("Has creado 0 naves");
            nNavesColoso.setText("Has creado 0 naves");

            while (rs.next()) {

                String tipo = rs.getString("tipo");
                int total = rs.getInt("total");

                switch (tipo) {
                    case "Corveta":
                        nNavesCorveta.setText("Has creado " + String.valueOf(total) + " naves");
                        break;
                    case "Fragata":
                        nNavesFragata.setText("Has creado " + String.valueOf(total) + " naves");
                        break;
                    case "Destructor":
                        nNavesDestructor.setText("Has creado " + String.valueOf(total) + " naves");
                        break;
                    case "Crucero":
                        nNavesCrucero.setText("Has creado " + String.valueOf(total) + " naves");
                        break;
                    case "Acorazado":
                        nNavesAcorazado.setText("Has creado " + String.valueOf(total) + " naves");
                        break;
                    case "Titan":
                        nNavesTitan.setText("Has creado " + String.valueOf(total) + " naves");
                        break;
                    case "Coloso":
                        nNavesColoso.setText("Has creado " + String.valueOf(total) + " naves");
                        break;
                    case "Insignia":
                        nNavesInsignia.setText("Has creado " + String.valueOf(total) + " naves");
                        break;
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR PANEL PERSONAL: " + e.getMessage());
        }
    }

    private void cargarIconosTipos() {
        iconoCorveta.setImage(new Image(getClass().getResourceAsStream("/icons/corveta.png")));
        iconoFragata.setImage(new Image(getClass().getResourceAsStream("/icons/fragata.png")));
        iconoDestructor.setImage(new Image(getClass().getResourceAsStream("/icons/destructor.png")));
        iconoCrucero.setImage(new Image(getClass().getResourceAsStream("/icons/crucero.png")));
        iconoAcorazado.setImage(new Image(getClass().getResourceAsStream("/icons/acorazado.png")));
        iconoTitan.setImage(new Image(getClass().getResourceAsStream("/icons/titán.png")));
        iconoColoso.setImage(new Image(getClass().getResourceAsStream("/icons/coloso.png")));
        iconoInsignia.setImage(new Image(getClass().getResourceAsStream("/icons/insignia.png")));
    }

    private void cargarBanner(Pane banner, String nombreImagen) {
        Image img = new Image(getClass().getResourceAsStream("/banners/" + nombreImagen + ".png"));

        ImageView view = new ImageView(img);

        view.fitWidthProperty().bind(banner.widthProperty());
        view.fitHeightProperty().bind(banner.heightProperty());

        view.setPreserveRatio(false);

        banner.getChildren().setAll(view);
    }

    private void cargarIconosLaterales(String modo) {
        if (modo.equals("naves")) {
            cargarIconoLateral(iconoCorvetaLateral, "corveta");
            cargarIconoLateral(iconoFragataLateral, "fragata");
            cargarIconoLateral(iconoDestructorLateral, "destructor");
            cargarIconoLateral(iconoCruceroLateral, "crucero");
            cargarIconoLateral(iconoAcorazadoLateral, "acorazado");
            cargarIconoLateral(iconoTitanLateral, "titán");
            cargarIconoLateral(iconoColosoLateral, "coloso");
            cargarIconoLateral(iconoInsigniaLateral, "insignia");

        } else if (modo.equals("flotas")) {
            cargarIconoLateral(iconoImperioLateral, "bannerImperioIcono");
            cargarIconoLateral(iconoRebeldesLateral, "bannerRebeldesIcono");
            cargarIconoLateral(iconoPiratasLateral, "bannerPiratasIcono");
            cargarIconoLateral(iconoRepublicaLateral, "bannerRepublicaIcono");
            cargarIconoLateral(iconoSeparatistasLateral, "bannerSeparatistasIcono");
            cargarIconoLateral(iconoMercenariosLateral, "bannerMercenariosIcono");

        }
    }

    private void cargarIconoLateral(ImageView view, String nombre) {
        InputStream is = getClass().getResourceAsStream("/icons/" + nombre + ".png");
        if (is != null) {
            view.setImage(new Image(is));
            view.setVisible(true);
        } else {
            view.setVisible(false);
        }
    }



    private Image cargarIconoPorTipo(String tipo) {
        
        String file = "/icons/" + tipo.toLowerCase() + ".png";
        InputStream is = getClass().getResourceAsStream(file);

        return new Image(is);
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

        iconoOpciones.setImage(new Image(getClass().getResourceAsStream("/icons/iconoOpcionesUsuario.png")));
        iconoCerrarSesion.setImage(new Image(getClass().getResourceAsStream("/icons/iconoLogOut.png")));

        Circle clipOpciones = new Circle(iconoOpciones.getFitWidth() / 2, iconoOpciones.getFitHeight() / 2, 35);
        iconoOpciones.setClip(clipOpciones);

        Circle clipCerrar = new Circle(iconoCerrarSesion.getFitWidth() / 2, iconoCerrarSesion.getFitHeight() / 2, 35);
        iconoCerrarSesion.setClip(clipCerrar);

        gifNuevaNave.setImage(new Image(getClass().getResourceAsStream("/gif/gifNuevaNave.gif")));
        gifNuevaFlota.setImage(new Image(getClass().getResourceAsStream("/gif/gifNuevaFlota.gif")));
 
        listaNaves = dameListaNaves("Corveta");
        cargarBanner(bannerNaves, "bannerCorveta");
        cargarIconosLaterales("naves");

        String cellStyle = "-fx-alignment: CENTER; -fx-font-size: 12pt;";
        nombreNave.setStyle(cellStyle);
        tipoNave.setStyle(cellStyle);
        potenciaNave.setStyle(cellStyle);

        nombreNave.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tipoNave.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        potenciaNave
                .setCellValueFactory(cellData -> new SimpleStringProperty(calcularPotenciaNave(cellData.getValue())));

        tablaNaves.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        tablaNaves.setRowFactory(tv -> {
            TableRow<Nave> row = new TableRow<>();
            row.setPrefHeight(80);
            row.setMaxHeight(80);
            return row;
        });

        fotoNave.setCellFactory(column -> new TableCell<Nave, String>() {
            private final ImageView imageView = new ImageView();
            {
                setAlignment(Pos.CENTER);
            }

            @Override
            protected void updateItem(String base64Image, boolean empty) {
                super.updateItem(base64Image, empty);

                if (empty || base64Image == null || base64Image.isEmpty()) {
                    setGraphic(null);
                    return;
                }

                try {
                    byte[] bytes = java.util.Base64.getDecoder().decode(base64Image);
                    Image img = new Image(new java.io.ByteArrayInputStream(bytes));

                    imageView.setImage(img);
                    imageView.setFitHeight(70);
                    imageView.setFitWidth(120);
                    imageView.setPreserveRatio(true);

                    setGraphic(imageView);
                } catch (Exception e) {
                    setGraphic(null);
                }
            }
        });

        fotoNave.setCellValueFactory(new PropertyValueFactory<>("imagen"));
        botonesNave.setCellFactory(col -> new TableCell<>() {
            private final ImageView iconEditar = new ImageView(
                    new Image(getClass().getResourceAsStream("/icons/iconoOpcionesNave.png")));
            private final ImageView iconBorrar = new ImageView(
                    new Image(getClass().getResourceAsStream("/icons/iconoBorrarNave.png")));
            private final Button btnEditar = new Button("", iconEditar);
            private final Button btnBorrar = new Button("", iconBorrar);
            private final HBox contenedor = new HBox(6, btnEditar, btnBorrar);

            {
                iconEditar.setFitWidth(60);
                iconEditar.setFitHeight(60);
                iconBorrar.setFitWidth(60);
                iconBorrar.setFitHeight(60);

                contenedor.setAlignment(Pos.CENTER);

                btnEditar.setOnAction(event -> {
                    Nave nave = getTableView().getItems().get(getIndex());
                    editarNave(nave);
                    refrescarTablaNaves(nave.getTipo());
                });

                btnBorrar.setOnAction(event -> {
                    Nave nave = getTableView().getItems().get(getIndex());
                    borrarNave(nave);
                });

                contenedor.setAlignment(Pos.CENTER);
            }

            @Override
            protected void updateItem(HBox item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                Nave nave = getTableView().getItems().get(getIndex());
                if (nave.getId_usuario() != Sesion.getUsuario().getId_usuario()) {
                    setGraphic(null);
                    return;
                }

                setGraphic(contenedor);
            }
        });

        tablaNaves.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Nave nave, boolean empty) {
                super.updateItem(nave, empty);
                setPrefHeight(80);
                setMaxHeight(80);
                if (empty || nave == null) {
                    setStyle("");
                } else if (nave.getId_usuario() != Sesion.getUsuario().getId_usuario()) {
                    setStyle("-fx-background-color: #FFF176;");
                } else {
                    setStyle("");
                }
            }
        });

        tablaNaves.setItems(listaNaves);

        listaFlotas = dameListaFlotas("Imperio");

        nombreFlota.setStyle(cellStyle);
        navesTotalesFlota.setStyle(cellStyle);

        tablaFlotas.setRowFactory(tv -> {
            TableRow<Flota> row = new TableRow<>();
            row.setMinHeight(80);
            row.setPrefHeight(80);
            row.setMaxHeight(80);
            return row;
        });

        tablaFlotas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        nombreFlota.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tiposFlota.setCellFactory(col -> new TableCell<Flota, Void>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getTableRow().getItem() == null) {
                    setGraphic(null);
                    return;
                }

                Flota flota = getTableRow().getItem();

                ObservableList<String> tipos = generarIconoTipoNaveFlota(flota.getId_flota());

                List<String> orden = List.of("Corveta","Fragata","Destructor","Crucero","Acorazado","Titán","Coloso","Insignia");

                tipos.sort(Comparator.comparingInt(orden::indexOf));

                FlowPane flow = new FlowPane();
                flow.setHgap(4);
                flow.setVgap(0);
                flow.setPrefWrapLength(262.5);
                flow.setAlignment(Pos.CENTER_LEFT);

                for (String tipo : tipos) {
                    ImageView img = new ImageView(cargarIconoPorTipo(tipo));

                    img.setFitWidth(60);
                    img.setFitHeight(40);

                    flow.getChildren().add(img);
                }

                setGraphic(flow);
            }
        });
        navesTotalesFlota.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        botonesFlota.setCellFactory(col -> new TableCell<>() {
            private final ImageView iconEditar = new ImageView(
                    new Image(getClass().getResourceAsStream("/icons/iconoOpcionesNave.png"))
                );
            private final ImageView iconBorrar = new ImageView(
                    new Image(getClass().getResourceAsStream("/icons/iconoBorrarNave.png"))
                );

            private final Button btnEditar = new Button("", iconEditar);
            private final Button btnBorrar = new Button("", iconBorrar);
            private final HBox contenedor = new HBox(6, btnEditar, btnBorrar);

            {
                iconEditar.setFitWidth(60);
                iconEditar.setFitHeight(60);
                iconBorrar.setFitWidth(60);
                iconBorrar.setFitHeight(60);

                contenedor.setAlignment(Pos.CENTER);

                btnEditar.setOnAction(event -> {
                    Flota flota = getTableView().getItems().get(getIndex());
                    editarFlota(flota);
                    refrescarTablaFlotas(flota.getFaccion());
                });

                btnBorrar.setOnAction(event -> {
                    Flota flota = getTableView().getItems().get(getIndex());
                    borrarFlota(flota);
                });
            }

            @Override
            protected void updateItem(HBox item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                Flota flota = getTableView().getItems().get(getIndex());
                int idActual = Sesion.getUsuario().getId_usuario();

                if (flota.getId_usuario() != idActual) {
                    setGraphic(null);
                    return;
                }

                setGraphic(contenedor);
            }
        });

        tablaFlotas.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Flota flota, boolean empty) {
                super.updateItem(flota, empty);
                setPrefHeight(80);
                setMaxHeight(80);
                if (empty || flota == null) {
                    setStyle("");
                } else if (flota.getId_usuario() != Sesion.getUsuario().getId_usuario()) {
                    setStyle("-fx-background-color: #FFF176;");
                } else {
                    setStyle("");
                }
            }
        });
        
        
        tablaFlotas.setItems(listaFlotas);



        Button[] botonesTipos = new Button[] {
            btnAcorazado, btnColoso, btnCorveta, btnCrucero,
            btnDestructor, btnFragata, btnInsignia, btnTitan,
            btnImperio, btnRebeldes, btnPiratas, btnRepublica, 
            btnSeparatistas, btnMercenarios, btnGlobal, btnPersonal
        };

        for (Button btn : botonesTipos) {
            DropShadow sombra = new DropShadow(10, Color.GRAY);

            btn.setOnMouseEntered(e -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(200), btn);
                st.setToX(1.15);
                st.setToY(1.05);
                st.play();

                btn.setEffect(sombra);
            });

            btn.setOnMouseExited(e -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(200), btn);
                st.setToX(1.0);
                st.setToY(1.0);
                st.play();

                btn.setEffect(null);
            });
        }

        Platform.runLater(() -> {
            Scene scene = btnVentanaNaves.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.Q, KeyCombination.ALT_DOWN),
                () -> btnVentanaNaves.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnVentanaFlotas.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.W, KeyCombination.ALT_DOWN),
                () -> btnVentanaFlotas.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnVentanaRanking.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.E, KeyCombination.ALT_DOWN),
                () -> btnVentanaRanking.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnOpciones.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.O, KeyCombination.ALT_DOWN),
                () -> btnOpciones.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnCerrarSesion.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.P, KeyCombination.ALT_DOWN),
                () -> btnCerrarSesion.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnNuevaNave.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.N, KeyCombination.ALT_DOWN),
                () -> btnNuevaNave.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnNuevaFlota.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN),
                () -> btnNuevaFlota.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnCorveta.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN),
                () -> btnCorveta.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnFragata.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN),
                () -> btnFragata.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnDestructor.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN),
                () -> btnDestructor.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnCrucero.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN),
                () -> btnCrucero.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnAcorazado.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN),
                () -> btnAcorazado.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnTitan.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN),
                () -> btnTitan.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnColoso.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.J, KeyCombination.CONTROL_DOWN),
                () -> btnColoso.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnInsignia.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.K, KeyCombination.CONTROL_DOWN),
                () -> btnInsignia.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnImperio.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN),
                () -> btnImperio.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnRebeldes.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN),
                () -> btnRebeldes.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnPiratas.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN),
                () -> btnPiratas.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnRepublica.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN),
                () -> btnRepublica.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnSeparatistas.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN),
                () -> btnSeparatistas.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnMercenarios.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN),
                () -> btnMercenarios.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnGlobal.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN),
                () -> btnGlobal.fire()
            );
        });

        Platform.runLater(() -> {
            Scene scene = btnPersonal.getScene();
            scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN),
                () -> btnPersonal.fire()
            );
        });
    }
}
