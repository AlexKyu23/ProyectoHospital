package Clases.Paciente.presentation;

import Clases.AbstractModel;
import Clases.Paciente.logic.Paciente;

public class PacienteModel extends AbstractModel {
    private Paciente current;

    public static final String CURRENT = "current";

    public PacienteModel() {
        this.current = null;
    }

    public Paciente getCurrent() {
        return current;
    }

    public void setCurrent(Paciente current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }
}
