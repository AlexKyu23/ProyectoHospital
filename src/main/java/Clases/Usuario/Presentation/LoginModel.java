package Clases.Usuario.Presentation;

import Clases.AbstractModel;
import Clases.Usuario.logic.Usuario;

public class LoginModel extends AbstractModel {
    private Usuario usuario;

    public LoginModel() {
        this.usuario = new Usuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        firePropertyChange("usuario");
    }
}

