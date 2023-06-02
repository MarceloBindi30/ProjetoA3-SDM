package com.a3sdm.Jogos;


import javax.swing.*;

import com.a3sdm.Client.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.util.Random;

public class JogoDaVelha extends JFrame {
    private JButton[][] botoes;
    private char jogadorAtual;
    private boolean jogoAtivo;
    private Random rdm = new Random();
    GUI Menu;

    public JogoDaVelha() {
        super("Jogo da Velha");
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 3));

        //volta pro menu quando fecha o jogo
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                
                Menu = new GUI();
                Menu.setVisible(true);

            }

        });

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
                cpuJoga();
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

    private void cpuJoga(){
        int linha = 0;
        int coluna = 0;
        boolean flag = true;

        while(flag){
            linha = rdm.nextInt(3);
            coluna = rdm.nextInt(3);
            if (jogoAtivo && botoes[linha][coluna].getText().isEmpty()) {
                botoes[linha][coluna].setText(String.valueOf(jogadorAtual));
                flag = false;
                //System.out.println(linha + " " + coluna);  ***TESTE UNITÁRIO***
                verificarVencedor();
            }
        }
        //flag = true;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JogoDaVelha();
            }
        });
    }
}
