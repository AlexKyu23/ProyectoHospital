package Clases.Usuario.Presentation;

import Clases.AbstractModel;
import Clases.Usuario.logic.Usuario;

import java.beans.PropertyChangeListener;

public class LoginModel extends AbstractModel {
    private static Usuario current;

    public static String ID = "Id";
    public static String CLAVE = "Clave";

    public LoginModel(Usuario usuario) {
        current = usuario;
    }

    @Override
    public void  addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(ID);
        firePropertyChange(CLAVE);
    }

    public static String getID() {
        return current.getId();
    }

    public static String getClave() {
        return current.getClave();
    }

    public void setPassword(String clave) {
        current.setClave(clave);
        firePropertyChange(CLAVE);
    }

}
