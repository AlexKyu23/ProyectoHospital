package hospital.presentation.Paciente.presentation;

import hospital.presentation.AbstractModel;
import logic.Paciente;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class PacienteModel extends AbstractModel {
    private Paciente current;
    private List<Paciente> list;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public void init() {
        current = new Paciente();
        list = new ArrayList<>();
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
    }

    public Paciente getCurrent() {
        return current != null ? current : new Paciente();
    }

    public List<Paciente> getList() {
        return list != null ? list : new ArrayList<>();
    }

    public void setCurrent(Paciente paciente) {
        this.current = paciente != null ? paciente : new Paciente();
        firePropertyChange(CURRENT);
    }

    public void setList(List<Paciente> list) {
        this.list = list != null ? list : new ArrayList<>();
        firePropertyChange(LIST);
    }
}
