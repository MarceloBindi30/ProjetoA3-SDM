package com.a3sdm.Server;

import java.io.IOException;

import com.a3sdm.Jogos.ImparParSingle;
import com.a3sdm.Jogos.JogoDaVelha;
import com.a3sdm.Util.ClientHandler;
//import com.a3sdm.Util.Player;


public class ThreadSingle extends Thread{
    private ClientHandler handler;
    private ImparParSingle imparPar;
    
    public ThreadSingle(ClientHandler c1){
        this.handler = c1;
    }

    public void ModoJogo(int jogo) throws IOException{
        if (jogo == 1){
                imparPar = new ImparParSingle(handler.getPlayerSocket());
                imparPar.PlayerVSCPUOutroJeito();
        }else if(jogo == 2 ){
                new JogoDaVelha(); // Abre um novo jogo da velha
        }else{
            System.out.println("Jogo não reconhecido");
        }
    }
    
    @Override
    public void run() {
        try {
            ModoJogo(handler.getJogo());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
