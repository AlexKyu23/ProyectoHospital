package logic;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

public class LineChart_AWT implements Serializable {

    public static JPanel getChartPanel(RepositorioRecetas repositorio, String selectedMed, LocalDate start, LocalDate end) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<String, Integer> conteoPorMes = new TreeMap<>();
        for (Receta r : repositorio.getRecetas()) {
            LocalDate fecha = r.getFechaConfeccion();
            if (fecha != null && !fecha.isBefore(start) && !fecha.isAfter(end)) {
                String mesAno = fecha.format(DateTimeFormatter.ofPattern("yyyy-MM"));
                for (ItemReceta item : r.getMedicamentos()) {
                    if (selectedMed == null || "Todos".equals(selectedMed) || item.getDescripcion().equals(selectedMed)) {
                        int current = conteoPorMes.getOrDefault(mesAno, 0);
                        conteoPorMes.put(mesAno, current + item.getCantidad());
                    }
                }
            }
        }

        for (Map.Entry<String, Integer> entry : conteoPorMes.entrySet()) {
            dataset.addValue(entry.getValue(), "Cantidad", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Cantidad de Medicamentos Prescritos por Mes",
                "Mes", "Cantidad",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        return new ChartPanel(chart);
    }
}