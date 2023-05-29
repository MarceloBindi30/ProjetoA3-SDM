package JogoDaVelha;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;

public class JogoDaVelha {
    private Socket player1;
    private Socket player2;
    
    boolean checkWinner = false;
    String result = " ";
    int turno = 0;
    boolean flag = false;


    public JogoDaVelha(Socket player1,Socket player2){
        this.player1 = player1;
        this.player2 = player2;
    }
    
    public JogoDaVelha(Socket player1){
        this.player1 = player1;
    }

    public void PlayerVSCPU() throws IOException{
        Random random = new Random();
        int pick;
        boolean flag = false;
        String[] board = {"-","-","-","-","-","-","-","-","-"};
        Board teste = new Board(board);

        while(checkWinner == false){
            // Gera um número aleatório para a escolha do servidor
            pick = random.nextInt(9);
            do{
                if(verificaJogada(board, pick)){
                    board[pick] = "X";
                    flag = true;
                }else{
                 System.out.println("Jogada inválida");
                }
            }
            while(flag == false);

            String[] board = {"-","X","-","-","-","-","-","-","-"};


            teste.attBoard(board);

            do{
                if(verificaJogada(board, pick)){
                    board[pick] = "X";
                    flag = true;
                }else{
                 System.out.println("Jogada inválida");
                }
            }
            while(flag == false);
            
            // Envia a escolha do servidor para os jogadores
            //player1OutputStream.write(serverPick);
 
            // Recebe as escolhas dos jogadores
            //int player1Choice 
 
        
            // Verifica quem ganhou e Envia o resultado para o jogador
            checkWinner = checkWinner(board, result);
            if (result.equals("X")){
                result = "Servidor ganhou!";
            }else if (result.equals("O")){
                result = "Jogador 1 ganhou!";
            }else{
                result = "Empate!";
            }
            //player1OutputStream.write(result.getBytes());

            // Fecha os sockets
            player1.close();
        }
    }

    public void PlayerVSPlayer(){

    }

    private boolean checkWinner(String[] board, String result){
        for (int a = 0; a < 8; a++) {
            String line = null;
 
            switch (a) {
            case 0:
                line = board[0] + board[1] + board[2];
                break;
            case 1:
                line = board[3] + board[4] + board[5];
                break;
            case 2:
                line = board[6] + board[7] + board[8];
                break;
            case 3:
                line = board[0] + board[3] + board[6];
                break;
            case 4:
                line = board[1] + board[4] + board[7];
                break;
            case 5:
                line = board[2] + board[5] + board[8];
                break;
            case 6:
                line = board[0] + board[4] + board[8];
                break;
            case 7:
                line = board[2] + board[4] + board[6];
                break;
            }
            //Vitória do servidor
            if (line.equals("XXX")) {
                result = "X";
                return true;
            }
             
            //Vitória do player
            else if (line.equals("OOO")) {
                result = "O";
                return true;
            }
        }
         
        for (int a = 0; a < 9; a++) {
            if (Arrays.asList(board).contains(
                    String.valueOf(a + 1))) {
                break;
            }
            else if (a == 8) {
                //empate
                return true;
            }
        }
        return false;
    }

    public boolean verificaJogada(String[] board, int pick){
        if(board[pick].equals("-")){
            return true;
        }else{
            return false;
        }
    }

}