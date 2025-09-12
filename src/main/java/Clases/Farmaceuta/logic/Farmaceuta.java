package Clases.Farmaceuta.logic;

import Clases.Trabajador.Trabajador;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "farmaceuta")
public class Farmaceuta extends Trabajador {
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
