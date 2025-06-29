package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import src.Utilidades.ComboItem;



public class ClaseUsuario extends ClasePersona {
    private Integer id_usuario;
    private Integer cod_tusuario; // campo adicional específico de Usuario
    private String contrasena;
    private String mensajeError = "";


    private String criterioBusqueda;
    private List<GrillaUsuario> listaResultados = new ArrayList<>();



    // Constructor vacío
    public ClaseUsuario() {
        super();
    }
    public String getMensajeError() {
        return mensajeError;
    }
 
    public ClaseUsuario(Integer id, String apellido, String nombre, String email, String tel,
                    Integer cod_tusuario, String contrasena) {
    super(id, apellido, nombre, email, tel);
    this.id_usuario = id;
    this.cod_tusuario = cod_tusuario;
    this.contrasena = contrasena;
}

    // Getters y setters específicos
    public Integer getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }
    public Integer getCod_tusuario() {
        return cod_tusuario;
    }
    public void setCod_tusuario(Integer cod_tusuario) {
        this.cod_tusuario = cod_tusuario;
    }
    public void setContrasena(String Contrasena) {
        this.contrasena = Contrasena;
    }
    public String getContrasena() {
            return contrasena;
     }

    public void setApellido(String apellido) 
    { 
        this.apellido = apellido; 
    }
    public void setNombre(String nombre) 
    { 
        this.nombre = nombre; 
    }
    private String whereClause;

    public void setcriterioBusqueda(String criterioBusqueda) 
    { 
        this.criterioBusqueda = criterioBusqueda; 
    }
    public List<GrillaUsuario> getListaResultados() 
    {
        return listaResultados;
    }
 
    @FXML private TextField txtusuario;
    @FXML private TextField txtapellido;
    @FXML private TextField txtnombre;
    @FXML private TextField txttelefono;
    @FXML private TextField txtemail;
    @FXML private TextField txtconfirmarContrasena;
    @FXML private TextField txtcontrasena;
    @FXML private Button btnnuevo;
    @FXML private Button btngrabar;
     @FXML private Button btnEliminar;
     @FXML private Button btnModificar;
     @FXML private Button btnBuscar;
    
    @FXML private TableView<GrillaUsuario> grillaUsuario;
 
    @FXML private TableColumn<GrillaUsuario, String> col_usuario;
    @FXML private TableColumn<GrillaUsuario, String> col_apellido;
    @FXML private TableColumn<GrillaUsuario, String> col_nombre;
    @FXML private TableColumn<GrillaUsuario, String> col_rol;
    @FXML private TableColumn<GrillaUsuario, String> col_email;
    @FXML private TableColumn<GrillaUsuario, String> col_Telefono;
    @FXML private ComboBox<ComboItem> cmbroles;

   @Override public boolean alta(Connection conexion) throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            throw new SQLException("No hay conexión con la base de datos.");
        }

        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }

        String sqlPersona = "INSERT INTO personas (apellido, nombre, tel, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmtPersona = conexion.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS)) {
            stmtPersona.setString(1, apellido);
            stmtPersona.setString(2, nombre);
            stmtPersona.setString(3, tel);
            stmtPersona.setString(4, email);
            int filasPersona = stmtPersona.executeUpdate();
            if (filasPersona > 0) {
                ResultSet rs = stmtPersona.getGeneratedKeys();
                if (rs.next()) {
                    id_persona = rs.getInt(1);
                }
            }
        }

        String sqlUsuario = "INSERT INTO usuarios2 (id_usuario, cod_tusuario, contrasena) VALUES (?, ?, ?)";
        try (PreparedStatement stmtUsuario = conexion.prepareStatement(sqlUsuario)) {
            stmtUsuario.setInt(1, id_persona);
            stmtUsuario.setInt(2, cod_tusuario);
            stmtUsuario.setString(3, contrasena);
            return stmtUsuario.executeUpdate() > 0;
        }
}

    public String getCriterioBusqueda() {
        return criterioBusqueda;
    }

    public void setCriterioBusqueda(String criterioBusqueda) {
        this.criterioBusqueda = criterioBusqueda;
    }

    public boolean modificarUsuario(Connection conexion) {
    mensajeError = "";  // Limpiar cualquier mensaje de error anterior

    try {
        String sql = "UPDATE personas p " +
                "JOIN usuarios2 u ON p.id_persona = u.id_usuario " +
                "SET p.nombre = ?, p.apellido = ?, p.tel = ?, p.email = ?, u.contrasena = ?, u.cod_tusuario = ? " +
                "WHERE u.id_usuario = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, this.nombre);
            stmt.setString(2, this.apellido);
            stmt.setString(3, this.tel);
            stmt.setString(4, this.email);
            stmt.setString(5, this.contrasena);
            stmt.setInt(6, this.cod_tusuario);
            stmt.setInt(7, this.id_usuario);

            int filasAfectadas = stmt.executeUpdate();

            return filasAfectadas > 0;
        }
    } catch (Exception e) {
        mensajeError = "Error al modificar: " + e.getMessage();
        return false;
    }
}

    @Override  public boolean modificar(Connection conexion) {
        return modificarUsuario(conexion);
    }

    @Override public boolean eliminar(Connection conexion) {
    mensajeError = "";

    if (id_usuario == null) {
        mensajeError = "ID de usuario no definido.";
        return false;
    }

    try {
        // Verificar si existen órdenes asociadas
        String consultaOrdenes = "SELECT COUNT(*) FROM ordenes WHERE id_profesional = ?";
        try (PreparedStatement checkStmt = conexion.prepareStatement(consultaOrdenes)) {
            checkStmt.setInt(1, id_usuario);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                mensajeError = "No se puede eliminar el usuario porque tiene órdenes asociadas.";
                return false;
            }
        }

        // Eliminar de usuarios2 primero por FK, luego de personas
        String eliminarUsuario = "DELETE FROM usuarios2 WHERE id_usuario = ?";
        try (PreparedStatement stmt1 = conexion.prepareStatement(eliminarUsuario)) {
            stmt1.setInt(1, id_usuario);
            stmt1.executeUpdate();
        }

        String eliminarPersona = "DELETE FROM personas WHERE id_persona = ?";
        try (PreparedStatement stmt2 = conexion.prepareStatement(eliminarPersona)) {
            stmt2.setInt(1, id_usuario);
            stmt2.executeUpdate();
        }

        return true;

    } catch (SQLException e) {
        mensajeError = "Error al eliminar: " + e.getMessage();
        return false;
    }
}

public void BuscarUsuario(String where) {
    this.whereClause = where;

    try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/clinica", "root", "Pchard_1971")) {
        buscar(conexion);  // ejecuta y llena la lista
    } catch (SQLException e) {
        mensajeError = "Error al eliminar: " + e.getMessage();
        e.printStackTrace();
    }
}
@Override public boolean buscar(Connection conexion) throws SQLException {
    listaResultados.clear();

    String sql = "SELECT u.id_usuario, p.apellido, p.nombre, r.descripcion, p.email, p.tel " +
                 "FROM usuarios2 u " +
                 "JOIN personas p ON u.id_usuario = p.id_persona " +
                 "JOIN roles r ON u.cod_tusuario = r.id_rol";

    if (whereClause != null && !whereClause.isEmpty()) {
        sql += whereClause;
    }
   System.out.println(sql);
    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            listaResultados.add(new GrillaUsuario(
                String.valueOf(rs.getInt("id_usuario")),
                rs.getString("apellido"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getString("email"),
                rs.getString("tel")
            ));
        }
        return !listaResultados.isEmpty();
    } catch (SQLException e) {
        mensajeError = "Error en búsqueda: " + e.getMessage();
        throw e; // puede ser capturado arriba
    }
}

    
@Override public ClaseUsuario consultarPorId(Connection conexion, Integer id) throws SQLException {
    //String sql = "SELECT p.id_persona, p.apellido, p.nombre, p.email, p.tel, u.cod_tusuario, u.contrasena " +
    String sql = "SELECT p.id_usuario,p.apellido, p.nombre, r.descripcion, p.email, " +
                "p.tel,u.id_usuario,u.cod_tusuario, u.contrasena" +
               " FROM usuarios2 u " +
               " JOIN personas p ON u.id_persona = p.id_persona " +
               " JOIN roles r ON u.cod_tusuario = r.id_rol";    
    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, id);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new ClaseUsuario(
                rs.getInt("id_persona"),
                rs.getString("apellido"),
                rs.getString("nombre"),
                rs.getString("email"),
                rs.getString("tel"),
                rs.getInt("cod_tusuario"),
                rs.getString("contrasena")
            );          
            } 
        } 
    } 
    return null;
} 

/*
    // Método para modificar el usuario
    public boolean modificar(Connection conexion) {
        String sql = "UPDATE usuarios SET apellido = ?, nombre = ?, cod_tusuario = ?, email = ?, tel = ? WHERE id_usuario = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, apellido);
            stmt.setString(2, nombre);
            stmt.setInt(3, cod_tusuario != null ? cod_tusuario : java.sql.Types.INTEGER);
            stmt.setString(4, email);
            stmt.setString(5, tel);
            stmt.setInt(6, id_persona); // Usamos id_persona como clave

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }*/



/*
//public class ClaseUsuario {
public class ClaseUsuario extends ClasePersona {
    private Integer id_usuario;
    private String apellido;       
    private String nombre;         
    private Integer cod_tusuario;
    private String email;      
    private String tel;
    
    // Constructor vacío 
    public ClaseUsuario() {
    }
    public ClaseUsuario(String apellido, String nombre, int cod_tusuario, String email, String tel) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.cod_tusuario = cod_tusuario;
        this.email = email;
        this.tel = tel;
        //this.contrasena = ""; // o valor por defecto
    //this.bloquear = "NO"; // o valor por defecto
    }

    // Getters y Setters
    public Integer getid_usuario() {
        return id_usuario;
    }
    public void setid_usuario(Integer id_Usuario) {
        this.id_usuario = id_Usuario;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

 

    public Integer getCod_tusuario() {
        return cod_tusuario;
    }

    public void setCod_tusuario(Integer cod_tusuario) {
        this.cod_tusuario = cod_tusuario;
    }

    
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
   

   public boolean alta(Connection conexion) {
    try {
        String sql = "INSERT INTO usuarios (apellido, nombre, cod_tusuario, email, tel) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, apellido);
            stmt.setString(2, nombre);
            
            if (cod_tusuario != null) {
                stmt.setInt(3, cod_tusuario); // cod_tusuario es Integer
            } else {
                stmt.setNull(3, java.sql.Types.INTEGER);
            }
            
            stmt.setString(4, email);
            stmt.setString(5, tel);
            
            int filas = stmt.executeUpdate();
            return filas > 0;  // Retorna true si insertó filas
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


public boolean modificar(Connection conexion) {
    try {
        String sql = "UPDATE usuarios SET apellido = ?, nombre = ?,  cod_tusuario = ?, email = ?,tel = ?,  WHERE usuario = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, apellido);
            stmt.setString(2, nombre);
            //stmt.setString(3, cod_tusuario != null ? cod_tusuario.getCod_tusuario : "NO");
            
            stmt.setString(4, email);
            stmt.setString(5, tel);
           
            int filas = stmt.executeUpdate();
            return filas > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

*/
}

