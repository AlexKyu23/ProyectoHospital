package Clases.Prescribir.presentation;

import Clases.Prescribir.data.PrescripcionesWrapper;
import Clases.Prescribir.logic.Prescripcion;
import Listas.*;
import Clases.Medico.logic.Medico;
import Clases.Paciente.logic.Paciente;
import Clases.Medicamento.logic.Medicamento;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PrescribirController {

    private PrescribirView view;
    private PrescripcionModel model;
    private DefaultTableModel tableModel;

    private Medico medicoEnSesion;

    private ListaMedicos listaMedicos;
    private catalogoMedicamentos catalogoMed;
    private ListaPacientes listaPacientes;

    public PrescribirController(PrescribirView view, PrescripcionModel model,
                                Medico medicoEnSesion, ListaMedicos listaMedicos,
                                catalogoMedicamentos catalogoMed, ListaPacientes listaPacientes) {

        this.view = view;
        this.model = model;
        this.medicoEnSesion = medicoEnSesion;
        this.listaMedicos = listaMedicos;
        this.catalogoMed = catalogoMed;
        this.listaPacientes = listaPacientes;

        model.setMedico(medicoEnSesion);

        tableModel = new DefaultTableModel(new Object[]{"Medicamento"}, 0);
        view.getMedicamentosPreenscritos().setModel(tableModel);

        initController();
    }

    private void initController() {
        view.getBuscarPacienteButton().addActionListener(e -> buscarPaciente());
        view.getAgregarMedicamentoButton().addActionListener(e -> agregarMedicamento());
        view.getDescartarMedicamentoButton().addActionListener(e -> descartarMedicamento());
        view.getLimpiarButton().addActionListener(e -> limpiarFormulario());
        view.getGuardarButton().addActionListener(e -> guardarPrescripcion());
        view.getDetallesButton().addActionListener(e -> mostrarDetalles());
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
                model.agregarMedicamento(med);
                tableModel.addRow(new Object[]{med.getNombre()});
            } else {
                JOptionPane.showMessageDialog(view.getPanel(), "Medicamento no encontrado.");
            }
        }
    }

    private void descartarMedicamento() {
        int row = view.getMedicamentosPreenscritos().getSelectedRow();
        if (row >= 0) {
            String nombreMed = (String) tableModel.getValueAt(row, 0);
            Medicamento aEliminar = model.getMedicamentos().stream()
                    .filter(m -> m.getNombre().equals(nombreMed))
                    .findFirst().orElse(null);
            if (aEliminar != null) {
                model.eliminarMedicamento(aEliminar);
                tableModel.removeRow(row);
            }
        }
    }

    private void limpiarFormulario() {
        model.setPaciente(null);
        model.limpiarMedicamentos();
        view.getNombrePacienteLabel().setText("Paciente:");
        tableModel.setRowCount(0);
        view.getCalendario().clear();
    }

    private void guardarPrescripcion() {
        if (model.getPaciente() == null) {
            JOptionPane.showMessageDialog(view.getPanel(), "Debe seleccionar un paciente");
            return;
        }

        LocalDate fechaSeleccionada = view.getCalendario().getDate();
        if (fechaSeleccionada == null) {
            JOptionPane.showMessageDialog(view.getPanel(), "Debe seleccionar una fecha de retiro");
            return;
        }

        // Crear objeto Prescripcion
        Prescripcion presc = new Prescripcion(
                model.getPaciente(),
                model.getMedico(),
                model.getMedicamentos(),
                LocalDateTime.now(),
                fechaSeleccionada.atStartOfDay(),
                "confeccionada"
        );

        RepositorioPrescripciones.agregar(presc);

        // Guardar en XML usando XmlPersister con un wrapper
        try {
            PrescripcionesWrapper wrapper = new PrescripcionesWrapper();
            wrapper.setPrescripciones(RepositorioPrescripciones.getPrescripciones());
            XmlPersister.save(wrapper, new File("prescripciones.xml"));
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view.getPanel(), "Error guardando la prescripción en XML");
            return;
        }

        JOptionPane.showMessageDialog(view.getPanel(),
                "Prescripción guardada.\nMédico: " + presc.getMedico().getNombre() +
                        "\nPaciente: " + presc.getPaciente().getNombre() +
                        "\nMedicamentos: " + presc.getMedicamentos().size() +
                        "\nFecha de retiro: " + fechaSeleccionada);

        limpiarFormulario();
    }

    private void mostrarDetalles() {
        List<Prescripcion> lista = RepositorioPrescripciones.getPrescripciones();
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(view.getPanel(), "No hay prescripciones guardadas.");
            return;
        }

        String[] opciones = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            Prescripcion p = lista.get(i);
            opciones[i] = (i + 1) + ". " + p.getPaciente().getNombre() +
                    " | Médico: " + p.getMedico().getNombre() +
                    " | Fecha de retiro: " + (p.getFechaRetiro() != null ? p.getFechaRetiro() : "No asignada") +
                    " (" + p.getMedicamentos().size() + " meds)";
        }

        String seleccion = (String) JOptionPane.showInputDialog(
                view.getPanel(),
                "Seleccione una prescripción:",
                "Detalles de Prescripciones",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                opciones[0]);

        if (seleccion != null) {
            int index = Integer.parseInt(seleccion.split("\\.")[0]) - 1;
            Prescripcion presc = lista.get(index);

            int decision = JOptionPane.showConfirmDialog(
                    view.getPanel(),
                    "Médico: " + presc.getMedico().getNombre() +
                            "\nPaciente: " + presc.getPaciente().getNombre() +
                            "\nMedicamentos: " + presc.getMedicamentos() +
                            "\nFecha de retiro: " + (presc.getFechaRetiro() != null ? presc.getFechaRetiro() : "No asignada") +
                            "\n\n¿Desea eliminar esta prescripción?",
                    "Detalles",
                    JOptionPane.YES_NO_OPTION);

            if (decision == JOptionPane.YES_OPTION) {
                RepositorioPrescripciones.eliminar(presc);
                JOptionPane.showMessageDialog(view.getPanel(), "Prescripción eliminada.");
            }
        }
    }

}
