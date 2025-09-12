package Clases.Medico.data;

import Clases.Medico.logic.Medico;

import java.util.ArrayList;
import java.util.List;

public class MedicoData {
    private List<Medico> medicos;

    public MedicoData() {
        medicos = new ArrayList<>();

        // Datos quemados de ejemplo
        medicos.add(new Medico("MED-111", "David", "123", "Pediatría"));
        medicos.add(new Medico("MED-222", "Miguel", "123", "Neurocirugía"));
    }

    public List<Medico> getMedicos() {
        return medicos;
    }
}
