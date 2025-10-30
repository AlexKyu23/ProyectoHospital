// hospital/presentation/Dashboard/presentation/LineChart_AWT.java
package hospital.presentation.Dashboard.presentation;

import hospital.logic.Service;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

public class LineChart_AWT {

    public static JPanel getChartPanel(LocalDate start, LocalDate end, String medicamento) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM");

        try {
            var recetas = Service.instance().searchRecetasBetween(start, end);
            var conteoPorMes = new TreeMap<String, Integer>();

            for (var r : recetas) {
                LocalDate fecha = r.getFechaConfeccion();
                if (fecha == null) continue;

                String mesAno = fecha.format(fmt);

                for (var item : r.getMedicamentos()) {
                    String nombreMed = item.getDescripcion(); // ✅ corregido

                    if (medicamento == null || "Todos".equals(medicamento) || nombreMed.equals(medicamento)) {
                        conteoPorMes.put(mesAno, conteoPorMes.getOrDefault(mesAno, 0) + item.getCantidad());
                    }
                }
            }

            conteoPorMes.forEach((mes, cant) -> dataset.addValue(cant, "Cantidad", mes));

        } catch (Exception e) {
            dataset.addValue(0, "Cantidad", "Error");
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Cantidad de Medicamentos Prescritos por Mes",
                "Mes",
                "Cantidad",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        return new ChartPanel(chart);
    }

}