package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.Alert;


public class ClasePrestadores {
    private int id_prestador;
    private String razon_social;
    private String cuit;
    private String direccion;
    private String telefono;
    private String fax;
    private String email;
    private boolean gravado;
    private boolean rural;
    private boolean monotributo;
    private boolean familia;
    private boolean enActividad; 
    private boolean clasicYAcord;
 

    // Constructor completo
    public ClasePrestadores(int id_prestador, String razon_social, String cuit, String direccion, String telefono,
                       String fax, String email, boolean gravado, boolean rural, boolean monotributo,
                       boolean familia, boolean enActividad, boolean clasicYAcord) {
        this.id_prestador = id_prestador;
        this.razon_social = razon_social;
        this.cuit = cuit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fax = fax;
        this.email = email;
        this.gravado = gravado;
        this.rural = rural;
        this.monotributo = monotributo;
        this.familia = familia;
        this.enActividad = enActividad;
        this.clasicYAcord = clasicYAcord;
    }
    public ClasePrestadores() {
        // Constructor vacío necesario para instancias sin datos iniciales
    }

    // Getters y Setters

    public int getId_prestador() {
        return id_prestador;
    }

    public void setId_prestador(int id_prestador) {
        this.id_prestador = id_prestador;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isGravado() {
        return gravado;
    }

    public void setGravado(boolean gravado) {
        this.gravado = gravado;
    }

    public boolean isRural() {
        return rural;
    }

    public void setRural(boolean rural) {
        this.rural = rural;
    }

    public boolean isMonotributo() {
        return monotributo;
    }

    public void setMonotributo(boolean monotributo) {
        this.monotributo = monotributo;
    }

    public boolean isFamilia() {
        return familia;
    }

    public void setFamilia(boolean familia) {
        this.familia = familia;
    }

    public boolean isEnActividad() {
        return enActividad;
    }

    public void setEnActividad(boolean enActividad) {
        this.enActividad = enActividad;
    }

    public boolean isClasicYAcord() {
        return clasicYAcord;
    }

    public void setClasicYAcord(boolean clasicYAcord) {
        this.clasicYAcord = clasicYAcord;
    }

    public void guardarPrestadorEnBD() throws SQLException  {
        String sqlId = "SELECT MAX(id_prestador) FROM prestadores";
        String sqlInsert = "INSERT INTO prestadores (id_prestador, razon_social, cuit, direccion, telefono, fax, email, Gravado, Rural, Monotributo, Familia, EnActividad, ClasicYAcord) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971")) {
            // Obtener nuevo ID
            try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlId)) {
                if (rs.next()) {
                    this.id_prestador = rs.getInt(1) + 1;
                } else {
                    this.id_prestador = 1;
                }
            }
            // Insertar nuevo prestador
            try (PreparedStatement stmt = conn.prepareStatement(sqlInsert)) {
                stmt.setInt(1, this.id_prestador);
                stmt.setString(2, this.razon_social);
                stmt.setString(3, this.cuit);
                stmt.setString(4, this.direccion);
                stmt.setString(5, this.telefono);
                stmt.setString(6, this.fax);
                stmt.setString(7, this.email);
                stmt.setBoolean(8, this.gravado);
                stmt.setBoolean(9, this.rural);
                stmt.setBoolean(10, this.monotributo);
                stmt.setBoolean(11, this.familia);
                stmt.setBoolean(12, this.enActividad);
                stmt.setBoolean(13, this.clasicYAcord);

                stmt.executeUpdate();
            }
        }
}

    public void eliminarPrestadorDeBD() throws SQLException {
        String sql = "DELETE FROM prestadores WHERE id_prestador = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971");
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, this.id_prestador);  // Usa el ID de esta instancia
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0) {
                throw new SQLException("No se encontró el prestador con ID: " + this.id_prestador);
            }
        }
    }

    
} 


