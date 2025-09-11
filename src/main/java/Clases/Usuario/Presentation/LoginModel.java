package Clases.Usuario.Presentation;

import Clases.AbstractModel;
import Clases.Usuario.logic.Usuario;

import java.beans.PropertyChangeListener;

public class LoginModel extends AbstractModel {
    private Usuario current;

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

    public String getID() {
        return current.getId();
    }

    public String getClave() {
        return current.getClave();
    }

    public void setPassword(String clave) {
        current.setClave(clave);
        firePropertyChange(CLAVE);
    }

}
