package Clases.Farmaceuta.data;

import Clases.Farmaceuta.logic.Farmaceuta;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "listaFarmaceutas")
public class ListaFarmaceutas { //basicamente el data
    private List<Farmaceuta> farmaceutas;

    public ListaFarmaceutas() {
        farmaceutas = new ArrayList<>();
    }

    @XmlElement(name = "farmaceuta")
    public List<Farmaceuta> getFarmaceutas() {
        return farmaceutas;
    }

    public void setFarmaceutas(List<Farmaceuta> farmaceutas) {
        this.farmaceutas = farmaceutas;
    }

    // Inclusión
    public void inclusion(Farmaceuta farmaceuta) {
        farmaceutas.add(farmaceuta);
    }

    public List<Farmaceuta> consulta() {
        return farmaceutas;
    }


    public Farmaceuta busquedaPorId(String id) {
        for (Farmaceuta f : farmaceutas) {
            if (f.getId().equalsIgnoreCase(id)) {
                return f;
            }
        }
        return null;
    }


    public Farmaceuta busquedaPorNombre(String nombre) {
        for (Farmaceuta f : farmaceutas) {
            if (f.getNombre().equalsIgnoreCase(nombre)) {
                return f;
            }
        }
        return null;
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