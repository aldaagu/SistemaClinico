package src;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
 
public class GrillaPrestacionesXOrdenes {
    private Integer idOrden;
    private Integer idPrestacion;
    private LocalDate FecIngreso;
    private LocalDate FecEgreso;
    private String Diagnostico;
    private String CondicionAlta;
    private String sala;
    private String UTI;
    private String USI;
    private Double Monto;
    private String Observaciones;
    
 

    public GrillaPrestacionesXOrdenes(Integer idOrden, Integer idPrestacion, LocalDate fecIngreso, LocalDate fecEgreso,
                                  String diagnostico, String condicionAlta,  
                                  String sala, String uti, String usi, Double monto, String observaciones) {
    this.idOrden = idOrden;
    this.idPrestacion = idPrestacion;
    this.FecIngreso = fecIngreso;
    this.FecEgreso = fecEgreso;
    this.Diagnostico = diagnostico;
    this.CondicionAlta = condicionAlta;
    this.sala = sala;
    this.UTI = uti;
    this.USI = usi;
    this.Monto = monto;
    this.Observaciones = observaciones;
}


    //public GrillaPrestacionesXOrdenes() {
        //TODO Auto-generated constructor stub
    //}

    // Getters y Setters
    public Integer getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Integer id_orden) {
        this.idOrden = id_orden;
    }

    public Integer getIdPrestacion() {
        return idPrestacion;
    }

    public void setIdPrestacion(Integer id_prestacion) {
        this.idPrestacion = id_prestacion;
    }

    public LocalDate getFecIngreso() {
        return FecIngreso;
    }

    public void setFecIngreso(LocalDate fecingreso) {
        this.FecIngreso = fecingreso;
    }

    public LocalDate getFecEgreso() {
        return FecEgreso;
    }

    public void setFecEgreso(LocalDate fecegreso) {
        this.FecEgreso = fecegreso;
    }

    public String getDiagnostico() {
        return Diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.Diagnostico = diagnostico;
    }

    public String getCondicionAlta() {
        return CondicionAlta;
    }

    public void setCondicionAlta(String condicionalta) {
        this.CondicionAlta = condicionalta;
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

    public void setUTI(String uti) {
        this.UTI = uti;
    }

    public String getUSI() {
        return USI;
    }

    public void setUSI(String usi) {
        this.USI = usi;
    }

    public Double getMonto() {
        return Monto;
    }

    public void setMonto(Double monto) {
        this.Monto = monto;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.Observaciones = observaciones;
    }

    //public int getIdPrestacion() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'getIdPrestacion'");
    //}
}