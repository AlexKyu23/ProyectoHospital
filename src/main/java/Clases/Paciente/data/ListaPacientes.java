package Clases.Paciente.data;

import Clases.Paciente.logic.Paciente;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "listaPacientes")
public class ListaPacientes {
    private List<Paciente> pacientes;

    private static final File ARCHIVO = new File("pacientes.xml");

    public ListaPacientes() {
        pacientes = new ArrayList<>();
    }

    @XmlElement(name = "paciente")
    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    // 🔹 Agregar paciente
    public void inclusion(Paciente paciente) {
        pacientes.add(paciente);
    }

    // 🔹 Consultar todos
    public List<Paciente> consulta() {
        return pacientes;
    }

    // 🔹 Buscar por ID
    public Paciente busquedaPorId(String id) {
        for (Paciente p : pacientes) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    // 🔹 Buscar por nombre
    public Paciente busquedaPorNombre(String nombre) {
        for (Paciente p : pacientes) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    // 🔹 Modificar paciente existente
    public void modificacion(Paciente paciente) {
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getId().equalsIgnoreCase(paciente.getId())) {
                pacientes.set(i, paciente);
                return;
            }
        }
    }

    // 🔹 Borrar por ID
    public void borrado(String id) {
        pacientes.removeIf(p -> p.getId().equalsIgnoreCase(id));
    }

    public void guardar() {
        try {
            JAXBContext context = JAXBContext.newInstance(ListaPacientes.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(this, ARCHIVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargar() {
        if (ARCHIVO.exists()) {
            try {
                JAXBContext context = JAXBContext.newInstance(ListaPacientes.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                ListaPacientes cargada = (ListaPacientes) unmarshaller.unmarshal(ARCHIVO);
                this.pacientes = cargada.getPacientes(); // reemplaza la lista actual
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
