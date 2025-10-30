package hospital.presentation.Usuario.Presentation;

import logic.Usuario;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView extends JPanel implements PropertyChangeListener {
    private LoginController controller;
    private LoginModel model;

    private JPanel panel;
    private JLabel icon;
    private JPanel login;
    private JTextField id;
    private JPasswordField clave;
    private JPanel botones;
    private JButton entrarButton;
    private JButton cancelarButton;
    private JButton cambiarClaveButton;

    public LoginView() {}

    public void setController(LoginController controller) {
        this.controller = controller;
        initListeners();
    }

    public void setModel(LoginModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    private void initListeners() {
        entrarButton.addActionListener(e -> controller.entrar());
        cancelarButton.addActionListener(e -> controller.limpiar());
        cambiarClaveButton.addActionListener(e -> controller.cambiarClave());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case LoginModel.CURRENT -> {
                Usuario u = model.getCurrent();
                id.setText(u.getId());
                clave.setText(u.getClave());
            }
            case LoginModel.AUTENTICADO -> {
                if (model.isAutenticado()) {
                    JOptionPane.showMessageDialog(panel, "Bienvenido " + model.getCurrent().getNombre());
                }
            }
        }
        panel.revalidate();
    }

    // 🔹 Getters para el controlador
    public JPanel getPanel() { return panel; }
    public JTextField getId() { return id; }
    public JPasswordField getClave() { return clave; }
    public JButton getEntrarButton() { return entrarButton; }
    public JButton getCancelarButton() { return cancelarButton; }
    public JButton getCambiarClaveButton() { return cambiarClaveButton; }
}
