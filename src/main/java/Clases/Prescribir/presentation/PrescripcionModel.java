package Clases.Prescribir.presentation;

import Clases.AbstractModel;
import Clases.Medico.logic.Medico;
import Clases.Paciente.logic.Paciente;
import Clases.Medicamento.logic.Medicamento;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class PrescripcionModel extends AbstractModel {
    private Paciente paciente;
    private Medico medico;
    private List<Medicamento> medicamentos;
    private LocalDate fechaRetiro;


    public PrescripcionModel() {
        this.medicamentos = new ArrayList<>();
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void agregarMedicamento(Medicamento medicamento) {
        medicamentos.add(medicamento);
    }

    public void eliminarMedicamento(Medicamento medicamento) {
        medicamentos.remove(medicamento);
    }

    public void limpiarMedicamentos() {
        medicamentos.clear();
    }

    public LocalDate getFechaRetiro() { return fechaRetiro; }

    public void setFechaRetiro(LocalDate fechaRetiro) { this.fechaRetiro = fechaRetiro; }


}
