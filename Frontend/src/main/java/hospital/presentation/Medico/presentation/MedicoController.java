package hospital.presentation.Medico.presentation;

import hospital.logic.Service;
import logic.Medico;
import logic.Usuario;

import javax.swing.*;

public class MedicoController {
    private final MedicoModel model;
    private final MedicoView view;

    public MedicoController(MedicoModel model, MedicoView view) throws Exception {
        this.model = model;
        this.view = view;

        view.setController(this);
        view.setModel(model);

        model.init();
        model.setList(Service.instance().findAllMedicos());
    }

    public void guardar() {
        String id = view.getId().getText().trim();
        String nombre = view.getNombre().getText().trim();
        String especialidad = view.getEspecialidad().getText().trim();

        if (id.isEmpty() || nombre.isEmpty() || especialidad.isEmpty()) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Debe llenar todos los campos");
            return;
        }

        try {
            Medico existente = Service.instance().readMedico(id);
            existente.setNombre(nombre);
            existente.setEspecialidad(especialidad);
            Service.instance().updateMedico(existente);
            JOptionPane.showMessageDialog(view.getMainPanel(), "Médico actualizado");
        } catch (Exception e) {
            try {
                Medico nuevo = new Medico(id, nombre, id, especialidad);
                Service.instance().createMedico(nuevo);

                Usuario u = new Usuario(id, nombre, id, "MED");
                Service.instance().createUsuario(u);

                JOptionPane.showMessageDialog(view.getMainPanel(), "Médico agregado y acceso creado");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view.getMainPanel(), ex.getMessage());
            }
        }

        actualizarLista();
    }

    public void borrar() {
        String id = view.getId().getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Debe ingresar un ID");
            return;
        }

        try {
            Service.instance().deleteMedico(id);
            Service.instance().deleteUsuario(id);
            JOptionPane.showMessageDialog(view.getMainPanel(), "Médico eliminado y acceso revocado");
            actualizarLista();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getMainPanel(), e.getMessage());
        }
    }

    public void buscar() {
        String criterio = view.getNombreBuscar().getText().trim();
        if (criterio.isEmpty()) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Ingrese nombre o ID para buscar");
            return;
        }

        try {
            Medico m = Service.instance().readMedico(criterio);
            if (m != null) {
                model.setCurrent(m);
                JOptionPane.showMessageDialog(view.getMainPanel(), "Médico encontrado");
            } else {
                JOptionPane.showMessageDialog(view.getMainPanel(), "No se encontró el médico");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getMainPanel(), e.getMessage());
        }
    }

    public void limpiar() {
        model.setCurrent(new Medico());
        view.getNombreBuscar().setText("");
    }

    public void reporte() {
        StringBuilder sb = new StringBuilder("📋 Lista de Médicos:\n\n");
        for (Medico m : model.getList()) {
            sb.append("ID: ").append(m.getId()).append("\n");
            sb.append("Nombre: ").append(m.getNombre()).append("\n");
            sb.append("Especialidad: ").append(m.getEspecialidad()).append("\n");
            sb.append("-------------------------\n");
        }
        JOptionPane.showMessageDialog(view.getMainPanel(), sb.toString());
    }

    private void actualizarLista() {
        try {
            model.setList(Service.instance().findAllMedicos());
            model.setCurrent(new Medico());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Error al actualizar lista: " + e.getMessage());
        }
    }
}
