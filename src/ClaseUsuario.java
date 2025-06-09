package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




public class ClaseUsuario {
    
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
            stmt.setString(3, cod_tusuario != null ? cod_tusuario.getCod_tusuario : "NO");
            
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


}

