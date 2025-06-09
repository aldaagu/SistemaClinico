package src;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.SQLException;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class ControllerBusquedaOrdenes {
    @FXML private DatePicker dtpdesde;
    @FXML private DatePicker dtphasta;
    @FXML private TextField txtbusqueda;
    

    @FXML private RadioButton rbtnfecha;
    @FXML private RadioButton rbtnorden;
    @FXML private RadioButton rbtnprofesional;
    @FXML private RadioButton rbtnpaciente;
    @FXML private RadioButton rbtnos;
    @FXML private RadioButton rbtnprestacion;
    @FXML private TableView<GrillaBusquedaOrdenes> grillabusqueda;
    @FXML private TableColumn<GrillaBusquedaOrdenes, Integer> col_id;
    @FXML private TableColumn<GrillaBusquedaOrdenes, LocalDate> col_fecha;
    @FXML private TableColumn<GrillaBusquedaOrdenes, String> col_razon;
    @FXML private TableColumn<GrillaBusquedaOrdenes, String> col_profesional;
    @FXML private TableColumn<GrillaBusquedaOrdenes, String> col_prestacion;
    @FXML private TableColumn<GrillaBusquedaOrdenes, Integer> col_federacion;
    @FXML private TableColumn<GrillaBusquedaOrdenes, String> col_paciente;
    @FXML private Button btnbusqueda;
    @FXML private Button btnseleccionar;
    @FXML private Button btnsalir;

    @FXML public void initialize() {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id_orden"));
        col_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        col_razon.setCellValueFactory(new PropertyValueFactory<>("razon_social"));
        col_profesional.setCellValueFactory(new PropertyValueFactory<>("profesional"));
        col_prestacion.setCellValueFactory(new PropertyValueFactory<>("prestacion"));
        col_federacion.setCellValueFactory(new PropertyValueFactory<>("id_federacion"));
        col_paciente.setCellValueFactory(new PropertyValueFactory<>("apeNom"));
        limpiarCampos();
        grillabusqueda.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                seleccionarOrdenYSalir();
            }
        });
    }


    private void limpiarCampos() {
        dtpdesde.setValue(null);
        dtphasta.setValue(null);
        txtbusqueda.clear();

        rbtnfecha.setSelected(false);
        rbtnorden.setSelected(false);
        rbtnprofesional.setSelected(false);
        rbtnpaciente.setSelected(false);
        rbtnos.setSelected(false);
        rbtnprestacion.setSelected(false);

        txtbusqueda.setDisable(true);
        dtpdesde.setDisable(true);
        dtphasta.setDisable(true);

        grillabusqueda.getItems().clear();
    }

    @FXML private void onRadioSeleccionado(ActionEvent event) {
        txtbusqueda.clear();
        dtpdesde.setValue(null);
        dtphasta.setValue(null);
        grillabusqueda.getItems().clear();

        if (rbtnfecha.isSelected()) {
            txtbusqueda.setDisable(true);
            dtpdesde.setDisable(false);
            dtphasta.setDisable(false);
        } else {
            txtbusqueda.setDisable(false);
            dtpdesde.setDisable(true);
            dtphasta.setDisable(true);
        }
    }
    @FXML private void onBuscar(ActionEvent event) {
        grillabusqueda.getItems().clear(); // Limpia resultados previos

        String sql = "SELECT DISTINCT o.id_orden, o.fecha, p.razon_social, " +
                    "CONCAT(pr.apellido, ', ', pr.nombre) AS profesional, " +
                    "pre.nombre_prestacion, f.id_Federacion, o.ApeNom " +
                    "FROM PrestacionesXordenes px " +
                    "JOIN Ordenes o ON px.id_orden = o.id_orden " +
                    "JOIN Prestaciones pre ON px.id_prestacion = pre.id_prestacion " +
                    "JOIN Prestadores p ON o.id_prestador = p.id_prestador " +
                    "JOIN Profesionales pr ON o.id_profesional = pr.id_profesional " +
                    "LEFT JOIN Federaciones f ON o.id_federacion = f.id_Federacion " +
                    "WHERE 1=1 ";

        if (rbtnfecha.isSelected()) {
            if (dtpdesde.getValue() != null && dtphasta.getValue() != null) {
                sql += " AND o.fecha BETWEEN '" + dtpdesde.getValue() + "' AND '" + dtphasta.getValue() + "'";
            } else {
                mostrarError("Debe seleccionar ambas fechas.");
                return;
            }
        } else if (rbtnorden.isSelected()) {
            sql += " AND o.id_orden = " + txtbusqueda.getText();
        } else if (rbtnprofesional.isSelected()) {
            sql += " AND CONCAT(pr.apellido, ', ', pr.nombre) LIKE '%" + txtbusqueda.getText() + "%'";
        } else if (rbtnpaciente.isSelected()) {
            sql += " AND o.ApeNom LIKE '%" + txtbusqueda.getText() + "%'";
        } else if (rbtnos.isSelected()) {
            sql += " AND p.razon_social LIKE '%" + txtbusqueda.getText() + "%'";
        } else if (rbtnprestacion.isSelected()) {
            sql += " AND pre.nombre_prestacion LIKE '%" + txtbusqueda.getText() + "%'";
        }     
        cargarResultados(sql);
        //System.out.println(sql);
}

    private void mostrarError(String mensaje){
        System.err.println("mostrar error");
    }
    public void mostrarMensaje(String titulo, String encabezado, String contenido, AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    private void cargarResultados(String sql){
        
         try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // Obtener la fecha de la base de datos como Timestamp
            Timestamp timestamp = rs.getTimestamp("fecha");
            LocalDate fecha = null;

            // Comprobar si la fecha no es null
            if (timestamp != null) {
                // Convertir el Timestamp a LocalDate
                fecha = timestamp.toLocalDateTime().toLocalDate();
                //System.out.println(rs.getInt("id_orden") + "-- " + fecha);  // Imprimir para depuración
            }

            // Obtener el valor de 'ApeNom' y manejar el caso de null
            String apeNom = rs.getString("ApeNom");
            if (apeNom == null || apeNom.trim().isEmpty()) {
                apeNom = "Desconocido";
            }  // Asignar "Desconocido" si es null

            // Crear un DatePicker y asignar la fecha
            //DatePicker datePicker = new DatePicker();
            //datePicker.setValue(fecha);  // La fecha puede ser null, y el DatePicker lo maneja correctamente
            
            //System.out.println(rs.getInt("id_orden") + "--" + fecha + "--" + apeNom + "--" +rs.getString("razon_social")+"--"+rs.getString("profesional"));

            // Crear el objeto de 'GrillaBusquedaOrdenes' con los valores obtenidos
            GrillaBusquedaOrdenes resultado = new GrillaBusquedaOrdenes( 
                rs.getInt("id_orden"),
                fecha,  // Aquí pasamos el DatePicker con la fecha
                rs.getString("razon_social"),
                rs.getString("profesional"),
                rs.getString("nombre_prestacion"),
                rs.getInt("id_Federacion"),
                apeNom
            );
               
                //System.out.println(rs.getInt("id_orden") + "--" + rs.getTimestamp("fecha"));
                grillabusqueda.getItems().add(resultado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //mostrarError("Error al cargar resultados.");
            mostrarMensaje("Error", "Falló la busqueda", "Error al cargar los resultados", Alert.AlertType.ERROR);
            
        }
    }


    @FXML  private void onSalir(ActionEvent event) {
        // Obtener el Stage actual desde el botón
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close(); // Cierra la ventana

    }

    @FXML private void onSeleccionar(ActionEvent event) {
        seleccionarOrdenYSalir();
    }
    
   private void seleccionarOrdenYSalir() {
    GrillaBusquedaOrdenes orden = grillabusqueda.getSelectionModel().getSelectedItem();
    if (orden != null) {
        ClaseCompartida.nroOrdenSeleccionado = orden.getId_orden();

        try {
            // Cargar pantallaCargaOrdenes.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pantallaCargaOrdenes.fxml"));
            Parent root = loader.load();

            // Obtener controlador y pasarle los datos seleccionados
            ControllerPrestacionesXOrden controlador = loader.getController();
            controlador.cargarDatosOrden(ClaseCompartida.nroOrdenSeleccionado);

            // Obtener el Stage actual y reemplazar el contenido (no crear uno nuevo)
            //Stage currentStage = (Stage) grillabusqueda.getScene().getWindow();
            //Scene newScene = new Scene(root);
            //currentStage.setScene(newScene); // Reemplaza la escena en la misma ventana
            
            Stage stage = (Stage) grillabusqueda.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


    
}






