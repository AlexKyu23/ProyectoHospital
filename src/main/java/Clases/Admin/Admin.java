package Clases.Admin;


import Clases.Trabajador.Trabajador;

public class Admin extends Trabajador {

    public Admin(String id, String nombre, String clave) {
        super(id, nombre, clave);
    }

    @Override
    public String toString() {
        return "Admin{id='" + id + "', nombre='" + nombre + "', clave='" + getClave() + "'}";
    }
}

