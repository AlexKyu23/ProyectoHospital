package Clases.Dashboard.presentation;

import Clases.Dashboard.logic.LineChart_AWT;
import Clases.Dashboard.logic.PieChart_AWT;
import Clases.Medicamento.data.catalogoMedicamentos;
import Clases.Medicamento.logic.Medicamento;
import Clases.Receta.Data.RepositorioRecetas;

import javax.swing.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private JPanel graficoMedicamentos; // Importante! El gráfico "linechart" va aquí
    private JPanel graficoRecetas;     // Importante! El gráfico "pie" va aquí
    private JPanel dashboard;

    private RepositorioRecetas repositorioRecetas;
    private catalogoMedicamentos catalogMedicamentos;

    public DashboardView(RepositorioRecetas repositorioRecetas, catalogoMedicamentos catalogMedicamentos) {
        this.repositorioRecetas = repositorioRecetas;
        this.catalogMedicamentos = catalogMedicamentos;

        // Poblar combo boxes
        populateCombos();

        // Inicializar gráficos con datos completos
        updateLineChart();
        updatePieChart();

        // Action listeners para botones
        checkButton.addActionListener(e -> updateLineChart());
        doubleCheckButton.addActionListener(e -> updatePieChart());
    }

    private void populateCombos() {
        // Años: desde 2020 a 2030
        List<String> years = new ArrayList<>();
        for (int year = 2020; year <= 2030; year++) {
            years.add(String.valueOf(year));
        }
        desde_año.setModel(new DefaultComboBoxModel<>(years.toArray(new String[0])));
        hasta_año.setModel(new DefaultComboBoxModel<>(years.toArray(new String[0])));
        desde_año.setSelectedItem("2025");
        hasta_año.setSelectedItem("2025");

        // Meses: 01 a 12
        List<String> months = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            months.add(String.format("%02d", month));
        }
        desde_diaMes.setModel(new DefaultComboBoxModel<>(months.toArray(new String[0])));
        hasta_diaMes.setModel(new DefaultComboBoxModel<>(months.toArray(new String[0])));
        desde_diaMes.setSelectedItem("01");
        hasta_diaMes.setSelectedItem("12");

        // Medicamentos: "Todos" + nombres únicos ordenados
        List<String> medNames = catalogMedicamentos.consulta().stream()
                .map(Medicamento::getNombre)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        medNames.add(0, "Todos");
        medicamentos.setModel(new DefaultComboBoxModel<>(medNames.toArray(new String[0])));
        medicamentos.setSelectedItem("Todos");
    }

    private void updateLineChart() {
        String fromYearStr = (String) desde_año.getSelectedItem();
        String fromMonthStr = (String) desde_diaMes.getSelectedItem();
        String toYearStr = (String) hasta_año.getSelectedItem();
        String toMonthStr = (String) hasta_diaMes.getSelectedItem();

        int fromYear = Integer.parseInt(fromYearStr);
        int fromMonth = Integer.parseInt(fromMonthStr);
        int toYear = Integer.parseInt(toYearStr);
        int toMonth = Integer.parseInt(toMonthStr);

        LocalDate start = LocalDate.of(fromYear, fromMonth, 1);
        LocalDate end = YearMonth.of(toYear, toMonth).atEndOfMonth();

        String selectedMed = (String) medicamentos.getSelectedItem();
        if ("Todos".equals(selectedMed)) {
            selectedMed = null;
        }

        // Limpia y agrega el gráfico de medicamentos
        graficoMedicamentos.removeAll();
        graficoMedicamentos.add(LineChart_AWT.getChartPanel(repositorioRecetas, selectedMed, start, end));
        graficoMedicamentos.revalidate();
        graficoMedicamentos.repaint();
    }

    private void updatePieChart() {
        // Limpia y agrega el gráfico de recetas
        graficoRecetas.removeAll();
        graficoRecetas.add(PieChart_AWT.getChartPanel(repositorioRecetas));
        graficoRecetas.revalidate();
        graficoRecetas.repaint();
    }

    public JPanel getDashboard() {
        return dashboard;
    }
}