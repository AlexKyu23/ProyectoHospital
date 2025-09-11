package Listas;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class XmlPersister {

    public static <T> void save(T object, File file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            marshaller.marshal(object, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T load(File file, Class<T> clazz) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        try (FileInputStream fis = new FileInputStream(file)) {
            return (T) unmarshaller.unmarshal(fis);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
