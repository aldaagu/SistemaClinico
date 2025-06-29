package src;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




public class Ordenes {

    // Atributos
    private int idOrden;
    private Integer idPrestador;
    private Integer idProfesional;
    private Date fecha;
    private String nroAfiliado;
    private String apeNom;
    private Integer idFederacion;
    private Integer edad;
    private String domicilio;
    private String localidad;
    private Integer tipoLiquidacion;
    private  Integer Estado;  
    
    // Constructor
    public Ordenes(int idOrden, Integer idPrestador, Integer idProfesional, Date fecha, 
            String nroAfiliado, String apeNom, Integer idFederacion, Integer edad, 
            String domicilio, String localidad,Integer tipoLiquidacion, Integer Estado) {
        this.idOrden = idOrden;
        this.idPrestador = idPrestador;
        this.idProfesional = idProfesional;
        this.fecha = fecha;
        this.nroAfiliado = nroAfiliado;
        this.apeNom = apeNom;
        this.idFederacion = idFederacion;
        this.edad = edad;
        this.domicilio = domicilio;
        this.localidad = localidad;
        this.tipoLiquidacion = tipoLiquidacion;
        this.Estado=0;
    }

    // Getters y Setters
    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public Integer getIdPrestador() {
        return idPrestador;
    }

    public void setIdPrestador(Integer idPrestador) {
        this.idPrestador = idPrestador;
    }

    public Integer getIdProfesional() {
        return idProfesional;
    }

    public void setIdProfesional(Integer idProfesional) {
        this.idProfesional = idProfesional;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNroAfiliado() {
        return nroAfiliado;
    }

    public void setNroAfiliado(String nroAfiliado) {
        this.nroAfiliado = nroAfiliado;
    }

    public String getApeNom() {
        return apeNom;
    }

    public void setApeNom(String apeNom) {
        this.apeNom = apeNom;
    }

    public Integer getIdFederacion() {
        return idFederacion;
    }

    public void setIdFederacion(Integer idFederacion) {
        this.idFederacion = idFederacion;
    }

    public Integer getTipoLiquidacion() {
        return tipoLiquidacion;
    }

    public void setTipoLiquidacion(Integer tipoLiquidacion) {
        this.tipoLiquidacion = tipoLiquidacion;
    }


    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }
    public int getEstado() {
        return Estado;
    }

    public void setEstado(int idEstado) {
        this.Estado = idEstado;
    }

    // Método para mostrar la información de la orden
    @Override
    public String toString() {
        return "Orden [idOrden=" + idOrden + ", fecha=" + fecha + ", nroAfiliado=" + nroAfiliado 
                + ", apeNom=" + apeNom + ", idFederacion=" + idFederacion + ", edad=" + edad + 
                "," + Estado + "]";
    }

    // Método para insertar la orden en la base de datos
    public void insertar() throws SQLException {
        //inserto el detalle de la orden y la orden fue insertada en la clase ordenes
        String sqlOrden = "INSERT INTO ordenes (id_orden, id_prestador, id_profesional," + 
        "Fecha, Nro_Afiliado, ApeNom, id_Federacion, Edad, Domicilio, " +
        "Localidad, Tipo_Liquidacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971");
             PreparedStatement psOrden = conn.prepareStatement(sqlOrden)) {
            psOrden.setInt(1, this.idOrden);
            psOrden.setInt(2, this.idPrestador);
            psOrden.setInt(3, this.idProfesional);
            psOrden.setDate(4, this.fecha);         
            psOrden.setString(5, this.nroAfiliado);  
            psOrden.setString(6, this.apeNom);  
            psOrden.setInt(7, this.idFederacion);
            psOrden.setInt(8, this.edad);
            psOrden.setString(9, this.domicilio);  
            psOrden.setString(10, this.localidad);  
            psOrden.setInt(11, this.tipoLiquidacion);  
        psOrden.executeUpdate();  // Ejecutar la inserción
        }   
    }
    // Método para insertar la orden en la base de datos
   /*   public void insertarOrden() {
        String sql = "INSERT INTO prestacionesxordenes (id_orden, id_prestacion, FecIngreso, FecEgreso, "
                   + "Diagnostico, CondicionAlta, sala, UTI, USI, Monto, Observaciones) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        //try (Connection conn = Conexion.getConnection(); // Obtener conexión a la base de datos
        //String sql = "SELECT MAX(id_prestacion) FROM prestaciones";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971");
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
             //PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Establecer los parámetros del PreparedStatement
            stmt.setInt(1, this.idOrden);
            stmt.setInt(2, this.idPrestacion);
            stmt.setDate(3, this.fecIngreso);
            stmt.setDate(4, this.fecEgreso);
            stmt.setString(5, this.diagnostico);
            stmt.setString(6, this.condicionAlta);
            stmt.setString(7, this.sala);
            stmt.setString(8, this.uti);
            stmt.setString(9, this.usi);
            stmt.setDouble(10, this.monto);
            stmt.setString(11, this.observaciones);

            // Ejecutar la inserción
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("La orden se ha insertado correctamente.");
            } else {
                System.out.println("No se pudo insertar la orden.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al insertar la orden: " + e.getMessage());
        }
   / }*/
}

