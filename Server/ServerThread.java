package Server;

import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread{
    private Socket player1;
    private Socket player2;
    private int jogo;
    private ImparPar imparPar;
    private JogoDaVelha jogoDaVelha;

    //Se jogo = 1, então Impar ou Par, se jogo = 2, então jogo da velha
    public ServerThread(Socket player1, Socket player2,int jogo){
        this.player1 = player1;
        this.player2 = player2;
        this.jogo = jogo;
    }

    public ServerThread(Socket player1,int jogo){
        this.player1 = player1;
        this.jogo = jogo;
    }

    public void ModoJogo(int jogo) throws IOException{
        if (jogo == 1){
            if (player2 == null){
                imparPar = new ImparPar(player1);
                imparPar.PlayerVSCPU();
            }else{
                imparPar = new ImparPar(player1, player2);
                imparPar.PlayerVSPlayer();
            }
        }else if(jogo == 2 ){
            if (player2 == null){
                jogoDaVelha = new JogoDaVelha(player1);
                jogoDaVelha.PlayerVSCPU();
            }else{
                jogoDaVelha = new JogoDaVelha(player1, player2);
                jogoDaVelha.PlayerVSPlayer();
            }
        }else{
            System.out.println("Jogo não reconhecido");
        }
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
