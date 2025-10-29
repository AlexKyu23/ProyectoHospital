package hospital.presentation.Receta.Presentation;

import hospital.presentation.AbstractModel;
import logic.Receta;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class RecetaModel extends AbstractModel {
    public static final String LIST = "list";

    private List<Receta> list;

    public void init() {
        list = new ArrayList<>();
        firePropertyChange(LIST);
    }

    public void setList(List<Receta> list) {
        this.list = list;
        firePropertyChange(LIST);
    }

    public List<Receta> getList() {
        return list;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
    }
}
