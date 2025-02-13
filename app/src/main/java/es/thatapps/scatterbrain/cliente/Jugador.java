package es.thatapps.scatterbrain.cliente;

import android.os.Handler;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import es.thatapps.scatterbrain.data.ScatterFinals;

public class Jugador {

    private Socket socket;
    private DataInputStream din;
    private DataOutputStream dout;
    private Handler handler;

    public Jugador(Handler handler) {
        this.handler = handler;
    }

    public void conectar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Obtiene la IP del wifi actual del dispositivo
                    String SERVER_IP = InetAddress.getLocalHost().getHostAddress();
                    socket = new Socket(SERVER_IP, ScatterFinals.SERVER_PORT);
                    System.out.println("Se ha conectado al servidor en " + SERVER_IP + " puerto " + ScatterFinals.SERVER_PORT);

                    // Crea flujos de entrada y salida
                    din = new DataInputStream(socket.getInputStream());
                    dout = new DataOutputStream(socket.getOutputStream());

                    // Leer mensajes de bienvenida al servidor
                    String serverMessage = din.readUTF();
                    System.out.println("Servidor: " + serverMessage);

                    // Enviar una solicitud de union a la sala
                    dout.writeUTF("Solicitud de union a la sala");
                    dout.flush(); // Asegurar que el mensaje se envia inmediatamente

                    // Espera la respuesta del servidor
                    String serverResponse = din.readUTF();
                    if (serverResponse.equals("Solicitud aceptada")) {
                        System.out.println("Solicitud aceptada por el servidor");
                        escucharJugadoresConectados();
                    } else {
                        System.out.println("No se ha podido unir a la sala: " + serverResponse);
                    }

                } catch (Exception e) {
                    System.out.println("No se ha podido conectar al servidor: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // Método para escuchar la lista de jugadores conectados
    private void escucharJugadoresConectados() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        // Espera y recibe la lista de jugadores conectados
                        String listaJugadores = din.readUTF();
                        // Actualizar la UI en el hilo principal
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                // Actualizar el TextView con la lista de jugadores
                                System.out.println(listaJugadores);
                            }
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Error al recibir actualizaciones de jugadores: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // Metodo para desconectar el servidor
    public void desconectar() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("Se ha desconectado del servidor");
            }
        } catch (Exception e) {
            System.out.println("Error al desconectar del servidor: " + e.getMessage());
        }
    }
}
