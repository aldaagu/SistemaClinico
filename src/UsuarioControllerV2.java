package src;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UsuarioControllerV2 {

    @FXML
    private MenuItem smnuABMusu; // MenuItem para ABMC de Usuarios

    @FXML
    private TableView<GrillaUsuario> grillaUsuario;

    @FXML
    private TableColumn<GrillaUsuario, Integer> col_id;
    @FXML
    private TableColumn<GrillaUsuario, String> col_usu;
    @FXML
    private TableColumn<GrillaUsuario, String> col_nombre;
    @FXML
    private TableColumn<GrillaUsuario, String> col_apellido;
    @FXML
    private TableColumn<GrillaUsuario, String> col_rol;
    @FXML
    private TableColumn<GrillaUsuario, String> col_tel;
    @FXML
    private TableColumn<GrillaUsuario, String> col_email;

    private ObservableList<GrillaUsuario> listaUsuarios = FXCollections.observableArrayList();

    public void initialize() {
        System.out.println("UsuarioController inicializado");

        // Configurar columnas de la tabla
        configureTableColumns();

        // Cargar datos en la tabla
        System.out.println("Ingreso a cargar datos");
        cargarDatos();

        // Configurar acción del MenuItem
        if (smnuABMusu != null) {
            smnuABMusu.setOnAction(this::abrirPantallaGestionUsuarios);
        } else {
            System.out.println("El MenuItem smnuABMusu no está vinculado correctamente.");
        }
    }

    private void configureTableColumns() {
        col_id.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        col_usu.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        col_rol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        col_tel.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void cargarDatos() {
        listaUsuarios.clear();

        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "password");
             Statement statement = conexion.createStatement()) {

            // Consulta SQL
            String query = "SELECT  u.usuario, u.nombre, u.apellido, r.descripcion AS rol, " +
                    "u.telefono, u.email FROM usuarios u JOIN roles r ON u.id_rol = r.id_rol";

            ResultSet resultSet = statement.executeQuery(query);

            // Rellenar la lista de usuarios
            while (resultSet.next()) {
                listaUsuarios.add(new GrillaUsuario(
                        
                        resultSet.getString("usuario"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
                        resultSet.getString("rol"),
                        resultSet.getString("telefono"),
                        resultSet.getString("email")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Configurar datos en la tabla
        grillaUsuario.setItems(listaUsuarios);
    }

    private void abrirPantallaGestionUsuarios(ActionEvent event) {
        System.out.println("Ingreso a abrir pantalla");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionUsuarios.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Gestión de Usuarios");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}