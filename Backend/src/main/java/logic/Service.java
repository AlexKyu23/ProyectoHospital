package logic;

import data.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private static Service instance;
    private final AdminDAO adminDAO = new AdminDAO();
    private final FarmaceutaDAO farmaceutaDAO = new FarmaceutaDAO();
    private final MedicamentoDAO medicamentoDAO = new MedicamentoDAO();
    private final MedicoDAO medicoDAO = new MedicoDAO();
    private final PacienteDAO pacienteDAO = new PacienteDAO();
    private final RecetaDAO recetaDAO = new RecetaDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final ItemRecetaDAO itemRecetaDAO = new ItemRecetaDAO();
    private final PrescripcionDAO prescripcionDAO = new PrescripcionDAO();

    private Service() {
        try {
            System.out.println("✅ Usuarios cargados desde SQL: " +
                    usuarioDAO.search(new Usuario("", "", "", "")).size());
        } catch (Exception e) {
            System.err.println("Error al cargar usuarios: " + e.getMessage());
        }
    }

    public static Service instance() {
        if (instance == null) instance = new Service();
        return instance;
    }

    // =============================================================
    // === MÉTODOS GENERALES DE APOYO ==============================
    // =============================================================
    private <T> List<T> safeList(List<T> list) {
        return list != null ? list : new ArrayList<>();
    }

    // =============================================================
    // === ADMIN ===================================================
    // =============================================================
    public void create(Admin e) throws Exception { adminDAO.guardar(e); }
    public Admin readAdmin(String id) throws Exception { return adminDAO.buscarPorId(id); }
    public void update(Admin e) throws Exception { adminDAO.actualizar(e); }
    public void deleteAdmin(String id) throws Exception { adminDAO.borrar(id); }
    public List<Admin> searchAdmin() throws Exception { return safeList(adminDAO.listar()); }

    // =============================================================
    // === FARMACEUTA ==============================================
    // =============================================================
    public void create(Farmaceuta e) throws Exception { farmaceutaDAO.guardar(e); }
    public Farmaceuta readFarmaceuta(String id) throws Exception { return farmaceutaDAO.buscarPorId(id); }
    public void update(Farmaceuta e) throws Exception { farmaceutaDAO.actualizar(e); }
    public void deleteFarmaceuta(String id) throws Exception { farmaceutaDAO.borrar(id); }
    public List<Farmaceuta> searchFarmaceuta(Farmaceuta f) throws Exception { return safeList(farmaceutaDAO.search(f)); }

    // =============================================================
    // === MEDICAMENTO (Código tipo String) ========================
    // =============================================================
    public void create(Medicamento e) throws Exception {
        if (medicamentoDAO.buscarPorCodigo(e.getCodigo()) != null)
            throw new Exception("Medicamento ya existe");
        medicamentoDAO.guardar(e);
    }

    public Medicamento readMedicamento(String codigo) throws Exception {
        return medicamentoDAO.buscarPorCodigo(codigo);
    }

    public void update(Medicamento e) throws Exception { medicamentoDAO.actualizar(e); }
    public void deleteMedicamento(String codigo) throws Exception { medicamentoDAO.borrar(codigo); }
    public List<Medicamento> searchMedicamento(Medicamento e) throws Exception { return safeList(medicamentoDAO.search(e)); }

    // =============================================================
    // === MÉDICO ==================================================
    // =============================================================
    public void create(Medico e) throws Exception { medicoDAO.guardar(e); }
    public Medico readMedico(String id) throws Exception { return medicoDAO.buscarPorId(id); }
    public void update(Medico e) throws Exception { medicoDAO.actualizar(e); }
    public void deleteMedico(String id) throws Exception { medicoDAO.borrar(id); }
    public List<Medico> searchMedico(Medico e) throws Exception { return safeList(medicoDAO.search(e)); }

    // =============================================================
    // === PACIENTE ================================================
    // =============================================================
    public void create(Paciente e) throws Exception { pacienteDAO.guardar(e); }
    public Paciente readPaciente(String id) throws Exception { return pacienteDAO.buscarPorId(id); }
    public void update(Paciente e) throws Exception { pacienteDAO.actualizar(e); }
    public void deletePaciente(String id) throws Exception { pacienteDAO.borrar(id); }
    public List<Paciente> searchPaciente(Paciente e) throws Exception { return safeList(pacienteDAO.search(e)); }

    // =============================================================
    // === RECETA ==================================================
    // =============================================================
    public void create(Receta e) throws Exception { recetaDAO.guardar(e); }
    public Receta readReceta(String id) throws Exception { return recetaDAO.buscarPorId(id); }
    public void update(Receta e) throws Exception { recetaDAO.actualizar(e); }
    public void deleteReceta(String id) throws Exception { recetaDAO.borrar(id); }
    public List<Receta> searchReceta() throws Exception { return safeList(recetaDAO.listar()); }

    public List<Receta> findRecetasBetween(LocalDate start, LocalDate end) throws Exception {
        return safeList(recetaDAO.listar()).stream()
                .filter(r -> {
                    LocalDate f = r.getFechaConfeccion();
                    return f != null && !f.isBefore(start) && !f.isAfter(end);
                }).collect(Collectors.toList());
    }

    public void cambiarEstadoReceta(String recetaId, EstadoReceta estado) throws Exception {
        Receta r = recetaDAO.buscarPorId(recetaId);
        if (r != null) { r.setEstado(estado); recetaDAO.actualizar(r); }
    }

    // =============================================================
    // === ITEM RECETA =============================================
    // =============================================================
    public void create(ItemReceta e) throws Exception { itemRecetaDAO.guardar(e); }
    public List<ItemReceta> buscarItemsPorReceta(String recetaId) throws Exception {
        return safeList(itemRecetaDAO.buscarPorRecetaId(recetaId));
    }
    public void deleteItemReceta(String itemRecetaId) throws Exception {
        if (itemRecetaId == null || itemRecetaId.isEmpty()) {
            throw new Exception("ItemRecetaId inválido para borrar");
        }
        itemRecetaDAO.borrar(itemRecetaId);
    }

    // =============================================================
// === PRESCRIPCIÓN ============================================
// =============================================================
    public void create(Prescripcion p) throws Exception {
        prescripcionDAO.guardar(p);
    }

    public Prescripcion readPrescripcion(int numero) throws Exception {
        return prescripcionDAO.buscarPorNumero(numero);
    }

    public void update(Prescripcion p) throws Exception {
        prescripcionDAO.actualizar(p);
    }

    public void deletePrescripcion(int numero) throws Exception {
        prescripcionDAO.borrar(numero);
    }

    public List<Prescripcion> searchPrescripcion(Prescripcion filtro) throws Exception {
        // Si tu DAO no tiene un método search personalizado, puedes hacer esto:
        return safeList(prescripcionDAO.listar()).stream()
                .filter(p ->
                        (filtro.getPaciente() == null || filtro.getPaciente().getId() == null ||
                                p.getPaciente().getId().contains(filtro.getPaciente().getId())) &&
                                (filtro.getMedico() == null || filtro.getMedico().getId() == null ||
                                        p.getMedico().getId().contains(filtro.getMedico().getId())) &&
                                (filtro.getEstado() == null || filtro.getEstado().isEmpty() ||
                                        p.getEstado().equalsIgnoreCase(filtro.getEstado()))
                )
                .toList();
    }

    public List<Prescripcion> listarPrescripciones() throws Exception {
        return safeList(prescripcionDAO.listar());
    }

    // =============================================================
    // === USUARIO ==================================================
    // =============================================================
    public void create(Usuario e) throws Exception { usuarioDAO.guardar(e); }
    public Usuario readUsuario(String id) throws Exception { return usuarioDAO.buscarPorId(id); }
    public void update(Usuario e) throws Exception { usuarioDAO.actualizar(e); }
    public void deleteUsuario(String id) throws Exception { usuarioDAO.borrar(id); }
    public List<Usuario> searchUsuario(Usuario e) throws Exception { return safeList(usuarioDAO.search(e)); }

    // =============================================================
    // === DESPACHO (LÓGICA DE ESTADOS) ============================
    // =============================================================
    public List<Receta> recetasParaDespacho() throws Exception {
        LocalDate hoy = LocalDate.now();
        return safeList(recetaDAO.listar()).stream()
                .filter(r -> r.getEstado() == EstadoReceta.CONFECCIONADA)
                .filter(r -> {
                    LocalDate retiro = r.getFechaRetiro();
                    return retiro != null && !retiro.isBefore(hoy.minusDays(3)) && !retiro.isAfter(hoy.plusDays(3));
                })
                .collect(Collectors.toList());
    }

    public void iniciarDespacho(String id) throws Exception { cambiarEstadoReceta(id, EstadoReceta.EN_PROCESO); }
    public void alistarMedicamentos(String id) throws Exception { cambiarEstadoReceta(id, EstadoReceta.LISTA); }
    public void entregarReceta(String id) throws Exception { cambiarEstadoReceta(id, EstadoReceta.ENTREGADA); }

    // =============================================================
    // === REGISTROS AUTOMÁTICOS ==================================
    // =============================================================
    public void registrarMedico(Medico m) throws Exception {
        if (medicoDAO.buscarPorId(m.getId()) != null) {
            throw new Exception("El médico con ID " + m.getId() + " ya existe");
        }
        if (usuarioDAO.buscarPorId(m.getId()) != null) {
            throw new Exception("El usuario con ID " + m.getId() + " ya existe");
        }
        create(m);
        create(new Usuario(m.getId(), m.getNombre(), m.getId(), "MED"));
    }

    public void registrarFarmaceuta(Farmaceuta f) throws Exception {
        if (farmaceutaDAO.buscarPorId(f.getId()) != null) {
            throw new Exception("El farmaceuta con ID " + f.getId() + " ya existe");
        }
        if (usuarioDAO.buscarPorId(f.getId()) != null) {
            throw new Exception("El usuario con ID " + f.getId() + " ya existe");
        }
        create(f);
        create(new Usuario(f.getId(), f.getNombre(), f.getId(), "FAR"));
    }

}
