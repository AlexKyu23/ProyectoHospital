package Clases.Despacho.presentation;

import Clases.Despacho.logic.DespachoService;
import Clases.Receta.logic.EstadoReceta;
import Clases.Receta.logic.Receta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DespachoController {
    private DespachoModel model;
    private DespachoView view;
    private DespachoService service;
    private DefaultTableModel tableModel;

    private DefaultTableModel tableModelIniciar;
    private DefaultTableModel tableModelAlistar;
    private DefaultTableModel tableModelLista;

    public DespachoController(DespachoModel model, DespachoView view) {
        this.model = model;
        this.view = view;
        this.service = new DespachoService();

        // 🔹 Inicialización de modelos de tabla
        tableModelIniciar = new DefaultTableModel(new Object[]{"ID", "Paciente", "Fecha Retiro", "Estado"}, 0);
        tableModelAlistar = new DefaultTableModel(new Object[]{"ID", "Paciente", "Fecha Retiro", "Estado"}, 0);
        tableModelLista = new DefaultTableModel(new Object[]{"ID", "Paciente", "Fecha Retiro", "Estado"}, 0);

        // 🔹 Asociar modelos a las tablas de la vista
        view.getTablaIniciar().setModel(tableModelIniciar);
        view.getTablaAlistar().setModel(tableModelAlistar);
        view.getTablaRecetaLista().setModel(tableModelLista);

        cargarRecetas();
        initController();
    }

    private void cargarRecetas() {
        tableModelIniciar.setRowCount(0);
        tableModelAlistar.setRowCount(0);
        tableModelLista.setRowCount(0);

        // 🔹 Usar recetas dummy del mismo controller para prueba
        model.setRecetas(recetasDisponiblesParaDespacho());

        for (Receta r : model.getRecetas()) {
            Object[] fila = {
                    r.getId(),
                    r.getPacienteId(),
                    r.getFechaRetiro(),
                    r.getEstado()
            };

            switch (r.getEstado()) {
                case CONFECCIONADA -> tableModelIniciar.addRow(fila);
                case EN_PROCESO -> tableModelAlistar.addRow(fila);
                case LISTA -> tableModelLista.addRow(fila);
            }
        }
    }
    private void initController() {
        view.getIniciarBtn().addActionListener(e -> cambiarEstado("proceso"));
        view.getAlistarBtn().addActionListener(e -> cambiarEstado("lista"));
        view.getEntregarBtn().addActionListener(e -> cambiarEstado("entregada"));
    }

    private void cambiarEstado(String accion) {
        JTable selectedTable = null;
        DefaultTableModel selectedModel = null;

        // Verificar de qué tabla viene la selección
        if (view.getTablaIniciar().getSelectedRow() >= 0) {
            selectedTable = view.getTablaIniciar();
            selectedModel = tableModelIniciar;
        } else if (view.getTablaAlistar().getSelectedRow() >= 0) {
            selectedTable = view.getTablaAlistar();
            selectedModel = tableModelAlistar;
        } else if (view.getTablaRecetaLista().getSelectedRow() >= 0) {
            selectedTable = view.getTablaRecetaLista();
            selectedModel = tableModelLista;
        }

        if (selectedTable == null) {
            JOptionPane.showMessageDialog(view, "Seleccione una receta primero.");
            return;
        }
        int row = selectedTable.getSelectedRow();
        String recetaId = selectedModel.getValueAt(row, 0).toString(); // 🔹 .toString() evita ClassCastException

        // 🔹 Validar flujo correcto de estados
        switch (accion) {
            case "proceso" -> {
                if (selectedModel == tableModelIniciar) {
                    service.iniciarDespacho(recetaId);
                } else {
                    JOptionPane.showMessageDialog(view, "Solo recetas CONFECCIONADAS pueden pasar a PROCESO.");
                }
            }
            case "lista" -> {
                if (selectedModel == tableModelAlistar) {
                    service.alistarMedicamentos(recetaId);
                } else {
                    JOptionPane.showMessageDialog(view, "Solo recetas EN PROCESO pueden pasar a LISTA.");
                }
            }
            case "entregada" -> {
                if (selectedModel == tableModelLista) {
                    service.entregarReceta(recetaId);
                } else {
                    JOptionPane.showMessageDialog(view, "Solo recetas LISTAS pueden ser ENTREGADAS.");
                }
            }
        }

        // 🔹 Refrescar tablas
        cargarRecetas();
    }
    public List<Receta> recetasDisponiblesParaDespacho() {
        List<Receta> dummy = new ArrayList<>();
        Receta r1 = new Receta("R1", "P1", LocalDate.now(), EstadoReceta.CONFECCIONADA);
        Receta r2 = new Receta("R2", "P2", LocalDate.now(), EstadoReceta.EN_PROCESO);
        Receta r3 = new Receta("R3", "P3", LocalDate.now(), EstadoReceta.LISTA);
        dummy.add(r1);
        dummy.add(r2);
        dummy.add(r3);
        return dummy;
    }

}

