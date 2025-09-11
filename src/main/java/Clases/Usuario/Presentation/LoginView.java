package Clases.Usuario.Presentation;

import javax.swing.*;

public class LoginView {
    private JLabel icon;
    private JPanel login;
    private JTextField id;
    private JPasswordField clave;
    private JPanel botones;
    private JButton salirButton;
    private JButton cancelarButton;
    private JButton cambiarClaveButton;

    public JTextField getId(){
        return id;
    }
    public JPasswordField getPassword() {
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
}
