package Client;

import  javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener{
    
    public GUI(){
        JFrame frame = new JFrame();

        JButton butao1 = new JButton("Ímpar/par");
        JButton butao2 = new JButton("Jogo da velha");

        JLabel label1 = new JLabel("Seja bem-vindo a plataforma de jogos, selecione o jogo desejado: ");

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(label1);
        panel.add(butao1);
        panel.add(butao2);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Tic Poc Tic Brei Charlie Brown");
        frame.pack();
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}
