package Clases.Admin;

import Clases.AbstractModel;

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

