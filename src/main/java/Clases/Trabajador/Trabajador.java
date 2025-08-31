package Clases.Trabajador;

import Clases.Persona.Persona;

public class Trabajador extends Persona {

    private String clave;

    public Trabajador(String id, String nombre, String clave) {
        super(id, nombre);
        this.clave = clave;
    }


    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String toString() {//Cualquier cosa esto  es solo para ver si
                             //se hacen bien no creo sea buena idea enseñar claves xD
        return "Trabajador{id='" + id + "', nombre='" + nombre + "', clave='" + clave + "'}";
    }
}