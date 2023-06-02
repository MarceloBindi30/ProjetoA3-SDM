package com.a3sdm.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        //GUI Menu = new GUI();
        //Board teste = new Board();
        String host = "localhost"; // Endereço IP do servidor
        int port = 8888; // Porta do servidor
        String requestAnswer = "|Request_Answer|";

        
        try {
            Socket socket = new Socket(host, port);
            System.out.println("Conectado ao servidor " + host + " na porta " + port);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            String serverMessage;
            while ((serverMessage = serverReader.readLine()) != null) {
                if (serverMessage.equals(requestAnswer)) {
                    int x = 5; // wait 2 seconds at most
                    long startTime = System.currentTimeMillis();
                    while ((System.currentTimeMillis() - startTime) < x * 1000 &&
                            !reader.ready()) {
                    }
                    if (reader.ready()) {
                        writer.println(reader.readLine());
                    } else {
                        writer.println("");
                    }
                } else {
                    System.out.println(serverMessage);
                }
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

