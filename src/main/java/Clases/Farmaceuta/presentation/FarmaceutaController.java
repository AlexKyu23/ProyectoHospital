package Clases.Farmaceuta.presentation;

import Clases.Farmaceuta.logic.Farmaceuta;
import Clases.Farmaceuta.logic.FarmaceutaService;
import Clases.Farmaceuta.presentation.View.FarmaceutaView;

import javax.swing.*;

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
                Farmaceuta seleccionado = ((FarmaceutaTableModel) view.getTablaFarmaceutas().getModel()).getRowAt(fila);
                view.getId().setText(seleccionado.getId());
                view.getNombre().setText(seleccionado.getNombre());
            }
        });

        view.getGuardarButton().addActionListener(e -> guardarFarmaceuta());
        view.getBorrarButton().addActionListener(e -> borrarFarmaceuta());
        view.getBuscarButton().addActionListener(e -> buscarFarmaceuta());
        view.getLimpiarButton().addActionListener(e -> limpiarCampos());
        view.getReporteButton().addActionListener(e -> generarReporte());
    }

    private void inicializarTabla() {
        int[] columnas = {FarmaceutaTableModel.ID, FarmaceutaTableModel.NOMBRE};
        FarmaceutaTableModel tableModel = new FarmaceutaTableModel(columnas, FarmaceutaService.instance().findAll());
        view.getTablaFarmaceutas().setModel(tableModel);
    }

    private void refrescarTabla() {
        ((FarmaceutaTableModel) view.getTablaFarmaceutas().getModel()).fireTableDataChanged();
    }

    private void guardarFarmaceuta() {
        String id = view.getId().getText();
        String nombre = view.getNombre().getText();

        if (id.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
            return;
        }

        try {
            Farmaceuta existente = FarmaceutaService.instance().readById(id);
            existente.setNombre(nombre);
            JOptionPane.showMessageDialog(null, "Farmaceuta actualizado");
        } catch (Exception e) {
            Farmaceuta nuevo = new Farmaceuta(id, nombre, id);
            try {
                FarmaceutaService.instance().create(nuevo);
                JOptionPane.showMessageDialog(null, "Farmaceuta agregado");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }

        limpiarCampos();
    }

    private void borrarFarmaceuta() {
        String id = view.getId().getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un id");
            return;
        }

        FarmaceutaService.instance().delete(id);
        JOptionPane.showMessageDialog(null, "Farmaceuta eliminado");
        limpiarCampos();
    }

    private void buscarFarmaceuta() {
        String criterio = view.getNombreBuscar().getText();
        Farmaceuta f = FarmaceutaService.instance().readByNombre(criterio);
        if (f == null) {
            f = FarmaceutaService.instance().readById(criterio);
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
        for (Farmaceuta f : FarmaceutaService.instance().findAll()) {
            reporte.append("ID: ").append(f.getId()).append("\n");
            reporte.append("Nombre: ").append(f.getNombre()).append("\n");
            reporte.append("-------------------------\n");
        }

        JOptionPane.showMessageDialog(null, reporte.toString());
    }
}

