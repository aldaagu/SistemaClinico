package src;


public class GrillaUsuario {
    //private int idUsuario;          // ID del usuario
    private String usuario;         // Nombre de usuario
    private String nombre;          // Nombre del usuario
    private String apellido;        // Apellido del usuario
    private String rol;             // Descripción del rol
    private String telefono;        // Teléfono
    private String email;           // Correo electrónico

    // Constructor
    public GrillaUsuario(String usuario, String nombre, String apellido,
                         String rol, String telefono, String email) {
        //this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rol = rol;
        this.telefono = telefono;
        this.email = email;
    }

  // Métodos getters
  //public Integer getIdUsuario() {
   // return idUsuario;
//}

public String getUsuario() {
    return usuario;
}

public String getNombre() {
    return nombre;
}

public String getApellido() {
    return apellido;
}

public String getRol() {
    return rol;
}

public String getTelefono() {
    return telefono;
}

public String getEmail() {
    return email;
}


@Override
public String toString() {
    return "GrillaUsuario{" +
            "usuario='" + usuario + '\'' +
            ", nombre='" + nombre + '\'' +
            ", apellido='" + apellido + '\'' +
            ", rol='" + rol + '\'' +
            ", telefono='" + telefono + '\'' +
            ", email='" + email + '\'' +
            '}';
}

}