package src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar la pantalla de login.fxml
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent loginRoot = loginLoader.load();

        // Configurar la escena inicial con login.fxml
        Scene loginScene = new Scene(loginRoot);
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Clínica Oftalmológica - Login");
        primaryStage.show();
    }

    // Método para cargar pantallaPrincipal.fxml después del login
    public static void cargarPantallaPrincipal() {
        try {
            FXMLLoader pantallaLoader = new FXMLLoader(MainApp.class.getResource("/pantallaPrincipal.fxml"));
            Parent pantallaRoot = pantallaLoader.load();

            // Crear nueva ventana para pantallaPrincipal.fxml
            Stage stage = new Stage();
            stage.setScene(new Scene(pantallaRoot));
            stage.setTitle("Clínica Oftalmológica - Pantalla Principal");
            stage.show();
        } catch (Exception e) {
            System.err.println("Error al cargar pantallaPrincipal.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}