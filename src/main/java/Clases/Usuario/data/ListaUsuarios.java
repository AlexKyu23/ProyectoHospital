package Clases.Usuario.data;

import Clases.Usuario.logic.Usuario;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "listaUsuarios")
public class ListaUsuarios {
    private List<Usuario> usuarios;
    private static final File ARCHIVO = new File("usuarios.xml");

    public ListaUsuarios() {
        usuarios = new ArrayList<>();
    }

    @XmlElement(name = "usuario")
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
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

    public void cargar() {
        if (ARCHIVO.exists()) {
            try {
                ListaUsuarios cargada = Clases.Usuario.data.XmlPersister.load(ListaUsuarios.class, ARCHIVO);
                this.usuarios = cargada.getUsuarios();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            usuarios.add(new Usuario("med01", "Dr. House", "1234", "MED"));
            usuarios.add(new Usuario("far01", "Farmacia Ana", "5678", "FAR"));
            usuarios.add(new Usuario("adm01", "Administrador", "admin", "ADM"));
            guardar();
        }
    }

    public void guardar() {
        try {
            Clases.Usuario.data.XmlPersister.save(this, ARCHIVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

