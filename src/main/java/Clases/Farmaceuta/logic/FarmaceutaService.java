package Clases.Farmaceuta.logic;

import Clases.Farmaceuta.data.ListaFarmaceutas;
import java.util.List;

public class FarmaceutaService {
    private static FarmaceutaService instance;
    private ListaFarmaceutas lista;

    public static FarmaceutaService instance() {
        if (instance == null) instance = new FarmaceutaService();
        return instance;
    }

    private FarmaceutaService() {
        lista = new ListaFarmaceutas();
        System.out.println("⏳ Cargando farmaceutas desde XML...");
        lista.cargar();

        if (lista.consulta().isEmpty()) {
            System.out.println("⚠️ Lista de farmaceutas vacía. Precargando...");
            lista.inclusion(new Farmaceuta("FAR-001", "Carla Jiménez", "FAR-001"));
            lista.inclusion(new Farmaceuta("FAR-002", "Luis Mora", "FAR-002"));
            lista.guardar();
            System.out.println("✅ Precarga de farmaceutas guardada.");
        } else {
            System.out.println("✅ Farmaceutas cargados: " + lista.consulta().size());
        }
    }

    public List<Farmaceuta> findAll() {
        List<Farmaceuta> actual = lista.consulta();
        System.out.println("📋 Consulta de farmaceutas: " + actual.size());
        return actual;
    }

    public void create(Farmaceuta f) throws Exception {
        if (lista.busquedaPorId(f.getId()) != null)
            throw new Exception("Ya existe un farmaceuta con ese ID");
        f.setClave(f.getId());
        lista.inclusion(f);
        lista.guardar();
        System.out.println("🆕 Farmaceuta creado: " + f.getNombre() + " (" + f.getId() + ")");
    }

    public void delete(String id) {
        lista.borrado(id);
        lista.guardar();
        System.out.println("🗑️ Farmaceuta eliminado: " + id);
    }

    public Farmaceuta readById(String id) {
        return lista.busquedaPorId(id);
    }

    public Farmaceuta readByNombre(String nombre) {
        return lista.busquedaPorNombre(nombre);
    }

    public void update(Farmaceuta f) {
        lista.modificacion(f);
        lista.guardar();
        System.out.println("✏️ Farmaceuta actualizado: " + f.getNombre() + " (" + f.getId() + ")");
    }
    public void guardar() {
        lista.guardar();
    }

}
