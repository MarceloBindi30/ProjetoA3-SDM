package com.a3sdm.Server;

import java.net.Socket;
//import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import com.a3sdm.Util.Multiplayer;
import com.a3sdm.Util.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;

public class Server {
    private static List<Player> playerList = new ArrayList<>();
    private static ThreadSingle GameThread = null;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket;
        int port = 8080;
        
        try{
        //Criação do ServerSocket
            serverSocket = new ServerSocket(port);
            System.out.println("Aguardando jogadores...");

            while(true){
                Socket clientSocket = serverSocket.accept();
                if(clientSocket != null){
                    System.out.println("Nova conexão estabelecida: " + clientSocket.getInetAddress());
                }
                Player p = new Player(clientSocket);
                ThreadRecepcao t1 = new ThreadRecepcao(p);
                t1.run();

            // Socket clientSocket = serverSocket.accept();
            // ClientHandler clientHandler = new ClientHandler(clientSocket);
            // Thread clientThread = new Thread(clientHandler);
            // clientThread.start();
            }
        } catch (Exception e){
            System.out.println("Deu erro:" + e.getMessage());
        } 
    }

    private static void sendMessage(Socket socket, String message) {
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}