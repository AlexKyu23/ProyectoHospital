package Clases.Paciente.presentation.View;

import Clases.Paciente.logic.Paciente;
import Clases.Paciente.presentation.PacienteController;
import Clases.Paciente.presentation.PacienteModel;
import Clases.Paciente.presentation.PacienteTableModel;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PacienteView extends JPanel implements PropertyChangeListener {
    private PacienteController controller;
    private PacienteModel model;

    private JPanel todo;
    private JTextField id;
    private JTextField nombre;
    private JTextField numeroTelefono;
    private DatePicker fechaNacimiento;
    private JTextField nombreBuscar;
    private JButton guardarButton;
    private JButton borrarButton;
    private JButton limpiarButton;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable tablaPacientes;

    // 🔹 Integración MVC
    public void setController(PacienteController controller) {
        this.controller = controller;
        initListeners();
    }

    public void setModel(PacienteModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    private void initListeners() {
        guardarButton.addActionListener(e -> controller.guardar());
        borrarButton.addActionListener(e -> controller.borrar());
        limpiarButton.addActionListener(e -> controller.limpiar());
        buscarButton.addActionListener(e -> controller.buscar());
        reporteButton.addActionListener(e -> controller.reporte());

        tablaPacientes.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaPacientes.getSelectedRow();
            if (fila >= 0 && model.getList().size() > fila) {
                Paciente seleccionado = model.getList().get(fila);
                model.setCurrent(seleccionado);
            }
        });
    }

    // 🔹 Actualización visual
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case PacienteModel.CURRENT -> {
                Paciente p = model.getCurrent();
                if (p != null) {
                    id.setText(p.getId());
                    nombre.setText(p.getNombre());
                    numeroTelefono.setText(p.getTelefono());
                    fechaNacimiento.setDate(p.getFechaNacimiento());
                }
            }
            case PacienteModel.LIST -> {
                int[] cols = {
                        PacienteTableModel.ID,
                        PacienteTableModel.NOMBRE,
                        PacienteTableModel.TELEFONO,
                        PacienteTableModel.FECHA
                };
                tablaPacientes.setModel(new PacienteTableModel(cols, model.getList()));
            }
        }
        todo.revalidate();
    }

    // 🔹 Getters para el controlador
    public JPanel getMainPanel() { return todo; }
    public JTable getTablaPacientes() { return tablaPacientes; }
    public JTextField getId() { return id; }
    public JTextField getNombre() { return nombre; }
    public JTextField getNumeroTelefono() { return numeroTelefono; }
    public DatePicker getFechaNacimiento() { return fechaNacimiento; }
    public JTextField getNombreBuscar() { return nombreBuscar; }
    public JButton getGuardarButton() { return guardarButton; }
    public JButton getBorrarButton() { return borrarButton; }
    public JButton getLimpiarButton() { return limpiarButton; }
    public JButton getBuscarButton() { return buscarButton; }
    public JButton getReporteButton() { return reporteButton; }
}

