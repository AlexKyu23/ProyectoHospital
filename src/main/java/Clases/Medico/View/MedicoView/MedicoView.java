package Clases.Medico.View.MedicoView;

import javax.swing.*;

public class MedicoView {
    private JTabbedPane tabbedPane1;
    private JPanel medicos;
    private JPanel medico;
    private JTextField id;
    private JTextField especialidad;
    private JTextField nombre;
    private JButton guardarButton;
    private JButton borrarButton;
    private JButton limpiarButton;
    private JPanel busqueda;
    private JPanel listado;
    private JTextField nombreBuscar;
    private JTextField idBuscar;
    private JButton buscarButton;
    private JButton reporteButton;
    // 🔹 Para integrarlo en AdminView
    public JPanel getMainPanel() {
        return medicos;
    }

    // 🔹 Getters para los controles (el Controller necesita acceder)
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
}
