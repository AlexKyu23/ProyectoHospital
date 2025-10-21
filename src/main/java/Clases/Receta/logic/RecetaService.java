package Clases.Receta.logic;

import Datos.RecetaDAO;
import java.util.List;

public class RecetaService {
    private static RecetaService instance;
    private RecetaDAO dao;

    private RecetaService() {
        this.dao = RecetaDAO.instance();
    }

    public static RecetaService instance() {
        if (instance == null) instance = new RecetaService();
        return instance;
    }

    public void create(Receta r) throws Exception {
        dao.guardar(r);
    }


    public Receta readById(String id) throws Exception {
        return dao.buscarPorId(id);
    }

    public List<Receta> findByPaciente(String pacienteId) throws Exception {
        return dao.listar().stream()
                .filter(r -> r.getPacienteId().equalsIgnoreCase(pacienteId))
                .toList();
    }

    public List<Receta> findAll() throws Exception {
        return dao.listar();
    }

    public void cambiarEstado(String recetaId, EstadoReceta nuevoEstado) throws Exception {
        Receta r = dao.buscarPorId(recetaId);
        if (r != null) {
            r.setEstado(nuevoEstado);
            dao.actualizar(r);
        }
    }
}
