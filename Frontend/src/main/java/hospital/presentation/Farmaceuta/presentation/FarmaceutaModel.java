// Frontend/src/main/java/presentation/Farmaceuta/presentation/FarmaceutaModel.java
package hospital.presentation.Farmaceuta.presentation;

import logic.Farmaceuta;
import hospital.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class FarmaceutaModel extends AbstractModel {
    private Farmaceuta filter;
    private List<Farmaceuta> list;
    private Farmaceuta current;
    private int mode;

    public static final String LIST = "list";
    public static final String CURRENT = "current";
    public static final String FILTER = "filter";
    public static final String MODE = "mode";

    public FarmaceutaModel() {
        init();
    }

    public void init() {
        filter = new Farmaceuta();
        current = new Farmaceuta();
        list = new ArrayList<>();
        mode = 1; // MODE_CREATE
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LIST);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);
        firePropertyChange(MODE);
    }

    // === GETTERS ===
    public Farmaceuta getFilter() { return filter; }
    public List<Farmaceuta> getList() { return list; }
    public Farmaceuta getCurrent() { return current; }
    public int getMode() { return mode; }

    // === SETTERS (solo disparan evento, sin old/new) ===
    public void setFilter(Farmaceuta filter) {
        this.filter = filter;
        firePropertyChange(FILTER);  // ← Solo 1 argumento
    }

    public void setList(List<Farmaceuta> list) {
        this.list = list;
        firePropertyChange(LIST);
    }

    public void setCurrent(Farmaceuta current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public void setMode(int mode) {
        this.mode = mode;
        firePropertyChange(MODE);
    }
}