package Clases;
import Clases.Usuario.Presentation.LoginView;
import Clases.Usuario.Presentation.LoginModel;
import Clases.Usuario.Presentation.LoginController;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crear frame contenedor
            JFrame loginFrame = new JFrame("Login");
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setSize(400, 300);
            loginFrame.setLocationRelativeTo(null);

            // Instanciar MVC
            LoginView view = new LoginView();
            LoginModel model = new LoginModel();
            new LoginController(model, view, loginFrame);

            // Mostrar vista
            loginFrame.setContentPane(view.getPanel());
            loginFrame.setVisible(true);
        });
    }
}