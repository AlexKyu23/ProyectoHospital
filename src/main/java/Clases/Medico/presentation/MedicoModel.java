package Clases.Medico.presentation;

import Clases.AbstractModel;
import Clases.Medico.logic.Medico;

import java.util.ArrayList;
import java.util.List;

public class MedicoModel extends AbstractModel {
    private Medico current;
    private List<Medico> medicos;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public MedicoModel() {
        this.current = null;
        this.medicos = new ArrayList<>();
    }

    public Medico getCurrent() {
        return current;
    }

    public void setCurrent(Medico current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public void setMedicos(List<Medico> medicos) {
        this.medicos = medicos;
        firePropertyChange(LIST);
    }

    public void addMedico(Medico medico) {
        this.medicos.add(medico);
        firePropertyChange(LIST);
    }

    public void deleteMedico(String id) {
        this.medicos.removeIf(m -> m.getId().equals(id));
        firePropertyChange(LIST);
    }

    public Medico findById(String id) {
        return this.medicos.stream()
                .filter(m -> m.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public Medico findByNombre(String nombre) {
        return this.medicos.stream()
                .filter(m -> m.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }
}
