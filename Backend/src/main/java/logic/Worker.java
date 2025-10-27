package logic;

import java.io.*;
import java.net.Socket;
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
            System.out.println("Error al iniciar streams: " + e.getMessage());
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
            System.out.println("Error al cerrar socket: " + e.getMessage());
        }
        System.out.println("Conexión cerrada...");
    }

    public void listen() {
        while (continuar) {
            try {
                int method = is.readInt();
                System.out.println("Operación recibida: " + method);

                switch (method) {
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
                    case Protocol.MEDICAMENTO_SEARCH -> {
                        Medicamento filtro = (Medicamento) is.readObject();
                        List<Medicamento> lista = service.findAllMedicamento(); // o searchMedicamento si lo agregás
                        os.writeInt(Protocol.ERROR_NO_ERROR);
                        os.writeObject(lista);
                    }
                    case Protocol.USUARIO_LOGIN -> {
                        Usuario u = (Usuario) is.readObject();
                        boolean ok = service.verificarClaveUsuario(u.getId(), u.getClave());
                        os.writeInt(ok ? Protocol.ERROR_NO_ERROR : Protocol.ERROR_ERROR);
                    }
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
                    case Protocol.DISCONNECT -> {
                        stop();
                        server.remove(this);
                    }
                    default -> os.writeInt(Protocol.ERROR_ERROR);
                }

                os.flush();
            } catch (Exception e) {
                System.out.println("Error en operación: " + e.getMessage());
                stop();
            }
        }
    }
}
