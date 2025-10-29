package hospital.presentation.Usuario.Presentation;

import hospital.presentation.AbstractModel;

import java.beans.PropertyChangeListener;

public class CambiarClaveModel extends AbstractModel {
    private String nuevaClave;

    public static final String NUEVA_CLAVE = "nuevaClave";

    public CambiarClaveModel() {
        nuevaClave = "";
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(NUEVA_CLAVE);
    }

    public String getNuevaClave() {
        return nuevaClave;
    }

    public void setNuevaClave(String nuevaClave) {
        this.nuevaClave = nuevaClave;
        firePropertyChange(NUEVA_CLAVE);
    }
}
