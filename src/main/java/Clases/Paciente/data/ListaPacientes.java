package Clases.Paciente.data;

import Clases.Paciente.logic.Paciente;
import Clases.Usuario.data.XmlPersister;
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

    public void inclusion(Paciente paciente) {
        pacientes.add(paciente);
    }

    public List<Paciente> consulta() {
        return pacientes;
    }

    public Paciente busquedaPorId(String id) {
        return pacientes.stream()
                .filter(p -> p.getId().equalsIgnoreCase(id))
                .findFirst().orElse(null);
    }

    public Paciente busquedaPorNombre(String nombre) {
        return pacientes.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst().orElse(null);
    }

    public void modificacion(Paciente paciente) {
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getId().equalsIgnoreCase(paciente.getId())) {
                pacientes.set(i, paciente);
                return;
            }
        }
    }

    public void borrado(String id) {
        pacientes.removeIf(p -> p.getId().equalsIgnoreCase(id));
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
                ListaPacientes cargada = XmlPersister.load(ListaPacientes.class, ARCHIVO);
                this.pacientes = cargada.getPacientes();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
