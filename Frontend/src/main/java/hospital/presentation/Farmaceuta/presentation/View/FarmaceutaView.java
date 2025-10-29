
package hospital.presentation.Farmaceuta.presentation.View;
import logic.Farmaceuta;
import hospital.presentation.Farmaceuta.presentation.FarmaceutaController;
import hospital.presentation.Farmaceuta.presentation.FarmaceutaModel;
import hospital.presentation.Farmaceuta.presentation.FarmaceutaTableModel;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FarmaceutaView implements PropertyChangeListener {
    private FarmaceutaController controller;
    private FarmaceutaModel model;


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

    // Para validación visual
    private static final Border BORDER_ERROR = BorderFactory.createLineBorder(java.awt.Color.RED, 1);
    private static final Border BORDER_NORMAL = UIManager.getBorder("TextField.border");

    public FarmaceutaView() {
        initListeners();
    }

    // === LISTENERS ===
    private void initListeners() {
        // BUSCAR
        buscarButton.addActionListener(e -> {
            try {
                Farmaceuta filter = new Farmaceuta();
                filter.setNombre(nombreBuscar.getText());
                controller.search(filter);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(todo, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // GUARDAR
        guardarButton.addActionListener(e -> {
            if (validate()) {
                Farmaceuta f = take();
                try {
                    controller.save(f);
                    JOptionPane.showMessageDialog(todo, "REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(todo, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // SELECCIONAR FILA
        tablaFarmaceutas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tablaFarmaceutas.getSelectedRow();
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


    }

    // === MVC ===
    public void setController(FarmaceutaController controller) {
        this.controller = controller;
    }

    public void setModel(FarmaceutaModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public JPanel getMainPanel() {
        return todo;
    }

    // === PROPERTY CHANGE ===
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FarmaceutaModel.LIST -> {
                int[] cols = {FarmaceutaTableModel.ID, FarmaceutaTableModel.NOMBRE};
                tablaFarmaceutas.setModel(new FarmaceutaTableModel(cols, model.getList()));
                tablaFarmaceutas.setRowHeight(30);
            }
            case FarmaceutaModel.CURRENT -> {
                Farmaceuta f = model.getCurrent();
                id.setText(f.getId());
                nombre.setText(f.getNombre());
                resetBorders();
            }
            case FarmaceutaModel.FILTER -> {
                nombreBuscar.setText(model.getFilter().getNombre());
            }
            case FarmaceutaModel.MODE -> {
                boolean editando = model.getMode() == 2; // MODE_EDIT
                guardarButton.setText(editando ? "Actualizar" : "Guardar");
                borrarButton.setEnabled(editando);
                id.setEnabled(!editando); // ID no editable al editar
            }
        }
        todo.revalidate();
    }

    // === TOMAR DATOS DEL FORMULARIO ===
    public Farmaceuta take() {
        Farmaceuta f = new Farmaceuta();
        f.setId(id.getText().trim());
        f.setNombre(nombre.getText().trim());
        f.setClave(f.getId()); // clave = id
        return f;
    }

    // === VALIDAR CAMPOS ===
    private boolean validate() {
        boolean valid = true;
        resetBorders();

        if (id.getText().trim().isEmpty()) {
            valid = false;
            id.setBorder(BORDER_ERROR);
        }

        if (nombre.getText().trim().isEmpty()) {
            valid = false;
            nombre.setBorder(BORDER_ERROR);
        }

        return valid;
    }

    private void resetBorders() {
        id.setBorder(BORDER_NORMAL);
        nombre.setBorder(BORDER_NORMAL);
    }

    // === GETTERS (para compatibilidad con Controller si los necesitas) ===
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