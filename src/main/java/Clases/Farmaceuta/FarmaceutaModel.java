package Clases.Farmaceuta;
import Clases.AbstractModel;

import java.util.ArrayList;
import java.util.List;

public class FarmaceutaModel extends AbstractModel {
    private List<Farmaceuta> farmaceutas;   // lista de farmaceutas
    private Farmaceuta current;             // farmaceuta en edición/seleccionado

    public static final String FARMACEUTAS = "farmaceutas";
    public static final String CURRENT = "current";

    public FarmaceutaModel() {
        farmaceutas = new ArrayList<>();
        current = null;
    }

    // 🔹 Getters
    public List<Farmaceuta> getFarmaceutas() {
        return farmaceutas;
    }

    public Farmaceuta getCurrent() {
        return current;
    }

    // 🔹 Setters con notificación
    public void setFarmaceutas(List<Farmaceuta> farmaceutas) {
        this.farmaceutas = farmaceutas;
        firePropertyChange(FARMACEUTAS);
    }

    public void setCurrent(Farmaceuta farmaceuta) {
        this.current = farmaceuta;
        firePropertyChange(CURRENT);
    }

    // 🔹 Operaciones CRUD
    public void addFarmaceuta(Farmaceuta f) {
        farmaceutas.add(f);
        firePropertyChange(FARMACEUTAS);
    }

    public void updateFarmaceuta(Farmaceuta f) {
        for (int i = 0; i < farmaceutas.size(); i++) {
            if (farmaceutas.get(i).getId().equals(f.getId())) {
                farmaceutas.set(i, f);
                firePropertyChange(FARMACEUTAS);
                return;
            }
        }
    }

    public void deleteFarmaceuta(String id) {
        farmaceutas.removeIf(f -> f.getId().equals(id));
        firePropertyChange(FARMACEUTAS);
    }

    // 🔹 Búsquedas
    public Farmaceuta findById(String id) {
        for (Farmaceuta f : farmaceutas) {
            if (f.getId().equalsIgnoreCase(id)) {
                return f;
            }
        }
        return null;
    }

    public Farmaceuta findByNombre(String nombre) {
        for (Farmaceuta f : farmaceutas) {
            if (f.getNombre().equalsIgnoreCase(nombre)) {
                return f;
            }
        }
        return null;
    }
}

