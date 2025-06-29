package src;

import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import src.Utilidades;
import src.ClasePrestadores;

public class PrestadoresController {
    @FXML private TextField txtid;
    @FXML private TextField txtrazon_social;
    @FXML private TextField txtcuit;
    @FXML private TextField txtdireccion;
    @FXML private TextField txttelefono;
    @FXML private TextField txtfax;
    @FXML private TextField txtemail;
    
    //@FXML private TextField LiquidacionConsultasPracticas;
    //@FXML private TextField LiquidacionQuirurgicas;
    @FXML private CheckBox chkGravado;
   // @FXML private CheckBox chkNoGravado;
    @FXML private CheckBox chkRural;
    @FXML private CheckBox chkMonotributo;
    @FXML private CheckBox chkFamilia;
    @FXML private CheckBox chkEnActividad;
    @FXML private CheckBox chkClasic;
    //@FXML private CheckBox chkTratamientoEspecial;/
    // Definir las columnas de la tabla
    @FXML private TableColumn<GrillaPrestadores,Integer> col_id;
    @FXML private TableColumn<GrillaPrestadores, String> col_prestador;
    @FXML private TableColumn<GrillaPrestadores, String> col_cuit;
    @FXML private TableColumn<GrillaPrestadores, String> col_direccion;
    @FXML private TableColumn<GrillaPrestadores,String> col_telefono;
    @FXML private TableColumn<GrillaPrestadores, String> col_fax;
    @FXML private TableColumn<GrillaPrestadores, String> col_email;
    @FXML private TableColumn<GrillaPrestadores, Boolean> col_gravado;
    @FXML private TableColumn<GrillaPrestadores, Boolean> col_rural;
    @FXML private TableColumn<GrillaPrestadores, Boolean> col_monotributo;
    @FXML private TableColumn<GrillaPrestadores, Boolean> col_familia;
    @FXML private  TableColumn<GrillaPrestadores, Boolean> col_enactividad;
    @FXML private TableColumn<GrillaPrestadores, Boolean> col_clasic;

    @FXML private RadioButton rbtnSeparadas;
    @FXML private RadioButton rbtnJuntas;
    @FXML private RadioButton rbtnSermax;
    @FXML private RadioButton rbtnServiciosAsistenciales;
    @FXML private RadioButton rbtnFacturacionSermex;
    @FXML private RadioButton rbtnOspat;
    @FXML private RadioButton rbtnSi;
    @FXML private RadioButton rbtnNo;

    @FXML private Button btnnuevo;
    @FXML private Button btnguardar;
    @FXML private Button btnbuscar;

    @FXML private TableView<GrillaPrestadores> grillaPrestadores;

    private enum ModoOperacion { NINGUNO, NUEVO, MODIFICAR }
    private ModoOperacion modo = ModoOperacion.NINGUNO;

    private final ObservableList<GrillaPrestadores> listaPrestadores = FXCollections.observableArrayList();

    private Connection obtenerConexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/clinica";
        String user = "root";
        String password = "Pchard_1971";
        return DriverManager.getConnection(url, user, password);
    }

@FXML  public void initialize() {
        // Configurar columnas si usás TableView con columnas (opcional)
        configureTableColumns();

        grillaPrestadores.setItems(listaPrestadores);
        
        try {
            cargarPrestadoresDesdeBD();
        } catch (Exception e) {
            System.out.println("Error al llamar cargarPrestadoresDesdeBD: " + e.getMessage());
            e.printStackTrace();
        }
        //cargarPrestadoresDesdeBD();
        //System.out.println("termino de cargar PrestadoresdesdeBD");
        grillaPrestadores.setRowFactory(tv -> {
            TableRow<GrillaPrestadores> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    GrillaPrestadores prestadorSeleccionado = row.getItem();
                    ClasePrestadores prestador = buscarPrestadorPorId(prestadorSeleccionado.getId_prestador());
                    cargarDatosEnFormulario(prestadorSeleccionado);
                    cargarChkEnFormulario(prestador);
                    habilitarCampos(true);
                }
            });
            return row;
        });
}
private void cargarChkEnFormulario(ClasePrestadores prestador) {

    chkGravado.setSelected(prestador.isGravado());
    chkRural.setSelected(prestador.isRural());
    chkMonotributo.setSelected(prestador.isMonotributo());
    chkFamilia.setSelected(prestador.isFamilia());
    chkEnActividad.setSelected(prestador.isEnActividad());
    chkClasic.setSelected(prestador.isClasicYAcord());
}

private void cargarPrestadoresDesdeBD() {
    listaPrestadores.clear();
    String sql = "SELECT * FROM prestadores";

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971");
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

        System.out.println("Consulta ejecutada, verificando resultados...");

        if (!rs.isBeforeFirst()) {
            System.out.println("La tabla 'prestadores' está vacía o no se obtuvieron resultados.");
        } else{
            System.out.println("salio por el else, es decir la tabla tiene datos");
        }

        while (rs.next()) {
            //System.out.println("ingresar a cargarPrestadoresdesdeBD");
            //System.out.println("Cargando prestadores...");
              
            //System.out.println("REGISTRO:" + rs.getInt("id_prestador") + rs.getString("razon_social"));
            try{
            GrillaPrestadores prestador = new GrillaPrestadores(
                rs.getInt("id_prestador"),
                rs.getString("razon_social"),
                rs.getString("cuit"),
                rs.getString("direccion"),
                rs.getString("telefono"),
                rs.getString("fax"),
                rs.getString("email"),
                rs.getBoolean("Gravado"),
                rs.getBoolean("Rural"),
                rs.getBoolean("Monotributo"),
                rs.getBoolean("Familia"),
                rs.getBoolean("EnActividad"),
                rs.getBoolean("ClasicYAcord") // o "Clasic" si ese es el nombre real
            );

            listaPrestadores.add(prestador);
        } catch (SQLException e) {
            System.err.println("Error al procesar un registro: " + e.getMessage());
        }
        }

        grillaPrestadores.setItems(listaPrestadores);

    } catch (SQLException e) {
        mostrarAlerta("Error al cargar prestadores: " + e.getMessage(), Alert.AlertType.ERROR);
    }
}

@FXML private void onNuevo() {
        modo = ModoOperacion.NUEVO;
        limpiarCampos();
        habilitarCampos(true);
        btnguardar.setDisable(false);
        btnbuscar.setDisable(true);
        txtrazon_social.requestFocus();
        System.out.println("modo en onnuevo=" + modo);
}

@FXML private void onGuardar() {
    if (modo == ModoOperacion.NUEVO) {
    try {
        ClasePrestadores prestador = new ClasePrestadores(
            0, //Integer.parseInt(txtid.getText()),
            txtrazon_social.getText().trim(),
            txtcuit.getText().trim(),
            txtdireccion.getText().trim(),
            txttelefono.getText().trim(),
            txtfax.getText().trim(),
            txtemail.getText().trim(),
            chkGravado.isSelected(),
            chkRural.isSelected(),
            chkMonotributo.isSelected(),
            chkFamilia.isSelected(),
            chkEnActividad.isSelected(),
            chkClasic.isSelected()
        );
        prestador.guardarPrestadorEnBD();
        txtid.setText(String.valueOf(prestador.getId_prestador()));
        Utilidades.mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Prestador registrado correctamente.");
        limpiarCampos();
        cargarPrestadoresDesdeBD(); // método que recarga la tabla
        btnguardar.setDisable(false);

    } catch (NumberFormatException e) {
        Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "El ID debe ser un número.");
    } catch (SQLException e) {
        e.printStackTrace();
        Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al guardar en la base de datos.");
    }
     }
 }

    /*private void insertarPrestador() {
        String sql = "INSERT INTO prestadores ( razon_social, cuit, direccion, telefono, " +
        "fax, email, Gravado, Rural, Monotributo, Familia, EnActividad, ClasicYAcord)" +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        
        
        try (Connection conn = obtenerConexion();

             PreparedStatement stmt = conn.prepareStatement(sql)) {
                //stmt.setString(1, txtid.getText());
                stmt.setString(1, txtrazon_social.getText());
                stmt.setString(2, txtcuit.getText());
                stmt.setString(3, txtdireccion.getText());
                stmt.setString(4, txttelefono.getText());
                stmt.setString(5, txtfax.getText());
                stmt.setString(6, txtemail.getText());
                stmt.setBoolean(7, chkGravado.isSelected());
                stmt.setBoolean(8, chkRural.isSelected());
                stmt.setBoolean(9, chkMonotributo.isSelected());
                stmt.setBoolean(10, chkFamilia.isSelected());
                stmt.setBoolean(11, chkEnActividad.isSelected());
                stmt.setBoolean(12, chkClasic.isSelected());
 
                System.out.println("sql insert a ejecutar '"+ sql +"'");
            stmt.executeUpdate();
            mostrarAlerta("Prestador guardado correctamente.", Alert.AlertType.INFORMATION);

        } catch (SQLException e) {
            mostrarAlerta("Error al guardar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }*/

    private void limpiarCampos() {
        txtid.clear();
        txtrazon_social.clear();
        txtcuit.clear();
        txtdireccion.clear();
        txttelefono.clear();
        txtemail.clear();
        txtfax.clear();

        chkGravado.setSelected(false);
        chkRural.setSelected(false);
        chkMonotributo.setSelected(false);
        chkFamilia.setSelected(false);
        chkEnActividad.setSelected(false);
        chkClasic.setSelected(false);

    }

    private void habilitarCampos(boolean habilitar) {
        txtrazon_social.setDisable(!habilitar);
        txtcuit.setDisable(!habilitar);
        txtdireccion.setDisable(!habilitar);
        txttelefono.setDisable(!habilitar);
        txtemail.setDisable(!habilitar);
        txtfax.setDisable(!habilitar);

        chkGravado.setDisable(!habilitar);
        chkRural.setDisable(!habilitar);
        chkMonotributo.setDisable(!habilitar);
        chkFamilia.setDisable(!habilitar);
        chkEnActividad.setDisable(!habilitar);
        chkClasic.setDisable(!habilitar);
        //TratamientoEspecial.setDisable(!habilitar);
        btnguardar.setDisable(!habilitar);
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    private String getCondicionFiscalSeleccionada() {
        List<String> condiciones = new ArrayList<>();

        if (chkGravado.isSelected()) condiciones.add("Gravado");
        //if (chkNoGravado.isSelected()) condiciones.add("No Gravado");
        if (chkRural.isSelected()) condiciones.add("Rural");
        if (chkMonotributo.isSelected()) condiciones.add("Monotributo");
        if (chkFamilia.isSelected()) condiciones.add("Familia");

        return String.join(", ", condiciones); // Ej: "Gravado, Rural"
    }

@FXML public void onCargarPantallaPrestadores(ActionEvent event) {
    System.out.println("Se cargará la pantalla de prestaciones.");
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/prestadores.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Gestión de Prestadores");
        stage.centerOnScreen();
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private void configureTableColumns() {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id_prestador"));
        col_prestador.setCellValueFactory(new PropertyValueFactory<>("razon_social"));
        col_cuit.setCellValueFactory(new PropertyValueFactory<>("cuit"));
        col_direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        col_telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        col_fax.setCellValueFactory(new PropertyValueFactory<>("fax"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));

        col_gravado.setCellValueFactory(new PropertyValueFactory<>("gravado"));
        col_rural.setCellValueFactory(new PropertyValueFactory<>("rural"));
        col_monotributo.setCellValueFactory(new PropertyValueFactory<>("monotributo"));
        col_familia.setCellValueFactory(new PropertyValueFactory<>("familia"));
        col_enactividad.setCellValueFactory(new PropertyValueFactory<>("enactividad"));
        col_clasic.setCellValueFactory(new PropertyValueFactory<>("clasic"));
}

private void cargarDatosEnFormulario(GrillaPrestadores prestador) {
    txtid.setText(String.valueOf(prestador.getId_prestador()));
    txtrazon_social.setText(prestador.getRazon_social());
    txtcuit.setText(prestador.getCuit());
    txtdireccion.setText(prestador.getDireccion());
    txttelefono.setText(prestador.getTelefono());
    txtfax.setText(prestador.getFax());
    txtemail.setText(prestador.getEmail());

    chkGravado.setSelected(prestador.isGravado());
    chkRural.setSelected(prestador.isRural());
    chkMonotributo.setSelected(prestador.isMonotributo());
    chkFamilia.setSelected(prestador.isFamilia());
    chkEnActividad.setSelected(prestador.isEnactividad());
    chkClasic.setSelected(prestador.isClasic());

    //rbtnSeparadas
    //rbtnJuntas.setSelected(prestador.)
    //rbtnServiciosAsistenciales.set Selected(prestador.)
}

@FXML private void onEliminar() {
    try {
        int id = Integer.parseInt(txtid.getText().trim());

        // Confirmación antes de eliminar
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Estás seguro que deseas eliminar este prestador?");
        confirmacion.setContentText("ID del prestador: " + id);

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            ClasePrestadores prestador = new ClasePrestadores();
            prestador.setId_prestador(id);
            prestador.eliminarPrestadorDeBD();

            Utilidades.mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Prestador eliminado correctamente.");
            limpiarCampos();
            cargarPrestadoresDesdeBD();
        } else {
            Utilidades.mostrarAlerta(Alert.AlertType.INFORMATION, "Cancelado", "La operación de eliminación fue cancelada.");
        }

    } catch (NumberFormatException e) {
        Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "El ID debe ser un número.");
    } catch (SQLException e) {
        Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error en la base de datos", "No se pudo eliminar el prestador.");
        e.printStackTrace();
    }
}

@FXML private void onBuscar(ActionEvent event) {
    // Lógica de búsqueda acá
    
    buscarPrestadorPorId(5);
    System.out.println("Método onBuscar() en preparacion");
}

@FXML private void onModificar(ActionEvent event) {
    // Lógica de búsqueda acá
    System.out.println("Método onModificar() en preparacion");
}

private ClasePrestadores buscarPrestadorPorId(int id) {

    ClasePrestadores prestador = null;
    String sql = "SELECT * FROM prestadores WHERE id_prestador = ?";

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971");
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                prestador = new ClasePrestadores(
                    rs.getInt("id_prestador"),
                    rs.getString("razon_social"),
                    rs.getString("cuit"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("fax"),
                    rs.getString("email"),
                    rs.getBoolean("Gravado"),
                    rs.getBoolean("Rural"),
                    rs.getBoolean("Monotributo"),
                    rs.getBoolean("Familia"),
                    rs.getBoolean("EnActividad"),
                    rs.getBoolean("ClasicYAcord")
                );
            }
        }
    } catch (SQLException e) {
        mostrarAlerta("Error al buscar prestador: " + e.getMessage(), Alert.AlertType.ERROR);
    }

    return prestador;
}

} //llave de clase


