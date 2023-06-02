// package com.a3sdm.Jogos;
// import java.awt.Color;
// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStream;
// import java.io.InputStreamReader;
// import java.io.OutputStream;
// import java.io.PrintWriter;
// import java.net.Socket;
// import java.util.Random;

// import javax.swing.JFrame;
// import javax.swing.JTextField;

// import com.a3sdm.Util.ClientHandler;

// public class ImparParPVP extends JFrame{
//     private ClientHandler player1;
//     private ClientHandler player2;
//     private JTextField campoTexto;
//     private BufferedReader reader1, reader2;
//     private PrintWriter writer1, writer2;

//     public ImparParPVP(){
//         super("Par ou Ímpar");
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// 		setLayout(null);
// 		setSize(1000,700);
//         setBackground(Color.cyan); 
//         campoTexto = new JTextField(); 
//         campoTexto.setVisible(true); 
//         campoTexto.setBounds(250, 450, 200, 100);
//         setLocationRelativeTo(null);
//         add(campoTexto);

//         setVisible(true); 
//     }

//     public ImparParPVP (ClientHandler player1, ClientHandler player2) throws IOException{
//         this.player1 = player1;
//         this.player2 = player2;
//     }

//     public void PlayerVSPlayer() throws IOException{
//         String choice;
//         while ((choice = requestAnswer()) != null) {
//             if (choice.isEmpty()) {
//                 continue;
//             }

//             if (player1.getIsPlayerTurn()) {
//                 if (choice.equalsIgnoreCase("par") || choice.equalsIgnoreCase("impar")) {
//                     handlePlayerGuess(choice);
//                 } else if (choice.equalsIgnoreCase("cansei")) {
//                     sendMessage("Tá sei... Cansou nada... Você arregou isso sim! rsrs");
//                     break;
//                 } else {
//                     sendMessage("Opção inválida seu oreia seca!!!");
//                     sendMessage("As opções válidas são: par e impar");
//                     sendMessage("Ou digite 'cansei' para sair");
//                 }
//             } else {
//                 sendMessage("Opa! Não é sua vez oreia! Não seja ansioso rsrs");
//             }
//         }

//     }

//     private void handlePlayerGuess(String guess) {
//         int playerNumber = generateRandomNumber();
//         int opponentNumber = player1.getOpponent().generateRandomNumber();
//         Long opponentName = player1.getOpponent().getId();
//         int sum = playerNumber + opponentNumber;
//         String youWonMessage = "Parabéns! Você ganhou!!!";
//         String youLostMessage = "Xiiiiii! Você perdeu!!!";

//         String result = (sum % 2 == 0) ? "par" : "impar";
//         sendMessage("Você escolheu " + guess + " e seu número aleatório é " + playerNumber);
//         sendMessage("Como o número aleatório do jogador " + opponentName + " é " + opponentNumber);
//         sendMessage("O resultado foi " + result);

//         player1.getOpponent().sendMessage(player1 + " escolheu " + guess + " e o número aleatório dele é " + playerNumber);
//         player1.getOpponent().sendMessage("Como o seu número aleatório é " + opponentNumber);
//         player1.getOpponent().sendMessage("O resultado foi " + result);

//         if (guess.equalsIgnoreCase(result)) {
//             sendMessage(youWonMessage);
//             player1.getOpponent().sendMessage(youLostMessage);
//         } else {
//             sendMessage(youLostMessage);
//             player1.getOpponent().sendMessage(youWonMessage);
//         }

//         isPlayerTurn = false;
//         opponent.setPlayerTurn(true);
//     }


//     private void sendMessage(String message) {
//         writer.println(message);
//     }
    
//     private String requestAnswer() throws IOException {
//         String resp;
//         String requestAnswer = "|Request_Answer|";
//         sendMessage(requestAnswer);
//         resp = reader.readLine();
//         return resp;
//     }

//     private int generateRandomNumber() {
//         return (int) (Math.random() * 6) + 1;
//     }

// }



    // public void PlayerVSCPUSemSocket() throws IOException{
    //     Random random = new Random();
    //     int numeroAleatorio = random.nextInt(5) + 1;
        
    //     Scanner scanner = new Scanner(System.in);

    //     // Gera um número aleatório para a escolha do servidor (ímpar ou par)
    //     boolean serverIsOdd = random.nextBoolean();

    //     // Envia a escolha do servidor para os jogadores
    //     String serverChoice = serverIsOdd ? "ímpar" : "par";
    //     System.out.println("A soma deve ser: " + serverChoice);

    //     System.out.println("Escolha um número de 1 a 5");
    //     // Recebe as escolhas dos jogadores
    //     String player1Choice = scanner.nextLine();

    //     boolean canContinue = true;

    //     while (canContinue) {
    //         if (player1Choice.matches("\\d+") && Integer.parseInt(player1Choice) > 0 && Integer.parseInt(player1Choice) < 6) {
    //             canContinue = false;
    //         } else {
    //             System.out.println("Entrada inválida. Por favor, insira um número entre 1 e 5:");
    //             player1Choice = scanner.nextLine();
    //         }
    //     }

    //     // Verifica quem ganhou
    //     int sum = Integer.parseInt(player1Choice) + (numeroAleatorio);
    //     boolean isSumOdd = sum % 2 != 0;
    //     boolean player1Wins = (isSumOdd && serverIsOdd) || (!isSumOdd && !serverIsOdd);

    //     // Envia o resultado para os jogadores
    //     String result = player1Wins ? "Jogador 1 ganhou!" : "Servidor ganhou!";
    //     System.out.println("A soma deu "+ sum + "\n"+result);

    //     scanner.close();
         
    // }
