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
            // 🔐 Simular usuario administrador
            Admin admin = new Admin("adm01", "Administrador", "1");

            // 🧩 Crear modelo y vista
            AdminModel adminModel = new AdminModel(admin.getId(), admin.getNombre(), admin.getClave());
            adminModel.setCurrent(admin);

            AdminView adminView = new AdminView();
            adminView.setModel(adminModel);

            // 🖼️ Crear ventana
            JFrame adminFrame = new JFrame("Panel de Administrador");
            adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            adminFrame.setSize(1000, 700);
            adminFrame.setLocationRelativeTo(null);
            adminFrame.setContentPane(adminView.getMainPanel());
            adminFrame.setVisible(true);

            // 🔧 Inicializar controlador
            try {
                AdminController adminController = new AdminController(adminModel, adminView);
                adminView.setController(adminController);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Error al inicializar panel de administrador:\n" + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }

            adminFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    System.out.println("✅ Guardado completo al cerrar (admin).");
                }
            });
        });
    }
}