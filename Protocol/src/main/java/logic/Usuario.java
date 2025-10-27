package logic;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement(name = "usuario")
public class Usuario implements Serializable {
    private String id;
    private String nombre;
    private String clave;
    private String rol;

    public Usuario() {}

    public Usuario(String id, String nombre, String clave, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.clave = clave;
        this.rol = rol;
    }

    @XmlElement
    public String getId() { return id; }

    @XmlElement
    public String getNombre() { return nombre; }

    @XmlElement
    public String getClave() { return clave; }

    @XmlElement
    public String getRol() { return rol; }

    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setClave(String clave) { this.clave = clave; }
    public void setRol(String rol) { this.rol = rol; }

    public boolean verificarClave(String intento) {
        return this.clave.equals(intento);
    }

    public void cambiarClave(String nuevaClave) {
        this.clave = nuevaClave;
    }
}
