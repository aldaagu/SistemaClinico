package src;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
 
public class GrillaPrestacionesXOrdenes {
    private int id_orden;
    private int id_prestacion;
    private int Edad;
    private LocalDate FecIngreso;
    private LocalDate FecEgreso;
    private String Domicilio;
    private String Localidad;
    private String Diagnostico;
    private String CondicionAlta;
    private String sala;
    private String UTI;
    private String USI;
    private BigDecimal Monto;
    private String Observaciones;
    
 

     // Constructor
    public GrillaPrestacionesXOrdenes(int id_orden, int id_prestacion, int Edad, LocalDate FecIngreso, LocalDate FecEgreso,
                                      String Domicilio, String Localidad, String Diagnostico, String CondicionAlta,
                                      String sala, String UTI, String USI, BigDecimal Monto, String Observaciones) {
        this.id_orden = id_orden;
        this.id_prestacion = id_prestacion;
        this.Edad = Edad;
        this.FecIngreso = FecIngreso;
        this.FecEgreso = FecEgreso;
        this.Domicilio = Domicilio;
        this.Localidad = Localidad;
        this.Diagnostico = Diagnostico;
        this.CondicionAlta = CondicionAlta;
        this.sala = sala;
        this.UTI = UTI;
        this.USI = USI;
        this.Monto = Monto;
        this.Observaciones = Observaciones;
    }

    public GrillaPrestacionesXOrdenes() {
        //TODO Auto-generated constructor stub
    }

    // Getters y Setters
    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
    }

    public int getId_prestacion() {
        return id_prestacion;
    }

    public void setId_prestacion(int id_prestacion) {
        this.id_prestacion = id_prestacion;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int Edad) {
        this.Edad = Edad;
    }

    public LocalDate getFecIngreso() {
        return FecIngreso;
    }

    public void setFecIngreso(LocalDate FecIngreso) {
        this.FecIngreso = FecIngreso;
    }

    public LocalDate getFecEgreso() {
        return FecEgreso;
    }

    public void setFecEgreso(LocalDate FecEgreso) {
        this.FecEgreso = FecEgreso;
    }

    public String getDomicilio() {
        return Domicilio;
    }

    public void setDomicilio(String Domicilio) {
        this.Domicilio = Domicilio;
    }

    public String getLocalidad() {
        return Localidad;
    }

    public void setLocalidad(String Localidad) {
        this.Localidad = Localidad;
    }

    public String getDiagnostico() {
        return Diagnostico;
    }

    public void setDiagnostico(String Diagnostico) {
        this.Diagnostico = Diagnostico;
    }

    public String getCondicionAlta() {
        return CondicionAlta;
    }

    public void setCondicionAlta(String CondicionAlta) {
        this.CondicionAlta = CondicionAlta;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getUTI() {
        return UTI;
    }

    public void setUTI(String UTI) {
        this.UTI = UTI;
    }

    public String getUSI() {
        return USI;
    }

    public void setUSI(String USI) {
        this.USI = USI;
    }

    public BigDecimal getMonto() {
        return Monto;
    }

    public void setMonto(BigDecimal Monto) {
        this.Monto = Monto;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }
}