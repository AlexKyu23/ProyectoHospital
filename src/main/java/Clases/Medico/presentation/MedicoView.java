package Clases.Medico.presentation;

import Clases.Medico.logic.Medico;
import Clases.Medico.presentation.MedicoTableModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MedicoView extends JPanel implements PropertyChangeListener {
    private MedicoController controller;
    private MedicoModel model;

    private JPanel medicos;
    private JPanel medico;
    private JPanel busqueda;
    private JPanel listado;
    private JTextField id;
    private JTextField especialidad;
    private JTextField nombre;
    private JTextField nombreBuscar;
    private JButton guardarButton;
    private JButton borrarButton;
    private JButton limpiarButton;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable tablaMedicos;
    private JTextField idBuscar;

    // 🔹 Integración MVC
    public void setController(MedicoController controller) {
        this.controller = controller;
        initListeners();
    }

    public void setModel(MedicoModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    private void initListeners() {
        guardarButton.addActionListener(e -> controller.guardar());
        borrarButton.addActionListener(e -> controller.borrar());
        limpiarButton.addActionListener(e -> controller.limpiar());
        buscarButton.addActionListener(e -> controller.buscar());
        reporteButton.addActionListener(e -> controller.reporte());

        tablaMedicos.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaMedicos.getSelectedRow();
            if (fila >= 0 && model.getList().size() > fila) {
                Medico seleccionado = model.getList().get(fila);
                model.setCurrent(seleccionado);
            }
        });
    }

    // 🔹 Actualización visual
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case MedicoModel.CURRENT -> {
                Medico m = model.getCurrent();
                if (m != null) {
                    id.setText(m.getId());
                    nombre.setText(m.getNombre());
                    especialidad.setText(m.getEspecialidad());
                }
            }
            case MedicoModel.LIST -> {
                int[] cols = {
                        MedicoTableModel.ID,
                        MedicoTableModel.NOMBRE,
                        MedicoTableModel.ESPECIALIDAD
                };
                tablaMedicos.setModel(new MedicoTableModel(cols, model.getList()));
            }
        }
        medicos.revalidate();
    }

    // 🔹 Getters para el controlador
    public JPanel getMainPanel() { return medicos; }
    public JTable getTablaMedicos() { return tablaMedicos; }
    public JTextField getId() { return id; }
    public JTextField getEspecialidad() { return especialidad; }
    public JTextField getNombre() { return nombre; }
    public JTextField getNombreBuscar() { return nombreBuscar; }
    public JButton getGuardarButton() { return guardarButton; }
    public JButton getBorrarButton() { return borrarButton; }
    public JButton getBuscarButton() { return buscarButton; }
    public JButton getReporteButton() { return reporteButton; }
    public JButton getLimpiarButton() { return limpiarButton; }
}
