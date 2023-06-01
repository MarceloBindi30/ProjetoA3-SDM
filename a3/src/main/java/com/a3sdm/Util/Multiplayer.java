package com.a3sdm.Util;

import java.util.ArrayList;
import com.a3sdm.Server.ThreadMultiplayer;
import java.util.List;

public class Multiplayer {
    private List<Player> connectedPlayers = new ArrayList<>();
    private List<Game> activeGames = new ArrayList<>();
    private int maxPlayersPerGame = 2;

    public synchronized void addPlayer(Player player) {
        connectedPlayers.add(player);
        notifyPlayerJoined();
    }

    private synchronized void notifyPlayerJoined() {
        this.notify();
    }

    public void waitForPlayers() {
        while (true) {
            if (connectedPlayers.size() >= maxPlayersPerGame) {
                List<Player> playersForNewGame = new ArrayList<>();
                for (int i = 0; i < maxPlayersPerGame; i++) {
                    playersForNewGame.add(connectedPlayers.remove(0));
                }
                startNewGame(playersForNewGame);
            } else {
                try {
                    synchronized (this) {
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void startNewGame(List<Player> players) {
        Game newGame = new Game(players);
        activeGames.add(newGame);
        Thread gameThread = new Thread(newGame);
        gameThread.start();
    }

    private class Game implements Runnable {
        private List<Player> players;

        public Game(List<Player> players) {
            this.players = players;
        }

        @Override
        public void run() {
            ThreadMultiplayer multiplayer = new ThreadMultiplayer(players.get(0), players.get(1));
            multiplayer.getId();
            activeGames.remove(this);
        }
    }
}

//IMPLEMENTAR LOBBY DE ESPERA
// SE TIVER MAIS DE 1 JOGADOR QUERENDO JOGAR O MESMO JOGO, MANDAR PARA A THREAD MULTIPLAYER
// public class Multiplayer {
//     private java.util.List<Socket> availablePlayers = new ArrayList<>();
//     private ThreadMultiplayer GameThread;
//     private Player player;
//     private int jogo;

//     public Multiplayer(Player player){
//         this.player = player;
//     }

//     public void Espera(){   
//     if (player.getNumbOfPlayers() == 2) { //ESCOLHEU JOGAR MULTIPLAYER
//             availablePlayers.add(player.getSocketPlayer()); //ADICIONA NA FILA DE ESPERA SÓ COM SOCKET
//             notifyPlayerJoined();
//             if (availablePlayers.size() >= 2) { //VERIFICA A LISTA DE ESPERA
//                 Socket player1 = availablePlayers.remove(0); //PEGA O SOCKET DO PRIMEIRO DA LISTA E REMOVE ELE
//                 Socket player2 = availablePlayers.remove(0); //PEGA O SOCKET DO SEGUNDO DA LISTA E REMOVE ELE
//                 GameThread = new ThreadMultiplayer(player1, player2, jogo); // CRIA THREAD COM OS PLAYERS E O JOGO
//             } else {
//                 while (availablePlayers.size() < 2) { //LOOP DE AGURADE PARA MULTIPLAYER
//                     try {
//                         this.wait(); // Aguarda até que haja jogadores suficientes
//                     } catch (InterruptedException e) {
//                         e.printStackTrace();
//                     }
//                 }
//                 Socket player1 = availablePlayers.remove(0); //PEGA O SOCKET DO PRIMEIRO DA LISTA E REMOVE ELE
//                 Socket player2 = availablePlayers.remove(0); //PEGA O SOCKET DO sEGUNDO DA LISTA E REMOVE ELE
//                 GameThread = new ThreadMultiplayer(player1, player2, jogo); // CRIA THREAD COM OS PLAYERS E O JOGO
//             }
//         }
//     }

//     public void notifyPlayerJoined() {
//         synchronized (this){
//             this.notify();  // Notifica o servidor que um jogador se juntou
//         }
//     }

// }