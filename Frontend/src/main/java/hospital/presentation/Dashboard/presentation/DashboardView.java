// Frontend/src/main/java/hospital/presentation/Dashboard/presentation/DashboardView.java
package hospital.presentation.Dashboard.presentation;
import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class DashboardView implements PropertyChangeListener {
    private DashboardController controller;
    private DashboardModel model;

    // Componentes del .form
    private JPanel dashboard;
    private JPanel Datos;
    private JPanel Medicamentos;
    private JPanel Recetas;
    private JPanel Desde_hasta;
    private JComboBox desde_año;
    private JComboBox hasta_año;
    private JComboBox desde_diaMes;
    private JComboBox hasta_diaMes;
    private JComboBox medicamentos;
    private JButton checkButton;
    private JButton doubleCheckButton;
    private JPanel graficoMedicamentos;
    private JPanel graficoRecetas;
    private JPanel lista;

    public DashboardView() {
        initListeners();
    }

    private void initListeners() {
        checkButton.addActionListener(e -> controller.updateLineChart());
        doubleCheckButton.addActionListener(e -> controller.updatePieChart());

        // Actualizar modelo al cambiar combo
        desde_año.addActionListener(e -> updateFechaInicio());
        desde_diaMes.addActionListener(e -> updateFechaInicio());
        hasta_año.addActionListener(e -> updateFechaFin());
        hasta_diaMes.addActionListener(e -> updateFechaFin());
        medicamentos.addActionListener(e -> {
            model.setMedicamentoSeleccionado((String) medicamentos.getSelectedItem());
        });
    }

    private void updateFechaInicio() {
        try {
            int year = Integer.parseInt((String) desde_año.getSelectedItem());
            int month = Integer.parseInt((String) desde_diaMes.getSelectedItem());
            model.setFechaInicio(LocalDate.of(year, month, 1));
        } catch (Exception ignored) {}
    }

    private void updateFechaFin() {
        try {
            int year = Integer.parseInt((String) hasta_año.getSelectedItem());
            int month = Integer.parseInt((String) hasta_diaMes.getSelectedItem());
            model.setFechaFin(YearMonth.of(year, month).atEndOfMonth());
        } catch (Exception ignored) {}
    }

    public void setController(DashboardController controller) {
        this.controller = controller;
    }

    public void setModel(DashboardModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public JPanel getDashboard() { return dashboard; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case DashboardModel.MEDICAMENTOS -> {
                List<String> meds = model.getMedicamentos();
                medicamentos.setModel(new DefaultComboBoxModel<>(meds.toArray(new String[0])));
                medicamentos.setSelectedItem("Todos");
            }
            case DashboardModel.FECHA_INICIO -> {
                LocalDate d = model.getFechaInicio();
                desde_año.setSelectedItem(String.valueOf(d.getYear()));
                desde_diaMes.setSelectedItem(String.format("%02d", d.getMonthValue()));
            }
            case DashboardModel.FECHA_FIN -> {
                LocalDate d = model.getFechaFin();
                hasta_año.setSelectedItem(String.valueOf(d.getYear()));
                hasta_diaMes.setSelectedItem(String.format("%02d", d.getMonthValue()));
            }
            case DashboardModel.MEDICAMENTO -> {
                medicamentos.setSelectedItem(model.getMedicamentoSeleccionado());
            }
        }
        dashboard.revalidate();
    }

    public void updateLineChart(LocalDate start, LocalDate end, String medicamento) {
        graficoMedicamentos.removeAll();
            graficoMedicamentos.add(LineChart_AWT.getChartPanel(start, end, medicamento));
        graficoMedicamentos.revalidate();
        graficoMedicamentos.repaint();
    }

    public void updatePieChart() {
        graficoRecetas.removeAll();
        graficoRecetas.add(PieChart_AWT.getChartPanel());
        graficoRecetas.revalidate();
        graficoRecetas.repaint();
    }
}