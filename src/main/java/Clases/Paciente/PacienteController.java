package Clases.Paciente;

import Clases.Paciente.View.PacienteView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PacienteController {
    private PacienteModel model;
    private PacienteView view;

    public PacienteController(PacienteModel model, PacienteView view) {
        this.model = model;
        this.view = view;

        this.view.getGuardarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPaciente();
            }
        });

        this.view.getBorrarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarPaciente();
            }
        });

        this.view.getBuscarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPaciente();
            }
        });

        this.view.getLimpiarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }

    private void guardarPaciente() {
        String id = view.getId().getText();
        String nombre = view.getNombre().getText();

        if (id.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
            return;
        }

        Paciente existente = model.findById(id);
        if (existente == null) {
            Paciente nuevo = new Paciente(id, nombre); // 🔹 ajusta constructor según tu clase Paciente
            model.addPaciente(nuevo);
            JOptionPane.showMessageDialog(null, "Paciente agregado");
        } else {
            existente.setNombre(nombre);
            JOptionPane.showMessageDialog(null, "Paciente actualizado");
        }
        limpiarCampos();
    }

    private void borrarPaciente() {
        String id = view.getId().getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un id");
            return;
        }
        model.deletePaciente(id);
        JOptionPane.showMessageDialog(null, "Paciente eliminado");
        limpiarCampos();
    }

    private void buscarPaciente() {
        String criterio = view.getNombreBuscar().getText();
        Paciente encontrado = model.findById(criterio);
        if (encontrado == null) {
            encontrado = model.findByNombre(criterio);
        }

        if (encontrado != null) {
            view.getId().setText(encontrado.getId());
            view.getNombre().setText(encontrado.getNombre());
            JOptionPane.showMessageDialog(null, "Paciente encontrado");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el paciente");
        }
    }

    private void limpiarCampos() {
        view.getId().setText("");
        view.getNombre().setText("");
        view.getNombreBuscar().setText("");
    }
}
