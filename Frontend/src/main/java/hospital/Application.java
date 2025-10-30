package hospital;

import hospital.presentation.Admin.presentation.AdminController;
import hospital.presentation.Admin.presentation.AdminModel;
import hospital.presentation.Admin.presentation.AdminView;
import hospital.presentation.Dashboard.presentation.DashboardView;
import hospital.presentation.Despacho.presentation.DespachoController;
import hospital.presentation.Despacho.presentation.DespachoModel;
import hospital.presentation.Despacho.presentation.DespachoView;
import hospital.presentation.Farmaceuta.presentation.FarmaceutaModel;
import hospital.presentation.Medicamento.presentation.MedicamentoModel;
import hospital.presentation.Medico.presentation.MedicoModel;
import hospital.presentation.Paciente.presentation.PacienteModel;
import hospital.presentation.Prescribir.presentation.PrescribirController;
import hospital.presentation.Prescribir.presentation.PrescribirView;
import hospital.presentation.Prescribir.presentation.PrescripcionModel;
import hospital.presentation.Receta.Presentation.RecetaController;
import hospital.presentation.Receta.Presentation.RecetaModel;
import hospital.presentation.Receta.Presentation.RecetaView;
import hospital.presentation.Usuario.Presentation.LoginController;
import hospital.presentation.Usuario.Presentation.LoginModel;
import hospital.presentation.Usuario.Presentation.LoginView;
import logic.Admin;
import logic.Farmaceuta;
import logic.Medico;
import logic.Usuario;

import javax.swing.*;

public class Application {
    public static final int MODE_CREATE = 1;
    public static final int MODE_EDIT = 2;

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
                    try {
                        hospital.logic.Service proxy = hospital.logic.Service.instance();

                        int totalMedicos = proxy.findAllMedicos().size();
                        int totalPacientes = proxy.findAllPacientes().size();
                        int totalMedicamentos = proxy.findAllMedicamentos().size();
                        int totalRecetas = proxy.findAllRecetas().size();

                        System.out.println("📊 Verificación de base de datos:");
                        System.out.println("   Médicos: " + totalMedicos);
                        System.out.println("   Pacientes: " + totalPacientes);
                        System.out.println("   Medicamentos: " + totalMedicamentos);
                        System.out.println("   Recetas: " + totalRecetas);

                        if (totalMedicos == 0 || totalPacientes == 0 || totalMedicamentos == 0) {
                            JOptionPane.showMessageDialog(null,
                                    "⚠️ La base de datos está vacía o incompleta.\n" +
                                            "Por favor, asegúrese de cargar datos antes de continuar.",
                                    "Advertencia",
                                    JOptionPane.WARNING_MESSAGE);
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null,
                                "❌ Error al verificar la base de datos:\n" + e.getMessage(),
                                "Error de conexión",
                                JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                    }

                    if ("ADM".equalsIgnoreCase(u.getRol())) {
                        System.out.println("🔑 Iniciando sesión como ADMIN...");

                        Admin admin = new Admin(u.getId(), u.getNombre(), u.getClave());
                        AdminModel adminModel = new AdminModel(admin.getId(), admin.getNombre(), admin.getClave());
                        adminModel.setCurrent(admin);

                        AdminView adminView = new AdminView();
                        adminView.setModel(adminModel);

                        JFrame adminFrame = new JFrame("Panel de Administrador");
                        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        System.out.println("🖼️ Configurando ventana...");
                        adminFrame.setContentPane(adminView.getMainPanel());
                        adminFrame.setSize(1000, 700);
                        adminFrame.setLocationRelativeTo(null);
                        adminFrame.setVisible(true);
                        System.out.println("✅ Ventana de admin mostrada");

                        // Inicializa el controller DESPUÉS de mostrar la ventana
                        try {
                            System.out.println("🔧 Inicializando AdminController...");
                            AdminController adminController = new AdminController(adminModel, adminView);
                            adminView.setController(adminController);
                        } catch (Exception e) {
                            System.err.println("❌ ERROR al inicializar panel de administrador:");
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null,
                                    "Error al inicializar panel de administrador:\n" + e.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                        adminFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                System.out.println("✅ Guardado completo al cerrar (admin).");
                            }
                        });

                    } else if ("MED".equalsIgnoreCase(u.getRol())) {
                        Medico med = new Medico(u.getId(), u.getNombre(), u.getClave(), "");

                        PrescripcionModel prescModel = new PrescripcionModel();
                        prescModel.setMedico(med);

                        PrescribirView prescView = new PrescribirView();
                        try {
                            new PrescribirController(
                                    prescView, prescModel,
                                    med,
                                    new PacienteModel(),
                                    new MedicamentoModel()
                            );
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        RecetaModel recetaModelMed = new RecetaModel();
                        RecetaView recetaViewMed = new RecetaView();
                        try {
                            new RecetaController(recetaModelMed, recetaViewMed);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        JTabbedPane tabbedPane = new JTabbedPane();
                        tabbedPane.addTab("Prescripción", prescView.getPanel());
                        tabbedPane.addTab("Histórico de Recetas", recetaViewMed.getTableRecetas());
                        tabbedPane.addTab("Dashboard", new DashboardView().getDashboard());

                        JFrame prescFrame = new JFrame("Panel Médico");
                        prescFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        prescFrame.setSize(900, 600);
                        prescFrame.setLocationRelativeTo(null);
                        prescFrame.setContentPane(tabbedPane);
                        prescFrame.setVisible(true);

                        prescFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                System.out.println("✅ Guardado completo al cerrar (médico).");
                            }
                        });

                    } else if ("FAR".equalsIgnoreCase(u.getRol())) {
                        Farmaceuta farm = new Farmaceuta(u.getId(), u.getNombre(), u.getClave());

                        DespachoModel despachoModel = new DespachoModel();
                        despachoModel.setFarmaceuta(farm);

                        DespachoView despachoView = new DespachoView();
                        try {
                            new DespachoController(despachoModel, despachoView);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        RecetaModel recetaModelFar = new RecetaModel();
                        RecetaView recetaViewFar = new RecetaView();
                        try {
                            new RecetaController(recetaModelFar, recetaViewFar);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        JTabbedPane tabbedPane = new JTabbedPane();
                        tabbedPane.addTab("Despacho", despachoView.getDespacho());
                        tabbedPane.addTab("Histórico de Recetas", recetaViewFar.getTableRecetas());
                        tabbedPane.addTab("Dashboard", new DashboardView().getDashboard());

                        JFrame despachoFrame = new JFrame("Panel Farmacéutico");
                        despachoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        despachoFrame.setSize(900, 600);
                        despachoFrame.setLocationRelativeTo(null);
                        despachoFrame.setContentPane(tabbedPane);
                        despachoFrame.setVisible(true);

                        despachoFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                System.out.println("✅ Guardado completo al cerrar (farmacéutico).");
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