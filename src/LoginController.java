package src;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML  private TextField txtusuario;
    @FXML  private PasswordField txtpassword;
    @FXML  private Button btnlogin;

    // Método que se ejecuta al hacer clic en el botón
    @FXML private void iniciarSesion() {
        String usuario = txtusuario.getText();
        String passwordInput = txtpassword.getText();

        // Validar que los campos no estén vacíos
        if (usuario.isEmpty() || passwordInput.isEmpty()) {
            System.out.println("Por favor, complete todos los campos.");
            return;
        }
        // Intentar la conexión a la base de datos
        try {
            // Cambia estos parámetros según tu base de datos
            String url = "jdbc:mysql://localhost:3306/clinica";
            String user = "root";
            String password = "Pchard_1971";

            // Conexión con JDBC y validacion de usuario
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            if (conn!=null) {

                // validamos usuario y contrase;a
                String query = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                
                stmt.setString(1, usuario);
                stmt.setString(2, passwordInput);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Inicio de sesión exitoso. ¡Bienvenido, " + usuario + "!");
                    
                    // Redirigir a PantallaPrincipal.fxml

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/pantallaPrincipal.fxml"));
                   


                    Scene scene = new Scene(loader.load());

                    Stage stage = (Stage) btnlogin.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Pantalla Principal");
                    stage.centerOnScreen();
                    stage.show();

                
                } else {
                    System.out.println("Usuario o contraseña incorrectos.");
                }

            conn.close(); //cerramos la conexion
        } else {
            //System.out.println("Error: No se pudo establecer conexión con la base de datos.");
            Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error en la conexion",
                        "No se pudo establecer conexión con la base de datos");
        }

        } catch (Exception e) {
            //e.printStackTrace();
            //System.out.println("Error al conectar con la base de datos.");
            System.out.println("Descripción del error: " + e.getMessage());
            e.printStackTrace();  // Esto sigue imprimiendo el stack trace para mayor detalle si es necesario.
            System.out.println("Error al conectar con la base de datos.");
        }
    }
}
