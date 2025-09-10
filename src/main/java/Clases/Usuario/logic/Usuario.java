package Clases.Usuario.logic;

public class Usuario {
    private String id;
    private String nombre;
    private String clave;
    private String rol; // "ADM", "MED", "FAR"

    public Usuario() {}

    public Usuario(String id, String nombre, String clave, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.clave = clave;
        this.rol = rol;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getClave() { return clave; }
    public String getRol() { return rol; }

    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setClave(String clave) { this.clave = clave; }
    public void setRol(String rol) { this.rol = rol; }

    public boolean verificarClave(String intento) {
        return this.clave.equals(intento);
    }

    public void cambiarClave(String nuevaClave) {
        this.clave = nuevaClave;
    }
}
