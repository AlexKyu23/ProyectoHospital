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
            System.out.println("🔐 Intentando login con ID: " + id + " y clave: " + clave);
            Usuario u = Service.instance().readUsuario(id); // ← usa protocolo USUARIO_READ
            System.out.println("📥 Usuario recibido: " + (u != null ? u.getNombre() : "null"));

            if (u == null) {
                JOptionPane.showMessageDialog(view.getPanel(), "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!u.getClave().equals(clave)) {
                JOptionPane.showMessageDialog(view.getPanel(), "Clave incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Sesion.setUsuario(u);
            model.setCurrent(u);
            model.setAutenticado(true);
            System.out.println("✅ Autenticación exitosa para: " + u.getNombre());
            loginFrame.setVisible(false);

        } catch (Exception e) {
            String msg = e.getMessage() != null ? e.getMessage() : "Error inesperado";
            JOptionPane.showMessageDialog(view.getPanel(), "Error al autenticar: " + msg, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void limpiar() {
        view.getId().setText("");
        view.getClave().setText("");
        model.setCurrent(new Usuario());
        model.setAutenticado(false);
        System.out.println("🔄 Campos de login limpiados.");
    }

    public void cambiarClave() {
        String id = view.getId().getText().trim();
        String claveActual = new String(view.getClave().getPassword());

        if (id.isEmpty() || claveActual.isEmpty()) {
            JOptionPane.showMessageDialog(view.getPanel(), "Debe ingresar ID y clave actual", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            System.out.println("🔐 Verificando clave actual para ID: " + id);
            Usuario u = Service.instance().readUsuario(id); // ← usa protocolo USUARIO_READ
            if (u == null) {
                JOptionPane.showMessageDialog(view.getPanel(), "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!u.getClave().equals(claveActual)) {
                JOptionPane.showMessageDialog(view.getPanel(), "Clave actual incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nuevaClave = JOptionPane.showInputDialog(view.getPanel(), "Ingrese nueva clave:");
            if (nuevaClave != null && !nuevaClave.isEmpty()) {
                u.setClave(nuevaClave);
                Service.instance().updateUsuario(u);
                JOptionPane.showMessageDialog(view.getPanel(), "✅ Clave actualizada correctamente.");
                System.out.println("🔑 Clave cambiada para usuario: " + u.getId());
            }

        } catch (Exception e) {
            String msg = e.getMessage() != null ? e.getMessage() : "Error inesperado";
            JOptionPane.showMessageDialog(view.getPanel(), "Error al cambiar clave: " + msg, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
