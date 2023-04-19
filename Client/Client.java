package Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

//import Util.MsgRequest;
//import Util.MsgResponse;
//import Util.Status;

public class Client {
    public static void main(String[] args) {
        Socket socket;
        final String HOST = "Localhost"; //HOT LOCAL
        final int PORT = 12345; // PORTA DE CONEXÃO COM SERVER
        //double value1, value2;
        //char oper;
        Scanner sc = new Scanner(System.in);

        try {
            socket = new Socket(HOST, PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream()); //OUTPUT
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());  //INPUT

            /* ETAPA DE VERIFICAÇÃO CALCULADORA (TIRAR)
            System.out.println("Digite a operação (+, -, *, /): ");
            oper = sc.nextLine().charAt(0); //pega apenas o primeiro caracter
            System.out.println("Digite o primeiro valor: ");
            value1 = Double.parseDouble(sc.nextLine()); 
            System.out.println("Digite o segundo valor: ");
            value2 = Double.parseDouble(sc.nextLine()); 

           
            MsgRequest request = new MsgRequest(value1, value2, oper);
            out.writeObject(request);

            MsgResponse response = (MsgResponse) in.readObject();
            
            if(response.getStatus() == Status.SUCESSO){
                System.out.println("Resposta: " + response.getValue());
            }else{
                if (response.getStatus() == Status.DIVISAO_ZERO) {
                    System.out.println("Erro. Divisão por zero");
                }else{
                    System.out.println("Operador Inválido");
                }
            }
            */

            socket.close();
            sc.close();
            in.close();
            out.close();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
