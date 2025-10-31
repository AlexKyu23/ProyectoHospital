package hospital.presentation.Usuario.Presentation;

import logic.Usuario;

public interface ThreadListener {
    public void deliver_message(Usuario usuario, String message);
    public void deliver_login(Usuario usuario);
    public void deliver_logout(Usuario usuario);
}