package Clases.Receta.Data;

import Clases.Farmaceuta.data.ListaFarmaceutas;
import Clases.Receta.logic.Receta;
import Clases.Usuario.data.XmlPersister;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "historicoRecetas")
public class historicoRecetas {
    private List<Receta> recetas;
    private static final File ARCHIVO = new File("recetas.xml");

    public historicoRecetas() {
        recetas = new ArrayList<>();
    }

    @XmlElement(name = "receta")
    public List<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
    }


    public void inclusion(Receta receta) {
        recetas.add(receta);
    }


    public List<Receta> consulta() {
        return recetas;
    }

    //
    public Receta busqueda(int i) {
        if (i >= 0 && i < recetas.size()) {
            return recetas.get(i);
        }
        return null;
    }


    public Receta busqueda(Receta receta) {
        for (Receta r : recetas) {
            if (r.equals(receta)) {
                return r;
            }
        }
        return null;
    }

    public void mostrarDetalles() {
        for (Receta r : recetas) {
            System.out.println(r);
        }
    }

    public void cargar() {
        if (ARCHIVO.exists()) {
            try {
                historicoRecetas cargada = XmlPersister.load(historicoRecetas.class, ARCHIVO);
                this.recetas = cargada.getRecetas();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}