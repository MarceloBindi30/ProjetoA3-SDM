package com.a3sdm.Server;

import java.io.IOException;
import java.net.Socket;

import com.a3sdm.Jogos.ImparParSingle;
import com.a3sdm.Jogos.JogoDaVelha;
import com.a3sdm.Jogos.JogoDaVelhaPvP;


public class ThreadSingle extends Thread{
    private Socket player1;
    private int jogo;
    private JogoDaVelha jogoDaVelha;
    private JogoDaVelhaPvP jogoDaVelhaPvP;
    private ImparParSingle imparPar;

    //Se jogo = 1, então Impar ou Par, se jogo = 2, então jogo da velha
    
    public ThreadSingle(Socket player1,int jogo){
         this.player1 = player1;
         this.jogo = jogo;
    }

    public void ModoJogo(int jogo) throws IOException{
        if (jogo == 1){
                imparPar = new ImparParSingle(player1);
                imparPar.PlayerVSCPU();
        }else if(jogo == 2 ){
                jogoDaVelha = new JogoDaVelha(); // Abre um novo jogo da velha
        }else{
            System.out.println("Jogo não reconhecido");
        }
    }

    public void JogaNovamente(){
        
    } 
    
    @Override
    public void run() {
        try {
            ModoJogo(this.jogo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
