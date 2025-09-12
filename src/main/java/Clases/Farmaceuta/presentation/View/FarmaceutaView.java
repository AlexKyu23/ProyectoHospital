package Clases.Farmaceuta.presentation.View;

import Clases.Farmaceuta.logic.Farmaceuta;
import Clases.Farmaceuta.presentation.FarmaceutaController;
import Clases.Farmaceuta.presentation.FarmaceutaModel;
import Clases.Farmaceuta.presentation.FarmaceutaTableModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FarmaceutaView extends JPanel implements PropertyChangeListener {
    private FarmaceutaController controller;
    private FarmaceutaModel model;

    // 🔹 Componentes generados por .form
    private JPanel todo;
    private JPanel farmaceutas;
    private JPanel busqueda;
    private JPanel listado;
    private JTextField id;
    private JTextField nombre;
    private JTextField nombreBuscar;
    private JButton guardarButton;
    private JButton borrarButton;
    private JButton limpiarButton;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable tablaFarmaceutas;

    // 🔹 Integración MVC
    public void setController(FarmaceutaController controller) {
        this.controller = controller;
        initListeners();
    }

    public void setModel(FarmaceutaModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    private void initListeners() {
        guardarButton.addActionListener(e -> controller.guardar());
        borrarButton.addActionListener(e -> controller.borrar());
        limpiarButton.addActionListener(e -> controller.limpiar());
        buscarButton.addActionListener(e -> controller.buscar());
        reporteButton.addActionListener(e -> controller.reporte());

        tablaFarmaceutas.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaFarmaceutas.getSelectedRow();
            if (fila >= 0 && model.getList().size() > fila) {
                Farmaceuta seleccionado = model.getList().get(fila);
                model.setCurrent(seleccionado);
            }
        });
    }

    // 🔹 Actualización visual
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FarmaceutaModel.CURRENT -> {
                Farmaceuta f = model.getCurrent();
                if (f != null) {
                    id.setText(f.getId());
                    nombre.setText(f.getNombre());
                }
            }
            case FarmaceutaModel.LIST -> {
                int[] cols = {FarmaceutaTableModel.ID, FarmaceutaTableModel.NOMBRE};
                tablaFarmaceutas.setModel(new FarmaceutaTableModel(cols, model.getList()));
            }
        }
        todo.revalidate();
    }

    // 🔹 Getters para el controlador
    public JPanel getMainPanel() { return todo; }
    public JTextField getId() { return id; }
    public JTextField getNombre() { return nombre; }
    public JTextField getNombreBuscar() { return nombreBuscar; }
    public JTable getTablaFarmaceutas() { return tablaFarmaceutas; }
    public JButton getGuardarButton() { return guardarButton; }
    public JButton getBorrarButton() { return borrarButton; }
    public JButton getLimpiarButton() { return limpiarButton; }
    public JButton getBuscarButton() { return buscarButton; }
    public JButton getReporteButton() { return reporteButton; }
}
