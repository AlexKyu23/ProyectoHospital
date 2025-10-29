package hospital.presentation.Receta.Presentation;

import hospital.logic.Service;
import logic.Receta;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class RecetaController {
    private final RecetaModel model;
    private final RecetaView view;

    public RecetaController(RecetaModel model, RecetaView view) throws Exception {
        this.model = model;
        this.view = view;

        view.setModel(model);
        view.setController(this);

        model.init();
        cargarTodas();
        initListeners();
    }

    private void initListeners() {
        view.getBotonBuscar().addActionListener(e -> buscarPorId());
    }

    private void cargarTodas() {
        try {
            List<Receta> recetas = Service.instance().findAllRecetas();
            model.setList(recetas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Error al cargar recetas: " + e.getMessage());
        }
    }

    public void buscarPorId() {
        String id = view.getIdBuscar().getText().trim();
        if (id.isEmpty()) {
            cargarTodas();
            return;
        }

        try {
            List<Receta> todas = Service.instance().findAllRecetas();
            List<Receta> filtradas = todas.stream()
                    .filter(r -> r.getId().toLowerCase().contains(id.toLowerCase()))
                    .collect(Collectors.toList());
            model.setList(filtradas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Error al buscar recetas: " + e.getMessage());
        }
    }
}
