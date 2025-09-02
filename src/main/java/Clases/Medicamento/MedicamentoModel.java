package Clases.Medicamento;

import Clases.AbstractModel;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoModel extends AbstractModel {
    private Medicamento current;
    private List<Medicamento> medicamentos;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public MedicamentoModel() {
        this.current = null;
        this.medicamentos = new ArrayList<>();
    }

    public Medicamento getCurrent() {
        return current;
    }

    public void setCurrent(Medicamento current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
        firePropertyChange(LIST);
    }

    public void addMedicamento(Medicamento medicamento) {
        this.medicamentos.add(medicamento);
        firePropertyChange(LIST);
    }

    public void deleteMedicamento(int codigo) {
        this.medicamentos.removeIf(m -> m.getCodigo() == codigo);
        firePropertyChange(LIST);
    }

    public Medicamento findByCodigo(int codigo) {
        return this.medicamentos.stream()
                .filter(m -> m.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    public Medicamento findByNombre(String nombre) {
        return this.medicamentos.stream()
                .filter(m -> m.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }
}
