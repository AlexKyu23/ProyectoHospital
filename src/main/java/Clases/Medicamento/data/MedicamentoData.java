package Clases.Medicamento.data;

import Clases.Medicamento.logic.Medicamento;

import java.util.ArrayList;
import java.util.List;

public class MedicamentoData {
    private List<Medicamento> medicamentos;

    public MedicamentoData() {
        medicamentos = new ArrayList<>();
        medicamentos.add(new Medicamento("Paracetamol", "Analgésico y antipirético", 101));
        medicamentos.add(new Medicamento("Amoxicilina", "Antibiótico de amplio espectro", 102));
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }
}
