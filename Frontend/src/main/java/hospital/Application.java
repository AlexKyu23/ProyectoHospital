package hospital;

import hospital.logic.Service;
import hospital.presentation.Farmaceuta.presentation.FarmaceutaController;
import hospital.presentation.Farmaceuta.presentation.FarmaceutaModel;
import hospital.presentation.Farmaceuta.presentation.View.FarmaceutaView;
import hospital.presentation.Medico.presentation.MedicoController;
import hospital.presentation.Medico.presentation.MedicoModel;
import hospital.presentation.Medico.presentation.MedicoView;
import hospital.presentation.Medicamento.presentation.MedicamentoController;
import hospital.presentation.Medicamento.presentation.MedicamentoModel;
import hospital.presentation.Medicamento.presentation.MedicamentoView;
import hospital.presentation.Paciente.presentation.PacienteController;
import hospital.presentation.Paciente.presentation.PacienteModel;
import hospital.presentation.Paciente.presentation.View.PacienteView;
import hospital.presentation.Receta.Presentation.RecetaController;
import hospital.presentation.Receta.Presentation.RecetaModel;
import hospital.presentation.Receta.Presentation.RecetaView;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application {
    public static final int MODE_CREATE = 1;
    public static final int MODE_EDIT = 2;
    public static Border BORDER_ERROR = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED);

    public static JFrame window;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        window = new JFrame("Sistema Hospitalario");
        JTabbedPane tabs = new JTabbedPane();
        window.setContentPane(tabs);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Service.instance().stop();
                System.out.println("✅ Conexión cerrada correctamente.");
            }
        });

        // 🔹 MÉDICOS
        MedicoModel medicoModel = new MedicoModel();
        MedicoView medicoView = new MedicoView();
        try {
            new MedicoController(medicoModel, medicoView);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar módulo Médico: " + e.getMessage());
        }
        tabs.addTab("Médicos", medicoView.getMainPanel());

        // 🔹 FARMACÉUTICOS
        FarmaceutaModel farmModel = new FarmaceutaModel();
        FarmaceutaView farmView = new FarmaceutaView();
        try {
            new FarmaceutaController(farmView,farmModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar módulo Farmacéutico: " + e.getMessage());
        }
        tabs.addTab("Farmacéuticos", farmView.getMainPanel());

        // 🔹 PACIENTES
        PacienteModel pacModel = new PacienteModel();
        PacienteView pacView = new PacienteView();
        try {
            new PacienteController(pacModel, pacView);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar módulo Paciente: " + e.getMessage());
        }
        tabs.addTab("Pacientes", pacView.getMainPanel());

        // 🔹 MEDICAMENTOS
        MedicamentoModel medModel = new MedicamentoModel();
        MedicamentoView medView = new MedicamentoView();
        try {
            new MedicamentoController(medView, medModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar módulo Medicamento: " + e.getMessage());
        }
        tabs.addTab("Medicamentos", medView.getMainPanel());

        // 🔹 HISTÓRICO DE RECETAS
        RecetaModel recetaModel = new RecetaModel();
        RecetaView recetaView = new RecetaView();
        try {
            new RecetaController(recetaModel, recetaView);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar módulo Recetas: " + e.getMessage());
        }
        tabs.addTab("Recetas", recetaView.getMainPanel());

        // 🔹 Ventana principal
        window.setSize(1000, 700);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
}
