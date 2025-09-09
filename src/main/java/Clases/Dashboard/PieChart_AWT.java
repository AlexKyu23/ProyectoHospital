package Clases.Dashboard;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.chart.ui.ApplicationFrame;

public class PieChart_AWT extends ApplicationFrame {
    public PieChart_AWT( String title ) {
        super( title );
        setContentPane(createDemoPanel( ));
    }

    private static PieDataset createDataset( ) {                    //Editar para añadir los datos de recetas guardadas en el XML
        DefaultPieDataset dataset = new DefaultPieDataset( );
        dataset.setValue( "IPhone 5s" , 20 );
        dataset.setValue( "SamSung Grand" , 20 );
        dataset.setValue( "MotoG" , 40 );
        dataset.setValue( "Nokia Lumia" , 10);
        return dataset;
    }

    private static JFreeChart createChart( PieDataset dataset ) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Mobile Sales",   // chart title
                dataset,          // data
                true,             // include legend
                true,
                false);

        return chart;
    }

    public static JPanel createDemoPanel( ) {
        JFreeChart chart = createChart(createDataset( ) );
        return new ChartPanel( chart );
    }

    //Main para probar:

    /*public static void main( String[ ] args ) {
        PieChart_AWT demo = new PieChart_AWT( "Recetas" );
        demo.setSize( 560 , 367 );
        demo.setVisible( true );
    }*/
}
