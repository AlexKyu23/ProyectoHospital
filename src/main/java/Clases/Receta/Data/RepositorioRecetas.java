package Clases.Receta.Data;

import Clases.Receta.logic.Receta;
import java.util.ArrayList;
import java.util.List;

public class RepositorioRecetas {
    private List<Receta> recetas = new ArrayList<>();

    public void agregar(Receta r) {
        recetas.add(r);
    }

    public List<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas != null ? recetas : new ArrayList<>();
    }

    public Receta buscarPorId(String id) {
        return recetas.stream()
                .filter(r -> r.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public void eliminarPorId(String id) {
        recetas.removeIf(r -> r.getId().equalsIgnoreCase(id));
    }

    public void actualizar(Receta recetaActualizada) {
        for (int i = 0; i < recetas.size(); i++) {
            if (recetas.get(i).getId().equalsIgnoreCase(recetaActualizada.getId())) {
                recetas.set(i, recetaActualizada);
                return;
            }
        }
    }
}
