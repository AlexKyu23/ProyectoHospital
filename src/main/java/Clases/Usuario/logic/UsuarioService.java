package Clases.Usuario.logic;

import Clases.Usuario.data.ListaUsuarios;

public class UsuarioService {
    private static UsuarioService instance;
    private ListaUsuarios lista;

    private UsuarioService() {
        lista = new ListaUsuarios();
        System.out.println("⏳ Cargando usuarios desde XML...");
        lista.cargar();
        System.out.println("✅ Usuarios cargados: " + lista.getUsuarios().size());
    }

    public static UsuarioService instance() {
        if (instance == null) instance = new UsuarioService();
        return instance;
    }

    public Usuario readById(String id) {
        return lista.busquedaPorId(id);
    }

    public boolean verificarClave(String id, String clave) {
        Usuario u = readById(id);
        return u != null && u.getClave().equals(clave);
    }

    public void create(Usuario u) throws Exception {
        if (readById(u.getId()) != null)
            throw new Exception("Ya existe un usuario con ese ID");
        lista.inclusion(u);
        lista.guardar();
        System.out.println("🆕 Usuario creado: " + u.getNombre() + " (" + u.getId() + ")");
    }

    public void update(Usuario u) {
        lista.modificacion(u);
        lista.guardar();
        System.out.println("✏️ Usuario actualizado: " + u.getNombre() + " (" + u.getId() + ")");
    }

    public void delete(String id) {
        lista.borrado(id);
        lista.guardar();
        System.out.println("🗑️ Usuario eliminado: " + id);
    }

    public void guardar() {
        lista.guardar();
        System.out.println("💾 Usuarios guardados manualmente.");
    }

    public ListaUsuarios getLista() {
        return lista;
    }
}
