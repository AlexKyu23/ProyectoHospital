package Clases.Trabajador;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.PROPERTY)
public abstract class Trabajador {
    protected String id;
    protected String nombre;
    protected String clave;

    public Trabajador() {}

    public Trabajador(String id, String nombre, String clave) {
        this.id = id;
        this.nombre = nombre;
        this.clave = clave;
    }

    @XmlElement
    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    @XmlElement
    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    @XmlElement
    public String getClave() { return clave; }

    public void setClave(String clave) { this.clave = clave; }
}
