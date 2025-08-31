package Clases.Medicamento;

import Clases.Receta.Receta;

public class Medicamento {

    private String descripcion;
    private int codigo;
    private Receta receta;


    public Medicamento(String descripcion, int codigo, Receta receta) {
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.receta = receta;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }
    @Override
    public String toString() {
        return "Medicamento{" +
                "descripcion='" + descripcion + '\'' +
                ", codigo=" + codigo +
                ", receta=" + (receta != null ? receta.toString() : "null") +
                '}';
    }
}
