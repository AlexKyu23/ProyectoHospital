package Clases.Prescribir;

import Listas.ListaMedicos;
import Listas.catalogoMedicamentos;
import Listas.RepositorioPrescripciones;
// import Listas.ListaPacientes; // <-- descomentar cuando tengas tu lista de pacientes
import Clases.Medico.Medico;
import Clases.Paciente.Paciente;
import Clases.Medicamento.Medicamento;

import com.github.lgooddatepicker.components.DatePicker;
import java.time.LocalDate;
import javax.swing.JOptionPane;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PrescribirController {
    private PrescribirView view;
    private PrescripcionModel model;
    private DefaultTableModel tableModel;

    private Medico medicoEnSesion;

    // Referencias a tus listas
    private ListaMedicos listaMedicos;
    private catalogoMedicamentos catalogoMed;
    // private ListaPacientes listaPacientes; // <-- usar cuando tengas definida la clase

    public PrescribirController(PrescribirView view, PrescripcionModel model/*, Medico medicoEnSesion,
                                ListaMedicos listaMedicos, catalogoMedicamentos catalogoMed , ListaPacientes listaPacientes */) {
        this.view = view;
        this.model = model;
       // this.medicoEnSesion = medicoEnSesion;
        //this.listaMedicos = listaMedicos;
       // this.catalogoMed = catalogoMed;
        // this.listaPacientes = listaPacientes;

        model.setMedico(medicoEnSesion);

        tableModel = new DefaultTableModel(new Object[]{"Medicamento"}, 0);
        view.getMedicamentosPreenscritos().setModel(tableModel);

        initController();
    }

    private void initController() {
        view.getBuscarPacienteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPaciente();
            }
        });

        view.getAgregarMedicamentoButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarMedicamento();
            }
        });

        view.getDescartarMedicamentoButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                descartarMedicamento();
            }
        });

        view.getLimpiarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });

        view.getGuardarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPrescripcion();
            }
        });

        view.getDetallesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDetalles();
            }
        });
    }

    private void buscarPaciente() {
        // 🔹 Aquí usarías tu clase de lista de pacientes
        // Ejemplo:
        // Paciente paciente = listaPacientes.seleccionarPaciente();
        Paciente paciente = null; // <- reemplazar por método real
        String nombre = JOptionPane.showInputDialog("Aquí debe abrir la lista de pacientes para seleccionar uno");
        if (nombre != null && !nombre.trim().isEmpty()) {
            paciente = new Paciente("id_temp", nombre); // <- temporal, reemplazar por selección real
            model.setPaciente(paciente);
            view.getNombrePacienteLabel().setText("Paciente: " + paciente.getNombre());
        }
    }

    private void agregarMedicamento() {
        // 🔹 Selección de medicamento real usando catalogoMedicamentos
        List<Medicamento> meds = catalogoMed.consulta();
        // Por ahora, usamos un input temporal
        String nombre = JOptionPane.showInputDialog("Aquí debe abrir la lista de medicamentos para seleccionar uno");
        if (nombre != null && !nombre.trim().isEmpty()) {
            Medicamento seleccionado = new Medicamento(nombre, "descripcion_temp", 0); // temporal
            model.agregarMedicamento(seleccionado);
            tableModel.addRow(new Object[]{seleccionado.getNombre()});
        }
    }

    private void descartarMedicamento() {
        int row = view.getMedicamentosPreenscritos().getSelectedRow();
        if (row >= 0) {
            String nombreMed = (String) tableModel.getValueAt(row, 0);
            Medicamento aEliminar = null;
            for (Medicamento m : model.getMedicamentos()) {
                if (m.getNombre().equals(nombreMed)) {
                    aEliminar = m;
                    break;
                }
            }
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
        PrescripcionModel nueva = new PrescripcionModel();
        nueva.setPaciente(model.getPaciente());
        nueva.setMedico(model.getMedico());

        // Guardar los medicamentos
        for (Medicamento med : model.getMedicamentos()) {
            nueva.agregarMedicamento(med);
        }

        // 🔹 Guardar la fecha seleccionada en el DatePicker
        LocalDate fechaSeleccionada = view.getCalendario().getDate();
        if (fechaSeleccionada != null) {
            nueva.setFechaRetiro(fechaSeleccionada);
        } else {
            JOptionPane.showMessageDialog(view.getPanel(), "Debe seleccionar una fecha de retiro");
            return; // no guardar si no hay fecha
        }

        // Guardar la prescripción en el repositorio
        RepositorioPrescripciones.agregar(nueva);

        // Mensaje de confirmación
        JOptionPane.showMessageDialog(view.getPanel(),
                "Prescripción guardada.\nMédico: " + nueva.getMedico().getNombre() +
                        "\nPaciente: " + nueva.getPaciente().getNombre() +
                        "\nMedicamentos: " + nueva.getMedicamentos().size() +
                        "\nFecha de retiro: " + fechaSeleccionada);
    }

    private void mostrarDetalles() {
        java.util.List<PrescripcionModel> lista = RepositorioPrescripciones.getPrescripciones();
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(view.getPanel(), "No hay prescripciones guardadas.");
            return;
        }

        String[] opciones = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            PrescripcionModel p = lista.get(i);
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
            PrescripcionModel presc = lista.get(index);

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
