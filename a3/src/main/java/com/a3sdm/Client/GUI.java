package com.a3sdm.Client;

import java.awt.Font;
import java.net.Socket;

import javax.swing.*;

//import Server.ImparPar;

public class GUI extends JFrame{

    private Socket player1;
    private Socket player2;
    JButton button1;
    JButton button2;
    JButton button3;
    JButton button4;
    JLabel label1;
    //private ImparPar ImparPar;

    GUI(){

        //Recepcionar
        label1 = new JLabel("Seja bem-vindo a plataforma de jogos, selecione o jogo!");
        label1.setBounds(250, 200, 1000, 100);
        label1.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));

        //button1
        button1 = new JButton();
        button1.setBounds(250, 300, 200, 100);
        button1.addActionListener(e -> System.out.println("pain"));
        button1.setText("Par ou Ímpar (Vs. Bot)");
        button1.setFocusable(false);
        //button2
        button2 = new JButton();
        button2.setBounds(250, 450, 200, 100);
        button2.addActionListener(e -> System.out.println("Vitin eh  gay"));
        button2.setText("Par ou Ímpar (Vs. Player)");
        button2.setFocusable(false);
        //button3
        button3 = new JButton();
        button3.setBounds(550, 300, 200, 100);
        button3.addActionListener(e -> System.out.println("Vitin eh  gay"));
        button3.setText("Jogo da Velha (Vs. Bot)");
        button3.setFocusable(false);
        //button4
        button4 = new JButton();
        button4.setBounds(550, 450, 200, 100);
        button4.addActionListener(e -> System.out.println("Vitin eh  gay"));
        button4.setText("Jogo da Velha (Vs. Player)");
        button4.setFocusable(false);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(1000,700);
		this.setVisible(true);
        this.add(button1);
        this.add(button2);
        this.add(button3);
        this.add(button4);
        this.add(label1);

    }

}
