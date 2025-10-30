package hospital.presentation.Paciente.presentation;

import hospital.logic.Service;
import hospital.presentation.Paciente.presentation.View.PacienteView;
import hospital.presentation.Refresher;
import hospital.presentation.ThreadListener;
import logic.Paciente;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class PacienteController implements ThreadListener {
    private final PacienteModel model;
    private final PacienteView view;
    private Refresher refresher;

    public PacienteController(PacienteModel model, PacienteView view) throws Exception {
        this.model = model;
        this.view = view;

        System.out.println("📦 Iniciando PacienteController...");
        view.setController(this);
        view.setModel(model);
        model.init();
        cargarDatosAsincrono();

        refresher = new Refresher(this);
        refresher.start();
        System.out.println("✅ PacienteController inicializado");
    }

    private void cargarDatosAsincrono() {
        SwingWorker<List<Paciente>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Paciente> doInBackground() {
                try {
                    System.out.println("🔄 Cargando pacientes del backend...");
                    return Service.instance().findAllPacientes();
                } catch (Exception e) {
                    System.err.println("❌ Error al cargar pacientes: " + e.getMessage());
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

    public void guardar() {
        String id = view.getId().getText().trim();
        String nombre = view.getNombre().getText().trim();
        String telefono = view.getNumeroTelefono().getText().trim();
        LocalDate fechaNacimiento = view.getFechaNacimiento().getDate();

        if (id.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || fechaNacimiento == null) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Debe llenar todos los campos");
            return;
        }

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    Paciente existente = Service.instance().readPaciente(id);
                    existente.setNombre(nombre);
                    existente.setTelefono(telefono);
                    existente.setFechaNacimiento(fechaNacimiento);
                    Service.instance().updatePaciente(existente);
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(view.getMainPanel(), "Paciente actualizado"));
                } catch (Exception e) {
                    try {
                        Paciente nuevo = new Paciente(id, nombre, telefono, fechaNacimiento);
                        Service.instance().createPaciente(nuevo);
                        SwingUtilities.invokeLater(() ->
                                JOptionPane.showMessageDialog(view.getMainPanel(), "Paciente agregado"));
                    } catch (Exception ex) {
                        System.err.println("❌ Error al crear paciente: " + ex.getMessage());
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                actualizarLista();
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
            protected Void doInBackground() {
                try {
                    Service.instance().deletePaciente(id);
                } catch (Exception e) {
                    System.err.println("❌ Error al eliminar paciente: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void done() {
                JOptionPane.showMessageDialog(view.getMainPanel(), "Paciente eliminado");
                actualizarLista();
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

        SwingWorker<Paciente, Void> worker = new SwingWorker<>() {
            @Override
            protected Paciente doInBackground() {
                try {
                    return Service.instance().readPaciente(criterio);
                } catch (Exception e) {
                    System.err.println("❌ Error en búsqueda: " + e.getMessage());
                    return null;
                }
            }

            @Override
            protected void done() {
                try {
                    Paciente p = get();
                    if (p != null) {
                        model.setCurrent(p);
                        JOptionPane.showMessageDialog(view.getMainPanel(), "Paciente encontrado");
                    } else {
                        JOptionPane.showMessageDialog(view.getMainPanel(), "No se encontró el paciente");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(view.getMainPanel(), "Error: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }

    public void limpiar() {
        model.setCurrent(new Paciente());
        view.getNombreBuscar().setText("");
    }

    public void reporte() {
        StringBuilder sb = new StringBuilder("📋 Lista de Pacientes:\n\n");
        for (Paciente p : model.getList()) {
            sb.append("ID: ").append(p.getId()).append("\n");
            sb.append("Nombre: ").append(p.getNombre()).append("\n");
            sb.append("Teléfono: ").append(p.getTelefono()).append("\n");
            sb.append("Fecha de Nacimiento: ").append(p.getFechaNacimiento()).append("\n");
            sb.append("-------------------------\n");
        }
        JOptionPane.showMessageDialog(view.getMainPanel(), sb.toString());
    }

    private void actualizarLista() {
        SwingWorker<List<Paciente>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Paciente> doInBackground() {
                try {
                    return Service.instance().findAllPacientes();
                } catch (Exception e) {
                    System.err.println("❌ Error al actualizar lista: " + e.getMessage());
                    return List.of();
                }
            }

            @Override
            protected void done() {
                try {
                    model.setList(get());
                    model.setCurrent(new Paciente());
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
        if (refresher != null) refresher.stop();
    }
}
