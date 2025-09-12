package Clases.Medico.logic;

import Clases.Medico.data.ListaMedicos;

import java.util.List;

public class MedicoService {
    private static MedicoService instance;
    private ListaMedicos lista;

    private MedicoService() {
        lista = new ListaMedicos();

        // Precarga opcional
        lista.inclusion(new Medico("MED-001", "Dr. Salas", "MED-001", "Cardiología"));
        lista.inclusion(new Medico("MED-002", "Dra. Vargas", "MED-002", "Pediatría"));
    }

    public static MedicoService instance() {
        if (instance == null) instance = new MedicoService();
        return instance;
    }

    public void create(Medico m) throws Exception {
        if (readById(m.getId()) != null)
            throw new Exception("Médico ya existe");
        m.setClave(m.getId()); // clave = id al crear
        lista.inclusion(m);
    }

    public Medico readById(String id) {
        return lista.busquedaPorId(id);
    }

    public Medico readByNombre(String nombre) {
        return lista.busquedaPorNombre(nombre);
    }

    public void delete(String id) {
        lista.borrado(id);
    }

    public List<Medico> findAll() {
        return lista.consulta();
    }

    public void update(Medico m) {
        lista.modificacion(m);
    }
}
