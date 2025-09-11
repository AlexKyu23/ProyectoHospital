package Clases.Usuario.logic;

import Clases.Usuario.data.Data;

public class Service {
    private static Service instance;

    public static Service instance() {
        if (instance == null) instance = new Service();
        return instance;
    }

    public Usuario read(Usuario u) throws Exception {
        Usuario result = Data.getUsuario();/*.stream()                  //Busca el usuario en la lista guardada, pero no tenemos la lista aún
                .filter(i -> i.getId().equals(e.getId()))
                .findFirst()
                .orElse(null);*/
        if (result != null) {
            return result;
        } else {
            throw new Exception("Persona no existe");
        }
    }

}
