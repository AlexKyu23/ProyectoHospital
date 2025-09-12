package Clases.Paciente.logic;

import Clases.Paciente.data.PacienteData;

import java.util.List;

public class PacienteService {
    private static PacienteService instance;
    private PacienteData data;

    private PacienteService() {
        data = new PacienteData();
    }

    public static PacienteService instance() {
        if (instance == null) instance = new PacienteService();
        return instance;
    }

    public void create(Paciente p) throws Exception {
        if (readById(p.getId()) != null)
            throw new Exception("Paciente ya existe");
        data.getPacientes().add(p);
    }

    public Paciente readById(String id) {
        return data.getPacientes().stream()
                .filter(p -> p.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public Paciente readByNombre(String nombre) {
        return data.getPacientes().stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    public void delete(String id) {
        data.getPacientes().removeIf(p -> p.getId().equalsIgnoreCase(id));
    }

    public List<Paciente> findAll() {
        return data.getPacientes();
    }
}
