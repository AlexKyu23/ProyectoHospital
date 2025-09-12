package Clases.Medicamento.logic;

import Clases.Medicamento.data.catalogoMedicamentos;

import java.util.List;

public class MedicamentoService {
    private static MedicamentoService instance;
    private catalogoMedicamentos catalogo;

    private MedicamentoService() {
        catalogo = new catalogoMedicamentos();
        catalogo.cargar(); // ← carga desde XML

        // Precarga opcional si el archivo está vacío
        if (catalogo.consulta().isEmpty()) {
            catalogo.inclusion(new Medicamento("Paracetamol", "Analgésico y antipirético", 101));
            catalogo.inclusion(new Medicamento("Amoxicilina", "Antibiótico de amplio espectro", 102));
            catalogo.guardar(); // ← guarda precarga
        }
    }

    public static MedicamentoService instance() {
        if (instance == null) instance = new MedicamentoService();
        return instance;
    }

    public void create(Medicamento m) throws Exception {
        if (readByCodigo(m.getCodigo()) != null)
            throw new Exception("Medicamento ya existe");
        catalogo.inclusion(m);
        catalogo.guardar(); // ← guarda en XML
    }

    public Medicamento readByCodigo(int codigo) {
        return catalogo.busquedaPorCodigo(codigo);
    }

    public Medicamento readByNombre(String nombre) {
        for (Medicamento m : catalogo.consulta()) {
            if (m.getNombre().equalsIgnoreCase(nombre)) return m;
        }
        return null;
    }

    public List<Medicamento> findAll() {
        return catalogo.consulta();
    }

    public void delete(int codigo) {
        catalogo.borrado(codigo);
        catalogo.guardar(); // ← guarda en XML
    }

    public void update(Medicamento nuevo) {
        catalogo.modificacion(nuevo.getCodigo(), nuevo);
        catalogo.guardar(); // ← guarda en XML
    }
    public void guardar() {
        catalogo.guardar();
    }

}
