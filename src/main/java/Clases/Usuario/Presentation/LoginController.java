package Clases.Usuario.Presentation;

import Clases.Usuario.logic.Sesion;
import Clases.Usuario.logic.Usuario;
import Clases.Usuario.logic.UsuarioService;

import javax.swing.*;

/**
 * Controlador del login. Maneja autenticación, limpieza de campos y cambio de clave.
 */
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

        // 🔹 Estado inicial
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

        Usuario u = UsuarioService.instance().readById(id);
        if (u == null || !u.getClave().equals(clave)) {
            JOptionPane.showMessageDialog(view.getPanel(), "Credenciales inválidas", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 🔹 Usuario autenticado
        Sesion.setUsuario(u);
        model.setCurrent(u);
        model.setAutenticado(true);
        loginFrame.setVisible(false);
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

        Usuario u = UsuarioService.instance().readById(id);
        if (u == null || !u.getClave().equals(claveActual)) {
            JOptionPane.showMessageDialog(view.getPanel(), "Clave actual incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nuevaClave = JOptionPane.showInputDialog(view.getPanel(), "Ingrese nueva clave:");
        if (nuevaClave != null && !nuevaClave.isEmpty()) {
            u.setClave(nuevaClave);
            UsuarioService.instance().update(u);
            JOptionPane.showMessageDialog(view.getPanel(), "Clave actualizada");
        }
    }
}
