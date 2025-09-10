package Clases.Farmaceuta.logic;

import Clases.Trabajador.Trabajador;

public class Farmaceuta extends Trabajador {

    public Farmaceuta(String id, String nombre, String clave) {
        super(id, nombre, clave);
    }

    @Override
    public String toString() {
        return "Farmaceuta{id='" + id + "', nombre='" + nombre + "', clave='" + getClave() + "'}";
    }
}
