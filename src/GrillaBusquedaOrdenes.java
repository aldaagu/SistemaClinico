package src;
import java.sql.Date;
import java.time.LocalDate;

import javafx.scene.control.DatePicker;

public class GrillaBusquedaOrdenes {
   
    private Integer Id_orden;
    private LocalDate fecha;
    private String razon_social;
    private String profesional;
    private String prestacion;
    private Integer id_federacion;
    private String ApeNom;

    // Constructor
    public   GrillaBusquedaOrdenes (Integer id_orden, LocalDate fecha,  String razon_social,
        String profesional,String prestacion,Integer id_federacion, String ApeNom) {
        this.Id_orden = id_orden;
        this.fecha = fecha;
        this.razon_social = razon_social;
        this.profesional = profesional;
        this.prestacion = prestacion;
        this.id_federacion = id_federacion;
        this.ApeNom = ApeNom;
    }

    // Getters
    public Integer getId_orden() {
        return Id_orden;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public String getProfesional() {
        return profesional;
    }

    public String getPrestacion() {
        return prestacion;
    }

    public Integer getId_federacion() {
        return id_federacion;
    }

    public String getApeNom() {
        return ApeNom;
    }

    // Setters
    public void setId_orden(Integer Id_orden) {
        this.Id_orden = Id_orden;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public void setProfesional(String profesional) {
        this.profesional = profesional;
    }

    public void setPrestacion(String prestacion) {
        this.prestacion = prestacion;
    }

    public void setId_federacion(Integer id_federacion) {
        this.id_federacion = id_federacion;
    }

    public void setApeNom(String ApeNom) {
        this.ApeNom = ApeNom;
    }
}


