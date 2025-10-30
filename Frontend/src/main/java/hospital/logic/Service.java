package hospital.logic;

import logic.*;
import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;

public class Service {
    private static Service theInstance;
    public static Service instance() {
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final Object lock = new Object();

    private Service() {
        try {
            socket = new Socket(Protocol.SERVER, Protocol.PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("✅ Conectado al backend en " + Protocol.SERVER + ":" + Protocol.PORT);
        } catch (Exception e) {
            System.err.println("❌ ERROR: No se pudo conectar al backend: " + e.getMessage());
            System.exit(-1);
        }
    }

    // ===================================================================
    // === MÉTODOS HELPER (La magia de la compactación) ================
    // ===================================================================

    // Para operaciones CRUD que no retornan nada
    private void execute(int code, Object data, String errorMsg) throws Exception {
        synchronized (lock) {
            out.writeInt(code);
            if (data != null) out.writeObject(data);
            out.flush();
            if (in.readInt() != Protocol.ERROR_NO_ERROR) throw new Exception(errorMsg);
        }
    }

    // Para operaciones READ que retornan un objeto
    private <T> T fetch(int code, Object param, String errorMsg) throws Exception {
        synchronized (lock) {
            out.writeInt(code);
            if (param != null) out.writeObject(param);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) return (T) in.readObject();
            throw new Exception(errorMsg);
        }
    }

    // Para operaciones SEARCH que retornan lista (no fallan, retornan vacío)
    private <T> List<T> search(int code, Object param) throws Exception {
        synchronized (lock) {
            out.writeInt(code);
            if (param != null) out.writeObject(param);
            out.flush();
            return in.readInt() == Protocol.ERROR_NO_ERROR ? (List<T>) in.readObject() : List.of();
        }
    }

    // ===================================================================
    // === USUARIO =======================================================
    // ===================================================================
    public boolean login(Usuario u) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.USUARIO_LOGIN);
            out.writeObject(u);
            out.flush();
            return in.readInt() == Protocol.ERROR_NO_ERROR;
        }
    }
    public void createUsuario(Usuario u) throws Exception { execute(Protocol.USUARIO_CREATE, u, "USUARIO DUPLICADO"); }
    public Usuario readUsuario(String id) throws Exception { return fetch(Protocol.USUARIO_READ, id, "USUARIO NO EXISTE"); }
    public void updateUsuario(Usuario u) throws Exception { execute(Protocol.USUARIO_UPDATE, u, "USUARIO NO EXISTE"); }
    public void deleteUsuario(String id) throws Exception { execute(Protocol.USUARIO_DELETE, id, "USUARIO NO EXISTE"); }
    public void logout() throws Exception { execute(Protocol.USUARIO_LOGOUT, null, "ERROR AL LOGOUT"); }

    // ===================================================================
    // === MÉDICO ========================================================
    // ===================================================================
    public void createMedico(Medico m) throws Exception { execute(Protocol.MEDICO_CREATE, m, "MÉDICO DUPLICADO"); }
    public Medico readMedico(String id) throws Exception { return fetch(Protocol.MEDICO_READ, id, "MÉDICO NO EXISTE"); }
    public void updateMedico(Medico m) throws Exception { execute(Protocol.MEDICO_UPDATE, m, "MÉDICO NO EXISTE"); }
    public void deleteMedico(String id) throws Exception { execute(Protocol.MEDICO_DELETE, id, "MÉDICO NO EXISTE"); }
    public List<Medico> searchMedico(String criterio) throws Exception { return search(Protocol.MEDICO_SEARCH, criterio); }
    public List<Medico> findAllMedicos() throws Exception { return search(Protocol.MEDICO_READ_ALL, null); }

    // ===================================================================
    // === FARMACÉUTICO ==================================================
    // ===================================================================
    public void createFarmaceuta(Farmaceuta f) throws Exception { execute(Protocol.FARMACEUTA_CREATE, f, "FARMACÉUTICO DUPLICADO"); }
    public Farmaceuta readFarmaceuta(String id) throws Exception { return fetch(Protocol.FARMACEUTA_READ, id, "FARMACÉUTICO NO EXISTE"); }
    public void updateFarmaceuta(Farmaceuta f) throws Exception { execute(Protocol.FARMACEUTA_UPDATE, f, "FARMACÉUTICO NO EXISTE"); }
    public void deleteFarmaceuta(String id) throws Exception { execute(Protocol.FARMACEUTA_DELETE, id, "FARMACÉUTICO NO EXISTE"); }
    public List<Farmaceuta> searchFarmaceuta(String criterio) throws Exception { return search(Protocol.FARMACEUTA_SEARCH, criterio); }
    public List<Farmaceuta> findAllFarmaceuta() throws Exception { return search(Protocol.FARMACEUTA_READ_ALL, null); }

    // ===================================================================
    // === PACIENTE ======================================================
    // ===================================================================
    public void createPaciente(Paciente p) throws Exception { execute(Protocol.PACIENTE_CREATE, p, "PACIENTE DUPLICADO"); }
    public Paciente readPaciente(String id) throws Exception { return fetch(Protocol.PACIENTE_READ, id, "PACIENTE NO EXISTE"); }
    public void updatePaciente(Paciente p) throws Exception { execute(Protocol.PACIENTE_UPDATE, p, "PACIENTE NO EXISTE"); }
    public void deletePaciente(String id) throws Exception { execute(Protocol.PACIENTE_DELETE, id, "PACIENTE NO EXISTE"); }
    public List<Paciente> searchPaciente(String criterio) throws Exception { return search(Protocol.PACIENTE_SEARCH, criterio); }
    public List<Paciente> findAllPacientes() throws Exception { return search(Protocol.PACIENTE_READ_ALL, null); }

    // ===================================================================
    // === MEDICAMENTO (✅ CODIGO COMO STRING) ==========================
    // ===================================================================
    public void createMedicamento(Medicamento m) throws Exception { execute(Protocol.MEDICAMENTO_CREATE, m, "MEDICAMENTO DUPLICADO"); }
    public Medicamento readMedicamento(String codigo) throws Exception { return fetch(Protocol.MEDICAMENTO_READ, codigo, "MEDICAMENTO NO EXISTE"); }
    public void updateMedicamento(Medicamento m) throws Exception { execute(Protocol.MEDICAMENTO_UPDATE, m, "MEDICAMENTO NO EXISTE"); }
    public void deleteMedicamento(String codigo) throws Exception { execute(Protocol.MEDICAMENTO_DELETE, codigo, "MEDICAMENTO NO EXISTE"); }
    public List<Medicamento> searchMedicamento(Medicamento filtro) throws Exception { return search(Protocol.MEDICAMENTO_SEARCH, filtro); }
    public List<Medicamento> findAllMedicamentos() throws Exception { return search(Protocol.MEDICAMENTO_READ_ALL, null); }

    // ===================================================================
    // === RECETA ========================================================
    // ===================================================================
    public void createReceta(Receta r) throws Exception { execute(Protocol.RECETA_CREATE, r, "RECETA DUPLICADA"); }
    public Receta readReceta(String id) throws Exception { return fetch(Protocol.RECETA_READ, id, "RECETA NO EXISTE"); }
    public void updateReceta(Receta r) throws Exception { execute(Protocol.RECETA_UPDATE, r, "RECETA NO EXISTE"); }
    public void deleteReceta(String id) throws Exception { execute(Protocol.RECETA_DELETE, id, "RECETA NO EXISTE"); }
    public List<Receta> searchReceta(String criterio) throws Exception { return search(Protocol.RECETA_SEARCH, criterio); }
    public List<Receta> findAllRecetas() throws Exception { return search(Protocol.RECETA_READ_ALL, null); }

    public List<Receta> findRecetasBetween(LocalDate start, LocalDate end) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.RECETA_SEARCH_BETWEEN);
            out.writeObject(start);
            out.writeObject(end);
            out.flush();
            return in.readInt() == Protocol.ERROR_NO_ERROR ? (List<Receta>) in.readObject() : List.of();
        }
    }

    // ===================================================================
    // === ITEMRECETA ====================================================
    // ===================================================================
    public void createItemReceta(ItemReceta ir) throws Exception { execute(Protocol.ITEMRECETA_CREATE, ir, "ITEM DUPLICADO"); }
    public List<ItemReceta> findItemsByReceta(String recetaId) throws Exception { return search(Protocol.ITEMRECETA_READ, recetaId); }

    // ===================================================================
    // === DESCONEXIÓN ===================================================
    // ===================================================================
    public void stop() {
        synchronized (lock) {
            try {
                out.writeInt(Protocol.DISCONNECT);
                out.flush();
                socket.shutdownOutput();
                socket.close();
                System.out.println("✅ Desconectado del backend.");
            } catch (Exception e) {
                System.err.println("❌ Error al desconectar: " + e.getMessage());
            }
        }
    }
}