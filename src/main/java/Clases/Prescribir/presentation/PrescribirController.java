package Clases.Prescribir.presentation;

import Clases.Receta.logic.ItemReceta;
import Clases.Receta.logic.Receta;
import Clases.Receta.logic.EstadoReceta;
import Clases.Receta.Data.RecetasWrapper;
import Clases.Receta.logic.RecetaService;
import Listas.*;
import Clases.Medico.logic.Medico;
import Clases.Paciente.logic.Paciente;
import Clases.Medicamento.logic.Medicamento;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.UUID;

public class PrescribirController {

    private PrescribirView view;
    private PrescripcionModel model;
    private DefaultTableModel tableModel;

    private Medico medicoEnSesion;
    private ListaPacientes listaPacientes;
    private catalogoMedicamentos catalogoMed;

    public PrescribirController(PrescribirView view, PrescripcionModel model,
                                Medico medicoEnSesion,
                                ListaPacientes listaPacientes,
                                catalogoMedicamentos catalogoMed) {

        this.view = view;
        this.model = model;
        this.medicoEnSesion = medicoEnSesion;
        this.listaPacientes = listaPacientes;
        this.catalogoMed = catalogoMed;

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
        String nombre = JOptionPane.showInputDialog("Ingrese nombre del paciente:");
        if (nombre != null && !nombre.trim().isEmpty()) {
            Paciente paciente = listaPacientes.busquedaPorNombre(nombre);
            if (paciente != null) {
                model.setPaciente(paciente);
                view.getNombrePacienteLabel().setText("Paciente: " + paciente.getNombre());
            } else {
                JOptionPane.showMessageDialog(view.getPanel(), "Paciente no encontrado.");
            }
        }
    }

    private void agregarMedicamento() {
        String nombre = JOptionPane.showInputDialog("Ingrese nombre del medicamento:");
        if (nombre != null && !nombre.trim().isEmpty()) {
            Medicamento med = catalogoMed.busquedaPorDescripcion(nombre).stream().findFirst().orElse(null);
            if (med != null) {
                try {
                    int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad a prescribir:"));
                    String indicaciones = JOptionPane.showInputDialog("Indicaciones:");
                    int duracion = Integer.parseInt(JOptionPane.showInputDialog("Duración en días:"));

                    ItemReceta item = new ItemReceta(med.getCodigo(), med.getNombre(), cantidad, indicaciones, duracion);
                    model.agregarItem(item);
                    tableModel.addRow(new Object[]{med.getNombre(), cantidad, indicaciones, duracion});
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view.getPanel(), "Datos inválidos");
                }
            } else {
                JOptionPane.showMessageDialog(view.getPanel(), "Medicamento no encontrado.");
            }
        }
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

        LocalDate fechaSeleccionada = view.getCalendario().getDate();
        if (fechaSeleccionada == null) {
            JOptionPane.showMessageDialog(view.getPanel(), "Debe seleccionar una fecha de retiro");
            return;
        }

        String recetaId = UUID.randomUUID().toString();

        Receta receta = new Receta(
                recetaId,
                model.getMedico().getId(),
                model.getPaciente().getId(),
                LocalDate.now(),
                fechaSeleccionada,
                EstadoReceta.CONFECCIONADA
        );
        receta.setMedicamentos(model.getItems());

        try {
            RecetaService.instance().create(receta);

            RecetasWrapper wrapper = new RecetasWrapper();
            wrapper.setRecetas(RecetaService.instance().findAll());
            XmlPersister.save(wrapper, new File("recetas.xml"));

            JOptionPane.showMessageDialog(view.getPanel(),
                    "Receta guardada.\nMédico: " + model.getMedico().getNombre() +
                            "\nPaciente: " + model.getPaciente().getNombre() +
                            "\nMedicamentos: " + receta.getMedicamentos().size() +
                            "\nFecha de retiro: " + fechaSeleccionada);

            limpiarFormulario();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view.getPanel(), "Error: " + ex.getMessage());
        }
    }
}
