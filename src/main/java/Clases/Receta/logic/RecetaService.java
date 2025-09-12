package Clases.Receta.logic;

import Clases.Receta.Data.historicoRecetas;

import java.util.List;
import java.util.stream.Collectors;

public class RecetaService {
    private static RecetaService instance;
    private historicoRecetas historico;

    private RecetaService() {
        historico = new historicoRecetas();

        // Precarga opcional
        // historico.inclusion(new Receta(...));
    }

    public static RecetaService instance() {
        if (instance == null) instance = new RecetaService();
        return instance;
    }

    public void create(Receta r) throws Exception {
        if (readById(r.getId()) != null)
            throw new Exception("Receta ya existe");
        historico.inclusion(r);
    }

    public Receta readById(String id) {
        return historico.consulta().stream()
                .filter(r -> r.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public List<Receta> findByPaciente(String pacienteId) {
        return historico.consulta().stream()
                .filter(r -> r.getPacienteId().equalsIgnoreCase(pacienteId))
                .collect(Collectors.toList());
    }

    public List<Receta> findAll() {
        return historico.consulta();
    }

    public void cambiarEstado(String recetaId, EstadoReceta nuevoEstado) {
        Receta r = readById(recetaId);
        if (r != null) r.setEstado(nuevoEstado);
    }
}
