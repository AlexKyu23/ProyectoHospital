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


public class AdminController {
    private final AdminModel model;
    private final AdminView view;

    public AdminController(AdminModel model, AdminView view) throws Exception {
        this.model = model;
        this.view = view;

        inicializarTabs();
    }

    private void inicializarTabs() throws Exception {
        // MÉDICOS
        MedicoModel medicoModel = new MedicoModel();
        MedicoView medicoView = new MedicoView();
        new MedicoController(medicoModel, medicoView);
        view.getTabbedPane().addTab("Médicos", medicoView.getMainPanel());

        // FARMACÉUTICOS
        FarmaceutaModel farmModel = new FarmaceutaModel();
        FarmaceutaView farmView = new FarmaceutaView();
        new FarmaceutaController(farmView,farmModel);
        view.getTabbedPane().addTab("Farmacéuticos", farmView.getMainPanel());

        // PACIENTES
        PacienteModel pacienteModel = new PacienteModel();
        PacienteView pacienteView = new PacienteView();
        new PacienteController(pacienteModel, pacienteView);
        view.getTabbedPane().addTab("Pacientes", pacienteView.getMainPanel());

        // MEDICAMENTOS
        MedicamentoModel medicamentoModel = new MedicamentoModel();
        MedicamentoView medicamentoView = new MedicamentoView();
        new MedicamentoController(medicamentoView, medicamentoModel);
        view.getTabbedPane().addTab("Medicamentos", medicamentoView.getMainPanel());

        // HISTÓRICO DE RECETAS
        RecetaModel recetaModel = new RecetaModel();
        RecetaView recetaView = new RecetaView();
        new RecetaController(recetaModel, recetaView);
        view.getTabbedPane().addTab("Histórico de Recetas", recetaView.getMainPanel());

        view.getTabbedPane().setSelectedIndex(0);
    }
}
