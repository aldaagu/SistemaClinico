package src;

import java.io.IOException;
import java.io.InputStream;
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
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.Utilidades.ComboItem;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;


public class UsuarioController {

    //@FXML
    //private TableView<GrillaUsuario> grillaUsuario;

    private ObservableList<GrillaUsuario> listaUsuarios = FXCollections.observableArrayList();

    @FXML
    private MenuItem smnuABMusu; // IMPORTANTE: vincular este campo con el fx:id en el FXML
    private MenuItem smnuABMCprestaciones;
    private MenuItem smnCargaOrdenes;
    @FXML private MenuItem smnuABMCOs;
    @FXML private MenuItem smnuSalir;


    @FXML public void initialize() {
        System.out.println("UsuarioController inicializado");

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
        if(smnCargaOrdenes != null) {
            smnCargaOrdenes.setOnAction(this::AbrirPantallaCargadeOrdenes); 
        } else{
            System.out.println("El MenuItem  no está vinculado correctamente.");
        }
    } 

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
    //onCargarPantallaPrestaciones
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
    @FXML public void onCargarPantallaPrestadores(ActionEvent event) {
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
    
    @FXML public void AbrirPantallaCargadeOrdenes(ActionEvent event) {
        System.out.println("Se abrirá la pantalla de carga de órdenes metodo abrirpantallacargadeordenes.");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pantallaCargaOrdenes.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Carga de Órdenes");
            stage.centerOnScreen();
            stage.show();
            System.out.println("paso el comando .show");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("se ha producido u  error");
        }
    }
    @FXML public void AbrirPantallaLiquidacion(ActionEvent event) {
            System.out.println("En desarrollo");
    }
    @FXML public void OnSalirApp(ActionEvent event) {
            System.out.println("En desarrollo");
    }
}


 