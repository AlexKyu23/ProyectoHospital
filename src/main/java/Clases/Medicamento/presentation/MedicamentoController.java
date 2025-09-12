package Clases.Medicamento.presentation;

import Clases.Medicamento.logic.Medicamento;
import Clases.Medicamento.logic.MedicamentoService;

import javax.swing.*;

public class MedicamentoController {
    private MedicamentoModel model;
    private MedicamentoView view;

    public MedicamentoController(MedicamentoModel model, MedicamentoView view) {
        this.model = model;
        this.view = view;

        inicializarTabla();
        refrescarTabla();

        view.getTablaMedicamentos().getSelectionModel().addListSelectionListener(e -> {
            int fila = view.getTablaMedicamentos().getSelectedRow();
            if (fila >= 0) {
                Medicamento seleccionado = ((MedicamentoTableModel) view.getTablaMedicamentos().getModel()).getRowAt(fila);
                model.setCurrent(seleccionado);
                view.getCodigo().setText(String.valueOf(seleccionado.getCodigo()));
                view.getNombre().setText(seleccionado.getNombre());
                view.getDescripcion().setText(seleccionado.getDescripcion());
            }
        });

        view.getGuardarButton().addActionListener(e -> guardarMedicamento());
        view.getBorrarButton().addActionListener(e -> borrarMedicamento());
        view.getBuscarButton().addActionListener(e -> buscarMedicamento());
        view.getLimpiarButton().addActionListener(e -> limpiarCampos());
        view.getReporteButton().addActionListener(e -> generarReporte());
    }

    private void inicializarTabla() {
        int[] columnas = {MedicamentoTableModel.CODIGO, MedicamentoTableModel.NOMBRE, MedicamentoTableModel.DESCRIPCION};
        MedicamentoTableModel tableModel = new MedicamentoTableModel(columnas, MedicamentoService.instance().findAll());
        view.getTablaMedicamentos().setModel(tableModel);
    }

    private void refrescarTabla() {
        ((MedicamentoTableModel) view.getTablaMedicamentos().getModel()).fireTableDataChanged();
    }

    private void guardarMedicamento() {
        try {
            int codigo = Integer.parseInt(view.getCodigo().getText());
            String nombre = view.getNombre().getText();
            String descripcion = view.getDescripcion().getText();

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe llenar todos los campos obligatorios");
                return;
            }

            Medicamento existente = MedicamentoService.instance().readByCodigo(codigo);
            if (existente == null) {
                Medicamento nuevo = new Medicamento(nombre, descripcion, codigo);
                MedicamentoService.instance().create(nuevo);
                model.setCurrent(nuevo);
                JOptionPane.showMessageDialog(null, "Medicamento agregado");
            } else {
                existente.setNombre(nombre);
                existente.setDescripcion(descripcion);
                JOptionPane.showMessageDialog(null, "Medicamento actualizado");
            }

            limpiarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "El código debe ser un número");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void borrarMedicamento() {
        try {
            int codigo = Integer.parseInt(view.getCodigo().getText());
            MedicamentoService.instance().delete(codigo);
            JOptionPane.showMessageDialog(null, "Medicamento eliminado");
            limpiarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un código válido");
        }
    }

    private void buscarMedicamento() {
        String criterio = view.getNombreBuscar().getText();
        Medicamento encontrado = null;

        try {
            int codigo = Integer.parseInt(criterio);
            encontrado = MedicamentoService.instance().readByCodigo(codigo);
        } catch (NumberFormatException e) {
            encontrado = MedicamentoService.instance().readByNombre(criterio);
        }

        if (encontrado != null) {
            model.setCurrent(encontrado);
            view.getCodigo().setText(String.valueOf(encontrado.getCodigo()));
            view.getNombre().setText(encontrado.getNombre());
            view.getDescripcion().setText(encontrado.getDescripcion());
            JOptionPane.showMessageDialog(null, "Medicamento encontrado");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el medicamento");
        }
    }

    private void limpiarCampos() {
        view.getCodigo().setText("");
        view.getNombre().setText("");
        view.getDescripcion().setText("");
        view.getNombreBuscar().setText("");
        refrescarTabla();
    }

    private void generarReporte() {
        StringBuilder reporte = new StringBuilder("📋 Lista de Medicamentos:\n\n");
        for (Medicamento m : MedicamentoService.instance().findAll()) {
            reporte.append("Código: ").append(m.getCodigo()).append("\n");
            reporte.append("Nombre: ").append(m.getNombre()).append("\n");
            reporte.append("Descripción: ").append(m.getDescripcion()).append("\n");
            reporte.append("-------------------------\n");
        }

        JOptionPane.showMessageDialog(null, reporte.toString());
    }
}
