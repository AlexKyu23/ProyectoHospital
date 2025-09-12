package Clases.Medicamento.presentation;

import Clases.AbstractModel;
import Clases.Medicamento.logic.Medicamento;

public class MedicamentoModel extends AbstractModel {
    private Medicamento current;

    public static final String CURRENT = "current";

    public MedicamentoModel() {
        this.current = null;
    }

    public Medicamento getCurrent() {
        return current;
    }

    public void setCurrent(Medicamento current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }
}
