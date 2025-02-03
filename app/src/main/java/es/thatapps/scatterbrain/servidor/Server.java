package es.thatapps.scatterbrain.servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Server {

    private static final int NUM_MAX_JUGADORES = 4;
    private static final int PUERTO = 53296;
    private ArrayList<Socket> socketClientes;
    private static Semaphore semaphore = new Semaphore(NUM_MAX_JUGADORES);
    private static List<ClientHandler> players = Collections.synchronizedList(new ArrayList<>());

    // Metodo para generar un codigo de sala aleatorio
    public static String generarCodigoSala() {
        SecureRandom random = new SecureRandom();
        int codigoSala = random.nextInt(10000); // Genera un número entre 0 y 999999
        return String.format("%05d", codigoSala); // Asegura que siempre tenga 5 dígitos
    }


    public void abrirServidor() {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO,50, InetAddress.getByName("0.0.0.0"))) {
            System.out.println("Servidor abierto en el puerto " + PUERTO);

            // Acepta la conexion de jugadores
            while (true) {
                Socket socket = serverSocket.accept();

                // Agrega el jugador al servidor controlado por un hilo
                if(semaphore.tryAcquire()) {
                    System.out.println("Jugador conectado: " + socket.getInetAddress());

                    // Crear un nuevo hilo para manejar el cliente
                    ClientHandler player = new ClientHandler(socket);
                    players.add(player);
                    new Thread(player).start();
                } else {
                    // Si ya hay 4 jugadores, no se puede agregar otro
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    out.writeUTF("El servidor esta lleno");
                    socket.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Clase que maneja la comunicacion con los jugadores
    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private DataInputStream din;
        private DataOutputStream dout;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                // Crea flujos de entrada y salida
                din = new DataInputStream(clientSocket.getInputStream());
                dout = new DataOutputStream(clientSocket.getOutputStream());

                // Muestra un mensaje de bienvenida al jugador
                dout.writeUTF("Bienvenido al servidor");
                System.out.println("Esperando al cliente...");

                // Leer los mensajes del jugador
                String message = din.readUTF();
                System.out.println("Mensaje recibido: " + message);

                // El servidor aceptara al jugador si hay espacio en la sala
                if (message.equals("Solicitud de union a la sala")) {
                    if (players.size() <= NUM_MAX_JUGADORES) {
                        dout.writeUTF("Solicitud aceptada");
                    } else {
                        dout.writeUTF("Sala llena");
                        clientSocket.close();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    // Liberar un espacio de semaphore cada vez que un jugador se desconecta
                    clientSocket.close();
                    semaphore.release();
                    players.remove(this);
                    System.out.println("Jugador desconectado");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}