package src;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class GrillaLDetalle {
    private IntegerProperty idLiquidacion;
    private StringProperty profesional;  // Profesional: combinación de Persona (apellido, nombre)
    private StringProperty federacion;   // Federación: nombre de la federación
    private StringProperty prestadora;   // Prestadora: razón social
    private StringProperty prestacion;   // Prestación: nombre de la prestación
    private IntegerProperty cantidad;    // Cantidad
    private DoubleProperty monto;        // Monto

    // Constructor
    public GrillaLDetalle(
        int idLiquidacion,  String profesional, String federacion, 
        String prestadora, String prestacion, int cantidad, double monto) {
        
        this.idLiquidacion = new SimpleIntegerProperty(idLiquidacion);
        this.profesional = new SimpleStringProperty(profesional);
        this.federacion = new SimpleStringProperty(federacion);
        this.prestadora = new SimpleStringProperty(prestadora);
        this.prestacion = new SimpleStringProperty(prestacion);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.monto = new SimpleDoubleProperty(monto);
    }

    // Getters y Setters
    public int getIdLiquidacion() {
        return idLiquidacion.get();
    }

    public void setIdLiquidacion(int idLiquidacion) {
        this.idLiquidacion.set(idLiquidacion);
    }

    public IntegerProperty idLiquidacionProperty() {
        return idLiquidacion;
    }

    public String getProfesional() {
        return profesional.get();
    }

    public void setProfesional(String profesional) {
        this.profesional.set(profesional);
    }

    public StringProperty profesionalProperty() {
        return profesional;
    }

    public String getFederacion() {
        return federacion.get();
    }

    public void setFederacion(String federacion) {
        this.federacion.set(federacion);
    }

    public StringProperty federacionProperty() {
        return federacion;
    }

    public String getPrestadora() {
        return prestadora.get();
    }

    public void setPrestadora(String prestadora) {
        this.prestadora.set(prestadora);
    }

    public StringProperty prestadoraProperty() {
        return prestadora;
    }

    public String getPrestacion() {
        return prestacion.get();
    }

    public void setPrestacion(String prestacion) {
        this.prestacion.set(prestacion);
    }

    public StringProperty prestacionProperty() {
        return prestacion;
    }

    public int getCantidad() {
        return cantidad.get();
    }

    public void setCantidad(int cantidad) {
        this.cantidad.set(cantidad);
    }

    public IntegerProperty cantidadProperty() {
        return cantidad;
    }

    public double getMonto() {
        return monto.get();
    }

    public void setMonto(double monto) {
        this.monto.set(monto);
    }

    public DoubleProperty montoProperty() {
        return monto;
    }
}
