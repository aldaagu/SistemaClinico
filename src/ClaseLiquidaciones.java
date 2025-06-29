package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
//import javafx.scene.control.PasswordField;

public class ClaseLiquidaciones {
    private Integer id_liquidacion;
    private String periodo;
    private Double monto;
    private boolean estado;
    private Integer tipo_liquidacion;

    //@FXML private TextField txtnroLiquidacion;
    //@FXML private TextField txtPeriodo;


  // Constructor vacío
    public ClaseLiquidaciones() {
    }

    // Constructor con todos los parámetros
    public ClaseLiquidaciones(Integer id_liquidacion, String periodo, Double monto, boolean estado, Integer tipo_liquidacion) {
        this.id_liquidacion = id_liquidacion;
        this.periodo = periodo;
        this.monto = monto;
        this.estado = estado;
        this.tipo_liquidacion= tipo_liquidacion;
    }

    // Getters y Setters
    public Integer getTipo_liquidacion() {
        return tipo_liquidacion;
    }

    public void setTipo_liquidacion(Integer tipoliquidacion) {
        this.tipo_liquidacion = tipoliquidacion;
    }

    public Integer getId_liquidacion() {
        return id_liquidacion;
    }

    public void setId_liquidacion(Integer id_liquidacion) {
        this.id_liquidacion = id_liquidacion;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public static boolean alta(Connection conexion, int idLiquidacion, String periodoACalcular, Double MontoTotal, List<GrillaLDetalle> detalles,int tipo) throws SQLException {
        
    try {
        // Calcular monto total desde los detalles
        MontoTotal = 0.0; //detalles.stream().mapToDouble(ClaseDetalleLiquidacion::getMonto).sum();

        System.out.println(periodoACalcular+ "--"+MontoTotal);
        // 1. Insertar en tabla Liquidaciones
        PreparedStatement psIns = conexion.prepareStatement(
        "INSERT INTO Liquidaciones (id_liquidacion, periodo, monto, estado, tipo) VALUES (?, ?, ?, 0,?)");
        psIns.setInt(1, idLiquidacion);
        psIns.setString(2, periodoACalcular);
        psIns.setDouble(3, MontoTotal);
         psIns.setDouble(4, tipo);
        psIns.executeUpdate();

        // 2. Insertar los detalles de la liquidación
        ClaseDetalleLiquidacion.altaDetalle(idLiquidacion, detalles, conexion);

        // 3. Confirmar los cambios
        conexion.commit();

        return true;  // todo salió bien
    } catch (SQLException e) {
        conexion.rollback();  // deshacer cambios si hay error
        e.printStackTrace();
        return false;
    }
}


public static boolean ValidarExistencia(Connection conn, String periodo) {
    try {
        PreparedStatement psVal = conn.prepareStatement(
            "SELECT id_liquidacion, estado FROM Liquidaciones WHERE periodo = ?"
        );
        psVal.setString(1, periodo);
        ResultSet rsVal = psVal.executeQuery();

        if (rsVal.next()) {
            boolean estado = rsVal.getBoolean("estado");
            if (estado) {
                Utilidades.mostrarAlerta(Alert.AlertType.ERROR,"Período ya liquidado y cerrado.","Periodo cerrado");
                return false;
            } else {
                return true; // Existe, pero no está cerrado
            }
        } else {
            // No existe aún → se puede continuar
            return true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "Error al validar existencia del período.");
        return false;
    }
}

public static int CalcularId(Connection conn) {
    int newId = 1; // Valor por defecto si no hay registros aún
    try {
        Statement st = conn.createStatement();
        ResultSet rsMax = st.executeQuery("SELECT MAX(id_liquidacion) AS ultimoID FROM Liquidaciones");

        if (rsMax.next()) {
            newId = rsMax.getInt("ultimoID") + 1;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return newId;
}

public static boolean eliminarLiquidacion(int idLiquidacion) {
        String queryEliminarLiquidacion = "DELETE FROM Liquidaciones WHERE id_liquidacion = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971")) {
        //try (Connection conn = DriverManager.getConnection(URL_DB, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(queryEliminarLiquidacion); //{
            
            stmt.setInt(1, idLiquidacion);
            int filasEliminadas = stmt.executeUpdate();

            return filasEliminadas > 0; // Si se eliminó alguna fila, retorna true
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Si hubo un error, retorna false
        }
    }

    // Método para verificar el estado de la liquidación (simulación)
    public static boolean  verificarEstado(int idLiquidacion) {
        // Lógica para verificar el estado de la liquidación (puedes consultar la base de datos)
        return true; // Retorna true o false según sea necesario
    }


}
