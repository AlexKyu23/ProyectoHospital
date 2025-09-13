
import Clases.Admin.logic.Admin;
import Clases.Admin.presentation.AdminController;
import Clases.Admin.presentation.AdminModel;
import Clases.Admin.presentation.AdminView;

import Clases.Farmaceuta.logic.FarmaceutaService;
import Clases.Farmaceuta.presentation.FarmaceutaModel;
import Clases.Farmaceuta.presentation.FarmaceutaController;
import Clases.Farmaceuta.presentation.View.FarmaceutaView;

import Clases.Medicamento.logic.MedicamentoService;
import Clases.Medicamento.presentation.MedicamentoModel;
import Clases.Medicamento.presentation.MedicamentoController;
import Clases.Medicamento.presentation.MedicamentoView;

import Clases.Medico.logic.MedicoService;
import Clases.Medico.presentation.MedicoModel;
import Clases.Medico.presentation.MedicoController;
import Clases.Medico.presentation.MedicoView;

import Clases.Paciente.logic.PacienteService;
import Clases.Paciente.presentation.PacienteModel;
import Clases.Paciente.presentation.PacienteController;
import Clases.Paciente.presentation.View.PacienteView;

import Clases.Prescribir.presentation.PrescribirController;
import Clases.Prescribir.presentation.PrescribirView;
import Clases.Prescribir.presentation.PrescripcionModel;

import Clases.AcercaDe.presentation.AcercaDeView;

import Clases.Paciente.data.ListaPacientes;
import Clases.Medicamento.data.catalogoMedicamentos;
import Clases.Medico.data.ListaMedicos;
import Clases.Farmaceuta.data.ListaFarmaceutas;
import Clases.Usuario.data.ListaUsuarios;
import Clases.Prescribir.data.RepositorioPrescripciones;
import Clases.Usuario.logic.UsuarioService;

import javax.swing.*;

public class app {
    public static void main(String[] args) {
        // 🔹 Crear instancia de Admin
        Admin admin = new Admin("001", "SuperAdmin", "1234");
        AdminModel adminModel = new AdminModel(admin.getId(), admin.getNombre(), admin.getClave());
        adminModel.setCurrent(admin);

        // 🔹 Crear vista principal del administrador
        AdminView adminView = new AdminView();

        // 🔹 Crear modelos
        MedicoModel medicoModel = new MedicoModel();
        FarmaceutaModel farmaceutaModel = new FarmaceutaModel();
        PacienteModel pacienteModel = new PacienteModel();
        MedicamentoModel medicamentoModel = new MedicamentoModel();
        PrescripcionModel prescModel = new PrescripcionModel();

        // 🔹 Cargar listas persistentes
        ListaMedicos listaMedicos = new ListaMedicos(); listaMedicos.cargar();
        ListaFarmaceutas listaFarmaceutas = new ListaFarmaceutas(); listaFarmaceutas.cargar();
        ListaPacientes listaPacientes = new ListaPacientes(); listaPacientes.cargar();
        catalogoMedicamentos catalogoMed = new catalogoMedicamentos(); catalogoMed.cargar();
        ListaUsuarios listaUsuarios = new ListaUsuarios(); listaUsuarios.cargar();
        RepositorioPrescripciones.cargar();

        // 🔹 Cargar datos en modelos
        medicoModel.setList(listaMedicos.consulta());
        farmaceutaModel.setList(listaFarmaceutas.consulta());
        pacienteModel.setList(listaPacientes.consulta());
        medicamentoModel.setList(catalogoMed.consulta());

        // 🔹 Crear controladores
        new MedicoController(medicoModel, new MedicoView());
        new FarmaceutaController(farmaceutaModel, new FarmaceutaView());
        new PacienteController(pacienteModel, new PacienteView());
        new MedicamentoController(medicamentoModel, new MedicamentoView());

        // 🔹 Controlador principal del administrador
        new AdminController(adminModel, adminView,
                medicoModel,
                farmaceutaModel,
                pacienteModel,
                medicamentoModel);

        // 🔹 Mostrar ventana principal
        adminView.setVisible(true);

        // 🔹 Guardar al cerrar la aplicación
        adminView.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.out.println("💾 Guardando datos al cerrar...");

                MedicoService.instance().guardar();
                FarmaceutaService.instance().guardar();
                PacienteService.instance().guardar();
                MedicamentoService.instance().guardar();
                UsuarioService.instance().guardar();
                RepositorioPrescripciones.guardar();

                System.out.println("✅ Guardado completo.");
            }


    });
    }
}

