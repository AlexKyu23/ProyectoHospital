// Frontend/src/main/java/presentation/Medicamento/presentation/MedicamentoModel.java
package hospital.presentation.Medicamento.presentation;


import logic.Medicamento;
import hospital.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoModel extends AbstractModel {
    private Medicamento filter;
    private List<Medicamento> list;
    private Medicamento current;
    private int mode;

    public static final String LIST = "list";
    public static final String CURRENT = "current";
    public static final String FILTER = "filter";
    public static final String MODE = "mode";

    public MedicamentoModel() {
        init();
    }

    public void init() {
        filter = new Medicamento();
        current = new Medicamento();
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

    public Medicamento getFilter() { return filter; }
    public List<Medicamento> getList() { return list; }
    public Medicamento getCurrent() { return current; }
    public int getMode() { return mode; }

    public void setFilter(Medicamento filter) {
        this.filter = filter;
        firePropertyChange(FILTER);
    }

    public void setList(List<Medicamento> list) {
        this.list = list;
        firePropertyChange(LIST);
    }

    public void setCurrent(Medicamento current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public void setMode(int mode) {
        this.mode = mode;
        firePropertyChange(MODE);
    }
}