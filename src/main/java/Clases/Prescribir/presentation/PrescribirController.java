package Clases.Prescribir.presentation;

import Clases.Medicamento.presentation.MedicamentoModel;
import Clases.Medicamento.presentation.MedicamentoView;
import Clases.Paciente.presentation.PacienteModel;
import Clases.Paciente.presentation.View.PacienteView;
import Clases.Receta.logic.ItemReceta;
import Clases.Receta.logic.Receta;
import Clases.Receta.logic.EstadoReceta;
import Clases.Receta.logic.RecetaService;
import Clases.Medico.logic.Medico;
import Clases.Paciente.logic.Paciente;
import Clases.Medicamento.logic.Medicamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.UUID;

public class PrescribirController {
    private PrescribirView view;
    private PrescripcionModel model;
    private DefaultTableModel tableModel;
    private Medico medicoEnSesion;
    private PacienteModel pacienteModel;
    private MedicamentoModel medicamentoModel;

    public PrescribirController(PrescribirView view, PrescripcionModel model,
                                Medico medicoEnSesion, PacienteModel pacienteModel,
                                MedicamentoModel medicamentoModel) {
        this.view = view;
        this.model = model;
        this.medicoEnSesion = medicoEnSesion;
        this.pacienteModel = pacienteModel;
        this.medicamentoModel = medicamentoModel;

        model.setMedico(medicoEnSesion);
        tableModel = new DefaultTableModel(new Object[]{"Medicamento", "Cantidad", "Indicaciones", "Duración"}, 0);
        view.getMedicamentosPreenscritos().setModel(tableModel);

        initController();
    }

    private void initController() {
        view.getBuscarPacienteButton().addActionListener(e -> buscarPaciente());
        view.getAgregarMedicamentoButton().addActionListener(e -> agregarMedicamento());
        view.getDescartarMedicamentoButton().addActionListener(e -> descartarMedicamento());
        view.getLimpiarButton().addActionListener(e -> limpiarFormulario());
        view.getGuardarButton().addActionListener(e -> guardarReceta());
    }

    private void buscarPaciente() {
        PacienteView selectorView = new PacienteView();
        selectorView.setModel(pacienteModel);

        // Crear un panel combinado para busqueda y listado
        JPanel combinedPanel = new JPanel();
        combinedPanel.setLayout(new java.awt.BorderLayout());
        combinedPanel.add(selectorView.getBusqueda(), java.awt.BorderLayout.NORTH);
        combinedPanel.add(selectorView.getListado(), java.awt.BorderLayout.CENTER);

        JFrame selectorFrame = new JFrame("Buscar Paciente");
        selectorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        selectorFrame.setSize(600, 400);
        selectorFrame.setLocationRelativeTo(view.getPanel());
        selectorFrame.setContentPane(combinedPanel);
        selectorFrame.setVisible(true);

        // Configurar el botón de búsqueda del panel busqueda
        selectorView.getBuscarButton().addActionListener(e -> {
            String nombreBusqueda = selectorView.getNombreBuscar().getText().toLowerCase();


            PacienteModel filteredModel = new PacienteModel();
            filteredModel.setList(pacienteModel.getList().stream()
                    .filter(p -> (nombreBusqueda.isEmpty() || p.getNombre().toLowerCase().contains(nombreBusqueda)))
                    .toList());
            selectorView.setModel(filteredModel);
        });

        // Selección de paciente desde la tabla
        selectorView.getTablaPacientes().getSelectionModel().addListSelectionListener(e -> {
            int fila = selectorView.getTablaPacientes().getSelectedRow();
            if (fila >= 0 && pacienteModel.getList().size() > fila) {
                Paciente seleccionado = pacienteModel.getList().get(fila);
                model.setPaciente(seleccionado);
                view.getNombrePacienteLabel().setText("Paciente: " + seleccionado.getNombre());
                selectorFrame.dispose();
            }
        });
    }

    private void agregarMedicamento() {
        MedicamentoView selectorView = new MedicamentoView();
        selectorView.setModel(medicamentoModel);

        // Crear un panel combinado para busqueda y listado
        JPanel combinedPanel = new JPanel();
        combinedPanel.setLayout(new java.awt.BorderLayout());
        combinedPanel.add(selectorView.getBusqueda(), java.awt.BorderLayout.NORTH);
        combinedPanel.add(selectorView.getListado(), java.awt.BorderLayout.CENTER);

        JFrame selectorFrame = new JFrame("Buscar Medicamento");
        selectorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        selectorFrame.setSize(600, 400);
        selectorFrame.setLocationRelativeTo(view.getPanel());
        selectorFrame.setContentPane(combinedPanel);
        selectorFrame.setVisible(true);

        // Configurar el botón de búsqueda del panel busqueda
        selectorView.getBuscarButton().addActionListener(e -> {
            String nombreBusqueda = selectorView.getNombreBuscar().getText().toLowerCase();
            String codigoBusqueda = selectorView.getCodigoBuscar().getText();
            String descripcionBusqueda = selectorView.getDescripcionBuscar().getText().toLowerCase();

            MedicamentoModel filteredModel = new MedicamentoModel();
            filteredModel.setList(medicamentoModel.getList().stream()
                    .filter(m -> (nombreBusqueda.isEmpty() || m.getNombre().toLowerCase().contains(nombreBusqueda))
                            && (codigoBusqueda.isEmpty() || String.valueOf(m.getCodigo()).contains(codigoBusqueda))
                            && (descripcionBusqueda.isEmpty() || m.getDescripcion().toLowerCase().contains(descripcionBusqueda)))
                    .toList());
            selectorView.setModel(filteredModel);
        });

        // Selección de medicamento desde la tabla
        selectorView.getTablaMedicamentos().getSelectionModel().addListSelectionListener(e -> {
            int fila = selectorView.getTablaMedicamentos().getSelectedRow();
            if (fila >= 0 && medicamentoModel.getList().size() > fila) {
                Medicamento seleccionado = medicamentoModel.getList().get(fila);
                selectorFrame.dispose();

                try {
                    int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad a prescribir:"));
                    String indicaciones = JOptionPane.showInputDialog("Indicaciones:");
                    int duracion = Integer.parseInt(JOptionPane.showInputDialog("Duración en días:"));

                    ItemReceta item = new ItemReceta(seleccionado.getCodigo(), seleccionado.getNombre(), cantidad, indicaciones, duracion);
                    model.agregarItem(item);
                    tableModel.addRow(new Object[]{seleccionado.getNombre(), cantidad, indicaciones, duracion});
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view.getPanel(), "Datos inválidos: " + ex.getMessage());
                }
            }
        });
    }

    private void descartarMedicamento() {
        int row = view.getMedicamentosPreenscritos().getSelectedRow();
        if (row >= 0) {
            String nombreMed = (String) tableModel.getValueAt(row, 0);
            ItemReceta aEliminar = model.getItems().stream()
                    .filter(i -> i.getDescripcion().equals(nombreMed))
                    .findFirst().orElse(null);
            if (aEliminar != null) {
                model.eliminarItem(aEliminar);
                tableModel.removeRow(row);
            }
        }
    }

    private void limpiarFormulario() {
        model.setPaciente(null);
        model.limpiarItems();
        view.getNombrePacienteLabel().setText("Paciente:");
        tableModel.setRowCount(0);
        view.getCalendario().clear();
    }

    private void guardarReceta() {
        if (model.getPaciente() == null) {
            JOptionPane.showMessageDialog(view.getPanel(), "Debe seleccionar un paciente");
            return;
        }

        LocalDate fechaSeleccionada = null;
        try {
            fechaSeleccionada = view.getCalendario().getDate();
            if (fechaSeleccionada == null) {
                JOptionPane.showMessageDialog(view.getPanel(), "Debe seleccionar una fecha de retiro válida");
                return;
            }
            if (fechaSeleccionada.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(view.getPanel(), "La fecha de retiro no puede ser anterior a hoy");
                return;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view.getPanel(), "Error al procesar la fecha: " + ex.getMessage());
            return;
        }

        String recetaId = UUID.randomUUID().toString();
        Receta receta = new Receta(
                recetaId,
                model.getMedico().getId(),
                model.getPaciente().getId(),
                LocalDate.now(),
                fechaSeleccionada
        );
        receta.setMedicamentos(model.getItems());

        try {
            RecetaService.instance().create(receta);
            System.out.println("✅ Prescripción guardada correctamente: ID=" + recetaId +
                    ", Médico=" + model.getMedico().getNombre() +
                    ", Paciente=" + model.getPaciente().getNombre() +
                    ", Fecha de retiro=" + fechaSeleccionada);
            JOptionPane.showMessageDialog(view.getPanel(),
                    "Receta guardada.\nMédico: " + model.getMedico().getNombre() +
                            "\nPaciente: " + model.getPaciente().getNombre() +
                            "\nMedicamentos: " + receta.getMedicamentos().size() +
                            "\nFecha de retiro: " + fechaSeleccionada);
            limpiarFormulario();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view.getPanel(), "Error al guardar la receta: " + ex.getMessage());
        }
    }
}