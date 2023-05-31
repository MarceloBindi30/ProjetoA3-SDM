package com.a3sdm.Jogos;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class ImparPar {
    private Socket player1;
    private Socket player2;

    public ImparPar(Socket player1,Socket player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    public ImparPar(Socket player1){
        this.player1 = player1;
    }

    public void PlayerVSCPU() throws IOException{
        Random random = new Random();
        int numeroAleatorio = random.nextInt(5) + 1;
        
        sendMessage(player1,"Escolha um número de 1 a 5");
        
        // Obtém streams de entrada e saída para comunicação com os jogadores
        InputStream player1InputStream = player1.getInputStream();
        Scanner scanner = new Scanner(player1InputStream);
        // Gera um número aleatório para a escolha do servidor (ímpar ou par)
        boolean serverIsOdd = random.nextBoolean();

        // Envia a escolha do servidor para os jogadores
        String serverChoice = serverIsOdd ? "ímpar" : "par";
        sendMessage(player1,"A soma deve ser: " + serverChoice);

        boolean canContinue = true;
        String respostaPlayer = scanner.nextLine();

        while (canContinue) {
            if (respostaPlayer.matches("\\d+") && Integer.parseInt(respostaPlayer) > 0 && Integer.parseInt(respostaPlayer) < 6) {
                canContinue = false;
            } else {
                sendMessage(player1,"Entrada inválida. Por favor, insira um número entre 1 e 5:" + respostaPlayer);
                respostaPlayer = scanner.nextLine();
            }
        }

        // Verifica quem ganhou
        int sum = Integer.parseInt(respostaPlayer) + (numeroAleatorio);
        boolean isSumOdd = sum % 2 != 0;
        boolean player1Wins = (isSumOdd && serverIsOdd) || (!isSumOdd && !serverIsOdd);

        // Envia o resultado para os jogadores
        String result = player1Wins ? "Jogador 1 ganhou!" : "Servidor ganhou!";
        sum = Integer.parseInt(respostaPlayer) + (numeroAleatorio);
        sendMessage(player1,"A soma deu " + sum + "\n" + result);
        
        String message = "O que gostaria de fazer agora? \n" +
           "1 - Jogar Novamente \n" +
           "2 - Voltar para o menu principal \n" +
           "3 - Sair";
        sendMessage(player1,message);

        respostaPlayer = scanner.nextLine();
        canContinue = true;

        }


    public void PlayerVSPlayer() throws IOException{
        // Obtém streams de entrada e saída para comunicação com os jogadores
        InputStream player1InputStream = player1.getInputStream();
        OutputStream player1OutputStream = player1.getOutputStream();

        InputStream player2InputStream = player2.getInputStream();
        OutputStream player2OutputStream = player2.getOutputStream();

        // Gera um número aleatório para a escolha do servidor (ímpar ou par)
        Random random = new Random();
        boolean serverIsOdd = random.nextBoolean();

        // Envia a escolha do servidor para os jogadores
        String serverChoice = serverIsOdd ? "ímpar" : "par";
        player1OutputStream.write(serverChoice.getBytes());
        player2OutputStream.write(serverChoice.getBytes());

        // Recebe as escolhas dos jogadores
        byte[] buffer = new byte[1024];

        int bytesRead1 = player1InputStream.read(buffer);
        String player1Choice = new String(buffer, 0, bytesRead1);

        int bytesRead2 = player2InputStream.read(buffer);
        String player2Choice = new String(buffer, 0, bytesRead2);

        // Verifica quem ganhou
        int sum = Integer.parseInt(player1Choice) + Integer.parseInt(player2Choice);
        boolean isSumOdd = sum % 2 != 0;
        boolean player1Wins = (isSumOdd && serverIsOdd) || (!isSumOdd && !serverIsOdd);

        // Envia o resultado para os jogadores
        String result = player1Wins ? "Jogador 1 ganhou!" : "Jogador 2 ganhou!";
        player1OutputStream.write(result.getBytes());
        player2OutputStream.write(result.getBytes());

        // Fecha os sockets
        player1.close();
        player2.close();

    }

    public void PlayerVSCPUSemSocket() throws IOException{
        Random random = new Random();
        int numeroAleatorio = random.nextInt(5) + 1;
        
        Scanner scanner = new Scanner(System.in);

        // Gera um número aleatório para a escolha do servidor (ímpar ou par)
        boolean serverIsOdd = random.nextBoolean();

        // Envia a escolha do servidor para os jogadores
        String serverChoice = serverIsOdd ? "ímpar" : "par";
        System.out.println("A soma deve ser: " + serverChoice);

        System.out.println("Escolha um número de 1 a 5");
        // Recebe as escolhas dos jogadores
        String player1Choice = scanner.nextLine();

        boolean canContinue = true;

        while (canContinue) {
            if (player1Choice.matches("\\d+") && Integer.parseInt(player1Choice) > 0 && Integer.parseInt(player1Choice) < 6) {
                canContinue = false;
            } else {
                System.out.println("Entrada inválida. Por favor, insira um número entre 1 e 5:");
                player1Choice = scanner.nextLine();
            }
        }

        // Verifica quem ganhou
        int sum = Integer.parseInt(player1Choice) + (numeroAleatorio);
        boolean isSumOdd = sum % 2 != 0;
        boolean player1Wins = (isSumOdd && serverIsOdd) || (!isSumOdd && !serverIsOdd);

        // Envia o resultado para os jogadores
        String result = player1Wins ? "Jogador 1 ganhou!" : "Servidor ganhou!";
        System.out.println("A soma deu "+ sum + "\n"+result);

        scanner.close();
         
    }

    private static void sendMessage(Socket socket, String message) {
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
