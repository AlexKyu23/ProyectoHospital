package Clases.Receta.Presentation;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class RecetaView extends JPanel implements PropertyChangeListener {
    private JTable tableRecetas;
    private JTextField idBuscar;
    private JButton botonBuscar;
    private JPanel panel;

    private RecetaModel model;

    public void setModel(RecetaModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
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
    public RecetaView() {
        panel = new JPanel(new BorderLayout());
        tableRecetas = new JTable();
        panel.add(new JScrollPane(tableRecetas), BorderLayout.CENTER);

        idBuscar = new JTextField(15);
        botonBuscar = new JButton("Buscar");

        JPanel buscador = new JPanel();
        buscador.add(new JLabel("ID:"));
        buscador.add(idBuscar);
        buscador.add(botonBuscar);

        panel.add(buscador, BorderLayout.NORTH);
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTable getTableRecetas() {
        return tableRecetas;
    }

    public JTextField getIdBuscar() {
        return idBuscar;
    }

    public JButton getBotonBuscar() {
        return botonBuscar;
    }
}
