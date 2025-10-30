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
            protected List<Medicamento> doInBackground() {
                try {
                    System.out.println("🔄 Cargando medicamentos del backend...");
                    return Service.instance().findAllMedicamentos();
                } catch (Exception e) {
                    System.err.println("❌ Error al cargar medicamentos: " + e.getMessage());
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

    public void search(Medicamento filter) {
        SwingWorker<List<Medicamento>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Medicamento> doInBackground() {
                try {
                    return Service.instance().searchMedicamento(filter);
                } catch (Exception e) {
                    System.err.println("❌ Error en búsqueda: " + e.getMessage());
                    return List.of();
                }
            }

            @Override
            protected void done() {
                try {
                    model.setFilter(filter != null ? filter : new Medicamento());
                    model.setMode(Application.MODE_CREATE);
                    model.setCurrent(new Medicamento());
                    model.setList(get());
                } catch (Exception e) {
                    System.err.println("❌ Error al aplicar búsqueda: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }

    public void save(Medicamento m) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    if (model.getMode() == Application.MODE_CREATE) {
                        Service.instance().createMedicamento(m);
                    } else {
                        Service.instance().updateMedicamento(m);
                    }
                } catch (Exception e) {
                    System.err.println("❌ Error al guardar medicamento: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void done() {
                model.setFilter(new Medicamento());
                search(model.getFilter());
            }
        };
        worker.execute();
    }

    public void edit(int row) {
        List<Medicamento> lista = model.getList();
        if (lista == null || row >= lista.size()) return;

        Medicamento m = lista.get(row);
        SwingWorker<Medicamento, Void> worker = new SwingWorker<>() {
            @Override
            protected Medicamento doInBackground() {
                try {
                    return Service.instance().readMedicamento(m.getCodigo());
                } catch (Exception e) {
                    System.err.println("❌ Error al leer medicamento: " + e.getMessage());
                    return null;
                }
            }

            @Override
            protected void done() {
                try {
                    Medicamento result = get();
                    if (result != null) {
                        model.setMode(Application.MODE_EDIT);
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
                    Medicamento m = model.getCurrent();
                    Service.instance().deleteMedicamento(m.getCodigo());
                } catch (Exception e) {
                    System.err.println("❌ Error al eliminar medicamento: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void done() {
                model.setFilter(new Medicamento());
                search(model.getFilter());
            }
        };
        worker.execute();
    }

    public void clear() {
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Medicamento());
        view.getDescripcionBuscar().setText("");
        view.getCodigoBuscar().setText("");
    }

    @Override
    public void refresh() {
        cargarDatosAsincrono();
    }

    public void reporte() {
        StringBuilder sb = new StringBuilder("📋 Lista de Medicamentos:\n\n");
        for (Medicamento m : model.getList()) {
            sb.append("Código: ").append(m.getCodigo()).append("\n");
            sb.append("Nombre: ").append(m.getNombre()).append("\n");
            sb.append("Descripción: ").append(m.getDescripcion()).append("\n");
            sb.append("-------------------------\n");
        }
        JOptionPane.showMessageDialog(view.getMainPanel(), sb.toString(), "Reporte", JOptionPane.INFORMATION_MESSAGE);
    }

    public void stop() {
        if (refresher != null) refresher.stop();
    }
}
