package Client;

import java.awt.Font;
import javax.swing.*;

public class GUI extends JFrame{

    JButton button1;
    JButton button2;
    JLabel label1;

    GUI(){

        //Recepcionar
        label1 = new JLabel("Seja bem-vindo a plataforma de jogos, selecione o jogo!");
        label1.setBounds(250, 200, 1000, 100);
        label1.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));

        //button1
        button1 = new JButton();
        button1.setBounds(400, 300, 200, 100);
        button1.addActionListener(e -> System.out.println("Vitin eh  gay"));
        button1.setText("Par ou Ímpar");
        button1.setFocusable(false);
        //button2
        button2 = new JButton();
        button2.setBounds(400, 450, 200, 100);
        button2.addActionListener(e -> System.out.println("Vitin eh  gay"));
        button2.setText("Jogo da velha");
        button2.setFocusable(false);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(1000,700);
		this.setVisible(true);
        this.add(button1);
        this.add(button2);
        this.add(label1);

    }

}
