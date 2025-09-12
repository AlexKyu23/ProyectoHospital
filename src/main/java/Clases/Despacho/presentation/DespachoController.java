package Clases.Despacho.presentation;

import Clases.Despacho.logic.DespachoService;
import Clases.Receta.logic.Receta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DespachoController {
    private DespachoModel model;
    private DespachoView view;
    private DespachoService service;
    private DefaultTableModel tableModel;

    public DespachoController(DespachoModel model, DespachoView view) {
        this.model = model;
        this.view = view;
        this.service = new DespachoService();

        tableModel = new DefaultTableModel(new Object[]{"ID", "Paciente", "Fecha Retiro", "Estado"}, 0);
        view.getTabla().setModel(tableModel);

        cargarRecetas();
        initController();
    }

    private void cargarRecetas() {
        model.setRecetas(service.recetasDisponiblesParaDespacho());
        tableModel.setRowCount(0);
        for (Receta r : model.getRecetas()) {
            tableModel.addRow(new Object[]{
                    r.getId(),
                    r.getPacienteId(),
                    r.getFechaRetiro(),
                    r.getEstado()
            });
        }
    }

    private void initController() {
        view.getIniciarBtn().addActionListener(e -> cambiarEstado("proceso"));
        view.getAlistarBtn().addActionListener(e -> cambiarEstado("lista"));
        view.getEntregarBtn().addActionListener(e -> cambiarEstado("entregada"));
    }

    private void cambiarEstado(String accion) {
        int row = view.getTabla().getSelectedRow();
        if (row < 0) return;

        String recetaId = (String) tableModel.getValueAt(row, 0);
        switch (accion) {
            case "proceso" -> service.iniciarDespacho(recetaId);
            case "lista" -> service.alistarMedicamentos(recetaId);
            case "entregada" -> service.entregarReceta(recetaId);
        }

        cargarRecetas();
    }
}
