package Clases.Usuario.data;

import Clases.Medico.logic.Medico;
import Clases.Usuario.logic.Usuario;

public class Data {

    private static Medico medicoPrueba = new Medico("MED-111", "David", "123","Pediatría");
    private static Usuario usuarioPrueba = new Usuario(medicoPrueba.getId(), medicoPrueba.getNombre(), medicoPrueba.getClave(), "MED");

    public static Usuario getUsuario() {
        return usuarioPrueba;
    }

}
