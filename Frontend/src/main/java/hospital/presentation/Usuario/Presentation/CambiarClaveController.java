package hospital.presentation.Usuario.Presentation;

import hospital.logic.Service;
import hospital.presentation.Usuario.Presentation.CambiarClaveView.CambiarClaveView;
import logic.Usuario;

import javax.swing.*;

public class CambiarClaveController {
    private final CambiarClaveModel model;
    private final CambiarClaveView view;
    private final Usuario usuario;

    public CambiarClaveController(CambiarClaveModel model, CambiarClaveView view, Usuario usuario) {
        this.model = model;
        this.view = view;
        this.usuario = usuario;

        view.setController(this);
        view.setModel(model);

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

        try {
            usuario.setClave(claveNueva);
            Service.instance().updateUsuario(usuario);
            model.setNuevaClave(claveNueva);
            view.enviarMensaje("Clave correctamente cambiada.", "Clave actualizada", JOptionPane.INFORMATION_MESSAGE);
            view.cerrar();
        } catch (Exception e) {
            view.enviarMensaje("Error al guardar clave: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cancelar() {
        view.cerrar();
    }
}
