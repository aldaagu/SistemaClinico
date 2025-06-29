package src;


 

import java.util.List;
import java.util.Locale;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import java.sql.DriverManager;

import java.sql.ResultSet;

import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ClaseDetalleLiquidacion {
    private int idLiquidacion;
    private String profesional;
    private String federacion;
    private String prestadora;
    private String prestacion;
    private int cantidad;
    private double monto;

    @FXML private TextField txtMontoPracticas;
    @FXML private TableView<GrillaLDetalle> listaDetalleLiquidacion;
    

    
    //private listaDetalleLiquidacion objetList
   // private ObservableList<ClaseDetalleLiquidacion> listaDetalleLiquidacion = FXCollections.observableArrayList();

    // Constructor
    public ClaseDetalleLiquidacion(int idLiquidacion, String profesional, String federacion,
                              String prestadora, String prestacion, int cantidad, double monto) {
        this.idLiquidacion = idLiquidacion;
  
        this.profesional = profesional;
        this.federacion = federacion;
        this.prestadora = prestadora;
        this.prestacion = prestacion;
        this.cantidad = cantidad;
        this.monto = monto;
                              }
    //2do constructor
    public ClaseDetalleLiquidacion(String profesional, String federacion, String prestadora, String prestacion, int cantidad, double monto) {
        this.profesional = profesional;
        this.federacion = federacion;
        this.prestadora = prestadora;
        this.prestacion = prestacion;
        this.cantidad = cantidad;
        this.monto = monto;
    }

    // Getters y setters
    public int getIdLiquidacion() { return idLiquidacion; }
    public void setIdLiquidacion(int idLiquidacion) { this.idLiquidacion = idLiquidacion; }


    public String getProfesional() { return profesional; }
    public void setProfesional(String profesional) { this.profesional = profesional; }

    public String getFederacion() { return federacion; }
    public void setFederacion(String federacion) { this.federacion = federacion; }

    public String getPrestadora() { return prestadora; }
    public void setPrestadora(String prestadora) { this.prestadora = prestadora; }

    public String getPrestacion() { return prestacion; }
    public void setPrestacion(String prestacion) { this.prestacion = prestacion; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }


    public static void altaDetalle(int newId, List<GrillaLDetalle> listaDetalle, Connection conn) throws SQLException {
       
        double montoTotal = 0.0;
        double total=0.0;

        String sqlInsert = "INSERT INTO Liquidaciones_detalles " +
            "(id_liquidacion, id_profesional, id_federacion, id_prestadora, id_prestacion, cantidad, monto) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement psDet = conn.prepareStatement(sqlInsert)) {
            for (GrillaLDetalle det : listaDetalle) {
                int idProfesional = obtenerId(conn,
                    "SELECT p.id_profesional FROM Profesionales2 p " +
                    "JOIN Personas pe ON p.id_profesional = pe.id_persona " +
                    "WHERE CONCAT(pe.apellido, ', ', pe.nombre) = ?",
                    det.getProfesional());
                int idFederacion = obtenerId(conn,
                    "SELECT id_federacion FROM Federaciones WHERE Nombre_federacion = ?",
                    det.getFederacion());
                int idPrestadora = obtenerId(conn,
                    "SELECT id_prestador FROM Prestadores WHERE razon_social = ?",
                    det.getPrestadora());
                int idPrestacion = obtenerId(conn,
                    "SELECT id_prestacion FROM Prestaciones WHERE nombre_prestacion = ?",
                    det.getPrestacion());

                psDet.setInt(1, newId);
                psDet.setInt(2, idProfesional);
                psDet.setInt(3, idFederacion);
                psDet.setInt(4, idPrestadora);
                psDet.setInt(5, idPrestacion);
                psDet.setInt(6, det.getCantidad());
                psDet.setDouble(7, det.getMonto());
                psDet.executeUpdate();
            }
            
            
        }
    }

  
    private static int obtenerId(Connection conn, String sql, String valor) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, valor);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    return 1;
                    //throw new SQLException("No se encontró ID para '" + valor + "'");
                }
            }
        }
    }

 public static boolean eliminarDetallesLiquidacion(int idLiquidacion) {
        String queryEliminarDetalles = "DELETE FROM Liquidaciones_Detalles WHERE id_liquidacion = ?";

         try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971")) {
        //try (Connection conn = DriverManager.getConnection(URL_DB, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(queryEliminarDetalles) ;
            
            stmt.setInt(1, idLiquidacion);
            int filasEliminadas = stmt.executeUpdate();

            return filasEliminadas > 0; // Si se eliminó alguna fila, retorna true
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Si hubo un error, retorna false
        }
    }

}
