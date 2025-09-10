import Clases.Admin.logic.Admin;
import Clases.Admin.presentation.AdminController;
import Clases.Admin.presentation.AdminModel;
import Clases.Admin.presentation.AdminView;
import Clases.Farmaceuta.logic.Farmaceuta;
import Clases.Farmaceuta.presentation.FarmaceutaModel;
import Clases.Medicamento.logic.Medicamento;
import Clases.Medicamento.presentation.MedicamentoModel;
import Clases.Medico.logic.Medico;
import Clases.Medico.presentation.MedicoModel;
import Clases.Paciente.logic.Paciente;
import Clases.Paciente.presentation.PacienteModel;


import java.time.LocalDate;

public class app {
    public static void main(String[] args) {
        // 🔹 Admin
        Admin admin = new Admin("001", "SuperAdmin", "1234");
        AdminModel adminModel = new AdminModel();
        adminModel.setCurrent(admin);

        // 🔹 Datos quemados
        PacienteModel pacienteModel = new PacienteModel();
        pacienteModel.addPaciente(new Paciente("P-001", "Ana López", "8888-1234", LocalDate.of(1990, 3, 12)));
        pacienteModel.addPaciente(new Paciente("P-002", "Luis Mora", "8999-5678", LocalDate.of(1985, 7, 25)));

        MedicoModel medicoModel = new MedicoModel();
        medicoModel.addMedico(new Medico("MED-111", "David", "123","Pediatría"));
        medicoModel.addMedico(new Medico("MED-222", "Miguel", "123","Neurocirugía"));

        MedicamentoModel medicamentoModel = new MedicamentoModel();
        medicamentoModel.addMedicamento(new Medicamento("Paracetamol", "Analgésico y antipirético", 101));
        medicamentoModel.addMedicamento(new Medicamento("Amoxicilina", "Antibiótico de amplio espectro", 102));

        FarmaceutaModel farmaceutaModel = new FarmaceutaModel();
        farmaceutaModel.addFarmaceuta(new Farmaceuta("FARM-01", "Carla Jiménez", "FARM-01"));
        farmaceutaModel.addFarmaceuta(new Farmaceuta("FARM-02", "José Ramírez", "FARM-02"));

        // 🔹 Vista principal
        AdminView adminView = new AdminView();

        // 🔹 Controlador con modelos pre-cargados
        new AdminController(adminModel, adminView,
                medicoModel,
                farmaceutaModel,
                pacienteModel,
                medicamentoModel);

        adminView.setVisible(true);
    }
}
