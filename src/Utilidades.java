package src;
//package src.Utilidades;

import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.sql.*;

public class Utilidades {
    private String mensajeError;

    //public Connection conexion;  // Declarar la conexión como variable de instancia

    public static void cargarCombo(ComboBox<ComboItem> combo, String sql, String campoVisible, String campoValor) {
        combo.getItems().clear();
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt(campoValor);
                String descripcion = rs.getString(campoVisible);
                combo.getItems().add(new ComboItem(id, descripcion));
            }
            
            System.out.println("Número de elementos cargados: " + combo.getItems().size());

        } catch (SQLException e) {
            System.out.println("Error cargando combo: " + e.getMessage());
        }
    }
    // Clase auxiliar para representar los ítems del ComboBox
    public static class ComboItem {
        private int id;
        private String descripcion;

        public ComboItem(int id, String descripcion) {
            this.id = id;
            this.descripcion = descripcion;
        }

        public int getId() {
            return id;
        }

        public String getDescripcion() {
            return descripcion;
        }

        @Override
        public String toString() {
            return descripcion;
        }
    }
    
    // Método que limpia múltiples TextFields y ComboBox
 public static void limpiarCampos(TextField[] textos, TextArea[] txtareas, 
 ComboBox<?>... combos) {
    try {
        System.out.println("Método limpiarCampos iniciado...");

        if (textos != null) {
            for (TextField campo : textos) {
                if (campo != null) {
                    campo.setText("");
                }
            }
        }

        if (txtareas != null) {
            for (TextArea campo : txtareas) {
                if (campo != null) {
                    campo.setText("");
                }
            }
        }

        if (combos != null) {
            for (ComboBox<?> combo : combos) {
                if (combo != null) {
                    combo.setValue(null);
                }
            }
        }

            System.out.println("Campos limpiados correctamente.");
        } catch (Exception e) {
            System.err.println("Error al limpiar los campos: " + e.getMessage());
            e.printStackTrace(); // Opcional: muestra más detalles del error
        }
    }

    public static void habilitarCampos(boolean habilitar, TextField[] textos, ComboBox<?>... combos) {
        for (TextField txt : textos) {
            if (txt != null) txt.setDisable(!habilitar);
        }
        for (ComboBox<?> cmb : combos) {
            if (cmb != null) cmb.setDisable(!habilitar);
        }
    }

    public static boolean verificarCampo(String tabla, String campo, String valor) {
        String sql = "SELECT COUNT(*) FROM " + tabla + " WHERE " + campo + " = ?";
        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971");
            PreparedStatement stmt = conexion.prepareStatement(sql)) {
                stmt.setString(1, valor);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count > 0) {
                        System.out.println("Ya existe un registro con " + campo + " = '" + valor + "' en la tabla " + tabla);
                        return true;
                    }
                 }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al verificar si existe el valor: " + valor);
        }
    
        return false; // No existe o ocurrió un error
    }
    
    public boolean validarComboBox(ComboBox<ComboItem> combo, String nombreCampo) {
        ComboItem itemSeleccionado = combo.getValue();
        if (itemSeleccionado == null) {
            mensajeError = "Debe seleccionar un valor para " + nombreCampo + ".";
            return false;
        }
        return true;
    }

    public boolean validarTextField(TextField txt, String nombreCampo) {
    
        if (txt.getText() == null || txt.getText().trim().isEmpty()) {
          mensajeError = "Debe ingresar un valor en " + nombreCampo + ".";
          return false;
        }
        return true;
    }

    // Función para mostrar el error
    public void mostrarError() {
        if (mensajeError != null && !mensajeError.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Se ha producido un error");
            alert.setContentText(mensajeError);
            alert.showAndWait();
        }
    }
    
    public static void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null); // No muestra encabezado extra
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    //public static void cambiarColorBoton(Button boton) {
   //     boton.setStyle("-fx-background-color: #ff9900;"); // Cambia el color a naranja

    //    PauseTransition pausa = new PauseTransition(Duration.seconds(0.3));
     //   pausa.setOnFinished(e -> boton.setStyle("")); // Restaura el estilo original
   //     pausa.play();
    //}

    public static void configurarEfectoPresionado(Button boton) {
        //cambiarColorBoton
        //if (boton != null) {
            //boton.setStyle("-fx-background-color: #ff9900;"); // Cambia el color a naranja

            //PauseTransition pausa = new PauseTransition(Duration.seconds(0.1));
            //pausa.setOnFinished(e -> boton.setStyle("")); // Regresa al color original
            //pausa.play();
            boton.setOnMousePressed(event -> boton.setStyle("-fx-background-color: red;")); // Color cuando se presiona
            boton.setOnMouseReleased(event -> boton.setStyle("-fx-background-color: orange;")); // Regresa al estilo original cuando se suelta
        //}
    }
}

