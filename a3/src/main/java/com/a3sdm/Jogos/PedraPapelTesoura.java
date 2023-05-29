package com.a3sdm.Jogos;

public class PedraPapelTesoura {
// import java.io.;
// import java.net.;

// public class Client {
//     public static void main(String[] args) {
//         try {
//             // Criação da Porta para Conexão
//             Socket socket = new Socket("localhost", 1234);

//             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

//             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
//             System.out.print("Escolha!!!: (Pedra/Papel/Tesoura): ");
//             String playerChoice = userInput.readLine();

//             // Envie para o Servidor a escolha 
//             out.println(playerChoice);

//             // Recebe e mostra o Resultado
//             String result = in.readLine();
//             System.out.println("Result: " + result);

//             // Fecha o Socket
//             socket.close();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }


// import java.io.*;
// import java.net.*;

// public class Server {
//     public static void main(String[] args) {
//         try {
//             // Criação Server Socket
//             ServerSocket serverSocket = new ServerSocket(1234);
//             System.out.println("Servidor Iniciado, Aguardando por Jogadores...");

//             // Aceitar a Conexão dos Jogadores
//             Socket player1Socket = serverSocket.accept();
//             System.out.println("Jogador 1 Conectado.");

//             Socket player2Socket = serverSocket.accept();
//             System.out.println("Jogador 2 Conectado.");

//             // Comunicação com o Jogador 1
//             BufferedReader player1In = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
//             PrintWriter player1Out = new PrintWriter(player1Socket.getOutputStream(), true);

//             // Mesma coisa mas com o Jogador 2
//             BufferedReader player2In = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));
//             PrintWriter player2Out = new PrintWriter(player2Socket.getOutputStream(), true);

//             // Escolha do Jogador 1
//             String player1Choice = player1In.readLine();

//             // Escolha do Jogador 2
//             String player2Choice = player2In.readLine();

//             // Define o Vencedor
//             String result = determineWinner(player1Choice, player2Choice);

//             // Devolve o Resultado para os Jogadores
//             player1Out.println(result);
//             player2Out.println(result);

//             // Fecha os Sockets
//             player1Socket.close();
//             player2Socket.close();
//             serverSocket.close();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     // Pedra Papel e Tesoura 
//     private static String determineWinner(String player1Choice, String player2Choice) {
//         if (player1Choice.equals(player2Choice)) {
//             return "Empate!!";
//         } else if ((player1Choice.equals("rock") && player2Choice.equals("scissors"))
//                 || (player1Choice.equals("paper") && player2Choice.equals("rock"))
//                 || (player1Choice.equals("scissors") && player2Choice.equals("paper"))) {
//             return "Jogador 1 Vence!!";
//         } else {
//             return "Jogador 2 Vence!!";
//         }
//     }
// }
}
