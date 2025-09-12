package Clases.Medico.logic;

import Clases.Medico.data.ListaMedicos;
import java.util.List;

public class MedicoService {
    private static MedicoService instance;
    private ListaMedicos lista;

    private MedicoService() {
        lista = new ListaMedicos();
        System.out.println("⏳ Cargando médicos desde XML...");
        lista.cargar();

        if (lista.consulta().isEmpty()) {
            System.out.println("⚠️ Lista de médicos vacía. Precargando...");
            lista.inclusion(new Medico("MED-001", "Dr. Salas", "MED-001", "Cardiología"));
            lista.inclusion(new Medico("MED-002", "Dra. Vargas", "MED-002", "Pediatría"));
            lista.guardar();
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
        lista.guardar();
        System.out.println("🆕 Médico creado: " + m.getNombre() + " (" + m.getId() + ")");
    }

    public Medico readById(String id) {
        return lista.busquedaPorId(id);
    }

    public Medico readByNombre(String nombre) {
        return lista.busquedaPorNombre(nombre);
    }

    public void delete(String id) {
        lista.borrado(id);
        lista.guardar();
        System.out.println("🗑️ Médico eliminado: " + id);
    }

    public List<Medico> findAll() {
        List<Medico> actual = lista.consulta();
        System.out.println("📋 Consulta de médicos: " + actual.size());
        return actual;
    }

    public void update(Medico m) {
        lista.modificacion(m);
        lista.guardar();
        System.out.println("✏️ Médico actualizado: " + m.getNombre() + " (" + m.getId() + ")");
    }
    public void guardar() {
        lista.guardar();
    }

}
