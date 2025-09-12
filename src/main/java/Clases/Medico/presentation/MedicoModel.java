package Clases.Medico.presentation;

import Clases.AbstractModel;
import Clases.Medico.logic.Medico;

public class MedicoModel extends AbstractModel {
    private Medico current;

    public static final String CURRENT = "current";

    public MedicoModel() {
        this.current = null;
    }

    public Medico getCurrent() {
        return current;
    }

    public void setCurrent(Medico current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }
}
