package Clases.Usuario.Presentation;

import Clases.Usuario.logic.Sesion;
import Clases.Usuario.logic.Usuario;
import Clases.Usuario.logic.Service;

import javax.swing.*;

public class LoginController {
    private LoginView view;
    private LoginModel model;
    private JFrame loginFrame;

    public LoginController(LoginView view, LoginModel model, JFrame loginFrame) {
        this.view = view;
        this.model = model;
        this.loginFrame = loginFrame;
        initController(); // ✅ Activar listeners
    }

    public void login(Usuario usuario) throws Exception {
        Usuario logged = Service.instance().read(usuario);
        if (!logged.verificarClave(usuario.getClave())) {
            throw new Exception("Usuario o clave incorrecto");
        }
        Sesion.setUsuario(logged);
    }

    public void salir() {
        System.exit(0);
    }

    private void initController() {
        view.getEntrarButton().addActionListener(e -> {
            try {
                String id = view.getId().getText();
                String clave = new String(view.getClave().getPassword());
                Usuario intento = new Usuario(id, "", clave, "");
                login(intento);
                JOptionPane.showMessageDialog(view.getPanel(), "Bienvenido " + Sesion.getUsuario().getNombre());
                loginFrame.setVisible(false); // Ocultar login
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view.getPanel(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        view.getCancelarButton().addActionListener(e -> {
            view.getId().setText("");
            view.getClave().setText("");
        });

        view.getEntrarButton().addActionListener(e -> salir());

        view.getCambiarClaveButton().addActionListener(e -> cambiarClave());
    }

    private void cambiarClave() {
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
