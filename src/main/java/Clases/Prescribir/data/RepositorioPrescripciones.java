package Clases.Prescribir.data;

import Clases.Prescribir.logic.Prescripcion;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RepositorioPrescripciones {

    // Lista que guarda todas las prescripciones
    private static List<Prescripcion> prescripciones = new ArrayList<>();

    private static final File ARCHIVO = new File("prescripciones.xml");

    // Agregar una prescripción
    public static void agregar(Prescripcion p) {
        prescripciones.add(p);
    }

    // Obtener todas las prescripciones
    public static List<Prescripcion> getPrescripciones() {
        return prescripciones;
    }

    // Eliminar una prescripción
    public static void eliminar(Prescripcion p) {
        prescripciones.remove(p);
    }

    // Limpiar todas las prescripciones (opcional)
    public static void limpiar() {
        prescripciones.clear();
    }

    public static void guardar() {
        try {
            JAXBContext context = JAXBContext.newInstance(RepositorioPrescripciones.class, Prescripcion.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(new RepositorioWrapper(prescripciones), ARCHIVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cargar() {
        if (ARCHIVO.exists()) {
            try {
                JAXBContext context = JAXBContext.newInstance(RepositorioPrescripciones.class, Prescripcion.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                RepositorioWrapper wrapper = (RepositorioWrapper) unmarshaller.unmarshal(ARCHIVO);
                prescripciones = wrapper.getPrescripciones();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // -------------------- Wrapper interno --------------------
    // Necesario para JAXB porque la lista está dentro de un atributo estático
    private static class RepositorioWrapper {
        private List<Prescripcion> prescripciones;

        public RepositorioWrapper() {
            prescripciones = new ArrayList<>();
        }

        public RepositorioWrapper(List<Prescripcion> prescripciones) {
            this.prescripciones = prescripciones;
        }

        @jakarta.xml.bind.annotation.XmlElement(name = "prescripcion")
        public List<Prescripcion> getPrescripciones() {
            return prescripciones;
        }

        public void setPrescripciones(List<Prescripcion> prescripciones) {
            this.prescripciones = prescripciones;
        }
    }

}
