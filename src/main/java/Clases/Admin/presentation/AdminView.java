package Clases.Admin.presentation;
import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AdminView extends JFrame implements PropertyChangeListener {
    private AdminController controller;
    private AdminModel model;

    private JTabbedPane tabbedPane;
    private JPanel mainPanel;

    public AdminView() {
        setTitle("Panel de Administrador");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        mainPanel = new JPanel();
        mainPanel.setLayout(new java.awt.BorderLayout());
        mainPanel.add(tabbedPane, java.awt.BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    // 🔹 Integración MVC
    public void setController(AdminController controller) {
        this.controller = controller;
    }

    public void setModel(AdminModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    // 🔹 Actualización visual
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (AdminModel.CURRENT.equals(evt.getPropertyName())) {
            // Aquí podrías actualizar algún encabezado o estado del panel si lo deseas
        }
    }

    // 🔹 Getter para el controlador
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
}
