package Clases.Receta.logic;

import Clases.Receta.Data.RecetaData;

import java.util.List;
import java.util.stream.Collectors;


public class RecetaService {
    private static RecetaService instance;
    private RecetaData data;

    private RecetaService() {
        data = new RecetaData();
    }

    public static RecetaService instance() {
        if (instance == null) instance = new RecetaService();
        return instance;
    }

    public void create(Receta r) throws Exception {
        if (readById(r.getId()) != null)
            throw new Exception("Receta ya existe");
        data.getRecetas().add(r);
    }

    public Receta readById(String id) {
        return data.getRecetas().stream()
                .filter(r -> r.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public List<Receta> findByPaciente(String pacienteId) {
        return data.getRecetas().stream()
                .filter(r -> r.getPacienteId().equalsIgnoreCase(pacienteId))
                .collect(Collectors.toList());
    }

    public List<Receta> findAll() {
        return data.getRecetas();
    }

    public void cambiarEstado(String recetaId, EstadoReceta nuevoEstado) {
        Receta r = readById(recetaId);
        if (r != null) r.setEstado(nuevoEstado);
    }
}
