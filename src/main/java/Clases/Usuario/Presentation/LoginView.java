package Clases.Usuario.Presentation;

import Clases.Usuario.logic.Usuario;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView {
    private  static JPanel panel1;
    private JLabel icon;
    private JPanel login;
    private JTextField id;
    private JPasswordField clave;
    private JPanel botones;
    private JButton salirButton;
    private JButton cancelarButton;
    private JButton cambiarClaveButton;

    public LoginView() {}

    public static JPanel getPanel1() { return panel1; }
    public JTextField getId(){
        return id;
    }
    public JPasswordField getClave() {
        return clave;
    }
    public JButton getSalirButton() {
        return salirButton;
    }
    public JButton getCancelarButton() {
        return cancelarButton;
    }
    public JButton getCambiarClaveButton() {
        return cambiarClaveButton;
    }

    //---

}
