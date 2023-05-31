package com.a3sdm.Server;

import java.net.Socket;

import com.a3sdm.Jogos.JogoDaVelha;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket;
        int port = 8080;
        ServerThread GameThread = null;
        Player player1,player2;
        String mensagem;

        try{
        
        //Criação do ServerSocket
        serverSocket = new ServerSocket(port);
        System.out.println("Aguardando jogadores...");

        //JogoDaVelha.main(args); /***TESTE***/

        while(true){
            Socket player1Socket = serverSocket.accept();
            System.out.println("Conexão estabelecida com Jogador 1: " + player1Socket.getInetAddress());
            player1 = new Player(player1Socket);

            Socket player2Socket = serverSocket.accept();
            System.out.println("Conexão estabelecida com Jogador 2: " + player2Socket.getInetAddress());
            player2 = new Player(player2Socket);

            if (player1.getJogo() == 1 && player1.getNumbOfPlayers() == 1){
                GameThread = new ServerThread(player1.getSocketPlayer(),player1.getJogo());
            }

            if (player2.getJogo() == 1 && player2.getNumbOfPlayers() == 1){
                GameThread = new ServerThread(player2.getSocketPlayer(),player2.getJogo());
            }

            if (player1.getJogo() == 2 && player1.getNumbOfPlayers() == 1){
                GameThread = new ServerThread(player1.getSocketPlayer(),player1.getJogo());
            }

            if (player2.getJogo() == 2 && player2.getNumbOfPlayers() == 1){
                GameThread = new ServerThread(player2.getSocketPlayer(),player2.getJogo());
            }

            if ((player1.getJogo() == 1 && player1.getNumbOfPlayers() == 2) || (player2.getJogo() == 1 && player2.getNumbOfPlayers() == 2)){
                do{
                    mensagem = "Parece que o outro jogador está tirando um cochilo cósmico!, Aguarde só um momento";
                    if (player1.getNumbOfPlayers() != 2){
                        sendMessage(player2.getSocketPlayer(),mensagem);
                    }else if(player2.getNumbOfPlayers() != 2){
                        sendMessage(player1.getSocketPlayer(),mensagem);
                    }

                }while((player1.getJogo()!= player2.getJogo())||(player1.getNumbOfPlayers()!= player2.getNumbOfPlayers()));
                
                GameThread = new ServerThread(player1.getSocketPlayer(),player2.getSocketPlayer(),player1.getJogo());
            }  

            if ((player1.getJogo() == 2 && player1.getNumbOfPlayers() == 2) || (player2.getJogo() == 2 && player2.getNumbOfPlayers() == 2)){
                do{
                    mensagem = "Parece que o outro jogador está tirando um cochilo cósmico!, Aguarde só um momento";
                    if (player1.getNumbOfPlayers() != 2){
                        sendMessage(player2.getSocketPlayer(),mensagem);
                    }else if(player2.getNumbOfPlayers() != 2){
                        sendMessage(player1.getSocketPlayer(),mensagem);
                    }
                }while((player1.getJogo()!= player2.getJogo())||(player1.getNumbOfPlayers()!= player2.getNumbOfPlayers()));
                GameThread = new ServerThread(player1.getSocketPlayer(),player2.getSocketPlayer(),player1.getJogo());
            }

            if (GameThread != null){
                GameThread.start();
            }

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