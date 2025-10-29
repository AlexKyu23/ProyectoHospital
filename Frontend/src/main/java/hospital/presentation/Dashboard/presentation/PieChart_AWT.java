package hospital.presentation.Dashboard.presentation;

import hospital.logic.Service;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class PieChart_AWT {

    public static JPanel getChartPanel() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        try {
            var recetas = Service.instance().findAllRecetas();
            Map<String, Integer> conteoPorEstado = new HashMap<>();

            for (var r : recetas) {
                String estado = r.getEstado().name(); // ✅ Convertir enum a String
                conteoPorEstado.put(estado, conteoPorEstado.getOrDefault(estado, 0) + 1);
            }

            conteoPorEstado.forEach(dataset::setValue);

        } catch (Exception e) {
            dataset.setValue("Sin datos", 1);
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Distribución de Recetas por Estado",
                dataset,
                true,
                true,
                false
        );

        return new ChartPanel(chart);
    }
}
