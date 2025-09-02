package Clases.Farmaceuta.View;

import javax.swing.*;

public class FarmaceutaView {
    private JPanel Farmaceutas;
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
    public JPanel getMainPanel() { return Farmaceutas; }

    // 🔹 Getters
    public JButton getGuardarButton() { return guardarButton; }
    public JButton getBorrarButton() { return borrarButton; }
    public JButton getBuscarButton() { return buscarButton; }
    public JButton getLimpiarButton() { return limpiarButton; }
    public JButton getReporteButton() { return reporteButton; }

    public JTextField getId() { return id; }
    public JTextField getNombre() { return nombre; }
    public JTextField getNombreBuscar() { return nombreBuscar; }

    public JPanel getListado() { return Listado; }
}
