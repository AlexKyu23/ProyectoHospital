package hospital.presentation.Prescribir.presentation;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

public class PrescribirView extends JPanel implements PropertyChangeListener {
    private PrescribirController controller;
    private PrescripcionModel model;

    // Componentes del .form
    private JPanel todo;
    private JPanel panel;
    private JButton buscarPacienteButton;
    private JButton agregarMedicamentoButton;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton descartarMedicamentoButton;
    private JButton detallesButton;
    private JLabel nombrePaciente;
    private JTable medicamentosPreenscritos;
    private DatePicker calendario;

    public PrescribirView() {
        // Si el diseñador no inicializa el DatePicker, lo hacemos manualmente
        if (calendario == null) {
            calendario = new DatePicker();
            calendario.getSettings().setAllowEmptyDates(false);
            calendario.getSettings().setDateRangeLimits(LocalDate.now(), null);
        }
    }

    public void setController(PrescribirController controller) {
        this.controller = controller;
        initListeners();
    }

    public void setModel(PrescripcionModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    private void initListeners() {
        buscarPacienteButton.addActionListener(e -> controller.buscarPaciente());
        agregarMedicamentoButton.addActionListener(e -> controller.agregarMedicamento());
        descartarMedicamentoButton.addActionListener(e -> controller.descartarMedicamento());
        limpiarButton.addActionListener(e -> controller.limpiarFormulario());
        guardarButton.addActionListener(e -> controller.guardarReceta());
        // detallesButton puede usarse para ver receta completa si lo implementás
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (PrescripcionModel.CURRENT.equals(evt.getPropertyName())) {
            if (model.getPaciente() != null) {
                nombrePaciente.setText("Paciente: " + model.getPaciente().getNombre());
            } else {
                nombrePaciente.setText("Paciente:");
            }
        }
    }

    // Getters para el controlador
    public JPanel getPanel() { return panel != null ? panel : todo; }
    public JButton getBuscarPacienteButton() { return buscarPacienteButton; }
    public JButton getAgregarMedicamentoButton() { return agregarMedicamentoButton; }
    public JButton getGuardarButton() { return guardarButton; }
    public JButton getLimpiarButton() { return limpiarButton; }
    public JButton getDescartarMedicamentoButton() { return descartarMedicamentoButton; }
    public JLabel getNombrePaciente() { return nombrePaciente; }
    public JTable getMedicamentosPreenscritos() { return medicamentosPreenscritos; }
    public DatePicker getCalendario() { return calendario; }
}

