package Clases.Prescribir.data;

import Clases.Prescribir.logic.Prescripcion;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RepositorioPrescripciones {

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

    public static void eliminar(Prescripcion p) {
        prescripciones.remove(p);
    }

    public static void limpiar() {
        prescripciones.clear();
    }

    // Guardar en XML
    public static void guardar() {
        try {
            JAXBContext context = JAXBContext.newInstance(PrescripcionesWrapper.class, Prescripcion.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            PrescripcionesWrapper wrapper = new PrescripcionesWrapper();
            wrapper.setPrescripciones(prescripciones);

            marshaller.marshal(wrapper, ARCHIVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Cargar desde XML
    public static void cargar() {
        if (ARCHIVO.exists()) {
            try {
                JAXBContext context = JAXBContext.newInstance(PrescripcionesWrapper.class, Prescripcion.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();

                PrescripcionesWrapper wrapper = (PrescripcionesWrapper) unmarshaller.unmarshal(ARCHIVO);
                prescripciones = wrapper.getPrescripciones() != null ? wrapper.getPrescripciones() : new ArrayList<>();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
