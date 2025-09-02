package Clases.Farmaceuta;
import Clases.Farmaceuta.View.FarmaceutaView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FarmaceutaController {
    private FarmaceutaModel model;
    private FarmaceutaView view;

    public FarmaceutaController(FarmaceutaModel model, FarmaceutaView view) {
        this.model = model;
        this.view = view;

        // Botón Guardar → agrega o actualiza
        this.view.getGuardarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarFarmaceuta();
            }
        });

        // Botón Borrar
        this.view.getBorrarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarFarmaceuta();
            }
        });


        // Botón Buscar
        this.view.getBuscarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarFarmaceuta();
            }
        });

        // Botón Limpiar
        this.view.getLimpiarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }

    private void guardarFarmaceuta() {
        String id = view.getId().getText();
        String nombre = view.getNombre().getText();

        if (id.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
            return;
        }

        Farmaceuta existente = model.findById(id);
        if (existente == null) {
            // nuevo → clave = id por defecto
            Farmaceuta f = new Farmaceuta(id, nombre, id);
            model.addFarmaceuta(f);
            JOptionPane.showMessageDialog(null, "Farmaceuta agregado");
        } else {
            // actualizar
            existente.setNombre(nombre);
            model.updateFarmaceuta(existente);
            JOptionPane.showMessageDialog(null, "Farmaceuta actualizado");
        }
    }

    private void borrarFarmaceuta() {
        String id = view.getId().getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un id");
            return;
        }
        model.deleteFarmaceuta(id);
        JOptionPane.showMessageDialog(null, "Farmaceuta eliminado");
    }

    private void buscarFarmaceuta() {
        String criterio = view.getNombreBuscar().getText();
        Farmaceuta f = model.findByNombre(criterio);
        if (f == null) {
            f = model.findById(criterio);
        }
        if (f != null) {
            view.getId().setText(f.getId());
            view.getNombre().setText(f.getNombre());
            JOptionPane.showMessageDialog(null, "Farmaceuta encontrado");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el farmaceuta");
        }
    }

    private void limpiarCampos() {
        view.getId().setText("");
        view.getNombre().setText("");
        view.getNombreBuscar().setText("");
    }
    private JPanel panel1;
    private JPanel Farmaceuta;
    private JTextField ID;
    private JTextField Nombre;
    private JButton guardarButton;
    private JButton LImpiarButton;
    private JButton borrarButton;
    private JTextField NombreB;
    private JButton buscarButton;
    private JButton reporteButton;
    private JPanel Listado;
    private JTable table1;
}
