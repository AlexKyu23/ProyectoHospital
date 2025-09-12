package Clases.Usuario.logic;

import Clases.Usuario.data.Data;

public class Service {
    private static Service instance;

    public static Service instance() {
        if (instance == null) instance = new Service();
        return instance;
    }

    public Usuario read(Usuario u) throws Exception {
        return Data.getUsuarios().stream()
                .filter(i -> i.getId().equalsIgnoreCase(u.getId()))
                .findFirst()
                .orElseThrow(() -> new Exception("Usuario no existe"));
    }


}
