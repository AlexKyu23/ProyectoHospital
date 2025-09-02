package Clases.Paciente.View;

import javax.swing.*;

public class PacienteView {
    private JPanel Pacientes;
    private JTextField id;
    private JTextField nombre;
    private JButton guardarButton;
    private JButton borrarButton;
    private JButton limpiarButton;
    private JPanel Búsqueda;
    private JTextField nombreBuscar;
    private JButton buscarButton;
    private JButton reporteButton;
    private JPanel Listado;

    // 🔹 Para integrarlo en AdminView
    public JPanel getMainPanel() { return Pacientes; }

    // 🔹 Getters
    public JTextField getId() { return id; }
    public JTextField getNombre() { return nombre; }
    public JTextField getNombreBuscar() { return nombreBuscar; }

    public JButton getGuardarButton() { return guardarButton; }
    public JButton getBorrarButton() { return borrarButton; }
    public JButton getLimpiarButton() { return limpiarButton; }
    public JButton getBuscarButton() { return buscarButton; }
    public JButton getReporteButton() { return reporteButton; }

    public JPanel getListado() { return Listado; }
}

