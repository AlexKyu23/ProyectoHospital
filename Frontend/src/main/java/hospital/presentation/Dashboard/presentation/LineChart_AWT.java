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
            // ✅ Corregido: findRecetasBetween en lugar de searchRecetasBetween
            var recetas = Service.instance().findRecetasBetween(start, end);
            var conteoPorMes = new TreeMap<String, Integer>();

            for (var r : recetas) {
                LocalDate fecha = r.getFechaConfeccion();
                if (fecha == null) continue;

                String mesAno = fecha.format(fmt);

                // ✅ Validación: verifica que getMedicamentos() no sea null
                var medicamentos = r.getMedicamentos();
                if (medicamentos == null) continue;

                for (var item : medicamentos) {
                    String nombreMed = item.getDescripcion();

                    // ✅ Validación: verifica que nombreMed no sea null
                    if (nombreMed == null) continue;

                    if (medicamento == null || "Todos".equals(medicamento) || nombreMed.equals(medicamento)) {
                        conteoPorMes.put(mesAno, conteoPorMes.getOrDefault(mesAno, 0) + item.getCantidad());
                    }
                }
            }

            // ✅ Si no hay datos, agrega un punto en cero
            if (conteoPorMes.isEmpty()) {
                dataset.addValue(0, "Cantidad", start.format(fmt));
            } else {
                conteoPorMes.forEach((mes, cant) -> dataset.addValue(cant, "Cantidad", mes));
            }

        } catch (Exception e) {
            System.err.println("❌ Error al generar gráfico: " + e.getMessage());
            e.printStackTrace();
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