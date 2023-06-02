package com.a3sdm.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.a3sdm.Util.ClientHandler;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        int port = 8888;
        long id = 1;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server iniciado na porta " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(id, clientSocket);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
                id++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}