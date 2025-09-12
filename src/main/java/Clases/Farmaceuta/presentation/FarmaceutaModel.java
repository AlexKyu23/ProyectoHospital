package Clases.Farmaceuta.presentation;

import Clases.AbstractModel;
import Clases.Farmaceuta.logic.Farmaceuta;

public class FarmaceutaModel extends AbstractModel {
    private Farmaceuta current;

    public static final String CURRENT = "current";

    public FarmaceutaModel() {
        current = null;
    }

    public Farmaceuta getCurrent() {
        return current;
    }

    public void setCurrent(Farmaceuta farmaceuta) {
        this.current = farmaceuta;
        firePropertyChange(CURRENT);
    }
}
