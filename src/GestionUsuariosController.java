package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.event.ActionEvent;






import src.Utilidades.ComboItem;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import src.ClaseUsuario;
import javafx.scene.control.TableRow;


public class GestionUsuariosController {

    private String mensajeError = "";
 
    //private void onCancelarClicked() {
    //    formularioUsuario.setVisible(false);
    //    formularioUsuario.setManaged(false);
    //}

  

    private boolean modoNuevo = false;

    @FXML private TextField txtusuario;
    @FXML private TextField txtapellido;
    @FXML private TextField txtnombre;
    @FXML private TextField txttelefono;
    @FXML private TextField txtemail;
    @FXML private TextField txtconfirmarContrasena;
    @FXML private TextField txtcontrasena;
    @FXML private TextField txtBusqueda;
    @FXML private Button btnnuevo;
    @FXML private Button btngrabar;
     @FXML private Button btnEliminar;
     @FXML private Button btnModificar;
     @FXML private Button btnBuscar;
    @FXML private  RadioButton rbtnPorUsuario;
    @FXML private  RadioButton rbtnPorRol;

    
    @FXML private TableView<GrillaUsuario> grillaUsuario;
    private ToggleGroup grupoBusqueda = new ToggleGroup();
    @FXML private TableColumn<GrillaUsuario, String> col_usuario;
    @FXML private TableColumn<GrillaUsuario, String> col_apellido;
    @FXML private TableColumn<GrillaUsuario, String> col_nombre;
    @FXML private TableColumn<GrillaUsuario, String> col_rol;
    @FXML private TableColumn<GrillaUsuario, String> col_email;
    @FXML private TableColumn<GrillaUsuario, String> col_Telefono;
    @FXML private ComboBox<ComboItem> cmbroles;
    

    private int idRolSeleccionado= -1;
    private Map<String, Integer> mapaRoles = new HashMap<>();

    private Connection conexion;  // Declarar la conexión como variable de instancia

    private ObservableList<GrillaUsuario> listaUsuarios = FXCollections.observableArrayList();
    
private enum ModoOperacion {
        NINGUNO, NUEVO, MODIFICAR, ELIMINAR
    }
    
private ModoOperacion modoActual = ModoOperacion.NINGUNO;

@FXML public void onNuevoClicked(ActionEvent event){
        
        modoActual = ModoOperacion.NUEVO;
        modoNuevo = true;
        limpiarCampos();
        habilitarCampos(true);
        btngrabar.setDisable(true); // al principio está deshabilitado porque el txtusuario está vacío
        txtusuario.requestFocus();
        grillaUsuario.getSelectionModel().clearSelection();
        btngrabar.setDisable(false); // Habilita el botón grabar
    }

    //metodo llamado al presionar el boton modificar
    //carga los datos del usuario y llama al metodo usuario.modificarUsuario(conexion)
@FXML private void onModificarClicked(ActionEvent event) {
    try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971")) {
        
        ComboItem rolSeleccionado = cmbroles.getValue();
        if (rolSeleccionado == null) {
            Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Debe seleccionar un rol.");
            return;
        }

        // Crear y poblar el objeto
        ClaseUsuario usuario = new ClaseUsuario();
        usuario.setId_usuario(Integer.parseInt(txtusuario.getText().trim()));
        usuario.setNombre(txtnombre.getText().trim());
        usuario.setApellido(txtapellido.getText().trim());
        usuario.setTel(txttelefono.getText().trim());
        usuario.setEmail(txtemail.getText().trim());
        usuario.setContrasena(txtcontrasena.getText().trim());
        usuario.setCod_tusuario(rolSeleccionado.getId());

        if (usuario.modificarUsuario(conexion)) {
            cargarDatos(); // refresca la grilla
            Utilidades.mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario modificado correctamente.");
        } else {
            Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo modificar: " + usuario.getMensajeError());
        }

    } catch (Exception e) {
        Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error inesperado: " + e.getMessage());
        e.printStackTrace();
    }
}
 /*
  @FXML private void onBtnModificar() {
    try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971")) {
        ClaseUsuario usuario = new ClaseUsuario();

        // Seteamos los valores desde la UI al objeto
        usuario.setId_usuario(Integer.parseInt(txtusuario.getText().trim()));
        usuario.setNombre(txtnombre.getText().trim());
        usuario.setApellido(txtapellido.getText().trim());
        usuario.setTel(txttelefono.getText().trim());
        usuario.setEmail(txtemail.getText().trim());
        usuario.setContrasena(txtcontrasena.getText().trim());

        ComboItem rolSeleccionado = cmbroles.getValue();
        if (rolSeleccionado != null) {
            usuario.setCod_tusuario(rolSeleccionado.getId());
        } else {
            Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Debe seleccionar un rol.");
            return;
        }

        // Llamar al método del modelo
        if (usuario.modificar(conexion)) {
            cargarDatos(); // recargar grilla
            Utilidades.mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario modificado correctamente.");
        } else {
            Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo modificar: " + usuario.getMensajeError());
        }

    } catch (SQLException e) {
        e.printStackTrace();
        Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error de conexión", e.getMessage());
    }
}*/



@FXML public void onGrabarClicked(ActionEvent event) {
    try {
        if (conexion == null || conexion.isClosed()) {
            mostrarAlerta("Error", "No hay conexión con la base de datos.");
            return;
        }

        ComboItem rolSeleccionado = cmbroles.getValue();
        if (rolSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un rol.");
            return;
        }

        // Obtener el nuevo ID antes de instanciar el usuario
        String sqlId = "SELECT COALESCE(MAX(id_persona), 0) + 1 AS nuevoId FROM personas";
        int nuevoId = 1;

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sqlId)) {
            if (rs.next()) {
                nuevoId = rs.getInt("nuevoId"); // Usa alias correcto
            }
        }

        // Asignar el nuevo ID al campo txtusuario en el hilo correcto de JavaFX
        final int idGenerado = nuevoId;
        Platform.runLater(() -> txtusuario.setText(String.valueOf(idGenerado)));
System.out.println("antes de crear el objeto usuarios en gestionusuarioscotroller");
System.out.println(txtapellido.getText().trim());
        // Instanciar ClaseUsuario con datos del formulario
        ClaseUsuario usuario = new ClaseUsuario(
            idGenerado, 
            txtapellido.getText().trim(),
            txtnombre.getText().trim(),
            txtemail.getText().trim(),
            txttelefono.getText().trim(),
            rolSeleccionado.getId(),
            txtcontrasena.getText().trim()
        );
System.out.println("apellido;  "+txtapellido.getText().trim());
        boolean exito;
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971")) {
            conn.setAutoCommit(false); // Iniciamos la transacción

            if (modoActual == ModoOperacion.NUEVO) {
                exito = usuario.alta(conn);
            } else {
                exito = usuario.modificar(conn);
            }

            if (exito) {
                conn.commit(); // Confirmamos la transacción
                mostrarAlerta("Éxito", modoActual == ModoOperacion.NUEVO ? "Usuario creado correctamente." : "Usuario modificado correctamente.");
                limpiarCampos();
                cargarDatos();
            } else {
                conn.rollback(); // Revertimos la operación si falla
                mostrarAlerta("Error", modoActual == ModoOperacion.NUEVO ? "No se pudo crear el usuario." : "No se pudo modificar el usuario.");
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error en la base de datos: " + e.getMessage());
            e.printStackTrace();
        }

    } catch (IllegalArgumentException e) {
        mostrarAlerta("Error", e.getMessage()); // Para errores de validación
    } catch (Exception e) {
        mostrarAlerta("Error", "Ocurrió un error inesperado.");
        e.printStackTrace();
    }
}




/*public boolean altaUsuario() {
    mensajeError = "";  
    try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971");
            //String sql = "INSERT INTO usuarios (usuario, nombre, apellido, tel, email, contrasena, id_rol) VALUES (?, ?, ?, ?, ?, ?, ?)";
            String sql = "INSERT INTO usuarios (usuario, nombre, apellido, tel, email, contrasena, id_rol) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, txtusuario.getText());
            stmt.setString(2, txtnombre.getText());
            stmt.setString(3, txtapellido.getText());
            stmt.setString(4, txttelefono.getText());
            stmt.setString(5, txtemail.getText());
            stmt.setString(6, txtcontrasena.getText());
            stmt.setInt(7, 2); // Por ahora un rol fijo, podrías cambiarlo
            ComboItem itemSeleccionado = cmbroles.getValue();
            if (itemSeleccionado == null) {
                mensajeError = "Debe seleccionar un rol.";
                return false;
            }
            stmt.executeUpdate();
            return true;
    } catch (SQLException e) {
            mensajeError = e.getMessage();  // Guardamos el error
            return false;
    }
}*/
@FXML private void onbtnGrabar(ActionEvent event) {
    String sqlId = "SELECT COALESCE(MAX(id_persona), 0) + 1 AS nuevoId FROM personas";

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971")) {
        conn.setAutoCommit(false); // Iniciamos la transacción

        // Obtener el nuevo ID antes de crear la instancia
        int nuevoId = 1;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlId)) {
            if (rs.next()) {
                nuevoId = rs.getInt("nuevoId"); // Usa el alias correcto
            }
        }
        
        // Crear instancia de ClaseUsuario con los atributos de la clase padre
        ClaseUsuario usuario = new ClaseUsuario(
            nuevoId,  // ID calculado correctamente
            txtapellido.getText().trim(),
            txtnombre.getText().trim(),
            txtemail.getText().trim(),
            txttelefono.getText().trim(),
            cmbroles.getValue().getId(),
            txtcontrasena.getText().trim()
        );
        System.out.println(txtapellido.getText());
        // Llamar al método alta() de ClaseUsuario
        boolean exito = usuario.alta(conn);

        if (exito) {
            mostrarAlerta("Éxito", "Usuario registrado correctamente.");
            limpiarCampos(); 
            conn.commit(); 
        } else {
            mostrarAlerta("Error", "No se pudo registrar el usuario.");
            conn.rollback(); 
        }
    } catch (SQLException e) {
        mostrarAlerta("Error", "Error en la base de datos: " + e.getMessage());
        e.printStackTrace();
    }
}
    
private void mostrarAlerta(String titulo, String mensaje) {
        // Aquí puedes mostrar una alerta de tipo 'Alert' para mostrar mensajes al usuario
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
@FXML  public void initialize() { 
        rbtnPorUsuario.setToggleGroup(grupoBusqueda);
        rbtnPorRol.setToggleGroup(grupoBusqueda);
        try {
            
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al conectar con la base de datos.");
        }

        grillaUsuario.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends GrillaUsuario> obs, GrillaUsuario oldSelection, GrillaUsuario newSelection) -> {
                if (newSelection != null) {
                    txtusuario.setText(newSelection.getIdUsuario());
                    txtapellido.setText(newSelection.getApellido());
                    txtnombre.setText(newSelection.getNombre());

                    for (ComboItem item : cmbroles.getItems()) {
                        if (item.toString().equals(newSelection.getRol())) {
                            cmbroles.getSelectionModel().select(item);
                            break;
                        }
                    }                  
                    txtemail.setText(newSelection.getEmail());
                    txttelefono.setText(newSelection.getTelefono());
                }
            }
        );

        btngrabar.setDisable(true);

        // Configurar columnas de la tabla
        configureTableColumns();

        // Cargar datos
        cargarDatos();
        // cargar en el combo cmbroles los roles
        Utilidades.cargarCombo(cmbroles, "SELECT id_rol, descripcion FROM roles", "descripcion", "id_rol");

        grillaUsuario.setRowFactory(tv -> {
            TableRow<GrillaUsuario> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    GrillaUsuario usuario = row.getItem();
                    //.setVisible(true);
                    //formularioUsuario.setManaged(true);
                    cargarDatosFormulario(usuario);
                    onUsuarioSeleccionado();
                }
            });
            return row;
        });   
        System.out.println("va a entrar al procedimiento de habilitar(fañse)");
        habilitarCampos(false);
        // Listener para habilitar o deshabilitar el botón según el contenido del txtusuario
        txtusuario.textProperty().addListener((observable, oldValue, newValue) -> {
            if (modoNuevo) {
                btngrabar.setDisable(newValue.trim().isEmpty());
            }
        });
    }

@FXML private void onUsuarioSeleccionado() {
    GrillaUsuario usuario = grillaUsuario.getSelectionModel().getSelectedItem();
    if (usuario != null) {
        // Cambiar al modo de modificación
        modoActual = ModoOperacion.MODIFICAR;
        modoNuevo = false;
      
        txtusuario.setText(usuario.getIdUsuario());
        txtnombre.setText(usuario.getNombre());
        txtapellido.setText(usuario.getApellido());
        txttelefono.setText(usuario.getTelefono());
        txtemail.setText(usuario.getEmail());
        //txtcontrasena.setText(usuario.getContrasena());
        //txtconfirmarContrasena.setText(usuario.getContrasena());

        // Habilitar los campos
        habilitarCampos(true);
        btngrabar.setDisable(false); 
        grillaUsuario.getSelectionModel().clearSelection();
        txtapellido.requestFocus();
        String rolDelUsuario = usuario.getRol(); // Es un string

        // Seleccionar el rol correspondiente en el ComboBox
        //String rolDelUsuario = usuario.getRol() //String.valueOf(usuario.getCod_tusuario());   //getRol(); //   
        for (ComboItem item : cmbroles.getItems()) {
            if (item.getDescripcion().equals(rolDelUsuario)) {
                cmbroles.setValue(item); // Seleccionar el ítem
                break;
            }
        }
    }
}

private void habilitarCampos(boolean habilitar) {
    txtusuario.setDisable(habilitar);
    txtnombre.setDisable(!habilitar);
    txtapellido.setDisable(!habilitar);
    txttelefono.setDisable(!habilitar);
    txtemail.setDisable(!habilitar);
    txtcontrasena.setDisable(!habilitar);
    txtconfirmarContrasena.setDisable(!habilitar);
    btngrabar.setDisable(!habilitar);
    System.out.println("habilitarCampos: " + habilitar);
}

private void configureTableColumns() {
        //col_id.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        //col_usuario.setCellValueFactory(new PropertyValueFactory<>("id_usuario"));
        col_usuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdUsuario()));
        col_apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_rol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_Telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
}

public void cargarDatos() {
        listaUsuarios.clear();
        //try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971");
        
            try(Statement statement = conexion.createStatement()) {
            // Consulta SQL
            //String query = "SELECT u.usuario, u.apellido,u.nombre, r.descripcion, " +
            //        "u.email, u.tel FROM usuarios u JOIN roles r ON u.cod_TUSUARIO = r.id_rol";
            String query = "SELECT u.id_usuario, p.apellido, p.nombre,  r.descripcion, p.email, p.tel " +
               "FROM usuarios2 u " +
               "JOIN personas p ON u.id_usuario = p.id_persona " +
               "JOIN roles r ON u.cod_tusuario = r.id_rol";
            ResultSet resultSet = statement.executeQuery(query);
            // Rellenar la lista de usuarios
            while (resultSet.next()) {
                listaUsuarios.add(new GrillaUsuario(
                       // resultSet.getInt("idusuario"),
                        //resultSet.getString("id_persona"),
                        String.valueOf(resultSet.getInt("id_usuario")),  // convierte int a String,
                        resultSet.getString("apellido"),
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion"), 
                        resultSet.getString("email"),
                        resultSet.getString("tel")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Configurar datos en la tabla
        grillaUsuario.setItems(listaUsuarios);
}

    private void cargarDatosFormulario(GrillaUsuario usuario) {
        txtapellido.setText(usuario.getApellido());
        txtnombre.setText(usuario.getNombre()); 
        for (ComboItem item : cmbroles.getItems()) {
            if (item.toString().equals(usuario.getRol())) {
                cmbroles.getSelectionModel().select(item);
                break;
            }
        }
        txtemail.setText(usuario.getEmail());
        txttelefono.setText(usuario.getTelefono());
        
    }
    
    private void limpiarCampos(){
        txtusuario.setText("");
        txtapellido.setText("");
        txtnombre.setText("");
        txttelefono.setText("");
        txtemail.setText("");
        cmbroles.setValue(null);
    }

    private boolean usuarioExiste(String usuario) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE usuario = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("El usuario "+usuario+" ya existe");
                int count = rs.getInt(1);
                return count > 0; // true si ya existe
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("El usuario "+usuario+" NO existe");
        }
            return false; // Por defecto, no existe (o error)
    }

@FXML public void onEliminarClicked(ActionEvent event) {
//@FXML private void onEliminarClicked() {
    if (txtusuario.getText() == null || txtusuario.getText().trim().isEmpty()) {
        Utilidades.mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Debe seleccionar un usuario para eliminar.");
        return;
    }

    int idUsuario = Integer.parseInt(txtusuario.getText().trim());

    // Confirmar eliminación
    Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    confirmacion.setTitle("Confirmar eliminación");
    confirmacion.setHeaderText("¿Está seguro que desea eliminar este usuario?");
    confirmacion.setContentText("Esta acción no se puede deshacer.");

    Optional<ButtonType> resultado = confirmacion.showAndWait();

    if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971")) {
            ClaseUsuario usuario = new ClaseUsuario();
            usuario.setId_usuario(idUsuario); // Este es clave

            boolean exito = usuario.eliminar(conexion);

            if (exito) {
                Utilidades.mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario eliminado correctamente.");
                cargarDatos(); // refrescar grilla
                limpiarCampos();
            } else {
                Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error", usuario.getMensajeError());
            }
        } catch (Exception e) {
            Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al eliminar: " + e.getMessage());
        }
    }
}

/*
@FXML
private void onBuscarClicked() {
    ClaseUsuario usuario = new ClaseUsuario();

    // Criterio seleccionado por radio buttons
    if (rbtnPorUsuario.isSelected()) {
        usuario.setApellido(txtapellido.getText().trim());
        usuario.setNombre(txtnombre.getText().trim());
        usuario.setCriterioBusqueda("usuario");
    } else if (rbtnPorRol.isSelected()) {
        ComboItem rol = cmbroles.getValue();
        if (rol != null) {
            usuario.setCod_tusuario(rol.getId());
            usuario.setCriterioBusqueda("rol");
        } else {
            Utilidades.mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Debe seleccionar un rol.");
            return;
        }
    }

    try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971")) {
        if (usuario.buscar(conexion)) {
            grillaUsuario.setItems(FXCollections.observableArrayList(usuario.getListaResultados()));
            Utilidades.mostrarAlerta(Alert.AlertType.INFORMATION, "Resultado", 
                "Se encontraron " + usuario.getListaResultados().size() + " registros.");
        } else {
            Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error", usuario.getMensajeError());
        }
    } catch (SQLException e) {
        e.printStackTrace();
        Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al conectar a la base de datos.");
    }
}

@FXML public void onBuscar(ActionEvent event) {
    ClaseUsuario usuario = new ClaseUsuario();

    String textoBusqueda = txtBusqueda.getText().trim();

    // Establecer criterio de búsqueda
    if (rbtnPorUsuario.isSelected()) {
        usuario.setCriterioBusqueda("usuario");
        usuario.setApellido(textoBusqueda); // Se usa como apellido o nombre
        usuario.setNombre(textoBusqueda);   // Para cubrir ambas columnas
    } else if (rbtnPorRol.isSelected()) {
        usuario.setCriterioBusqueda("rol");

        // Intentar parsear el código de rol desde el texto ingresado
        try {
            int codRol = Integer.parseInt(textoBusqueda);
            usuario.setCod_tusuario(codRol);
        } catch (NumberFormatException e) {
            Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Debe ingresar un código de rol válido.");
            return;
        }
    }

    try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971")) {
        if (usuario.buscar(conexion)) {   // buscar devuelve boolean
            List<GrillaUsuario> resultados = usuario.getListaResultados();  // obtener la lista de resultados
            grillaUsuario.getItems().clear();
            grillaUsuario.getItems().addAll(resultados);
            Utilidades.mostrarAlerta(Alert.AlertType.INFORMATION, "Resultado de búsqueda", resultados.size() + " registro(s) encontrado(s).");
        } else {
            Utilidades.mostrarAlerta(Alert.AlertType.INFORMATION, "Resultado de búsqueda", "No se encontraron registros.");
        }
    } catch (SQLException e) {
        Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al buscar: " + e.getMessage());
        }
}
*/

@FXML public void onBuscar(ActionEvent event) {
//@FXML public void BuscarUsuario(ActionEvent event) {
    String where = "";
    String textoBusqueda = txtBusqueda.getText().trim();

    if (rbtnPorRol.isSelected()) {
        ComboItem item = cmbroles.getValue();
        if (item == null) {
            Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Debe seleccionar un rol.");
            return;
        }
        int codRol = item.getId();
        where = " WHERE u.cod_tusuario = " + codRol;
    } else if (rbtnPorUsuario.isSelected()) {
        where = " WHERE p.apellido LIKE '%" + textoBusqueda + "%' OR p.nombre LIKE '%" + textoBusqueda + "%'";
    } else {
        Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Seleccione un criterio de búsqueda.");
        return;
    }

    ClaseUsuario usuario = new ClaseUsuario();
    usuario.BuscarUsuario(where);

    // Cargar resultados en grilla después de búsqueda
    grillaUsuario.getItems().clear();
    grillaUsuario.getItems().addAll(usuario.getListaResultados());

    int cantidad = usuario.getListaResultados().size();
    Alert.AlertType tipo = cantidad > 0 ? Alert.AlertType.INFORMATION : Alert.AlertType.WARNING;
    Utilidades.mostrarAlerta(tipo, "Resultado de búsqueda", cantidad + " registro(s) encontrado(s).");
}

}




