package Clases.Medicamento;

import Clases.Medicamento.View.MedicamentoView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MedicamentoController {
    private MedicamentoModel model;
    private MedicamentoView view;

    public MedicamentoController(MedicamentoModel model, MedicamentoView view) {
        this.model = model;
        this.view = view;

        this.view.getGuardarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarMedicamento();
            }
        });

        this.view.getBorrarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarMedicamento();
            }
        });

        this.view.getBuscarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarMedicamento();
            }
        });

        this.view.getLimpiarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        this.view.getReporteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporte();
            }
        });
    }

    private void guardarMedicamento() {
        try {
            int codigo = Integer.parseInt(view.getCodigo().getText());
            String nombre = view.getNombre().getText();
            String descripcion = view.getDescripcion().getText();

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe llenar todos los campos obligatorios");
                return;
            }

            Medicamento existente = model.findByCodigo(codigo);
            if (existente == null) {
                Medicamento nuevo = new Medicamento(nombre, descripcion, codigo);
                model.addMedicamento(nuevo);
                JOptionPane.showMessageDialog(null, "Medicamento agregado");
            } else {
                existente.setNombre(nombre);
                existente.setDescripcion(descripcion);
                JOptionPane.showMessageDialog(null, "Medicamento actualizado");
            }
            limpiarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "El código debe ser un número");
        }
    }

    private void borrarMedicamento() {
        try {
            int codigo = Integer.parseInt(view.getCodigo().getText());
            model.deleteMedicamento(codigo);
            JOptionPane.showMessageDialog(null, "Medicamento eliminado");
            limpiarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un código válido");
        }
    }

    private void buscarMedicamento() {
        String criterio = view.getNombreBuscar().getText();

        Medicamento encontrado = null;
        try {
            int codigo = Integer.parseInt(criterio);
            encontrado = model.findByCodigo(codigo);
        } catch (NumberFormatException e) {
            encontrado = model.findByNombre(criterio);
        }

        if (encontrado != null) {
            view.getCodigo().setText(String.valueOf(encontrado.getCodigo()));
            view.getNombre().setText(encontrado.getNombre());
            view.getDescripcion().setText(encontrado.getDescripcion());
            JOptionPane.showMessageDialog(null, "Medicamento encontrado");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el medicamento");
        }
    }

    private void limpiarCampos() {
        view.getCodigo().setText("");
        view.getNombre().setText("");
        view.getDescripcion().setText("");
        view.getNombreBuscar().setText("");
    }

    private void generarReporte() {
        StringBuilder reporte = new StringBuilder("📋 Lista de Medicamentos:\n\n");
        for (Medicamento m : model.getMedicamentos()) {
            reporte.append("Código: ").append(m.getCodigo()).append("\n");
            reporte.append("Nombre: ").append(m.getNombre()).append("\n");
            reporte.append("Descripción: ").append(m.getDescripcion()).append("\n");
            reporte.append("-------------------------\n");
        }

        JOptionPane.showMessageDialog(null, reporte.toString());
    }
}
