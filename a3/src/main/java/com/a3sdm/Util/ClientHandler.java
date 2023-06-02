package com.a3sdm.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.a3sdm.Server.ThreadMultiplayer;
import com.a3sdm.Server.ThreadSingle;

public class ClientHandler implements Runnable {
    private Socket playerSocket;
    private Long id;
    private int jogo, qtJogador;    
    private BufferedReader reader;
    private PrintWriter writer;
    private String playerName, jogoTemp, qtJogadorTemp;
    private ClientHandler opponent;
    private boolean isPlayerTurn;
    //private static Map<Long, ClientHandler> playersMap = new HashMap<>();
    //private static Queue<ClientHandler> playersQueue = new ConcurrentLinkedQueue<>();


    public ClientHandler(long id, Socket playerSocket) throws IOException {
        this.id = id;
        this.playerSocket = playerSocket;
        reader = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
        writer = new PrintWriter(playerSocket.getOutputStream(), true);
        isPlayerTurn = false;
    }
    
    

    @Override
    public void run() {
        System.out.println("Nova conexão recebida");

        try {
            // sendMessage("Seja bem vindo ao jogo ImparPar do Vitão!");
            // sendMessage("Por favor, informe seu nome :)");

            // String playerName;

            // while (true) {
            //     playerName = requestAnswer();

            //     if (playerName == null) {
            //         return; // conexão encerrada
            //     }

            //     if (playerName.isBlank()) {
            //         continue;
            //     }

            //     // Verifica se o nome está disponível
            //     if (!playersMap.containsKey(playerName)) {
            //         break;
            //     }

            //     sendMessage("Xiii, esse nome já foi escolhido por outra pessoa...");
            //     sendMessage("Vou precisar que você escolha outro :P");
            // }

            sendMessage("Defina seu jogo: ");
            while (true) {              
                jogoTemp = requestAnswer();
                if (jogoTemp == null) {
                    return; // conexão encerrada
                }

                if(jogoTemp.equals("1") || jogoTemp.equals("2")){
                    break;
                }
            }

            sendMessage("Defina a quantidade de jogadores: ");
            while (true) {
                qtJogadorTemp = requestAnswer();
                if (qtJogadorTemp == null) {
                    return; // conexão encerrada
                }

                if(qtJogadorTemp.equals("1") || qtJogadorTemp.equals("2")){
                    break;
                }
            }
        
            //this.playerName = playerName;

            if (jogoTemp.equals("1")) {
                this.jogo = 1;
            }else{
                this.jogo = 2;
            }
            if (qtJogadorTemp.equals("1")) {
                this.qtJogador = 1;
            }else{
                this.qtJogador = 2;
            }
            

            if(qtJogador == 1){
                ThreadSingle t1 = new ThreadSingle(this);
                t1.run();
            }else{
                ThreadMultiplayer t2 = new ThreadMultiplayer(this);
                t2.run();
            }

            //playersMap.put(id, this);
            //playersQueue.add(this);
        }catch(Exception e){

        }
    
    }

    public Long getId() {
        return id;
    }

    public int getJogo() {
        return jogo;
    }

    public ClientHandler getOpponent() {
        return opponent;
    }

    public Socket getPlayerSocket() {
        return playerSocket;
    }

    public boolean getIsPlayerTurn(){
        return isPlayerTurn;
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

    
    private String requestAnswer() throws IOException {
        String resp;
        String requestAnswer = "|Request_Answer|";
        sendMessage(requestAnswer);
        resp = reader.readLine();
        return resp;
    }

    public void setOpponent(ClientHandler opponent) {
        this.opponent = opponent;

        if (opponent != null) {
            sendMessage("Opa! A partida começou!!! Você irá disputar com " + opponent.playerName);
        } else {
            sendMessage("Parabéns!!! Seu oponente arregou!!! Você cansou ele kkkk");
            sendMessage("Aguarde até que outro oponente digno entre no jogo!");
        }
    }

    public void setPlayerTurn(boolean isPlayerTurn) {
        this.isPlayerTurn = isPlayerTurn;
        if (isPlayerTurn) {
            sendMessage("Qual opção você escolhe? Impar ou Par? (ou Cansei para sair)");
        } else {
            sendMessage("Seu oponente irá começar jogando... Aguarde pela escolha dele!");
        }
    }

    public int generateRandomNumber() {
        return (int) (Math.random() * 6) + 1;
    }
}