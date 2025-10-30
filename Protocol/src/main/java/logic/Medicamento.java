package logic;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;


@XmlRootElement(name = "medicamento")
public class Medicamento implements Serializable {
    private String nombre;
    private String descripcion;
    private String codigo;


    public Medicamento() {}

    public Medicamento(String nombre, String descripcion, String codigo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigo = codigo;

    }

    @XmlElement
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlElement
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlElement
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}


    @Override
    public String toString() {
        return "Medicamento{" +
                "descripcion='" + descripcion + '\'' +
                ", codigo=" + codigo;

    }
}
