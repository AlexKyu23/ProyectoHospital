package hospital.presentation.Admin.presentation;

import hospital.presentation.Farmaceuta.presentation.FarmaceutaController;
import hospital.presentation.Farmaceuta.presentation.FarmaceutaModel;
import hospital.presentation.Farmaceuta.presentation.View.FarmaceutaView;
import hospital.presentation.Medicamento.presentation.MedicamentoController;
import hospital.presentation.Medicamento.presentation.MedicamentoModel;
import hospital.presentation.Medicamento.presentation.MedicamentoView;
import hospital.presentation.Medico.presentation.MedicoController;
import hospital.presentation.Medico.presentation.MedicoModel;
import hospital.presentation.Medico.presentation.MedicoView;
import hospital.presentation.Paciente.presentation.PacienteController;
import hospital.presentation.Paciente.presentation.PacienteModel;
import hospital.presentation.Paciente.presentation.View.PacienteView;
import hospital.presentation.Receta.Presentation.RecetaController;
import hospital.presentation.Receta.Presentation.RecetaModel;
import hospital.presentation.Receta.Presentation.RecetaView;

import javax.swing.*;

public class AdminController {
    private final AdminModel model;
    private final AdminView view;

    public AdminController(AdminModel model, AdminView view) throws Exception {
        System.out.println("📦 Iniciando AdminController...");
        this.model = model;
        this.view = view;

        // Inicializa tabs de forma asíncrona para no bloquear el UI
        inicializarTabsAsincrono();
    }

    private void inicializarTabsAsincrono() {
        System.out.println("🔧 Inicializando tabs del panel de administrador (asíncrono)...");

        // Agrega un tab de "Cargando..." temporal
        JPanel loadingPanel = new JPanel();
        loadingPanel.add(new JLabel("⏳ Cargando módulos del sistema..."));
        view.getTabbedPane().addTab("Cargando...", loadingPanel);

        // Ejecuta la inicialización en segundo plano
        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    // MÉDICOS
                    publish("Inicializando Médicos...");
                    MedicoModel medicoModel = new MedicoModel();
                    MedicoView medicoView = new MedicoView();
                    new MedicoController(medicoModel, medicoView);
                    SwingUtilities.invokeLater(() ->
                            view.getTabbedPane().addTab("Médicos", medicoView.getMainPanel())
                    );

                    // FARMACÉUTICOS
                    publish("Inicializando Farmacéuticos...");
                    FarmaceutaModel farmModel = new FarmaceutaModel();
                    FarmaceutaView farmView = new FarmaceutaView();
                    new FarmaceutaController(farmView, farmModel);
                    SwingUtilities.invokeLater(() ->
                            view.getTabbedPane().addTab("Farmacéuticos", farmView.getMainPanel())
                    );

                    // PACIENTES
                    publish("Inicializando Pacientes...");
                    PacienteModel pacienteModel = new PacienteModel();
                    PacienteView pacienteView = new PacienteView();
                    new PacienteController(pacienteModel, pacienteView);
                    SwingUtilities.invokeLater(() ->
                            view.getTabbedPane().addTab("Pacientes", pacienteView.getMainPanel())
                    );

                    // MEDICAMENTOS
                    publish("Inicializando Medicamentos...");
                    MedicamentoModel medicamentoModel = new MedicamentoModel();
                    MedicamentoView medicamentoView = new MedicamentoView();
                    new MedicamentoController(medicamentoView, medicamentoModel);
                    SwingUtilities.invokeLater(() ->
                            view.getTabbedPane().addTab("Medicamentos", medicamentoView.getMainPanel())
                    );

                    // HISTÓRICO DE RECETAS
                    publish("Inicializando Histórico de Recetas...");
                    RecetaModel recetaModel = new RecetaModel();
                    RecetaView recetaView = new RecetaView();
                    new RecetaController(recetaModel, recetaView);
                    SwingUtilities.invokeLater(() ->
                            view.getTabbedPane().addTab("Histórico de Recetas", recetaView.getMainPanel())
                    );

                } catch (Exception e) {
                    System.err.println("❌ Error inicializando tabs:");
                    e.printStackTrace();
                    throw e;
                }
                return null;
            }

            @Override
            protected void process(java.util.List<String> chunks) {
                // Actualiza el label de carga con el progreso
                for (String msg : chunks) {
                    System.out.println("  └─ " + msg);
                    ((JLabel)((JPanel)view.getTabbedPane().getComponentAt(0)).getComponent(0))
                            .setText("⏳ " + msg);
                }
            }

            @Override
            protected void done() {
                try {
                    get(); // Lanza excepción si hubo error

                    // Remueve el tab de carga
                    SwingUtilities.invokeLater(() -> {
                        view.getTabbedPane().removeTabAt(0);
                        view.getTabbedPane().setSelectedIndex(0);
                        System.out.println("✅ Todas las tabs inicializadas correctamente");
                    });
                } catch (Exception e) {
                    System.err.println("❌ Error en inicialización:");
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(
                            view,
                            "Error al cargar módulos:\n" + e.getMessage(),
                            "Error de Inicialización",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };

        worker.execute();
        System.out.println("✅ AdminController inicializado (carga en progreso)");
    }
}