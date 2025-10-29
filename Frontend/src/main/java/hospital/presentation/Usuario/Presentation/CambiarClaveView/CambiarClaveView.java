package hospital.presentation.Usuario.Presentation.CambiarClaveView;

import hospital.presentation.Usuario.Presentation.CambiarClaveController;
import hospital.presentation.Usuario.Presentation.CambiarClaveModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CambiarClaveView extends JPanel implements PropertyChangeListener {
    private CambiarClaveController controller;
    private CambiarClaveModel model;

    private JPanel cambiarClave;
    private JTextField claveActual;
    private JTextField claveNueva;
    private JTextField confirmarClave;
    private JButton confirmarButton;
    private JButton cancelarButton;
    private JPanel botones;

    public void setController(CambiarClaveController controller) {
        this.controller = controller;
        initListeners();
    }

    public void setModel(CambiarClaveModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    private void initListeners() {
        confirmarButton.addActionListener(e -> controller.guardarClave());
        cancelarButton.addActionListener(e -> controller.cancelar());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (CambiarClaveModel.NUEVA_CLAVE.equals(evt.getPropertyName())) {
            claveActual.setText("");
            claveNueva.setText("");
            confirmarClave.setText("");
        }
        cambiarClave.revalidate();
    }

    // 🔹 Getters para el controlador
    public String getClaveActual() { return claveActual.getText(); }
    public String getClaveNueva() { return claveNueva.getText(); }
    public String getConfirmarClave() { return confirmarClave.getText(); }
    public JPanel getPanel() { return cambiarClave; }

    public void cerrar() {
        SwingUtilities.getWindowAncestor(cambiarClave).dispose();
    }

    public void enviarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(cambiarClave, mensaje, titulo, tipo);
    }
}
