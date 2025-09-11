package Clases.Farmaceuta.logic;

import Clases.Trabajador.Trabajador;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "farmaceuta")
public class Farmaceuta extends Trabajador {

    public Farmaceuta() {
        super("", "", ""); // se puede inicializar con valores vacíos
    }

    public Farmaceuta(String id, String nombre, String clave) {
        super(id, nombre, clave);
    }

    @XmlElement
    @Override
    public String getId() {
        return super.getId();
    }

    @XmlElement
    @Override
    public String getNombre() {
        return super.getNombre();
    }

    @XmlElement
    @Override
    public String getClave() {
        return super.getClave();
    }

    @Override
    public String toString() {
        return "Farmaceuta{id='" + id + "', nombre='" + nombre + "', clave='" + getClave() + "'}";
    }
}
