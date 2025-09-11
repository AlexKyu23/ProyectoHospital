package Clases.Usuario.Presentation;

import Clases.Usuario.logic.Sesion;
import Clases.Usuario.logic.Usuario;
import Clases.Usuario.logic.Service;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {

    private LoginView view;
    private LoginModel model;

    public LoginController(LoginView view, LoginModel model) {
        this.view = view;
        this.model = model;
    }

    public void login(Usuario usuario) throws Exception {
        Usuario logged= Service.instance().read(usuario);
        if(!logged.getClave().equals(usuario.getClave())){
            throw new Exception("Usuario o clave incorrecto");
        }
        Sesion.setUsuario(logged);
    }

    private void initController() {
        view.getId().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //AÑADIR
            }
        });

        view.getPassword().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //AÑADIR
            }
        });

        view.getSalirButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //AÑADIR
            }
        });

        view.getCancelarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //AÑADIR
            }
        });

        view.getCambiarClaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //cambiarClave();
            }
        });

        /*private void cambiarClave(){

        }*/

    }
}
