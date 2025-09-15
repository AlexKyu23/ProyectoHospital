package Clases.Receta.logic;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "itemReceta")
public class ItemReceta {
    private int medicamentoCodigo;
    private String descripcion;
    private int cantidad;
    private String indicaciones;
    private int duracionDias;

    public ItemReceta() {}

    public ItemReceta(int medicamentoCodigo, String descripcion, int cantidad, String indicaciones, int duracionDias) {
        this.medicamentoCodigo = medicamentoCodigo;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.indicaciones = indicaciones;
        this.duracionDias = duracionDias;
    }

    @XmlElement
    public int getMedicamentoCodigo() { return medicamentoCodigo; }
    public void setMedicamentoCodigo(int medicamentoCodigo) { this.medicamentoCodigo = medicamentoCodigo; }

    @XmlElement
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @XmlElement
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    @XmlElement
    public String getIndicaciones() { return indicaciones; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }

    @XmlElement
    public int getDuracionDias() { return duracionDias; }
    public void setDuracionDias(int duracionDias) { this.duracionDias = duracionDias; }
}