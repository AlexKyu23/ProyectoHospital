package Clases.Despacho.presentation;

import javax.swing.*;
import java.awt.*;

public class DespachoView extends JFrame {
    private JTable tabla;
    private JButton iniciarBtn;
    private JButton alistarBtn;
    private JButton entregarBtn;
    private JPanel despachoEnProceso;
    private JPanel despachoLista;
    private JPanel despachoEntregada;
    private JTextField idPaciente1;
    private JTextField idPaciente3;
    private JTable tablaIniciar;
    private JTextField idPaciente2;
    private JTable tablaAlistar;
    private JTable tablaRecetaLista;
    private JPanel despacho;
    private JTable alistarTabla;
    private JTable procesarTabla;
    private JTable entregarTabla;

    public DespachoView() {
        setTitle("Despacho de Recetas");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        tabla = new JTable();
        iniciarBtn = new JButton("Iniciar despacho");
        alistarBtn = new JButton("Alistar medicamentos");
        entregarBtn = new JButton("Entregar receta");

        JPanel botones = new JPanel();
        botones.add(iniciarBtn);
        botones.add(alistarBtn);
        botones.add(entregarBtn);

        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        tablaIniciar = new JTable();
        tablaAlistar = new JTable();
        tablaRecetaLista = new JTable();

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Nuevas", new JScrollPane(tablaIniciar));
        tabs.add("En Proceso", new JScrollPane(tablaAlistar));
        tabs.add("Listas", new JScrollPane(tablaRecetaLista));

        add(tabs, BorderLayout.CENTER);

    }

    public JTable getTabla() { return tabla; }
    public JButton getIniciarBtn() { return iniciarBtn; }
    public JButton getAlistarBtn() { return alistarBtn; }
    public JButton getEntregarBtn() { return entregarBtn; }
    public JPanel getDespacho() { return despacho; }
    public JTable getTablaIniciar() { return tablaIniciar; }
    public JTable getTablaAlistar() { return tablaAlistar; }
    public JTable getTablaRecetaLista() { return tablaRecetaLista; }




}
