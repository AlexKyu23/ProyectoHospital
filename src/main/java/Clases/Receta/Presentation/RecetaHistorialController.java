package Clases.Receta.Presentation;

import Clases.Receta.Data.RepositorioRecetas;
import Clases.Receta.logic.Receta;

import java.util.List;
import java.util.stream.Collectors;

public class RecetaHistorialController {
    private RecetaModel model;
    private RecetaView view;

    public RecetaHistorialController(RecetaModel model, RecetaView view) {
        this.model = model;
        this.view = view;

        view.setModel(model);
        initController();
        cargarTodas();
    }

    private void initController() {
        view.getBotonBuscar().addActionListener(e -> buscarPorId());
    }

    private void cargarTodas() {
        model.setList(RepositorioRecetas.getRecetas());
    }

    private void buscarPorId() {
        String id = view.getIdBuscar().getText().trim();
        if (id.isEmpty()) {
            cargarTodas();
        } else {
            List<Receta> filtradas = RepositorioRecetas.getRecetas().stream()
                    .filter(r -> r.getId().toLowerCase().contains(id.toLowerCase()))
                    .collect(Collectors.toList());
            model.setList(filtradas);
        }
    }
}
