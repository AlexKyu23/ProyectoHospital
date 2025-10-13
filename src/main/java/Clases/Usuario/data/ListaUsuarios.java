package Clases.Usuario.data;

import Clases.Usuario.logic.Usuario;
import java.util.ArrayList;
import java.util.List;

public class ListaUsuarios {
    private List<Usuario> usuarios;

    public ListaUsuarios() {
        usuarios = new ArrayList<>();
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios != null ? usuarios : new ArrayList<>();
    }

    public void inclusion(Usuario u) {
        usuarios.add(u);
    }

    public Usuario busquedaPorId(String id) {
        return usuarios.stream()
                .filter(u -> u.getId().equalsIgnoreCase(id))
                .findFirst().orElse(null);
    }

    public void modificacion(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equalsIgnoreCase(usuario.getId())) {
                usuarios.set(i, usuario);
                return;
            }
        }
    }

    public void borrado(String id) {
        usuarios.removeIf(u -> u.getId().equalsIgnoreCase(id));
    }
}
