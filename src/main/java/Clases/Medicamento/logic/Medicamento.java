package Clases.Medicamento.logic;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "medicamento")
public class Medicamento {
    private String nombre;
    private String descripcion;
    private int codigo;


    public Medicamento() {}

    public Medicamento(String nombre, String descripcion, int codigo) {
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
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
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
