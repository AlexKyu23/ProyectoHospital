package logic;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PieChart_AWT implements Serializable {

    public static JPanel getChartPanel() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        // Obtener todas las recetas desde el Service
        Service service = Service.instance();
        try {
            Map<String, Integer> conteoPorTipo = new HashMap<>();
            for (Receta r : service.findAllReceta()) {
                String tipo = String.valueOf(r.getEstado());
                conteoPorTipo.put(tipo, conteoPorTipo.getOrDefault(tipo, 0) + 1);
            }

            for (Map.Entry<String, Integer> entry : conteoPorTipo.entrySet()) {
                dataset.setValue(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            System.err.println("Error al obtener recetas para el gráfico: " + e.getMessage());
            // Opcional: puedes establecer valores por defecto o lanzar una excepción
            dataset.setValue("Error", 1); // Valor placeholder en caso de error
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