package Clases.Medico.logic;

import Clases.Medico.data.MedicoData;

import java.util.List;

public class MedicoService {
    private static MedicoService instance;
    private MedicoData data;

    private MedicoService() {
        data = new MedicoData();
    }

    public static MedicoService instance() {
        if (instance == null) instance = new MedicoService();
        return instance;
    }

    public void create(Medico m) throws Exception {
        if (readById(m.getId()) != null)
            throw new Exception("Médico ya existe");
        data.getMedicos().add(m);
    }

    public Medico readById(String id) {
        return data.getMedicos().stream()
                .filter(m -> m.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public Medico readByNombre(String nombre) {
        return data.getMedicos().stream()
                .filter(m -> m.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    public void delete(String id) {
        data.getMedicos().removeIf(m -> m.getId().equalsIgnoreCase(id));
    }

    public List<Medico> findAll() {
        return data.getMedicos();
    }
}
