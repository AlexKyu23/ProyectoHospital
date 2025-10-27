package data;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import logic.Receta;

import java.util.List;

@XmlRootElement(name = "recetas")
public class RecetasWrapper {
    private List<Receta> recetas;

    @XmlElement(name = "receta")
    public List<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
    }
}
