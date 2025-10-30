package hospital.presentation.Admin.presentation;

import logic.Admin;
import hospital.presentation.AbstractModel;

import java.beans.PropertyChangeListener;

public class AdminModel extends AbstractModel {
    private Admin current;

    public static final String CURRENT = "current";

    public AdminModel() {
        this.current = null;
        System.out.println("🧩 AdminModel creado (vacío)");
    }

    public AdminModel(String id, String nombre, String clave) {
        current = new Admin(id, nombre, clave);
        System.out.println("🧩 AdminModel creado con admin: " + nombre);
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
        Admin old = this.current;
        this.current = admin;
        firePropertyChange(CURRENT);
        System.out.println("🔄 Admin actualizado en modelo: " + (admin != null ? admin.getNombre() : "null"));
    }
}