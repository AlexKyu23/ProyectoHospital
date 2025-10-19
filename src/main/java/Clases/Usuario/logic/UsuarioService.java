package Clases.Usuario.logic;

import Datos.UsuarioDAO;
import java.util.List;

public class UsuarioService {
    private static UsuarioService instance;
    private final UsuarioDAO dao;

    private UsuarioService() {
        dao = UsuarioDAO.instance();
        System.out.println("✅ Usuarios cargados desde SQL: " + dao.findAll().size());
    }

    public static UsuarioService instance() {
        if (instance == null) instance = new UsuarioService();
        return instance;
    }

    public Usuario readById(String id) {
        return dao.readById(id);
    }

    public boolean verificarClave(String id, String clave) {
        Usuario u = readById(id);
        return u != null && u.getClave().equals(clave);
    }

    public void create(Usuario u) throws Exception {
        if (readById(u.getId()) != null)
            throw new Exception("Ya existe un usuario con ese ID");
        dao.create(u);
    }

    public void update(Usuario u) {
        dao.update(u);
    }

    public void delete(String id) {
        dao.delete(id);
    }

    public List<Usuario> findAll() {
        return dao.findAll();
    }
}