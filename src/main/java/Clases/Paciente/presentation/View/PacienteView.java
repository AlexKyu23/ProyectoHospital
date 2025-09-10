package Clases.Paciente.presentation.View;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;

public class PacienteView {
    private JPanel Pacientes;
    private JTextField id;
    private JTextField nombre;
    private JButton guardarButton;
    private JButton borrarButton;
    private JButton limpiarButton;
    private JPanel Busqueda;
    private JTextField nombreBuscar;
    private JButton buscarButton;
    private JButton reporteButton;
    private JPanel Listado;
    private JTextField idBuscar;
    private DatePicker fechaNacimiento;
    private JTextField numeroTelefono;
    private JPanel todo;
    private JTable tablaPacientes;

    // 🔹 Para integrarlo en AdminView
    public JPanel getMainPanel() { return todo; }
    public JPanel getBusquedaPanel() { return Busqueda; }
    public JPanel getListadoPanel() { return Listado; }
    public JTextField getIdBuscar() { return idBuscar; }
    public DatePicker getFechaNacimiento() { return fechaNacimiento; }
    public JTextField getNumeroTelefono() { return numeroTelefono; }
    public JTable getTablaPacientes() { return tablaPacientes; }
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

