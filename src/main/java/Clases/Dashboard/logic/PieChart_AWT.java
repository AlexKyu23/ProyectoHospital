package Clases.Dashboard.logic;

import Clases.Receta.Data.historicoRecetas;
import Clases.Receta.logic.Receta;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.chart.ui.ApplicationFrame;

import java.util.HashMap;
import java.util.Map;

public class PieChart_AWT {

    public static JPanel getChartPanel(historicoRecetas recetas) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        Map<String, Integer> conteoPorTipo = new HashMap<>();
        for (Receta r : recetas.consulta()) {
            String tipo = String.valueOf(r.getEstado()); // Ajusta según tu clase Receta
            conteoPorTipo.put(tipo, conteoPorTipo.getOrDefault(tipo, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : conteoPorTipo.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
            "Distribución de Recetas",
            dataset,
            true, true, false
        );

        return new ChartPanel(chart);
    }

    /*public static JPanel getChartPanel() {                                    //Datos quemados para probar
        JFreeChart chart = ChartFactory.createPieChart(
                "Recetas",
                createDataset(),
                true,
                true,
                false
        );
        return new ChartPanel(chart);
    }

    private static PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("IPhone 5s", 20);
        dataset.setValue("SamSung Grand", 20);
        dataset.setValue("MotoG", 40);
        dataset.setValue("Nokia Lumia", 10);
        return dataset;
    }*/
}
