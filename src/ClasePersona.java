package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase abstracta que representa una persona en el sistema.
 * Puede ser extendida por Usuario, Profesional, etc.
 */
public abstract class ClasePersona {
    protected Integer id_persona;
    protected String apellido;
    protected String nombre;
    protected String email;
    protected String tel;

    // Constructor vacío
    public ClasePersona() {}

    // Constructor con parámetros
    public ClasePersona(Integer id_persona, String apellido, String nombre, String email, String tel) {
        this.id_persona= id_persona;
        this.apellido = apellido;
        this.nombre = nombre;
        this.email = email;
        this.tel = tel;
    }

    // Getters y Setters
    public Integer getId_persona() {
        return id_persona;
    }

    public void setId_persona(Integer id_persona) {
        this.id_persona = id_persona;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
   
    // Métodos abstractos para que las subclases los implementen
    public abstract boolean alta(Connection conexion) throws SQLException;
    public abstract boolean modificar(Connection conexion) throws SQLException;
    public abstract boolean eliminar(Connection conexion) throws SQLException;
    public abstract boolean buscar(Connection conexion) throws SQLException;
    public abstract ClasePersona consultarPorId(Connection conexion, Integer id) throws SQLException;
    

    // Método estático para insertar en tabla personas
    public static Integer insertarPersona(Connection conexion, ClasePersona persona) throws SQLException {
        String sql = "INSERT INTO personas (apellido, nombre, tel, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, persona.getApellido());
            stmt.setString(2, persona.getNombre());
            stmt.setString(3, persona.getTel());
            stmt.setString(4, persona.getEmail());

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // devuelve id_persona generado
                }
            }
        }
        return null;
    }
// Método común para modificar persona
    public static boolean modificarPersona(Connection conexion, ClasePersona persona) throws SQLException {
        String sql = "UPDATE personas SET apellido=?, nombre=?, email=?, telefono=? WHERE id_persona=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, persona.getApellido());
            stmt.setString(2, persona.getNombre());
            stmt.setString(3, persona.getEmail());
            stmt.setString(4, persona.getTel());
            stmt.setInt(5, persona.getId_persona());
            int filas = stmt.executeUpdate();
            return filas > 0;
        }
    }

    // Método común para eliminar persona
    public static boolean eliminarPersona(Connection conexion, Integer id_persona) throws SQLException {
        String sql = "DELETE FROM personas WHERE id_persona=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id_persona);
            int filas = stmt.executeUpdate();
            return filas > 0;
        }
    }

    // Método común para consultar persona por id
    public static ClasePersona consultarPersonaPorId(Connection conexion, Integer id_persona) throws SQLException {
        String sql = "SELECT * FROM personas WHERE id_persona=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id_persona);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Aquí no se puede instanciar Persona porque es abstracta
                    // La subclase debe implementar este método para construir el objeto
                    return null;
                }
            }
        }
        return null;
    }
    
       
}
