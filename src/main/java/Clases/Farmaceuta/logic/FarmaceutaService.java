package Clases.Farmaceuta.logic;

import Datos.FarmaceutaDAO;

import java.util.List;

public class FarmaceutaService {
    private static FarmaceutaService instance;
    private final FarmaceutaDAO farmaceutaDAO;

    public FarmaceutaService() {
        farmaceutaDAO = FarmaceutaDAO.instance();
    }

    public static FarmaceutaService instance() {
        if (instance == null) instance = new FarmaceutaService();
        return instance;
    }

    public void create(Farmaceuta f) throws Exception {
        if (farmaceutaDAO.buscarPorId(f.getId()) != null)
            throw new Exception("El farmaceuta ya existe");
        farmaceutaDAO.guardar(f);
    }

    public Farmaceuta read(String idB) throws Exception {
        return farmaceutaDAO.buscarPorId(idB);
    }

    public void update(Farmaceuta f) throws Exception {
        farmaceutaDAO.actualizar(f);
    }

    public Farmaceuta readById(String id) throws Exception {
        return farmaceutaDAO.buscarPorId(id);
    }

    public Farmaceuta readByNombre(String nombre) throws Exception {
        return farmaceutaDAO.buscarPorNombre(nombre);
    }

    public void delete(String id) throws Exception {
        farmaceutaDAO.borrar(farmaceutaDAO.buscarPorId(id));
    }

    public List<Farmaceuta> findAll() throws Exception {
        return farmaceutaDAO.listar();
    }
}