package Clases.Persona.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.PROPERTY)
public abstract class Persona {
    protected String id;
    protected String nombre;

    public Persona() {}

    public Persona(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @XmlElement
    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    @XmlElement
    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() {
        return "Persona{id='" + id + "', nombre='" + nombre + "'}";
    }
}
