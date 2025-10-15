package Clases.Paciente.logic;

import Datos.PacienteDAO;

import java.util.List;

public class PacienteService {
    private static PacienteService instance;
    private final PacienteDAO dao;

    private PacienteService() {
        dao = new PacienteDAO();
    }

    public static PacienteService instance() {
        if (instance == null) instance = new PacienteService();
        return instance;
    }

    public void create(Paciente p) throws Exception {
        if (readById(p.getId()) != null)
            throw new Exception("Paciente ya existe");
        dao.guardar(p);
    }

    public Paciente readById(String id) {
        return dao.buscarPorId(id);
    }

    public Paciente readByNombre(String nombre) {
        return dao.buscarPorNombre(nombre);
    }

    public void delete(String id) {
        dao.borrar(id);
    }

    public List<Paciente> findAll() {
        return dao.listar();
    }

    public void update(Paciente p) {
        dao.actualizar(p);
    }
}
