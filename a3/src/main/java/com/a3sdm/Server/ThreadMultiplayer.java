package com.a3sdm.Server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.a3sdm.Util.ClientHandler;

public class ThreadMultiplayer extends Thread {

    private ClientHandler player;

    private static Map<Long, ClientHandler> playersMapImP = new HashMap<>();
    private static Queue<ClientHandler> playersQueueImP = new ConcurrentLinkedQueue<>();
    private static Map<Long, ClientHandler> playersMapVelha = new HashMap<>();
    private static Queue<ClientHandler> playersQueueVelha = new ConcurrentLinkedQueue<>();

    public ThreadMultiplayer(ClientHandler player) throws IOException {
        this.player = player;
    }

    private void ModoJogoPVP() throws IOException {

        if (player.getJogo() == 1) {
            playersMapImP.put(player.getId(), player);
            playersQueueImP.add(player);
            player.sendMessage("Você está na fila: aguardando próximos jogadores.");
        } else {
            playersMapVelha.put(player.getId(), player);
            playersQueueVelha.add(player);
        }

        if (playersQueueImP.size() >= 2) {
            player.sendMessage("OPONENTE ENCONTRADO!");

            ClientHandler client1 = playersQueueImP.poll();
            ClientHandler client2 = playersQueueImP.poll();
            client1.setOpponent(client2);
            client2.setOpponent(client1);
            client1.setPlayerTurn(true);
            client1.sendWelcomeMsg();
            client2.setPlayerTurn(false);
            client2.sendWelcomeMsg();
        }

        try {
            String choice;

            while ((choice = player.requestAnswer()) != null) {
                if (choice.isEmpty()) {
                    continue;
                }

                if (player.getIsPlayerTurn()) {
                    if (choice.equalsIgnoreCase("par") || choice.equalsIgnoreCase("impar")) {
                        handlePlayerGuess(choice);
                    } else if (choice.equalsIgnoreCase("cansei")) {
                        player.sendMessage("Tá sei... Cansou nada... Você arregou isso sim! rsrs");
                        break;
                    } else {
                        player.sendMessage("Opção inválida seu oreia seca!!!");
                        player.sendMessage("As opções válidas são: par e impar");
                        player.sendMessage("Ou digite 'cansei' para sair");
                    }
                } else {
                    player.sendMessage("Opa! Não é sua vez oreia! Não seja ansioso rsrs");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            playersMapImP.remove(player.getId());
            playersQueueImP.remove(player);

            if (player.getOpponent() != null) {
                player.getOpponent().setOpponent(null);
                player.getOpponent().setPlayerTurn(false);
                playersQueueImP.add(player.getOpponent()); // fica na espera por outro oponente
            }

            try {
                player.getPlayerSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            ModoJogoPVP();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlePlayerGuess(String guess) {
        int playerNumber = player.generateRandomNumber();
        int opponentNumber = player.getOpponent().generateRandomNumber();
        String opponentName = player.getOpponent().getId().toString();
        int sum = playerNumber + opponentNumber;
        String youWonMessage = "Parabéns! Você ganhou!!!";
        String youLostMessage = "Xiiiiii! Você perdeu!!!";

        String result = (sum % 2 == 0) ? "par" : "impar";
        player.sendMessage("Você escolheu " + guess + " e seu número aleatório é " + playerNumber);
        player.sendMessage("Como o número aleatório do " + opponentName + " é " + opponentNumber);
        player.sendMessage("O resultado foi " + result);

        player.getOpponent().sendMessage(
                "Jogador" + getId() + " escolheu " + guess + " e o número aleatório dele é " + playerNumber);
        player.getOpponent().sendMessage("Como o seu número aleatório é " + opponentNumber);
        player.getOpponent().sendMessage("O resultado foi " + result);

        if (guess.equalsIgnoreCase(result)) {
            player.sendMessage(youWonMessage);
            player.getOpponent().sendMessage(youLostMessage);
        } else {
            player.sendMessage(youLostMessage);
            player.getOpponent().sendMessage(youWonMessage);
        }

        player.setPlayerTurn(false);
        player.getOpponent().setPlayerTurn(true);
        player.sendWelcomeMsg();
        player.getOpponent().sendWelcomeMsg();
    }
}
