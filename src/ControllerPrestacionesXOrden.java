package src;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.sql.Statement;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import src.Utilidades.ComboItem;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ControllerPrestacionesXOrden{
    @FXML private Label lblnroOrden;
    @FXML private TextField txtAfiliado;
    @FXML private TextField txtApenom;
    @FXML private TextField txtEdad;
    @FXML private TextField txtDenuncia;
    @FXML private TextField txtDomicilio;
    @FXML private TextField txtLocalidad;
    @FXML private TextField txtDiagnostico;
    @FXML private TextField txtSala;
    @FXML private TextField txtUti;
    @FXML private TextField txtUsi;
    @FXML private TextField txtMonto;
    @FXML private TextField txtAlta;
    @FXML private TextArea txtObservaciones;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnModificar;
    @FXML private Button btnGrabar;
    @FXML private Button btnFiltrar;
    @FXML private Button btnAgregar;
    @FXML private ComboBox<ComboItem> cmbPrestaciones;
    @FXML private ComboBox<ComboItem> cmbProfesional;
    @FXML private ComboBox<ComboItem> cmbOS;
    @FXML private ComboBox<ComboItem> cmbFederacion;
    @FXML private TableView<GrillaPrestacionesXOrdenes> grillaPrestacionesXOrdenes;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, Integer> col_id;
    @FXML private DatePicker dtpingreso;
    @FXML private DatePicker dtpEgreso;
    @FXML private DatePicker dtpFecha;
    @FXML private RadioButton optgravado;
    @FXML private RadioButton optNoGravado;
    @FXML private RadioButton optmonotributo;
    @FXML private RadioButton optrural;
    @FXML private RadioButton optActividad;
    @FXML private RadioButton optFamilia;
    @FXML private RadioButton optClasic;
    
    
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, Integer> col_prestacion;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, Integer> col_Edad;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, Date> col_Ingreso;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, Date> col_Egreso;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, String> col_Domicilio;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, String> col_Localidad;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, String> col_Diagnostico;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, String> col_Condicion;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, String> col_Sala;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, String> col_Uti;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, String> col_Usi;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, Double> col_Monto;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, String> col_Observaciones;
   

  

    @FXML public void initialize() {
        System.out.println("✅ Método initialize ejecutado");

        col_id.setCellValueFactory(new PropertyValueFactory<>("id_orden"));
        col_prestacion.setCellValueFactory(new PropertyValueFactory<>("id_prestacion"));
        col_Edad.setCellValueFactory(new PropertyValueFactory<>("Edad"));
        col_Ingreso.setCellValueFactory(new PropertyValueFactory<>("FecIngreso"));
        col_Egreso.setCellValueFactory(new PropertyValueFactory<>("FecEgreso"));    
        col_Domicilio.setCellValueFactory(new PropertyValueFactory<>("Domicilio"));
        col_Localidad.setCellValueFactory(new PropertyValueFactory<>("Localidad"));
        col_Diagnostico.setCellValueFactory(new PropertyValueFactory<>("Diagnostico"));
        col_Condicion.setCellValueFactory(new PropertyValueFactory<>("CondicionAlta"));
        col_Sala.setCellValueFactory(new PropertyValueFactory<>("sala"));
        col_Uti.setCellValueFactory(new PropertyValueFactory<>("UTI"));
        col_Usi.setCellValueFactory(new PropertyValueFactory<>("USI"));
        col_Monto.setCellValueFactory(new PropertyValueFactory<>("Monto"));
        col_Observaciones.setCellValueFactory(new PropertyValueFactory<>("Observaciones"));
        
        Utilidades.configurarEfectoPresionado(btnNuevo);
        Utilidades.configurarEfectoPresionado(btnGrabar);
        
        String sql = "SELECT id_profesional, CONCAT( UPPER(apellido), ' ', UPPER(nombre)) AS nombre  FROM profesionales";
        Utilidades.cargarCombo(cmbProfesional, sql,"nombre", "id_profesional");

        System.out.println("cmbPrestaciones: " + cmbPrestaciones);

        System.out.println("cmbPrestaciones in initialize: " + cmbPrestaciones);
        String sql1 = "SELECT id_prestacion,  UPPER(nombre_prestacion)AS nombre_prestacion FROM prestaciones";
        Utilidades.cargarCombo(cmbPrestaciones,sql1, "nombre_prestacion","id_prestacion");
        
        
        String sql2 = "SELECT id_prestador,  UPPER(razon_social) AS razon_social FROM prestadores";    
        Utilidades.cargarCombo(cmbOS,sql2, "razon_social", "id_prestador");

        
        String sql3 = "SELECT id_federacion,  UPPER(Nombre_federacion) AS nombre_federacion FROM federaciones";    
        Utilidades.cargarCombo(cmbFederacion,sql3,"nombre_federacion", "id_federacion");

        //txtMonto.setDisable(true);
        //cargarDatosPrestacionesOrden();

    }

    @FXML private void onNuevaOrden(ActionEvent event) {
        
        //btnAgregar.setDisable(!validarCamposCompletos());
        btnAgregar.setDisable(false);
        //Utilidades.limpiarCampos(null, null);
        Utilidades.limpiarCampos(new TextField[]{txtAfiliado, txtApenom,
        txtEdad, txtDenuncia,txtDomicilio,txtLocalidad,txtDiagnostico,txtSala,
        txtUsi,txtUti, txtAlta,txtMonto},new TextArea[]{txtObservaciones},cmbOS, 
        cmbProfesional,cmbPrestaciones, cmbFederacion);
        dtpFecha.setValue(null);
        dtpingreso.setValue(null);
        dtpEgreso.setValue(null);
        
        Button boton = (Button) event.getSource(); // Obtiene el botón que fue presionado
        Utilidades.configurarEfectoPresionado(boton); // Aplica el cambio de color
    }

    @FXML private void onbtnGrabar(ActionEvent event){
        int nuevoId = obtenerNuevoIdOrden(); 
        String StringId = String.valueOf(nuevoId);
        lblnroOrden.setText(StringId);
        String sqlOrden = "INSERT INTO ordenes (id_orden, id_prestador, id_profesional, fecha, nro_afiliado, ApeNom, id_prof_derivado, id_Federacion, tipo_liquidacion, cod_denuncia, id_profesional_original) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlPrestacion = "INSERT INTO PrestacionesXordenes (id_orden, id_prestacion, Edad, FecIngreso, FecEgreso, Domicilio, Localidad, Diagnostico, CondicionAlta, sala, UTI, USI, Monto, Observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971")) {
        conn.setAutoCommit(false); // Iniciamos la transacción

            // Insertar orden
            try (PreparedStatement psOrden = conn.prepareStatement(sqlOrden)) {
                psOrden.setInt(1, nuevoId);
                psOrden.setInt(2, cmbOS.getSelectionModel().getSelectedIndex()); // ejemplo
                psOrden.setInt(3, cmbProfesional.getSelectionModel().getSelectedIndex());
                psOrden.setDate(4, java.sql.Date.valueOf(dtpFecha.getValue()));
                psOrden.setString(5, txtAfiliado.getText());
                psOrden.setString(6, txtApenom.getText());
                psOrden.setInt(7, 0); // derivado: valor por defecto
                psOrden.setInt(8, cmbFederacion.getSelectionModel().getSelectedIndex());
                psOrden.setInt(9, obtenerTipoLiquidacion());
                psOrden.setString(10, txtDenuncia.getText());
                psOrden.setInt(11, cmbProfesional.getSelectionModel().getSelectedIndex()); // mismo profesional original
                psOrden.executeUpdate();
            }

            // Insertar prestaciones desde la grilla
            try (PreparedStatement psPrestacion = conn.prepareStatement(sqlPrestacion)) {
                for (Object item : grillaPrestacionesXOrdenes.getItems()) {
                    GrillaPrestacionesXOrdenes  prestacion = (GrillaPrestacionesXOrdenes ) item; // crea este modelo tú mismo
                    psPrestacion.setInt(1, nuevoId);
                    psPrestacion.setInt(2, prestacion.getId_prestacion());
                    psPrestacion.setInt(3, prestacion.getEdad());
                    System.out.println("dtpingreso.getValue(): " + dtpingreso.getValue());

                    psPrestacion.setDate(4, java.sql.Date.valueOf(prestacion.getFecIngreso()));
                    psPrestacion.setDate(5, java.sql.Date.valueOf(prestacion.getFecEgreso()));
                    psPrestacion.setString(6, prestacion.getDomicilio());
                    psPrestacion.setString(7, prestacion.getLocalidad());
                    psPrestacion.setString(8, prestacion.getDiagnostico());
                    psPrestacion.setString(9, prestacion.getCondicionAlta());
                    psPrestacion.setString(10, prestacion.getSala());
                    psPrestacion.setString(11, prestacion.getUTI());
                    psPrestacion.setString(12, prestacion.getUSI());
                    psPrestacion.setBigDecimal(13, prestacion.getMonto());
                    psPrestacion.setString(14, prestacion.getObservaciones());
                    psPrestacion.addBatch(); // agrupamos las inserciones
                }
                psPrestacion.executeBatch();
        }

        conn.commit(); // Confirmamos la transacción

        // Recorrer las filas de la grilla y actualizar solo la columna id_orden
        for (Object item : grillaPrestacionesXOrdenes.getItems()) {
            GrillaPrestacionesXOrdenes prestacion = (GrillaPrestacionesXOrdenes) item;
            System.out.println("dentro del for de actualizacoion del id_orden en la grilla");
            // Actualizar el id_orden en la grilla
            prestacion.setId_orden(Integer.parseInt(lblnroOrden.getText())); // Aquí asignamos el nuevo id_orden a cada objeto
            //item.setId_orden(Integer.parseInt(lblnroOrden.getText()));
        }
        grillaPrestacionesXOrdenes.refresh();

        mostrarMensaje("Información", null, 
        "La orden fue grabada correctamente.", 
        Alert.AlertType.INFORMATION);;
    } catch (SQLException e) {
        e.printStackTrace();
        mostrarMensaje("Error", "Falló la registracion", 
        "No se pudo conectar con la base de datos.", 
        Alert.AlertType.ERROR);
;
    }
    } 
    @FXML public void handleBotonPresionado(ActionEvent event) {
        
        Button boton = (Button) event.getSource(); // Obtiene el botón que fue presionado
        Utilidades.configurarEfectoPresionado(boton); // Llama al método genérico
    }

    @FXML private void onAgregarAGrilla(ActionEvent event) {
    System.out.println("Ingresando a crear un objeto de GrillaPrestacionesXOrdenes");

    lblnroOrden.setText(String.valueOf(123));
    if (cmbPrestaciones.getValue() == null) {
        System.out.println("❌ Error: No hay una prestación seleccionada.");
        return; // Evita que el programa falle
    }

    GrillaPrestacionesXOrdenes item = new GrillaPrestacionesXOrdenes();
 
    if (!lblnroOrden.getText().isEmpty() && lblnroOrden.getText().matches("\\d+")) {
        item.setId_orden(Integer.parseInt(lblnroOrden.getText()));
    } else {
        System.out.println("❌ Error: lblnroOrden no tiene un número válido.");
        return;
    }

    item.setId_prestacion(((ComboItem) cmbPrestaciones.getValue()).getId());
    item.setEdad(Integer.parseInt(txtEdad.getText()));
    if (dtpingreso != null && dtpingreso.getValue() != null) {
        item.setFecIngreso(dtpingreso.getValue());
    } else {
        System.out.println("❌ Error: El DatePicker dtpingreso es null o no tiene valor seleccionado.");
    }

    //item.setFecIngreso(dtpingreso.getValue());
    item.setFecEgreso(dtpEgreso.getValue());
    item.setDomicilio(txtDomicilio.getText());
    item.setLocalidad(txtLocalidad.getText());
    item.setDiagnostico(txtDiagnostico.getText());
    item.setCondicionAlta(txtAlta.getText());
    item.setSala(txtSala.getText());
    item.setUTI(txtUti.getText());
    item.setUSI(txtUsi.getText());
    item.setMonto(new BigDecimal(txtMonto.getText()));
    item.setObservaciones(txtObservaciones.getText());

    grillaPrestacionesXOrdenes.getItems().add(item); // Agregar a la tabla

    System.out.println("✅ Objeto agregado correctamente.");
    }

    @FXML private void onSeleccionarPrestacion(ActionEvent event) { 
        ComboItem item = cmbPrestaciones.getValue(); // obtiene el item seleccionado
        if (item != null) {
            System.out.println("🟢 Prestación seleccionada: " + item.getDescripcion() + " (ID: " + item.getId() + ")");
            // Podés hacer lo que quieras con el ID o el texto acá
           String sqltipo = "SELECT tipo_prestaciones.id_tipo_prestacion , tipo_prestaciones.nombre_tipo_prestacion," + 
                            "prestaciones.nombre_prestacion,tipo_prestacion " +
                            "FROM prestaciones INNER JOIN tipo_prestaciones " +
                            "ON prestaciones.tipo_prestacion = tipo_prestaciones.id_tipo_prestacion " +
                            "WHERE id_prestacion= ?";
                            
            System.out.println("SQL a ejecutar: " + sqltipo.replace("?", String.valueOf(item.getId())));
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971");
            PreparedStatement stmt = conn.prepareStatement(sqltipo)) {
    
            // Seteamos el parámetro para evitar concatenación insegura
            stmt.setInt(1, item.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {  // Hay al menos un registro
                    int idTipo = rs.getInt("id_tipo_prestacion");
                    if (idTipo == 1 || idTipo == 2) {
                        // Tu lógica para cuando es tipo 1 o 2
                        txtMonto.setText(String.valueOf(0));
                        txtMonto.setDisable(true);
                        System.out.println("Tipo 1 o 2 encontrado");
                        // Por ejemplo: return 0; o alguna acción
                    } else {
                        // Otro tipo distinto a 1 o 2
                        txtMonto.setDisable(false);
                        System.out.println("Tipo distinto a 1 o 2: " + idTipo);
                        // Otra acción o return
                    }
                } else {
                    // No hay registros
                    System.out.println("No hay registros, retornando 1");
                  }
            } //cierra el try
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepción
            }
        } //cierra el primer if
    }

    @FXML private void SeleccionarProfesional(ActionEvent event) {
        // Por ahora vacío
    }

    @FXML private void SeleccionarOS(ActionEvent event) {
        // Por ahora vacío
            System.out.println("Ingresando a metodo seleccionarOS");
        
    }

    private int obtenerNuevoIdOrden() {
        String sql = "SELECT MAX(Id_orden) FROM ordenes";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971");
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1) + 1; // Devuelve el máximo + 1
            } else {
                return 1; // Si no hay registros, el id comienza en 1
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Retorna un valor negativo si hay error
        }
    }

    private int obtenerTipoLiquidacion() {
        if (optgravado.isSelected()) return 1;
        if (optNoGravado.isSelected()) return 2;
        if (optrural.isSelected()) return 3;
        if (optmonotributo.isSelected()) return 4;
        if (optActividad.isSelected()) return 5;
        if (optFamilia.isSelected()) return 6;
        if (optClasic.isSelected()) return 7;
        return 0; // Por defecto
    }

    public void mostrarMensaje(String titulo, String encabezado, String contenido, AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

}
   
