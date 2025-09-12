package Clases.Receta.Data;

import Clases.Receta.logic.Receta;

import java.util.ArrayList;
import java.util.List;

public class RecetaData {
    private List<Receta> recetas;

    public RecetaData() {
        recetas = new ArrayList<>();
    }

    public List<Receta> getRecetas() {
        return recetas;
    }
}
