package Clases.Trabajador;

import jakarta.xml.bind.annotation.XmlTransient;

public class Trabajador {
    protected String id;
    protected String nombre;
    protected String clave;

    public Trabajador() {}

    public Trabajador(String id, String nombre, String clave) {
        this.id = id;
        this.nombre = nombre;
        this.clave = clave;
    }

    // 🔹 JAXB usará estos getters como propiedades
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getClave() { return clave; }

    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setClave(String clave) { this.clave = clave; }
}
