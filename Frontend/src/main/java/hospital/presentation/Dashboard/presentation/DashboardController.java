// Frontend/src/main/java/hospital/presentation/Dashboard/presentation/DashboardController.java
package hospital.presentation.Dashboard.presentation;

import hospital.logic.Service;
import hospital.presentation.Refresher;
import hospital.presentation.ThreadListener;
import logic.Medicamento;


import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;

public class DashboardController implements ThreadListener {
    private final DashboardView view;
    private final DashboardModel model;
    private Refresher refresher;

    public DashboardController(DashboardView view, DashboardModel model) {
        this.view = view;
        this.model = model;

        model.init();
        cargarMedicamentos();

        view.setController(this);
        view.setModel(model);

        refresher = new Refresher(this);
        refresher.start();

        updateLineChart();
        updatePieChart();
    }

    private void cargarMedicamentos() {
        try {
            List<String> meds = Service.instance().findAllMedicamentos().stream()
                    .map(Medicamento::getNombre)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());
            meds.add(0, "Todos");
            model.setMedicamentos(meds);
        } catch (Exception e) {
            model.setMedicamentos(List.of("Todos"));
        }
    }

    public void updateLineChart() {
        LocalDate start = model.getFechaInicio();
        LocalDate end = model.getFechaFin();
        String med = "Todos".equals(model.getMedicamentoSeleccionado()) ? null : model.getMedicamentoSeleccionado();

        view.updateLineChart(start, end, med);
    }

    public void updatePieChart() {
        view.updatePieChart();
    }

    @Override
    public void refresh() {
        cargarMedicamentos();
        updateLineChart();
        updatePieChart();
    }

    public void stop() {
        if (refresher != null) refresher.stop();
    }
}