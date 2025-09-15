package Clases.Receta.Presentation;

import Clases.Receta.logic.Receta;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class RecetaModel {
    public static final String LIST = "list";

    private List<Receta> list;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void setList(List<Receta> list) {
        this.list = list;
        support.firePropertyChange(LIST, null, list);
    }

    public List<Receta> getList() {
        return list;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
