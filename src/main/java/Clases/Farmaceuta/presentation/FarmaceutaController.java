package Clases.Farmaceuta.presentation;

import Clases.Farmaceuta.logic.Farmaceuta;
import Clases.Farmaceuta.presentation.View.FarmaceutaView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FarmaceutaController {
    private FarmaceutaModel model;
    private FarmaceutaView view;

    public FarmaceutaController(FarmaceutaModel model, FarmaceutaView view) {
        this.model = model;
        this.view = view;

        inicializarTabla();
        refrescarTabla();

        view.getTablaFarmaceutas().getSelectionModel().addListSelectionListener(e -> {
            int fila = view.getTablaFarmaceutas().getSelectedRow();
            if (fila >= 0) {
                String id = view.getTablaFarmaceutas().getValueAt(fila, 0).toString();
                Farmaceuta f = model.findById(id);
                if (f != null) {
                    view.getId().setText(f.getId());
                    view.getNombre().setText(f.getNombre());
                }
            }
        });

        view.getGuardarButton().addActionListener(e -> guardarFarmaceuta());
        view.getBorrarButton().addActionListener(e -> borrarFarmaceuta());
        view.getBuscarButton().addActionListener(e -> buscarFarmaceuta());
        view.getLimpiarButton().addActionListener(e -> limpiarCampos());
        view.getReporteButton().addActionListener(e -> generarReporte());
    }

    private void inicializarTabla() {
        String[] columnas = {"ID", "Nombre"};
        DefaultTableModel tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        view.getTablaFarmaceutas().setModel(tableModel);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < view.getTablaFarmaceutas().getColumnCount(); i++) {
            view.getTablaFarmaceutas().getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        view.getTablaFarmaceutas().setRowHeight(25);
    }

    private void refrescarTabla() {
        DefaultTableModel tableModel = (DefaultTableModel) view.getTablaFarmaceutas().getModel();
        tableModel.setRowCount(0);

        for (Farmaceuta f : model.getFarmaceutas()) {
            Object[] fila = {f.getId(), f.getNombre()};
            tableModel.addRow(fila);
        }
    }

    private void guardarFarmaceuta() {
        String id = view.getId().getText();
        String nombre = view.getNombre().getText();

        if (id.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
            return;
        }

        Farmaceuta existente = model.findById(id);
        if (existente == null) {
            Farmaceuta f = new Farmaceuta(id, nombre, id); // clave = id por defecto
            model.addFarmaceuta(f);
            JOptionPane.showMessageDialog(null, "Farmaceuta agregado");
        } else {
            existente.setNombre(nombre);
            model.updateFarmaceuta(existente);
            JOptionPane.showMessageDialog(null, "Farmaceuta actualizado");
        }

        limpiarCampos();
    }

    private void borrarFarmaceuta() {
        String id = view.getId().getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un id");
            return;
        }

        model.deleteFarmaceuta(id);
        JOptionPane.showMessageDialog(null, "Farmaceuta eliminado");
        limpiarCampos();
    }

    private void buscarFarmaceuta() {
        String criterio = view.getNombreBuscar().getText();
        Farmaceuta f = model.findByNombre(criterio);
        if (f == null) {
            f = model.findById(criterio);
        }

        if (f != null) {
            view.getId().setText(f.getId());
            view.getNombre().setText(f.getNombre());
            JOptionPane.showMessageDialog(null, "Farmaceuta encontrado");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el farmaceuta");
        }
    }

    private void limpiarCampos() {
        view.getId().setText("");
        view.getNombre().setText("");
        view.getNombreBuscar().setText("");
        refrescarTabla();
    }

    private void generarReporte() {
        StringBuilder reporte = new StringBuilder("📋 Lista de Farmaceutas:\n\n");
        for (Farmaceuta f : model.getFarmaceutas()) {
            reporte.append("ID: ").append(f.getId()).append("\n");
            reporte.append("Nombre: ").append(f.getNombre()).append("\n");
            reporte.append("-------------------------\n");
        }

        JOptionPane.showMessageDialog(null, reporte.toString());
    }
}
