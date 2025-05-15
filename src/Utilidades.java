package src;
//package src.Utilidades;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.*;

public class Utilidades {

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
    public static void limpiarCampos(TextField[] textos, ComboBox<?>... combos) {
        if (textos != null) {
            for (TextField campo : textos) {
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
    

}
