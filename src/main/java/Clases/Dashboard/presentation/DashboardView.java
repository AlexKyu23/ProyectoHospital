package Clases.Dashboard.presentation;

import Clases.Dashboard.logic.LineChart_AWT;
import Clases.Dashboard.logic.PieChart_AWT;
import Clases.Medicamento.data.catalogoMedicamentos;
import Clases.Receta.Data.historicoRecetas;

import javax.swing.*;

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
    private JPanel graficoMedicamentos;         //Importante! El gráfico "linechart" va aquí private JPanel graficoRecetas;
    private JPanel graficoRecetas;              // Importante! El gráfico "pie" va aquí
    private JPanel dashboard;

    public DashboardView(historicoRecetas historicRecetas, catalogoMedicamentos catalogMedicamentos) {
        // Limpia y agrega el gráfico de medicamentos
        graficoMedicamentos.removeAll();
        graficoMedicamentos.add(LineChart_AWT.getChartPanel(catalogMedicamentos));
        graficoMedicamentos.revalidate();
        graficoMedicamentos.repaint();

        // Limpia y agrega el gráfico de recetas
        graficoRecetas.removeAll();
        graficoRecetas.add(PieChart_AWT.getChartPanel(historicRecetas));
        graficoRecetas.revalidate();
        graficoRecetas.repaint();
    }

    public JPanel getDashboard() {
        return dashboard;
    }

}