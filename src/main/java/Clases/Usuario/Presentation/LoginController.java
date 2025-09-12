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
        initController();
    }

    private void initController() {
        view.getEntrarButton().addActionListener(e -> {
            try {
                String id = view.getId().getText();
                String clave = new String(view.getClave().getPassword());
                Usuario intento = new Usuario(id, "", clave, "");
                Usuario logged = Service.instance().read(intento);

                if (!logged.verificarClave(clave)) {
                    throw new Exception("Clave incorrecta");
                }

                Sesion.setUsuario(logged);
                JOptionPane.showMessageDialog(view.getPanel(), "Bienvenido " + logged.getNombre());
                loginFrame.setVisible(false);

                // Aquí podrías redirigir según rol
                System.out.println("Rol: " + logged.getRol());

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view.getPanel(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        view.getCancelarButton().addActionListener(e -> {
            view.getId().setText("");
            view.getClave().setText("");
        });

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
