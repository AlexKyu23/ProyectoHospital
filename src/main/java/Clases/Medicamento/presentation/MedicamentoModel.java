package Clases.Medicamento.presentation;

import Clases.AbstractModel;
import Clases.Medicamento.logic.Medicamento;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoModel extends AbstractModel {
    private Medicamento current;
    private List<Medicamento> list;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public MedicamentoModel() {
        current = new Medicamento();
        list = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    public Medicamento getCurrent() {
        return current;
    }

    public void setCurrent(Medicamento medicamento) {
        this.current = medicamento;
        firePropertyChange(CURRENT);
    }

    public List<Medicamento> getList() {
        return list;
    }

    public void setList(List<Medicamento> list) {
        this.list = list;
        firePropertyChange(LIST);
    }
}
