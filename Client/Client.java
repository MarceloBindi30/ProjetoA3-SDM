package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        GUI Menu = new GUI();
        //Board teste = new Board();
        String serverIP = "localhost"; // Endereço IP do servidor
        int serverPort = 8080; // Porta do servidor
        Scanner teclado = new Scanner(System.in);
        Scanner scanner;
        String mensagem = "";

        //TO DO
        //socketClient.getInetAddress().getHostAddress()
        
        try {
            // Conectando ao servidor
            Socket socket = new Socket(serverIP, serverPort);
            System.out.println("Conectado ao servidor");

            // Obtém streams de entrada e saída para comunicação
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            
            //SÓ ESTA LENDO O PAR OU IMPAR//
            ////////////////////////////////
            try {
                PrintStream printStream = new PrintStream(socket.getOutputStream());
                scanner = new Scanner(socket.getInputStream());
                teclado = new Scanner(System.in);
               
            } catch (Exception e) {
                System.out.println("Erro na troca de dados");
            }
    
            // Recebe a escolha do servidor
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            String serverChoice = new String(buffer, 0, bytesRead);

            // Fechando conexão
            socket.close();
            System.out.println("Conexão encerrada");

        } catch (IOException e) {
            e.printStackTrace();
        }
        teclado.close();
    }
}

