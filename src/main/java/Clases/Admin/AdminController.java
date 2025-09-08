package Clases.Admin;

import Clases.Farmaceuta.FarmaceutaController;
import Clases.Farmaceuta.FarmaceutaModel;
import Clases.Farmaceuta.View.FarmaceutaView;
import Clases.Medicamento.MedicamentoController;
import Clases.Medicamento.MedicamentoModel;
import Clases.Medicamento.View.MedicamentoView;
import Clases.Medico.View.MedicoController;
import Clases.Medico.View.MedicoModel;
import Clases.Medico.View.MedicoView;
import Clases.Paciente.PacienteController;
import Clases.Paciente.PacienteModel;
import Clases.Paciente.View.PacienteView;

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

        // 🔹 Seleccionar por defecto Médicos
        view.getTabbedPane().setSelectedIndex(0);
    }
}
