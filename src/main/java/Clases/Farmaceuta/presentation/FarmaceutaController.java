package Clases.Farmaceuta.presentation;

import Clases.Farmaceuta.logic.Farmaceuta;
import Clases.Farmaceuta.logic.FarmaceutaService;
import Clases.Farmaceuta.presentation.FarmaceutaModel;
import Clases.Farmaceuta.presentation.View.FarmaceutaView;
import Clases.Usuario.logic.Usuario;
import Clases.Usuario.logic.UsuarioService;

import javax.swing.*;

public class FarmaceutaController {
    private FarmaceutaModel model;
    private FarmaceutaView view;

    public FarmaceutaController(FarmaceutaModel model, FarmaceutaView view) throws Exception {
        this.model = model;
        this.view = view;

        // 🔹 Conexión MVC explícita
        view.setController(this);
        view.setModel(model);

        // 🔹 Inicializar datos
        model.setList(FarmaceutaService.instance().findAll());
        model.setCurrent(new Farmaceuta());
    }
    public void guardar() throws Exception {
        String id = view.getId().getText();
        String nombre = view.getNombre().getText();

        if (id.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Debe llenar todos los campos");
            return;
        }

        try {
            Farmaceuta existente = FarmaceutaService.instance().readById(id);
            existente.setNombre(nombre);
            FarmaceutaService.instance().update(existente);
            JOptionPane.showMessageDialog(view.getMainPanel(), "Farmaceuta actualizado");
        } catch (Exception e) {
            Farmaceuta nuevo = new Farmaceuta(id, nombre, id);
            try {
                FarmaceutaService.instance().create(nuevo);

                // 🔹 Crear usuario automáticamente
                Usuario u = new Usuario(id, nombre, id, "FAR");
                UsuarioService.instance().create(u);

                JOptionPane.showMessageDialog(view.getMainPanel(), "Farmaceuta agregado y acceso creado");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view.getMainPanel(), ex.getMessage());
            }
        }

        model.setList(FarmaceutaService.instance().findAll());
        model.setCurrent(new Farmaceuta());
    }


    public void borrar() throws Exception {
        String id = view.getId().getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Debe ingresar un id");
            return;
        }

        FarmaceutaService.instance().delete(id);
        UsuarioService.instance().delete(id); // 🔹 Eliminar acceso del farmaceuta

        JOptionPane.showMessageDialog(view.getMainPanel(), "Farmaceuta eliminado y acceso revocado");

        model.setList(FarmaceutaService.instance().findAll());
        model.setCurrent(new Farmaceuta());
    }


    public void buscar() throws Exception {
        String criterio = view.getNombreBuscar().getText();
        Farmaceuta f = FarmaceutaService.instance().readByNombre(criterio);
        if (f == null) f = FarmaceutaService.instance().readById(criterio);

        if (f != null) {
            model.setCurrent(f);
            JOptionPane.showMessageDialog(view.getMainPanel(), "Farmaceuta encontrado");
        } else {
            JOptionPane.showMessageDialog(view.getMainPanel(), "No se encontró el farmaceuta");
        }
    }

    public void limpiar() {
        model.setCurrent(new Farmaceuta());
        view.getNombreBuscar().setText("");
    }

    public void reporte() {
        StringBuilder reporte = new StringBuilder("📋 Lista de Farmaceutas:\n\n");
        for (Farmaceuta f : model.getList()) {
            reporte.append("ID: ").append(f.getId()).append("\n");
            reporte.append("Nombre: ").append(f.getNombre()).append("\n");
            reporte.append("-------------------------\n");
        }

        JOptionPane.showMessageDialog(view.getMainPanel(), reporte.toString());
    }
}

