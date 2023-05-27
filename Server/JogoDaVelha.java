package Server;

import java.net.Socket;

public class JogoDaVelha {
    private Socket player1;
    private Socket player2;

    public JogoDaVelha(Socket player1,Socket player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    public JogoDaVelha(Socket player1){
        this.player1 = player1;
    }

    public void PlayerVSCPU(){

    }

    public void PlayerVSPlayer(){

    }
}