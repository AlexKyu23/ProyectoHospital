package hospital.presentation.Medicamento.presentation;

import hospital.Application;
import hospital.logic.Service;
import hospital.presentation.Refresher;
import hospital.presentation.ThreadListener;
import logic.Medicamento;

import javax.swing.*;
import java.util.List;

public class MedicamentoController implements ThreadListener {
    private final MedicamentoView view;
    private final MedicamentoModel model;
    private Refresher refresher;

    public MedicamentoController(MedicamentoView view, MedicamentoModel model) throws Exception {
        this.view = view;
        this.model = model;

        System.out.println("📦 Iniciando MedicamentoController...");

        model.init();

        // ✅ CARGA ASÍNCRONA
        cargarDatosAsincrono();

        view.setController(this);
        view.setModel(model);

        refresher = new Refresher(this);
        refresher.start();
        System.out.println("✅ MedicamentoController inicializado");
    }

    private void cargarDatosAsincrono() {
        SwingWorker<List<Medicamento>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Medicamento> doInBackground() throws Exception {
                System.out.println("🔄 Cargando medicamentos del backend...");
                return Service.instance().findAllMedicamentos();
            }

            @Override
            protected void done() {
                try {
                    List<Medicamento> lista = get();
                    System.out.println("📡 Medicamentos cargados: " + lista.size());
                    model.setList(lista);
                } catch (Exception e) {
                    System.err.println("❌ Error al cargar medicamentos: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    public void search(Medicamento filter) {
        SwingWorker<List<Medicamento>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Medicamento> doInBackground() throws Exception {
                return Service.instance().searchMedicamento(filter);
            }

            @Override
            protected void done() {
                try {
                    model.setFilter(filter);
                    model.setMode(Application.MODE_CREATE);
                    model.setCurrent(new Medicamento());
                    model.setList(get());
                } catch (Exception e) {
                    System.err.println("Error en búsqueda: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }

    public void save(Medicamento m) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                switch (model.getMode()) {
                    case Application.MODE_CREATE -> Service.instance().createMedicamento(m);
                    case Application.MODE_EDIT -> Service.instance().updateMedicamento(m);
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    model.setFilter(new Medicamento());
                    search(model.getFilter());
                } catch (Exception ex) {
                    System.err.println("Error al guardar: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    public void edit(int row) {
        Medicamento m = model.getList().get(row);
        SwingWorker<Medicamento, Void> worker = new SwingWorker<>() {
            @Override
            protected Medicamento doInBackground() throws Exception {
                return Service.instance().readMedicamento(m.getCodigo());
            }

            @Override
            protected void done() {
                try {
                    model.setMode(Application.MODE_EDIT);
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
                Service.instance().deleteMedicamento(model.getCurrent().getCodigo());
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
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Medicamento());
        // ❌ REMOVIDO: view.getNombreBuscar().setText("");
        view.getDescripcionBuscar().setText("");
        view.getCodigoBuscar().setText("");
    }

    @Override
    public void refresh() {
        SwingWorker<List<Medicamento>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Medicamento> doInBackground() throws Exception {
                return Service.instance().findAllMedicamentos();
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

    public void reporte() {
        StringBuilder sb = new StringBuilder("Lista de Medicamentos:\n\n");
        for (Medicamento m : model.getList()) {
            sb.append("Código: ").append(m.getCodigo())
                    .append(" | Nombre: ").append(m.getNombre())
                    .append(" | Desc: ").append(m.getDescripcion())
                    .append("\n-------------------------\n");
        }
        JOptionPane.showMessageDialog(view.getMainPanel(), sb.toString(), "Reporte", JOptionPane.INFORMATION_MESSAGE);
    }

    public void stop() {
        if (refresher != null) refresher.stop();
    }
}