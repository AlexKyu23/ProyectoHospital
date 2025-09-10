package Clases.Medicamento.presentation;

import Clases.Medicamento.logic.Medicamento;
import Clases.Medicamento.presentation.MedicamentoView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                int codigo = Integer.parseInt(view.getTablaMedicamentos().getValueAt(fila, 0).toString());
                Medicamento encontrado = model.findByCodigo(codigo);
                if (encontrado != null) {
                    model.setCurrent(encontrado);
                    view.getCodigo().setText(String.valueOf(encontrado.getCodigo()));
                    view.getNombre().setText(encontrado.getNombre());
                    view.getDescripcion().setText(encontrado.getDescripcion());
                }
            }
        });

        view.getGuardarButton().addActionListener(e -> guardarMedicamento());
        view.getBorrarButton().addActionListener(e -> borrarMedicamento());
        view.getBuscarButton().addActionListener(e -> buscarMedicamento());
        view.getLimpiarButton().addActionListener(e -> limpiarCampos());
        view.getReporteButton().addActionListener(e -> generarReporte());
    }

    private void inicializarTabla() {
        String[] columnas = {"Código", "Nombre", "Descripción"};
        DefaultTableModel tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        view.getTablaMedicamentos().setModel(tableModel);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < view.getTablaMedicamentos().getColumnCount(); i++) {
            view.getTablaMedicamentos().getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        view.getTablaMedicamentos().setRowHeight(25);
    }

    private void refrescarTabla() {
        DefaultTableModel tableModel = (DefaultTableModel) view.getTablaMedicamentos().getModel();
        tableModel.setRowCount(0);

        for (Medicamento m : model.getMedicamentos()) {
            Object[] fila = {
                    m.getCodigo(),
                    m.getNombre(),
                    m.getDescripcion()
            };
            tableModel.addRow(fila);
        }
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

            Medicamento existente = model.findByCodigo(codigo);
            if (existente == null) {
                Medicamento nuevo = new Medicamento(nombre, descripcion, codigo);
                model.addMedicamento(nuevo);
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
        }
    }

    private void borrarMedicamento() {
        try {
            int codigo = Integer.parseInt(view.getCodigo().getText());
            model.deleteMedicamento(codigo);
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
            encontrado = model.findByCodigo(codigo);
        } catch (NumberFormatException e) {
            encontrado = model.findByNombre(criterio);
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
        for (Medicamento m : model.getMedicamentos()) {
            reporte.append("Código: ").append(m.getCodigo()).append("\n");
            reporte.append("Nombre: ").append(m.getNombre()).append("\n");
            reporte.append("Descripción: ").append(m.getDescripcion()).append("\n");
            reporte.append("-------------------------\n");
        }

        JOptionPane.showMessageDialog(null, reporte.toString());
    }
}
