package Clases.Medico;

import Clases.Trabajador.Trabajador;

public class Medico extends Trabajador {
    private String especialidad;

    public Medico(String id, String nombre, String clave, String especialidad) {
        super(id, nombre, clave);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        return "Medico{id='" + id + "', nombre='" + nombre +
                "', clave='" + getClave() + "', especialidad='" + especialidad + "'}";
    }
}