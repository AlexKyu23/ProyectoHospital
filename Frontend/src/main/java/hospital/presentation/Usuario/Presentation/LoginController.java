package hospital.presentation.Usuario.Presentation;

import hospital.logic.Service;
import logic.Sesion;
import logic.Usuario;

import javax.swing.*;

public class LoginController {
    private final LoginModel model;
    private final LoginView view;
    private final JFrame loginFrame;

    public LoginController(LoginModel model, LoginView view, JFrame loginFrame) {
        this.model = model;
        this.view = view;
        this.loginFrame = loginFrame;

        view.setController(this);
        view.setModel(model);

        model.setCurrent(new Usuario());
        model.setAutenticado(false);
    }

    public void entrar() {
        String id = view.getId().getText().trim();
        String clave = new String(view.getClave().getPassword());

        if (id.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(view.getPanel(), "Debe completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Usuario u = Service.instance().readUsuario(id);
            if (u == null || !u.getClave().equals(clave)) {
                JOptionPane.showMessageDialog(view.getPanel(), "Credenciales inválidas", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Sesion.setUsuario(u);
            model.setCurrent(u);
            model.setAutenticado(true);
            loginFrame.setVisible(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getPanel(), "Error al autenticar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiar() {
        view.getId().setText("");
        view.getClave().setText("");
        model.setCurrent(new Usuario());
        model.setAutenticado(false);
    }

    public void cambiarClave() {
        String id = view.getId().getText().trim();
        String claveActual = new String(view.getClave().getPassword());

        try {
            Usuario u = Service.instance().readUsuario(id);
            if (u == null || !u.getClave().equals(claveActual)) {
                JOptionPane.showMessageDialog(view.getPanel(), "Clave actual incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nuevaClave = JOptionPane.showInputDialog(view.getPanel(), "Ingrese nueva clave:");
            if (nuevaClave != null && !nuevaClave.isEmpty()) {
                u.setClave(nuevaClave);
                Service.instance().updateUsuario(u);
                JOptionPane.showMessageDialog(view.getPanel(), "Clave actualizada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getPanel(), "Error al cambiar clave: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
