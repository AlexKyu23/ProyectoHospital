package Clases.Dashboard.logic;

import Clases.Medicamento.data.catalogoMedicamentos;
import Clases.Medicamento.logic.Medicamento;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

public class LineChart_AWT {

    public static JPanel getChartPanel(catalogoMedicamentos catalogo) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<String, Integer> conteoPorAño = new TreeMap<>();
       for (Medicamento m : catalogo.consulta()) {
           // String año = m.getFechaReceta().substring(0, 4); // Ajusta según formato
           // conteoPorAño.put(año, conteoPorAño.getOrDefault(año, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : conteoPorAño.entrySet()) {
            dataset.addValue(entry.getValue(), "Medicamentos", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Medicamentos Recetados por Año",
                "Año", "Cantidad",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        return new ChartPanel(chart);
    }

    /*public static Component getChartPanel() {                                 //Datos quemados para probar
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Medicamentos recetados por meses",
                "Mes", "Cantidad",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false
        );
        return new ChartPanel(lineChart);
    }

    private static DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(15, "schools", "1970");
        dataset.addValue(30, "schools", "1980");
        dataset.addValue(60, "schools", "1990");
        dataset.addValue(120, "schools", "2000");
        dataset.addValue(240, "schools", "2010");
        dataset.addValue(300, "schools", "2014");
        return dataset;
    }*/
}