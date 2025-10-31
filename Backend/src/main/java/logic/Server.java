package logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<>());

    public Server() {

        try {
            serverSocket = new ServerSocket(Protocol.PORT);
            System.out.println("Servidor iniciado en puerto " + Protocol.PORT);
        } catch (IOException e) {
            System.out.println("Error al iniciar servidor: " + e.getMessage());
        }
    }

    public void run() {
        if (serverSocket == null) {
            System.out.println("❌ El servidor no se pudo iniciar. Abortando ejecución.");
            return;
        }

        Service service = Service.instance();
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Conexión aceptada...");
                Worker worker = new Worker(this, socket, service);
                workers.add(worker);
                System.out.println("Clientes conectados: " + workers.size());
                worker.start();
            } catch (IOException e) {
                System.out.println("Error al aceptar conexión: " + e.getMessage());
            }
        }
    }


    public void remove(Worker w) {
        workers.remove(w);
        System.out.println("Clientes conectados: " + workers.size());
    }
    public void stop() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("🔌 ServerSocket cerrado correctamente.");
            }
        } catch (IOException e) {
            System.out.println("⚠️ Error al cerrar ServerSocket: " + e.getMessage());
        }
    }

    public void join(Socket as, ObjectOutputStream aos, ObjectInputStream ais, String sid){
        for(Worker w:workers){
            if(w.sessionId.equals(sid)){
                w.setAs(as, aos, ais);
                break;
            }
        }
    }

    public void deliver_message(Worker from, Usuario usuario, String message){
        for(Worker w:workers){
            if (w!=from) w.deliver_message(usuario,message);
        }
    }

    public void deliver_login(Worker from, Usuario usuario){
        for(Worker w:workers){
            if(w!=from) w.deliver_login(usuario);
        }
    }

    public void deliver_logout(Worker from, Usuario usuario){
        for(Worker w:workers){
            if(w!=from) w.deliver_logout(usuario);
        }
    }

}
