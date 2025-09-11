package Clases.Usuario.Presentation.CambiarClaveView;

import javax.swing.*;
import Clases.Usuario.Presentation.CambiarClaveController;

import java.awt.event.ActionListener;

public class CambiarClaveView {
    private JPanel cambiarClave;
    private JTextField claveActual;
    private JTextField claveNueva;
    private JTextField confirmarClave;
    private JPanel botones;
    private JButton confirmarButton;
    private JButton cancelarButton;

    private CambiarClaveController claveController;

    public CambiarClaveView() {                                                 //Revisar
        confirmarButton.setText("Confirmar");
        cancelarButton.setText("Cancelar");
    }

    public String getClaveActual() {
        return claveActual.getText();
    }

    public String getClaveNueva() {
        return claveNueva.getText();
    }

    public String getConfirmarClave() {
        return confirmarClave.getText();
    }

    public JPanel getPanel() {
        return cambiarClave;
    }

    public void addConfirmarListener(ActionListener listener) {
        confirmarButton.addActionListener(listener);
    }

    public void addCancelarListener(ActionListener listener) {
        cancelarButton.addActionListener(listener);
    }

    public void cerrar(){
        SwingUtilities.getWindowAncestor(cambiarClave).dispose();
    }

    public void enviarMensaje(String mensaje, String titulo, int tipo){
        JOptionPane.showMessageDialog(cambiarClave,mensaje,titulo,tipo);
    }

}
