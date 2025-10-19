package Clases.Medico.logic;

import Clases.Medico.data.ListaMedicos;
import Clases.DatosIniciales;
import Datos.MedicoDAO;

import java.util.List;

public class MedicoService {
    private static MedicoService instance;
    private final MedicoDAO medicoDAO;

    private MedicoService() {
        medicoDAO = MedicoDAO.instance();
    }

    public static MedicoService instance() {
        if (instance == null) instance = new MedicoService();
        return instance;
    }

    public void create(Medico m) throws Exception {
        if (medicoDAO.buscarPorId(m.getId()) != null)
            throw new Exception("El medico ya existe");
        medicoDAO.guardar(m);
    }

    public Medico read(String idB) throws Exception {
        return medicoDAO.buscarPorId(idB);
    }

    public void update(Medico m) throws Exception {
        medicoDAO.actualizar(m);
    }

    public Medico readById(String id) throws Exception {
        return medicoDAO.buscarPorId(id);
    }

    public Medico readByNombre(String nombre) throws Exception {
        return medicoDAO.buscarPorNombre(nombre);
    }

    public void delete(String id) throws Exception {
        medicoDAO.borrar(medicoDAO.buscarPorId(id));
    }

    public List<Medico> findAll() throws Exception {
        return medicoDAO.listar();
    }
}
