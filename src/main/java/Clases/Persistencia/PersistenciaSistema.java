package Clases.Persistencia;

import Clases.Persistencia.DatosSistemaWrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class PersistenciaSistema {
    private static final File ARCHIVO = new File("sistema.xml");

    public static void guardarTodo(DatosSistemaWrapper datos) {
        try {
            JAXBContext context = JAXBContext.newInstance(DatosSistemaWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(datos, ARCHIVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DatosSistemaWrapper cargarTodo() {
        if (!ARCHIVO.exists()) return new DatosSistemaWrapper();
        try {
            JAXBContext context = JAXBContext.newInstance(DatosSistemaWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (DatosSistemaWrapper) unmarshaller.unmarshal(ARCHIVO);
        } catch (Exception e) {
            e.printStackTrace();
            return new DatosSistemaWrapper();
        }
    }
}
