package hospital.presentation.Admin.presentation;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AdminView extends JPanel implements PropertyChangeListener {
    private AdminController controller;
    private AdminModel model;
    private JTabbedPane tabbedPane;

    public AdminView() {
        System.out.println("🎨 Creando AdminView...");

        tabbedPane = new JTabbedPane();

        // Configura este JPanel directamente
        this.setLayout(new BorderLayout());
        this.add(tabbedPane, BorderLayout.CENTER);

        System.out.println("✅ AdminView creado con JTabbedPane");
    }

    public void setController(AdminController controller) {
        this.controller = controller;
        System.out.println("🔗 Controller conectado a AdminView");
    }

    public void setModel(AdminModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
        System.out.println("🔗 Model conectado a AdminView");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (AdminModel.CURRENT.equals(evt.getPropertyName())) {
            System.out.println("🔄 AdminView actualizado");
        }
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public JPanel getMainPanel() {
        return this;
    }
}