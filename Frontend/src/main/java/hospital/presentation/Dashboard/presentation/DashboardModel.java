// Frontend/src/main/java/hospital/presentation/Dashboard/presentation/DashboardModel.java
package hospital.presentation.Dashboard.presentation;


import hospital.presentation.AbstractModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DashboardModel extends AbstractModel {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String medicamentoSeleccionado;
    private List<String> medicamentos;

    public static final String FECHA_INICIO = "fechaInicio";
    public static final String FECHA_FIN = "fechaFin";
    public static final String MEDICAMENTO = "medicamento";
    public static final String MEDICAMENTOS = "medicamentos";

    public DashboardModel() {
        init();
    }

    public void init() {
        fechaInicio = LocalDate.of(2025, 1, 1);
        fechaFin = LocalDate.of(2025, 12, 31);
        medicamentoSeleccionado = "Todos";
        medicamentos = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(java.beans.PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(FECHA_INICIO);
        firePropertyChange(FECHA_FIN);
        firePropertyChange(MEDICAMENTO);
        firePropertyChange(MEDICAMENTOS);
    }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
        firePropertyChange(FECHA_INICIO);
    }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
        firePropertyChange(FECHA_FIN);
    }

    public String getMedicamentoSeleccionado() { return medicamentoSeleccionado; }
    public void setMedicamentoSeleccionado(String medicamentoSeleccionado) {
        this.medicamentoSeleccionado = medicamentoSeleccionado;
        firePropertyChange(MEDICAMENTO);
    }

    public List<String> getMedicamentos() { return medicamentos; }
    public void setMedicamentos(List<String> medicamentos) {
        this.medicamentos = medicamentos;
        firePropertyChange(MEDICAMENTOS);
    }
}