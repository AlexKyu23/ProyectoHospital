package Clases.Farmaceuta.data;

import Clases.Farmaceuta.logic.Farmaceuta;
import java.util.ArrayList;
import java.util.List;

public class ListaFarmaceutas {
    private List<Farmaceuta> farmaceutas;

    public ListaFarmaceutas() {
        farmaceutas = new ArrayList<>();
    }

    public List<Farmaceuta> consulta() {
        return farmaceutas;
    }

    public void setList(List<Farmaceuta> farmaceutas) {
        this.farmaceutas = farmaceutas != null ? farmaceutas : new ArrayList<>();
    }

    public void inclusion(Farmaceuta farmaceuta) {
        farmaceutas.add(farmaceuta);
    }

    public Farmaceuta busquedaPorId(String id) {
        return farmaceutas.stream()
                .filter(f -> f.getId().equalsIgnoreCase(id))
                .findFirst().orElse(null);
    }

    public Farmaceuta busquedaPorNombre(String nombre) {
        return farmaceutas.stream()
                .filter(f -> f.getNombre().equalsIgnoreCase(nombre))
                .findFirst().orElse(null);
    }

    public void modificacion(Farmaceuta farmaceuta) {
        for (int i = 0; i < farmaceutas.size(); i++) {
            if (farmaceutas.get(i).getId().equalsIgnoreCase(farmaceuta.getId())) {
                farmaceutas.set(i, farmaceuta);
                return;
            }
        }
    }

    public void borrado(String id) {
        farmaceutas.removeIf(f -> f.getId().equalsIgnoreCase(id));
    }
}
