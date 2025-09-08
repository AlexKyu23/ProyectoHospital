package Clases.Medicamento.View;

import javax.swing.*;

public class MedicamentoView {
    private JPanel Medicamentos;
    private JTextField codigo;
    private JTextField nombre;
    private JTextField descripcion;
    private JButton guardarButton;
    private JButton borrarButton;
    private JButton limpiarButton;
    private JPanel Busqueda;
    private JTextField nombreBuscar;
    private JButton buscarButton;
    private JButton reporteButton;
    private JPanel Listado;
    private JTextField descripcionBuscar;
    private JTextField codigoBuscar;
    private JPanel todo;

    // 🔹 Getters
    public JPanel getMainPanel() { return todo; }

    public JTextField getCodigo() { return codigo; }
    public JTextField getNombre() { return nombre; }
    public JTextField getDescripcion() { return descripcion; }
    public JTextField getNombreBuscar() { return nombreBuscar; }
    public JTextField getDescripcionBuscar() { return descripcionBuscar; }
    public JTextField getCodigoBuscar() { return codigoBuscar; }
    public JButton getGuardarButton() { return guardarButton; }
    public JButton getBorrarButton() { return borrarButton; }
    public JButton getBuscarButton() { return buscarButton; }
    public JButton getLimpiarButton() { return limpiarButton; }
    public JButton getReporteButton() { return reporteButton; }

    public JPanel getListado() { return Listado; }
}
