package Clases.Admin;

import javax.swing.*;

public class AdminView extends JFrame {
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

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
}
