package com.javafx.modelos;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class PiezasNaves {

    public static final List<String> ARMADURAS = List.of(
        "Blindaje de Titanio Cuántico",
        "Placas de Neoduro Reforzado",
        "Armadura Grafénica Multicapa",
        "Revestimiento Cerámico Ablativo",
        "Blindaje Orgánico Autorregenerativo",
        "Chasis de Aleación Mithril-Omega",
        "Armadura Cristalina Prismática",
        "Blindaje Térmico Antiplasma",
        "Revestimiento Camaleónico Sigiloso",
        "Armadura de Nanobots Reconstituyentes",
        "Blindaje Magneto-Reactivo",
        "Placas de Acero Estelar Antiguo"
    );

    public static final List<String> ARMAMENTO = List.of(
        "Cañón de Plasma Pesado",
        "Lanzarrayos Fotónicos",
        "Misiles Teledirigidos de Antimateria",
        "Cañón Gauss Orbital",
        "Torpedos Warp",
        "Láser de Rayos Gamma",
        "Ametralladora Iónica Rotatoria",
        "Lanzador de Micro-Meteoritos",
        "Batería de Rayos Tractor-Inversos Ofensivos",
        "Cañón de Partículas Oscilantes",
        "Generador de Descargas de Vacío",
        "Lanzador de Fulgor Solar",
        "Misiles de Fragmentación Cuántica",
        "Cañón Tormenta Eléctrica",
        "Lanzamisiles Gravitacionales",
        "Rayo Desintegrador Molecular",
        "Cañón Sónico de Largo Alcance",
        "Torpedos de Energía Comprimida",
        "Mortero de Plasma en Racimo",
        "Rayo Congelante Criocósmico",
        "Cañón Rúnico de Energía Arcana",
        "Proyector de Agujeros Negros Miniatura",
        "Acelerador Masivo de Antimateria",
        "Cañón de Luz Pulsante",
        "Lanzador de Insectoides Biomecánicos",
        "Lanzador Corrosivo de Ácido Estelar",
        "Cañón de Magneto-Tracción Ofensiva",
        "Batería Automática de Drones Kamikaze",
        "Lanzador de Trampas Cuánticas",
        "Cañón de Choque Cinético Extremo",
        "Bombas Temporales Inestables",
        "Minigun Fotónica Dual",
        "Torreta de Misiles Serpentinos",
        "Rayo Purificador de Radiación",
        "Lanzador de Nanoplaga",
        "Cañón de Vórtice de Vacío",
        "Disruptor de Materia Oscura",
        "Lanzaestrellas de Tungsteno",
        "Torpedo Psiónico",
        "Cañón de Energía Oscura",
        "Lanzador de Micro-Haces Supercalientes",
        "Cañón de Deformación Espacial",
        "Lanzarrayos de Antimateria Líquida",
        "Arpón Electromagnético de Abordaje",
        "Lanzador de Cuchillas de Plasma",
        "Batería Cuádruple de Rayos Láser",
        "Cañón Nuclear Miniaturizado",
        "Esfera de Aniquilación Condensada",
        "Lanzador de Torbellinos Gravitacionales",
        "Cañón de Relámpagos Cuánticos"
    );

    public static final List<String> ESCUDOS = List.of(
        "Escudo Deflector de Plasma",
        "Campo de Energía Reforzada",
        "Escudo Fotónico Omnidireccional",
        "Burbuja Gravitatoria Defensiva",
        "Campo de Distorsión Espacial",
        "Escudo Reflectante Hexacristalino",
        "Esfera Antimateria de Contención",
        "Campo Iónico Disipador",
        "Escudo de Barrera Temporal",
        "Campo Prismático Desvía-Láseres",
        "Escudo de Absorción Cinética",
        "Revestimiento Energético de Auto-Recarga"
    );

    public static final List<String> PROPULSIONES = List.of(
        "Motor Iónico de Alta Eficiencia",
        "Propulsor Warp Interdimensional",
        "Motor de Antimateria Híbrido",
        "Propulsión Gravitatoria Inversa",
        "Motores de Curvatura Lumínica",
        "Propulsor de Plasma Vectorial",
        "Propulsión de Salto Cuántico",
        "Sistema de Microfusión Dual",
        "Motores de Vela Solar Reforzada",
        "Propulsores de Energía Oscura",
        "Motor Taquiónico Experimental"
    );

    public static final List<String> SISTEMA_DEFENSIVO = List.of(
        "Contramedidas Electrónicas Avanzadas",
        "Señuelos Holográficos Dinámicos",
        "Sistema de Interferencia de Misiles",
        "Disipador Térmico de Baja Firma",
        "Generador de Niebla Electromagnética",
        "Barrera de Drones Interceptores",
        "Emisor de Ondas de Choque Defensivas",
        "Sistema de Cancelación de Trayectoria",
        "Oscurecedor Óptico Cuántico",
        "Repetidor de Campo Protector Secundario",
        "Neutralizador de Energía Dirigida"
    );

    public static List<String> getArmaduras() {
        return ARMADURAS;
    }

    public static List<String> getArmamento() {
        return ARMAMENTO;
    }

    public static List<String> getEscudos() {
        return ESCUDOS;
    }

    public static List<String> getPropulsiones() {
        return PROPULSIONES;
    }

    public static List<String> getSistemaDefensivo() {
        return SISTEMA_DEFENSIVO;
    }

    public static void enableAutoComplete(ComboBox<String> combo, ObservableList<String> items) {

        combo.setItems(items);

        combo.getEditor().addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            String input = combo.getEditor().getText();
            switch (event.getCode()) {
                case UP, DOWN, LEFT, RIGHT, HOME, END, TAB, ENTER:
                    return;
                default:
                    break;
            }

            if (input == null || input.isEmpty()) {
                combo.setItems(items);
                combo.hide();
                combo.show();
                return;
            }

            ObservableList<String> filtered = FXCollections.observableArrayList();
            String inputLower = input.toLowerCase();

            for (String item : items) {
                if (item.toLowerCase().startsWith(inputLower)) {
                    filtered.add(item);
                }
            }

            combo.setItems(filtered);
            combo.getEditor().setText(input);
            combo.getEditor().positionCaret(input.length());

            combo.hide(); 
            if (!filtered.isEmpty()) {
                combo.show();
            }
        });
    }

    public static void validateComboBoxValue(ComboBox<String> combo, ObservableList<String> items, boolean obligatorio) {
        combo.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) { // cuando pierde focus
                String value = combo.getEditor().getText();

                if ((obligatorio && (value == null || !items.contains(value))) ||
                    (!obligatorio && value != null && !value.isEmpty() && !items.contains(value))) {

                    combo.getSelectionModel().clearSelection();
                    combo.getEditor().setText("");

                    // Forzar refresco de la lista desplegable
                    combo.hide();
                    combo.setItems(FXCollections.observableArrayList()); // limpiar temporalmente
                    combo.setItems(items); // reasignar items originales
                }
            }
        });
    }

    public static void quitarErrorAlEscribir(ComboBox<String> combo) {
        combo.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            combo.getStyleClass().remove("combo-box-error");
        });
    }

    public static void quitarErrorAlEscribir(TextField tf) {
        tf.textProperty().addListener((obs, oldVal, newVal) -> {
            tf.getStyleClass().remove("text-field-error");
        });
    }

    public static boolean validarCamposObligatorios(
        ComboBox<String> armadura,
        ComboBox<String> propulsion,
        ComboBox<String> sistemaDefensivo,
        ComboBox<String> escudo,
        TextField nombreNave,
        ComboBox<String>[] armamentos
    ) {
        boolean valido = true;

        
        armadura.getStyleClass().remove("combo-box-error");
        propulsion.getStyleClass().remove("combo-box-error");
        sistemaDefensivo.getStyleClass().remove("combo-box-error");
        escudo.getStyleClass().remove("combo-box-error");
        nombreNave.getStyleClass().remove("text-field-error");

        for (ComboBox<String> cb : armamentos) {
            cb.getStyleClass().remove("combo-box-error");
        }

        if (armadura.getValue() == null || !armadura.getItems().contains(armadura.getValue())) {
            armadura.getStyleClass().add("combo-box-error");
            valido = false;
        }
        if (propulsion.getValue() == null || !propulsion.getItems().contains(propulsion.getValue())) {
            propulsion.getStyleClass().add("combo-box-error");
            valido = false;
        }
        if (sistemaDefensivo.getValue() == null || !sistemaDefensivo.getItems().contains(sistemaDefensivo.getValue())) {
            sistemaDefensivo.getStyleClass().add("combo-box-error");
            valido = false;
        }
        if (escudo.getValue() == null || !escudo.getItems().contains(escudo.getValue())) {
            escudo.getStyleClass().add("combo-box-error");
            valido = false;
        }
        if (nombreNave.getText() == null || nombreNave.getText().isEmpty()) {
            nombreNave.getStyleClass().add("text-field-error");
            valido = false;
        }

        for (int i = 0; i < armamentos.length; i++) {
            ComboBox<String> cb = armamentos[i];
            String value = cb.getValue();
            if (i == 0) { 
                if (value == null || !cb.getItems().contains(value)) {
                    cb.getStyleClass().add("combo-box-error");
                    valido = false;
                }
            } else { 
                if (value != null && !value.isEmpty() && !cb.getItems().contains(value)) {
                    cb.getStyleClass().add("combo-box-error");
                    valido = false;
                }
            }
        }
        return valido;
    }
}