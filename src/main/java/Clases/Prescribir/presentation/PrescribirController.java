// Controlador principal del módulo de prescripción de recetas
package Clases.Prescribir.presentation;

// Importación de clases necesarias para lógica de medicamentos, pacientes, recetas y utilidades
import Clases.Medicamento.data.catalogoMedicamentos;
import Clases.Paciente.data.ListaPacientes;
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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.UUID;

public class PrescribirController {

    // Referencias al modelo y vista del módulo
    private PrescribirView view;
    private PrescripcionModel model;

    // Modelo de tabla para mostrar medicamentos preescritos
    private DefaultTableModel tableModel;

    // Datos de sesión y listas necesarias para prescripción
    private Medico medicoEnSesion;
    private ListaPacientes listaPacientes;
    private catalogoMedicamentos catalogoMed;

    // Constructor del controlador, recibe vista, modelo, médico en sesión y listas de pacientes y medicamentos
    public PrescribirController(PrescribirView view, PrescripcionModel model,
                                Medico medicoEnSesion,
                                ListaPacientes listaPacientes,
                                catalogoMedicamentos catalogoMed) {

        this.view = view;
        this.model = model;
        this.medicoEnSesion = medicoEnSesion;
        this.listaPacientes = listaPacientes;
        this.catalogoMed = catalogoMed;

        // Se asigna el médico al modelo
        model.setMedico(medicoEnSesion);

        // Se configura la tabla de medicamentos en la vista
        tableModel = new DefaultTableModel(new Object[]{"Medicamento", "Cantidad", "Indicaciones", "Duración"}, 0);
        view.getMedicamentosPreenscritos().setModel(tableModel);

        // Se inicializan los listeners de los botones
        initController();
    }

    public PrescribirController(PrescribirView prescView, PrescripcionModel prescModel) {
    }

    // Asocia los botones de la vista con sus respectivas acciones
    private void initController() {
        view.getBuscarPacienteButton().addActionListener(e -> buscarPaciente());
        view.getAgregarMedicamentoButton().addActionListener(e -> agregarMedicamento());
        view.getDescartarMedicamentoButton().addActionListener(e -> descartarMedicamento());
        view.getLimpiarButton().addActionListener(e -> limpiarFormulario());
        view.getGuardarButton().addActionListener(e -> guardarReceta());
    }

    // Busca un paciente por nombre y lo asigna al modelo
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

    // Agrega un medicamento a la receta actual
    private void agregarMedicamento() {
        String nombre = JOptionPane.showInputDialog("Ingrese nombre del medicamento:");
        if (nombre != null && !nombre.trim().isEmpty()) {
            Medicamento med = catalogoMed.busquedaPorDescripcion(nombre).stream().findFirst().orElse(null);
            if (med != null) {
                try {
                    int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad a prescribir:"));
                    String indicaciones = JOptionPane.showInputDialog("Indicaciones:");
                    int duracion = Integer.parseInt(JOptionPane.showInputDialog("Duración en días:"));

                    // Se crea el item de receta y se agrega al modelo
                    ItemReceta item = new ItemReceta(med.getCodigo(), med.getNombre(), cantidad, indicaciones, duracion);
                    model.agregarItem(item);

                    // Se actualiza la tabla visual
                    tableModel.addRow(new Object[]{med.getNombre(), cantidad, indicaciones, duracion});
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view.getPanel(), "Datos inválidos");
                }
            } else {
                JOptionPane.showMessageDialog(view.getPanel(), "Medicamento no encontrado.");
            }
        }
    }

    // Elimina un medicamento seleccionado de la receta
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

    // Limpia el formulario de prescripción
    private void limpiarFormulario() {
        model.setPaciente(null);
        model.limpiarItems();
        view.getNombrePacienteLabel().setText("Paciente:");
        tableModel.setRowCount(0);
        view.getCalendario().clear();
    }

    // Guarda la receta en el sistema y en archivo XML
    private void guardarReceta() {
        // Validación de paciente
        if (model.getPaciente() == null) {
            JOptionPane.showMessageDialog(view.getPanel(), "Debe seleccionar un paciente");
            return;
        }

        // Validación de fecha de retiro
        LocalDate fechaSeleccionada = view.getCalendario().getDate();
        if (fechaSeleccionada == null) {
            JOptionPane.showMessageDialog(view.getPanel(), "Debe seleccionar una fecha de retiro");
            return;
        }

        // Se genera un ID único para la receta
        String recetaId = UUID.randomUUID().toString();

        // Se crea la receta con los datos actuales
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
            // Se guarda la receta en el servicio
            RecetaService.instance().create(receta);

            // Se guarda en archivo XML
            RecetasWrapper wrapper = new RecetasWrapper();
            wrapper.setRecetas(RecetaService.instance().findAll());
            XmlPersister.save(wrapper, new File("recetas.xml"));

            // Se muestra confirmación al usuario
            JOptionPane.showMessageDialog(view.getPanel(),
                    "Receta guardada.\nMédico: " + model.getMedico().getNombre() +
                            "\nPaciente: " + model.getPaciente().getNombre() +
                            "\nMedicamentos: " + receta.getMedicamentos().size() +
                            "\nFecha de retiro: " + fechaSeleccionada);

            // Se limpia el formulario para nueva receta
            limpiarFormulario();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view.getPanel(), "Error: " + ex.getMessage());
        }
    }
}

