package com.a3sdm.Server;

import java.net.Socket;
//import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;

public class Server {
    private static List<Player> playerList = new ArrayList();
    private static List<Socket> availablePlayers = new ArrayList<>();
    private static ServerThread GameThread = null;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket;
        int port = 8080;
        String mensagem;
        //List<Socket> clientList = new ArrayList();
        
        try{
        
        //Criação do ServerSocket
        serverSocket = new ServerSocket(port);
        System.out.println("Aguardando jogadores...");

        while(true){
            Socket clientSocket = serverSocket.accept();
            System.out.println("Nova conexão estabelecida: " + clientSocket.getInetAddress());

            playerList.add(new Player(clientSocket));
            
            for (Player player : playerList){
                if(player.getJogo() == 1){ //imparpar
                    addPlayer(player,1);
                }else if(player.getJogo() == 2){//jogo da velha
                    addPlayer(player,2);
                }else if(player.getJogo() == 3){//pedra/papel/tesoura
                    addPlayer(player,3);
                }
            }

        }

        //JogoDaVelha.main(args); /***TESTE***/

        //while(true){
        //     Socket player1Socket = serverSocket.accept();
        //     System.out.println("Conexão estabelecida com Jogador 1: " + player1Socket.getInetAddress());
        //     player1 = new Player(player1Socket);
            
        //     Socket player2Socket = serverSocket.accept();
        //     System.out.println("Conexão estabelecida com Jogador 2: " + player2Socket.getInetAddress());
        //     player2 = new Player(player2Socket);

        //     if (player1.getJogo() == 1 && player1.getNumbOfPlayers() == 1){
        //         GameThread = new ServerThread(player1.getSocketPlayer(),player1.getJogo());
        //     }

        //     if (player2.getJogo() == 1 && player2.getNumbOfPlayers() == 1){
        //         GameThread = new ServerThread(player2.getSocketPlayer(),player2.getJogo());
        //     }

        //     if (player1.getJogo() == 2 && player1.getNumbOfPlayers() == 1){
        //         GameThread = new ServerThread(player1.getSocketPlayer(),player1.getJogo());
        //     }

        //     if (player2.getJogo() == 2 && player2.getNumbOfPlayers() == 1){
        //         GameThread = new ServerThread(player2.getSocketPlayer(),player2.getJogo());
        //     }

        //     if ((player1.getJogo() == 1 && player1.getNumbOfPlayers() == 2) || (player2.getJogo() == 1 && player2.getNumbOfPlayers() == 2)){
        //         do{
        //             mensagem = "Parece que o outro jogador está tirando um cochilo cósmico!, Aguarde só um momento";
        //             if (player1.getNumbOfPlayers() != 2){
        //                 sendMessage(player2.getSocketPlayer(),mensagem);
        //             }else if(player2.getNumbOfPlayers() != 2){
        //                 sendMessage(player1.getSocketPlayer(),mensagem);
        //             }

        //         }while((player1.getJogo()!= player2.getJogo())||(player1.getNumbOfPlayers()!= player2.getNumbOfPlayers()));
                
        //         GameThread = new ServerThread(player1.getSocketPlayer(),player2.getSocketPlayer(),player1.getJogo());
        //     }  

        //     if ((player1.getJogo() == 2 && player1.getNumbOfPlayers() == 2) || (player2.getJogo() == 2 && player2.getNumbOfPlayers() == 2)){
        //         do{
        //             mensagem = "Parece que o outro jogador está tirando um cochilo cósmico!, Aguarde só um momento";
        //             if (player1.getNumbOfPlayers() != 2){
        //                 sendMessage(player2.getSocketPlayer(),mensagem);
        //             }else if(player2.getNumbOfPlayers() != 2){
        //                 sendMessage(player1.getSocketPlayer(),mensagem);
        //             }
        //         }while((player1.getJogo()!= player2.getJogo())||(player1.getNumbOfPlayers()!= player2.getNumbOfPlayers()));
        //         GameThread = new ServerThread(player1.getSocketPlayer(),player2.getSocketPlayer(),player1.getJogo());
        //     }

        //     if (GameThread != null){
        //         GameThread.start();
        //     }

        // }

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

    public static synchronized void addPlayer(Player player, int jogo) {
        if (player.getNumbOfPlayers() == 2) {
            availablePlayers.add(player.getSocketPlayer());
            notifyPlayerJoined();
            if (availablePlayers.size() >= 2) {
                Socket player1 = availablePlayers.remove(0);
                Socket player2 = availablePlayers.remove(0);
                GameThread = new ServerThread(player1, player2,jogo);
            } else {
                while (availablePlayers.size() < 2) {
                    try {
                        Server.class.wait(); // Aguarda até que haja jogadores suficientes
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Socket player1 = availablePlayers.remove(0);
                Socket player2 = availablePlayers.remove(0);
                GameThread = new ServerThread(player1, player2, jogo);
            }
        } else {
            GameThread = new ServerThread(player.getSocketPlayer(),jogo);
        }
        GameThread.start();
    }

    public static synchronized void notifyPlayerJoined() {
        Server.class.notify(); // Notifica o servidor que um jogador se juntou
    }

}