package Clases.Medico.data;

import Clases.Medico.logic.Medico;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

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

    // Guardar lista en XML
    public void guardar() {
        try {
            JAXBContext context = JAXBContext.newInstance(ListaMedicos.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(this, ARCHIVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Cargar lista desde XML si existe
    public void cargar() {
        if (ARCHIVO.exists()) {
            try {
                JAXBContext context = JAXBContext.newInstance(ListaMedicos.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                ListaMedicos cargada = (ListaMedicos) unmarshaller.unmarshal(ARCHIVO);
                this.medicos = cargada.getMedicos(); // reemplaza la lista actual
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}