package Clases.Medico.presentation;

import Clases.Medico.logic.Medico;
import Clases.Medico.logic.MedicoService;

import javax.swing.*;

public class MedicoController {
    private MedicoModel model;
    private MedicoView view;

    public MedicoController(MedicoModel model, MedicoView view) {
        this.model = model;
        this.view = view;

        inicializarTabla();
        refrescarTabla();

        this.view.getGuardarButton().addActionListener(e -> guardarMedico());

        this.view.getTablaMedicos().getSelectionModel().addListSelectionListener(e -> {
            int fila = view.getTablaMedicos().getSelectedRow();
            if (fila >= 0) {
                Medico seleccionado = ((MedicoTableModel) view.getTablaMedicos().getModel()).getRowAt(fila);
                view.getId().setText(seleccionado.getId());
                view.getNombre().setText(seleccionado.getNombre());
                view.getEspecialidad().setText(seleccionado.getEspecialidad());
            }
        });

        this.view.getBorrarButton().addActionListener(e -> borrarMedico());
        this.view.getBuscarButton().addActionListener(e -> buscarMedico());
        this.view.getLimpiarButton().addActionListener(e -> limpiarCampos());
    }

    private void inicializarTabla() {
        int[] columnas = {MedicoTableModel.ID, MedicoTableModel.NOMBRE, MedicoTableModel.ESPECIALIDAD};
        MedicoTableModel tableModel = new MedicoTableModel(columnas, MedicoService.instance().findAll());
        view.getTablaMedicos().setModel(tableModel);
    }

    private void refrescarTabla() {
        ((MedicoTableModel) view.getTablaMedicos().getModel()).fireTableDataChanged();
    }

    private void guardarMedico() {
        String id = view.getId().getText();
        String nombre = view.getNombre().getText();
        String especialidad = view.getEspecialidad().getText();

        if (id.isEmpty() || nombre.isEmpty() || especialidad.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
            return;
        }

        try {
            Medico existente = MedicoService.instance().readById(id);
            existente.setNombre(nombre);
            existente.setEspecialidad(especialidad);
            JOptionPane.showMessageDialog(null, "Médico actualizado");
        } catch (Exception e) {
            Medico nuevo = new Medico(id, nombre, id, especialidad);
            try {
                MedicoService.instance().create(nuevo);
                JOptionPane.showMessageDialog(null, "Médico agregado");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }

        limpiarCampos();
    }

    private void borrarMedico() {
        String id = view.getId().getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un id");
            return;
        }

        MedicoService.instance().delete(id);
        JOptionPane.showMessageDialog(null, "Médico eliminado");
        limpiarCampos();
    }

    private void buscarMedico() {
        String criterio = view.getNombreBuscar().getText();
        Medico encontrado = MedicoService.instance().readById(criterio);
        if (encontrado == null) {
            encontrado = MedicoService.instance().readByNombre(criterio);
        }

        if (encontrado != null) {
            view.getId().setText(encontrado.getId());
            view.getNombre().setText(encontrado.getNombre());
            view.getEspecialidad().setText(encontrado.getEspecialidad());
            JOptionPane.showMessageDialog(null, "Médico encontrado");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el médico");
        }
    }

    private void limpiarCampos() {
        view.getId().setText("");
        view.getNombre().setText("");
        view.getEspecialidad().setText("");
        view.getNombreBuscar().setText("");
        refrescarTabla();
    }
}
