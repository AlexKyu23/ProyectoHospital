import Clases.Admin.logic.Admin;
import Clases.Admin.presentation.AdminController;
import Clases.Admin.presentation.AdminModel;
import Clases.Admin.presentation.AdminView;

import Clases.Farmaceuta.presentation.FarmaceutaModel;
import Clases.Farmaceuta.presentation.FarmaceutaController;
import Clases.Farmaceuta.presentation.View.FarmaceutaView;

import Clases.Medicamento.data.catalogoMedicamentos;
import Clases.Medico.data.ListaMedicos;
import Clases.Medico.presentation.MedicoModel;
import Clases.Medico.presentation.MedicoController;
import Clases.Medico.presentation.MedicoView;

import Clases.Medicamento.presentation.MedicamentoModel;
import Clases.Medicamento.presentation.MedicamentoController;
import Clases.Medicamento.presentation.MedicamentoView;

import Clases.Paciente.data.ListaPacientes;
import Clases.Paciente.presentation.PacienteModel;
import Clases.Paciente.presentation.PacienteController;
import Clases.Paciente.presentation.View.PacienteView;
import Clases.Prescribir.data.RepositorioPrescripciones;
import Clases.Prescribir.presentation.PrescribirController;
import Clases.Prescribir.presentation.PrescribirView;
import Clases.Prescribir.presentation.PrescripcionModel;

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
        PrescribirView prescribirView = new PrescribirView();

        // 🔹 Modelos
        MedicoModel medicoModel = new MedicoModel();
        FarmaceutaModel farmaceutaModel = new FarmaceutaModel();
        PacienteModel pacienteModel = new PacienteModel();
        MedicamentoModel medicamentoModel = new MedicamentoModel();
        PrescripcionModel prescModel = new PrescripcionModel();


        ListaPacientes listaPacientes = new ListaPacientes();
        listaPacientes.cargar();

        catalogoMedicamentos catalogoMed = new catalogoMedicamentos();
        catalogoMed.cargar();

        ListaMedicos listaMedicos = new ListaMedicos();
        listaMedicos.cargar();

        RepositorioPrescripciones.cargar(); // carga prescripciones


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
        new PrescribirController(prescribirView, prescModel,
                listaMedicos.consulta().isEmpty() ? null : listaMedicos.consulta().get(0), // médico en sesión
                listaPacientes,
                catalogoMed);

        // 🔹 Mostrar ventana principal
        adminView.setVisible(true);

        // 🔹 Guardar al cerrar la aplicación
        adminView.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                listaPacientes.guardar();
                catalogoMed.guardar();
                listaMedicos.guardar();
                RepositorioPrescripciones.guardar();
            }
        });
    }
    }




