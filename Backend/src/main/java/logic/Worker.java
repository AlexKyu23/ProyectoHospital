package logic;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
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

                    // 🔐 USUARIO
                    case Protocol.USUARIO_LOGIN -> {
                        Usuario u = (Usuario) is.readObject();
                        System.out.println("🔐 Login solicitado para ID: " + u.getId());
                        boolean ok = service.verificarClaveUsuario(u.getId(), u.getClave());
                        os.writeInt(ok ? Protocol.ERROR_NO_ERROR : Protocol.ERROR_ERROR);
                    }

                    case Protocol.USUARIO_READ -> {
                        String id = (String) is.readObject();
                        System.out.println("🔍 USUARIO_READ solicitado para ID: " + id);
                        Usuario u = service.readUsuarioById(id);
                        os.writeInt(Protocol.ERROR_NO_ERROR);
                        os.writeObject(u);
                        System.out.println("📤 Usuario enviado: " + (u != null ? u.getNombre() : "null"));
                    }

                    case Protocol.USUARIO_CREATE -> {
                        Usuario u = (Usuario) is.readObject();
                        service.createUsuario(u);
                        os.writeInt(Protocol.ERROR_NO_ERROR);
                    }

                    case Protocol.USUARIO_UPDATE -> {
                        Usuario u = (Usuario) is.readObject();
                        service.updateUsuario(u);
                        os.writeInt(Protocol.ERROR_NO_ERROR);
                    }

                    case Protocol.USUARIO_DELETE -> {
                        String id = (String) is.readObject();
                        service.deleteUsuario(id);
                        os.writeInt(Protocol.ERROR_NO_ERROR);
                    }

                    // 👨‍⚕️ MÉDICO
                    case Protocol.MEDICO_CREATE -> {
                        Medico m = (Medico) is.readObject();
                        service.createMedico(m);
                        os.writeInt(Protocol.ERROR_NO_ERROR);
                    }

                    case Protocol.MEDICO_READ -> {
                        String id = (String) is.readObject();
                        Medico m = service.readMedico(id);
                        os.writeInt(Protocol.ERROR_NO_ERROR);
                        os.writeObject(m);
                    }

                    case Protocol.MEDICO_READ_ALL -> {
                        System.out.println("📥 Solicitud recibida: MEDICO_READ_ALL");
                        List<Medico> lista = service.findAllMedico();
                        os.writeInt(Protocol.ERROR_NO_ERROR);
                        os.writeObject(lista);
                    }

                    // 👩‍🔬 FARMACÉUTICO
                    case Protocol.FARMACEUTA_READ_ALL -> {
                        System.out.println("📥 Solicitud recibida: FARMACEUTA_READ_ALL");
                        List<Farmaceuta> lista = service.findAllFarmaceuta();
                        os.writeInt(Protocol.ERROR_NO_ERROR);
                        os.writeObject(lista);
                    }

                    // 🧑‍💼 ADMIN
                    case Protocol.ADMIN_READ_ALL -> {
                        System.out.println("📥 Solicitud recibida: ADMIN_READ_ALL");
                        List<Admin> lista = service.findAllAdmin();
                        os.writeInt(Protocol.ERROR_NO_ERROR);
                        os.writeObject(lista);
                    }

                    // 🧍 PACIENTE
                    case Protocol.PACIENTE_CREATE -> {
                        Paciente p = (Paciente) is.readObject();
                        service.createPaciente(p);
                        os.writeInt(Protocol.ERROR_NO_ERROR);
                    }

                    case Protocol.PACIENTE_READ -> {
                        String id = (String) is.readObject();
                        Paciente p = service.readPacienteById(id);
                        os.writeInt(Protocol.ERROR_NO_ERROR);
                        os.writeObject(p);
                    }

                    case Protocol.PACIENTE_READ_ALL -> {
                        System.out.println("📥 Solicitud recibida: PACIENTE_READ_ALL");
                        List<Paciente> lista = service.findAllPaciente();
                        os.writeInt(Protocol.ERROR_NO_ERROR);
                        os.writeObject(lista);
                    }

                    // 💊 MEDICAMENTO
                    case Protocol.MEDICAMENTO_SEARCH -> {
                        Medicamento filtro = (Medicamento) is.readObject();
                        List<Medicamento> lista = service.findAllMedicamento(); // o searchMedicamento si lo agregás
                        os.writeInt(Protocol.ERROR_NO_ERROR);
                        os.writeObject(lista);
                    }

                    case Protocol.MEDICAMENTO_READ_ALL -> {
                        System.out.println("📥 Solicitud recibida: MEDICAMENTO_READ_ALL");
                        List<Medicamento> lista = service.findAllMedicamento();
                        os.writeInt(Protocol.ERROR_NO_ERROR);
                        os.writeObject(lista);
                    }

                    // 📄 RECETA
                    case Protocol.RECETA_CREATE -> {
                        Receta r = (Receta) is.readObject();
                        service.createReceta(r);
                        os.writeInt(Protocol.ERROR_NO_ERROR);
                    }

                    case Protocol.RECETA_READ -> {
                        String id = (String) is.readObject();
                        Receta r = service.readRecetaById(id);
                        os.writeInt(Protocol.ERROR_NO_ERROR);
                        os.writeObject(r);
                    }

                    case Protocol.RECETA_SEARCH -> {
                        String pacienteId = (String) is.readObject();
                        List<Receta> recetas = service.findRecetaByPaciente(pacienteId);
                        os.writeInt(Protocol.ERROR_NO_ERROR);
                        os.writeObject(recetas);
                    }

                    case Protocol.RECETA_READ_ALL -> {
                        List<Receta> recetas = service.findAllRecetas();
                        os.writeInt(Protocol.ERROR_NO_ERROR);
                        os.writeObject(recetas);
                    }

                    case Protocol.RECETA_SEARCH_BETWEEN -> {
                        LocalDate start = (LocalDate) is.readObject();
                        LocalDate end = (LocalDate) is.readObject();
                        List<Receta> recetas = service.findRecetasBetween(start, end);
                        os.writeInt(Protocol.ERROR_NO_ERROR);
                        os.writeObject(recetas);
                    }

                    // 🔌 DESCONECTAR
                    case Protocol.DISCONNECT -> {
                        System.out.println("🔌 Cliente solicitó desconexión.");
                        stop();
                        server.remove(this);
                    }

                    // ❌ DEFAULT
                    default -> {
                        System.out.println("⚠️ Código de operación no reconocido: " + method);
                        os.writeInt(Protocol.ERROR_ERROR);
                    }
                }

                os.flush();

            } catch (Exception e) {
                System.out.println("❌ Error en operación: " + e.getMessage());
                e.printStackTrace();
                stop();
            }
        }
    }
}
