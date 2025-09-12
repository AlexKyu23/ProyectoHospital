package Clases.Farmaceuta.logic;

import Clases.Farmaceuta.data.FarmaceutaData;

import java.util.List;

public class FarmaceutaService {
    private static FarmaceutaService instance;
    private FarmaceutaData data;

    private FarmaceutaService() {
        data = new FarmaceutaData();
    }

    public static FarmaceutaService instance() {
        if (instance == null) instance = new FarmaceutaService();
        return instance;
    }

    public void create(Farmaceuta f) throws Exception {
        if (readById(f.getId()) != null)
            throw new Exception("Farmaceuta ya existe");
        data.getFarmaceutas().add(f);
    }

    public Farmaceuta readById(String id) {
        return data.getFarmaceutas().stream()
                .filter(f -> f.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public Farmaceuta readByNombre(String nombre) {
        return data.getFarmaceutas().stream()
                .filter(f -> f.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    public void delete(String id) {
        data.getFarmaceutas().removeIf(f -> f.getId().equalsIgnoreCase(id));
    }

    public List<Farmaceuta> findAll() {
        return data.getFarmaceutas();
    }
}
