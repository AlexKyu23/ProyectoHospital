package Clases.Usuario.Presentation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView /*implements PropertyChangeListener */{
    private /*static*/ JPanel panel;
    private JLabel icon;
    private JPanel login;
    private JTextField id;
    private JPasswordField clave;
    private JPanel botones;
    private JButton entrarButton;
    private JButton cancelarButton;
    private JButton cambiarClaveButton;

    LoginController loginController;
    LoginModel loginModel;

    public LoginView() {}

    public /*static*/ JPanel getPanel() { return panel; }
    public JTextField getId(){
        return id;
    }
    public JPasswordField getClave() {
        return clave;
    }
    public JButton getSalirButton() {
        return entrarButton;
    }
    public JButton getCancelarButton() {
        return cancelarButton;
    }
    public JButton getCambiarClaveButton() {
        return cambiarClaveButton;
    }


    /*
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        switch (e.getPropertyName()) {
            case LoginModel.ID:
                id.setText(LoginModel.getID());
                break;
            case LoginModel.CLAVE:
                clave.setText(LoginModel.getClave());
                break;
        }
    }

    public void init() {

        entrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idText = id.getText();
                String claveText = clave.getText();

                if(idText.isEmpty() || claveText.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Completar todos los espacios para continuar", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    loginController.entrar(idText, claveText);
                }
            }
        })

        cancelarButton.addActionListener(new ActionListener() {             //salir
            @Override
            public void actionPerformed(ActionEvent e){
                loginController.salir();
            }
        });

        cambiarClaveButton.addActionListener(new ActionListener() {         //cambiar clave
            @Override
            public void actionPerformed(ActionEvent e){
                String idText = id.getText();
                loginController.cambiarClave(idText);
            }
        });
    }*/

}
