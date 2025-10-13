package Clases.Medicamento.data;

import Clases.Medicamento.logic.Medicamento;
import java.util.ArrayList;
import java.util.List;

public class catalogoMedicamentos {
    private List<Medicamento> medicamentos;

    public catalogoMedicamentos() {
        medicamentos = new ArrayList<>();
    }

    public List<Medicamento> consulta() {
        return medicamentos;
    }

    public void setList(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos != null ? medicamentos : new ArrayList<>();
    }

    public void inclusion(Medicamento medicamento) {
        medicamentos.add(medicamento);
    }

    public List<Medicamento> busquedaPorDescripcion(String descripcion) {
        List<Medicamento> resultados = new ArrayList<>();
        for (Medicamento m : medicamentos) {
            if (m.getDescripcion().equalsIgnoreCase(descripcion)) {
                resultados.add(m);
            }
        }
        return resultados;
    }

    public Medicamento busquedaPorCodigo(int codigo) {
        return medicamentos.stream()
                .filter(m -> m.getCodigo() == codigo)
                .findFirst().orElse(null);
    }

    public void modificacion(int codigo, Medicamento nuevoMedicamento) {
        for (int i = 0; i < medicamentos.size(); i++) {
            if (medicamentos.get(i).getCodigo() == codigo) {
                medicamentos.set(i, nuevoMedicamento);
                return;
            }
        }
    }

    public void borrado(int codigo) {
        medicamentos.removeIf(m -> m.getCodigo() == codigo);
    }
}

