package src;

public class usuario {
    private int idUsuario;           // ID del usuario
    private String usuario;          // Nombre de usuario
    private int idRol;               // ID del rol
    private String contrasena;       // Contraseña
    private String apellido;         // Apellido del usuario
    private String nombre;           // Nombre del usuario
    private String email;            // Correo electrónico
    private String tel;              // Teléfono

    // Constructor
    public usuario(int idUsuario, String usuario, int idRol, String contrasena,
                   String apellido, String nombre, String email, String tel) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.idRol = idRol;
        this.contrasena = contrasena;
        this.apellido = apellido;
        this.nombre = nombre;
        this.email = email;
        this.tel = tel;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "usuario{" +
               "idUsuario=" + idUsuario +
               ", usuario='" + usuario + '\'' +
               ", idRol=" + idRol +
               ", contrasena='" + contrasena + '\'' +
               ", apellido='" + apellido + '\'' +
               ", nombre='" + nombre + '\'' +
               ", email='" + email + '\'' +
               ", tel='" + tel + '\'' +
               '}';
    }
}
