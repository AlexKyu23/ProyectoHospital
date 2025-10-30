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
        firePropertyChange(LIST);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);
        firePropertyChange(MODE);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
    }

    public Farmaceuta getFilter() {
        return filter != null ? filter : new Farmaceuta();
    }

    public List<Farmaceuta> getList() {
        return list != null ? list : new ArrayList<>();
    }

    public Farmaceuta getCurrent() {
        return current != null ? current : new Farmaceuta();
    }

    public int getMode() {
        return mode;
    }

    public void setFilter(Farmaceuta filter) {
        this.filter = filter != null ? filter : new Farmaceuta();
        firePropertyChange(FILTER);
    }

    public void setList(List<Farmaceuta> list) {
        this.list = list != null ? list : new ArrayList<>();
        firePropertyChange(LIST);
    }

    public void setCurrent(Farmaceuta current) {
        this.current = current != null ? current : new Farmaceuta();
        firePropertyChange(CURRENT);
    }

    public void setMode(int mode) {
        this.mode = mode;
        firePropertyChange(MODE);
    }
}
