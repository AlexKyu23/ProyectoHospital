package hospital.presentation.Usuario.Presentation;


import hospital.presentation.AbstractModel;
import logic.Usuario;

import java.beans.PropertyChangeListener;

/**
 * Modelo del login. Guarda el usuario actual y si está autenticado.
 */
public class LoginModel extends AbstractModel {
    private Usuario current;
    private boolean autenticado;

    public static final String CURRENT = "current";
    public static final String AUTENTICADO = "autenticado";

    public LoginModel() {
        current = new Usuario();
        autenticado = false;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(AUTENTICADO);
    }

    public Usuario getCurrent() {
        return current;
    }

    public void setCurrent(Usuario usuario) {
        this.current = usuario;
        firePropertyChange(CURRENT);
    }

    public boolean isAutenticado() {
        return autenticado;
    }

    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
        firePropertyChange(AUTENTICADO);
    }
}
