package src;

public class GrillaPrestaciones {

    private int id_prestacion;
    private String nombre_prestacion;
    private String nombre_tipo_prestacion;

    // Constructor
    public GrillaPrestaciones(int id, String nombre,  String nombre_tipo_prestacion) {
        this.id_prestacion = id;
        this.nombre_prestacion = nombre;
        this.nombre_tipo_prestacion = nombre_tipo_prestacion;
    }

    // Getters
    public int getId_prestacion() {
        return id_prestacion;
    }

    public String getNombre_prestacion() {
        return nombre_prestacion;
    }

    public String getNombre_tipo_prestacion() {
        return nombre_tipo_prestacion;
    }

    // Setters
    public void setId_prestacion(int id_prestacion) {
        this.id_prestacion = id_prestacion;
    }

    public void setNombre_prestacion(String nombre_prestacion) {
        this.nombre_prestacion = nombre_prestacion;
    }



    public void setNombre_tipo_prestacion(String nombre_tipo_prestacion) {
        this.nombre_tipo_prestacion = nombre_tipo_prestacion;
    }

    @Override
    public String toString() {
        return "GrillaPrestaciones{" +
                "id_prestacion=" + id_prestacion +
                ", nombre_prestacion='" + nombre_prestacion + '\'' +
                ", nombre_tipo_prestacion='" + nombre_tipo_prestacion + '\'' +
                '}';
    }
}
