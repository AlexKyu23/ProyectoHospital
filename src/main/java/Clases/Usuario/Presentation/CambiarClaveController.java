package Clases.Usuario.Presentation;

import Clases.Usuario.Presentation.CambiarClaveView.CambiarClaveView;
import Clases.Usuario.logic.Usuario;

import javax.swing.*;

public class CambiarClaveController {
    private CambiarClaveModel model;
    private CambiarClaveView view;
    private Usuario usuario;

    public CambiarClaveController(CambiarClaveModel model, CambiarClaveView view, Usuario usuario) {
        this.model = model;
        this.view = view;
        this.usuario = usuario;

        // 🔹 Conexión MVC explícita
        view.setController(this);
        view.setModel(model);

        // 🔹 Inicializar estado
        model.setNuevaClave("");
    }

    public void guardarClave() {
        String claveActual = view.getClaveActual();
        String claveNueva = view.getClaveNueva();
        String claveConfirmar = view.getConfirmarClave();

        if (claveActual.isEmpty() || claveNueva.isEmpty() || claveConfirmar.isEmpty()) {
            view.enviarMensaje("Rellene todos los campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!usuario.getClave().equals(claveActual)) {
            view.enviarMensaje("La contraseña actual es incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!claveNueva.equals(claveConfirmar)) {
            view.enviarMensaje("La nueva contraseña y su confirmación no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        usuario.setClave(claveNueva);
        model.setNuevaClave(claveNueva);

        view.enviarMensaje("Clave correctamente cambiada.", "Clave actualizada", JOptionPane.INFORMATION_MESSAGE);
        view.cerrar();
    }

    public void cancelar() {
        view.cerrar();
    }
}

