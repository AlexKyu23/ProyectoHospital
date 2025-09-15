package Clases.Receta.logic;

import Clases.Receta.Data.RepositorioRecetas;

import java.util.List;
import java.util.stream.Collectors;

public class RecetaService {
    private static RecetaService instance;
    private RepositorioRecetas repositorio;

    private RecetaService() {
        repositorio = new RepositorioRecetas();
        RepositorioRecetas.cargar(); // Carga inicial
    }

    public static RecetaService instance() {
        if (instance == null) instance = new RecetaService();
        return instance;
    }

    public void create(Receta r) throws Exception {
        if (readById(r.getId()) != null)
            throw new Exception("Receta ya existe");
        RepositorioRecetas.agregar(r);
        RepositorioRecetas.guardar();
    }

    public Receta readById(String id) {
        return RepositorioRecetas.getRecetas().stream()
                .filter(r -> r.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public List<Receta> findByPaciente(String pacienteId) {
        return RepositorioRecetas.getRecetas().stream()
                .filter(r -> r.getPacienteId().equalsIgnoreCase(pacienteId))
                .collect(Collectors.toList());
    }

    public List<Receta> findAll() {
        return RepositorioRecetas.getRecetas();
    }

    public void cambiarEstado(String recetaId, EstadoReceta nuevoEstado) {
        Receta r = readById(recetaId);
        if (r != null) {
            r.setEstado(nuevoEstado);
            RepositorioRecetas.guardar(); // Persistir cambio
        }
    }
}