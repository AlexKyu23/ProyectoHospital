package Clases.Despacho.presentation;

import Clases.AbstractModel;
import Clases.Farmaceuta.logic.Farmaceuta;
import Clases.Medico.logic.Medico;
import Clases.Paciente.logic.Paciente;
import Clases.Receta.logic.ItemReceta;
import Clases.Receta.logic.Receta;

import java.util.ArrayList;
import java.util.List;

public class DespachoModel extends AbstractModel {
    private Paciente paciente;
    private Farmaceuta farmaceuta;
    private List<Receta> recetas;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public DespachoModel() {
        this.recetas = new ArrayList<>();
    }

    public List<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
        firePropertyChange("recetas");
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
        firePropertyChange(CURRENT);
    }

    public Farmaceuta getFarmaceuta() {
        return farmaceuta;
    }

    public void setFarmaceuta(Farmaceuta farmaceuta) {
        this.farmaceuta = farmaceuta;
        firePropertyChange(CURRENT);
    }

    public void agregarReceta(Receta receta) {
        recetas.add(receta);
        firePropertyChange(LIST);
    }

    public void eliminarReceta(Receta receta) {
        recetas.remove(receta);
        firePropertyChange(LIST);
    }

    public void limpiarRecetas() {
        recetas.clear();
        firePropertyChange(LIST);
    }
}