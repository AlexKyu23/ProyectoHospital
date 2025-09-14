package Clases.Farmaceuta.data;

import Clases.Farmaceuta.logic.Farmaceuta;
import Clases.Usuario.data.XmlPersister;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "listaFarmaceutas")
public class ListaFarmaceutas {
    private List<Farmaceuta> farmaceutas;
    private static final File ARCHIVO = new File("farmaceutas.xml");

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

    public void inclusion(Farmaceuta farmaceuta) {
        farmaceutas.add(farmaceuta);
    }

    public List<Farmaceuta> consulta() {
        return farmaceutas;
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

    public void guardar() {
        try {
            XmlPersister.save(this, ARCHIVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargar() {
        if (ARCHIVO.exists()) {
            try {
                ListaFarmaceutas cargada = XmlPersister.load(ListaFarmaceutas.class, ARCHIVO);
                this.farmaceutas = cargada.getFarmaceutas();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
