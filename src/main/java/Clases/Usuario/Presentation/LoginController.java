package Clases.Usuario.Presentation;

import Clases.Persona.logic.Persona;
import Clases.Usuario.logic.Sesion;
import Clases.Usuario.logic.Usuario;
import Clases.Usuario.logic.Service;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {

    private LoginView view;
    private LoginModel model;
    private JFrame loginFrame;

    public LoginController(LoginView view, LoginModel model, JFrame loginFrame) {
        this.view = view;
        this.model = model;
        this.loginFrame = loginFrame;
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

        view.getClave().addActionListener(new ActionListener() {
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

        /*
        private void cambiarClave(String id){

            Persona persona = Service.instance().searchPersonaPorID(id);
            if (persona == null) {
                JOptionPane.showMessageDialog(LoginView.getPanel1(), "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Mostrar pantalla para cambiar la clave

            CambiarClaveView changeView = new cambiarClaveView();
            CambiarClaveModel changeModel = new cambiarClaveModel();          //Revisar

            new CambiarClaveController(cambiarClaveView, cambiarClaveModel, persona);
            JFrame cambiarClaveFrame = new JFrame("Cambiar Clave");
            cambiarClaveFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            cambiarClaveFrame.setContentPane((JPanel) cambiarClaveFrame.getContentPane());  //Revisar
            cambiarClaveFrame.setSize(450, 300);
            cambiarClaveFrame.setLocationRelativeTo(null);

            //Volver al Login
            changeVentana.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void ventanaCerrada(java.awt.event.WindowAdapter e){
                    if(loginFrame != null){
                        loginFrame.setVisible(true);
                    }
                }

            });
        }*/

    }
}
