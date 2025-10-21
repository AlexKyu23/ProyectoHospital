package Clases.Receta.Presentation;

import Clases.Receta.logic.Receta;
import Clases.Receta.logic.RecetaService;
import java.util.List;
import java.util.stream.Collectors;

public class RecetaHistorialController {
    private RecetaModel model;
    private RecetaView view;
    private RecetaService service;

    public RecetaHistorialController(RecetaModel model, RecetaView view) {
        this.model = model;
        this.view = view;
        this.service = RecetaService.instance();

        view.setModel(model);
        initController();
        cargarTodas();
    }

    private void initController() {
        view.getBotonBuscar().addActionListener(e -> buscarPorId());
    }

    private void cargarTodas() {
        try {
            model.setList(service.findAll());  // ✅ ahora carga desde SQL
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buscarPorId() {
        String id = view.getIdBuscar().getText().trim();
        if (id.isEmpty()) {
            cargarTodas();
        } else {
            try {
                List<Receta> todas = service.findAll();
                List<Receta> filtradas = todas.stream()
                        .filter(r -> r.getId().toLowerCase().contains(id.toLowerCase()))
                        .collect(Collectors.toList());
                model.setList(filtradas);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}