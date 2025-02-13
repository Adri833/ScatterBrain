# **📱Scatterbrain - Juego Multijugador Local**

### Descripción del Proyecto

Scatterbrain es un juego multijugador local desarrollado en Java, utilizando el modelo TCP cliente-servidor para la comunicación entre jugadores. En este juego, un dispositivo actúa como servidor y puede también participar como jugador, mientras que los demás dispositivos actúan como clientes.

El objetivo principal de este proyecto es implementar una arquitectura robusta para la comunicación en red, permitiendo hasta 4 jugadores simultáneamente dentro de una partida.

# **💻Tecnologías Utilizadas**

- **Java (JDK 17)**
- **Java.net** (para la comunicación TCP)
- **Threads y Concurrencia** (para manejar múltiples clientes)
- **Semáforos** (para la sincronización de jugadores en la sala)
- **Colecciones sincronizadas** (para gestionar conexiones concurrentes)

# **ℹ️Instalación y Ejecución**

### 📌 Requisitos previos

- Tener Java 17 o superior instalado. Puedes descargarlo desde [aquí](https://adoptopenjdk.net/).
- Un entorno de desarrollo como IntelliJ IDEA o Eclipse.
- Acceso a una red local para la conexión entre dispositivos.

### 🚀 Pasos para ejecutar el servidor

1. Clona este repositorio en el dispositivo que actuará como servidor:

    ```bash
    git clone https://github.com/tuusuario/Scatterbrain.git
    ```

2. Abre el proyecto en tu IDE y compila el código.

3. Ejecuta la clase `Server` para iniciar el servidor:

    ```bash
    java Server
    ```

4. Espera a que los jugadores se conecten.

### 🎮 Pasos para unirse como cliente

1. Clona el repositorio en cada dispositivo que actuará como cliente.

2. Abre el proyecto y compila el código.

3. Ejecuta la clase `Jugador` e introduce la **IP del servidor** cuando se solicite. Puedes encontrar la IP del servidor con el siguiente comando:

    - En Linux/macOS: `ifconfig` (o `ip a`)
    - En Windows: `ipconfig`

4. Una vez que todos los jugadores estén conectados, el juego iniciará automáticamente.

# 📡 Arquitectura del Juego

### 🔹 Servidor (Server.java)

- Administra la conexión de los jugadores.
- Controla la lógica de la partida.
- Utiliza un `Semaphore` para gestionar un máximo de 4 conexiones simultáneas.
- Maneja la lista sincronizada de clientes.
- Envía y recibe datos de los jugadores a través de `DataInputStream` y `DataOutputStream`.

### 🔹 Cliente (Jugador.java)

- Se conecta al servidor mediante `Socket`.
- Envía solicitudes de unión a la sala.
- Recibe y procesa datos enviados por el servidor.
- Participa en la partida enviando y recibiendo comandos en tiempo real.

# **🎯 Flujo de Comunicación**

1. El servidor se inicia y queda a la espera de conexiones.
2. Los jugadores ejecutan el cliente, ingresan la IP del servidor y se conectan.
3. El servidor valida las conexiones y añade los jugadores a la sala.
4. Cuando se alcanza el número mínimo de jugadores, el juego comienza.
5. El servidor coordina la partida, enviando información a los clientes.
6. Los clientes envían sus movimientos, que son procesados por el servidor.
7. El juego finaliza y se muestran los resultados.

# **⚠️ Posibles Errores y Soluciones**

| Error              | Causa                                      | Solución                                         |
|--------------------|--------------------------------------------|--------------------------------------------------|
| Connection refused  | El servidor no está en ejecución           | Asegúrate de iniciar `Server.java` antes de los clientes |
| SocketTimeoutException | El cliente no puede alcanzar al servidor  | Verifica que el servidor y clientes estén en la misma red |
| Max clients reached | Se intentó conectar un quinto jugador     | Solo se permiten 4 jugadores simultáneamente     |

# **📜 Licencia**

Este proyecto es de uso académico y puede ser modificado libremente para fines educativos.

