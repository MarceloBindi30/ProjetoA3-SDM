package com.a3sdm.Client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
//import java.util.Timer;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.Timer;


public class PlayerVictor {
    private int jogo;
    private int numbOfPlayers;
    private Socket socketPlayer;

    public PlayerVictor() throws IOException {
        this.jogo = jogo; //recepcionar();
        this.numbOfPlayers = 1; //jogadores();
        this.socketPlayer = socketPlayer;
    }

    public int getJogo() {
        return jogo;
    }

    public int getNumbOfPlayers() {
        return numbOfPlayers;
    }

    public Socket getSocketPlayer() {
        return socketPlayer;
    }

    private int recepcionar() throws IOException {
        String message;
        String resposta;
        int jogo = 0;

        message = "Seja bem-vindo à plataforma de jogos!\n" +
                "Digite o número correspondente ao jogo que você deseja jogar:\n" +
                "1 - Ímpar ou Par\n" +
                "2 - Jogo da Velha";

        do {
            resposta = JOptionPane.showInputDialog(null, message);
            if (resposta == null) {
                // O jogador fechou a janela ou cancelou a operação
                System.exit(0);
            }

            if (!(resposta.equals("1")) && !(resposta.equals("2"))) {
                JOptionPane.showMessageDialog(null,
                        "Oh, oh! Parece que algo não está certo no reino dos bits e bytes!\n" +
                                "Parece que uma informação errada se perdeu na jogada.\n" +
                                "Vamos tentar novamente, mas dessa vez, com a mágica da precisão!");
            }

            if (resposta.equals("1")) {
                resposta = JOptionPane.showInputDialog(null,
                        "Parece que você quer jogar Ímpar ou Par, é isso mesmo?\n" +
                                "Digite 'S' para sim ou qualquer outra tecla para não!");
                jogo = 1;
            } else if (resposta.equals("2")) {
                resposta = JOptionPane.showInputDialog(null,
                        "Parece que você quer jogar Jogo da Velha, é isso mesmo?\n" +
                                "Digite 'S' para sim ou qualquer outra tecla para não!");
                jogo = 2;
            }
        } while (!resposta.equalsIgnoreCase("S"));

        return jogo;
    }

    private int jogadores() throws IOException {
        String message;
        String resposta;
        int numbOfPlayers = 0;

        message = "Você é um lobo solitário em busca de desafios ou quer mostrar suas habilidades desafiando alguém?\n" +
                "Digite 'S' para jogar sozinho(a) e 'M' para desafiar outro jogador!";

        do {
            resposta = JOptionPane.showInputDialog(null, message);
            if (resposta == null) {
                // O jogador fechou a janela ou cancelou a operação
                System.exit(0);
            }

            if (!resposta.equalsIgnoreCase("S") && !resposta.equalsIgnoreCase("M")) {
                JOptionPane.showMessageDialog(null,
                        "Eu não faço ideia do que isso significa, mas vamos tentar de novo!");
            }

            if (resposta.equalsIgnoreCase("S")) {
                resposta = JOptionPane.showInputDialog(null,
                        "Ah, um lobo solitário em busca de desafios!\n" +
                                "Essa é a escolha corajosa!\n" +
                                "Digite 'S' para sim ou qualquer outra tecla para não!");
                numbOfPlayers = 1;
            } else if (resposta.equalsIgnoreCase("M")) {
                resposta = JOptionPane.showInputDialog(null,
                        "Ah, um aventureiro pronto para mostrar suas habilidades e desafiar alguém!\n" +
                                "Essa é a escolha corajosa!\n" +
                                "Digite 'S' para sim ou qualquer outra tecla para não!");
                numbOfPlayers = 2;
            }
        } while (!resposta.equalsIgnoreCase("S"));

        return numbOfPlayers;
    }

    private static void sendMessage(Socket socket, String message) {
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // private static void showAnimatedDialog(String message, String gifPath) {
    //     JDialog dialog = new JDialog();
    //     dialog.setTitle("Caixa de Diálogo");
    //     dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    //     ImageIcon gifIcon = new ImageIcon(gifPath);
    //     JLabel gifLabel = new JLabel(gifIcon);

    //     JLabel messageLabel = new JLabel();

    //     JPanel panel = new JPanel();
    //     GroupLayout layout = new GroupLayout(panel);
    //     panel.setLayout(layout);
    //     layout.setAutoCreateGaps(true);
    //     layout.setAutoCreateContainerGaps(true);

    //     layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
    //             .addComponent(messageLabel)
    //             .addComponent(gifLabel));

    //     layout.setVerticalGroup(layout.createSequentialGroup()
    //             .addComponent(messageLabel)
    //             .addComponent(gifLabel));

    //     dialog.getContentPane().add(panel);

    //     dialog.pack();
    //     dialog.setLocationRelativeTo(null);
    //     dialog.setVisible(true);

    //     typeMessage(message, messageLabel);
    // }

    // private static void typeMessage(String message, JLabel label) {
    //     final int delay = 50; // Tempo de atraso entre cada caractere (em milissegundos)
    //     final int messageLength = message.length();
    //     final int lineBreakLimit = 10; // Limite de caracteres antes de pular uma linha
    //     final int[] index = { 0 };
    
    //     Timer timer = new Timer(delay, new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             if (index[0] < messageLength) {
    //                 String text = message.substring(0, index[0] + 1);
    //                 if (text.length() >= lineBreakLimit && text.charAt(text.length() - 1) == ' ') {
    //                     text += "<br>"; // Adiciona uma quebra de linha
    //                 }
    //                 label.setText("<html>" + text + "</html>"); // Define o texto como HTML para suportar a quebra de linha
    //                 index[0]++;
    //             } else {
    //                 ((Timer) e.getSource()).stop();
    //             }
    //         }
    //     });
    
    //     timer.start();
    // }

    // private static void showAnimatedDialog(String message, String gifPath) {
    //     JDialog dialog = new JDialog();
    //     dialog.setTitle("Caixa de Diálogo");
    //     dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    
    //     ImageIcon gifIcon = new ImageIcon(gifPath);
    //     JLabel gifLabel = new JLabel(gifIcon);
    
    //     JTextArea messageTextArea = new JTextArea();
    //     messageTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
    //     messageTextArea.setLineWrap(true);
    //     messageTextArea.setWrapStyleWord(true);
    //     messageTextArea.setEditable(false);
    //     messageTextArea.setBackground(dialog.getBackground());
    //     messageTextArea.setText("");
    
    //     JButton simButton = new JButton("Sim");
    //     JButton naoButton = new JButton("Não");
    
    //     JPanel panel = new JPanel();
    //     GroupLayout layout = new GroupLayout(panel);
    //     panel.setLayout(layout);
    //     layout.setAutoCreateGaps(true);
    //     layout.setAutoCreateContainerGaps(true);
    
    //     layout.setHorizontalGroup(layout.createParallelGroup()
    //             .addComponent(messageTextArea, GroupLayout.Alignment.CENTER)
    //             .addComponent(gifLabel, GroupLayout.Alignment.CENTER)
    //             .addGroup(layout.createSequentialGroup()
    //                     .addComponent(simButton)
    //                     .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
    //                     .addComponent(naoButton)));
    
    //     layout.setVerticalGroup(layout.createSequentialGroup()
    //             .addComponent(messageTextArea)
    //             .addComponent(gifLabel)
    //             .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
    //             .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
    //                     .addComponent(simButton)
    //                     .addComponent(naoButton)));
    
    //     dialog.getContentPane().add(panel);
    
    //     dialog.pack();
    //     dialog.setLocationRelativeTo(null);
    //     dialog.setVisible(true);
    
    //     typeMessage(message, messageTextArea);
    // }
    
    // private static void typeMessage(String message, JTextArea textArea) {
    //     final int delay = 100;
    //     final int messageLength = message.length();
    //     final int lineBreakLimit = 10;
    //     final int[] index = { 0 };
    
    //     Timer timer = new Timer(delay, new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             if (index[0] < messageLength) {
    //                 String text = message.substring(0, index[0] + 1);
    //                 if (text.length() >= lineBreakLimit && text.charAt(text.length() - 1) == ' ') {
    //                     text += "\n";
    //                 }
    //                 textArea.setText(text);
    //                 index[0]++;
    //             } else {
    //                 ((Timer) e.getSource()).stop();
    //             }
    //         }
    //     });
    
    //     timer.start();
    // }

    private static void showAnimatedDialog(String message, String gifPath) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Caixa de Diálogo");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    
        ImageIcon gifIcon = new ImageIcon(gifPath);
        JLabel gifLabel = new JLabel(gifIcon);
    
        JLabel messageLabel = new JLabel();
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    
        JButton simButton = new JButton("Sim");
        JButton naoButton = new JButton("Não");
    
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
    
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(messageLabel, GroupLayout.Alignment.CENTER)
                .addComponent(gifLabel, GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(simButton)
                        .addGap(18, 18, 18)
                        .addComponent(naoButton)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(messageLabel)
                .addComponent(gifLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(simButton)
                        .addComponent(naoButton)));
    
        dialog.getContentPane().add(panel);
    
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    
        typeMessage(message, messageLabel);
    }
    
    
    
    private static void typeMessage(String message, JLabel label) {
        final int delay = 100;
        final int messageLength = message.length();
        final int lineBreakLimit = 10;
        final int[] index = { 0 };
    
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index[0] < messageLength) {
                    String text = message.substring(0, index[0] + 1);
                    if (text.length() >= lineBreakLimit && text.charAt(text.length() - 1) == ' ') {
                        text += "<br>";
                    }
                    label.setText("<html>" + text + "</html>");
                    index[0]++;
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
    
        timer.start();
    }
    
    
    public static void main(String[] args) throws IOException {
        PlayerVictor teste = new PlayerVictor();
        teste.showAnimatedDialog("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","C:/Users/Victor/Downloads/teste.gif");
    }
 

}