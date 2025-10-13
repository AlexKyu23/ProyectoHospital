package Clases.Receta.logic;

import Clases.DatosIniciales;
import Clases.Receta.Data.RepositorioRecetas;

import java.util.List;
import java.util.stream.Collectors;

public class RecetaService {
    private static RecetaService instance;
    private RepositorioRecetas repositorio;

    private RecetaService() {
        this.repositorio = DatosIniciales.repositorioRecetas;
    }

    public static RecetaService instance() {
        if (instance == null) instance = new RecetaService();
        return instance;
    }

    public void create(Receta r) throws Exception {
        if (readById(r.getId()) != null)
            throw new Exception("Receta ya existe");
        repositorio.agregar(r);
        DatosIniciales.guardarTodo();
    }

    public Receta readById(String id) {
        return repositorio.getRecetas().stream()
                .filter(r -> r.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public List<Receta> findByPaciente(String pacienteId) {
        return repositorio.getRecetas().stream()
                .filter(r -> r.getPacienteId().equalsIgnoreCase(pacienteId))
                .collect(Collectors.toList());
    }

    public List<Receta> findAll() {
        return repositorio.getRecetas();
    }

    public void cambiarEstado(String recetaId, EstadoReceta nuevoEstado) {
        Receta r = readById(recetaId);
        if (r != null) {
            r.setEstado(nuevoEstado);
            DatosIniciales.guardarTodo();
        }
    }
}
