package src;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import javafx.scene.control.TextField;
import javafx.fxml.FXML;


public class ClasePrestaciones {
    private Integer id_prestacion;
    private String nombre_prestacion;
    private Integer tipo_prestacion;

    private String mensajeError = "";

    // Constructor vacío
    public ClasePrestaciones() {}

    // Constructor con parámetros
    public ClasePrestaciones(Integer id, String nombre, Integer tipo) {
        this.id_prestacion = id;
        this.nombre_prestacion = nombre;
        this.tipo_prestacion = tipo;
    }
    @FXML TextField txtid;
    @FXML TextField txtprestacion;

    // Getters y setters
    public Integer getId_prestacion() {
        return id_prestacion;
    }

    public void setId_prestacion(Integer id_prestacion) {
        this.id_prestacion = id_prestacion;
    }

    public String getNombre_prestacion() {
        return nombre_prestacion;
    }

    public void setNombre_prestacion(String nombre_prestacion) {
        this.nombre_prestacion = nombre_prestacion;
    }

    public Integer getTipo_prestacion() {
        return tipo_prestacion;
    }

    public void setTipo_prestacion(Integer tipo_prestacion) {
        this.tipo_prestacion = tipo_prestacion;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    // Método eliminar
    public boolean eliminar() {
        mensajeError = "";

        if (id_prestacion == null) {
            mensajeError = "ID de prestación no definido.";
            return false;
        }

        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971")) {

            // Verificar si existen registros relacionados en prestacionesxordenes
            String checkSQL = "SELECT COUNT(*) FROM prestacionesxordenes WHERE id_prestacion = ?";
            try (PreparedStatement checkStmt = conexion.prepareStatement(checkSQL)) {
                checkStmt.setInt(1, id_prestacion);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(null,
                        "No se puede eliminar la prestación porque está relacionada a órdenes.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            }

            // Confirmar con el usuario
            int confirmacion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea eliminar esta prestación?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

            if (confirmacion != JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Eliminación cancelada por el usuario.");
                return false;
            }

            // Ejecutar la eliminación
            String deleteSQL = "DELETE FROM prestaciones WHERE id_prestacion = ?";
            try (PreparedStatement deleteStmt = conexion.prepareStatement(deleteSQL)) {
                deleteStmt.setInt(1, id_prestacion);
                int filasAfectadas = deleteStmt.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Registro de prestación eliminado.");
                    return true;
                } else {
                    mensajeError = "No se pudo eliminar la prestación.";
                    return false;
                }
            }

        } catch (SQLException e) {
            mensajeError = "Error en la base de datos: " + e.getMessage();
            JOptionPane.showMessageDialog(null, mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean modificar(int id_prestacion, String nombrePrestacion) {
        mensajeError = "";


       if (id_prestacion == 0) {
            mensajeError = "ID de prestación no definido o inválido.";
            return false;
        }
        System.out.println(">>> Estoy en ClasePrestaciones.modificar()");

        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971")) {

            // Confirmar con el usuario
            int confirmacion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea modificar esta prestación?",
                "Confirmar MOdificacion",
                JOptionPane.YES_NO_OPTION);

            if (confirmacion != JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "MOdifdicacion cancelada");
                return false;
            }

            // Ejecutar la eliminación
            String deleteSQL = "Update prestaciones set nombre_prestacion=? WHERE id_prestacion = ?";
            try (PreparedStatement deleteStmt = conexion.prepareStatement(deleteSQL)) {
                deleteStmt.setString(1, nombrePrestacion);
                deleteStmt.setInt(2, id_prestacion);

                int filasAfectadas = deleteStmt.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Registro de prestación Modificado.");
                    return true;
                } else {
                    mensajeError = "No se pudo Modificar la prestación.";
                    return false;
                }
            }

        } catch (SQLException e) {
            mensajeError = "Error en la base de datos: " + e.getMessage();
            JOptionPane.showMessageDialog(null, mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
