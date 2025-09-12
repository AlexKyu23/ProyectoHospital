package Clases.Paciente.presentation;

import Clases.Paciente.logic.Paciente;
import Clases.Paciente.logic.PacienteService;
import Clases.Paciente.presentation.View.PacienteView;

import javax.swing.*;
import java.time.LocalDate;

public class PacienteController {
    private PacienteModel model;
    private PacienteView view;

    public PacienteController(PacienteModel model, PacienteView view) {
        this.model = model;
        this.view = view;

        // 🔹 Conexión MVC explícita
        view.setController(this);
        view.setModel(model);

        // 🔹 Inicializar datos
        model.setList(PacienteService.instance().findAll());
        model.setCurrent(new Paciente());
    }

    public void guardar() {
        String id = view.getId().getText();
        String nombre = view.getNombre().getText();
        String telefono = view.getNumeroTelefono().getText();
        LocalDate fechaNacimiento = view.getFechaNacimiento().getDate();

        if (id.isEmpty() || nombre.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Debe llenar todos los campos");
            return;
        }

        try {
            Paciente existente = PacienteService.instance().readById(id);
            existente.setNombre(nombre);
            existente.setTelefono(telefono);
            existente.setFechaNacimiento(fechaNacimiento);
            JOptionPane.showMessageDialog(view.getMainPanel(), "Paciente actualizado");
        } catch (Exception e) {
            Paciente nuevo = new Paciente(id, nombre, telefono, fechaNacimiento);
            try {
                PacienteService.instance().create(nuevo);
                JOptionPane.showMessageDialog(view.getMainPanel(), "Paciente agregado");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view.getMainPanel(), ex.getMessage());
            }
        }

        model.setList(PacienteService.instance().findAll());
        model.setCurrent(new Paciente());
    }

    public void borrar() {
        String id = view.getId().getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Debe ingresar un id");
            return;
        }

        PacienteService.instance().delete(id);
        JOptionPane.showMessageDialog(view.getMainPanel(), "Paciente eliminado");

        model.setList(PacienteService.instance().findAll());
        model.setCurrent(new Paciente());
    }

    public void buscar() {
        String criterio = view.getNombreBuscar().getText();
        Paciente p = PacienteService.instance().readByNombre(criterio);
        if (p == null) p = PacienteService.instance().readById(criterio);

        if (p != null) {
            model.setCurrent(p);
            JOptionPane.showMessageDialog(view.getMainPanel(), "Paciente encontrado");
        } else {
            JOptionPane.showMessageDialog(view.getMainPanel(), "No se encontró el paciente");
        }
    }

    public void limpiar() {
        model.setCurrent(new Paciente());
        view.getNombreBuscar().setText("");
    }

    public void reporte() {
        StringBuilder reporte = new StringBuilder("📋 Lista de Pacientes:\n\n");
        for (Paciente p : model.getList()) {
            reporte.append("ID: ").append(p.getId()).append("\n");
            reporte.append("Nombre: ").append(p.getNombre()).append("\n");
            reporte.append("Teléfono: ").append(p.getTelefono()).append("\n");
            reporte.append("Fecha de Nacimiento: ").append(p.getFechaNacimiento()).append("\n");
            reporte.append("-------------------------\n");
        }

        JOptionPane.showMessageDialog(view.getMainPanel(), reporte.toString());
    }
}

