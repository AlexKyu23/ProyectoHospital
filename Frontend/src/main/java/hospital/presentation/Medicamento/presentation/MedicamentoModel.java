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
        firePropertyChange(LIST);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);
        firePropertyChange(MODE);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
    }

    public Medicamento getFilter() {
        return filter != null ? filter : new Medicamento();
    }

    public List<Medicamento> getList() {
        return list != null ? list : new ArrayList<>();
    }

    public Medicamento getCurrent() {
        return current != null ? current : new Medicamento();
    }

    public int getMode() {
        return mode;
    }

    public void setFilter(Medicamento filter) {
        this.filter = filter != null ? filter : new Medicamento();
        firePropertyChange(FILTER);
    }

    public void setList(List<Medicamento> list) {
        this.list = list != null ? list : new ArrayList<>();
        firePropertyChange(LIST);
    }

    public void setCurrent(Medicamento current) {
        this.current = current != null ? current : new Medicamento();
        firePropertyChange(CURRENT);
    }

    public void setMode(int mode) {
        this.mode = mode;
        firePropertyChange(MODE);
    }
}
