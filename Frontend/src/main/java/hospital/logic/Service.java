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

    private Service() {
        try {
            socket = new Socket(Protocol.SERVER, Protocol.PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Conectado al backend en " + Protocol.SERVER + ":" + Protocol.PORT);
        } catch (Exception e) {
            System.err.println("ERROR: No se pudo conectar al backend: " + e.getMessage());
            System.exit(-1);
        }
    }

    // ===================================================================
    // === USUARIO (900–909) ============================================
    // ===================================================================
    public boolean login(Usuario u) throws Exception {
        out.writeInt(Protocol.USUARIO_LOGIN);
        out.writeObject(u);
        out.flush();
        return in.readInt() == Protocol.ERROR_NO_ERROR;
    }

    public void createUsuario(Usuario u) throws Exception {
        send(Protocol.USUARIO_CREATE, u);
    }

    public Usuario readUsuario(String id) throws Exception {
        return receiveObject(Protocol.USUARIO_READ, id, Usuario.class);
    }

    public void updateUsuario(Usuario u) throws Exception {
        send(Protocol.USUARIO_UPDATE, u);
    }

    public void deleteUsuario(String id) throws Exception {
        send(Protocol.USUARIO_DELETE, id);
    }

    public List<Usuario> searchUsuario(String criterio) throws Exception {
        return receiveList(Protocol.USUARIO_READ, criterio);
    }

    public void logout() throws Exception {
        send(Protocol.USUARIO_LOGOUT, null);
    }

    // ===================================================================
    // === MÉDICO (910–919) ============================================
    // ===================================================================
    public void createMedico(Medico m) throws Exception {
        send(Protocol.MEDICO_CREATE, m);
    }

    public Medico readMedico(String id) throws Exception {
        return receiveObject(Protocol.MEDICO_READ, id, Medico.class);
    }

    public void updateMedico(Medico m) throws Exception {
        send(Protocol.MEDICO_UPDATE, m);
    }

    public void deleteMedico(String id) throws Exception {
        send(Protocol.MEDICO_DELETE, id);
    }

    public List<Medico> searchMedico(String criterio) throws Exception {
        return receiveList(Protocol.MEDICO_SEARCH, criterio);
    }

    public List<Medico> findAllMedicos() throws Exception {
        return receiveList(Protocol.MEDICO_READ, null);
    }

    // ===================================================================
    // === FARMACÉUTICO (920–929) ======================================
    // ===================================================================
    public void createFarmaceuta(Farmaceuta f) throws Exception {
        send(Protocol.FARMACEUTA_CREATE, f);
    }

    public Farmaceuta readFarmaceuta(String id) throws Exception {
        return receiveObject(Protocol.FARMACEUTA_READ, id, Farmaceuta.class);
    }

    public void updateFarmaceuta(Farmaceuta f) throws Exception {
        send(Protocol.FARMACEUTA_UPDATE, f);
    }

    public void deleteFarmaceuta(String id) throws Exception {
        send(Protocol.FARMACEUTA_DELETE, id);
    }

    public List<Farmaceuta> searchFarmaceuta(String criterio) throws Exception {
        return receiveList(Protocol.FARMACEUTA_SEARCH, criterio);
    }

    public List<Farmaceuta> findAllFarmaceuta() throws Exception {
        return receiveList(Protocol.FARMACEUTA_READ, null);
    }

    // ===================================================================
    // === PACIENTE (940–949) ==========================================
    // ===================================================================
    public void createPaciente(Paciente p) throws Exception {
        send(Protocol.PACIENTE_CREATE, p);
    }

    public Paciente readPaciente(String id) throws Exception {
        return receiveObject(Protocol.PACIENTE_READ, id, Paciente.class);
    }

    public void updatePaciente(Paciente p) throws Exception {
        send(Protocol.PACIENTE_UPDATE, p);
    }

    public void deletePaciente(String id) throws Exception {
        send(Protocol.PACIENTE_DELETE, id);
    }

    public List<Paciente> searchPaciente(String criterio) throws Exception {
        return receiveList(Protocol.PACIENTE_SEARCH, criterio);
    }

    public List<Paciente> findAllPacientes() throws Exception {
        return receiveList(Protocol.PACIENTE_READ, null);
    }

    // ===================================================================
    // === MEDICAMENTO (950–959) =======================================
    // ===================================================================
    public void createMedicamento(Medicamento m) throws Exception {
        send(Protocol.MEDICAMENTO_CREATE, m);
    }

    public Medicamento readMedicamento(int codigo) throws Exception {
        return receiveObject(Protocol.MEDICAMENTO_READ, codigo, Medicamento.class);
    }

    public void updateMedicamento(Medicamento m) throws Exception {
        send(Protocol.MEDICAMENTO_UPDATE, m);
    }

    public void deleteMedicamento(int codigo) throws Exception {
        send(Protocol.MEDICAMENTO_DELETE, codigo);
    }

    public List<Medicamento> searchMedicamento(Medicamento filtro) throws Exception {
        return receiveList(Protocol.MEDICAMENTO_SEARCH, filtro);
    }

    public List<Medicamento> findAllMedicamentos() throws Exception {
        return receiveList(Protocol.MEDICAMENTO_READ, null);
    }

    // ===================================================================
    // === RECETA (970–979) ===========================================
    // ===================================================================
    public void createReceta(Receta r) throws Exception {
        send(Protocol.RECETA_CREATE, r);
    }

    public Receta readReceta(String id) throws Exception {
        return receiveObject(Protocol.RECETA_READ, id, Receta.class);
    }

    public void updateReceta(Receta r) throws Exception {
        send(Protocol.RECETA_UPDATE, r);
    }

    public void deleteReceta(String id) throws Exception {
        send(Protocol.RECETA_DELETE, id);
    }

    public List<Receta> searchReceta(String criterio) throws Exception {
        return receiveList(Protocol.RECETA_SEARCH, criterio);
    }

    public List<Receta> findRecetasByPaciente(String pacienteId) throws Exception {
        return receiveList(Protocol.RECETA_SEARCH, pacienteId);
    }

    public List<Receta> findRecetasByMedico(String medicoId) throws Exception {
        return receiveList(Protocol.RECETA_SEARCH, medicoId);
    }

    public List<Receta> findRecetasPendientes() throws Exception {
        return receiveList(Protocol.RECETA_READ, "PENDIENTE"); // Asumiendo filtro
    }
    public List<Receta> findAllRecetas() throws Exception {
        out.writeInt(Protocol.RECETA_READ_ALL);
        out.flush();
        if (in.readInt() == Protocol.ERROR_ERROR) {
            String msg = in.readObject() instanceof String s ? s : "Error al listar recetas";
            throw new Exception(msg);
        }
        return (List<Receta>) in.readObject();
    }

    public List<Receta> findRecetasBetween(LocalDate start, LocalDate end) throws Exception {
        out.writeInt(Protocol.RECETA_SEARCH_BETWEEN);
        out.writeObject(start);
        out.writeObject(end);
        out.flush();
        if (in.readInt() == Protocol.ERROR_ERROR) {
            String msg = in.readObject() instanceof String s ? s : "Error al buscar por fecha";
            throw new Exception(msg);
        }
        return (List<Receta>) in.readObject();
    }

    // ===================================================================
    // === ITEMRECETA (960–969) ========================================
    // ===================================================================
    public void createItemReceta(ItemReceta ir) throws Exception {
        send(Protocol.ITEMRECETA_CREATE, ir);
    }

    public List<ItemReceta> findItemsByReceta(String recetaId) throws Exception {
        return receiveList(Protocol.ITEMRECETA_READ, recetaId);
    }

    // ===================================================================
    // === UTILIDADES PRIVADAS =========================================
    // ===================================================================
    private void send(int method, Serializable data) throws Exception {
        out.writeInt(method);
        if (data != null) out.writeObject(data);
        out.flush();
        if (in.readInt() == Protocol.ERROR_ERROR) {
            String msg = in.readObject() instanceof String s ? s : "Error desconocido";
            throw new Exception(msg);
        }
    }

    private <T> T receiveObject(int method, Serializable param, Class<T> type) throws Exception {
        send(method, param);
        return type.cast(in.readObject());
    }

    private <T> List<T> receiveList(int method, Serializable param) throws Exception {
        send(method, param);
        return (List<T>) in.readObject();
    }

    // ===================================================================
    // === DESCONEXIÓN =================================================
    // ===================================================================
    public void stop() {
        try {
            if (out != null) {
                out.writeInt(Protocol.DISCONNECT);
                out.flush();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            System.out.println("Desconectado del backend.");
        } catch (Exception e) {
            System.err.println("Error al desconectar: " + e.getMessage());
        }
    }
}