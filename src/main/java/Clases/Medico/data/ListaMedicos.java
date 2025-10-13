package Clases.Medico.data;

import Clases.Medico.logic.Medico;
import java.util.ArrayList;
import java.util.List;

public class ListaMedicos {
    private List<Medico> medicos;

    public ListaMedicos() {
        medicos = new ArrayList<>();
    }

    public List<Medico> consulta() {
        return medicos;
    }

    public void setList(List<Medico> lista) {
        this.medicos = lista != null ? lista : new ArrayList<>();
    }

    public void inclusion(Medico medico) {
        medicos.add(medico);
    }

    public Medico busquedaPorId(String id) {
        return medicos.stream()
                .filter(m -> m.getId().equalsIgnoreCase(id))
                .findFirst().orElse(null);
    }

    public Medico busquedaPorNombre(String nombre) {
        return medicos.stream()
                .filter(m -> m.getNombre().equalsIgnoreCase(nombre))
                .findFirst().orElse(null);
    }

    public void modificacion(Medico medico) {
        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getId().equalsIgnoreCase(medico.getId())) {
                medicos.set(i, medico);
                return;
            }
        }
    }

    public void borrado(String id) {
        medicos.removeIf(m -> m.getId().equalsIgnoreCase(id));
    }

}
