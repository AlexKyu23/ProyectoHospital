package Clases.Medico.presentation;

import Clases.AbstractModel;
import Clases.Medico.logic.Medico;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class MedicoModel extends AbstractModel {
    private Medico current;
    private List<Medico> list;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public MedicoModel() {
        current = new Medico();
        list = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    public Medico getCurrent() {
        return current;
    }

    public void setCurrent(Medico medico) {
        this.current = medico;
        firePropertyChange(CURRENT);
    }

    public List<Medico> getList() {
        return list;
    }

    public void setList(List<Medico> list) {
        this.list = list;
        firePropertyChange(LIST);
    }
}