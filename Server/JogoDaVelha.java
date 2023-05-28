package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;

public class JogoDaVelha {
    private Socket player1;
    private Socket player2;
    private String[] board;
    boolean checkWinner = false;
    String result = " ";
    Board painel = new Board();

    public JogoDaVelha(Socket player1,Socket player2){
        this.player1 = player1;
        this.player2 = player2;
        this.board = new String[9];
    }

    public JogoDaVelha(Socket player1){
        this.player1 = player1;
    }

    public void PlayerVSCPU() throws IOException{
        Random random = new Random(9);

        // Obtém streams de entrada e saída para comunicação com os jogadores
        InputStream player1InputStream = player1.getInputStream();
        OutputStream player1OutputStream = player1.getOutputStream();
        boardWrite(board);

        while(checkWinner == false){

            // Gera um número aleatório para a escolha do servidor (0 - 8)
            int serverPick = random.nextInt();

            // Envia a escolha do servidor para o board
            board[serverPick] = "X";

            //Envia a escolha do servidor par o jogador
            boardWrite(board);
            // Recebe as escolhas dos jogadores
            

            // Verifica quem ganhou e Envia o resultado para o jogadore
            checkWinner = checkWinner(board, result);
            if (result.equals("X")){
                result = "Servidor ganhou!";
            }else if (result.equals("O")){
                result = "Jogador 1 ganhou!";
            }else{
                result = "Empate!";
            }
            player1OutputStream.write(result.getBytes());

            // Fecha os sockets
            player1.close();
        }
    }

    public void PlayerVSPlayer(){

    }

    private void boardWrite(String[] board){
        painel.atualizarPainel(board);
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

    
}