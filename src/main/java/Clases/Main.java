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
import Clases.Despacho.presentation.DespachoModel;
import Clases.Despacho.presentation.DespachoView;
import Clases.Despacho.presentation.DespachoController;
import Clases.Paciente.presentation.PacienteModel;
import Clases.Medicamento.presentation.MedicamentoModel;
import Clases.Medico.presentation.MedicoModel;
import Clases.Receta.Presentation.RecetaModel;
import Clases.Receta.Presentation.RecetaView;
import Clases.Receta.Presentation.RecetaHistorialController;
import Clases.Dashboard.presentation.DashboardView;
import Clases.DatosIniciales;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // 🔹 Cargar todos los datos desde sistema.xml
        DatosIniciales.cargarTodo();

        SwingUtilities.invokeLater(() -> {
            // 🔹 Mostrar ventana de login
            JFrame loginFrame = new JFrame("Login");
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setSize(400, 300);
            loginFrame.setLocationRelativeTo(null);

            LoginView view = new LoginView();
            LoginModel model = new LoginModel();
            new LoginController(model, view, loginFrame);

            loginFrame.setContentPane(view.getPanel());
            loginFrame.setVisible(true);

            // 🔹 Escuchar autenticación
            model.addPropertyChangeListener(evt -> {
                if (LoginModel.AUTENTICADO.equals(evt.getPropertyName()) && model.isAutenticado()) {
                    Usuario u = model.getCurrent();
                    loginFrame.dispose();

                    switch (u.getRol().toUpperCase()) {
                        case "ADM" -> lanzarPanelAdmin(u);
                        case "MED" -> lanzarPanelMedico(u);
                        case "FAR" -> lanzarPanelFarmaceutico(u);
                        default -> JOptionPane.showMessageDialog(null, "Rol no reconocido. Acceso denegado.");
                    }
                }
            });
        });
    }

    private static void lanzarPanelAdmin(Usuario u) {
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
                medicamentoModel,
                DatosIniciales.repositorioRecetas,
                DatosIniciales.catalogoMed);

        adminView.setVisible(true);
        adminView.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                DatosIniciales.guardarTodo();
                System.out.println("✅ Guardado completo al cerrar (admin).");
            }
        });
    }

    private static void lanzarPanelMedico(Usuario u) {
        Medico medico = DatosIniciales.listaMedicos.busquedaPorId(u.getId());
        if (medico == null) {
            medico = new Medico(u.getId(), u.getNombre(), u.getClave(), "General");
        }

        PrescripcionModel prescModel = new PrescripcionModel();
        prescModel.setMedico(medico);

        PacienteModel pacienteModel = new PacienteModel();
        pacienteModel.setList(DatosIniciales.listaPacientes.consulta());

        MedicamentoModel medicamentoModel = new MedicamentoModel();
        medicamentoModel.setList(DatosIniciales.catalogoMed.consulta());

        PrescribirView prescView = new PrescribirView();
        new PrescribirController(prescView, prescModel, medico, pacienteModel, medicamentoModel);

        RecetaModel recetaModelMed = new RecetaModel();
        RecetaView recetaViewMed = new RecetaView();
        new RecetaHistorialController(recetaModelMed, recetaViewMed);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Prescripción", prescView.getPanel());
        tabbedPane.addTab("Histórico de Recetas", recetaViewMed.getPanel());
        tabbedPane.addTab("Dashboard", new DashboardView(DatosIniciales.repositorioRecetas, DatosIniciales.catalogoMed).getDashboard());

        JFrame prescFrame = new JFrame("Panel Médico");
        prescFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        prescFrame.setSize(800, 600);
        prescFrame.setLocationRelativeTo(null);
        prescFrame.setContentPane(tabbedPane);
        prescFrame.setVisible(true);

        prescFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                DatosIniciales.guardarTodo();
                System.out.println("✅ Guardado completo al cerrar (médico).");
            }
        });
    }

    private static void lanzarPanelFarmaceutico(Usuario u) {
        Farmaceuta farm = DatosIniciales.listaFarmaceutas.busquedaPorId(u.getId());
        if (farm == null) {
            farm = new Farmaceuta(u.getId(), u.getNombre(), u.getClave());
        }

        DespachoModel despachoModel = new DespachoModel();
        despachoModel.setFarmaceuta(farm);

        DespachoView despachoView = new DespachoView();
        new DespachoController(despachoModel, despachoView);

        RecetaModel recetaModelFar = new RecetaModel();
        RecetaView recetaViewFar = new RecetaView();
        new RecetaHistorialController(recetaModelFar, recetaViewFar);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Despacho", despachoView.getDespacho());
        tabbedPane.addTab("Histórico de Recetas", recetaViewFar.getPanel());
        tabbedPane.addTab("Dashboard", new DashboardView(DatosIniciales.repositorioRecetas, DatosIniciales.catalogoMed).getDashboard());

        JFrame despachoFrame = new JFrame("Panel Farmacéuta");
        despachoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        despachoFrame.setSize(800, 600);
        despachoFrame.setLocationRelativeTo(null);
        despachoFrame.setContentPane(tabbedPane);
        despachoFrame.setVisible(true);

        despachoFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                DatosIniciales.guardarTodo();
                System.out.println("✅ Guardado completo al cerrar (farmacéuta).");
            }
        });
    }
}
