package Clases.Medico.presentation;

import Clases.Medico.logic.Medico;
import Clases.Medico.logic.MedicoService;

import javax.swing.*;

public class MedicoController {
    private MedicoModel model;
    private MedicoView view;

    public MedicoController(MedicoModel model, MedicoView view) {
        this.model = model;
        this.view = view;

        // 🔹 Conexión MVC explícita
        view.setController(this);
        view.setModel(model);

        // 🔹 Inicializar datos
        model.setList(MedicoService.instance().findAll());
        model.setCurrent(new Medico());
    }

    public void guardar() {
        String id = view.getId().getText();
        String nombre = view.getNombre().getText();
        String especialidad = view.getEspecialidad().getText();

        if (id.isEmpty() || nombre.isEmpty() || especialidad.isEmpty()) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Debe llenar todos los campos");
            return;
        }

        try {
            Medico existente = MedicoService.instance().readById(id);
            existente.setNombre(nombre);
            existente.setEspecialidad(especialidad);
            JOptionPane.showMessageDialog(view.getMainPanel(), "Médico actualizado");
        } catch (Exception e) {
            Medico nuevo = new Medico(id, nombre, id, especialidad);
            try {
                MedicoService.instance().create(nuevo);
                JOptionPane.showMessageDialog(view.getMainPanel(), "Médico agregado");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view.getMainPanel(), ex.getMessage());
            }
        }

        model.setList(MedicoService.instance().findAll());
        model.setCurrent(new Medico());
    }

    public void borrar() {
        String id = view.getId().getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Debe ingresar un id");
            return;
        }

        MedicoService.instance().delete(id);
        JOptionPane.showMessageDialog(view.getMainPanel(), "Médico eliminado");

        model.setList(MedicoService.instance().findAll());
        model.setCurrent(new Medico());
    }

    public void buscar() {
        String criterio = view.getNombreBuscar().getText();
        Medico m = MedicoService.instance().readByNombre(criterio);
        if (m == null) m = MedicoService.instance().readById(criterio);

        if (m != null) {
            model.setCurrent(m);
            JOptionPane.showMessageDialog(view.getMainPanel(), "Médico encontrado");
        } else {
            JOptionPane.showMessageDialog(view.getMainPanel(), "No se encontró el médico");
        }
    }

    public void limpiar() {
        model.setCurrent(new Medico());
        view.getNombreBuscar().setText("");
    }

    public void reporte() {
        StringBuilder reporte = new StringBuilder("📋 Lista de Médicos:\n\n");
        for (Medico m : model.getList()) {
            reporte.append("ID: ").append(m.getId()).append("\n");
            reporte.append("Nombre: ").append(m.getNombre()).append("\n");
            reporte.append("Especialidad: ").append(m.getEspecialidad()).append("\n");
            reporte.append("-------------------------\n");
        }

        JOptionPane.showMessageDialog(view.getMainPanel(), reporte.toString());
    }
}
