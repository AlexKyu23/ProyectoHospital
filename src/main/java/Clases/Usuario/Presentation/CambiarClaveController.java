package Clases.Usuario.Presentation;

import Clases.Usuario.Presentation.CambiarClaveView.CambiarClaveView;
import Clases.Usuario.logic.Usuario;

import javax.swing.*;

public class CambiarClaveController {
    private CambiarClaveView view;
    private CambiarClaveModel model;
    private Usuario usuario;

    public CambiarClaveController(CambiarClaveView view, CambiarClaveModel model, Usuario usuario) {
        this.view = view;
        this.model = model;
        this.usuario = usuario;

        view.addConfirmarListener(e -> guardarClave());
        view.addCancelarListener(e-> cancelar());
    }

    private void guardarClave() {
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

    private void cancelar() {
        view.cerrar();
    }

}
