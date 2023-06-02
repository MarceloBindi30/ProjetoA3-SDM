package com.a3sdm.Jogos;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class ImparParSingle extends JFrame{
    //private Socket player1;
    private JTextField campoTexto;
    private BufferedReader reader;
    private PrintWriter writer;
    Random random = new Random();
    
    public ImparParSingle(){
        super("Par ou Ímpar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(1000,700);
        setBackground(Color.cyan); 
        campoTexto = new JTextField(); 
        campoTexto.setVisible(true); 
        campoTexto.setBounds(250, 450, 200, 100);
        setLocationRelativeTo(null);
        add(campoTexto);

        setVisible(true); 
    }

    public ImparParSingle(Socket player1) throws IOException{
        //this.player1 = player1;
        this.reader = new BufferedReader(new InputStreamReader(player1.getInputStream()));
        this.writer = new PrintWriter(player1.getOutputStream(), true);
    }


    public void PlayerVSCPU() throws IOException{
        int numeroAleatorio = random.nextInt(5) + 1;

        // Gera um número aleatório para a escolha do servidor (ímpar ou par)
        boolean serverIsOdd = random.nextBoolean();
        // Envia a escolha do servidor para os jogadores
        String serverChoice = serverIsOdd ? "ímpar" : "par";
        sendMessage("A soma deve ser: " + serverChoice);
        sendMessage("Escolha um número de 1 a 5");
        String respostaPlayer = requestAnswer();

        boolean canContinue = true;

        while (canContinue) {
            if (respostaPlayer.matches("\\d+") && Integer.parseInt(respostaPlayer) > 0 && Integer.parseInt(respostaPlayer) < 6) {
                canContinue = false;
            } else {
                sendMessage("Entrada inválida. Por favor, insira um número entre 1 e 5:" + respostaPlayer);
                respostaPlayer = requestAnswer();
            }
        }

        // Verifica quem ganhou
        int sum = Integer.parseInt(respostaPlayer) + (numeroAleatorio);
        boolean isSumOdd = sum % 2 != 0;
        boolean player1Wins = (isSumOdd && serverIsOdd) || (!isSumOdd && !serverIsOdd);

        // Envia o resultado para os jogadores
        String result = player1Wins ? "Você ganhou!" : "Servidor ganhou!";
        sum = Integer.parseInt(respostaPlayer) + (numeroAleatorio);
        sendMessage("A soma deu " + sum + "\n" + result);
    }


    public void PlayerVSCPUOutroJeito() throws IOException{
        String respostaPlayer;
        boolean player1Wins;
        do{
            sendMessage("Escolha um: impar ou par");
            respostaPlayer = requestAnswer();
            if(!respostaPlayer.equalsIgnoreCase("impar") && !respostaPlayer.equalsIgnoreCase("par")){
                sendMessage("Resposta inválida, escolha apenas uma das opções");
            }
        }while(!respostaPlayer.equalsIgnoreCase("impar") && !respostaPlayer.equalsIgnoreCase("par"));

        int numeroAleatorioJogador= random.nextInt(5) + 1;;
        int numeroAleatorioCPU = random.nextInt(5) + 1;;

        //validar depois

        int sum = (numeroAleatorioJogador) + (numeroAleatorioCPU);
        boolean isSumOdd = sum % 2 != 0; // se != de 0 é impar se == a 0 é par

        player1Wins = (respostaPlayer.equalsIgnoreCase("impar") && isSumOdd == true );

        String result = player1Wins ? "Você ganhou!" : "Servidor ganhou!";

        // Envia o resultado para os jogador
        sendMessage("Você escolheu " + respostaPlayer + " e seu número aleatório é " + numeroAleatorioJogador);
        sendMessage("Como o número aleatório d Servidor é " + numeroAleatorioCPU);
        sendMessage("O resultado foi " + sum);
        sendMessage(result);

    }

    private String requestAnswer() throws IOException {
        String resp;
        String requestAnswer = "|Request_Answer|";
        sendMessage(requestAnswer);
        resp = reader.readLine();
        return resp;
    }

    private void sendMessage(String message) {
        writer.println(message);
    }
    
}


/*
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
*/

// public void PlayerVSCPUSemSocket() throws IOException{
    //     Random random = new Random();
    //     int numeroAleatorio = random.nextInt(5) + 1;
        
    //     Scanner scanner = new Scanner(System.in);

    //     // Gera um número aleatório para a escolha do servidor (ímpar ou par)
    //     boolean serverIsOdd = random.nextBoolean();

    //     // Envia a escolha do servidor para os jogadores
    //     String serverChoice = serverIsOdd ? "ímpar" : "par";
    //     System.out.println("A soma deve ser: " + serverChoice);

    //     System.out.println("Escolha um número de 1 a 5");
    //     // Recebe as escolhas dos jogadores
    //     String player1Choice = scanner.nextLine();

    //     boolean canContinue = true;

    //     while (canContinue) {
    //         if (player1Choice.matches("\\d+") && Integer.parseInt(player1Choice) > 0 && Integer.parseInt(player1Choice) < 6) {
    //             canContinue = false;
    //         } else {
    //             System.out.println("Entrada inválida. Por favor, insira um número entre 1 e 5:");
    //             player1Choice = scanner.nextLine();
    //         }
    //     }

    //     // Verifica quem ganhou
    //     int sum = Integer.parseInt(player1Choice) + (numeroAleatorio);
    //     boolean isSumOdd = sum % 2 != 0;
    //     boolean player1Wins = (isSumOdd && serverIsOdd) || (!isSumOdd && !serverIsOdd);

    //     // Envia o resultado para os jogadores
    //     String result = player1Wins ? "Jogador 1 ganhou!" : "Servidor ganhou!";
    //     System.out.println("A soma deu "+ sum + "\n"+result);

    //     scanner.close();
         
    // }