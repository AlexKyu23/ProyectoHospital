package Clases.Paciente.logic;

import Clases.Paciente.data.ListaPacientes;
import java.time.LocalDate;
import java.util.List;

public class PacienteService {
    private static PacienteService instance;
    private ListaPacientes lista;

    private PacienteService() {
        lista = new ListaPacientes();
        System.out.println("⏳ Cargando pacientes desde XML...");
        lista.cargar();

        if (lista.consulta().isEmpty()) {
            System.out.println("⚠️ Lista de pacientes vacía. Precargando...");
            lista.inclusion(new Paciente("PAC-001", "Laura", "8888-1111", LocalDate.of(1990, 5, 12)));
            lista.inclusion(new Paciente("PAC-002", "Carlos", "8888-2222", LocalDate.of(1985, 8, 23)));
            lista.guardar();
            System.out.println("✅ Precarga de pacientes guardada.");
        } else {
            System.out.println("✅ Pacientes cargados: " + lista.consulta().size());
        }
    }

    public static PacienteService instance() {
        if (instance == null) instance = new PacienteService();
        return instance;
    }

    public void create(Paciente p) throws Exception {
        if (readById(p.getId()) != null)
            throw new Exception("Paciente ya existe");
        lista.inclusion(p);
        lista.guardar();
        System.out.println("🆕 Paciente creado: " + p.getNombre() + " (" + p.getId() + ")");
    }

    public Paciente readById(String id) {
        return lista.busquedaPorId(id);
    }

    public Paciente readByNombre(String nombre) {
        return lista.busquedaPorNombre(nombre);
    }

    public void delete(String id) {
        lista.borrado(id);
        lista.guardar();
        System.out.println("🗑️ Paciente eliminado: " + id);
    }

    public List<Paciente> findAll() {
        List<Paciente> actual = lista.consulta();
        System.out.println("📋 Consulta de pacientes: " + actual.size());
        return actual;
    }

    public void update(Paciente p) {
        lista.modificacion(p);
        lista.guardar();
        System.out.println("✏️ Paciente actualizado: " + p.getNombre() + " (" + p.getId() + ")");
    }
    public void guardar() {
        lista.guardar();
    }

}
