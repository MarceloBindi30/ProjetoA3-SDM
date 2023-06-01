package com.a3sdm.Jogos;


import javax.swing.*;

import com.a3sdm.Server.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.Random;

public class JogoDaVelhaPvP extends JFrame {
    private JButton[][] botoes;
    private char jogadorAtual;
    private boolean jogoAtivo;
    private Random rdm = new Random();
    private Socket player1;
    private Socket player2;
    
    public JogoDaVelhaPvP(Socket player12, Socket player22){
        this.player1 = player12;
        this.player2 = player22;

    }

    public JogoDaVelhaPvP() {
        super("Jogo da Velha");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 3));

        botoes = new JButton[3][3];
        jogadorAtual = 'X';
        jogoAtivo = true;

        // Criação dos botões e registro do ActionListener
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton botao = new JButton();
                botao.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
                botao.addActionListener(new BotaoListener(i, j));
                botoes[i][j] = botao;
                add(botao);
            }
        }

        setVisible(true);
    }

    private class BotaoListener implements ActionListener {
        private int linha;
        private int coluna;

        public BotaoListener(int linha, int coluna) {
            this.linha = linha;
            this.coluna = coluna;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (jogoAtivo && botoes[linha][coluna].getText().isEmpty()) {
                botoes[linha][coluna].setText(String.valueOf(jogadorAtual));
                verificarVencedor();
                trocarJogador();
            }
        }
    }

    private void verificarVencedor() {
        // Verificar linhas
        for (int i = 0; i < 3; i++) {
            if (botoes[i][0].getText().equals(botoes[i][1].getText()) &&
                botoes[i][0].getText().equals(botoes[i][2].getText()) &&
                !botoes[i][0].getText().isEmpty()) {
                jogoAtivo = false;
                JOptionPane.showMessageDialog(this, "Jogador " + jogadorAtual + " venceu!");
                reiniciarJogo();
                return;
            }
        }

        // Verificar colunas
        for (int i = 0; i < 3; i++) {
            if (botoes[0][i].getText().equals(botoes[1][i].getText()) &&
                botoes[0][i].getText().equals(botoes[2][i].getText()) &&
                !botoes[0][i].getText().isEmpty()) {
                jogoAtivo = false;
                JOptionPane.showMessageDialog(this, "Jogador " + jogadorAtual + " venceu!");
                reiniciarJogo();
                return;
            }
        }

        // Verificar diagonais
        if ((botoes[0][0].getText().equals(botoes[1][1].getText()) &&
             botoes[0][0].getText().equals(botoes[2][2].getText()) &&
             !botoes[0][0].getText().isEmpty()) ||
            (botoes[0][2].getText().equals(botoes[1][1].getText()) &&
             botoes[0][2].getText().equals(botoes[2][0].getText()) &&
             !botoes[0][2].getText().isEmpty())) {
            jogoAtivo = false;
            JOptionPane.showMessageDialog(this, "Jogador " + jogadorAtual + " venceu!");
            reiniciarJogo();
            return;
        }

        // Verificar empate
        boolean empate = true;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (botoes[i][j].getText().isEmpty()) {
                    empate = false;
                    break;
                }
            }
        }

        if (empate) {
            jogoAtivo = false;
            JOptionPane.showMessageDialog(this, "Empate!");
            reiniciarJogo();
        }
    }

    private void trocarJogador() {
        jogadorAtual = (jogadorAtual == 'X') ? 'O' : 'X';
    }


    private void reiniciarJogo() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botoes[i][j].setText("");
            }
        }
        jogadorAtual = 'X';
        jogoAtivo = true;
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(new Runnable() {
    //         public void run() {
    //             new JogoDaVelhaPvP();
    //         }
    //     });
    // }
}
