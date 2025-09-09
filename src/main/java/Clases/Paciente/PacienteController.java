package Clases.Paciente;

import Clases.Paciente.View.PacienteView;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class PacienteController {
    private PacienteModel model;
    private PacienteView view;

    public PacienteController(PacienteModel model, PacienteView view) {
        this.model = model;
        this.view = view;
        inicializarTabla();
        this.view.getTablaPacientes().getSelectionModel().addListSelectionListener(e -> {
            int fila = view.getTablaPacientes().getSelectedRow();
            if (fila >= 0) {
                String id = view.getTablaPacientes().getValueAt(fila, 0).toString();
                Paciente encontrado = model.findById(id);
                if (encontrado != null) {
                    view.getId().setText(encontrado.getId());
                    view.getNombre().setText(encontrado.getNombre());
                    view.getNumeroTelefono().setText(encontrado.getTelefono());
                    view.getFechaNacimiento().setDate(encontrado.getFechaNacimiento());

                }
            }
        });
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
        String telefono = view.getNumeroTelefono().getText();
        LocalDate fechaNacimiento = view.getFechaNacimiento().getDate();
        if (id.isEmpty() || nombre.isEmpty()|| telefono.isEmpty() ) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
            return;
        }

        Paciente existente = model.findById(id);
        if (existente == null) {
            Paciente nuevo = new Paciente(id, nombre,telefono,fechaNacimiento); // 🔹 ajusta constructor según tu clase Paciente
            model.addPaciente(nuevo);
            JOptionPane.showMessageDialog(null, "Paciente agregado");
        } else {
            existente.setNombre(nombre);
            existente.setTelefono(telefono);
            existente.setFechaNacimiento(fechaNacimiento);

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

    private void refrescarTabla() {
        DefaultTableModel tableModel = (DefaultTableModel) view.getTablaPacientes().getModel();
        tableModel.setRowCount(0); // limpiar

        for (Paciente p : model.getPacientes()) {
            Object[] fila = {
                    p.getId(),               // ID
                    p.getNombre(),           // Nombre
                    p.getTelefono(),         // Teléfono
                    p.getFechaNacimiento()   // Fecha de Nacimiento
            };

            tableModel.addRow(fila);
        }
    }
    private void inicializarTabla() {
        String[] columnas = {"ID", "Nombre", "Teléfono", "Fecha de Nacimiento"};
        DefaultTableModel tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        view.getTablaPacientes().setModel(tableModel);

    }

    private void limpiarCampos() {
        view.getId().setText("");
        view.getNombre().setText("");
        view.getFechaNacimiento().clear();
        view.getNumeroTelefono().setText("");
        view.getNombreBuscar().setText("");
        refrescarTabla();
    }
}
