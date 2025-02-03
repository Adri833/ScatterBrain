package es.thatapps.scatterbrain.cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Jugador {
    // IP y puerto del servidor
    private static final String SERVER_IP = "10.192.116.49";
    private static final int SERVER_PORT = 53296;

    private Socket socket;
    private DataInputStream din;
    private DataOutputStream dout;

    public void conectar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(SERVER_IP, SERVER_PORT);
                    System.out.println("Se ha conectado al servidor en " + SERVER_IP + " puerto " + SERVER_PORT);

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
