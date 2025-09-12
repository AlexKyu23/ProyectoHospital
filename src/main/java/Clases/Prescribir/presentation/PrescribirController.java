// Controlador principal del módulo de prescripción de recetas
package Clases.Prescribir.presentation;

// Importación de clases necesarias para lógica de medicamentos, pacientes, recetas y utilidades
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
import Listas.*;
import Clases.Medico.logic.Medico;
import Clases.Paciente.logic.Paciente;
import Clases.Medicamento.logic.Medicamento;

import java.io.File;
import java.time.LocalDate;
import javax.swing.*;
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
        PacienteModel selectorModel = new PacienteModel();
        selectorModel.setList(listaPacientes.consulta());

        PacienteView selectorView = new PacienteView();
        selectorView.setModel(selectorModel);

        JFrame selectorFrame = new JFrame("Buscar Paciente");
        selectorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        selectorFrame.setSize(600, 400);
        selectorFrame.setLocationRelativeTo(view.getPanel());

        // Usamos solo el panel de búsqueda o listado
        selectorFrame.setContentPane(selectorView.getBusqueda()); // o getListado()
        selectorFrame.setVisible(true);

        // Podés agregar un botón "Seleccionar" en ese panel si querés confirmar
        // O simplemente usar el evento de selección de tabla
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

        JFrame selectorFrame = new JFrame("Buscar Medicamento");
        selectorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        selectorFrame.setSize(600, 400);
        selectorFrame.setLocationRelativeTo(view.getPanel());

        selectorFrame.setContentPane(selectorView.getBusqueda()); // o getListado()
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

