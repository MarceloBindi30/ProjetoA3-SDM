// package com.a3sdm.Util;

// import java.util.ArrayList;
// import java.util.HashMap;

// import com.a3sdm.Server.ThreadMultiplayer;
// import java.util.List;

// public class Multiplayer {
//     private List<Player> connectedPlayers = new ArrayList<>();
//     private HashMap<Integer, Player> map = new HashMap<>();
//     private List<Game> activeGames = new ArrayList<>();
//     private List<Player> playersForNewGame = new ArrayList<>();
//     private int maxPlayersPerGame = 6;

//     public synchronized void addPlayer(Player player) {
//         map.put(player.getJogo(), player);
//         notifyPlayerJoined();
//         waitForPlayers();
//     }

//     private synchronized void notifyPlayerJoined() {
//         this.notify();
//     }

//     public void waitForPlayers() {
//         while (true) {
//             int a = 0;
//             int b = 0;

//             for(Player player : connectedPlayers){
//                 if(player.getJogo() == 1){
//                     a++;
//                 }else if(player.getJogo() == 2){
//                     b++;
//                 }
//             }

//             if( a >= 2){
//                 for(Player player : connectedPlayers){
//                     if(player.getJogo() == 1){
//                         playersForNewGame.add(player);
//                     }
//                 }
//             }else if(b >=2){
//                 for(Player player : connectedPlayers){
//                     if(player.getJogo() == 1){
//                         playersForNewGame.add(player);
//                     }
//                 }
//             }

//             if(playersForNewGame.size() >= 2){
//                 playersForNewGame.add(connectedPlayers.remove(0));
//                 playersForNewGame.add(connectedPlayers.remove(0));
//                 startNewGame(playersForNewGame);
//             }
            
//             // if (connectedPlayers.size() >= maxPlayersPerGame) {
                
//             //     for (int i = 0; i < maxPlayersPerGame; i++) {
//             //         playersForNewGame.add(connectedPlayers.remove(0));
//             //     }
//             //     startNewGame(playersForNewGame);  
                
//             //     //VALIDAR JOGO QUE SERA INICIADO

//             // } else {
//             //     try {
//             //         synchronized (this) {
//             //             this.wait();
//             //         }
//             //     } catch (InterruptedException e) {
//             //         e.printStackTrace();
//             //     }
//             // }
//         }
//     }

//     private void startNewGame(List<Player> players) {
//         Game newGame = new Game(players);
//         activeGames.add(newGame);
//         newGame.run();
//         // Thread gameThread = new Thread(newGame);
//         // gameThread.start();
//     }

//     private class Game implements Runnable {
//         private List<Player> players;

//         public Game(List<Player> players) {
//             this.players = players;
//         }

//         @Override
//         public void run() {
//             //ThreadMultiplayer multiplayer = new ThreadMultiplayer(players.get(0), players.get(1));
//             //multiplayer.run();
//             activeGames.remove(this);
//         }
//     }
// }

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