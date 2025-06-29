package src;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Locale;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;


public class ControllerPantallaLiquidacion {

    @FXML private TableView<GrillaLiquidaciones> GrillaLiquidaciones;
    @FXML private TableColumn<GrillaLiquidaciones, Integer> col_idliq;
    @FXML private TableColumn<GrillaLiquidaciones, String> col_periodo;
    @FXML private TableColumn<GrillaLiquidaciones, Double> col_montoL;
    @FXML private TableColumn<GrillaLiquidaciones, Boolean> col_estado;
    @FXML private TableColumn<GrillaLiquidaciones, Integer> col_tipo;

    @FXML private TableView<GrillaLDetalle> GrillaLDetalle;
    @FXML private TableColumn<GrillaLDetalle, Integer> col_idliqD;
    @FXML private TableColumn<GrillaLDetalle, String> col_idPro;
    @FXML private TableColumn<GrillaLDetalle, String> col_idFed;
    @FXML private TableColumn<GrillaLDetalle, String> col_idPres;
    @FXML private TableColumn<GrillaLDetalle, String> col_idPrestacion;
    @FXML private TableColumn<GrillaLDetalle, Integer> col_cant;
    @FXML private TableColumn<GrillaLDetalle, Double> col_montoD;

    @FXML private TextField txtPeriodo;
    @FXML private Label txtnroLiquidacion;
    @FXML private TextField txtMontoPracticas;
    @FXML private Button btnEliminar;
    @FXML private RadioButton rbtnNoquirurgicos;
    @FXML private RadioButton rbtnquirurgico;
    @FXML private Label lbltitulo;

  
    private ObservableList<GrillaLiquidaciones> listaLiquidacion = FXCollections.observableArrayList();
    
    private ObservableList<GrillaLDetalle> listaDetalleLiquidacion = FXCollections.observableArrayList();



    @FXML public void initialize() {
        configurarTablas();
        configurarTablasDetalle();
       

        cargarDatosDesdeBD();
        ListenerdeGrilla();
       
     }

     private void ListenerdeGrilla(){
            GrillaLiquidaciones.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Obtenemos el periodo de la fila seleccionada
                int nroLiquidacion = newValue.getIdLiquidacion();

                String periodoSeleccionado = newValue.getPeriodo();  // Asumiendo que "getCol_periodo" es el método para obtener el valor de la columna "Periodo"
                txtnroLiquidacion.setText(String.valueOf(nroLiquidacion));

                // Mostrar el periodo seleccionado en algún lugar si es necesario
                System.out.println("Periodo seleccionado: " + periodoSeleccionado);
                //String periodo = newValue.getPeriodo();
                txtPeriodo.setText(periodoSeleccionado);
                // Llamamos al método para cargar los detalles en GrillaLDetalle
                String tipo="(1,2)";
                if (rbtnNoquirurgicos.isSelected()){
                    lbltitulo.setText("Liquidacion de Consultas y Practicas");
                    tipo="(1,2)";
                    cargarDetalleDesdeBD(periodoSeleccionado,tipo);}
                else{
                     lbltitulo.setText("Liquidacion quirurgica");
                    tipo="(3)";
                    cargarDetalleDesdeBD(periodoSeleccionado,tipo);
                }
                }
             else {
                System.out.println("newvalue es nulo");
            }
            });
     }
     
    private void configurarTablasDetalle() {
        col_idliqD.setCellValueFactory(cellData -> cellData.getValue().idLiquidacionProperty().asObject());
        col_idPro.setCellValueFactory(cellData -> cellData.getValue().profesionalProperty());
        col_idFed.setCellValueFactory(cellData -> cellData.getValue().federacionProperty());
        col_idPres.setCellValueFactory(cellData -> cellData.getValue().prestadoraProperty());
        col_idPrestacion.setCellValueFactory(cellData -> cellData.getValue().prestacionProperty());
        col_cant.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty().asObject());
        col_montoD.setCellValueFactory(cellData -> cellData.getValue().montoProperty().asObject());

        txtPeriodo.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d{0,2}(/\\d{0,4})?")) {
                txtPeriodo.setText(oldVal);
            }
        });
        txtPeriodo.setPromptText("MM/yyyy");


        GrillaLDetalle.setItems(listaDetalleLiquidacion);
    }

    private void configurarTablas() {
        col_idliq.setCellValueFactory(cellData -> 
            new SimpleIntegerProperty(cellData.getValue().getIdLiquidacion()).asObject());

        col_periodo.setCellValueFactory(new PropertyValueFactory<>("periodo"));
        col_montoL.setCellValueFactory(new PropertyValueFactory<>("monto"));
        col_estado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        col_tipo.setCellValueFactory(new PropertyValueFactory<>("idTipo"));

        // Configuración para la tabla de detalles
        col_idliqD.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdLiquidacion()));
        col_idPro.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getProfesional()));
        col_idFed.setCellValueFactory(cellData -> new SimpleObjectProperty<String>(cellData.getValue().getFederacion()));
        col_idPres.setCellValueFactory(cellData -> new SimpleObjectProperty<String>(cellData.getValue().getPrestadora()));
        
        col_idPres.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrestadora()));
        col_idPres.setCellValueFactory(cellData -> cellData.getValue().prestadoraProperty());


        col_idPres.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrestadora()));
        col_idPrestacion.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrestacion()));
        col_cant.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCantidad()));
        col_montoD.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMonto()));
    }

    private void cargarDatosDesdeBD() {
        String query = "SELECT id_liquidacion, periodo, monto, estado, tipo " +
        "FROM Liquidaciones ORDER BY id_liquidacion DESC";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Clinica", "root", "Pchard_1971");
             //PreparedStatement stmt = conn.prepareStatement(query);
              PreparedStatement stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = stmt.executeQuery()) {
   

            listaLiquidacion.clear(); // Limpio la lista antes de cargar
               // rs.first();
            while (rs.next()) {
    
                //int idLiquidacion = rs.getInt("id_liquidacion");
                int idLiquidacion = rs.getInt("id_liquidacion");
                String periodo = rs.getString("periodo");
                double monto = rs.getDouble("monto");

                String estado = rs.getString("estado");
                boolean estadoBool = estado != null && (estado.equalsIgnoreCase("true") || estado.equalsIgnoreCase("1") || estado.equalsIgnoreCase("activo"));
                int tipoprestacion = rs.getInt("Tipo");
                listaLiquidacion.add(new GrillaLiquidaciones(idLiquidacion, periodo, monto, estadoBool, tipoprestacion));
            }

            GrillaLiquidaciones.setItems(listaLiquidacion);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   /*
private void cargarDetalleDesdeBD() {

    String periodo = txtPeriodo.getText();
    String mesStr = periodo.substring(0, 2);     // "05"
    String anioStr = periodo.substring(3, 7);    // "2019"
    int mes = Integer.parseInt(mesStr);
    int anio = Integer.parseInt(anioStr);

    String query = "SELECT 1000000 AS id_liquidacion,\n" +
            "    CONCAT(Personas.apellido, ', ', Personas.nombre) AS Profesional,\n" +
            "    Federaciones.Nombre_federacion,\n" +
            "    Prestadores.razon_social,\n" +
            "    Tipo_Liquidacion.Nombre_tipoLiquidacion,\n" +
            "    Prestaciones.nombre_prestacion,\n" +
            "    COUNT(PrestacionesXordenes.id_prestacion) AS Cantidad,\n" +
            "    Valores_prestaciones.Valor_prestacion * COUNT(PrestacionesXordenes.id_prestacion) AS Monto\n" +
            "FROM Ordenes\n" +
            "LEFT JOIN Prestadores ON Ordenes.id_prestador = Prestadores.id_prestador\n" +
            "LEFT JOIN Profesionales2 ON Ordenes.id_profesional = Profesionales2.id_profesional\n" +
            "LEFT JOIN Personas ON Personas.id_persona = Profesionales2.id_profesional\n" +
            "LEFT JOIN PrestacionesXordenes ON Ordenes.id_orden = PrestacionesXordenes.id_orden\n" +
            "LEFT JOIN Prestaciones ON PrestacionesXordenes.id_prestacion = Prestaciones.id_prestacion\n" +
            "LEFT JOIN Federaciones ON Ordenes.id_Federacion = Federaciones.id_Federacion\n" +
            "LEFT JOIN Tipo_Liquidacion ON Ordenes.Tipo_Liquidacion = Tipo_Liquidacion.id_TipoLiquidacion\n" +
            "LEFT JOIN PrestacionesXPrestador \n" +
            "    ON Ordenes.id_prestador = PrestacionesXPrestador.id_prestador\n" +
            "    AND PrestacionesXordenes.id_prestacion = PrestacionesXPrestador.id_prestacion\n" +
            "LEFT JOIN Valores_prestaciones \n" +
            "    ON Valores_prestaciones.id_categoria = Profesionales2.id_categoria\n" +
            "    AND PrestacionesXPrestador.id_prestacion = Valores_prestaciones.id_prestacion\n" +
            "    AND PrestacionesXPrestador.id_prestador = Valores_prestaciones.id_prestador\n" +
            "WHERE \n" +
            "    (Prestaciones.tipo_prestacion IN (1, 2) OR Prestaciones.tipo_prestacion IS NULL)\n" +
            "    AND YEAR(Ordenes.Fecha) = ?\n" +
            "    AND MONTH(Ordenes.Fecha) = ?\n" +
            "GROUP BY \n" +
            "    Prestadores.razon_social,\n" +
            "    Personas.apellido,\n" +
            "    Personas.nombre,\n" +
            "    Prestaciones.nombre_prestacion,\n" +
            "    YEAR(fecha),\n" +
            "    MONTH(fecha),\n" +
            "    Valores_prestaciones.Valor_prestacion,\n" +
            "    Federaciones.Nombre_federacion,\n" +
            "    Tipo_Liquidacion.Nombre_tipoLiquidacion\n" +
            "ORDER BY profesional, nombre_federacion";

    try (
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Clinica", "root", "Pchard_1971");
        PreparedStatement stmt = conn.prepareStatement(query)
    ) {
        stmt.setInt(1, anio); // primero el año
        stmt.setInt(2, mes);  // luego el mes

        try (ResultSet rs = stmt.executeQuery()) {
            listaDetalleLiquidacion.clear();

            while (rs.next()) {
                int idLiquidacion = rs.getInt("id_liquidacion");
                String profesional = rs.getString("Profesional");
                String federacion = rs.getString("Nombre_federacion");
                String prestadora = rs.getString("razon_social");
                String prestacion = rs.getString("nombre_prestacion");
                int cantidad = rs.getInt("Cantidad");
                double monto = rs.getDouble("Monto");

                listaDetalleLiquidacion.add(new GrillaLDetalle(
                    idLiquidacion, profesional, federacion, prestadora, prestacion, cantidad, monto));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}*/
private void cargarDetalleDesdeBD(String periodo, String tipo) {

    String mesStr = periodo.substring(0, 2);     // "05"
    String anioStr = periodo.substring(3, 7);    // "2019"
    int mes = Integer.parseInt(mesStr);
    int anio = Integer.parseInt(anioStr);
    String tipoLiqui = tipo;
    Double Montototal=0.0;

    String query = "SELECT 1000000 AS id_liquidacion,\n" +
            "    CONCAT(Personas.apellido, ', ', Personas.nombre) AS Profesional,\n" +
            "    Federaciones.Nombre_federacion,\n" +
            "    Prestadores.razon_social,\n" +
            "    Tipo_Liquidacion.Nombre_tipoLiquidacion,\n" +
            "    Prestaciones.nombre_prestacion,\n" +
            "    COUNT(PrestacionesXordenes.id_prestacion) AS Cantidad,\n" +
            "    Valores_prestaciones.Valor_prestacion * COUNT(PrestacionesXordenes.id_prestacion) AS Monto\n" +
            "FROM Ordenes\n" +
            "LEFT JOIN Prestadores ON Ordenes.id_prestador = Prestadores.id_prestador\n" +
            "LEFT JOIN Profesionales2 ON Ordenes.id_profesional = Profesionales2.id_profesional\n" +
            "LEFT JOIN Personas ON Personas.id_persona = Profesionales2.id_profesional\n" +
            "LEFT JOIN PrestacionesXordenes ON Ordenes.id_orden = PrestacionesXordenes.id_orden\n" +
            "LEFT JOIN Prestaciones ON PrestacionesXordenes.id_prestacion = Prestaciones.id_prestacion\n" +
            "LEFT JOIN Federaciones ON Ordenes.id_Federacion = Federaciones.id_Federacion\n" +
            "LEFT JOIN Tipo_Liquidacion ON Ordenes.Tipo_Liquidacion = Tipo_Liquidacion.id_TipoLiquidacion\n" +
            "LEFT JOIN PrestacionesXPrestador \n" +
            "    ON Ordenes.id_prestador = PrestacionesXPrestador.id_prestador\n" +
            "    AND PrestacionesXordenes.id_prestacion = PrestacionesXPrestador.id_prestacion\n" +
            "LEFT JOIN Valores_prestaciones \n" +
            "    ON Valores_prestaciones.id_categoria = Profesionales2.id_categoria\n" +
            "    AND PrestacionesXPrestador.id_prestacion = Valores_prestaciones.id_prestacion\n" +
            "    AND PrestacionesXPrestador.id_prestador = Valores_prestaciones.id_prestador\n" +
            "WHERE \n" +
            "    (Prestaciones.tipo_prestacion IN " + tipoLiqui +" OR Prestaciones.tipo_prestacion IS NULL)\n" +
            "    AND YEAR(Ordenes.Fecha) = ?\n" +
            "    AND MONTH(Ordenes.Fecha) = ?\n" +
            "GROUP BY \n" +
            "    Prestadores.razon_social,\n" +
            "    Personas.apellido,\n" +
            "    Personas.nombre,\n" +
            "    Prestaciones.nombre_prestacion,\n" +
            "    YEAR(fecha),\n" +
            "    MONTH(fecha),\n" +
            "    Valores_prestaciones.Valor_prestacion,\n" +
            "    Federaciones.Nombre_federacion,\n" +
            "    Tipo_Liquidacion.Nombre_tipoLiquidacion\n" +
            "ORDER BY Profesional, nombre_federacion";
            System.out.println(query);

    try (
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Clinica", "root", "Pchard_1971");
        PreparedStatement stmt = conn.prepareStatement(query)
    ) {
        stmt.setInt(1, anio); // primero el año
        stmt.setInt(2, mes);  // luego el mes
        

        try (ResultSet rs = stmt.executeQuery()) {
            listaDetalleLiquidacion.clear();  // Limpiamos la lista antes de cargar nuevos detalles
            
            
            while (rs.next()) {
                int idLiquidacion = rs.getInt("id_liquidacion");
                String profesional = rs.getString("Profesional");
                String federacion = rs.getString("Nombre_federacion");
                String prestadora = rs.getString("razon_social");
                String prestacion = rs.getString("nombre_prestacion");
                int cantidad = rs.getInt("Cantidad");
                double monto = rs.getDouble("Monto");

                Montototal+=monto;

                // Añadimos los detalles a la lista
                listaDetalleLiquidacion.add(new GrillaLDetalle(
                    idLiquidacion, profesional, federacion, prestadora, prestacion, cantidad, monto));
            }
            txtMontoPracticas.setText(String.format("%.2f", Montototal));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

@FXML public void onNueva(ActionEvent event) {
        txtPeriodo.setText("");
        txtnroLiquidacion.setText("");
        txtMontoPracticas.setText("");
        GrillaLDetalle.getItems().clear();
        txtPeriodo.requestFocus();
    }
@FXML public void onGrabar(ActionEvent event) {
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Clinica", "root", "Pchard_1971")) {
        conn.setAutoCommit(false);

        String peri = txtPeriodo.getText();

        // Verificar si ya existe el periodo
        PreparedStatement check = conn.prepareStatement("SELECT id_liquidacion, estado FROM Liquidaciones WHERE periodo = ?");
        check.setString(1, peri);
        ResultSet rs = check.executeQuery();

        if (rs.next()) {
            boolean estado = rs.getBoolean("estado");
            if (estado) {
                Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Período ya liquidado y cerrado.",
                        "Ya existe una liquidación activa para este periodo.");
                return;
            }
        }

        // Obtener el nuevo ID
        Statement st = conn.createStatement();
        ResultSet rsMax = st.executeQuery("SELECT MAX(id_liquidacion) FROM Liquidaciones");
        int newId = 1;
        if (rsMax.next()) {
            newId = rsMax.getInt(1) + 1;
        }

        // Ejecutar alta (guarda con monto = 0.0)
        int Tipo=0;
        System.out.println(peri);
        if (rbtnNoquirurgicos.isSelected()){
            Tipo=2;
        }else {
            Tipo=3;
        }
        boolean exito = ClaseLiquidaciones.alta(conn, newId, peri, 0.0, listaDetalleLiquidacion,Tipo);
        System.out.println("salio de alta de claseliquidaciones");
        if (!exito) {
            conn.rollback();
            Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo insertar la liquidación.");
            return;
        }

        // Calcular monto total desde la grilla
        double montoTotal = calcularMonto(listaDetalleLiquidacion);
        //System.out.println("Monto calculado: " + montoTotal);

        // Actualizar el campo monto
        String sql = "UPDATE Liquidaciones SET monto = ? WHERE id_liquidacion = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, montoTotal);
            ps.setInt(2, newId);
            int filas = ps.executeUpdate();
            System.out.println("Filas actualizadas: " + filas);
        }

        // Confirmar todo recién acá
        conn.commit();

        // Mostrar en pantalla
        txtnroLiquidacion.setText(String.valueOf(newId));
        txtMontoPracticas.setText(String.format(Locale.US, "%.2f", montoTotal));
        Utilidades.mostrarAlerta(Alert.AlertType.INFORMATION, "Liquidación realizada", "Liquidación realizada con éxito");
        cargarDatosDesdeBD();

    } catch (SQLException e) {
        e.printStackTrace();
        Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Liquidación con error", "Error al grabar la liquidación.");
    }
}
public Double calcularMonto(List<GrillaLDetalle> listaDetalle) {
        double total = 0.0;
        for (GrillaLDetalle det : listaDetalle) {
            total += det.getMonto();
        }
        return total;
        //txtMontoPracticas.setText(String.format(Locale.US, "%.2f", total));
    }
@FXML  public void onSalir(ActionEvent event) {
        // código cuando presionen "Salir"
    }
@FXML public void onLiquidar(ActionEvent event) {
        if (txtPeriodo.getText() != null && !txtPeriodo.getText().isEmpty() && txtPeriodo.getText().matches("\\d{2}/\\d{4}")) {
           //cargarDatosDesdeBD();
           
           String periodo = txtPeriodo.getText(); 
           //String mes = texto.substring(0, 2);    // Letras 0 y 1 → "MM"
            //String anio = texto.substring(3, 7);   // Letras 3 a 6 → "YYYY"
            String condicion = "";
            if (rbtnNoquirurgicos.isSelected()) {
                condicion = "(1,2)";
                cargarDetalleDesdeBD(periodo,condicion);
            } else if (rbtnquirurgico.isSelected()) {
                condicion = "(3)";
                cargarDetalleDesdeBD(periodo,condicion);
            }
        } else {
                Utilidades.mostrarAlerta(Alert.AlertType.ERROR,  "Error", "Debe escribir el PERIODO en formato MM/YYYY");
        }
    
}

@FXML public void onEliminar(ActionEvent event){
   eliminarLiquidacion();
}

@FXML private void eliminarLiquidacion() {
    //int IdLiq = 0;
    String periodo = "";  // Declarar la variable periodo aquí

    // Obtenemos el número de liquidación del TextField
    GrillaLiquidaciones.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        txtnroLiquidacion.setText(String.valueOf(newValue.getIdLiquidacion()));
        txtPeriodo.setText(newValue.getPeriodo());  
    });
    periodo=txtPeriodo.getText();
    int IdLiq= Integer.parseInt(txtnroLiquidacion.getText());
    // Verificamos si el periodo está vacío
    if (periodo.isEmpty()) {
        Utilidades.mostrarAlerta(Alert.AlertType.WARNING, "Periodo vacío", "Por favor, ingrese un periodo o número de liquidación.");
        return;
    }

    // Confirmación de eliminación
    Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    confirmacion.setTitle("Confirmar Eliminación");
    confirmacion.setHeaderText("¿Está seguro de eliminar la liquidación nro " + IdLiq + "?");
    confirmacion.setContentText("Esta acción no puede deshacerse.");
    confirmacion.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
            // Verificamos el estado de la liquidación
            if (ClaseLiquidaciones.verificarEstado(IdLiq)) {
                                // Si el estado es 0, proceder con la eliminación
                // Eliminar los detalles
                if (ClaseDetalleLiquidacion.eliminarDetallesLiquidacion(IdLiq)) {
                    // Eliminar la liquidación
                    if (ClaseLiquidaciones.eliminarLiquidacion(IdLiq)) {
                        Utilidades.mostrarAlerta(Alert.AlertType.INFORMATION, "Eliminación exitosa", "La liquidación ha sido eliminada correctamente.");
                        // Aquí puedes actualizar las grillas
                        cargarDatosDesdeBD();
                    } else {
                        Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al eliminar la liquidación.");
                    }
                } else {
                    Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al eliminar los detalles de la liquidación.");
                }
                
            } else {
                // Si el estado es 1, no podemos eliminar
                Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "No se puede eliminar", "La liquidación ya está consolidada y no se puede eliminar.");

            }
        }
    });
}

}


    

