// Frontend/src/main/java/presentation/Farmaceuta/presentation/FarmaceutaController.java
package hospital.presentation.Farmaceuta.presentation;

import hospital.logic.Service;
import logic.Farmaceuta;
import logic.Usuario;
import hospital.presentation.Farmaceuta.presentation.View.FarmaceutaView;
import hospital.presentation.Refresher;
import hospital.presentation.ThreadListener;

import java.util.List;

public class FarmaceutaController implements ThreadListener {
    private final FarmaceutaView view;
    private final FarmaceutaModel model;
    private Refresher refresher;

    public FarmaceutaController(FarmaceutaView view, FarmaceutaModel model) throws Exception {
        this.view = view;
        this.model = model;

        model.init(); // Inicializa filter, current, list, mode
        model.setList(Service.instance().findAllFarmaceuta());
        view.setController(this);
        view.setModel(model);

        refresher = new Refresher(this);
        refresher.start();
    }

    // === BUSCAR ===
    public void search(Farmaceuta filter) {
        try {
            model.setFilter(filter);
            List<Farmaceuta> rows = Service.instance().searchFarmaceuta(filter.getNombre());
            model.setMode(1); // MODE_CREATE
            model.setCurrent(new Farmaceuta());
            model.setList(rows);
        } catch (Exception e) {
            // Silenciar o loguear
        }
    }

    // === GUARDAR (CREAR O EDITAR) ===
    public void save(Farmaceuta f) {
        try {
            switch (model.getMode()) {
                case 1 -> { // MODE_CREATE
                    Service.instance().createFarmaceuta(f);
                    Usuario u = new Usuario(f.getId(), f.getNombre(), f.getId(), "FAR");
                    Service.instance().createUsuario(u);
                }
                case 2 -> // MODE_EDIT
                        Service.instance().updateFarmaceuta(f);
            }
            model.setFilter(new Farmaceuta());
            search(model.getFilter());
        } catch (Exception ex) {
            // En producción: mostrar error
        }
    }

    // === EDITAR (SELECCIONAR FILA) ===
    public void edit(int row) {
        Farmaceuta f = model.getList().get(row);
        try {
            model.setMode(2); // MODE_EDIT
            model.setCurrent(Service.instance().readFarmaceuta(f.getId()));
        } catch (Exception ex) {
            // Silenciar
        }
    }

    // === BORRAR ===
    public void delete() {
        try {
            Farmaceuta f = model.getCurrent();
            Service.instance().deleteFarmaceuta(f.getId());
            Service.instance().deleteUsuario(f.getId());
            search(model.getFilter());
        } catch (Exception ex) {
            // Silenciar
        }
    }

    // === LIMPIAR FORMULARIO ===
    public void clear() {
        model.setMode(1); // MODE_CREATE
        model.setCurrent(new Farmaceuta());
        view.getNombreBuscar().setText("");
    }

    // === REFRESH AUTOMÁTICO ===
    @Override
    public void refresh() {
        try {
            model.setList(Service.instance().findAllFarmaceuta());
        } catch (Exception e) {
            // Silenciar
        }
    }

    // === DETENER REFRESHER ===
    public void stop() {
        if (refresher != null) {
            refresher.stop();
        }
    }
}