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

        // ✅ CARGA ASÍNCRONA
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
            protected List<Farmaceuta> doInBackground() throws Exception {
                System.out.println("🔄 Cargando farmacéuticos del backend...");
                return Service.instance().findAllFarmaceuta();
            }

            @Override
            protected void done() {
                try {
                    List<Farmaceuta> lista = get();
                    System.out.println("📡 Farmacéuticos cargados: " + lista.size());
                    model.setList(lista);
                } catch (Exception e) {
                    System.err.println("❌ Error al cargar farmacéuticos: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    public void search(Farmaceuta filter) {
        SwingWorker<List<Farmaceuta>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Farmaceuta> doInBackground() throws Exception {
                return Service.instance().searchFarmaceuta(filter.getNombre());
            }

            @Override
            protected void done() {
                try {
                    model.setFilter(filter);
                    model.setMode(1); // MODE_CREATE
                    model.setCurrent(new Farmaceuta());
                    model.setList(get());
                } catch (Exception e) {
                    System.err.println("Error en búsqueda: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }

    public void save(Farmaceuta f) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                switch (model.getMode()) {
                    case 1 -> { // MODE_CREATE
                        Service.instance().createFarmaceuta(f);
                        Usuario u = new Usuario(f.getId(), f.getNombre(), f.getId(), "FAR");
                        Service.instance().createUsuario(u);
                    }
                    case 2 -> // MODE_EDIT
                            Service.instance().updateFarmaceuta(f);
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    model.setFilter(new Farmaceuta());
                    search(model.getFilter());
                } catch (Exception ex) {
                    System.err.println("Error al guardar: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    public void edit(int row) {
        Farmaceuta f = model.getList().get(row);
        SwingWorker<Farmaceuta, Void> worker = new SwingWorker<>() {
            @Override
            protected Farmaceuta doInBackground() throws Exception {
                return Service.instance().readFarmaceuta(f.getId());
            }

            @Override
            protected void done() {
                try {
                    model.setMode(2); // MODE_EDIT
                    model.setCurrent(get());
                } catch (Exception ex) {
                    System.err.println("Error al editar: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    public void delete() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                Farmaceuta f = model.getCurrent();
                Service.instance().deleteFarmaceuta(f.getId());
                Service.instance().deleteUsuario(f.getId());
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    search(model.getFilter());
                } catch (Exception ex) {
                    System.err.println("Error al eliminar: " + ex.getMessage());
                }
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
        SwingWorker<List<Farmaceuta>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Farmaceuta> doInBackground() throws Exception {
                return Service.instance().findAllFarmaceuta();
            }

            @Override
            protected void done() {
                try {
                    model.setList(get());
                } catch (Exception e) {
                    System.err.println("Error al refrescar: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }

    public void stop() {
        if (refresher != null) {
            refresher.stop();
        }
    }
}