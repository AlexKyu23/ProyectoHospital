package logic;

import java.io.Serializable;

public class Admin extends Trabajador implements Serializable {

    public Admin(String id, String nombre, String clave) {
        super(id, nombre, clave);
    }

    @Override
    public String toString() {
        return "Admin{id='" + id + "', nombre='" + nombre + "', clave='" + getClave() + "'}";
    }
}

