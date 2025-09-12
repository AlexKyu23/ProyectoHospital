package Clases.Usuario.data;

import Clases.Usuario.logic.Usuario;
import java.util.ArrayList;
import java.util.List;

public class Data {
    private static List<Usuario> usuarios = new ArrayList<>();

    static {
        usuarios.add(new Usuario("med01", "Dr. House", "1234", "MED"));
        usuarios.add(new Usuario("far01", "Farmacia Ana", "5678", "FAR"));
        usuarios.add(new Usuario("adm01", "Administrador", "admin", "ADM"));
    }

    public static List<Usuario> getUsuarios() {
        return usuarios;
    }
}
