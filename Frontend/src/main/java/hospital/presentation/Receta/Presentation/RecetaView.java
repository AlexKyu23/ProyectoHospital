package hospital.presentation.Receta.Presentation;

import logic.Receta;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class RecetaView extends JPanel implements PropertyChangeListener {
    private RecetaController controller;
    private RecetaModel model;

    // Componentes del .form
    private JPanel mainPanel;
    private JTable tableRecetas;
    private JTextField idBuscar;
    private JButton botonBuscar;

    public void setModel(RecetaModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(RecetaController controller) {
        this.controller = controller;
        initListeners();
    }

    private void initListeners() {
        botonBuscar.addActionListener(e -> controller.buscarPorId());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (RecetaModel.LIST.equals(evt.getPropertyName())) {
            int[] cols = {
                    RecetaTableModel.ID,
                    RecetaTableModel.MEDICO,
                    RecetaTableModel.PACIENTE,
                    RecetaTableModel.FECHA,
                    RecetaTableModel.ESTADO,
                    RecetaTableModel.MEDICAMENTOS
            };
            tableRecetas.setModel(new RecetaTableModel(cols, model.getList()));
        }
    }

    // Getters para el controlador
    public JPanel getMainPanel() { return mainPanel; }
    public JTable getTableRecetas() { return tableRecetas; }
    public JTextField getIdBuscar() { return idBuscar; }
    public JButton getBotonBuscar() { return botonBuscar; }
}
