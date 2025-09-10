package Clases.Usuario.logic;

public class Sesion {
    private static Usuario usuario;

    public static void setUsuario(Usuario u) { usuario = u; }
    public static Usuario getUsuario() { return usuario; }
    public static void logout() { usuario = null; }
    public static boolean isLoggedIn() { return usuario != null; }
}
