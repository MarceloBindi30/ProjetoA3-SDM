package com.a3sdm.Jogos;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class ImparParSingle extends JFrame{
    private Socket player1;
    private JTextField campoTexto;
    private BufferedReader reader;
    private PrintWriter writer;
    
    // public ImparPar(Socket player1,Socket player2){
    //     this.player1 = player1;
    //     this.player2 = player2;
    // }

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
        this.player1 = player1;
        this.reader = new BufferedReader(new InputStreamReader(player1.getInputStream()));
        this.writer = new PrintWriter(player1.getOutputStream(), true);
    }


    public void PlayerVSCPU() throws IOException{
        Random random = new Random();
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
        String result = player1Wins ? "Jogador 1 ganhou!" : "Servidor ganhou!";
        sum = Integer.parseInt(respostaPlayer) + (numeroAleatorio);
        sendMessage("A soma deu " + sum + "\n" + result);
        
        // String message = "O que gostaria de fazer agora? \n" +
        //    "1 - Jogar Novamente \n" +
        //    "2 - Voltar para o menu principal \n" +
        //    "3 - Sair";
        // sendMessage(message);

        // respostaPlayer = requestAnswer();
        // canContinue = true;

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
