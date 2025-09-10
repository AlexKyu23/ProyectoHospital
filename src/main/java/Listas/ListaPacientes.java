package Listas;

import Clases.Paciente.logic.Paciente;

import java.util.ArrayList;
import java.util.List;

public class ListaPacientes {
    private List<Paciente> pacientes;

    public ListaPacientes() {
        pacientes = new ArrayList<>();
    }

    // 🔹 Agregar paciente
    public void inclusion(Paciente paciente) {
        pacientes.add(paciente);
    }

    // 🔹 Consultar todos
    public List<Paciente> consulta() {
        return pacientes;
    }

    // 🔹 Buscar por ID
    public Paciente busquedaPorId(String id) {
        for (Paciente p : pacientes) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    // 🔹 Buscar por nombre
    public Paciente busquedaPorNombre(String nombre) {
        for (Paciente p : pacientes) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    // 🔹 Modificar paciente existente
    public void modificacion(Paciente paciente) {
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getId().equalsIgnoreCase(paciente.getId())) {
                pacientes.set(i, paciente);
                return;
            }
        }
    }

    // 🔹 Borrar por ID
    public void borrado(String id) {
        pacientes.removeIf(p -> p.getId().equalsIgnoreCase(id));
    }
}
