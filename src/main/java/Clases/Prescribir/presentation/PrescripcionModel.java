package Clases.Prescribir.presentation;

import Clases.AbstractModel;
import Clases.Medico.logic.Medico;
import Clases.Paciente.logic.Paciente;
import Clases.Receta.logic.ItemReceta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrescripcionModel extends AbstractModel {
    private Paciente paciente;
    private Medico medico;
    private List<ItemReceta> items;
    private LocalDate fechaRetiro;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public PrescripcionModel() {
        this.items = new ArrayList<>();
    }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
        firePropertyChange(CURRENT);
    }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) {
        this.medico = medico;
        firePropertyChange(CURRENT);
    }

    public List<ItemReceta> getItems() { return items; }
    public void agregarItem(ItemReceta item) {
        items.add(item);
        firePropertyChange(LIST);
    }

    public void eliminarItem(ItemReceta item) {
        items.remove(item);
        firePropertyChange(LIST);
    }

    public void limpiarItems() {
        items.clear();
        firePropertyChange(LIST);
    }

    public LocalDate getFechaRetiro() { return fechaRetiro; }
    public void setFechaRetiro(LocalDate fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
        firePropertyChange(CURRENT);
    }
}