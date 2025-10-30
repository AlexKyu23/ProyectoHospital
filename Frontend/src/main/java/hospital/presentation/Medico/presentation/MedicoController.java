package hospital.presentation.Medico.presentation;

import hospital.logic.Service;
import hospital.presentation.Refresher;
import hospital.presentation.ThreadListener;
import logic.Medico;
import logic.Usuario;

import javax.swing.*;
import java.util.List;

public class MedicoController implements ThreadListener {
    private final MedicoModel model;
    private final MedicoView view;
    private Refresher refresher;

    public MedicoController(MedicoModel model, MedicoView view) throws Exception {
        this.model = model;
        this.view = view;

        System.out.println("📦 Iniciando MedicoController...");
        view.setController(this);
        System.out.println("🔗 View conectada al controller.");
        view.setModel(model);
        System.out.println("🔗 Model conectado a la view.");

        model.init();
        System.out.println("🧹 Modelo inicializado.");

        // ✅ CARGA ASÍNCRONA de datos del backend
        cargarDatosAsincrono();

        refresher = new Refresher(this);
        refresher.start();
        System.out.println("✅ MedicoController inicializado (cargando datos en background)");
    }

    /**
     * Carga la lista de médicos de forma asíncrona para no bloquear el UI
     */
    private void cargarDatosAsincrono() {
        SwingWorker<List<Medico>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Medico> doInBackground() throws Exception {
                System.out.println("🔄 Cargando médicos del backend...");
                return Service.instance().findAllMedicos();
            }

            @Override
            protected void done() {
                try {
                    List<Medico> lista = get();
                    System.out.println("📡 Lista de médicos recibida: " + lista.size() + " elementos.");
                    model.setList(lista);
                } catch (Exception e) {
                    System.err.println("❌ Error al obtener médicos del backend: " + e.getMessage());
                    e.printStackTrace();
                    // Mantiene la lista vacía pero no bloquea la UI
                    JOptionPane.showMessageDialog(
                            view.getMainPanel(),
                            "Error al cargar médicos: " + e.getMessage(),
                            "Error de Conexión",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        };

        worker.execute();
    }

    public void guardar() {
        String id = view.getId().getText().trim();
        String nombre = view.getNombre().getText().trim();
        String especialidad = view.getEspecialidad().getText().trim();

        if (id.isEmpty() || nombre.isEmpty() || especialidad.isEmpty()) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Debe llenar todos los campos");
            return;
        }

        // Ejecuta el guardado en background
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    Medico existente = Service.instance().readMedico(id);
                    existente.setNombre(nombre);
                    existente.setEspecialidad(especialidad);
                    Service.instance().updateMedico(existente);
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(view.getMainPanel(), "Médico actualizado")
                    );
                } catch (Exception e) {
                    Medico nuevo = new Medico(id, nombre, id, especialidad);
                    Service.instance().createMedico(nuevo);

                    Usuario u = new Usuario(id, nombre, id, "MED");
                    Service.instance().createUsuario(u);

                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(view.getMainPanel(), "Médico agregado y acceso creado")
                    );
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    actualizarLista();
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(view.getMainPanel(), ex.getMessage())
                    );
                }
            }
        };

        worker.execute();
    }

    public void borrar() {
        String id = view.getId().getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Debe ingresar un ID");
            return;
        }

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                Service.instance().deleteMedico(id);
                Service.instance().deleteUsuario(id);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    JOptionPane.showMessageDialog(view.getMainPanel(), "Médico eliminado y acceso revocado");
                    actualizarLista();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(view.getMainPanel(), e.getMessage());
                }
            }
        };

        worker.execute();
    }

    public void buscar() {
        String criterio = view.getNombreBuscar().getText().trim();
        if (criterio.isEmpty()) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Ingrese nombre o ID para buscar");
            return;
        }

        SwingWorker<Medico, Void> worker = new SwingWorker<>() {
            @Override
            protected Medico doInBackground() throws Exception {
                return Service.instance().readMedico(criterio);
            }

            @Override
            protected void done() {
                try {
                    Medico m = get();
                    if (m != null) {
                        model.setCurrent(m);
                        JOptionPane.showMessageDialog(view.getMainPanel(), "Médico encontrado");
                    } else {
                        JOptionPane.showMessageDialog(view.getMainPanel(), "No se encontró el médico");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(view.getMainPanel(), e.getMessage());
                }
            }
        };

        worker.execute();
    }

    public void limpiar() {
        model.setCurrent(new Medico());
        view.getNombreBuscar().setText("");
    }

    public void reporte() {
        StringBuilder sb = new StringBuilder("📋 Lista de Médicos:\n\n");
        for (Medico m : model.getList()) {
            sb.append("ID: ").append(m.getId()).append("\n");
            sb.append("Nombre: ").append(m.getNombre()).append("\n");
            sb.append("Especialidad: ").append(m.getEspecialidad()).append("\n");
            sb.append("-------------------------\n");
        }
        JOptionPane.showMessageDialog(view.getMainPanel(), sb.toString());
    }

    private void actualizarLista() {
        SwingWorker<List<Medico>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Medico> doInBackground() throws Exception {
                return Service.instance().findAllMedicos();
            }

            @Override
            protected void done() {
                try {
                    model.setList(get());
                    model.setCurrent(new Medico());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(view.getMainPanel(),
                            "Error al actualizar lista: " + e.getMessage());
                }
            }
        };

        worker.execute();
    }

    @Override
    public void refresh() {
        actualizarLista();
    }

    public void stop() {
        if (refresher != null) {
            refresher.stop();
        }
    }
}