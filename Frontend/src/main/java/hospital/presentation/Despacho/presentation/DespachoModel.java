package hospital.presentation.Despacho.presentation;

import hospital.presentation.AbstractModel;
import logic.Farmaceuta;
import logic.Receta;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class DespachoModel extends AbstractModel {
    private Receta filter;
    private List<Receta> list;
    private Receta current;
    private int mode;

    public static final String LIST = "list";
    public static final String CURRENT = "current";
    public static final String FILTER = "filter";
    public static final String MODE = "mode";

    public DespachoModel() {
        init();
    }

    public void init() {
        filter = new Receta();
        current = new Receta();
        list = new ArrayList<>();
        mode = 1; // MODE_CREATE

        firePropertyChange(LIST);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);
        firePropertyChange(MODE);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
    }

    public Receta getFilter() { return filter; }
    public List<Receta> getList() { return list; }
    public Receta getCurrent() { return current; }
    public int getMode() { return mode; }

    public void setFilter(Receta filter) {
        this.filter = filter;
        firePropertyChange(FILTER);
    }

    public void setList(List<Receta> list) {
        this.list = list;
        firePropertyChange(LIST);
    }

    public void setCurrent(Receta current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public void setMode(int mode) {
        this.mode = mode;
        firePropertyChange(MODE);
    }

    public void setFarmaceuta(Farmaceuta farm) {

    }
}
