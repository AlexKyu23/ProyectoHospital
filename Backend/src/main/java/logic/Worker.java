package logic;

import java.io.*;
import java.net.Socket;


import java.io.*;

import java.util.ArrayList;
import java.util.List;

public class Worker {
    private final Server server;
    private final Socket socket;
    private final Service service;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    private boolean continuar;

    public Worker(Server server, Socket socket, Service service) {
        this.server = server;
        this.socket = socket;
        this.service = service;
        try {
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("❌ Error al iniciar streams: " + e.getMessage());
        }
    }

    public void start() {
        continuar = true;
        new Thread(this::listen).start();
        System.out.println("🧠 Worker iniciado para cliente: " + socket.getInetAddress());
    }

    public void stop() {
        continuar = false;
        try { socket.close(); } catch (IOException ignored) {}
        System.out.println("🔌 Conexión finalizada.");
    }

    // ---------------------------
    // Métodos auxiliares
    // ---------------------------
    private void sendOk() throws IOException { os.writeInt(Protocol.ERROR_NO_ERROR); }
    private void sendError() throws IOException { os.writeInt(Protocol.ERROR_ERROR); }

    @SuppressWarnings("unchecked")
    public void listen() {
        while (continuar) {
            try {
                int method = is.readInt();
                System.out.println("📥 Operación recibida: " + method);

                switch (method) {

                    // ======================================================
                    // === USUARIO ==========================================
                    // ======================================================
                    case Protocol.USUARIO_CREATE -> handleSimpleOp(() ->
                            service.create((Usuario) is.readObject()));

                    case Protocol.USUARIO_READ -> handleReturnOp(() ->
                            service.readUsuario((String) is.readObject()));

                    case Protocol.USUARIO_UPDATE -> handleSimpleOp(() ->
                            service.update((Usuario) is.readObject()));

                    case Protocol.USUARIO_DELETE -> handleSimpleOp(() ->
                            service.deleteUsuario((String) is.readObject()));

                    case Protocol.USUARIO_SEARCH -> handleListOp(() ->
                            service.searchUsuario((Usuario) is.readObject()));

                    case Protocol.USUARIO_LOGIN -> {
                        try {
                            Usuario u = (Usuario) is.readObject();
                            Usuario real = service.readUsuario(u.getId());
                            if (real != null && real.getClave().equals(u.getClave())) {
                                os.writeInt(Protocol.ERROR_NO_ERROR);
                                os.writeObject(real); // enviamos el usuario válido
                            } else {
                                os.writeInt(Protocol.ERROR_ERROR);
                            }
                        } catch (Exception e) {
                            sendError();
                        }}


                    // ======================================================
                    // === MÉDICO ===========================================
                    // ======================================================
                    case Protocol.MEDICO_CREATE -> handleSimpleOp(() ->
                            service.create((Medico) is.readObject()));

                    case Protocol.MEDICO_READ -> handleReturnOp(() ->
                            service.readMedico((String) is.readObject()));

                    case Protocol.MEDICO_UPDATE -> handleSimpleOp(() ->
                            service.update((Medico) is.readObject()));

                    case Protocol.MEDICO_DELETE -> handleSimpleOp(() ->
                            service.deleteMedico((String) is.readObject()));

                    case Protocol.MEDICO_SEARCH -> handleListOp(() ->
                            service.searchMedico((Medico) is.readObject()));

                    // ======================================================
                    // === PACIENTE =========================================
                    // ======================================================
                    case Protocol.PACIENTE_CREATE -> handleSimpleOp(() ->
                            service.create((Paciente) is.readObject()));

                    case Protocol.PACIENTE_READ -> handleReturnOp(() ->
                            service.readPaciente((String) is.readObject()));

                    case Protocol.PACIENTE_UPDATE -> handleSimpleOp(() ->
                            service.update((Paciente) is.readObject()));

                    case Protocol.PACIENTE_DELETE -> handleSimpleOp(() ->
                            service.deletePaciente((String) is.readObject()));

                    case Protocol.PACIENTE_SEARCH -> handleListOp(() ->
                            service.searchPaciente((Paciente) is.readObject()));

                    // ======================================================
                    // === MEDICAMENTO ======================================
                    // ======================================================
                    case Protocol.MEDICAMENTO_CREATE -> handleSimpleOp(() ->
                            service.create((Medicamento) is.readObject()));

                    case Protocol.MEDICAMENTO_READ -> handleReturnOp(() ->
                            service.readMedicamento((String) is.readObject()));

                    case Protocol.MEDICAMENTO_UPDATE -> handleSimpleOp(() ->
                            service.update((Medicamento) is.readObject()));

                    case Protocol.MEDICAMENTO_DELETE -> handleSimpleOp(() ->
                            service.deleteMedicamento((String) is.readObject()));

                    case Protocol.MEDICAMENTO_SEARCH -> handleListOp(() ->
                            service.searchMedicamento((Medicamento) is.readObject()));

                    // ======================================================
                    // === RECETA ===========================================
                    // ======================================================
                    case Protocol.RECETA_CREATE -> handleSimpleOp(() ->
                            service.create((Receta) is.readObject()));

                    case Protocol.RECETA_READ -> handleReturnOp(() ->
                            service.readReceta((String) is.readObject()));

                    case Protocol.RECETA_UPDATE -> handleSimpleOp(() ->
                            service.update((Receta) is.readObject()));

                    case Protocol.RECETA_DELETE -> handleSimpleOp(() ->
                            service.deleteReceta((String) is.readObject()));

                    case Protocol.RECETA_SEARCH -> handleListOp(service::searchReceta);

                    // ======================================================
                    // === ITEM RECETA / PRESCRIPCIÓN =======================
                    // ======================================================
                    case Protocol.ITEMRECETA_CREATE -> handleSimpleOp(() ->
                            service.create((ItemReceta) is.readObject()));

                    case Protocol.ITEMRECETA_SEARCH -> handleListOp(() ->
                            service.buscarItemsPorReceta((String) is.readObject()));

                    case Protocol.PRESCRIPCION_CREATE -> handleSimpleOp(() ->
                            service.create((Prescripcion) is.readObject()));

                    case Protocol.PRESCRIPCION_SEARCH -> handleListOp(() ->
                            service.searchPrescripcion((Prescripcion) is.readObject()));

                    // ======================================================
                    // === ADMIN / FARMACEUTA ===============================
                    // ======================================================
                    case Protocol.ADMIN_CREATE -> handleSimpleOp(() ->
                            service.create((Admin) is.readObject()));

                    case Protocol.ADMIN_READ -> handleReturnOp(() ->
                            service.readAdmin((String) is.readObject()));

                    case Protocol.FARMACEUTA_CREATE -> handleSimpleOp(() ->
                            service.create((Farmaceuta) is.readObject()));

                    case Protocol.FARMACEUTA_READ -> handleReturnOp(() ->
                            service.readFarmaceuta((String) is.readObject()));

                    // ======================================================
                    // === DESCONECTAR ======================================
                    // ======================================================
                    case Protocol.DISCONNECT -> {
                        System.out.println("👋 Cliente desconectado.");
                        stop();
                        server.remove(this);
                    }

                    default -> {
                        System.out.println("⚠️ Operación no reconocida: " + method);
                        sendError();
                    }
                }

                os.flush();

            } catch (IOException e) {
                System.out.println("⚠️ Cliente desconectado: " + e.getMessage());
                stop();
            } catch (Exception e) {
                System.out.println("❌ Error general en Worker: " + e.getMessage());
                stop();
            }
        }
    }

    // ============================================================
    // === Handlers auxiliares genéricos ==========================
    // ============================================================

    private void handleSimpleOp(IOAction action) {
        try {
            action.run();
            sendOk();
        } catch (Exception e) {
            try { sendError(); } catch (IOException ignored) {}
            System.out.println("❌ Error en operación simple: " + e.getMessage());
        }
    }

    private void handleReturnOp(IOSupplier<?> supplier) {
        try {
            Object result = supplier.get();
            sendOk();
            os.writeObject(result);
        } catch (Exception e) {
            try {
                sendError();
                os.writeObject(null);
            } catch (IOException ignored) {}
            System.out.println("❌ Error en operación de lectura: " + e.getMessage());
        }
    }

    private void handleListOp(IOSupplier<List<?>> supplier) {
        try {
            List<?> result = supplier.get();
            sendOk();
            os.writeObject(result != null ? result : new ArrayList<>());
        } catch (Exception e) {
            try {
                sendError();
                os.writeObject(new ArrayList<>());
            } catch (IOException ignored) {}
            System.out.println("❌ Error en operación de lista: " + e.getMessage());
        }
    }

    // ============================================================
    // === Functional interfaces internas =========================
    // ============================================================
    @FunctionalInterface private interface IOAction { void run() throws Exception; }
    @FunctionalInterface private interface IOSupplier<T> { T get() throws Exception; }
}
