package logic;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
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
    }

    public void stop() {
        continuar = false;
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("❌ Error al cerrar socket: " + e.getMessage());
        }
        System.out.println("🔌 Conexión cerrada...");
    }

    public void listen() {
        while (continuar) {
            try {
                int method = is.readInt();
                System.out.println("📥 Operación recibida: " + method);

                switch (method) {

                    case Protocol.USUARIO_LOGIN -> {
                        try {
                            Usuario u = (Usuario) is.readObject();
                            boolean ok = service.verificarClaveUsuario(u.getId(), u.getClave());
                            os.writeInt(ok ? Protocol.ERROR_NO_ERROR : Protocol.ERROR_ERROR);
                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println("❌ Error en USUARIO_LOGIN: " + e.getMessage());
                        }
                    }

                    case Protocol.USUARIO_READ -> {
                        try {
                            String id = (String) is.readObject();
                            Usuario u = service.readUsuarioById(id);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(u);
                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            os.writeObject(null);
                            System.out.println("❌ Error en USUARIO_READ: " + e.getMessage());
                        }
                    }

                    case Protocol.USUARIO_CREATE -> {
                        try {
                            Usuario u = (Usuario) is.readObject();
                            service.createUsuario(u);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println("❌ Error en USUARIO_CREATE: " + e.getMessage());
                        }
                    }

                    case Protocol.USUARIO_UPDATE -> {
                        try {
                            Usuario u = (Usuario) is.readObject();
                            service.updateUsuario(u);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println("❌ Error en USUARIO_UPDATE: " + e.getMessage());
                        }
                    }

                    case Protocol.USUARIO_DELETE -> {
                        try {
                            String id = (String) is.readObject();
                            service.deleteUsuario(id);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println("❌ Error en USUARIO_DELETE: " + e.getMessage());
                        }
                    }

                    case Protocol.MEDICO_CREATE -> {
                        try {
                            Medico m = (Medico) is.readObject();
                            service.createMedico(m);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println("❌ Error en MEDICO_CREATE: " + e.getMessage());
                        }
                    }

                    case Protocol.MEDICO_UPDATE -> {
                        try {
                            Medico m = (Medico) is.readObject();
                            service.updateMedico(m);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println("❌ Error en MEDICO_UPDATE: " + e.getMessage());
                        }
                    }

                    case Protocol.MEDICO_READ -> {
                        try {
                            String id = (String) is.readObject();
                            Medico m = service.readMedico(id);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(m);
                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            os.writeObject(null);
                            System.out.println("❌ Error en MEDICO_READ: " + e.getMessage());
                        }
                    }

                    case Protocol.MEDICO_READ_ALL -> {
                        try {
                            List<Medico> lista = service.findAllMedico();
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lista != null ? lista : new ArrayList<>());
                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            os.writeObject(new ArrayList<>());
                            System.out.println("❌ Error en MEDICO_READ_ALL: " + e.getMessage());
                        }
                    }

                    case Protocol.PACIENTE_READ_ALL -> {
                        try {
                            List<Paciente> lista = service.findAllPaciente();
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lista != null ? lista : new ArrayList<>());
                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            os.writeObject(new ArrayList<>());
                            System.out.println("❌ Error en PACIENTE_READ_ALL: " + e.getMessage());
                        }
                    }

                    case Protocol.MEDICAMENTO_READ_ALL -> {
                        try {
                            List<Medicamento> lista = service.findAllMedicamento();
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lista != null ? lista : new ArrayList<>());
                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            os.writeObject(new ArrayList<>());
                            System.out.println("❌ Error en MEDICAMENTO_READ_ALL: " + e.getMessage());
                        }
                    }

                    case Protocol.RECETA_READ_ALL -> {
                        try {
                            List<Receta> recetas = service.findAllRecetas();
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(recetas != null ? recetas : new ArrayList<>());
                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            os.writeObject(new ArrayList<>());
                            System.out.println("❌ Error en RECETA_READ_ALL: " + e.getMessage());
                        }
                    }

                    case Protocol.DISCONNECT -> {
                        System.out.println("🔌 Cliente solicitó desconexión.");
                        stop();
                        server.remove(this);
                    }

                    default -> {
                        System.out.println("⚠️ Código de operación no reconocido: " + method);
                        os.writeInt(Protocol.ERROR_ERROR);
                    }
                }

                os.flush();

            } catch (Exception e) {
                System.out.println("❌ Error general en operación: " + e.getMessage());
                e.printStackTrace();
                stop();
            }
        }
    }
}

