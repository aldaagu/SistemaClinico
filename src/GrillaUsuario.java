package src;


public class GrillaUsuario {
    //private int idUsuario;          // ID del usuario
    private String Idusuario;         // Nombre de usuario
    private String apellido;
    private String nombre;          // Nombre del usuario
    private String rol;             // Descripción del rol
    private String email;        // Teléfono
    private String telefono;           // Correo electrónico

    // Constructor
    public GrillaUsuario(String idusuario,  String apellido,String nombre,
                         String rol, String email, String telefono) {
        //this.idUsuario = idUsuario;
        this.Idusuario = idusuario;
        this.apellido = apellido;
        this.nombre = nombre;
        
        this.rol = rol;
        this.email = email;
        this.telefono = telefono;
    }

  // Métodos getters
  //public Integer getIdUsuario() {
   // return idUsuario;
//}

public String getIdUsuario() {
    return Idusuario;
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


@Override public String toString() {
    return "GrillaUsuario{" +
            "  usuario='" + Idusuario + '\'' +
            ", apellido='" + apellido + '\'' +
            ", nombre='" + nombre + '\'' +
            ", rol='" + rol + '\'' +
            ", email='" + email + '\'' +
            ", telefono='" + telefono + '\'' +
            '}';
}

}