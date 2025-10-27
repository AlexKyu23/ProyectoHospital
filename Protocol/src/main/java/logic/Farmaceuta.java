package logic;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement(name = "farmaceuta")
public class Farmaceuta extends Trabajador implements Serializable {
    public Farmaceuta() {
        super("", "", "");
    }

    public Farmaceuta(String id, String nombre, String clave) {
        super(id, nombre, clave);
    }

    @Override
    public String toString() {
        return "Farmaceuta{id='" + id + "', nombre='" + nombre + "', clave='" + getClave() + "'}";
    }
}
