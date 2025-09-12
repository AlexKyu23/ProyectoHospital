package Clases.Medicamento.logic;

import Clases.Medicamento.data.MedicamentoData;

import java.util.List;

public class MedicamentoService {
    private static MedicamentoService instance;
    private MedicamentoData data;

    private MedicamentoService() {
        data = new MedicamentoData();
    }

    public static MedicamentoService instance() {
        if (instance == null) instance = new MedicamentoService();
        return instance;
    }

    public void create(Medicamento m) throws Exception {
        if (readByCodigo(m.getCodigo()) != null)
            throw new Exception("Medicamento ya existe");
        data.getMedicamentos().add(m);
    }

    public Medicamento readByCodigo(int codigo) {
        return data.getMedicamentos().stream()
                .filter(m -> m.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    public Medicamento readByNombre(String nombre) {
        return data.getMedicamentos().stream()
                .filter(m -> m.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    public void delete(int codigo) {
        data.getMedicamentos().removeIf(m -> m.getCodigo() == codigo);
    }

    public List<Medicamento> findAll() {
        return data.getMedicamentos();
    }
}
