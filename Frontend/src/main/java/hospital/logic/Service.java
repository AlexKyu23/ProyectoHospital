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

    // 🔄 Utilidad común para enviar y recibir
    private <T> T request(int code, Object payload, Class<T> type) {
        synchronized (lock) {
            try {
                out.writeInt(code);
                if (payload != null) out.writeObject(payload);
                out.flush();
                return in.readInt() == Protocol.ERROR_NO_ERROR ? type.cast(in.readObject()) : null;
            } catch (Exception e) {
                System.err.println("❌ Error en operación " + code + ": " + e.getMessage());
                return null;
            }
        }
    }

    private boolean send(int code, Object payload) {
        synchronized (lock) {
            try {
                out.writeInt(code);
                if (payload != null) out.writeObject(payload);
                out.flush();
                return in.readInt() == Protocol.ERROR_NO_ERROR;
            } catch (Exception e) {
                System.err.println("❌ Error en envío " + code + ": " + e.getMessage());
                return false;
            }
        }
    }

    // 🔐 USUARIO
    public boolean login(Usuario u) { return send(Protocol.USUARIO_LOGIN, u); }
    public Usuario readUsuario(String id) { return request(Protocol.USUARIO_READ, id, Usuario.class); }
    public boolean createUsuario(Usuario u) { return send(Protocol.USUARIO_CREATE, u); }
    public boolean updateUsuario(Usuario u) { return send(Protocol.USUARIO_UPDATE, u); }
    public boolean deleteUsuario(String id) { return send(Protocol.USUARIO_DELETE, id); }
    public List<Usuario> findAllUsuario() { return request(Protocol.ADMIN_READ_ALL, null, List.class); }

    // 👨‍⚕️ MÉDICO
    public boolean createMedico(Medico m) { return send(Protocol.MEDICO_CREATE, m); }
    public Medico readMedico(String id) { return request(Protocol.MEDICO_READ, id, Medico.class); }
    public boolean updateMedico(Medico m) { return send(Protocol.MEDICO_UPDATE, m); }
    public boolean deleteMedico(String id) { return send(Protocol.MEDICO_DELETE, id); }
    public List<Medico> findAllMedicos() { return request(Protocol.MEDICO_READ_ALL, null, List.class); }

    // 👩‍🔬 FARMACÉUTICO
    public boolean createFarmaceuta(Farmaceuta f) { return send(Protocol.FARMACEUTA_CREATE, f); }
    public Farmaceuta readFarmaceuta(String id) { return request(Protocol.FARMACEUTA_READ, id, Farmaceuta.class); }
    public boolean updateFarmaceuta(Farmaceuta f) { return send(Protocol.FARMACEUTA_UPDATE, f); }
    public boolean deleteFarmaceuta(String id) { return send(Protocol.FARMACEUTA_DELETE, id); }
    public List<Farmaceuta> findAllFarmaceuta() { return request(Protocol.FARMACEUTA_READ_ALL, null, List.class); }

    // 🧍 PACIENTE
    public boolean createPaciente(Paciente p) { return send(Protocol.PACIENTE_CREATE, p); }
    public Paciente readPaciente(String id) { return request(Protocol.PACIENTE_READ, id, Paciente.class); }
    public boolean updatePaciente(Paciente p) { return send(Protocol.PACIENTE_UPDATE, p); }
    public boolean deletePaciente(String id) { return send(Protocol.PACIENTE_DELETE, id); }
    public List<Paciente> findAllPacientes() { return request(Protocol.PACIENTE_READ_ALL, null, List.class); }

    // 💊 MEDICAMENTO
    public boolean createMedicamento(Medicamento m) { return send(Protocol.MEDICAMENTO_CREATE, m); }
    public Medicamento readMedicamento(int codigo) { return request(Protocol.MEDICAMENTO_READ, codigo, Medicamento.class); }
    public boolean updateMedicamento(Medicamento m) { return send(Protocol.MEDICAMENTO_UPDATE, m); }
    public boolean deleteMedicamento(int codigo) { return send(Protocol.MEDICAMENTO_DELETE, codigo); }
    public List<Medicamento> findAllMedicamentos() { return request(Protocol.MEDICAMENTO_READ_ALL, null, List.class); }

    // 📄 RECETA
    public boolean createReceta(Receta r) { return send(Protocol.RECETA_CREATE, r); }
    public Receta readReceta(String id) { return request(Protocol.RECETA_READ, id, Receta.class); }
    public boolean updateReceta(Receta r) { return send(Protocol.RECETA_UPDATE, r); }
    public boolean deleteReceta(String id) { return send(Protocol.RECETA_DELETE, id); }
    public List<Receta> findAllRecetas() { return request(Protocol.RECETA_READ_ALL, null, List.class); }

    // 📝 ITEMRECETA
    public boolean createItemReceta(ItemReceta ir) { return send(Protocol.ITEMRECETA_CREATE, ir); }
    public List<ItemReceta> findItemsByReceta(String recetaId) { return request(Protocol.ITEMRECETA_READ, recetaId, List.class); }
    // 🔍 Buscar usuarios por criterio


    // 🔍 Buscar médicos por criterio
    public List<Medico> searchMedico(String criterio) {
        return request(Protocol.MEDICO_SEARCH, criterio, List.class);
    }

    // 🔍 Buscar farmaceutas por criterio
    public List<Farmaceuta> searchFarmaceuta(String criterio) {
        return request(Protocol.FARMACEUTA_SEARCH, criterio, List.class);
    }

    // 🔍 Buscar pacientes por criterio
    public List<Paciente> searchPaciente(String criterio) {
        return request(Protocol.PACIENTE_SEARCH, criterio, List.class);
    }

    // 🔍 Buscar medicamentos por filtro
    public List<Medicamento> searchMedicamento(Medicamento filtro) {
        return request(Protocol.MEDICAMENTO_SEARCH, filtro, List.class);
    }

    // 🔍 Buscar recetas por criterio (ID de paciente o médico)
    public List<Receta> searchReceta(String criterio) {
        return request(Protocol.RECETA_SEARCH, criterio, List.class);
    }

    // 🔍 Buscar recetas entre fechas
    public List<Receta> searchRecetasBetween(LocalDate start, LocalDate end) {
        synchronized (lock) {
            try {
                out.writeInt(Protocol.RECETA_SEARCH_BETWEEN);
                out.writeObject(start);
                out.writeObject(end);
                out.flush();
                return in.readInt() == Protocol.ERROR_NO_ERROR ? (List<Receta>) in.readObject() : List.of();
            } catch (Exception e) {
                System.err.println("❌ Error en búsqueda por fechas: " + e.getMessage());
                return List.of();
            }
        }
    }

    // 🔌 DESCONECTAR
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
