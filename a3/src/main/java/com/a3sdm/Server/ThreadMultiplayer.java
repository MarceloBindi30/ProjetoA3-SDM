package com.a3sdm.Server;

import java.io.IOException;
import java.net.Socket;

import com.a3sdm.Jogos.ImparParPVP;
import com.a3sdm.Util.Player;

public class ThreadMultiplayer extends Thread{
    private int Jogo;
    private Player player1;
    private Player player2;
    private ImparParPVP imparPar;

    public ThreadMultiplayer(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        this.Jogo = player1.getJogo();
        
    }

    private void ModoJogoPVP() throws IOException{
        if(Jogo == 1){
            imparPar = new ImparParPVP(player1.getSocketPlayer(),player2.getSocketPlayer());
            imparPar.PlayerVSPlayer();
        }else if(Jogo == 2){

        }else if(Jogo == 3){

        }else{
            System.out.println("Jogo não reconhecido");
        }
    }

    @Override
    public void run() {
        
    }


    //RECEBER
    // 2 PLAYERS OU SOCKETS
    // O JOGO QUE SERA JOGADO
    // PORTA

    //VERIFICAR O JOGO E CHAMAR CLASSE
    /*
    EX:
    
    JOGO 1
    CHAMA CLASSE IMPARPARPVP();
    
     
     */
}
