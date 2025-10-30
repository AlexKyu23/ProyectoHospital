package hospital.presentation.Farmaceuta.presentation;

import hospital.logic.Service;
import logic.Farmaceuta;
import logic.Usuario;
import hospital.presentation.Farmaceuta.presentation.View.FarmaceutaView;
import hospital.presentation.Refresher;
import hospital.presentation.ThreadListener;

import javax.swing.*;
import java.util.List;

public class FarmaceutaController implements ThreadListener {
    private final FarmaceutaView view;
    private final FarmaceutaModel model;
    private Refresher refresher;

    public FarmaceutaController(FarmaceutaView view, FarmaceutaModel model) throws Exception {
        this.view = view;
        this.model = model;

        System.out.println("📦 Iniciando FarmaceutaController...");
        model.init();
        cargarDatosAsincrono();

        view.setController(this);
        view.setModel(model);

        refresher = new Refresher(this);
        refresher.start();
        System.out.println("✅ FarmaceutaController inicializado");
    }

    private void cargarDatosAsincrono() {
        SwingWorker<List<Farmaceuta>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Farmaceuta> doInBackground() {
                try {
                    System.out.println("🔄 Cargando farmacéuticos del backend...");
                    return Service.instance().findAllFarmaceuta();
                } catch (Exception e) {
                    System.err.println("❌ Error al cargar farmacéuticos: " + e.getMessage());
                    return List.of();
                }
            }

            @Override
            protected void done() {
                try {
                    model.setList(get());
                } catch (Exception e) {
                    System.err.println("❌ Error en carga asincrónica: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }

    public void search(Farmaceuta filter) {
        SwingWorker<List<Farmaceuta>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Farmaceuta> doInBackground() {
                try {
                    return Service.instance().searchFarmaceuta(filter.getNombre());
                } catch (Exception e) {
                    System.err.println("❌ Error en búsqueda: " + e.getMessage());
                    return List.of();
                }
            }

            @Override
            protected void done() {
                try {
                    model.setFilter(filter);
                    model.setMode(1); // MODE_CREATE
                    model.setCurrent(new Farmaceuta());
                    model.setList(get());
                } catch (Exception e) {
                    System.err.println("❌ Error al aplicar búsqueda: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }

    public void save(Farmaceuta f) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    if (model.getMode() == 1) {
                        Service.instance().createFarmaceuta(f);
                        Usuario u = new Usuario(f.getId(), f.getNombre(), f.getId(), "FAR");
                        Service.instance().createUsuario(u);
                    } else {
                        Service.instance().updateFarmaceuta(f);
                    }
                } catch (Exception e) {
                    System.err.println("❌ Error al guardar farmacéutico: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void done() {
                model.setFilter(new Farmaceuta());
                search(model.getFilter());
            }
        };
        worker.execute();
    }

    public void edit(int row) {
        List<Farmaceuta> lista = model.getList();
        if (lista == null || row >= lista.size()) return;

        Farmaceuta f = lista.get(row);
        SwingWorker<Farmaceuta, Void> worker = new SwingWorker<>() {
            @Override
            protected Farmaceuta doInBackground() {
                try {
                    return Service.instance().readFarmaceuta(f.getId());
                } catch (Exception e) {
                    System.err.println("❌ Error al leer farmacéutico: " + e.getMessage());
                    return null;
                }
            }

            @Override
            protected void done() {
                try {
                    Farmaceuta result = get();
                    if (result != null) {
                        model.setMode(2); // MODE_EDIT
                        model.setCurrent(result);
                    }
                } catch (Exception e) {
                    System.err.println("❌ Error al editar: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }

    public void delete() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    Farmaceuta f = model.getCurrent();
                    Service.instance().deleteFarmaceuta(f.getId());
                    Service.instance().deleteUsuario(f.getId());
                } catch (Exception e) {
                    System.err.println("❌ Error al eliminar farmacéutico: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void done() {
                model.setFilter(new Farmaceuta());
                search(model.getFilter());
            }
        };
        worker.execute();
    }

    public void clear() {
        model.setMode(1); // MODE_CREATE
        model.setCurrent(new Farmaceuta());
        view.getNombreBuscar().setText("");
    }

    @Override
    public void refresh() {
        cargarDatosAsincrono();
    }

    public void stop() {
        if (refresher != null) refresher.stop();
    }
}
