package src;

import java.util.Date;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;



public class PrestacionesxOrdenes {

    private int idOrden;
    private int idPrestacion;
    private LocalDate fecIngreso;
    private LocalDate fecEgreso;
    private String diagnostico;
    private String condicionAlta;
    private String sala;
    private String uti;
    private String usi;
    private Double monto;
    private String observaciones;

    // Constructor
   public PrestacionesxOrdenes(int idOrden, int idPrestacion, LocalDate fecIngreso, LocalDate fecEgreso,
    String diagnostico, String condicionAlta,String sala, String uti, 
    String usi, Double monto, String observaciones) {
    this.idOrden = idOrden;
    this.idPrestacion = idPrestacion;
    this.fecIngreso = fecIngreso;
    this.fecEgreso = fecEgreso;
    this.diagnostico = diagnostico;
    this.condicionAlta = condicionAlta;
    this.sala = sala;
    this.uti = uti;
    this.usi = usi;
    this.monto = monto;
    this.observaciones = observaciones;
}

    // Getters y Setters
    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getIdPrestacion() {
        return idPrestacion;
    }


    public void setIdPrestacion(int idPrestacion) {
        this.idPrestacion = idPrestacion;
    }

    public LocalDate getFecIngreso() {
        return fecIngreso;
    }

    public void setFecIngreso(LocalDate fecIngreso) {
        this.fecIngreso = fecIngreso;
    }

    public LocalDate getFecEgreso() {
        return fecEgreso;
    }

    public void setFecEgreso(LocalDate fecEgreso) {
        this.fecEgreso = fecEgreso;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getCondicionAlta() {
        return condicionAlta;
    }

    public void setCondicionAlta(String condicionAlta) {
        this.condicionAlta = condicionAlta;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getUti() {
        return uti;
    }

    public void setUti(String uti) {
        this.uti = uti;
    }

    public String getUsi() {
        return usi;
    }

    public void setUsi(String usi) {
        this.usi = usi;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "PrestacionesxOrdenes{" +
                "idOrden=" + idOrden +
                ", idPrestacion=" + idPrestacion +
                ", fecIngreso=" + fecIngreso +
                ", fecEgreso=" + fecEgreso +
                ", diagnostico='" + diagnostico + '\'' +
                ", condicionAlta='" + condicionAlta + '\'' +
                ", sala='" + sala + '\'' +
                ", uti='" + uti + '\'' +
                ", usi='" + usi + '\'' +
                ", monto=" + monto +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }

    // Método de inserción en la base de datos
    public void insertar(Connection conn) throws SQLException {
        String sql = "INSERT INTO PrestacionesXordenes (id_orden, id_prestacion, FecIngreso, FecEgreso, Diagnostico, CondicionAlta,  Sala, UTI, USI, Monto, Observaciones) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, this.idOrden);  // id_orden
            ps.setInt(2, this.idPrestacion);  // id_prestacion
            ps.setDate(3,  java.sql.Date.valueOf(this.fecIngreso));  // Para fecIngreso
            ps.setDate(4, java.sql.Date.valueOf(this.fecEgreso));   // Para fecEgreso
            ps.setString(5, this.diagnostico);  // Diagnóstico
            ps.setString(6, this.condicionAlta);  // Condición de Alta
            ps.setString(7, this.sala);  // Sala
            ps.setString(8, this.uti);  // UTI
            ps.setString(9, this.usi);  // USI
            ps.setDouble(10,this.monto);
            ps.setString(11, this.observaciones);  // Observaciones
            ps.executeUpdate();  // Ejecuta la inserción
        }
    }

}

