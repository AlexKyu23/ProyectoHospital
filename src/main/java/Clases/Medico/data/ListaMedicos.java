package Clases.Medico.data;

import Clases.Medico.logic.Medico;
import Clases.Usuario.data.XmlPersister;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "listaMedicos")
public class ListaMedicos {
    private List<Medico> medicos;
    private static final File ARCHIVO = new File("medicos.xml");

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

    public List<Medico> consulta() {
        return medicos;
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
                ListaMedicos cargada = XmlPersister.load(ListaMedicos.class, ARCHIVO);
                this.medicos = cargada.getMedicos();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
