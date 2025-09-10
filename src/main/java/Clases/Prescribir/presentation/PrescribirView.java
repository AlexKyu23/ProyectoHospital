package Clases.Prescribir.presentation;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;

public class PrescribirView {
    private JButton buscarPacienteButton;
    private JButton agregarMedicamentoButton;
    private JLabel NombrePaciente;
    private JTable MedicamentosPreenscritos;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton descartarMedicamentoButton;
    private JButton detallesButton;
    private JPanel todo;
    private DatePicker Calendario;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public JButton getBuscarPacienteButton() {
        return buscarPacienteButton;
    }

    public JButton getAgregarMedicamentoButton() {
        return agregarMedicamentoButton;
    }

    public JButton getGuardarButton() {
        return guardarButton;
    }

    public JButton getLimpiarButton() {
        return limpiarButton;
    }

    public JButton getDescartarMedicamentoButton() {
        return descartarMedicamentoButton;
    }

    public JButton getDetallesButton() {
        return detallesButton;
    }

    public JLabel getNombrePacienteLabel() {
        return NombrePaciente;
    }

    public JTable getMedicamentosPreenscritos() {
        return MedicamentosPreenscritos;
    }

    public JPanel getPanel() {
        return todo;
    }

    public DatePicker getCalendario() {
        return Calendario;
    }
}
