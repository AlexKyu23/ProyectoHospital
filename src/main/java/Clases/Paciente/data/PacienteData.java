package Clases.Paciente.data;

import Clases.Paciente.logic.Paciente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PacienteData {
    private List<Paciente> pacientes;

    public PacienteData() {
        pacientes = new ArrayList<>();
        pacientes.add(new Paciente("PAC-001", "Laura", "8888-1111", LocalDate.of(1990, 5, 12)));
        pacientes.add(new Paciente("PAC-002", "Carlos", "8888-2222", LocalDate.of(1985, 8, 23)));
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }
}
