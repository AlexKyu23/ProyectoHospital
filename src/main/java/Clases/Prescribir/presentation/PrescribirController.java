package Clases.Prescribir.presentation;

import Clases.Medicamento.data.catalogoMedicamentos;
import Clases.Medicamento.presentation.MedicamentoModel;
import Clases.Medicamento.presentation.MedicamentoView;
import Clases.Paciente.data.ListaPacientes;
import Clases.Paciente.presentation.PacienteModel;
import Clases.Paciente.presentation.View.PacienteView;
import Clases.Receta.logic.ItemReceta;
import Clases.Receta.logic.Receta;
import Clases.Receta.logic.EstadoReceta;
import Clases.Receta.Data.RecetasWrapper;
import Clases.Receta.logic.RecetaService;
import Clases.Medico.logic.Medico;
import Clases.Paciente.logic.Paciente;
import Clases.Medicamento.logic.Medicamento;
import Clases.Usuario.data.XmlPersister;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.time.LocalDate;
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

        System.out.println("🔍 Pacientes disponibles: " + listaPacientes.consulta().size());
        System.out.println("🔍 Medicamentos disponibles: " + catalogoMed.consulta().size());

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
        PacienteModel selectorModel = new PacienteModel();
        selectorModel.setList(listaPacientes.consulta());

        PacienteView selectorView = new PacienteView();
        selectorView.setModel(selectorModel);
        selectorView.(); // ← asegura que la tabla se actualice

        JFrame selectorFrame = new JFrame("Buscar Paciente");
        selectorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        selectorFrame.setSize(600, 400);
        selectorFrame.setLocationRelativeTo(view.getPanel());
        selectorFrame.setContentPane(selectorView.getMainPanel());
        selectorFrame.setVisible(true);

        selectorView.getTablaPacientes().getSelectionModel().addListSelectionListener(e -> {
            int fila = selectorView.getTablaPacientes().getSelectedRow();
            if (fila >= 0 && selectorModel.getList().size() > fila) {
                Paciente seleccionado = selectorModel.getList().get(fila);
                model.setPaciente(seleccionado);
                view.getNombrePacienteLabel().setText("Paciente: " + seleccionado.getNombre());
                selectorFrame.dispose();
            }
        });
    }

    private void agregarMedicamento() {
        MedicamentoModel selectorModel = new MedicamentoModel();
        selectorModel.setList(catalogoMed.consulta());

        MedicamentoView selectorView = new MedicamentoView();
        selectorView.setModel(selectorModel);
        selectorView.actualizarTabla(); // ← asegura que la tabla se actualice

        JFrame selectorFrame = new JFrame("Buscar Medicamento");
        selectorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        selectorFrame.setSize(600, 400);
        selectorFrame.setLocationRelativeTo(view.getPanel());
        selectorFrame.setContentPane(selectorView.getMainPanel());
        selectorFrame.setVisible(true);

        selectorView.getTablaMedicamentos().getSelectionModel().addListSelectionListener(e -> {
            int fila = selectorView.getTablaMedicamentos().getSelectedRow();
            if (fila >= 0 && selectorModel.getList().size() > fila) {
                Medicamento seleccionado = selectorModel.getList().get(fila);
                selectorFrame.dispose();

                try {
                    int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad a prescribir:"));
                    String indicaciones = JOptionPane.showInputDialog("Indicaciones:");
                    int duracion = Integer.parseInt(JOptionPane.showInputDialog("Duración en días:"));

                    ItemReceta item = new ItemReceta(seleccionado.getCodigo(), seleccionado.getNombre(), cantidad, indicaciones, duracion);
                    model.agregarItem(item);
                    tableModel.addRow(new Object[]{seleccionado.getNombre(), cantidad, indicaciones, duracion});
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view.getPanel(), "Datos inválidos");
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
