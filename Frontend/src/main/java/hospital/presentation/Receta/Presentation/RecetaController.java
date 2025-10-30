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

        System.out.println("📦 Iniciando RecetaController...");

        view.setModel(model);
        view.setController(this);

        model.init();

        // ✅ CARGA ASÍNCRONA
        cargarTodasAsincrono();

        initListeners();
        System.out.println("✅ RecetaController inicializado");
    }

    private void initListeners() {
        view.getBotonBuscar().addActionListener(e -> buscarPorId());
    }

    private void cargarTodasAsincrono() {
        SwingWorker<List<Receta>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Receta> doInBackground() throws Exception {
                System.out.println("🔄 Cargando recetas del backend...");
                return Service.instance().findAllRecetas();
            }

            @Override
            protected void done() {
                try {
                    List<Receta> recetas = get();
                    System.out.println("📡 Recetas cargadas: " + recetas.size());
                    model.setList(recetas);
                } catch (Exception e) {
                    System.err.println("❌ Error al cargar recetas: " + e.getMessage());
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(view.getMainPanel(),
                            "Error al cargar recetas: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }

    public void buscarPorId() {
        String id = view.getIdBuscar().getText().trim();

        if (id.isEmpty()) {
            cargarTodasAsincrono();
            return;
        }

        SwingWorker<List<Receta>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Receta> doInBackground() throws Exception {
                List<Receta> todas = Service.instance().findAllRecetas();
                return todas.stream()
                        .filter(r -> r.getId().toLowerCase().contains(id.toLowerCase()))
                        .collect(Collectors.toList());
            }

            @Override
            protected void done() {
                try {
                    List<Receta> filtradas = get();
                    model.setList(filtradas);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(view.getMainPanel(),
                            "Error al buscar recetas: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }
}