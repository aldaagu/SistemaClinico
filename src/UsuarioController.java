package src;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.Utilidades.ComboItem;
import javafx.event.ActionEvent;


public class UsuarioController {

    //@FXML
    //private TableView<GrillaUsuario> grillaUsuario;

    private ObservableList<GrillaUsuario> listaUsuarios = FXCollections.observableArrayList();

    //@FXML private TableView<GrillaUsuario> grillaprestaciones;
    //@FXML private TableColumn<GrillaUsuario, Integer> col_id;
    //@FXML private TableColumn<GrillaUsuario, Integer> col_usuario;
    //@FXML private TableColumn<GrillaUsuario, String> col_apellido;
   //@FXML private TableColumn<GrillaUsuario, String> col_nombre;
   // @FXML private TableColumn<GrillaUsuario, String> col_txtemail;  // Declara txtemail como un TextField
   // @FXML private TableColumn<GrillaUsuario, String> col_Telefono;
    //@FXML private ComboBox<ComboItem> cmbrol;
    // private TableColumn<GrillaUsuario, Integer> col_id;
   // @FXML
   // private TableColumn<GrillaUsuario, String> col_usu;
    //@FXML
   // private TableColumn<GrillaUsuario, String> col_nombre;
    //@FXML
   // private TableColumn<GrillaUsuario, String> col_apellido;
   // @FXML
    //private TableColumn<GrillaUsuario, String> col_rol;
   // @FXML
    //private TableColumn<GrillaUsuario, String> col_tel;
    //@FXML
   // private TableColumn<GrillaUsuario, String> col_email;

    @FXML
    private MenuItem smnuABMusu; // IMPORTANTE: vincular este campo con el fx:id en el FXML
    private MenuItem smnuABMCprestaciones;
    @FXML private MenuItem smnuABMCOs;


    @FXML
    public void initialize() {
        System.out.println("UsuarioController inicializado");

        // Configurar columnas de la tabla
        //configureTableColumns();

        // Cargar datos en la tabla
        //System.out.println("Ingreso a cargar datos");
       // cargarDatos();

        // Configurar acción del MenuItem
        if (smnuABMusu != null) {
            smnuABMusu.setOnAction(this::abrirPantallaGestionUsuarios);  // Pasar el evento ActionEvent
        } else {
            System.out.println("El MenuItem  no está vinculado correctamente.");
        }
        if(smnuABMCprestaciones != null) {
            smnuABMCprestaciones.setOnAction(this::onCargarPantallaPrestaciones); 
        } else{
            System.out.println("El MenuItem  no está vinculado correctamente.");
        }
        if(smnuABMCOs != null) {
            smnuABMCOs.setOnAction(this::onCargarPantallaPrestadores); 
            
        } else{
            System.out.println("El MenuItem  no está vinculado correctamente.");
        }
    }

    //if (smnuABMPrestaciones != null) {
      //  smnuABMPrestaciones.setOnAction(this::onCargarPantallaPrestaciones);  // Pasar el evento ActionEvent
    //} else {
    //    smnuABMPrestaciones.setOnAction(this::onCargarPantallaPrestaciones); 
      //  System.out.println("El MenuItem smnuABMprestaciones no está vinculado correctamente.");
    //}

   

    // Método para abrir la ventana de gestión de usuarios
    @FXML public void abrirPantallaGestionUsuarios(ActionEvent event) {  // Agregar ActionEvent como parámetro
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionUsuarios.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Gestión de Usuarios");
            stage.centerOnScreen();

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML public void onCargarPantallaPrestaciones(ActionEvent event) {
        System.out.println("Se cargará la pantalla de prestaciones.");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PantallaGestionPrestaciones.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Gestión de Prestaciones");
            stage.centerOnScreen();

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
public void onCargarPantallaPrestadores(ActionEvent event) {
    System.out.println("Se cargará la panta lla de prestaciones.");
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/prestadores.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Gestión de Prestadpres");
        stage.centerOnScreen();
        stage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
