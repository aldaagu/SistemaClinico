package src;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableColumn;

public class GrillaPrestadores {
   private Integer id_prestador;
    private String razon_social;
    private String cuit;
    private String direccion;
    private String telefono;
    private String fax;
    private String email;
    private Boolean gravado;
    private Boolean rural;
    private Boolean monotributo;
    private Boolean familia;
    private Boolean enactividad;
    private Boolean clasic;

 
    // Constructor
 

    
   public GrillaPrestadores( Integer id_prestador, String razon_social, String cuit, String direccion, 
                             String telefono, String fax, String email, Boolean gravado, 
                             Boolean rural, Boolean monotributo,Boolean familia,
                             Boolean enactividad, Boolean clasic) {
        this.id_prestador=id_prestador;
        this.razon_social = razon_social;
        this.cuit = cuit;
        this.direccion =direccion;
        this.telefono = telefono;
        this.fax = fax;
        this.email = email;
        this.gravado = gravado;
        this.rural = rural;
        this.monotributo = monotributo;
        this.familia = familia;
        this.enactividad = enactividad;
        this.clasic = clasic;
    }
    
    
    // Getters
    public int getId_prestador() {
        return id_prestador;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public String getCuit() {
        return cuit;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }

    public boolean isGravado() {
        return gravado;
    }

    public boolean isRural() {
        return rural;
    }

    public boolean isMonotributo() {
        return monotributo;
    }

    public boolean isFamilia() {
        return familia;
    }

    public boolean isEnactividad() {
        return enactividad;
    }

    public boolean isClasic() {
        return clasic;
    }

    // Setters
    public void setId_prestador(int id_prestador) {
        this.id_prestador=id_prestador;
    }

    public void setrazon_social(String razon_social) {
        this.razon_social=razon_social;
    }

    public void setcuit(String cuit) {
        this.cuit=cuit;
    }

    public void setdireccion(String direccion) {
        this.direccion=direccion;
    }

    public void settelefono(String telefono) {
        this.telefono=telefono;
    }

    public void setfax(String fax) {
        this.fax=fax;
    }

    public void setemail(String email) {
        this.email=email;
    }

    public void setgravado(boolean gravado) {
        this.gravado=gravado;
    }

    public void setrural(boolean rural) {
        this.rural=rural;
    }

    public void setmonotributo(boolean monotributo) {
        this.monotributo=monotributo;
    }

    public void setfamilia(boolean familia) {
        this.familia=familia;
    }

    public void setenactividad(boolean enactividad) {
        this.enactividad=enactividad;
    }

    public void setclasic(boolean clasic) {
        this.clasic=clasic;
    }

    // Properties (Métodos necesarios para usar PropertyValueFactory)
   // public IntegerProperty id_prestadorProperty() {
     //   return id_prestador;
   // }


    // toString
    @Override
public String toString() {
    return "GrillaPrestadores{" +
            "id_prestador=" + id_prestador +
            ", razon_social='" + (razon_social != null ? razon_social : "") + '\'' +
            ", cuit='" + (cuit != null ? cuit : "") + '\'' +
            ", direccion='" + (direccion != null ? direccion : "") + '\'' +
            ", telefono='" + (telefono != null ? telefono : "") + '\'' +
            ", fax='" + (fax != null ? fax : "") + '\'' +
            ", email='" + (email != null ? email : "") + '\'' +
            ", gravado=" + gravado +
            ", rural=" + rural +
            ", monotributo=" + monotributo +
            ", familia=" + familia +
            ", enactividad=" + enactividad +
            ", clasicYAcord=" + clasic +
            '}';
}
}

