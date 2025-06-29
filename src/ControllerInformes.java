package src;

import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import src.Utilidades.ComboItem;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;



public class ControllerInformes {

    @FXML private ComboBox<String> cmbFiltro;
    @FXML private TextField txtValorBusqueda;
    @FXML private Button btnBuscar;
    @FXML private TableView<ClaseInformeAgrupados> tablaInforme;
    @FXML private TextField txtTotal;
    @FXML private ComboBox<ComboItem> cmbdatos;
    @FXML private TableColumn<ClaseInformeAgrupados, String> col_periodo;
    @FXML private TableColumn<ClaseInformeAgrupados, String> col_profesional;
    @FXML private TableColumn<ClaseInformeAgrupados, String> col_federacion;
    @FXML private TableColumn<ClaseInformeAgrupados, String> col_prestadora;
    @FXML private TableColumn<ClaseInformeAgrupados, String> col_prestacion;
    @FXML private TableColumn<ClaseInformeAgrupados, Integer> col_cantidad;
    @FXML private TableColumn<ClaseInformeAgrupados, Double> col_monto;
  



    private ObservableList<ClaseInformeAgrupados> datosTabla = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Cargar opciones de filtro
        cmbFiltro.setItems(FXCollections.observableArrayList(
            "profesional", "prestadores", "federacion", "prestacion", "periodo"
        ));
        cmbFiltro.getSelectionModel().selectFirst();

        onFiltroCambiado(); // Cargar el primer combo inicialmente

        cmbFiltro.setOnAction(e -> onFiltroCambiado());

        cmbdatos.setOnAction(e -> {
        txtValorBusqueda.clear(); // 1. Limpiar
        ComboItem item = cmbdatos.getValue(); // 2. Obtener ítem
        if (item != null) {
            String idTexto = String.valueOf(item.getId());
        txtValorBusqueda.setText(idTexto);
        System.out.println("Combo seleccionado: " + item.getDescripcion() + " (ID: " + idTexto + ")");
    
        }
        });
        //System.out.println("Combo seleccionado: " + item.getDescripcion() + " (ID: " + idTexto + ")");

        
        col_periodo.setCellValueFactory(new PropertyValueFactory<>("periodo"));
        col_profesional.setCellValueFactory(new PropertyValueFactory<>("profesional"));
        col_federacion.setCellValueFactory(new PropertyValueFactory<>("federacion"));
        col_prestadora.setCellValueFactory(new PropertyValueFactory<>("prestadora"));
        col_prestacion.setCellValueFactory(new PropertyValueFactory<>("prestacion"));
        col_cantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        col_monto.setCellValueFactory(new PropertyValueFactory<>("monto"));

 
    }

   @FXML
public void onBuscarInforme(ActionEvent event) {
    String campoOriginal = cmbFiltro.getValue();
    String campo;

    if (campoOriginal.equals("profesional")) {
        campo = "prof.id_profesional";
    } else if (campoOriginal.equals("prestadores")) {
        campo = "ld.id_prestadora";
    } else if (campoOriginal.equals("federacion")) {
        campo = "ld.id_federacion";
    } else if (campoOriginal.equals("prestacion")) {
        campo = "ld.id_prestacion";
    } else if (campoOriginal.equals("periodo")) {
        campo = "l.periodo";  // Usamos l.periodo en la cláusula WHERE
    } else {
        System.out.println("Filtro desconocido: " + campoOriginal);
        return;
    }

    String valor = txtValorBusqueda.getText().trim();

    if (valor.isEmpty()) {
        System.out.println("Debés seleccionar un filtro y completar un valor.");
        return;
    }

    double total = 0;

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971")) {
         // Determinar operador
        String operador = campoOriginal.equals("periodo") ? "LIKE" : "=";
        String sql = "SELECT " +
            "l.periodo AS periodo, " +
            "CONCAT(pe.nombre, ' ', pe.apellido) AS profesional, " +
            "f.nombre_federacion AS federacion, " +
            "p.razon_social AS prestadora, " +
            "pr.nombre_prestacion AS prestacion, " +
            "SUM(ld.cantidad) AS cantidad, " +
            "SUM(ld.monto) AS monto " +
            "FROM liquidaciones_detalles ld " +
            "JOIN liquidaciones l ON ld.id_liquidacion = l.id_liquidacion " +
            "JOIN profesionales2 prof ON ld.id_profesional = prof.id_profesional " +
            "JOIN personas pe ON prof.id_profesional = pe.id_persona " +
            "JOIN federaciones f ON ld.id_federacion = f.id_federacion " +
            "JOIN prestadores p ON ld.id_prestadora = p.id_prestador " +
            "JOIN prestaciones pr ON ld.id_prestacion = pr.id_prestacion " +
            "WHERE " + campo + " " + operador + " ? " +
            "GROUP BY l.periodo, pe.nombre, pe.apellido, f.nombre_federacion, p.razon_social, pr.nombre_prestacion";

            // "GROUP BY l.periodo, profesional, federacion, prestadora, prestacion";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //stmt.setString(1, "%" + valor + "%");
            // Asignar el parámetro según el tipo de filtro
            if (campoOriginal.equals("periodo")) {
                stmt.setString(1, "%" + valor + "%");
            } else {
                stmt.setInt(1, Integer.parseInt(valor));
            }
            ResultSet rs = stmt.executeQuery();

            ObservableList<ClaseInformeAgrupados> datosInforme = FXCollections.observableArrayList();

            while (rs.next()) {
                ClaseInformeAgrupados info = new ClaseInformeAgrupados(
                    rs.getString("periodo"),
                    rs.getString("profesional"),
                    rs.getString("federacion"),
                    rs.getString("prestadora"),
                    rs.getString("prestacion"),
                    rs.getInt("cantidad"),
                    rs.getDouble("monto")
                );
                datosInforme.add(info);
                total += info.getMonto(); // Acumula total correctamente
            }

            tablaInforme.setItems(datosInforme);
            txtTotal.setText(String.format("%.2f", total));
        }

    } catch (SQLException e) {
        System.err.println("Error al buscar informes: " + e.getMessage());
    }
}


    @FXML private void onFiltroCambiado() {
    String filtro = cmbFiltro.getValue();

    switch (filtro) {
        case "profesional":
            Utilidades.cargarCombo(
                cmbdatos,
                "SELECT p.id_persona AS id, CONCAT(p.nombre, ' ', p.apellido) AS nombre FROM personas p JOIN profesionales2 pr ON p.id_persona = pr.id_profesional",
                "nombre", "id");
            break;

        case "prestadores":
            Utilidades.cargarCombo(
                cmbdatos,
                "SELECT id_prestador AS id, razon_social FROM prestadores order by razon_social",
                "razon_social", "id");
            break;

        case "prestacion":
            Utilidades.cargarCombo(
                cmbdatos,
                "SELECT id_prestacion AS id, nombre_prestacion FROM prestaciones order by nombre_prestacion",
                "nombre_prestacion", "id");
            break;

        case "federacion":
            Utilidades.cargarCombo(
                cmbdatos,
                "SELECT DISTINCT id_federacion AS id, Nombre_federacion FROM federaciones",
                "Nombre_federacion", "id");
            break;

        case "periodo":
            Utilidades.cargarCombo(
                cmbdatos,
                "SELECT DISTINCT id_liquidacion AS id, periodo AS nombre FROM liquidaciones",
                "nombre", "id");
            break;
    }
}

}

