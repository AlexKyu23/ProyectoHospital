package Clases.Paciente;

import Clases.Persona.Persona;

public class Paciente extends Persona {


    public Paciente(String id, String nombre) {
        super(id, nombre);
    }


    @Override
    public String toString() {
        return "Paciente{id='" + id + "', nombre='" + nombre + "'}";
    }
}
