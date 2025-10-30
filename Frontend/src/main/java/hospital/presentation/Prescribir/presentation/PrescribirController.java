package hospital.presentation.Prescribir.presentation;

import hospital.logic.Service;
import hospital.presentation.Medicamento.presentation.MedicamentoModel;
import hospital.presentation.Medicamento.presentation.MedicamentoView;
import hospital.presentation.Paciente.presentation.PacienteModel;
import hospital.presentation.Paciente.presentation.View.PacienteView;
import logic.ItemReceta;
import logic.Medico;
import logic.Medicamento;
import logic.Paciente;
import logic.Receta;
import logic.EstadoReceta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.UUID;

public class PrescribirController {
    private final PrescribirView view;
    private final PrescripcionModel model;
    private final Medico medicoEnSesion;
    private final PacienteModel pacienteModel;
    private final MedicamentoModel medicamentoModel;
    private final DefaultTableModel tableModel;

    public PrescribirController(PrescribirView view, PrescripcionModel model,
                                Medico medicoEnSesion,
                                PacienteModel pacienteModel,
                                MedicamentoModel medicamentoModel) {
        this.view = view;
        this.model = model;
        this.medicoEnSesion = medicoEnSesion;
        this.pacienteModel = pacienteModel;
        this.medicamentoModel = medicamentoModel;

        model.setMedico(medicoEnSesion);
        tableModel = new DefaultTableModel(new Object[]{"Medicamento", "Cantidad", "Indicaciones", "Duración"}, 0);
        view.getMedicamentosPreenscritos().setModel(tableModel);

        view.setController(this);
        view.setModel(model);
    }

    public void buscarPaciente() {
        PacienteView selectorView = new PacienteView();
        selectorView.setModel(pacienteModel);

        JFrame selectorFrame = new JFrame("Buscar Paciente");
        selectorFrame.setSize(600, 400);
        selectorFrame.setLocationRelativeTo(view.getPanel());
        selectorFrame.setContentPane(selectorView.getListado());
        selectorFrame.setVisible(true);

        selectorView.getBuscarButton().addActionListener(e -> {
            String nombre = selectorView.getNombreBuscar().getText().toLowerCase();
            PacienteModel filtered = new PacienteModel();
            filtered.setList(pacienteModel.getList().stream()
                    .filter(p -> p.getNombre().toLowerCase().contains(nombre))
                    .toList());
            selectorView.setModel(filtered);
        });

        selectorView.getTablaPacientes().getSelectionModel().addListSelectionListener(e -> {
            int fila = selectorView.getTablaPacientes().getSelectedRow();
            if (fila >= 0 && pacienteModel.getList().size() > fila) {
                Paciente seleccionado = pacienteModel.getList().get(fila);
                model.setPaciente(seleccionado);
                selectorFrame.dispose();
            }
        });
    }

    public void agregarMedicamento() {
        MedicamentoView selectorView = new MedicamentoView();
        selectorView.setModel(medicamentoModel);

        JFrame selectorFrame = new JFrame("Buscar Medicamento");
        selectorFrame.setSize(600, 400);
        selectorFrame.setLocationRelativeTo(view.getPanel());
        selectorFrame.setContentPane(selectorView.getMainPanel());
        selectorFrame.setVisible(true);

        selectorView.getBuscarButton().addActionListener(e -> {
            String nombre = selectorView.getNombre().getText().toLowerCase();
            String descripcion = selectorView.getDescripcionBuscar().getText().toLowerCase();
            MedicamentoModel filtered = new MedicamentoModel();
            filtered.setList(medicamentoModel.getList().stream()
                    .filter(m -> m.getNombre().toLowerCase().contains(nombre) || m.getDescripcion().toLowerCase().contains(descripcion))
                    .toList());
            selectorView.setModel(filtered);
        });

        selectorView.getTablaMedicamentos().getSelectionModel().addListSelectionListener(e -> {
            int fila = selectorView.getTablaMedicamentos().getSelectedRow();
            if (fila >= 0 && medicamentoModel.getList().size() > fila) {
                Medicamento seleccionado = medicamentoModel.getList().get(fila);
                try {
                    int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad:"));
                    String indicaciones = JOptionPane.showInputDialog("Indicaciones:");
                    int duracion = Integer.parseInt(JOptionPane.showInputDialog("Duración en días:"));

                    ItemReceta item = new ItemReceta();
                    item.setItemRecetaId("IR" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
                    item.setMedicamentoCodigo(seleccionado.getCodigo())

                    item.setDescripcion(seleccionado.getNombre());
                    item.setCantidad(cantidad);
                    item.setIndicaciones(indicaciones);
                    item.setDuracionDias(duracion);

                    model.agregarItem(item);
                    tableModel.addRow(new Object[]{seleccionado.getNombre(), cantidad, indicaciones, duracion});
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view.getPanel(), "Datos inválidos: " + ex.getMessage());
                }
                selectorFrame.dispose();
            }
        });
    }

    public void descartarMedicamento() {
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

    public void limpiarFormulario() {
        model.setPaciente(null);
        model.limpiarItems();
        view.getNombrePaciente().setText("Paciente:");
        tableModel.setRowCount(0);
        view.getCalendario().clear();
    }

    public void guardarReceta() {
        if (model.getPaciente() == null) {
            JOptionPane.showMessageDialog(view.getPanel(), "Debe seleccionar un paciente");
            return;
        }

        LocalDate fechaRetiro = view.getCalendario().getDate();
        if (fechaRetiro == null || fechaRetiro.isBefore(LocalDate.now())) {
            JOptionPane.showMessageDialog(view.getPanel(), "Seleccione una fecha de retiro válida");
            return;
        }

        try {
            String recetaId = "R" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
            Receta receta = new Receta(
                    recetaId,
                    model.getMedico().getId(),
                    model.getPaciente().getId(),
                    LocalDate.now(),
                    fechaRetiro
            );
            receta.setEstado(EstadoReceta.CONFECCIONADA);
            receta.setMedicamentos(model.getItems());

            Service.instance().createReceta(receta);
            JOptionPane.showMessageDialog(view.getPanel(), "Receta guardada correctamente");
            limpiarFormulario();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getPanel(), "Error al guardar receta: " + e.getMessage());
        }
    }
}