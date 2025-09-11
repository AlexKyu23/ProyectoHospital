package Clases.Usuario.Presentation;

import Clases.AbstractModel;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class CambiarClaveModel extends AbstractModel {

    private String nuevaClave;
    public static String NUEVA_CLAVE = "nuevaClave";

    public CambiarClaveModel() {
        nuevaClave = "";
    }

public String getNuevaClave() {
    return nuevaClave;
}

    public void setNuevaClave(String nuevaClave) {
    this.nuevaClave = nuevaClave;
    firePropertyChange(NUEVA_CLAVE);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
    }

}
