// Frontend/src/main/java/presentation/Medicamento/presentation/MedicamentoView.java
package hospital.presentation.Medicamento.presentation;

import hospital.Application;
import logic.Medicamento;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MedicamentoView implements PropertyChangeListener {
    private MedicamentoController controller;
    private MedicamentoModel model;

    // Componentes del .form
    private JPanel todo;
    private JPanel Medicamentos;
    private JPanel Busqueda;
    private JPanel listado;
    private JTextField codigo;
    private JTextField nombre;
    private JTextField descripcion;
    // ❌ REMOVIDO: private JTextField nombreBuscar;
    private JTextField descripcionBuscar;
    private JTextField codigoBuscar;
    private JButton guardarButton;
    private JButton borrarButton;
    private JButton limpiarButton;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable tablaMedicamentos;

    private static final Border BORDER_ERROR = BorderFactory.createLineBorder(java.awt.Color.RED, 1);
    private static final Border BORDER_NORMAL = UIManager.getBorder("TextField.border");

    public MedicamentoView() {
        initListeners();
    }

    private void initListeners() {
        // BUSCAR - Solo por descripción y código
        buscarButton.addActionListener(e -> {
            try {
                Medicamento filter = new Medicamento();
                // ❌ REMOVIDO: filter.setNombre(nombreBuscar.getText());
                filter.setDescripcion(descripcionBuscar.getText());
                try {
                    filter.setCodigo(Integer.parseInt(codigoBuscar.getText()));
                } catch (NumberFormatException ex) {
                    filter.setCodigo(0);
                }
                controller.search(filter);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(todo, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // GUARDAR
        guardarButton.addActionListener(e -> {
            if (validate()) {
                Medicamento m = take();
                try {
                    controller.save(m);
                    JOptionPane.showMessageDialog(todo, "REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(todo, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // SELECCIONAR FILA
        tablaMedicamentos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tablaMedicamentos.getSelectedRow();
                if (row >= 0) {
                    controller.edit(row);
                }
            }
        });

        // BORRAR
        borrarButton.addActionListener(e -> {
            try {
                controller.delete();
                JOptionPane.showMessageDialog(todo, "REGISTRO BORRADO", "", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(todo, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // LIMPIAR
        limpiarButton.addActionListener(e -> controller.clear());

        // REPORTE
        reporteButton.addActionListener(e -> controller.reporte());
    }

    public void setController(MedicamentoController controller) {
        this.controller = controller;
    }

    public void setModel(MedicamentoModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public JPanel getMainPanel() { return todo; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case MedicamentoModel.LIST -> {
                int[] cols = {MedicamentoTableModel.CODIGO, MedicamentoTableModel.NOMBRE, MedicamentoTableModel.DESCRIPCION};
                tablaMedicamentos.setModel(new MedicamentoTableModel(cols, model.getList()));
                tablaMedicamentos.setRowHeight(30);
            }
            case MedicamentoModel.CURRENT -> {
                Medicamento m = model.getCurrent();
                codigo.setText(String.valueOf(m.getCodigo()));
                nombre.setText(m.getNombre());
                descripcion.setText(m.getDescripcion());
                resetBorders();
            }
            case MedicamentoModel.FILTER -> {
                Medicamento f = model.getFilter();
                // ❌ REMOVIDO: nombreBuscar.setText(f.getNombre());
                descripcionBuscar.setText(f.getDescripcion());
                codigoBuscar.setText(f.getCodigo() > 0 ? String.valueOf(f.getCodigo()) : "");
            }
            case MedicamentoModel.MODE -> {
                boolean editando = model.getMode() == Application.MODE_EDIT;
                guardarButton.setText(editando ? "Actualizar" : "Guardar");
                borrarButton.setEnabled(editando);
                codigo.setEnabled(!editando);
            }
        }
        todo.revalidate();
    }

    public Medicamento take() {
        Medicamento m = new Medicamento();
        m.setCodigo(Integer.parseInt(codigo.getText().trim()));
        m.setNombre(nombre.getText().trim());
        m.setDescripcion(descripcion.getText().trim());
        return m;
    }

    private boolean validate() {
        boolean valid = true;
        resetBorders();

        try {
            Integer.parseInt(codigo.getText().trim());
        } catch (NumberFormatException e) {
            valid = false;
            codigo.setBorder(BORDER_ERROR);
        }

        if (nombre.getText().trim().isEmpty()) {
            valid = false;
            nombre.setBorder(BORDER_ERROR);
        }

        if (descripcion.getText().trim().isEmpty()) {
            valid = false;
            descripcion.setBorder(BORDER_ERROR);
        }

        return valid;
    }

    private void resetBorders() {
        codigo.setBorder(BORDER_NORMAL);
        nombre.setBorder(BORDER_NORMAL);
        descripcion.setBorder(BORDER_NORMAL);
    }

    // Getters
    public JTextField getCodigo() { return codigo; }
    public JTextField getNombre() { return nombre; }
    public JTextField getDescripcion() { return descripcion; }
    // ❌ REMOVIDO: public JTextField getNombreBuscar() { return nombreBuscar; }
    public JTextField getDescripcionBuscar() { return descripcionBuscar; }
    public JTextField getCodigoBuscar() { return codigoBuscar; }
    public JTable getTablaMedicamentos() { return tablaMedicamentos; }
    public JPanel getListado() {return listado;}
    public JButton getBuscarButton() { return buscarButton; }
}