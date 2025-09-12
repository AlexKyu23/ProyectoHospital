package Clases.Medicamento.presentation;

import Clases.Medicamento.logic.Medicamento;
import Clases.Medicamento.logic.MedicamentoService;
import Clases.Medicamento.presentation.MedicamentoView;

import javax.swing.*;

public class MedicamentoController {
    private MedicamentoModel model;
    private MedicamentoView view;

    public MedicamentoController(MedicamentoModel model, MedicamentoView view) {
        this.model = model;
        this.view = view;

        // 🔹 Conexión MVC explícita
        view.setController(this);
        view.setModel(model);

        // 🔹 Inicializar datos
        model.setList(MedicamentoService.instance().findAll());
        model.setCurrent(new Medicamento());
    }

    public void guardar() {
        try {
            int codigo = Integer.parseInt(view.getCodigo().getText());
            String nombre = view.getNombre().getText();
            String descripcion = view.getDescripcion().getText();

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(view.getMainPanel(), "Debe llenar todos los campos");
                return;
            }

            Medicamento existente = MedicamentoService.instance().readByCodigo(codigo);
            if (existente != null) {
                existente.setNombre(nombre);
                existente.setDescripcion(descripcion);
                JOptionPane.showMessageDialog(view.getMainPanel(), "Medicamento actualizado");
            } else {
                Medicamento nuevo = new Medicamento(nombre, descripcion, codigo);
                MedicamentoService.instance().create(nuevo);
                JOptionPane.showMessageDialog(view.getMainPanel(), "Medicamento agregado");
            }

            model.setList(MedicamentoService.instance().findAll());
            model.setCurrent(new Medicamento());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "El código debe ser un número");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view.getMainPanel(), ex.getMessage());
        }
    }

    public void borrar() {
        try {
            int codigo = Integer.parseInt(view.getCodigo().getText());
            MedicamentoService.instance().delete(codigo);
            JOptionPane.showMessageDialog(view.getMainPanel(), "Medicamento eliminado");

            model.setList(MedicamentoService.instance().findAll());
            model.setCurrent(new Medicamento());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Debe ingresar un código válido");
        }
    }

    public void buscar() {
        String criterio = view.getNombreBuscar().getText();
        Medicamento m = null;

        try {
            int codigo = Integer.parseInt(criterio);
            m = MedicamentoService.instance().readByCodigo(codigo);
        } catch (NumberFormatException e) {
            m = MedicamentoService.instance().readByNombre(criterio);
        }

        if (m != null) {
            model.setCurrent(m);
            JOptionPane.showMessageDialog(view.getMainPanel(), "Medicamento encontrado");
        } else {
            JOptionPane.showMessageDialog(view.getMainPanel(), "No se encontró el medicamento");
        }
    }

    public void limpiar() {
        model.setCurrent(new Medicamento());
        view.getNombreBuscar().setText("");
    }

    public void reporte() {
        StringBuilder reporte = new StringBuilder("📋 Lista de Medicamentos:\n\n");
        for (Medicamento m : model.getList()) {
            reporte.append("Código: ").append(m.getCodigo()).append("\n");
            reporte.append("Nombre: ").append(m.getNombre()).append("\n");
            reporte.append("Descripción: ").append(m.getDescripcion()).append("\n");
            reporte.append("-------------------------\n");
        }

        JOptionPane.showMessageDialog(view.getMainPanel(), reporte.toString());
    }
}