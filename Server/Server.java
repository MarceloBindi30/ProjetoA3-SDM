package Server;

import java.net.ServerSocket;
import java.net.Socket;
// o vitin continue yag
public class Server {
    public static void main(String[] args) {
        ServerSocket server;
        final int PORT = 12345; 

        try {
            server = new ServerSocket(PORT);

            while(true){
                
                System.out.println("Aguardando cliente... ");
                Socket client =  server.accept();

                // INICIA THREAD
                //ThreadCalc t1 = new ThreadCalc(client);
                //t1.start();
                client.close();
            }

            
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}