package Clases.Dashboard.View;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;

import javax.swing.*;
import java.io.File;

public class DashboardView {
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
    private JPanel Lista;
    private JPanel graficoMedicamentos;


    /* Step - 1: Define the data for the line chart  */
    DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();


    /* Step -2:Define the JFreeChart object to create line chart */
    /*JFreeChart lineChartObject= ChartFactory.createLineChart(
            "Medicamentos","Mes","Cantidad",
            line_chart_dataset, PlotOrientation.VERTICAL,true,true,false);

    // Step -3 : Write line chart to a file
    int width=640; /* Width of the image */
    //int height=480; /* Height of the image */
    //File lineChart=new File("line_Chart_example.png");

    //----------------------------------------------------------Versión para XML------------------------------------------------------------------

    /*JFreeChart chart = ChartFactory.createLineChart("Medicamentos", "Mes","Cantidad", line_chart_dataset, PlotOrientation.VERTICAL,true,true,false);
    CategoryPlot plot = (Categoryplot) chart.getPlot();
    LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
    renderer.setBaseShapesVisible(true);
    ChartPanel chartPanel = new ChartPanel(chart, 400, 320, )
    graficoMedicamentos.removeAll();
    graficoMedicamentos.add(chartPanel);
    */
}
