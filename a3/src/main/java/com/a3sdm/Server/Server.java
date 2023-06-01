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
        Multiplayer multiplayer1 = new Multiplayer();
        Multiplayer multiplayer2 = new Multiplayer();
        Multiplayer multiplayer3 = new Multiplayer();
        //List<Socket> clientList = new ArrayList();
        
        try{
        
        //Criação do ServerSocket
        serverSocket = new ServerSocket(port);
        System.out.println("Aguardando jogadores...");

        while(true){
            Socket clientSocket = serverSocket.accept();
            System.out.println("Nova conexão estabelecida: " + clientSocket.getInetAddress());

            // Recepção de cliente e criação de Player
            playerList.add(new Player(clientSocket));
            // Player aguardando na lista
            
            for (Player player : playerList){
                if (player.getNumbOfPlayers() != 1){
                    if(player.getJogo() == 1){ //Roda o ImparPar
                        addPlayer(player,1);
                    }else if(player.getJogo() == 2){//Roda o jogo da velha
                        addPlayer(player,2);
                    }else if(player.getJogo() == 3){//Roda o Pedra/Papel/tesoura
                        addPlayer(player,3);
                    }
                }else{
                    if(player.getJogo() == 1){ //Add na lista de espera imparpar
                        multiplayer1.addPlayer(player);
                    }else if(player.getJogo() == 2){//Add na lista de espera jogo da velha
                        multiplayer2.addPlayer(player);
                    }else if(player.getJogo() == 3){//Add na lista de espera pedra/papel/tesoura
                        multiplayer3.addPlayer(player);
                    }
                }
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

    /*************************************************************************************/
    //VERIFICAR NECESSIDADE DE CRIAR OUTRA CLASSE PARA GERENCIAR FILA DE ESPERA DO SERVER//
    /*************************************************************************************/

    public static synchronized void addPlayer(Player player, int jogo) {
        GameThread = new ThreadSingle(player.getSocketPlayer(),jogo); //ESCOLHEU JOGAR SINGLEPLAYER
        GameThread.start(); //START A THREAD
    }

    
}