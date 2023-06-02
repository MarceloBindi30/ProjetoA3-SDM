package com.a3sdm.Jogos;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.a3sdm.Client.GUI;

public class ImparParSingle extends JFrame implements ActionListener{
    private Socket player1;
    private Socket player2;
    Random random = new Random();
    private JTextField campoTexto;
    GUI Menu;
    JButton butaum;
    private JLabel pc;
    private int numPlayer;
    private JLabel result;
    private int numPC = random.nextInt(5) + 1;

    // public ImparPar(Socket player1,Socket player2){
    //     this.player1 = player1;
    //     this.player2 = player2;
    // }
    public ImparParSingle(){
        super("Par ou Ímpar");
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
		setLayout(new GridLayout(4, 1));
		setSize(300,250);
        
        //volta pro menu quando fecha o jogo
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                
                Menu = new GUI();
                Menu.setVisible(true);

            }

        });

        campoTexto = new JTextField(); 
        pc = new JLabel();
        result = new JLabel();
        butaum = new JButton("Responder");

        butaum.setVisible(true);
        butaum.addActionListener(this);
        // butaum.addActionListener(e -> pc.setVisible(true));
        // butaum.addActionListener(e -> result.setVisible(true));
        butaum.setFocusable(false);

        pc.setText(Integer.toString(numPC));
        pc.setVisible(false);
        result.setVisible(false);
        result.setText("");

        campoTexto.setVisible(true);
        
        add(campoTexto);
        add(butaum);
        add(pc);
        add(result);

        

        // if ((numPC + numPlayer) % 2 == 0 ){
        //     result.setText("Par!");
        // }
        // else{
        //     result.setText("Ímpar!");
        // }

        }
    

    public ImparParSingle(Socket player1){
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


    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == butaum){

            pc.setVisible(true);

            numPlayer = Integer.parseInt(campoTexto.getText());

            if ((numPC + numPlayer) % 2 == 0 ){
                result.setText("Par!");
            }
            else{
                result.setText("Ímpar!");
            }

            result.setVisible(true);
            
            numPC = 0;
        }
        
    }

}
