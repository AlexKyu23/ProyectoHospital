package logic;

import java.io.Serializable;

public class Sesion  implements Serializable {
    private static Usuario usuario;

    public static void setUsuario(Usuario u) { usuario = u; }
    public static Usuario getUsuario() { return usuario; }
    public static void logout() { usuario = null; }
    public static boolean isLoggedIn() { return usuario != null; }
}
