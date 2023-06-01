package com.a3sdm.Server;

import java.net.Socket;
//import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import com.a3sdm.Util.Player;

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

            // Recepção de cliente e criação de Player
            playerList.add(new Player(clientSocket));
            // Player aguardando na lista
            
            for (Player player : playerList){
                if(player.getJogo() == 1){ //Add na lista de espera imparpar
                    addPlayer(player,1);
                }else if(player.getJogo() == 2){//Add na lista de espera jogo da velha
                    addPlayer(player,2);
                }else if(player.getJogo() == 3){//Add na lista de espera pedra/papel/tesoura
                    addPlayer(player,3);
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
        if (player.getNumbOfPlayers() == 2) { //ESCOLHEU JOGAR MULTIPLAYER
            availablePlayers.add(player.getSocketPlayer()); //ADICIONA NA FILA DE ESPERA SÓ COM SOCKET
            notifyPlayerJoined();
            if (availablePlayers.size() >= 2) { //VERIFICA A LISTA DE ESPERA
                Socket player1 = availablePlayers.remove(0); //PEGA O SOCKET DO PRIMEIRO DA LISTA E REMOVE ELE
                Socket player2 = availablePlayers.remove(0); //PEGA O SOCKET DO sEGUNDO DA LISTA E REMOVE ELE
                GameThread = new ServerThread(player1, player2, jogo); // CRIA THREAD COM OS PLAYERS E O JOGO
            } else {
                while (availablePlayers.size() < 2) { //LOOP DE AGURADE PARA MULTIPLAYER
                    try {
                        Server.class.wait(); // Aguarda até que haja jogadores suficientes
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Socket player1 = availablePlayers.remove(0); //PEGA O SOCKET DO PRIMEIRO DA LISTA E REMOVE ELE
                Socket player2 = availablePlayers.remove(0); //PEGA O SOCKET DO sEGUNDO DA LISTA E REMOVE ELE
                GameThread = new ServerThread(player1, player2, jogo); // CRIA THREAD COM OS PLAYERS E O JOGO
            }
        } else {
            GameThread = new ServerThread(player.getSocketPlayer(),jogo); //ESCOLHEU JOGAR SINGLEPLAYER
        }
        GameThread.start(); //STARTA A THREAD
    }

    public static synchronized void notifyPlayerJoined() {
        Server.class.notify(); // Notifica o servidor que um jogador se juntou
    }

}