package src;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class UsuarioController {

    //@FXML
    //private TableView<GrillaUsuario> grillaUsuario;

    //private ObservableList<GrillaUsuario> listaUsuarios = FXCollections.observableArrayList();

    @FXML
    private MenuItem smnuABMusu; // IMPORTANTE: vincular este campo con el fx:id en el FXML
    private MenuItem smnuABMCprestaciones;
    private MenuItem smnCargaOrdenes;
    @FXML private MenuItem smnuABMCOs;
    @FXML private MenuItem smnuSalir;
    @FXML private MenuItem smnuInforme;
    @FXML public void initialize() {
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
        if(smnuInforme!= null) {
            smnuInforme.setOnAction(this::onInformes); 
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
    @FXML public void onInformes(ActionEvent event){
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InformesLiquidaciones.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Informes");
            stage.centerOnScreen();
            stage.show();
            

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("se ha producido u  error");
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
    
    @FXML public void OnSalirApp(ActionEvent event) {
            System.out.println("En desarrollo");
    }
    
    /*@FXML public void AbrirPantallaLiquidacion(ActionEvent event) {
        System.out.println("entro a abrirpantallaLiquidacion");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pantallaLiquidaciones.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Liquidaciones");
            stage.centerOnScreen();
            stage.show();
            

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("se ha producido u  error");
        }
    }*/
public void AbrirPantallaLiquidacion() {
    try {
        // Carga del archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PantallaLiquidaciones.fxml"));
        
        // Carga el contenido del FXML en la variable 'root'
        Parent root = loader.load();  // Aquí 'root' es el contenedor principal de la interfaz
        
        // Crea una escena con el contenido cargado
        Scene scene = new Scene(root);
        
        // Configura y muestra la nueva ventana (Stage)
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        
    } catch (IOException e) {
        e.printStackTrace();  // Imprime el error en caso de que falle la carga
    }
}
}

 