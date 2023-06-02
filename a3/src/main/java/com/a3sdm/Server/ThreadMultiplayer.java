package com.a3sdm.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.xml.sax.SAXNotSupportedException;

import com.a3sdm.Jogos.JogoDaVelhaPvP;
import com.a3sdm.Util.ClientHandler;

public class ThreadMultiplayer extends Thread{
    //ClientHandler client1, client2;
    private boolean isPlayerTurn;
    private ClientHandler opponent;
    private ClientHandler player;
    BufferedReader reader;
    PrintWriter writer; 
    private static Map<Long, ClientHandler> playersMapImP = new HashMap<>();
    private static Queue<ClientHandler> playersQueueImP = new ConcurrentLinkedQueue<>();
    private static Map<Long, ClientHandler> playersMapVelha = new HashMap<>();
    private static Queue<ClientHandler> playersQueueVelha = new ConcurrentLinkedQueue<>();

    public ThreadMultiplayer(ClientHandler player) throws IOException{
        this.player = player;
        reader = new BufferedReader(new InputStreamReader(player.getPlayerSocket().getInputStream()));
        writer = new PrintWriter(player.getPlayerSocket().getOutputStream(), true);
    }

    private void ModoJogoPVP() throws IOException{

        if(player.getJogo() == 1){
            playersMapImP.put(player.getId(), player);
            playersQueueImP.add(player);
            sendMessage("Vc esta na fila");
        }else{
            playersMapVelha.put(player.getId(), player);
            playersQueueVelha.add(player);
        }
        
        if (playersQueueImP.size() >= 2) {
            sendMessage("OPONENTE ENCONTRADO!");
            // for(ClientHandler c : playersQueueImP){
            //     System.out.println(c);
            // }
            ClientHandler client1 = playersQueueImP.poll();
            ClientHandler client2 = playersQueueImP.poll();
            client1.setOpponent(client2);
            client2.setOpponent(client1);
            client1.setPlayerTurn(true);
            client2.setPlayerTurn(false);
        
            try {
                String choice = requestAnswer();
                while (!choice.isEmpty()) {
                    if (choice.isEmpty()) {
                        continue;
                    }

                    if (isPlayerTurn) {
                        //sendMessage("passei aki");
                        if (choice.equalsIgnoreCase("par") || choice.equalsIgnoreCase("impar")) {
                            sendMessage("Escolha recebida1");
                            handlePlayerGuess(choice);
                            sendMessage("Escolha recebida2");
                        } else if (choice.equalsIgnoreCase("cansei")) {
                            sendMessage("Tá sei... Cansou nada... Você arregou isso sim! rsrs");
                            break;
                        } else {
                            sendMessage("Opção inválida seu oreia seca!!!");
                            sendMessage("As opções válidas são: par e impar");
                            sendMessage("Ou digite 'cansei' para sair");
                        }
                    } else {
                    sendMessage("Opa! Não é sua vez oreia! Não seja ansioso rsrs");
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if (playersQueueVelha.size() >= 2) {
            ClientHandler client1 = playersQueueVelha.poll();
            ClientHandler client2 = playersQueueVelha.poll();
            client1.setOpponent(client2);
            client2.setOpponent(client1);
            client1.setPlayerTurn(true);
            client2.setPlayerTurn(false);
            JogoDaVelhaPvP velhaPvP = new JogoDaVelhaPvP(client1.getPlayerSocket(), client2.getPlayerSocket());
        }  

    }
    

    @Override
    public void run() {
        try {
            ModoJogoPVP();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void handlePlayerGuess(String guess) {
        int playerNumber = generateRandomNumber();
        int opponentNumber = opponent.generateRandomNumber();
        String opponentName = opponent.getId().toString();
        int sum = playerNumber + opponentNumber;
        String youWonMessage = "Parabéns! Você ganhou!!!";
        String youLostMessage = "Xiiiiii! Você perdeu!!!";

        String result = (sum % 2 == 0) ? "par" : "impar";
        sendMessage("Você escolheu " + guess + " e seu número aleatório é " + playerNumber);
        sendMessage("Como o número aleatório do " + opponentName + " é " + opponentNumber);
        sendMessage("O resultado foi " + result);

        opponent.sendMessage("Jogador" + getId() + " escolheu " + guess + " e o número aleatório dele é " + playerNumber);
        opponent.sendMessage("Como o seu número aleatório é " + opponentNumber);
        opponent.sendMessage("O resultado foi " + result);

        if (guess.equalsIgnoreCase(result)) {
            sendMessage(youWonMessage);
            opponent.sendMessage(youLostMessage);
        } else {
            sendMessage(youLostMessage);
            opponent.sendMessage(youWonMessage);
        }

        isPlayerTurn = false;
        opponent.setPlayerTurn(true);
    }

    private void sendMessage(String message) {
        writer.println(message);
    }

    private String requestAnswer() throws IOException {
        String resp;
        String requestAnswer = "|Request_Answer|";
        sendMessage(requestAnswer);
        resp = reader.readLine();
        System.out.println("isso é o resp " + resp);
        return resp;
    }

    private void setOpponent(ClientHandler opponent) {
        this.opponent = opponent;

        if (opponent != null) {
            sendMessage("Opa! A partida começou!!! Você irá disputar com jogador" + opponent.getId());
        } else {
            sendMessage("Parabéns!!! Seu oponente arregou!!! Você cansou ele kkkk");
            sendMessage("Aguarde até que outro oponente digno entre no jogo!");
        }
    }

    private void setPlayerTurn(boolean isPlayerTurn) {
        this.isPlayerTurn = isPlayerTurn;
        if (isPlayerTurn) {
            sendMessage("Qual opção você escolhe? Impar ou Par? (ou Cansei para sair)");
        } else {
            sendMessage("Seu oponente irá começar jogando... Aguarde pela escolha dele!");
        }
    }

    private int generateRandomNumber() {
        return (int) (Math.random() * 6) + 1;
    }

}
   