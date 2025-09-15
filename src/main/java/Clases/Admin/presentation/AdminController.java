package Clases.Admin.presentation;

import Clases.Farmaceuta.presentation.FarmaceutaController;
import Clases.Farmaceuta.presentation.FarmaceutaModel;
import Clases.Farmaceuta.presentation.View.FarmaceutaView;

import Clases.Medicamento.presentation.MedicamentoController;
import Clases.Medicamento.presentation.MedicamentoModel;
import Clases.Medicamento.presentation.MedicamentoView;

import Clases.Medico.presentation.MedicoController;
import Clases.Medico.presentation.MedicoModel;
import Clases.Medico.presentation.MedicoView;

import Clases.Paciente.presentation.PacienteController;
import Clases.Paciente.presentation.PacienteModel;
import Clases.Paciente.presentation.View.PacienteView;

import Clases.Dashboard.presentation.DashboardView;
import Clases.Receta.Data.RepositorioRecetas;
import Clases.Receta.Presentation.RecetaModel;
import Clases.Receta.Presentation.RecetaView;
import Clases.Receta.Presentation.RecetaHistorialController;
import Clases.AcercaDe.presentation.AcercaDeView;
import Clases.Medicamento.data.catalogoMedicamentos;

public class AdminController {
    private AdminModel model;
    private AdminView view;

    private MedicoModel medicoModel;
    private FarmaceutaModel farmModel;
    private PacienteModel pacienteModel;
    private MedicamentoModel medicamentoModel;

    public AdminController(AdminModel model, AdminView view,
                           MedicoModel medicoModel,
                           FarmaceutaModel farmModel,
                           PacienteModel pacienteModel,
                           MedicamentoModel medicamentoModel,
                           RepositorioRecetas repositorioRecetas,
                           catalogoMedicamentos medicamentos) {
        this.model = model;
        this.view = view;
        this.medicoModel = medicoModel;
        this.farmModel = farmModel;
        this.pacienteModel = pacienteModel;
        this.medicamentoModel = medicamentoModel;

        RepositorioRecetas.cargar();
        inicializarTabs(repositorioRecetas, medicamentos);
    }

    private void inicializarTabs(RepositorioRecetas repositorioRecetas, catalogoMedicamentos medicamentos) {
        MedicoView medicoView = new MedicoView();
        new MedicoController(medicoModel, medicoView);
        view.getTabbedPane().addTab("Médicos", medicoView.getMainPanel());

        FarmaceutaView farmView = new FarmaceutaView();
        new FarmaceutaController(farmModel, farmView);
        view.getTabbedPane().addTab("Farmacéuticos", farmView.getMainPanel());

        PacienteView pacienteView = new PacienteView();
        new PacienteController(pacienteModel, pacienteView);
        view.getTabbedPane().addTab("Pacientes", pacienteView.getMainPanel());

        MedicamentoView medView = new MedicamentoView();
        new MedicamentoController(medicamentoModel, medView);
        view.getTabbedPane().addTab("Medicamentos", medView.getMainPanel());

        DashboardView dashboardView = new DashboardView(repositorioRecetas, medicamentos);
        view.getTabbedPane().addTab("Dashboard", dashboardView.getDashboard());

        // 🔹 Histórico de Recetas como tab
        RecetaModel recetaModel = new RecetaModel();
        RecetaView recetaView = new RecetaView();
        new RecetaHistorialController(recetaModel, recetaView);
        view.getTabbedPane().addTab("Histórico de Recetas", recetaView.getPanel());

        // AcercaDeView acercaDeView = new AcercaDeView();
        // view.getTabbedPane().addTab("Acerca de", acercaDeView.getAcercaDe());

        view.getTabbedPane().setSelectedIndex(0);
    }
}
