package hospital.presentation.Paciente.presentation;

import hospital.logic.Service;
import hospital.presentation.Paciente.presentation.View.PacienteView;
import logic.Paciente;

import javax.swing.*;
import java.time.LocalDate;

public class PacienteController {
    private final PacienteModel model;
    private final PacienteView view;

    public PacienteController(PacienteModel model, PacienteView view) throws Exception {
        this.model = model;
        this.view = view;

        view.setController(this);
        view.setModel(model);

        model.init();
        model.setList(Service.instance().findAllPacientes());
    }

    public void guardar() {
        String id = view.getId().getText().trim();
        String nombre = view.getNombre().getText().trim();
        String telefono = view.getNumeroTelefono().getText().trim();
        LocalDate fechaNacimiento = view.getFechaNacimiento().getDate();

        if (id.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || fechaNacimiento == null) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Debe llenar todos los campos");
            return;
        }

        try {
            Paciente existente = Service.instance().readPaciente(id);
            existente.setNombre(nombre);
            existente.setTelefono(telefono);
            existente.setFechaNacimiento(fechaNacimiento);
            Service.instance().updatePaciente(existente);
            JOptionPane.showMessageDialog(view.getMainPanel(), "Paciente actualizado");
        } catch (Exception e) {
            try {
                Paciente nuevo = new Paciente(id, nombre, telefono, fechaNacimiento);
                Service.instance().createPaciente(nuevo);
                JOptionPane.showMessageDialog(view.getMainPanel(), "Paciente agregado");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view.getMainPanel(), ex.getMessage());
            }
        }

        actualizarLista();
    }

    public void borrar() {
        String id = view.getId().getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Debe ingresar un ID");
            return;
        }

        try {
            Service.instance().deletePaciente(id);
            JOptionPane.showMessageDialog(view.getMainPanel(), "Paciente eliminado");
            actualizarLista();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getMainPanel(), e.getMessage());
        }
    }

    public void buscar() {
        String criterio = view.getNombreBuscar().getText().trim();
        if (criterio.isEmpty()) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Ingrese nombre o ID para buscar");
            return;
        }

        try {
            Paciente p = Service.instance().readPaciente(criterio);
            if (p != null) {
                model.setCurrent(p);
                JOptionPane.showMessageDialog(view.getMainPanel(), "Paciente encontrado");
            } else {
                JOptionPane.showMessageDialog(view.getMainPanel(), "No se encontró el paciente");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getMainPanel(), e.getMessage());
        }
    }

    public void limpiar() {
        model.setCurrent(new Paciente());
        view.getNombreBuscar().setText("");
    }

    public void reporte() {
        StringBuilder sb = new StringBuilder("📋 Lista de Pacientes:\n\n");
        for (Paciente p : model.getList()) {
            sb.append("ID: ").append(p.getId()).append("\n");
            sb.append("Nombre: ").append(p.getNombre()).append("\n");
            sb.append("Teléfono: ").append(p.getTelefono()).append("\n");
            sb.append("Fecha de Nacimiento: ").append(p.getFechaNacimiento()).append("\n");
            sb.append("-------------------------\n");
        }
        JOptionPane.showMessageDialog(view.getMainPanel(), sb.toString());
    }

    private void actualizarLista() {
        try {
            model.setList(Service.instance().findAllPacientes());
            model.setCurrent(new Paciente());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Error al actualizar lista: " + e.getMessage());
        }
    }
}
