package com.a3sdm.Util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Player {
    private int jogo;
    private int numbOfPlayers;
    private Socket socketPlayer;

    public Player(Socket socketPlayer) throws IOException {
        this.jogo = Recepcionar(socketPlayer);
        this.numbOfPlayers = Jogadores(socketPlayer);
        this.socketPlayer = socketPlayer;
    }

    public int getJogo() {
        return jogo;
    }
    public int getNumbOfPlayers() {
        return numbOfPlayers;
    }

    public Socket getSocketPlayer() {
        return socketPlayer;
    }

    private static int Recepcionar(Socket playerSocket) throws IOException{
        String message,resposta;
        Scanner scanner;
        int jogo = 0; 

        InputStream playerInputStream = playerSocket.getInputStream();
        scanner = new Scanner(playerInputStream);

        message = "Seja bem-vindo a plataforma de jogos \n " +
                      "Digite o número correspondente do  qual jogo você deseja jogar: \n " +
                      "1 - Impar ou Par \n" +
                      "2 - Jogo da Velha";
        
            do{
                sendMessage(playerSocket,message);
                resposta = scanner.nextLine();
                if( !(resposta.equals("1")) && !(resposta.equals("2"))){
                    sendMessage(playerSocket,"Oh, oh! Parece que algo não está certo no reino dos bits e bytes!\n" +
                    "Parece que uma informação errada se perdeu na jogada.\n" +
                    "Vamos tentar novamente, mas dessa vez, com a mágica da precisão! ");
                }

                if (resposta.equals("1")){
                    sendMessage(playerSocket,"Parece que você quer jogar Impar ou Par, é isso mesmo?\n" +
                    "Digite:\n" +
                    "S - Se sim!" +
                    "Outro caractere - Se não!");
                    resposta = scanner.nextLine();  
                    jogo = 1;
                }else if (resposta.equals("2")){
                    sendMessage(playerSocket,"Parece que você quer jogar Jogo Da Velha, é isso mesmo?"+
                    "Digite:\n" +
                    "S - Se sim!" +
                    "Outro caractere - Se não!");
                    resposta = scanner.nextLine();
                    jogo = 2;
                }
            }while(!resposta.equals("S") && !resposta.equals("s"));

            return jogo;
    }

    private static int Jogadores (Socket playerSocket) throws IOException{
        String message,resposta;
        Scanner scanner;
        int numbOfPlayers = 0;

        InputStream playerInputStream = playerSocket.getInputStream();
        scanner = new Scanner(playerInputStream);

        message = "Você é um(a) lobo(a) solitário(a) em busca de desafios " + 
                  "ou quer mostrar suas habilidades desafiando alguém?\n " +
                  "Digite (S) para jogar sozinho(a) e (M) para desafiar outro jogador! \n ";
            do{
                sendMessage(playerSocket,message);
                resposta = scanner.nextLine();
                if(!resposta.equals("S") && !resposta.equals("M")){
                    sendMessage(playerSocket,"Eu não faço ideia o que isso significa, mas vamos tentar de novo");
                }

                if (resposta.equals("S")){
                    sendMessage(playerSocket,"Ah, um(a) lobo(a) solitário(a) em busca de desafios! \n" +
                    "Essa é a escolha corajosa! \n" +
                    "Digite:\n" +
                    "S - Se sim!" +
                    "Outro caractere - Se não!");   
                    numbOfPlayers = 1;
                }else if (resposta.equals("M")){
                    sendMessage(playerSocket,"Ah, um(a) aventureiro(a) pronto(a) para mostrar suas habilidades e desafiar alguém! \n"+
                    "Essa é a escolha corajosa! \n" +
                    "Digite:\n" +
                    "S - Se sim!" +
                    "Outro caractere - Se não!");
                    numbOfPlayers = 2;
                }
            }while( !resposta.equals("S") && !resposta.equals("s") && !resposta.equals("M") && !resposta.equals("m"));

            return numbOfPlayers;
    }

    private static void sendMessage(Socket socket, String message) {
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
