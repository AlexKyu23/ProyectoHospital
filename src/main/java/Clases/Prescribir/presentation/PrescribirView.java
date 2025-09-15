package Clases.Prescribir.presentation;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.time.LocalDate;

public class PrescribirView {
    private JButton buscarPacienteButton;
    private JButton agregarMedicamentoButton;
    private JLabel NombrePaciente;
    private JTable MedicamentosPreenscritos;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton descartarMedicamentoButton;
    private JPanel todo;
    private JButton detallesButton;
    private DatePicker Calendario;


    public PrescribirView() {
        createUIComponents();
    }

    private void createUIComponents() {
        Calendario = new DatePicker();
        Calendario.getSettings().setAllowEmptyDates(false);
        Calendario.getSettings().setDateRangeLimits(LocalDate.now(), null); // Solo fechas futuras o actuales
    }

    public JButton getBuscarPacienteButton() { return buscarPacienteButton; }
    public JButton getAgregarMedicamentoButton() { return agregarMedicamentoButton; }
    public JButton getGuardarButton() { return guardarButton; }
    public JButton getLimpiarButton() { return limpiarButton; }
    public JButton getDescartarMedicamentoButton() { return descartarMedicamentoButton; }
    public JLabel getNombrePacienteLabel() { return NombrePaciente; }
    public JTable getMedicamentosPreenscritos() { return MedicamentosPreenscritos; }
    public JPanel getPanel() { return todo; }
    public DatePicker getCalendario() { return Calendario; }
}
