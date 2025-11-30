package biblioteca.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PUERTO = 9999;

    public static void main(String[] args){

        ExecutorService pool = Executors.newFixedThreadPool(10);

        System.out.println(">>> SERVIDOR BIBLIOTECA INICIADO <<<");

        try(ServerSocket serverSocket = new ServerSocket(PUERTO)){

            System.out.println("Escuchando en el puerto: " + PUERTO);
            System.out.println("Esperando clientes...");

            while(true){

                // se detiene aqui hasta que llega un cliente
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Nuevo cliente conectado desde: " + clienteSocket.getInetAddress());

                ClientHandler manejador = new ClientHandler(clienteSocket);

                pool.execute(manejador);

            }

        } catch (IOException e) {

            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

}