package Clases.Admin.presentation;

import Clases.AbstractModel;
import Clases.Admin.logic.Admin;

import java.beans.PropertyChangeListener;

public class AdminModel extends AbstractModel {
    private Admin current;

    public static final String CURRENT = "current";

    public AdminModel(String id, String nombre, String clave) {
        current = new Admin(id, nombre, clave);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
    }

    public Admin getCurrent() {
        return current;
    }

    public void setCurrent(Admin admin) {
        this.current = admin;
        firePropertyChange(CURRENT);
    }
}

