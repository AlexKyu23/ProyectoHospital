package logic;

import data.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private static Service instance;
    private final AdminDAO adminDAO;
    private final FarmaceutaDAO farmaceutaDAO;
    private final MedicamentoDAO medicamentoDAO;
    private final MedicoDAO medicoDAO;
    private final PacienteDAO pacienteDAO;
    private final RecetaDAO recetaDAO;
    private final UsuarioDAO usuarioDAO;
    private final ItemRecetaDAO itemRecetaDAO;
    private final PrescripcionDAO prescripcionDAO;

    private Service() {
        adminDAO = new AdminDAO();
        farmaceutaDAO = new FarmaceutaDAO();
        medicamentoDAO = new MedicamentoDAO();
        medicoDAO = new MedicoDAO();
        pacienteDAO = new PacienteDAO();
        recetaDAO = new RecetaDAO();
        usuarioDAO = new UsuarioDAO();
        itemRecetaDAO = new ItemRecetaDAO();
        prescripcionDAO = new PrescripcionDAO();
        try {
            System.out.println("✅ Usuarios cargados desde SQL: " + usuarioDAO.search(new Usuario("", "", "", "")).size());
        } catch (Exception e) {
            System.err.println("Error al cargar usuarios: " + e.getMessage());
        }
    }

    public static Service instance() {
        if (instance == null) instance = new Service();
        return instance;
    }

    // Admin
    public void createAdmin(Admin admin) throws Exception {
        if (adminDAO.buscarPorId(admin.getId()) != null)
            throw new Exception("El admin ya existe");
        adminDAO.guardar(admin);
    }

    public Admin readAdmin(String id) throws Exception {
        return adminDAO.buscarPorId(id);
    }

    public void updateAdmin(Admin admin) throws Exception {
        adminDAO.actualizar(admin);
    }

    public void deleteAdmin(String id) throws Exception {
        adminDAO.borrar(id);
    }

    // Farmaceuta
    public void createFarmaceuta(Farmaceuta f) throws Exception {
        if (farmaceutaDAO.buscarPorId(f.getId()) != null)
            throw new Exception("El farmaceuta ya existe");
        farmaceutaDAO.guardar(f);
    }

    public Farmaceuta readFarmaceuta(String id) throws Exception {
        return farmaceutaDAO.buscarPorId(id);
    }

    public Farmaceuta readFarmaceutaByNombre(String nombre) throws Exception {
        List<Farmaceuta> list = farmaceutaDAO.search(new Farmaceuta("", nombre, ""));
        return list.isEmpty() ? null : list.get(0);
    }

    public void updateFarmaceuta(Farmaceuta f) throws Exception {
        farmaceutaDAO.actualizar(f);
    }

    public void deleteFarmaceuta(String id) throws Exception {
        farmaceutaDAO.borrar(id);
    }

    public List<Farmaceuta> findAllFarmaceuta() throws Exception {
        return farmaceutaDAO.listar();
    }

    // Medicamento
    public void createMedicamento(Medicamento m) throws Exception {
        if (medicamentoDAO.buscarPorCodigo(m.getCodigo()) != null)
            throw new Exception("El medicamento ya existe");
        medicamentoDAO.guardar(m);
    }

    public Medicamento readMedicamento(int codigo) throws Exception {
        return medicamentoDAO.buscarPorCodigo(codigo);
    }

    public Medicamento readMedicamentoByNombre(String nombre) throws Exception {
        List<Medicamento> list = medicamentoDAO.search(new Medicamento(nombre, "", 0));
        return list.isEmpty() ? null : list.get(0);
    }

    public void updateMedicamento(Medicamento m) throws Exception {
        medicamentoDAO.actualizar(m);
    }

    public void deleteMedicamento(int codigo) throws Exception {
        medicamentoDAO.borrar(codigo);
    }

    public List<Medicamento> findAllMedicamento() throws Exception {
        return medicamentoDAO.listar();
    }

    // Medico
    public void createMedico(Medico m) throws Exception {
        if (medicoDAO.buscarPorId(m.getId()) != null)
            throw new Exception("El medico ya existe");
        medicoDAO.guardar(m);
    }

    public Medico readMedico(String id) throws Exception {
        return medicoDAO.buscarPorId(id);
    }

    public Medico readMedicoByNombre(String nombre) throws Exception {
        List<Medico> list = medicoDAO.search(new Medico("", nombre, "", ""));
        return list.isEmpty() ? null : list.get(0);
    }

    public void updateMedico(Medico m) throws Exception {
        medicoDAO.actualizar(m);
    }

    public void deleteMedico(String id) throws Exception {
        medicoDAO.borrar(id);
    }

    public List<Medico> findAllMedico() throws Exception {
        return medicoDAO.listar();
    }

    // Paciente
    public void createPaciente(Paciente p) throws Exception {
        if (pacienteDAO.buscarPorId(p.getId()) != null)
            throw new Exception("Paciente ya existe");
        pacienteDAO.guardar(p);
    }

    public Paciente readPacienteById(String id) throws Exception {
        return pacienteDAO.buscarPorId(id);
    }

    public Paciente readPacienteByNombre(String nombre) throws Exception {
        List<Paciente> list = pacienteDAO.search(new Paciente("", nombre, "", null));
        return list.isEmpty() ? null : list.get(0);
    }

    public void updatePaciente(Paciente p) throws Exception {
        pacienteDAO.actualizar(p);
    }

    public void deletePaciente(String id) throws Exception {
        pacienteDAO.borrar(id);
    }

    public List<Paciente> findAllPaciente() throws Exception {
        return pacienteDAO.listar();
    }

    // Receta
    public void createReceta(Receta r) throws Exception {
        recetaDAO.guardar(r);
    }

    public Receta readRecetaById(String id) throws Exception {
        return recetaDAO.buscarPorId(id);
    }

    public List<Receta> findRecetaByPaciente(String pacienteId) throws Exception {
        List<Receta> all = recetaDAO.listar();
        return all.stream()
                .filter(r -> r.getPacienteId().equalsIgnoreCase(pacienteId))
                .collect(Collectors.toList());
    }

    public List<Receta> findAllReceta() throws Exception {
        return recetaDAO.listar();
    }



    public void cambiarEstadoReceta(String recetaId, EstadoReceta nuevoEstado) throws Exception {
        Receta r = recetaDAO.buscarPorId(recetaId);
        if (r != null) {
            r.setEstado(nuevoEstado);
            recetaDAO.actualizar(r);
        }
    }

    // ItemReceta
    public void createItemReceta(ItemReceta item) throws Exception {
        if (itemRecetaDAO.buscarPorId(item.getItemRecetaId()) != null)
            throw new Exception("El item ya existe");
        itemRecetaDAO.guardar(item);
    }

    public ItemReceta readItemReceta(String id) throws Exception {
        return itemRecetaDAO.buscarPorId(id);
    }

    public void updateItemReceta(ItemReceta item) throws Exception {
        itemRecetaDAO.actualizar(item);
    }

    public void deleteItemReceta(String id) throws Exception {
        itemRecetaDAO.borrar(id);
    }

    public List<ItemReceta> findItemRecetaByReceta(String recetaId) throws Exception {
        return itemRecetaDAO.buscarPorRecetaId(recetaId);
    }

    // Prescripcion
    public void createPrescripcion(Prescripcion p) throws Exception {
        prescripcionDAO.guardar(p);
    }

    public Prescripcion readPrescripcion(int numero) throws Exception {
        return prescripcionDAO.buscarPorNumero(numero);
    }

    public void updatePrescripcion(Prescripcion p) throws Exception {
        prescripcionDAO.actualizar(p);
    }

    public void deletePrescripcion(int numero) throws Exception {
        prescripcionDAO.borrar(numero);
    }

    public List<Prescripcion> findAllPrescripcion() throws Exception {
        return prescripcionDAO.listar();
    }

    public List<Prescripcion> searchPrescripcionByEstado(String estado) throws Exception {
        Prescripcion filtro = new Prescripcion();
        filtro.setEstado(estado);
        return prescripcionDAO.search(filtro);
    }

    // Despacho
    public List<Receta> recetasDisponiblesParaDespacho() throws Exception {
        LocalDate hoy = LocalDate.now();
        List<Receta> all = recetaDAO.listar();
        return all.stream()
                .filter(r -> r.getEstado() == EstadoReceta.CONFECCIONADA)
                .filter(r -> {
                    LocalDate retiro = r.getFechaRetiro();
                    return retiro != null &&
                            !retiro.isBefore(hoy.minusDays(3)) &&
                            !retiro.isAfter(hoy.plusDays(3));
                })
                .collect(Collectors.toList());
    }

    public void iniciarDespacho(String recetaId) throws Exception {
        cambiarEstadoReceta(recetaId, EstadoReceta.EN_PROCESO);
    }

    public void alistarMedicamentos(String recetaId) throws Exception {
        cambiarEstadoReceta(recetaId, EstadoReceta.LISTA);
    }

    public void entregarReceta(String recetaId) throws Exception {
        cambiarEstadoReceta(recetaId, EstadoReceta.ENTREGADA);
    }

    // Usuario-related methods
    public Usuario readUsuarioById(String id) throws Exception {
        return usuarioDAO.buscarPorId(id);
    }

    public boolean verificarClaveUsuario(String id, String clave) throws Exception {
        Usuario u = readUsuarioById(id);
        return u != null && u.verificarClave(clave);
    }

    public void createUsuario(Usuario u) throws Exception {
        if (readUsuarioById(u.getId()) != null)
            throw new Exception("Ya existe un usuario con ese ID");
        usuarioDAO.guardar(u);
    }

    public void updateUsuario(Usuario u) throws Exception {
        usuarioDAO.actualizar(u);
    }

    public void deleteUsuario(String id) throws Exception {
        usuarioDAO.borrar(id);
    }

    public List<Usuario> findAllUsuario() throws Exception {
        return usuarioDAO.listar();
    }

    // Combined registration methods
    public void registrarMedico(Medico m) throws Exception {
        createMedico(m);
        Usuario u = new Usuario(m.getId(), m.getNombre(), m.getId(), "MED");
        createUsuario(u);
    }

    public void registrarFarmaceuta(Farmaceuta f) throws Exception {
        createFarmaceuta(f);
        Usuario u = new Usuario(f.getId(), f.getNombre(), f.getId(), "FAR");
        createUsuario(u);
    }
    public void deleteRecetaById(String recetaId) throws Exception {
        List<ItemReceta> items = itemRecetaDAO.buscarPorRecetaId(recetaId);
        for (ItemReceta item : items) {
            itemRecetaDAO.borrar(item.getItemRecetaId());
        }
        recetaDAO.borrar(recetaId);
    }
    public void resetDatosDePrueba() throws Exception {
        try {
            List<Prescripcion> pres = prescripcionDAO.listar();
            for (Prescripcion p : pres) {
                prescripcionDAO.borrar(p.getNumero());
            }
        } catch (Exception ignored) {}

        try {
            deleteRecetaById("R500");
        } catch (Exception ignored) {}

        try {
            itemRecetaDAO.borrar("IR600");
        } catch (Exception ignored) {}

        try {
            usuarioDAO.borrar("U700");
        } catch (Exception ignored) {}

        try {
            medicamentoDAO.borrar(3001);
        } catch (Exception ignored) {}

        try {
            pacienteDAO.borrar("P200");
        } catch (Exception ignored) {}

        try {
            medicoDAO.borrar("M100");
        } catch (Exception ignored) {}
    }



}