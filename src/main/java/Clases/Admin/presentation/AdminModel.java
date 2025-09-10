package Clases.Admin.presentation;

import Clases.AbstractModel;
import Clases.Admin.logic.Admin;

public class AdminModel extends AbstractModel {
    private Admin current;

    public static final String CURRENT = "current";

    public AdminModel() {
        this.current = null;
    }

    public Admin getCurrent() {
        return current;
    }

    public void setCurrent(Admin admin) {
        this.current = admin;
        firePropertyChange(CURRENT);
    }
}

