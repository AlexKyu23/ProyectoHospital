package Clases;

import Clases.Usuario.Presentation.LoginView;
import Clases.Usuario.Presentation.LoginModel;
import Clases.Usuario.Presentation.LoginController;
import Clases.Usuario.logic.Usuario;

import Clases.Admin.logic.Admin;
import Clases.Admin.presentation.AdminController;
import Clases.Admin.presentation.AdminModel;
import Clases.Admin.presentation.AdminView;

import Clases.Medico.logic.Medico;
import Clases.Prescribir.presentation.PrescribirController;
import Clases.Prescribir.presentation.PrescribirView;
import Clases.Prescribir.presentation.PrescripcionModel;

import Clases.Farmaceuta.logic.Farmaceuta;
import Clases.Farmaceuta.presentation.FarmaceutaModel;
import Clases.Farmaceuta.presentation.View.FarmaceutaView;
import Clases.Farmaceuta.presentation.FarmaceutaController;

import Clases.Despacho.presentation.DespachoModel;
import Clases.Despacho.presentation.DespachoView;
import Clases.Despacho.presentation.DespachoController;

import Clases.Paciente.presentation.PacienteModel;
import Clases.Medicamento.presentation.MedicamentoModel;
import Clases.Medico.presentation.MedicoModel;
import Clases.Admin.presentation.AdminView;
import Clases.Receta.Data.historicoRecetas;
import Clases.Medicamento.data.catalogoMedicamentos;

import Clases.Usuario.logic.UsuarioService;
import Clases.Prescribir.data.RepositorioPrescripciones;
import Clases.DatosIniciales;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // 🔹 Cargar todas las listas desde XML
        DatosIniciales.cargarTodo();
        historicoRecetas recetas = DatosIniciales.gethistoricoRecetas();
        catalogoMedicamentos medicamentos = DatosIniciales.getCatalogoMed();

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
                        Admin admin = new Admin(u.getId(), u.getNombre(), u.getClave());
                        AdminModel adminModel = new AdminModel(admin.getId(), admin.getNombre(), admin.getClave());
                        adminModel.setCurrent(admin);

                        AdminView adminView = new AdminView();

                        MedicoModel medicoModel = new MedicoModel();
                        FarmaceutaModel farmaceutaModel = new FarmaceutaModel();
                        PacienteModel pacienteModel = new PacienteModel();
                        MedicamentoModel medicamentoModel = new MedicamentoModel();

                        medicoModel.setList(DatosIniciales.listaMedicos.consulta());
                        farmaceutaModel.setList(DatosIniciales.listaFarmaceutas.consulta());
                        pacienteModel.setList(DatosIniciales.listaPacientes.consulta());
                        medicamentoModel.setList(DatosIniciales.catalogoMed.consulta());

                        new AdminController(adminModel, adminView,
                                medicoModel,
                                farmaceutaModel,
                                pacienteModel,
                                medicamentoModel, recetas, medicamentos);

                        adminView.setVisible(true);

                        adminView.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                UsuarioService.instance().guardar();
                                RepositorioPrescripciones.guardar();
                                System.out.println("✅ Guardado completo al cerrar (admin).");
                            }
                        });

                    } else if ("MED".equalsIgnoreCase(u.getRol())) {
                        Medico medico = DatosIniciales.listaMedicos.busquedaPorId(u.getId());
                        if (medico == null) {
                            medico = new Medico(u.getId(), u.getNombre(), u.getClave(), "General");
                        }

                        PrescripcionModel prescModel = new PrescripcionModel();
                        prescModel.setMedico(medico);

                        PrescribirView prescView = new PrescribirView();
                        new PrescribirController(prescView, prescModel,
                                medico,
                                DatosIniciales.listaPacientes,
                                DatosIniciales.catalogoMed);

                        JFrame prescFrame = new JFrame("Panel Médico - Prescripción");
                        prescFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        prescFrame.setSize(800, 600);
                        prescFrame.setLocationRelativeTo(null);
                        prescFrame.setContentPane(prescView.getPanel());
                        prescFrame.setVisible(true);

                        prescFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                UsuarioService.instance().guardar();
                                RepositorioPrescripciones.guardar();
                                System.out.println("✅ Guardado completo al cerrar (médico).");
                            }
                        });

                    } else if ("FAR".equalsIgnoreCase(u.getRol())) {
                        Farmaceuta farm = DatosIniciales.listaFarmaceutas.busquedaPorId(u.getId());
                        if (farm == null) {
                            farm = new Farmaceuta(u.getId(), u.getNombre(), u.getClave());
                        }

                        DespachoModel despachoModel = new DespachoModel();
                        despachoModel.setFarmaceuta(farm);

                        DespachoView despachoView = new DespachoView();
                        new DespachoController(despachoModel, despachoView);

                        JFrame despachoFrame = new JFrame("Panel Farmaceuta - Despacho");
                        despachoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        despachoFrame.setSize(800, 600);
                        despachoFrame.setLocationRelativeTo(null);
                        despachoFrame.setContentPane(despachoView.getDespacho());
                        despachoFrame.setVisible(true);

                        despachoFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                UsuarioService.instance().guardar();
                                RepositorioPrescripciones.guardar();
                                System.out.println("✅ Guardado completo al cerrar (farmaceuta).");
                            }
                        });

                    } else {
                        JOptionPane.showMessageDialog(null, "Rol no reconocido. Acceso denegado.");
                    }
                }
            });
        });
    }
}
