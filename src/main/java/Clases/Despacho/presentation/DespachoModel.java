package Clases.Despacho.presentation;

import Clases.AbstractModel;
import Clases.Receta.logic.Receta;

import java.util.List;

public class DespachoModel extends AbstractModel {
    private List<Receta> recetas;

    public List<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
        firePropertyChange("recetas");
    }
}
