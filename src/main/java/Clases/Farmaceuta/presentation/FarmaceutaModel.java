package Clases.Farmaceuta.presentation;

import Clases.AbstractModel;
import Clases.Farmaceuta.logic.Farmaceuta;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class FarmaceutaModel extends AbstractModel {
    private Farmaceuta current;
    private List<Farmaceuta> list;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public FarmaceutaModel() {
        current = new Farmaceuta();
        list = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    public Farmaceuta getCurrent() {
        return current;
    }

    public void setCurrent(Farmaceuta farmaceuta) {
        this.current = farmaceuta;
        firePropertyChange(CURRENT);
    }

    public List<Farmaceuta> getList() {
        return list;
    }

    public void setList(List<Farmaceuta> list) {
        this.list = list;
        firePropertyChange(LIST);
    }
}
