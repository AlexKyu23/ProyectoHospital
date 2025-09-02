package Clases.Medico.View;

import javax.swing.*;

public class MedicoView {
    private JPanel Medicos;       // panel raíz generado en el UI Designer
    private JTabbedPane tabbedPane1;
    private JLabel Medico;
    private JTextField id;
    private JTextField especialidad;
    private JTextField nombre;
    private JButton guardarButton;
    private JButton borrarButton;
    private JTextField nombreBuscar;
    private JButton buscarButton;
    private JButton reporteButton;
    private JPanel Listado;
    private JPanel Busqueda;
    private JButton limpiarButton;

    // 🔹 Para integrarlo en AdminView
    public JPanel getMainPanel() {
        return Medicos;
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
