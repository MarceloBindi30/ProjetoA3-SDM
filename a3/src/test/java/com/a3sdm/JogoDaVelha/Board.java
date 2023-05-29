/*
 *
 * 
 * 
 * 
 **************  
 * ABANDONADO *
 **************
 *
 *
 * 
 * 
 * 
 * 
*/

package com.a3sdm.JogoDaVelha;

import java.awt.Font;
import javax.swing.*;

public class Board extends JFrame{

    JButton button1;
    JButton button2;
    JButton button3;
    JButton button4;
    JButton button5;
    JButton button6;
    JButton button7;
    JButton button8;
    JButton button9;
    JLabel label1;
    String[] board;
    String pick;

    public Board(String[] board){
        this.board = board;
    }

    public void attBoard(String[] board){
        
    }


    Board(){

        //Recepcionar
        label1 = new JLabel("Sua vez de jogar!");
        label1.setBounds(250, 200, 1000, 100);
        label1.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));

        //button1
        button1 = new JButton();
        button1.setBounds(300, 400,  100, 50);
        button1.addActionListener(e -> board[0] = pick);
        button1.setText(board[0]);
        button1.setFocusable(false);

        //button2
        button2 = new JButton();
        button2.setBounds(400, 400,  100, 50);
        button2.addActionListener(e -> System.out.println("Vitin eh  gay"));
        button2.setText(board[1]);
        button2.setFocusable(false);

        //button3
        button3 = new JButton();
        button3.setBounds(500, 400,  100, 50);
        button3.addActionListener(e -> System.out.println("Vitin eh  gay"));
        button3.setText(board[2]);
        button3.setFocusable(false);

        //button4
        button4 = new JButton();
        button4.setBounds(300, 300, 100, 50);
        button4.addActionListener(e -> System.out.println("Vitin eh  gay"));
        button4.setText(board[3]);
        button4.setFocusable(false);
        
        //button5
        button5 = new JButton();
        button5.setBounds(400, 300, 100, 50);
        button5.addActionListener(e -> System.out.println("Vitin eh  gay"));
        button5.setText(board[4]);
        button5.setFocusable(false);
        
        //button6
        button6 = new JButton();
        button6.setBounds(500, 300, 100, 50);
        button6.addActionListener(e -> System.out.println("Vitin eh  gay"));
        button6.setText(board[5]);
        button6.setFocusable(false);

        //button7
        button7 = new JButton();
        button7.setBounds(300, 200, 100, 50);
        button7.addActionListener(e -> System.out.println("Vitin eh  gay"));
        button7.setText(board[6]);
        button7.setFocusable(false);
        
        //button8
        button8 = new JButton();
        button8.setBounds(400, 200,  100, 50);
        button8.addActionListener(e -> System.out.println("Vitin eh  gay"));
        button8.setText(board[7]);
        button8.setFocusable(false);

        //button9
        button9 = new JButton();
        button9.setBounds(500, 200, 100, 50);
        button9.addActionListener(e -> System.out.println("Vitin eh  gay"));
        button9.setText(board[8]);
        button9.setFocusable(false);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(1000,700);
		this.setVisible(true);
        this.add(button1);
        this.add(button2);
        this.add(button3);
        this.add(button4);
        this.add(button5);
        this.add(button6);
        this.add(button7);
        this.add(button8);
        this.add(button9);
        this.add(label1);

    }
}