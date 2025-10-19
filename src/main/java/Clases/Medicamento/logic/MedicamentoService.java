package Clases.Medicamento.logic;

import Clases.Medicamento.data.catalogoMedicamentos;
import Clases.DatosIniciales;
import Datos.MedicamentoDAO;

import java.util.List;

public class MedicamentoService {
    private static MedicamentoService instance;
    private final MedicamentoDAO medicamentoDAO;

    public MedicamentoService() {
        medicamentoDAO = MedicamentoDAO.instance();
    }

    public static MedicamentoService instance() {
        if (instance == null) instance = new MedicamentoService();
        return instance;
    }

    public void create(Medicamento m) throws Exception {
        if (medicamentoDAO.buscarPorCodigo(m.getCodigo()) != null)
            throw new Exception("El medico ya existe");
        medicamentoDAO.guardar(m);
    }

    public Medicamento read(int codigo) throws Exception {
        return medicamentoDAO.buscarPorCodigo(codigo);
    }

    public void update(Medicamento m) throws Exception {
        medicamentoDAO.actualizar(m);
    }

    public Medicamento readByCodigo(int codigo) throws Exception {
        return medicamentoDAO.buscarPorCodigo(codigo);
    }

    public Medicamento readByNombre(String nombre) throws Exception {
        return medicamentoDAO.buscarPorNombre(nombre);
    }

    public void delete(int codigo) throws Exception {
        medicamentoDAO.borrar(medicamentoDAO.buscarPorCodigo(codigo));
    }

    public List<Medicamento> findAll() throws Exception {
        return medicamentoDAO.listar();
    }
}
