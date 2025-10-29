package hospital.presentation.Despacho.presentation;

import hospital.logic.Service;
import logic.EstadoReceta;
import logic.Receta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.List;

public class DespachoController {
    private final DespachoModel model;
    private final DespachoView view;

    private final DefaultTableModel tableModelIniciar;
    private final DefaultTableModel tableModelAlistar;
    private final DefaultTableModel tableModelLista;
    private final DefaultTableModel tableModelEntregada;

    public DespachoController(DespachoModel model, DespachoView view) throws Exception {
        this.model = model;
        this.view = view;

        // Inicializar modelos de tabla
        tableModelIniciar = new DefaultTableModel(new Object[]{"ID", "Paciente", "Fecha Retiro", "Estado"}, 0);
        tableModelAlistar = new DefaultTableModel(new Object[]{"ID", "Paciente", "Fecha Retiro", "Estado"}, 0);
        tableModelLista = new DefaultTableModel(new Object[]{"ID", "Paciente", "Fecha Retiro", "Estado"}, 0);
        tableModelEntregada = new DefaultTableModel(new Object[]{"ID", "Paciente", "Fecha Retiro", "Estado"}, 0);

        // Asignar modelos a las tablas
        view.getTablaIniciar().setModel(tableModelIniciar);
        view.getTablaAlistar().setModel(tableModelAlistar);
        view.getTablaRecetaLista().setModel(tableModelLista);
        view.getTablaEntregada().setModel(tableModelEntregada);

        // Conectar MVC
        view.setController(this);
        view.setModel(model);

        // Inicializar modelo
        model.init();
        model.setList(Service.instance().findAllRecetas());

        cargarRecetas();
        initListeners();
    }

    private void initListeners() {
        view.getBuscarButton().addActionListener(e -> buscarPorPaciente());
        view.getIniciarBtn().addActionListener(e -> cambiarEstado(EstadoReceta.EN_PROCESO));
        view.getAlistarBtn().addActionListener(e -> cambiarEstado(EstadoReceta.LISTA));
        view.getEntregarBtn().addActionListener(e -> cambiarEstado(EstadoReceta.ENTREGADA));
    }

    private void cargarRecetas() {
        tableModelIniciar.setRowCount(0);
        tableModelAlistar.setRowCount(0);
        tableModelLista.setRowCount(0);
        tableModelEntregada.setRowCount(0);

        for (Receta r : model.getList()) {
            Object[] fila = { r.getId(), r.getPacienteId(), r.getFechaRetiro(), r.getEstado() };
            switch (r.getEstado()) {
                case CONFECCIONADA -> tableModelIniciar.addRow(fila);
                case EN_PROCESO -> tableModelAlistar.addRow(fila);
                case LISTA -> tableModelLista.addRow(fila);
                case ENTREGADA -> tableModelEntregada.addRow(fila);
            }
        }
    }

    private void buscarPorPaciente() {
        String pacienteId = view.getIdPacienteBuscar().getText().trim();
        if (pacienteId.isEmpty()) {
            JOptionPane.showMessageDialog(view.getDespacho(), "Ingrese el ID del paciente");
            return;
        }

        List<Receta> filtradas = model.getList().stream()
                .filter(r -> r.getPacienteId().equalsIgnoreCase(pacienteId))
                .filter(r -> {
                    if (r.getEstado() == EstadoReceta.CONFECCIONADA) {
                        LocalDate hoy = LocalDate.now();
                        LocalDate retiro = r.getFechaRetiro();
                        return retiro != null &&
                                !retiro.isBefore(hoy.minusDays(3)) &&
                                !retiro.isAfter(hoy.plusDays(3));
                    }
                    return true;
                })
                .toList();

        model.setFilter(new Receta());
        model.setList(filtradas);
        cargarRecetas();

        if (filtradas.isEmpty()) {
            JOptionPane.showMessageDialog(view.getDespacho(), "No se encontraron recetas para este paciente en el rango de fechas válido");
        }
    }

    private void cambiarEstado(EstadoReceta nuevoEstado) {
        JTable tablaSeleccionada = null;
        DefaultTableModel modeloSeleccionado = null;

        if (view.getTablaIniciar().getSelectedRow() >= 0) {
            tablaSeleccionada = view.getTablaIniciar();
            modeloSeleccionado = tableModelIniciar;
        } else if (view.getTablaAlistar().getSelectedRow() >= 0) {
            tablaSeleccionada = view.getTablaAlistar();
            modeloSeleccionado = tableModelAlistar;
        } else if (view.getTablaRecetaLista().getSelectedRow() >= 0) {
            tablaSeleccionada = view.getTablaRecetaLista();
            modeloSeleccionado = tableModelLista;
        }

        if (tablaSeleccionada == null) {
            JOptionPane.showMessageDialog(view.getDespacho(), "Seleccione una receta primero.");
            return;
        }

        int row = tablaSeleccionada.getSelectedRow();
        String recetaId = modeloSeleccionado.getValueAt(row, 0).toString();
        Receta receta = model.getList().stream()
                .filter(r -> r.getId().equals(recetaId))
                .findFirst()
                .orElse(null);

        if (receta == null) {
            JOptionPane.showMessageDialog(view.getDespacho(), "Receta no encontrada.");
            return;
        }

        try {
            receta.setEstado(nuevoEstado);
            Service.instance().updateReceta(receta);
            model.setCurrent(receta);
            view.mostrarDetallesReceta(receta, nuevoEstado.name());
            model.setList(Service.instance().findAllRecetas());
            cargarRecetas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getDespacho(), "Error al cambiar estado: " + e.getMessage());
        }
    }
}
