package Clases.Paciente.presentation;

import Clases.AbstractModel;
import Clases.Paciente.logic.Paciente;

import java.util.ArrayList;
import java.util.List;

public class PacienteModel extends AbstractModel {
    private Paciente current;
    private List<Paciente> pacientes;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public PacienteModel() {
        this.current = null;
        this.pacientes = new ArrayList<>();
    }

    public Paciente getCurrent() {
        return current;
    }

    public void setCurrent(Paciente current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
        firePropertyChange(LIST);
    }

    public void addPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
        firePropertyChange(LIST);
    }

    public void deletePaciente(String id) {
        this.pacientes.removeIf(p -> p.getId().equals(id));
        firePropertyChange(LIST);
    }

    public Paciente findById(String id) {
        return this.pacientes.stream()
                .filter(p -> p.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public Paciente findByNombre(String nombre) {
        return this.pacientes.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }
}
