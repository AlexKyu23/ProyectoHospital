package logic;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement(name = "ItemReceta")
public class ItemReceta implements Serializable {
    private String itemRecetaId;
    private String recetaId;
    private int medicamentoCodigo;
    private String descripcion;
    private int cantidad;
    private String indicaciones;
    private int duracionDias;

    public ItemReceta() {}

    public ItemReceta(String itemRecetaId, String recetaId, int medicamentoCodigo, String descripcion, int cantidad, String indicaciones, int duracionDias) {
        this.itemRecetaId = itemRecetaId;
        this.recetaId = recetaId;
        this.medicamentoCodigo = medicamentoCodigo;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.indicaciones = indicaciones;
        this.duracionDias = duracionDias;
    }

    public ItemReceta(String itemRecetaId,int medicamentoCodigo, String descripcion, int cantidad, String indicaciones, int duracionDias) {
        this.itemRecetaId = itemRecetaId;
        this.medicamentoCodigo = medicamentoCodigo;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.indicaciones = indicaciones;
        this.duracionDias = duracionDias;
    }

    @XmlElement
    public String getItemRecetaId() { return itemRecetaId; }
    public void setItemRecetaId(String itemRecetaId) { this.itemRecetaId = itemRecetaId; }

    @XmlElement
    public String getRecetaId() { return recetaId; }
    public void setRecetaId(String recetaId) { this.recetaId = recetaId; }

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