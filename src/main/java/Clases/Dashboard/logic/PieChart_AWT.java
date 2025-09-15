package Clases.Dashboard.logic;

import Clases.Receta.Data.RepositorioRecetas;
import Clases.Receta.logic.Receta;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.util.HashMap;
import java.util.Map;

public class PieChart_AWT {

    public static JPanel getChartPanel(RepositorioRecetas recetas) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        Map<String, Integer> conteoPorTipo = new HashMap<>();
        for (Receta r : recetas.getRecetas()) {
            String tipo = String.valueOf(r.getEstado());
            conteoPorTipo.put(tipo, conteoPorTipo.getOrDefault(tipo, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : conteoPorTipo.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Distribución de Recetas",
                dataset,
                true, // Mostrar leyenda
                true, // Mostrar tooltips
                false // No mostrar URLs
        );

        return new ChartPanel(chart);
    }
}