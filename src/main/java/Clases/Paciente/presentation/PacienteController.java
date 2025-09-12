package Clases.Paciente.presentation;

import Clases.Paciente.logic.Paciente;
import Clases.Paciente.logic.PacienteService;
import Clases.Paciente.presentation.View.PacienteView;

import javax.swing.*;
import java.time.LocalDate;

public class PacienteController {
    private PacienteModel model;
    private PacienteView view;

    public PacienteController(PacienteModel model, PacienteView view) {
        this.model = model;
        this.view = view;

        inicializarTabla();
        refrescarTabla();

        view.getTablaPacientes().getSelectionModel().addListSelectionListener(e -> {
            int fila = view.getTablaPacientes().getSelectedRow();
            if (fila >= 0) {
                Paciente seleccionado = ((PacienteTableModel) view.getTablaPacientes().getModel()).getRowAt(fila);
                model.setCurrent(seleccionado);
                view.getId().setText(seleccionado.getId());
                view.getNombre().setText(seleccionado.getNombre());
                view.getNumeroTelefono().setText(seleccionado.getTelefono());
                view.getFechaNacimiento().setDate(seleccionado.getFechaNacimiento());
            }
        });

        view.getGuardarButton().addActionListener(e -> guardarPaciente());
        view.getBorrarButton().addActionListener(e -> borrarPaciente());
        view.getBuscarButton().addActionListener(e -> buscarPaciente());
        view.getLimpiarButton().addActionListener(e -> limpiarCampos());
    }

    private void inicializarTabla() {
        int[] columnas = {PacienteTableModel.ID, PacienteTableModel.NOMBRE, PacienteTableModel.TELEFONO, PacienteTableModel.FECHA};
        PacienteTableModel tableModel = new PacienteTableModel(columnas, PacienteService.instance().findAll());
        view.getTablaPacientes().setModel(tableModel);
    }

    private void refrescarTabla() {
        ((PacienteTableModel) view.getTablaPacientes().getModel()).fireTableDataChanged();
    }

    private void guardarPaciente() {
        String id = view.getId().getText();
        String nombre = view.getNombre().getText();
        String telefono = view.getNumeroTelefono().getText();
        LocalDate fechaNacimiento = view.getFechaNacimiento().getDate();

        if (id.isEmpty() || nombre.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
            return;
        }

        try {
            Paciente existente = PacienteService.instance().readById(id);
            existente.setNombre(nombre);
            existente.setTelefono(telefono);
            existente.setFechaNacimiento(fechaNacimiento);
            JOptionPane.showMessageDialog(null, "Paciente actualizado");
        } catch (Exception e) {
            Paciente nuevo = new Paciente(id, nombre, telefono, fechaNacimiento);
            try {
                PacienteService.instance().create(nuevo);
                model.setCurrent(nuevo);
                JOptionPane.showMessageDialog(null, "Paciente agregado");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }

        limpiarCampos();
    }

    private void borrarPaciente() {
        String id = view.getId().getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un id");
            return;
        }

        PacienteService.instance().delete(id);
        JOptionPane.showMessageDialog(null, "Paciente eliminado");
        limpiarCampos();
    }

    private void buscarPaciente() {
        String criterio = view.getNombreBuscar().getText();
        Paciente encontrado = PacienteService.instance().readById(criterio);
        if (encontrado == null) {
            encontrado = PacienteService.instance().readByNombre(criterio);
        }

        if (encontrado != null) {
            model.setCurrent(encontrado);
            view.getId().setText(encontrado.getId());
            view.getNombre().setText(encontrado.getNombre());
            view.getNumeroTelefono().setText(encontrado.getTelefono());
            view.getFechaNacimiento().setDate(encontrado.getFechaNacimiento());
            JOptionPane.showMessageDialog(null, "Paciente encontrado");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el paciente");
        }
    }

    private void limpiarCampos() {
        view.getId().setText("");
        view.getNombre().setText("");
        view.getNumeroTelefono().setText("");
        view.getFechaNacimiento().clear();
        view.getNombreBuscar().setText("");
        refrescarTabla();
    }
}
