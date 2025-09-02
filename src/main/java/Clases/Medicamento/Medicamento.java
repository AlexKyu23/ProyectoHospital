package Clases.Medicamento;


public class Medicamento {
    private String nombre;
    private String descripcion;
    private int codigo;



    public Medicamento(String nombre, String descripcion, int codigo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigo = codigo;

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
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Medicamento{" +
                "descripcion='" + descripcion + '\'' +
                ", codigo=" + codigo;

    }
}
