package Clases.Despacho.presentation;

import Clases.Despacho.logic.DespachoService;
import Clases.Receta.Data.RepositorioRecetas;
import Clases.Receta.logic.EstadoReceta;
import Clases.Receta.logic.Receta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.List;

public class DespachoController {
    private DespachoModel model;
    private DespachoView view;
    private DespachoService service;
    private RepositorioRecetas repositorioRecetas;
    private DefaultTableModel tableModelIniciar;
    private DefaultTableModel tableModelAlistar;
    private DefaultTableModel tableModelLista;

    public DespachoController(DespachoModel model, DespachoView view, RepositorioRecetas repositorioRecetas) {
        this.model = model;
        this.view = view;
        this.service = new DespachoService();
        this.repositorioRecetas = repositorioRecetas;

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

        // 🔹 Usar recetas reales desde RepositorioRecetas
        model.setRecetas(repositorioRecetas.getRecetas());

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
        view.getBuscarButton().addActionListener(e -> buscarPorPaciente());
        view.getIniciarBtn().addActionListener(e -> cambiarEstado("proceso"));
        view.getAlistarBtn().addActionListener(e -> cambiarEstado("lista"));
        view.getEntregarBtn().addActionListener(e -> cambiarEstado("entregada"));
    }

    private void buscarPorPaciente() {
        String pacienteId = view.getIdPacienteBuscar().getText().trim();
        if (pacienteId.isEmpty()) {
            JOptionPane.showMessageDialog(view.getDespacho(), "Ingrese el ID del paciente");
            return;
        }

        // Filtrar recetas por ID de paciente
        tableModelIniciar.setRowCount(0);
        tableModelAlistar.setRowCount(0);
        tableModelLista.setRowCount(0);

        List<Receta> recetasFiltradas = service.recetasDisponiblesParaDespacho().stream()
                .filter(r -> r.getPacienteId().equalsIgnoreCase(pacienteId))
                .toList();

        for (Receta r : recetasFiltradas) {
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

        if (recetasFiltradas.isEmpty()) {
            JOptionPane.showMessageDialog(view.getDespacho(), "No se encontraron recetas para este paciente en el rango de fechas válido");
        }
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
            JOptionPane.showMessageDialog(view.getDespacho(), "Seleccione una receta primero.");
            return;
        }

        int row = selectedTable.getSelectedRow();
        String recetaId = selectedModel.getValueAt(row, 0).toString(); // 🔹 .toString() evita ClassCastException

        // 🔹 Validar flujo correcto de estados
        try {
            switch (accion) {
                case "proceso" -> {
                    if (selectedModel == tableModelIniciar) {
                        service.iniciarDespacho(recetaId);
                    } else {
                        JOptionPane.showMessageDialog(view.getDespacho(), "Solo recetas CONFECCIONADAS pueden pasar a PROCESO.");
                    }
                }
                case "lista" -> {
                    if (selectedModel == tableModelAlistar) {
                        service.alistarMedicamentos(recetaId);
                    } else {
                        JOptionPane.showMessageDialog(view.getDespacho(), "Solo recetas EN PROCESO pueden pasar a LISTA.");
                    }
                }
                case "entregada" -> {
                    if (selectedModel == tableModelLista) {
                        service.entregarReceta(recetaId);
                    } else {
                        JOptionPane.showMessageDialog(view.getDespacho(), "Solo recetas LISTAS pueden ser ENTREGADAS.");
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getDespacho(), "Error al cambiar estado: " + e.getMessage());
        }

        // 🔹 Refrescar tablas
        cargarRecetas();
    }
}