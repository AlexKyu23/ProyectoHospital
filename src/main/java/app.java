import Clases.Admin.logic.Admin;
import Clases.Admin.presentation.AdminController;
import Clases.Admin.presentation.AdminModel;
import Clases.Admin.presentation.AdminView;

import Clases.Farmaceuta.presentation.FarmaceutaModel;
import Clases.Farmaceuta.presentation.FarmaceutaController;
import Clases.Farmaceuta.presentation.View.FarmaceutaView;

import Clases.Medico.presentation.MedicoModel;
import Clases.Medico.presentation.MedicoController;
import Clases.Medico.presentation.MedicoView;

import Clases.Medicamento.presentation.MedicamentoModel;
import Clases.Medicamento.presentation.MedicamentoController;
import Clases.Medicamento.presentation.MedicamentoView;

import Clases.Paciente.presentation.PacienteModel;
import Clases.Paciente.presentation.PacienteController;
import Clases.Paciente.presentation.View.PacienteView;

public class app {
    public static void main(String[] args) {
        // 🔹 Admin
        Admin admin = new Admin("001", "SuperAdmin", "1234");
        AdminModel adminModel = new AdminModel("001", "SuperAdmin", "1234");
        adminModel.setCurrent(admin);

        // 🔹 Vistas
        AdminView adminView = new AdminView();
        MedicoView medicoView = new MedicoView();
        FarmaceutaView farmaceutaView = new FarmaceutaView();
        PacienteView pacienteView = new PacienteView();
        MedicamentoView medicamentoView = new MedicamentoView();

        // 🔹 Modelos
        MedicoModel medicoModel = new MedicoModel();
        FarmaceutaModel farmaceutaModel = new FarmaceutaModel();
        PacienteModel pacienteModel = new PacienteModel();
        MedicamentoModel medicamentoModel = new MedicamentoModel();

        // 🔹 Controladores
        new MedicoController(medicoModel, medicoView);
        new FarmaceutaController(farmaceutaModel, farmaceutaView);
        new PacienteController(pacienteModel, pacienteView);
        new MedicamentoController(medicamentoModel, medicamentoView);
        new AdminController(adminModel, adminView,
                medicoModel,
                farmaceutaModel,
                pacienteModel,
                medicamentoModel);

        // 🔹 Mostrar ventana principal
        adminView.setVisible(true);
    }
}
