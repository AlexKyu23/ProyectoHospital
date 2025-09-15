package Clases.Despacho.presentation;

import Clases.Receta.logic.ItemReceta;
import Clases.Receta.logic.Receta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DespachoView extends JFrame {
    private JTable tablaIniciar;
    private JTable tablaAlistar;
    private JTable tablaRecetaLista;
    private JTable tablaEntregada;
    private JButton iniciarBtn;
    private JButton alistarBtn;
    private JButton entregarBtn;
    private JTextField idPacienteBuscar;
    private JButton buscarButton;
    private JPanel despacho;

    public DespachoView() {
        setTitle("Despacho de Recetas");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        idPacienteBuscar = new JTextField(20);
        buscarButton = new JButton("Buscar por Paciente");
        iniciarBtn = new JButton("Iniciar despacho");
        alistarBtn = new JButton("Alistar medicamentos");
        entregarBtn = new JButton("Entregar receta");

        JPanel busquedaPanel = new JPanel();
        busquedaPanel.add(new JLabel("ID Paciente:"));
        busquedaPanel.add(idPacienteBuscar);
        busquedaPanel.add(buscarButton);

        JPanel botones = new JPanel();
        botones.add(iniciarBtn);
        botones.add(alistarBtn);
        botones.add(entregarBtn);

        tablaIniciar = new JTable();
        tablaAlistar = new JTable();
        tablaRecetaLista = new JTable();
        tablaEntregada = new JTable();

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Nuevas", new JScrollPane(tablaIniciar));
        tabs.addTab("En Proceso", new JScrollPane(tablaAlistar));
        tabs.addTab("Listas", new JScrollPane(tablaRecetaLista));
        tabs.addTab("Entregadas", new JScrollPane(tablaEntregada));

        add(busquedaPanel, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        despacho = new JPanel(new BorderLayout());
        despacho.add(busquedaPanel, BorderLayout.NORTH);
        despacho.add(tabs, BorderLayout.CENTER);
        despacho.add(botones, BorderLayout.SOUTH);
    }

    public JPanel getDespacho() { return despacho; }
    public JTable getTablaIniciar() { return tablaIniciar; }
    public JTable getTablaAlistar() { return tablaAlistar; }
    public JTable getTablaRecetaLista() { return tablaRecetaLista; }
    public JTable getTablaEntregada() { return tablaEntregada; }
    public JButton getIniciarBtn() { return iniciarBtn; }
    public JButton getAlistarBtn() { return alistarBtn; }
    public JButton getEntregarBtn() { return entregarBtn; }
    public JTextField getIdPacienteBuscar() { return idPacienteBuscar; }
    public JButton getBuscarButton() { return buscarButton; }

    public void mostrarDetallesReceta(Receta receta, String accion) {
        JDialog dialog = new JDialog(this, "Detalles de la Receta", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridLayout(3, 2));
        infoPanel.add(new JLabel("ID Receta:"));
        infoPanel.add(new JLabel(receta.getId()));
        infoPanel.add(new JLabel("Paciente ID:"));
        infoPanel.add(new JLabel(receta.getPacienteId()));
        infoPanel.add(new JLabel("Fecha Retiro:"));
        infoPanel.add(new JLabel(receta.getFechaRetiro().toString()));

        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"Medicamento", "Cantidad", "Indicaciones", "Duración"}, 0);
        JTable tablaMedicamentos = new JTable(tableModel);
        for (ItemReceta item : receta.getMedicamentos()) {
            tableModel.addRow(new Object[]{
                    item.getDescripcion(),
                    item.getCantidad(),
                    item.getIndicaciones(),
                    item.getDuracionDias()
            });
        }

        dialog.add(infoPanel, BorderLayout.NORTH);
        dialog.add(new JScrollPane(tablaMedicamentos), BorderLayout.CENTER);
        JButton cerrarBtn = new JButton("Cerrar");
        cerrarBtn.addActionListener(e -> dialog.dispose());
        dialog.add(cerrarBtn, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}