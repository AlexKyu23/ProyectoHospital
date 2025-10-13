package Clases.Usuario.logic;

import Clases.Usuario.data.ListaUsuarios;
import Clases.DatosIniciales;
import java.util.List;

public class UsuarioService {
    private static UsuarioService instance;
    private ListaUsuarios lista;

    private UsuarioService() {
        lista = DatosIniciales.listaUsuarios;
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
        DatosIniciales.guardarTodo();
    }

    public void update(Usuario u) {
        lista.modificacion(u);
        DatosIniciales.guardarTodo();
    }

    public void delete(String id) {
        lista.borrado(id);
        DatosIniciales.guardarTodo();
    }

    public List<Usuario> findAll() {
        return lista.getUsuarios();
    }
}
