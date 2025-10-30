package hospital.presentation.Medico.presentation;

import hospital.presentation.AbstractModel;
import logic.Medico;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class MedicoModel extends AbstractModel {
    private Medico current;
    private List<Medico> list;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public void init() {
        current = new Medico();
        list = new ArrayList<>();
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
    }

    public Medico getCurrent() {
        return current;
    }

    public List<Medico> getList() {
        return list != null ? list : new ArrayList<>();
    }

    public void setCurrent(Medico medico) {
        this.current = medico != null ? medico : new Medico();
        firePropertyChange(CURRENT);
    }

    public void setList(List<Medico> list) {
        this.list = list != null ? list : new ArrayList<>();
        firePropertyChange(LIST);
    }
}
