package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;



import src.Utilidades.ComboItem;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import src.Utilidades.ComboItem;
import javafx.scene.control.TableRow;


public class GestionUsuariosController {

    private String mensajeError = "";
    //@FXML
    //private void onCancelarClicked() {
    //    formularioUsuario.setVisible(false);
    //    formularioUsuario.setManaged(false);
    //}

   // @FXML
    //private void onAgregarClicked() {
    //    formularioUsuario.setVisible(true);
    //    formularioUsuario.setManaged(true);
     //   limpiarCampos(); // para asegurarte que estén vacíos
    //}

   // @FXML private void onModificarClicked() {
     //  if (grillaUsuario.getSelectionModel().getSelectedItem() != null) {
            //formularioUsuario.setVisible(true);
            //formularioUsuario.setManaged(true);
    //        cargarDatosFormulario(grillaUsuario.getSelectionModel().getSelectedItem());
     //       modoActual = ModoOperacion.MODIFICAR;
    //        modoNuevo = false;
            // Deshabilitar el botón de grabar si el campo de usuario está vacío
     //       btngrabar.setDisable(txtusuario.getText().trim().isEmpty());
            // Habilitar los campos de entrada para la modificación
    //        habilitarCampos(true);
     //   }else {
            // Si no hay usuario seleccionado, mostrar alerta
     //       mostrarAlerta("Error", "Debe seleccionar un usuario para modificar.");
     //   }
    //}

    private boolean modoNuevo = false;

    @FXML private TextField txtusuario;
    @FXML private TextField txtapellido;
    @FXML private TextField txtnombre;
    @FXML private TextField txttelefono;
    @FXML private TextField txtemail;
    @FXML private TextField txtconfirmarContrasena;
    @FXML private TextField txtcontrasena;
    @FXML private Button btnnuevo;
    @FXML private Button btngrabar;
    
    @FXML private TableView<GrillaUsuario> grillaUsuario;
    //@FXML private TableColumn<GrillaUsuario, Integer> col_id;
    @FXML private TableColumn<GrillaUsuario, String> col_usuario;
    @FXML private TableColumn<GrillaUsuario, String> col_nombre;
    @FXML private TableColumn<GrillaUsuario, String> col_apellido;
    @FXML private TableColumn<GrillaUsuario, String> col_rol;
    @FXML private TableColumn<GrillaUsuario, String> col_Telefono;
    @FXML private TableColumn<GrillaUsuario, String> col_email;
    //@FXML private ComboBox<String> cmbroles;
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
    
        System.out.println("click en nuevo");
        modoActual = ModoOperacion.NUEVO;
        modoNuevo = true;
        limpiarCampos();
        habilitarCampos(true);
        btngrabar.setDisable(true); // al principio está deshabilitado porque el txtusuario está vacío
        txtusuario.requestFocus();
        grillaUsuario.getSelectionModel().clearSelection();
        btngrabar.setDisable(false); // Habilita el botón grabar
    }
    
//@FXML public void onModificarClicked(ActionEvent event) {
    //@FXML private void onModificarClicked() {
        // Asegúrate de que haya un usuario seleccionado en la tabla
       // mostrarAlerta("antes", "de cargar usuarioseleccionado");
       // GrillaUsuario usuarioSeleccionado = grillaUsuario.getSelectionModel().getSelectedItem();
       
      //  if (usuarioSeleccionado != null) {
        //if (!txtusuario.getText().trim().isEmpty()) {
      //      mostrarAlerta("Entrada", "usuarioseleccionado tiene usuario y ademas va a cargar datosformulario");
            // Si un usuario está seleccionado, vamos a cargar sus datos en los campos de texto
     //      cargarDatosFormulario(usuarioSeleccionado);
            //cargarDatosFormulario(txtusuario);

    
            // Cambiar al modo de modificación
     //       modoActual = ModoOperacion.MODIFICAR;
    //        modoNuevo = false;  // El modo ya no es nuevo
    
            // Habilitar los campos de entrada para la modificación
     //       habilitarCampos(true);
     //       System.out.println("Contenido de txtusuario: '" + txtusuario.getText() + "'");
     //       if (!txtusuario.getText().trim().isEmpty()) {
     //           btngrabar.setDisable(false); // habilitar el botón de grabar
     //       } else {
    //            mostrarAlerta("Advertencia", "El campo de usuario está vacío después de cargar los datos.");
    //            btngrabar.setDisable(true);
     //       }            // habilitar el botón de grabar si el campo de usuario está vacío
            //btngrabar.setDisable(txtusuario.getText().trim().isEmpty());
     //   } else {
            // Si no hay ningún usuario seleccionado, mostrar una alerta
   //        mostrarAlerta("Error", "Debe seleccionar un usuario para modificar.");
     //   }
   //}
    //8990
@FXML public void onGrabarClicked(ActionEvent event) {
    try {
        // Verificar si es un nuevo usuario y si el usuario ya existe en la base de datos
        if (modoActual == ModoOperacion.NUEVO) {
            // Verificar si el usuario ya existe en la base de datos
            if (usuarioExiste(txtusuario.getText())) {
                System.out.println("El usuario ya existe.");
                // Mostrar mensaje de error, por ejemplo con un alert:
                mostrarAlerta("Error", "El usuario ya existe.");
                return; // Salir del método sin hacer nada
            }

            // Si no existe, realizar el alta en la base de datos
            if (altaUsuario()) {
                mostrarAlerta("Éxito", "Usuario creado exitosamente.");
                limpiarCampos();
                cargarDatos();
            } else {
                mostrarAlerta("Error", mensajeError);  // Mostrar el error real
            }

        } else if (modoActual == ModoOperacion.MODIFICAR) {
            // Si es modo modificar, se ejecuta la modificación
            if (modificarUsuario()) {
                System.out.println("Usuario modificado correctamente");
                mostrarAlerta("Éxito", "Usuario modificado exitosamente.");
                limpiarCampos();
                cargarDatos();  // Recargar la lista para mostrar los cambios
            } else {
                mostrarAlerta("Error", mensajeError);
            }
        }
    } //catch (SQLException e) {
        // Manejo de excepción SQL
      //  mostrarAlerta("Error de base de datos", "Hubo un error al procesar la solicitud.");
        //e.printStackTrace();  // Imprimir el error para depuración
    //} 
    catch (Exception e) {
        // Capturar cualquier otra excepción
        mostrarAlerta("Error", "Ocurrió un error inesperado.");
        e.printStackTrace();  // Imprimir el error para depuración
    }
}


public boolean modificarUsuario() {
    mensajeError = "";  // Limpiar error anterior
    //GrillaUsuario usuarioSeleccionado = grillaUsuario.getSelectionModel().getSelectedItem();
    try {
        // Verificar si los campos están vacíos
        if (txtusuario.getText().trim().isEmpty()){              
            mensajeError = "Por favor seleccione el usuario";
            return false;
        }

        // También la contraseña
        //if (txtcontrasena.getText().trim().isEmpty()) {
         //   mensajeError = "La contraseña no puede estar vacía.";
          //  return false;
       // }

        // Verificar que el ComboBox tenga un valor seleccionado
        ComboItem rolSeleccionado = cmbroles.getValue();
        if (rolSeleccionado == null) {
            mensajeError = "Debe seleccionar un rol.";
            return false;
        }

        // Actualizar el usuario en la base de datos
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, tel = ?, email = ?, contrasena = ?, id_rol = ? WHERE Usuario = ?";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, txtnombre.getText());
            stmt.setString(2, txtapellido.getText());
            stmt.setString(3, txttelefono.getText());
            stmt.setString(4, txtemail.getText());
            stmt.setString(5, txtcontrasena.getText());
            stmt.setInt(6, rolSeleccionado.getId()); // Asignar el ID del rol seleccionado
            //stmt.setString(7, usuarioSeleccionado.getUsuario()); // Clave primaria: Usuario
            stmt.setString(7, txtusuario.getText());
            // Ejecutar la actualización
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0; // Si se actualizaron filas, la actualización fue exitosa
        } catch (SQLException e) {
            mensajeError = e.getMessage();
            return false;
        }

    } catch (Exception e) {
        mensajeError = e.getMessage();
        return false;
    }
}

public boolean altaUsuario() {
    mensajeError = "";  // Limpiar error anterior
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
        try {
            
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al conectar con la base de datos.");
        }

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
      
        txtusuario.setText(usuario.getUsuario());
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
        txtusuario.requestFocus();
        // Seleccionar el rol correspondiente en el ComboBox
        String rolDelUsuario = usuario.getRol();
        for (ComboItem item : cmbroles.getItems()) {
            if (item.getDescripcion().equals(rolDelUsuario)) {
                cmbroles.setValue(item);
                break;
                      }
        }       
        
    }
}

private void habilitarCampos(boolean habilitar) {
    txtusuario.setDisable(!habilitar);
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
        col_usuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        col_rol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        col_Telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
}

private void cargarDatos() {
        listaUsuarios.clear();
        //try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971");
        
            try(Statement statement = conexion.createStatement()) {
            // Consulta SQL
            String query = "SELECT u.usuario, u.nombre, u.apellido, r.descripcion AS rol, " +
                    "u.tel, u.email FROM usuarios u JOIN roles r ON u.id_rol = r.id_rol";
            ResultSet resultSet = statement.executeQuery(query);
            // Rellenar la lista de usuarios
            while (resultSet.next()) {
                listaUsuarios.add(new GrillaUsuario(
                       // resultSet.getInt("idusuario"),
                        resultSet.getString("usuario"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
                        resultSet.getString("rol"),
                        resultSet.getString("tel"),
                        resultSet.getString("email")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Configurar datos en la tabla
        grillaUsuario.setItems(listaUsuarios);
}

    private void cargarDatosFormulario(GrillaUsuario usuario) {
        txtusuario.setText(usuario.getUsuario());
        txtnombre.setText(usuario.getNombre());
        txtapellido.setText(usuario.getApellido());
        txttelefono.setText(usuario.getTelefono());
        txtemail.setText(usuario.getEmail());
        // y el resto de los campos...
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

   
}

