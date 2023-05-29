package com.a3sdm.JogoDaVelha;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BotaoListener implements ActionListener {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
}