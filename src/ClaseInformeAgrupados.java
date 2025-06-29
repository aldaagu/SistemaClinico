package src;

public class ClaseInformeAgrupados {

    private String periodo;
    private String profesional;
    private String federacion;
    private String prestadora;
    private String prestacion;
    private int cantidad;
    private double monto;

    public ClaseInformeAgrupados(String periodo, String profesional, String federacion,
                              String prestadora, String prestacion, int cantidad, double monto) {
        this.periodo = periodo;
        this.profesional = profesional;
        this.federacion = federacion;
        this.prestadora = prestadora;
        this.prestacion = prestacion;
        this.cantidad = cantidad;
        this.monto = monto;
    }

    // Getters
    public String getPeriodo() { return periodo; }
    public String getProfesional() { return profesional; }
    public String getFederacion() { return federacion; }
    public String getPrestadora() { return prestadora; }
    public String getPrestacion() { return prestacion; }
    public int getCantidad() { return cantidad; }
    public double getMonto() { return monto; }
}


