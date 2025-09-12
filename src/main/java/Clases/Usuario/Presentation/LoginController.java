package Clases.Usuario.Presentation;

import Clases.Usuario.logic.Sesion;
import Clases.Usuario.logic.Usuario;
import Clases.Usuario.logic.Service;

import javax.swing.*;

public class LoginController {
    private LoginModel model;
    private LoginView view;
    private JFrame loginFrame;

    public LoginController(LoginModel model, LoginView view, JFrame loginFrame) {
        this.model = model;
        this.view = view;
        this.loginFrame = loginFrame;

        // 🔹 Conexión MVC explícita
        view.setController(this);
        view.setModel(model);

        // 🔹 Inicializar estado
        model.setCurrent(new Usuario());
        model.setAutenticado(false);
    }

    public void entrar() {
        String id = view.getId().getText();
        String clave = new String(view.getClave().getPassword());

        if (id.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(view.getPanel(), "Debe completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Usuario intento = new Usuario(id, "", clave, "");
            Usuario logged = Service.instance().read(intento);

            if (!logged.verificarClave(clave)) {
                throw new Exception("Clave incorrecta");
            }

            Sesion.setUsuario(logged);
            model.setCurrent(logged);
            model.setAutenticado(true);
            loginFrame.setVisible(false);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view.getPanel(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiar() {
        view.getId().setText("");
        view.getClave().setText("");
        model.setCurrent(new Usuario());
        model.setAutenticado(false);
    }

    public void cambiarClave() {
        String id = view.getId().getText();
        String claveActual = new String(view.getClave().getPassword());

        try {
            Usuario u = Service.instance().read(new Usuario(id, "", claveActual, ""));
            if (!u.verificarClave(claveActual)) {
                JOptionPane.showMessageDialog(view.getPanel(), "Clave actual incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nuevaClave = JOptionPane.showInputDialog(view.getPanel(), "Ingrese nueva clave:");
            if (nuevaClave != null && !nuevaClave.isEmpty()) {
                u.cambiarClave(nuevaClave);
                JOptionPane.showMessageDialog(view.getPanel(), "Clave actualizada");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view.getPanel(), "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
