package Clases.Farmaceuta.data;

import Clases.Farmaceuta.logic.Farmaceuta;

import java.util.ArrayList;
import java.util.List;

public class FarmaceutaData {
    private List<Farmaceuta> farmaceutas;

    public FarmaceutaData() {
        farmaceutas = new ArrayList<>();
        farmaceutas.add(new Farmaceuta("FAR-001", "Carla Jiménez", "FAR-001"));
        farmaceutas.add(new Farmaceuta("FAR-002", "Luis Mora", "FAR-002"));
    }

    public List<Farmaceuta> getFarmaceutas() {
        return farmaceutas;
    }
}
