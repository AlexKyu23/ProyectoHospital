package Clases.Dashboard;

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
    private JPanel graficoMedicamentos;             //Importante! El gráfico "linechart" va aquí
    private JPanel graficoRecetas;                  //Importante! El gráfico "pie" va aquí

    //---------------------------LineChart (Medicamentos)-----------------------------------

    LineChart_AWT chartMedicamento = new LineChart_AWT(
            "Medicamentos" ,
            "Medicamentos recetados por meses");

    /*chartMedicamento.pack( );                     //No sé si se ocupa
    chartMedicamento.setVisible( true );            //""
    graficoMedicamentos.removeAll();
    graficoMedicamentos.add(chartMedicamento);
    */

    //--------------------------------PieChart (Recetas)----------------------------------------

    PieChart_AWT chartRecetas = new PieChart_AWT( "Recetas" );

    /*chartRecetas.setSize( 560 , 367 );            //No sé si se ocupa
    chartRecetas.setVisible( true );                //""
    graficoRecetas.removeAll();
    graficoRecetas.add(chartRecetas)*/
}
