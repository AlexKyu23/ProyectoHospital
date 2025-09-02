
package Clases.Medico.View;


import Clases.Medico.Medico;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MedicoController {
    private MedicoModel model;
    private MedicoView view;

    public MedicoController(MedicoModel model, MedicoView view) {
        this.model = model;
        this.view = view;

        // Asignar listeners a los botones
        this.view.getGuardarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarMedico();
            }
        });

        this.view.getBorrarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarMedico();
            }
        });

        this.view.getBuscarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarMedico();
            }
        });

        this.view.getLimpiarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }

    private void guardarMedico() {
        String id = view.getId().getText();
        String nombre = view.getNombre().getText();
        String especialidad = view.getEspecialidad().getText();

        if (id.isEmpty() || nombre.isEmpty() || especialidad.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
            return;
        }

        Medico existente = model.findById(id);
        if (existente == null) {
            // clave = id por defecto
            Medico nuevo = new Medico(id, nombre, id, especialidad);
            model.addMedico(nuevo);
            JOptionPane.showMessageDialog(null, "Médico agregado");
        } else {
            existente.setNombre(nombre);
            existente.setEspecialidad(especialidad);
            JOptionPane.showMessageDialog(null, "Médico actualizado");
        }
        limpiarCampos();
    }

    private void borrarMedico() {
        String id = view.getId().getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un id");
            return;
        }
        model.deleteMedico(id);
        JOptionPane.showMessageDialog(null, "Médico eliminado");
        limpiarCampos();
    }

    private void buscarMedico() {
        String criterio = view.getNombreBuscar().getText();
        Medico encontrado = model.findById(criterio);
        if (encontrado == null) {
            encontrado = model.findByNombre(criterio);
        }

        if (encontrado != null) {
            view.getId().setText(encontrado.getId());
            view.getNombre().setText(encontrado.getNombre());
            view.getEspecialidad().setText(encontrado.getEspecialidad());
            JOptionPane.showMessageDialog(null, "Médico encontrado");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el médico");
        }
    }

    private void limpiarCampos() {
        view.getId().setText("");
        view.getNombre().setText("");
        view.getEspecialidad().setText("");
        view.getNombreBuscar().setText("");
    }
}
