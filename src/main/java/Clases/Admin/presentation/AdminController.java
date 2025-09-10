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
import Clases.Prescribir.presentation.PrescribirController;
import Clases.Prescribir.presentation.PrescribirView;
import Clases.Prescribir.presentation.PrescripcionModel;

public class AdminController {
    private AdminModel model;
    private AdminView view;

    public AdminController(AdminModel model, AdminView view) {
        this.model = model;
        this.view = view;

        inicializarTabs();
    }

    private void inicializarTabs() {
        //🔹 Medicos
       MedicoModel medicoModel = new MedicoModel();
        MedicoView medicoView = new MedicoView();
        new MedicoController(medicoModel, medicoView);
        view.getTabbedPane().addTab("Médicos", medicoView.getMainPanel());

        // 🔹 Farmaceutas
        FarmaceutaModel farmModel = new FarmaceutaModel();
        FarmaceutaView farmView = new FarmaceutaView();
        new FarmaceutaController(farmModel, farmView);
        view.getTabbedPane().addTab("Farmaceutas", farmView.getMainPanel());

        // 🔹 Pacientes
        PacienteModel pacienteModel = new PacienteModel();
        PacienteView pacienteView = new PacienteView();
        new PacienteController(pacienteModel, pacienteView);
        view.getTabbedPane().addTab("Pacientes", pacienteView.getMainPanel());

        // 🔹 Medicamentos
       MedicamentoModel medModel = new MedicamentoModel();
       MedicamentoView medView = new MedicamentoView();
       new MedicamentoController(medModel, medView);
       view.getTabbedPane().addTab("Medicamentos", medView.getMainPanel());
       //Pres
        PrescripcionModel prescModel = new PrescripcionModel();
        PrescribirView prescView = new PrescribirView();
        new PrescribirController(prescView,prescModel);
        view.getTabbedPane().addTab("Preescribir", prescView.getPanel());


        // 🔹 Seleccionar por defecto Médicos
        view.getTabbedPane().setSelectedIndex(0);
    }
}
