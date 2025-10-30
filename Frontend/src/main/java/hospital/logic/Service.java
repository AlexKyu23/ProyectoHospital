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
    // === USUARIO (900–909) ============================================
    // ===================================================================
    public boolean login(Usuario u) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.USUARIO_LOGIN);
            out.writeObject(u);
            out.flush();
            return in.readInt() == Protocol.ERROR_NO_ERROR;
        }
    }

    public void createUsuario(Usuario u) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.USUARIO_CREATE);
            out.writeObject(u);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {}
            else throw new Exception("USUARIO DUPLICADO");
        }
    }

    public Usuario readUsuario(String id) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.USUARIO_READ);
            out.writeObject(id);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) return (Usuario) in.readObject();
            else throw new Exception("USUARIO NO EXISTE");
        }
    }

    public void updateUsuario(Usuario u) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.USUARIO_UPDATE);
            out.writeObject(u);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {}
            else throw new Exception("USUARIO NO EXISTE");
        }
    }

    public void deleteUsuario(String id) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.USUARIO_DELETE);
            out.writeObject(id);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {}
            else throw new Exception("USUARIO NO EXISTE");
        }
    }

    public void logout() throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.USUARIO_LOGOUT);
            out.writeObject(null);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {}
            else throw new Exception("ERROR AL LOGOUT");
        }
    }

    // ===================================================================
    // === MÉDICO (910–919) ============================================
    // ===================================================================
    public void createMedico(Medico m) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.MEDICO_CREATE);
            out.writeObject(m);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {}
            else throw new Exception("MÉDICO DUPLICADO");
        }
    }

    public Medico readMedico(String id) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.MEDICO_READ);
            out.writeObject(id);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) return (Medico) in.readObject();
            else throw new Exception("MÉDICO NO EXISTE");
        }
    }

    public void updateMedico(Medico m) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.MEDICO_UPDATE);
            out.writeObject(m);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {}
            else throw new Exception("MÉDICO NO EXISTE");
        }
    }

    public void deleteMedico(String id) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.MEDICO_DELETE);
            out.writeObject(id);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {}
            else throw new Exception("MÉDICO NO EXISTE");
        }
    }

    public List<Medico> searchMedico(String criterio) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.MEDICO_SEARCH);
            out.writeObject(criterio);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Medico>) in.readObject();
            }
            else return List.of();
        }
    }

    public List<Medico> findAllMedicos() throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.MEDICO_READ_ALL);
            out.writeObject(null);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Medico>) in.readObject();
            }
            else return List.of();
        }
    }

    // ===================================================================
    // === FARMACÉUTICO (920–929) ======================================
    // ===================================================================
    public void createFarmaceuta(Farmaceuta f) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.FARMACEUTA_CREATE);
            out.writeObject(f);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {}
            else throw new Exception("FARMACÉUTICO DUPLICADO");
        }
    }

    public Farmaceuta readFarmaceuta(String id) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.FARMACEUTA_READ);
            out.writeObject(id);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) return (Farmaceuta) in.readObject();
            else throw new Exception("FARMACÉUTICO NO EXISTE");
        }
    }

    public void updateFarmaceuta(Farmaceuta f) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.FARMACEUTA_UPDATE);
            out.writeObject(f);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {}
            else throw new Exception("FARMACÉUTICO NO EXISTE");
        }
    }

    public void deleteFarmaceuta(String id) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.FARMACEUTA_DELETE);
            out.writeObject(id);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {}
            else throw new Exception("FARMACÉUTICO NO EXISTE");
        }
    }

    public List<Farmaceuta> searchFarmaceuta(String criterio) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.FARMACEUTA_SEARCH);
            out.writeObject(criterio);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Farmaceuta>) in.readObject();
            }
            else return List.of();
        }
    }

    public List<Farmaceuta> findAllFarmaceuta() throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.FARMACEUTA_READ_ALL);
            out.writeObject(null);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Farmaceuta>) in.readObject();
            }
            else return List.of();
        }
    }

    // ===================================================================
    // === PACIENTE (940–949) ==========================================
    // ===================================================================
    public void createPaciente(Paciente p) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.PACIENTE_CREATE);
            out.writeObject(p);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {}
            else throw new Exception("PACIENTE DUPLICADO");
        }
    }

    public Paciente readPaciente(String id) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.PACIENTE_READ);
            out.writeObject(id);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) return (Paciente) in.readObject();
            else throw new Exception("PACIENTE NO EXISTE");
        }
    }

    public void updatePaciente(Paciente p) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.PACIENTE_UPDATE);
            out.writeObject(p);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {}
            else throw new Exception("PACIENTE NO EXISTE");
        }
    }

    public void deletePaciente(String id) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.PACIENTE_DELETE);
            out.writeObject(id);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {}
            else throw new Exception("PACIENTE NO EXISTE");
        }
    }

    public List<Paciente> searchPaciente(String criterio) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.PACIENTE_SEARCH);
            out.writeObject(criterio);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Paciente>) in.readObject();
            }
            else return List.of();
        }
    }

    public List<Paciente> findAllPacientes() throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.PACIENTE_READ_ALL);
            out.writeObject(null);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Paciente>) in.readObject();
            }
            else return List.of();
        }
    }

    // ===================================================================
    // === MEDICAMENTO (950–959) ✅ CODIGO AHORA ES STRING =============
    // ===================================================================
    public void createMedicamento(Medicamento m) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.MEDICAMENTO_CREATE);
            out.writeObject(m);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {}
            else throw new Exception("MEDICAMENTO DUPLICADO");
        }
    }

    public Medicamento readMedicamento(String codigo) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.MEDICAMENTO_READ);
            out.writeObject(codigo); // ✅ String
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) return (Medicamento) in.readObject();
            else throw new Exception("MEDICAMENTO NO EXISTE");
        }
    }

    public void updateMedicamento(Medicamento m) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.MEDICAMENTO_UPDATE);
            out.writeObject(m);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {}
            else throw new Exception("MEDICAMENTO NO EXISTE");
        }
    }

    public void deleteMedicamento(String codigo) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.MEDICAMENTO_DELETE);
            out.writeObject(codigo); // ✅ String
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {}
            else throw new Exception("MEDICAMENTO NO EXISTE");
        }
    }

    public List<Medicamento> searchMedicamento(Medicamento filtro) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.MEDICAMENTO_SEARCH);
            out.writeObject(filtro);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Medicamento>) in.readObject();
            }
            else return List.of();
        }
    }

    public List<Medicamento> findAllMedicamentos() throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.MEDICAMENTO_READ_ALL);
            out.writeObject(null);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Medicamento>) in.readObject();
            }
            else return List.of();
        }
    }

    // ===================================================================
    // === RECETA (970–979) ===========================================
    // ===================================================================
    public void createReceta(Receta r) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.RECETA_CREATE);
            out.writeObject(r);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {}
            else throw new Exception("RECETA DUPLICADA");
        }
    }

    public Receta readReceta(String id) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.RECETA_READ);
            out.writeObject(id);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) return (Receta) in.readObject();
            else throw new Exception("RECETA NO EXISTE");
        }
    }

    public void updateReceta(Receta r) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.RECETA_UPDATE);
            out.writeObject(r);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {}
            else throw new Exception("RECETA NO EXISTE");
        }
    }

    public void deleteReceta(String id) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.RECETA_DELETE);
            out.writeObject(id);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {}
            else throw new Exception("RECETA NO EXISTE");
        }
    }

    public List<Receta> searchReceta(String criterio) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.RECETA_SEARCH);
            out.writeObject(criterio);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Receta>) in.readObject();
            }
            else return List.of();
        }
    }

    public List<Receta> findAllRecetas() throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.RECETA_READ_ALL);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Receta>) in.readObject();
            }
            else throw new Exception("Error al listar recetas");
        }
    }

    public List<Receta> findRecetasBetween(LocalDate start, LocalDate end) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.RECETA_SEARCH_BETWEEN);
            out.writeObject(start);
            out.writeObject(end);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Receta>) in.readObject();
            }
            else throw new Exception("Error al buscar por fecha");
        }
    }

    // ===================================================================
    // === ITEMRECETA (960–969) ========================================
    // ===================================================================
    public void createItemReceta(ItemReceta ir) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.ITEMRECETA_CREATE);
            out.writeObject(ir);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {}
            else throw new Exception("ITEM DUPLICADO");
        }
    }

    public List<ItemReceta> findItemsByReceta(String recetaId) throws Exception {
        synchronized (lock) {
            out.writeInt(Protocol.ITEMRECETA_READ);
            out.writeObject(recetaId);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<ItemReceta>) in.readObject();
            }
            else return List.of();
        }
    }

    // ===================================================================
    // === DESCONEXIÓN =================================================
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