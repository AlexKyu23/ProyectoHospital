package Clases.Usuario.data;

import Clases.Usuario.logic.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private static List<Usuario> usuarios = new ArrayList<>();

    static {
        usuarios.add(new Usuario("001", "SuperAdmin", "1234", "ADM"));
        usuarios.add(new Usuario("MED-111", "David", "123", "MED"));
        usuarios.add(new Usuario("FARM-01", "Carla Jiménez", "FARM-01", "FAR"));
    }

    public static List<Usuario> getUsuarios() {
        return usuarios;
    }

    public static void addUsuario(Usuario u) {
        usuarios.add(u);
    }
}
