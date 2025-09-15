package Clases.Receta.Data;

import Clases.Receta.logic.Receta;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import Clases.Receta.logic.ItemReceta;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RepositorioRecetas {
    private static List<Receta> recetas = new ArrayList<>();
    private static final File ARCHIVO = new File("recetas.xml");

    public static void agregar(Receta r) {
        recetas.add(r);
    }

    public static List<Receta> getRecetas() {
        return recetas;
    }

    public static void guardar() {
        try {
            JAXBContext context = JAXBContext.newInstance(RecetasWrapper.class, Receta.class, ItemReceta.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            RecetasWrapper wrapper = new RecetasWrapper();
            wrapper.setRecetas(recetas);
            marshaller.marshal(wrapper, ARCHIVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cargar() {
        if (ARCHIVO.exists()) {
            try {
                JAXBContext context = JAXBContext.newInstance(RecetasWrapper.class, Receta.class, ItemReceta.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                RecetasWrapper wrapper = (RecetasWrapper) unmarshaller.unmarshal(ARCHIVO);
                recetas = wrapper.getRecetas() != null ? wrapper.getRecetas() : new ArrayList<>();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}