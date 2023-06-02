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
    private static Map<Long, ClientHandler> playersMap = new HashMap<>();
    private static Queue<ClientHandler> playersQueue = new ConcurrentLinkedQueue<>();


    public ClientHandler(long id, Socket playerSocket) throws IOException {
        this.id = id;
        this.playerSocket = playerSocket;
        reader = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
        writer = new PrintWriter(playerSocket.getOutputStream(), true);
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
            }

            //playersMap.put(id, this);
            //playersQueue.add(this);
        }catch(Exception e){

        }


        //     sendMessage("Show! Você será conhecido como " + id);
        //     sendMessage("Aguarde até que um oponente entre no jogo!");

        //     // O próximo jogador que pegar a fila com 2 ou mais clientes
        //     // Irá iniciar a partida entre eles!
        //     if (playersQueue.size() >= 2) {
        //         ClientHandler client1 = playersQueue.poll();
        //         ClientHandler client2 = playersQueue.poll();
        //         client1.setOpponent(client2);
        //         client2.setOpponent(client1);
        //         client1.setPlayerTurn(true);
        //         client2.setPlayerTurn(false);
        //     }

        //     String choice;
        //     while ((choice = requestAnswer()) != null) {
        //         if (choice.isEmpty()) {
        //             continue;
        //         }

        //         if (isPlayerTurn) {
        //             if (choice.equalsIgnoreCase("par") || choice.equalsIgnoreCase("impar")) {
        //                 handlePlayerGuess(choice);
        //             } else if (choice.equalsIgnoreCase("cansei")) {
        //                 sendMessage("Tá sei... Cansou nada... Você arregou isso sim! rsrs");
        //                 break;
        //             } else {
        //                 sendMessage("Opção inválida seu oreia seca!!!");
        //                 sendMessage("As opções válidas são: par e impar");
        //                 sendMessage("Ou digite 'cansei' para sair");
        //             }
        //         } else {
        //             sendMessage("Opa! Não é sua vez oreia! Não seja ansioso rsrs");
        //         }
        //     }
        // } catch (Exception e) {
        //     e.printStackTrace();
        // } finally {
        //     try {
        //         if (playerName != null) {
        //             playersMap.remove(playerName);
        //             playersQueue.remove(this);
        //             if (opponent != null) {
        //                 opponent.setOpponent(null);
        //                 opponent.isPlayerTurn = false;
        //                 playersQueue.add(opponent); // fica na espera por outro oponente
        //             }
        //             System.out.println("Conexão do player " + playerName + " finalizada");
        //         } else {
        //             System.out.println("Conexão de player anônimo finalizada");
        //         }

        //         playerSocket.close();
        //     } catch (IOException e) {
        //         e.printStackTrace();
        //     }
        // }
    }

    public int getJogo() {
        return jogo;
    }

    public Socket getPlayerSocket() {
        return playerSocket;
    }

    private void sendMessage(String message) {
        writer.println(message);
    }

    private void handlePlayerGuess(String guess) {
        int playerNumber = generateRandomNumber();
        int opponentNumber = opponent.generateRandomNumber();
        String opponentName = opponent.playerName;
        int sum = playerNumber + opponentNumber;
        String youWonMessage = "Parabéns! Você ganhou!!!";
        String youLostMessage = "Xiiiiii! Você perdeu!!!";

        String result = (sum % 2 == 0) ? "par" : "impar";
        sendMessage("Você escolheu " + guess + " e seu número aleatório é " + playerNumber);
        sendMessage("Como o número aleatório do " + opponentName + " é " + opponentNumber);
        sendMessage("O resultado foi " + result);

        opponent.sendMessage(playerName + " escolheu " + guess + " e o número aleatório dele é " + playerNumber);
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

    

    private String requestAnswer() throws IOException {
        String resp;
        String requestAnswer = "|Request_Answer|";
        sendMessage(requestAnswer);
        resp = reader.readLine();
        return resp;
    }

    private void setOpponent(ClientHandler opponent) {
        this.opponent = opponent;

        if (opponent != null) {
            sendMessage("Opa! A partida começou!!! Você irá disputar com " + opponent.playerName);
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