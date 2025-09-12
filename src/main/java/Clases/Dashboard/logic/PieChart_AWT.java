package Clases.Dashboard.logic;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.chart.ui.ApplicationFrame;

public class PieChart_AWT {
    public static JPanel getChartPanel() {
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
    }
}
