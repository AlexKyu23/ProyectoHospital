package Clases.Medico.logic;

import Clases.Medico.data.ListaMedicos;
import Clases.DatosIniciales;
import java.util.List;

public class MedicoService {
    private static MedicoService instance;
    private ListaMedicos lista;

    private MedicoService() {
        lista = DatosIniciales.listaMedicos;

        if (lista.consulta().isEmpty()) {
            System.out.println("⚠️ Lista de médicos vacía. Precargando...");
            lista.inclusion(new Medico("MED-001", "Dr. Salas", "MED-001", "Cardiología"));
            lista.inclusion(new Medico("MED-002", "Dra. Vargas", "MED-002", "Pediatría"));
            DatosIniciales.guardarTodo();
            System.out.println("✅ Precarga de médicos guardada.");
        } else {
            System.out.println("✅ Médicos cargados: " + lista.consulta().size());
        }
    }

    public static MedicoService instance() {
        if (instance == null) instance = new MedicoService();
        return instance;
    }

    public void create(Medico m) throws Exception {
        if (readById(m.getId()) != null)
            throw new Exception("Médico ya existe");
        m.setClave(m.getId());
        lista.inclusion(m);
        DatosIniciales.guardarTodo();
    }

    public Medico readById(String id) {
        return lista.busquedaPorId(id);
    }

    public Medico readByNombre(String nombre) {
        return lista.busquedaPorNombre(nombre);
    }

    public void delete(String id) {
        lista.borrado(id);
        DatosIniciales.guardarTodo();
    }

    public List<Medico> findAll() {
        return lista.consulta();
    }

    public void update(Medico m) {
        lista.modificacion(m);
        DatosIniciales.guardarTodo();
    }
}
