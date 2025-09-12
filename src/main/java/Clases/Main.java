package Clases;

import Clases.Usuario.Presentation.LoginView;
import Clases.Usuario.Presentation.LoginModel;
import Clases.Usuario.Presentation.LoginController;
import Clases.Usuario.logic.Usuario;

import Clases.Admin.logic.Admin;
import Clases.Admin.presentation.AdminController;
import Clases.Admin.presentation.AdminModel;
import Clases.Admin.presentation.AdminView;

import Clases.Medico.presentation.MedicoModel;
import Clases.Farmaceuta.presentation.FarmaceutaModel;
import Clases.Paciente.presentation.PacienteModel;
import Clases.Medicamento.presentation.MedicamentoModel;

import Clases.Medico.logic.MedicoService;
import Clases.Farmaceuta.logic.FarmaceutaService;
import Clases.Paciente.logic.PacienteService;
import Clases.Medicamento.logic.MedicamentoService;
import Clases.Usuario.logic.UsuarioService;
import Clases.Prescribir.data.RepositorioPrescripciones;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame loginFrame = new JFrame("Login");
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setSize(400, 300);
            loginFrame.setLocationRelativeTo(null);

            LoginView view = new LoginView();
            LoginModel model = new LoginModel();
            new LoginController(model, view, loginFrame);

            loginFrame.setContentPane(view.getPanel());
            loginFrame.setVisible(true);

            model.addPropertyChangeListener(evt -> {
                if (LoginModel.AUTENTICADO.equals(evt.getPropertyName()) && model.isAutenticado()) {
                    Usuario u = model.getCurrent();
                    loginFrame.dispose();

                    if ("ADM".equalsIgnoreCase(u.getRol())) {
                        MedicoModel medicoModel = new MedicoModel();
                        FarmaceutaModel farmaceutaModel = new FarmaceutaModel();
                        PacienteModel pacienteModel = new PacienteModel();
                        MedicamentoModel medicamentoModel = new MedicamentoModel();

                        medicoModel.setList(MedicoService.instance().findAll());
                        farmaceutaModel.setList(FarmaceutaService.instance().findAll());
                        pacienteModel.setList(PacienteService.instance().findAll());
                        medicamentoModel.setList(MedicamentoService.instance().findAll());
                        RepositorioPrescripciones.cargar();

                        AdminView adminView = new AdminView();
                        AdminModel adminModel = new AdminModel(u.getId(), u.getNombre(), u.getClave());
                        adminModel.setCurrent(new Admin(u.getId(), u.getNombre(), u.getClave()));

                        new AdminController(adminModel, adminView,
                                medicoModel,
                                farmaceutaModel,
                                pacienteModel,
                                medicamentoModel);

                        adminView.setVisible(true);

                        adminView.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                MedicoService.instance().guardar();
                                FarmaceutaService.instance().guardar();
                                PacienteService.instance().guardar();
                                MedicamentoService.instance().guardar();
                                UsuarioService.instance().guardar();
                                RepositorioPrescripciones.guardar();
                                System.out.println("✅ Guardado completo al cerrar.");
                            }
                        });
                    } else {
                        JOptionPane.showMessageDialog(null, "Acceso denegado. Solo el administrador puede ingresar aquí.");
                    }
                }
            });
        });
    }
}
