package Clases.Medico.View;

import javax.swing.*;

public class MedicoView {
    private JPanel medicos;
    private JTabbedPane tabbedPane1;
    private JTextField id;
    private JTextField especialidad;
    private JTextField nombre;
    private JButton guardarButton;
    private JButton borrarButton;
    private JTextField nombreBuscar;
    private JButton buscarButton;
    private JButton reporteButton;
    private JPanel listado;
    private JPanel busqueda;
    private JButton limpiarButton;
    private JTextField idBuscar;
    private JPanel todo;

    // 🔹 Para integrarlo en AdminView
    public JPanel getMainPanel() {
        return todo;
    }

    // 🔹 Getters para los controles (el Controller necesita acceder)
    public JTextField getIdBuscar() {
        return idBuscar;
    }
    public JTextField getId() { return id; }
    public JTextField getEspecialidad() { return especialidad; }
    public JTextField getNombre() { return nombre; }
    public JTextField getNombreBuscar() { return nombreBuscar; }

    public JButton getGuardarButton() { return guardarButton; }
    public JButton getBorrarButton() { return borrarButton; }
    public JButton getBuscarButton() { return buscarButton; }
    public JButton getReporteButton() { return reporteButton; }
    public JButton getLimpiarButton() { return limpiarButton; }

    public JTabbedPane getTabbedPane1() { return tabbedPane1; }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
