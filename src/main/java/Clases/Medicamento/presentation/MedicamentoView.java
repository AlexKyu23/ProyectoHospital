package Clases.Medicamento.presentation;

import Clases.Medicamento.logic.Medicamento;
import Clases.Medicamento.presentation.MedicamentoController;
import Clases.Medicamento.presentation.MedicamentoModel;
import Clases.Medicamento.presentation.MedicamentoTableModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MedicamentoView extends JPanel implements PropertyChangeListener {
    private MedicamentoController controller;
    private MedicamentoModel model;

    // 🔹 Componentes generados por .form
    private JPanel todo;
    private JPanel Medicamentos;
    private JPanel Busqueda;
    private JPanel Listado;
    private JTextField codigo;
    private JTextField nombre;
    private JTextField descripcion;
    private JTextField nombreBuscar;
    private JTextField descripcionBuscar;
    private JTextField codigoBuscar;
    private JButton guardarButton;
    private JButton borrarButton;
    private JButton limpiarButton;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable tablaMedicamentos;

    public JPanel getBusqueda() {
        return Busqueda;
    }
    public JPanel getListado() {
        return Listado;
    }
    public void setBusqueda(JPanel Busqueda) {
        this.Busqueda = Busqueda;
    }
    public void setListado(JPanel Listado) {
        this.Listado = Listado;
    }


    // 🔹 Integración MVC
    public void setController(MedicamentoController controller) {
        this.controller = controller;
        initListeners();
    }

    public void setModel(MedicamentoModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    private void initListeners() {
        guardarButton.addActionListener(e -> controller.guardar());
        borrarButton.addActionListener(e -> controller.borrar());
        limpiarButton.addActionListener(e -> controller.limpiar());
        buscarButton.addActionListener(e -> controller.buscar());
        reporteButton.addActionListener(e -> controller.reporte());

        tablaMedicamentos.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaMedicamentos.getSelectedRow();
            if (fila >= 0 && model.getList().size() > fila) {
                Medicamento seleccionado = model.getList().get(fila);
                model.setCurrent(seleccionado);
            }
        });
    }

    // 🔹 Actualización visual
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case MedicamentoModel.CURRENT -> {
                Medicamento m = model.getCurrent();
                if (m != null) {
                    codigo.setText(String.valueOf(m.getCodigo()));
                    nombre.setText(m.getNombre());
                    descripcion.setText(m.getDescripcion());
                }
            }
            case MedicamentoModel.LIST -> {
                int[] cols = {
                        MedicamentoTableModel.CODIGO,
                        MedicamentoTableModel.NOMBRE,
                        MedicamentoTableModel.DESCRIPCION
                };
                tablaMedicamentos.setModel(new MedicamentoTableModel(cols, model.getList()));
            }
        }
        todo.revalidate();
    }

    // 🔹 Getters para el controlador
    public JPanel getMainPanel() { return todo; }
    public JTable getTablaMedicamentos() { return tablaMedicamentos; }
    public JTextField getCodigo() { return codigo; }
    public JTextField getNombre() { return nombre; }
    public JTextField getDescripcion() { return descripcion; }
    public JTextField getNombreBuscar() { return nombreBuscar; }
    public JTextField getDescripcionBuscar() { return descripcionBuscar; }
    public JTextField getCodigoBuscar() { return codigoBuscar; }
    public JButton getGuardarButton() { return guardarButton; }
    public JButton getBorrarButton() { return borrarButton; }
    public JButton getLimpiarButton() { return limpiarButton; }
    public JButton getBuscarButton() { return buscarButton; }
    public JButton getReporteButton() { return reporteButton; }
}
