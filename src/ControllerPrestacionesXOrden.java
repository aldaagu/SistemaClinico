package src;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.sql.Statement;
import java.time.LocalDate;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
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
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.util.Callback;


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
    @FXML private Button BtnFiltrar;
    @FXML private Button btnAgregar;
    @FXML private ComboBox<ComboItem> cmbPrestaciones;
    @FXML private ComboBox<ComboItem> cmbProfesional;
    @FXML private ComboBox<ComboItem> cmbOS;
    @FXML private ComboBox<ComboItem> cmbFederacion;
    @FXML private TableView<GrillaPrestacionesXOrdenes> grillaPrestacionesXOrdenes;
    
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
    
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, Integer> col_id;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, Integer> col_prestacion;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, Date> col_Ingreso;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, Date> col_Egreso;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, String> col_Diagnostico;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, String> col_Condicion;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, String> col_Sala;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, String> col_Uti;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, String> col_Usi;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, Double> col_Monto;
    @FXML private TableColumn<GrillaPrestacionesXOrdenes, String> col_Observaciones;
   
    private PrestacionesxOrdenes prestacionSeleccionada;

  

    @FXML public void initialize() {
        System.out.println("✅ Método initialize ejecutado");

        col_id.setCellValueFactory(new PropertyValueFactory<>("idOrden"));
        col_prestacion.setCellValueFactory(new PropertyValueFactory<>("idPrestacion"));
        col_Ingreso.setCellValueFactory(new PropertyValueFactory<>("FecIngreso"));
        col_Egreso.setCellValueFactory(new PropertyValueFactory<>("FecEgreso"));    
        col_Diagnostico.setCellValueFactory(new PropertyValueFactory<>("Diagnostico"));
        col_Condicion.setCellValueFactory(new PropertyValueFactory<>("CondicionAlta"));
        col_Sala.setCellValueFactory(new PropertyValueFactory<>("sala"));
        col_Uti.setCellValueFactory(new PropertyValueFactory<>("UTI"));
        col_Usi.setCellValueFactory(new PropertyValueFactory<>("USI"));
        col_Monto.setCellValueFactory(new PropertyValueFactory<>("Monto"));
        col_Observaciones.setCellValueFactory(new PropertyValueFactory<>("Observaciones"));
        
        Utilidades.configurarEfectoPresionado(btnNuevo);
        Utilidades.configurarEfectoPresionado(btnGrabar);
        Utilidades.configurarEfectoPresionado(BtnFiltrar);
        
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


    //});.setDisable(true);
        //cargarDatosPrestacionesOrden();
        Platform.runLater(() -> {
            if (ClaseCompartida.nroOrdenSeleccionado > 0) {
                cargarDatosOrden(ClaseCompartida.nroOrdenSeleccionado);
                ClaseCompartida.nroOrdenSeleccionado = 0; // Limpiar para evitar recarga innecesaria
            }
        });

      
        grillaPrestacionesXOrdenes.setRowFactory(tv -> {
        TableRow<GrillaPrestacionesXOrdenes> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !row.isEmpty()) {
                GrillaPrestacionesXOrdenes filaSeleccionada = row.getItem();

                int orden = filaSeleccionada.getIdOrden();
                int prestacion = filaSeleccionada.getIdPrestacion();

                System.out.println("col1: " + orden + ", col2: " + prestacion);

                if (Eliminar(orden, prestacion)) {
                    Utilidades.mostrarAlerta(AlertType.INFORMATION, "Prestacion Eliminada", "Prestacion Eliminada");
                    // Opcional: refrescar la tabla luego de borrar
                    cargarDatosOrden(orden); 
                    btnAgregar.setDisable(false);
                }
            }
    });
    return row;
});
    }

    @FXML private void onNuevaOrden(ActionEvent event) {
        
        //btnAgregar.setDisable(!validarCamposCompletos());
        btnAgregar.setDisable(false);
        //Utilidades.limpiarCampos(null, null);
        Utilidades.limpiarCampos(new TextField[]{txtAfiliado, txtApenom,
        txtEdad, txtDenuncia,txtDomicilio,txtLocalidad,txtDiagnostico,txtSala,
        txtUsi,txtUti, txtAlta,txtMonto},new TextArea[]{txtObservaciones},cmbOS, 
        cmbProfesional,cmbPrestaciones, cmbFederacion);
        lblnroOrden.setText("");
        grillaPrestacionesXOrdenes.getItems().clear();
        dtpFecha.setValue(null);
        dtpingreso.setValue(null);
        dtpEgreso.setValue(null);
        
        Button boton = (Button) event.getSource(); // Obtiene el botón que fue presionado
        Utilidades.configurarEfectoPresionado(boton); // Aplica el cambio de color
    }

    @FXML private void onbtnGrabar(ActionEvent event) {
    // Obtener nuevo ID para la orden
    int nuevoId = obtenerNuevoIdOrden(); 
    String StringId = String.valueOf(nuevoId);
    lblnroOrden.setText(StringId);
    

    // Validación de los campos antes de continuar
    if (!validarCampos()) {
        return; // Si los campos no son válidos, salimos de la función
    }

    // Validación para asegurarnos de que los ComboBox tengan valores seleccionados
    if (!validarCombo(cmbOS, "Debe seleccionar una obra social.") ||
        !validarCombo(cmbFederacion, "Debe seleccionar una federación.") ||
        !validarCombo(cmbPrestaciones, "Debe seleccionar una prestación.") ||
        !validarCombo(cmbProfesional, "Debe seleccionar un profesional.")) {
        return; // Si alguno de los ComboBox no tiene selección, salimos de la función
    }

    // Obtener los valores seleccionados de los ComboBox
    ComboItem osSeleccionada = cmbOS.getValue();
    ComboItem federacionSeleccionada = cmbFederacion.getValue();
    ComboItem prestacionSeleccionada = cmbPrestaciones.getValue();
    ComboItem profesionalSeleccionado = cmbProfesional.getValue();

    // Validamos que los ComboBox no sean nulos
    System.out.println(osSeleccionada+"--"+federacionSeleccionada+"--"+prestacionSeleccionada);
    if (osSeleccionada == null || federacionSeleccionada == null || prestacionSeleccionada == null || profesionalSeleccionado == null) {
        mostrarAlerta("Debe seleccionar todos los campos obligatorios.");
        return; // Si algún ComboBox es nulo, salimos de la función
    }

    // Obtener los IDs de los ComboItem seleccionados
    int osId = osSeleccionada.getId();  // ID de la obra social seleccionada
    int federacionId = federacionSeleccionada.getId();  // ID de la federación seleccionada
    //int prestacionId = prestacionSeleccionada.getId();  // ID de la prestación seleccionada
    int profesionalId = profesionalSeleccionado.getId();  // ID del profesional seleccionado

    // Obtener los valores de los campos de texto
    String nroAfiliado = txtAfiliado.getText();  // Número de afiliado
    String apeNom = txtApenom.getText();  // Apellido y nombre del afiliado
    int edad = Integer.parseInt(txtEdad.getText());  // Edad del afiliado
    String domicilio = txtDomicilio.getText();  // Domicilio del afiliado
    String localidad = txtLocalidad.getText();  // Localidad del afiliado
        
    // Aplicar la validación y conversión a null si es necesario
    nroAfiliado = isEmpty(nroAfiliado) ? "--" : nroAfiliado;
    apeNom = isEmpty(apeNom) ? "--" : apeNom;
    domicilio = isEmpty(domicilio) ? "--" : domicilio;
    localidad = isEmpty(localidad) ? "--" : localidad;
    
    // Crear la orden con todos los datos
    Ordenes orden = new Ordenes(
        nuevoId,  // ID de la orden, lo dejamos en 0 ya que es autoincremental
        osId,     // ID de la obra social
        profesionalId,  // ID del profesional
        java.sql.Date.valueOf(dtpFecha.getValue()),  // Fecha (convertimos LocalDate a Date)
        nroAfiliado,  // Número de afiliado
        apeNom,  // Apellido y nombre del afiliado
        federacionId,  // ID de la federación
        edad,  // Edad
        domicilio,  // Domicilio
        localidad,  // Localidad
        1,  // Tipo de liquidación (constante, en este caso 1)
        0   // Estado de la orden (0 significa estado inicial)
    );
    

   // SQL de inserción para la tabla 'ordenes' y 'prestacionesXordenes'
try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971")) {
    conn.setAutoCommit(false);  // Iniciamos la transacción

    // Insertar la orden
    System.out.println("antes de llamar a orden.insertar");
    orden.insertar();

 for (GrillaPrestacionesXOrdenes prestacion : grillaPrestacionesXOrdenes.getItems()) {

    // Convertir las fechas LocalDate a java.sql.Date
    //java.sql.Date sqlFecIngreso = java.sql.Date.valueOf(prestacion.getFecIngreso());  // Conversión de LocalDate a java.sql.Date
    //java.sql.Date sqlFecEgreso = java.sql.Date.valueOf(prestacion.getFecEgreso());    // Conversión de LocalDate a java.sql.Date

    
    // Crear la instancia de PrestacionesxOrdenes con los datos de la grilla
    PrestacionesxOrdenes prestacionorden = new PrestacionesxOrdenes(
        nuevoId,                          
        prestacion.getIdPrestacion(),     // ID de la prestación desde la grilla
        prestacion.getFecIngreso(), 
        prestacion.getFecEgreso(),   // Ya es LocalDate, no necesita conversión
        prestacion.getDiagnostico(),                     // Fecha de Egreso (convertida a java.sql.Date)
        prestacion.getCondicionAlta(),    // Condición de Alta
        prestacion.getSala(),             // Sala
        prestacion.getUTI(),              // UTI
        prestacion.getUSI(),              // USI
        prestacion.getMonto(),            // Monto
        prestacion.getObservaciones()     // Observaciones
    );

    // Llamada a la función de inserción
    System.out.println("antes de llamar a prestacionorden.insertar");
    prestacionorden.insertar(conn);  // Inserción en la base de datos
}


    conn.commit();  // Confirmar la transacción

    // Recorrer las filas de la grilla y actualizar solo la columna id_orden
    for (Object item : grillaPrestacionesXOrdenes.getItems()) {
        GrillaPrestacionesXOrdenes prestacion = (GrillaPrestacionesXOrdenes) item;
        prestacion.setIdOrden(nuevoId);  // Actualizar id_orden en la grilla
    }

    grillaPrestacionesXOrdenes.refresh();  // Refrescar la grilla

    // Mostrar mensaje de éxito
    mostrarMensaje("Información", null, "La orden fue grabada correctamente.", Alert.AlertType.INFORMATION);

} catch (SQLException e) {
    e.printStackTrace();
    mostrarMensaje("Error", "Falló la registración", "No se pudo conectar con la base de datos.", Alert.AlertType.ERROR);
}
}


    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
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

    GrillaPrestacionesXOrdenes item = new GrillaPrestacionesXOrdenes(
        Integer.parseInt(lblnroOrden.getText()),
        ((ComboItem) cmbPrestaciones.getValue()).getId(),
        dtpingreso.getValue(),
        dtpEgreso.getValue(),
        txtDiagnostico.getText(),
        txtAlta.getText(),
        txtSala.getText(),
        txtUti.getText(),
        txtUsi.getText(),
        Double.parseDouble(txtMonto.getText()),
        txtObservaciones.getText()
    );
 
    if (!lblnroOrden.getText().isEmpty() && lblnroOrden.getText().matches("\\d+")) {
        item.setIdOrden(Integer.parseInt(lblnroOrden.getText()));
    } else {
        System.out.println("❌ Error: lblnroOrden no tiene un número válido.");
        return;
    }

    item.setIdPrestacion(((ComboItem) cmbPrestaciones.getValue()).getId());
    //item.setEdad(Integer.parseInt(txtEdad.getText()));
    if (dtpingreso != null && dtpingreso.getValue() != null) {
        item.setFecIngreso(dtpingreso.getValue());
    } else {
        System.out.println("❌ Error: El DatePicker dtpingreso es null o no tiene valor seleccionado.");
    }

    //item.setFecIngreso(dtpingreso.getValue());
    item.setFecEgreso(dtpEgreso.getValue());
    item.setDiagnostico(txtDiagnostico.getText());
    item.setCondicionAlta(txtAlta.getText());
    item.setSala(txtSala.getText());
    item.setUTI(txtUti.getText());
    item.setUSI(txtUsi.getText());
    item.setMonto(Double.parseDouble(txtMonto.getText()));
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
        //Utilidades.limpiarCampos(new TextField[]{txtAfiliado, txtApenom,
        //txtEdad, txtDenuncia,txtDomicilio,txtLocalidad,txtDiagnostico,txtSala,
        //txtUsi,txtUti, txtAlta,txtMonto},new TextArea[]{txtObservaciones},cmbOS, 
        //cmbProfesional,cmbPrestaciones, cmbFederacion);
    }

    @FXML private void SeleccionarProfesional(ActionEvent event) {
        // Por ahora vacío
    }

    @FXML private void SeleccionarOS(ActionEvent event) {
        // Por ahora vacío
            System.out.println("Ingresando a metodo seleccionarOS");
        
    }

    @FXML private void onFiltrar(ActionEvent event){
        try {
            // Cargar el archivo FXML de la pantalla a abrir
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PantallaBusquedaOrdenes.fxml"));
            Parent root = loader.load();

            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            oldStage.close(); // Cerrás la ventana anterior

            // Crear una nueva escena
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setTitle("Buscar ordenes");
            newStage.setScene(scene);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();
            // Obtener el Stage actual
            //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            //stage.setScene(scene);
            //stage.show(); // Mostrar la nueva pantalla
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Error al abrir PantallaBusquedaOrdenes.");
        }
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

    public void cargarDatosOrden(int nroOrden) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971")) {
            // Consulta para obtener los datos de la orden
            String sqlOrden = "SELECT * FROM ordenes WHERE id_orden = ?";
            try (PreparedStatement psOrden = conn.prepareStatement(sqlOrden)) {
                psOrden.setInt(1, nroOrden);
                try (ResultSet rsOrden = psOrden.executeQuery()) {
                    if (rsOrden.next()) {
                        // Cargar datos de la orden en los campos correspondientes
                        int idOrden = rsOrden.getInt("id_orden");
                        lblnroOrden.setText(rsOrden.wasNull() ? "--" : String.valueOf(idOrden));
                        //txtAfiliado.setText(rsOrden.getString("Nro_Afiliado"));
                        txtAfiliado.setText(
                            rsOrden.getString("Nro_Afiliado") == null || rsOrden.getString("Nro_Afiliado").trim().isEmpty()
                            ? "--" : rsOrden.getString("Nro_Afiliado")
                        );
                        int idProfesional = rsOrden.getInt("id_profesional");
                        int idPrestador = rsOrden.getInt("id_prestador");
                        int idFederacion = rsOrden.getInt("id_Federacion");


                        for (ComboItem item : cmbProfesional.getItems()) {
                            if (item.getId() == idProfesional) {
                                cmbProfesional.getSelectionModel().select(item);
                                break;
                            }
                        }
                        for (ComboItem item : cmbOS.getItems()) {
                            if (item.getId() == idPrestador) {
                                cmbOS.getSelectionModel().select(item);
                                break;
                            }
                        }
                        for (ComboItem item : cmbOS.getItems()) {
                            if (item.getId() == idFederacion) {
                                cmbFederacion.getSelectionModel().select(item);
                                break;
                            }
                        }
                        //txtApenom.setText(rsOrden.getString("ApeNom"));
                        //txtApenom.setText( rsOrden.getString("ApeNom")!= null or Apenom="" ? rsOrden.getString("ApeNom") : "ddd");
                        txtApenom.setText((rsOrden.getString("ApeNom") == null || rsOrden.getString("ApeNom").isEmpty()) ? "desconocido" : rsOrden.getString("ApeNom") );
                        //txtEdad.setText(String.valueOf(rsOrden.getInt("Edad")));
                        dtpFecha.setValue(rsOrden.getDate("Fecha").toLocalDate());
                        int edad = rsOrden.getInt("Edad");
                        txtEdad.setText(String.valueOf(edad));
                        //txtDenuncia.setText(rsOrden.getString("cod_denuncia"));
                        String domicilio = rsOrden.getString("Domicilio");
                        domicilio = (domicilio == null || domicilio.trim().isEmpty()) ? "--" : domicilio;
                        String localidad = rsOrden.getString("Localidad");
                        localidad = (localidad == null || localidad.trim().isEmpty()) ? "--" : localidad;
                        String codDenuncia = rsOrden.getString("cod_denuncia");
                            txtDenuncia.setText(
                            codDenuncia == null || codDenuncia.trim().isEmpty() ? "--" : codDenuncia
                        );
                        //txtDiagnostico.setText(rsOrden.getString("diagnostico"));
                        // Cargar ComboBoxes y RadioButtons según corresponda
                        // ...
                        System.out.println(rsOrden.getString("ApeNom")+rsOrden.getDate("Fecha").toLocalDate());
                    }
                }
            }

            // Consulta para obtener las prestaciones asociadas a la orden
            String sqlPrestaciones = "SELECT * FROM prestacionesxordenes WHERE id_orden = ?";
            try (PreparedStatement psPrestaciones = conn.prepareStatement(sqlPrestaciones)) {
                psPrestaciones.setInt(1, nroOrden);
                try (ResultSet rsPrestaciones = psPrestaciones.executeQuery()) {
                    ObservableList<GrillaPrestacionesXOrdenes> listaPrestaciones = FXCollections.observableArrayList();
                    while (rsPrestaciones.next()) {
                            int idOrden = rsPrestaciones.getInt("id_orden");
                            int idPrestacion = rsPrestaciones.getInt("id_prestacion");
                            

                            LocalDate fecIngreso = rsPrestaciones.getDate("FecIngreso") != null ?
                                                rsPrestaciones.getDate("FecIngreso").toLocalDate() : null;

                            LocalDate fecEgreso = rsPrestaciones.getDate("FecEgreso") != null ?
                                                rsPrestaciones.getDate("FecEgreso").toLocalDate() : null;

                    
                            String diagnostico = rsPrestaciones.getString("Diagnostico");
                            diagnostico = (diagnostico == null || diagnostico.trim().isEmpty()) ? "--" : diagnostico;

                            String condicionAlta = rsPrestaciones.getString("CondicionAlta");
                            condicionAlta = (condicionAlta == null || condicionAlta.trim().isEmpty()) ? "--" : condicionAlta;

                            String sala = rsPrestaciones.getString("sala");
                            sala = (sala == null || sala.trim().isEmpty()) ? "--" : sala;

                            String uti = rsPrestaciones.getString("UTI");
                            uti = (uti == null || uti.trim().isEmpty()) ? "--" : uti;

                            String usi = rsPrestaciones.getString("USI");
                            usi = (usi == null || usi.trim().isEmpty()) ? "--" : usi;

                            Double monto = rsPrestaciones.getDouble("Monto"); // Validar null si lo necesitás

                            String observaciones = rsPrestaciones.getString("Observaciones");
                            observaciones = (observaciones == null || observaciones.trim().isEmpty()) ? "--" : observaciones;
                            listaPrestaciones.add(new GrillaPrestacionesXOrdenes(
                                    idOrden,idPrestacion,fecIngreso,fecEgreso,diagnostico, condicionAlta,
                                    sala,uti,usi,monto,observaciones
                            ));
                    }
                    //txtDiagnostico.setText(rsPrestaciones.getString("Edad"));
                    //txtEdad.setText(String.valueOf(rsPrestaciones.getInt("Edad")));
                    grillaPrestacionesXOrdenes.setItems(listaPrestaciones);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private boolean validarCampos() {
        // Verificamos que los campos no estén vacíos
        if (dtpFecha.getValue() == null) {
            mostrarAlerta("Fecha de orden es obligatoria.");
            return false;
        }

        if (cmbProfesional.getValue() == null) {
            mostrarAlerta("El campo Profesional es obligatorio.");
            return false;
        }


        if (cmbOS.getValue() == null) {
            mostrarAlerta("Debe seleccionar una obra social.");
        return false;
        }
    
        if (cmbPrestaciones.getValue() == null) {
            mostrarAlerta("Debe seleccionar una Prestacion.");
        return false;
        }

        if (txtAfiliado.getText().isEmpty()) {
            mostrarAlerta("El campo Número de Afiliado es obligatorio.");
            return false;
        }

        if (txtApenom.getText().isEmpty()) {
            mostrarAlerta("El campo Apellido y Nombre es obligatorio.");
            return false;
        }

        if (txtEdad.getText().isEmpty()) {
            mostrarAlerta("El campo Edad es obligatorio.");
            return false;
        }

        try {
            // Verificamos que la edad sea un número válido
            Integer.parseInt(txtEdad.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Edad debe ser un número válido.");
            return false;
        }

        if (txtDomicilio.getText().isEmpty()) {
            mostrarAlerta("El campo Domicilio es obligatorio.");
            return false;
        }

        if (txtLocalidad.getText().isEmpty()) {
            mostrarAlerta("El campo Localidad es obligatorio.");
            return false;
        }

        if (txtDiagnostico.getText().isEmpty()) {
            mostrarAlerta("El campo Diagnóstico es obligatorio.");
            return false;
        }

        if (txtSala.getText().isEmpty()) {
            mostrarAlerta("El campo Sala es obligatorio.");
            return false;
        }

        if (txtUti.getText().isEmpty()) {
            mostrarAlerta("El campo UTI es obligatorio.");
            return false;
        }

        if (txtUsi.getText().isEmpty()) {
            mostrarAlerta("El campo USI es obligatorio.");
            return false;
        }

        if (txtMonto.getText().isEmpty()) {
            mostrarAlerta("El campo Monto es obligatorio.");
            return false;
        }

        try {
            // Verificamos que el monto sea un número válido
            Double.parseDouble(txtMonto.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Monto debe ser un número válido.");
            return false;
        }

        return true;
    }
    private void mostrarAlerta(String mensaje) {
        // Mostrar un mensaje de alerta si un campo no es válido
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Validación");
        alert.setHeaderText("Campos incorrectos");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    public boolean validarCombo(ComboBox<ComboItem> combo, String mensaje) {
    ComboItem seleccion = combo.getValue();
    if (seleccion == null) {
        mostrarAlerta(mensaje); // Muestra el mensaje específico
        return false; // Regresa false si no se seleccionó nada
    }
    return true; // Regresa true si se seleccionó algo
}
    
private Boolean Eliminar(int Orden, int Prestacion) {
    System.out.println("Eliminar llamado con orden=" + Orden + ", prestacion=" + Prestacion);
    if (Orden > 0 && Prestacion > 0) {
        String sql = "DELETE FROM prestacionesxOrdenes WHERE id_orden = ? AND id_prestacion = ?";
        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971");
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, Orden);
            stmt.setInt(2, Prestacion);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar prestación de orden: " + e.getMessage());
            return false;
        }
    }
    // Agregado return para evitar error de método sin retorno
    return false;
}
} 
