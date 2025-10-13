package Clases.Paciente.data;

import Clases.Paciente.logic.Paciente;
import java.util.ArrayList;
import java.util.List;

public class ListaPacientes {
    private List<Paciente> pacientes;

    public ListaPacientes() {
        pacientes = new ArrayList<>();
    }

    public List<Paciente> consulta() {
        return pacientes;
    }

    public void setList(List<Paciente> pacientes) {
        this.pacientes = pacientes != null ? pacientes : new ArrayList<>();
    }

    public void inclusion(Paciente paciente) {
        pacientes.add(paciente);
    }

    public Paciente busquedaPorId(String id) {
        return pacientes.stream()
                .filter(p -> p.getId().equalsIgnoreCase(id))
                .findFirst().orElse(null);
    }

    public Paciente busquedaPorNombre(String nombre) {
        return pacientes.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst().orElse(null);
    }

    public void modificacion(Paciente paciente) {
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getId().equalsIgnoreCase(paciente.getId())) {
                pacientes.set(i, paciente);
                return;
            }
        }
    }

    public void borrado(String id) {
        pacientes.removeIf(p -> p.getId().equalsIgnoreCase(id));
    }
}
