package Listas;

import Clases.Receta.Receta;

import java.util.ArrayList;
import java.util.List;

public class historicoRecetas {
    private List<Receta> recetas;

    public historicoRecetas() {
        recetas = new ArrayList<>();
    }


    public void inclusion(Receta receta) {
        recetas.add(receta);
    }


    public List<Receta> consulta() {
        return recetas;
    }

    //
    public Receta busqueda(int i) {
        if (i >= 0 && i < recetas.size()) {
            return recetas.get(i);
        }
        return null;
    }


    public Receta busqueda(Receta receta) {
        for (Receta r : recetas) {
            if (r.equals(receta)) {
                return r;
            }
        }
        return null;
    }


    public void mostrarDetalles() {
        for (Receta r : recetas) {
            System.out.println(r);
        }
    }
}