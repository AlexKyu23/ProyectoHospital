package Listas;

import Clases.Medico.logic.Medico;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "listaMedicos")
public class ListaMedicos {
    private List<Medico> medicos;

    public ListaMedicos() {
        medicos = new ArrayList<>();
    }

    @XmlElement(name = "medico")
    public List<Medico> getMedicos() {
        return medicos;
    }

    public void setMedicos(List<Medico> medicos) {
        this.medicos = medicos;
    }

    public void inclusion(Medico medico) {
        medicos.add(medico);
    }

    // toda la lista?
    public List<Medico> consulta() {
        return medicos;
    }


    public Medico busquedaPorId(String id) {
        for (Medico m : medicos) {
            if (m.getId().equalsIgnoreCase(id)) {
                return m;
            }
        }
        return null;
    }


    public Medico busquedaPorNombre(String nombre) {
        for (Medico m : medicos) {
            if (m.getNombre().equalsIgnoreCase(nombre)) {
                return m;
            }
        }
        return null;
    }

    // Modificación (reemplaza el objeto con el mismo id)
    public void modificacion(Medico medico) {
        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getId().equalsIgnoreCase(medico.getId())) {
                medicos.set(i, medico);
                return;
            }
        }
    }

    // Borrado por id
    public void borrado(String id) {
        medicos.removeIf(m -> m.getId().equalsIgnoreCase(id));
    }
}