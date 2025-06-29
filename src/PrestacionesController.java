package src;

import java.io.IOException;
import java.sql.*;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import src.Utilidades.ComboItem;

public class PrestacionesController {
    @FXML private TextField txtid;
    @FXML private TextField txtprestacion;
    @FXML private ComboBox<ComboItem> cmbtipoprestacion;
    @FXML private TableView<GrillaPrestaciones> grillaprestacion;
    @FXML private TableColumn<GrillaPrestaciones,Integer> col_id;
    @FXML private TableColumn<GrillaPrestaciones, String> col_descripcion;
    @FXML private TableColumn<GrillaPrestaciones, String> col_tipo;
    @FXML private Button btnnuevo;
    @FXML private Button btngrabar;
    @FXML private Button btnEliminar;
    @FXML private Button btnModificar;
    @FXML private Button btnConsultar;

    private ObservableList<GrillaPrestaciones> listaPrestaciones = FXCollections.observableArrayList();

    private enum ModoOperacion {
        NINGUNO, NUEVO, MODIFICAR, ELIMINAR
    }

    private ModoOperacion modoActual = ModoOperacion.NINGUNO;
    private boolean modoNuevo = false;

    @FXML public void initialize() {
        Utilidades.habilitarCampos(false, new TextField[]{txtid, txtprestacion}, cmbtipoprestacion);
        btngrabar.setDisable(true);

        // Cargar ComboBox
        String sqlTipos1 = "SELECT id_tipo_prestacion, nombre_tipo_prestacion FROM tipo_prestaciones";
        Utilidades.cargarCombo(cmbtipoprestacion, sqlTipos1, "nombre_tipo_prestacion", "id_tipo_prestacion");

        // Configurar columnas de tabla
        col_id.setCellValueFactory(new PropertyValueFactory<>("id_prestacion"));
        col_descripcion.setCellValueFactory(new PropertyValueFactory<>("nombre_prestacion"));
        col_tipo.setCellValueFactory(new PropertyValueFactory<>("nombre_tipo_prestacion"));

        // Cargar datos en la tabla
        cargarDatosPrestaciones();

        // Manejo de clics en la tabla
        //onUsuarioSeleccionado();
        grillaprestacion.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                onUsuarioSeleccionado();  // llama tu método al seleccionar
            }
});
    }
    
    private ClasePrestaciones obtenerPrestacionSeleccionadaDesdeGrilla() {
    GrillaPrestaciones seleccionada = grillaprestacion.getSelectionModel().getSelectedItem();

    if (seleccionada == null) {
        Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Debe seleccionar una prestación.");
        return null;
    }

    try {
        int id = seleccionada.getId_prestacion();
        String nombre = seleccionada.getNombre_prestacion();
        String descripcionTipo = seleccionada.getNombre_tipo_prestacion();  // Corregido para usar el nombre del tipo

        int tipo = -1;

        String sql = "SELECT id_tipo_prestacion FROM tipo_prestaciones WHERE nombre_tipo_prestacion = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, descripcionTipo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tipo = rs.getInt("id_tipo_prestacion");
            } else {
                Utilidades.mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "No se encontró el tipo de prestación: " + descripcionTipo);
                return null;
            }
        } // fin try-with-resources

        return new ClasePrestaciones(id, nombre, tipo);

    } catch (Exception e) {
        Utilidades.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al obtener los datos de la prestación seleccionada: " + e.getMessage());
        return null;
    }
}


    private void cargarDatosFormulario(GrillaPrestaciones prestacion) {
        if (prestacion != null) {
            // Cargo los campos de texto
            txtid.setText(String.valueOf(prestacion.getId_prestacion()));
            txtprestacion.setText(prestacion.getNombre_prestacion());

            // Busco el ComboItem correcto en el ComboBox
            for (ComboItem item : cmbtipoprestacion.getItems()) {
                if (item.getDescripcion().equals(prestacion.getNombre_tipo_prestacion())) {
                    cmbtipoprestacion.setValue(item);
                    break;
                }
            }

            // Activo edición
            modoActual = ModoOperacion.MODIFICAR;
            btngrabar.setDisable(false);
            btnnuevo.setDisable(true);
            txtprestacion.requestFocus();
        }
    }

    @FXML private void onUsuarioSeleccionado() {
        GrillaPrestaciones prestaciones = grillaprestacion.getSelectionModel().getSelectedItem();
        if (prestaciones != null) {
            // Cambiar al modo de modificación
            modoActual = ModoOperacion.MODIFICAR;
            modoNuevo = false;

            txtid.setText(String.valueOf(prestaciones.getId_prestacion()));
            txtprestacion.setText(prestaciones.getNombre_prestacion());
            txtprestacion.setDisable(false);
            // Habilitar los campos
            btnnuevo.setDisable(true);
            btngrabar.setDisable(false);
            grillaprestacion.getSelectionModel().clearSelection();
            txtid.requestFocus();

            // Seleccionar el tipo de prestación correspondiente en el ComboBox
            String tipoPrestacion = prestaciones.getNombre_tipo_prestacion();
            for (ComboItem item : cmbtipoprestacion.getItems()) {
                if (item.getDescripcion().equals(tipoPrestacion)) {
                    cmbtipoprestacion.setValue(item);
                    break;
                }
            }
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

    @FXML private void onNuevoClicked(ActionEvent event) {
        // Limpiar campos y habilitar campos
        txtid.setText(String.valueOf(obtenerNuevoIdPrestacion()));
        txtprestacion.setText("");
        cmbtipoprestacion.getSelectionModel().clearSelection();
        btngrabar.setDisable(false); // Habilitar botón de grabar
        txtprestacion.setDisable(false);
        cmbtipoprestacion.setDisable(false);
        txtprestacion.requestFocus();
    }

    @FXML private void onGrabarClicked(ActionEvent event) {
        String nombre_prestacion = txtprestacion.getText();
        int id = Integer.parseInt(txtid.getText());

        // Obtener el ComboItem seleccionado, que contiene el id y el nombre
        ComboItem tipoSeleccionado = cmbtipoprestacion.getValue();
        int tipoId = tipoSeleccionado != null ? tipoSeleccionado.getId() : -1; // Si no se seleccionó nada, asignar -1 como valor por defecto

        if (tipoId == -1) {
            // Mostrar un mensaje de error si no se seleccionó tipo de prestación
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Debe seleccionar un tipo de prestación.");
            return;
        }

        if (nombrePrestacionExiste(nombre_prestacion)) {
            // Si ya existe, mostrar alerta
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "La prestación ya existe.");
        } else {
            // Si no existe, proceder a guardar
            String sql = "INSERT INTO prestaciones (id_prestacion, nombre_prestacion, tipo_prestacion) VALUES (?, ?, ?)";
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971");
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id); // id_prestacion
                stmt.setString(2, nombre_prestacion); // nombre_prestacion
                stmt.setInt(3, tipoId); // tipo_prestacion (usamos el ID del combo seleccionado)

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "La prestación ha sido registrada.");
                    cargarDatosPrestaciones(); // Recargar la lista de prestaciones en la tabla
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar la prestación.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al guardar la prestación.");
            }
        }
    }

    private void cargarDatosPrestaciones() {
        // Limpiar cualquier dato previo en la tabla
        grillaprestacion.getItems().clear();

        String sql = "SELECT p.id_prestacion, p.nombre_prestacion, t.nombre_tipo_prestacion " +
                     "FROM prestaciones p " +
                     "JOIN tipo_prestaciones t ON p.tipo_prestacion = t.id_tipo_prestacion";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971");
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            ObservableList<GrillaPrestaciones> lista = FXCollections.observableArrayList();

            while (rs.next()) {
                int id = rs.getInt("p.id_prestacion");
                String nombre = rs.getString("p.nombre_prestacion");
                String nombreTipo = rs.getString("t.nombre_tipo_prestacion");

                lista.add(new GrillaPrestaciones(id, nombre, nombreTipo));
            }

            grillaprestacion.setItems(lista);

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudieron cargar las prestaciones.");
        }
    }

    private int obtenerNuevoIdPrestacion() {
        String sql = "SELECT MAX(id_prestacion) FROM prestaciones";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971");
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1) + 1; // Devuelve el máximo + 1
            } else {
                return 1; // Si no hay registros, el id comienza en 1
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Retorna un valor negativo si hay error
        }
    }

    private boolean nombrePrestacionExiste(String nombre) {
        String sql = "SELECT COUNT(*) FROM prestaciones WHERE nombre_prestacion = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Si el contador es mayor a 0, el nombre ya existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

@FXML  private void onEliminar(ActionEvent event) {
    ClasePrestaciones prestacion = obtenerPrestacionSeleccionadaDesdeGrilla();

    if (prestacion == null) {
        mostrarAlerta(Alert.AlertType.ERROR, "Error", "Debe seleccionar una prestación para eliminar.");
        return;
    }
    // Llamar al método eliminar() que tiene toda la lógica interna
    if (prestacion.eliminar()) {
        mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Prestación eliminada correctamente.");
        cargarDatosPrestaciones();  // Refrescar tabla
        txtid.clear();
        txtprestacion.clear();
        cmbtipoprestacion.getSelectionModel().clearSelection();
        btngrabar.setDisable(true);
        btnnuevo.setDisable(false);
    } else {
        mostrarAlerta(Alert.AlertType.ERROR, "Error", prestacion.getMensajeError());
    }
}


@FXML private void onModificar(ActionEvent event){
     ClasePrestaciones prestacion = obtenerPrestacionSeleccionadaDesdeGrilla();
    String nombrePrestacion = txtprestacion.getText();
    int idPrestacion = Integer.parseInt(txtid.getText());

    if (prestacion == null) {
        mostrarAlerta(Alert.AlertType.ERROR, "Error", "Debe seleccionar una prestación para eliminar.");
        return;
    }
    // Llamar al método eliminar() que tiene toda la lógica interna
    if (prestacion.modificar(idPrestacion,nombrePrestacion)) {
        mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Prestación modifiicada correctamente.");
        cargarDatosPrestaciones();  // Refrescar tabla
        txtid.clear();
        txtprestacion.clear();
        cmbtipoprestacion.getSelectionModel().clearSelection();
        btngrabar.setDisable(true);
        btnnuevo.setDisable(false);
    } else {
        mostrarAlerta(Alert.AlertType.ERROR, "Error", prestacion.getMensajeError());
    }
    System.out.println(">>> Fin de onModificar");
}
@FXML private void onConsultar(ActionEvent event){
    System.out.println("onConsultar");
}
}

