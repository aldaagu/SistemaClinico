package src;

public class GrillaLiquidaciones {
    
    private int idLiquidacion;
    private String periodo;
    private Double monto;
    private Boolean estado;
    private int tipo;

    // Constructor
    public GrillaLiquidaciones(int idLiquidacion, String periodo, Double monto, Boolean estado, int tipo) {
        this.idLiquidacion = idLiquidacion;
        this.periodo = periodo;
        this.monto = monto;
        this.estado = estado;
        this.tipo = tipo;
    }

    // Getters y Setters
    public int getIdLiquidacion() { return idLiquidacion; }
    public void setIdLiquidacion(int idLiquidacion) { this.idLiquidacion = idLiquidacion; }

    public int getIdTipo() { return tipo; }
    public void setIdTipo(int tipo) { this.tipo = tipo; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }
}
