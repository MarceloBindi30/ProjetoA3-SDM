// package com.a3sdm.Server;

// import java.io.IOException;
// import java.net.Socket;

// import com.a3sdm.Util.Multiplayer;
// import com.a3sdm.Util.Player;


// public class ThreadRecepcao extends Thread{
//     Player p;
//     private Socket clientSocket;
//     Multiplayer lobby = new Multiplayer();

//     // public ThreadRecepcao(Socket clientSocket) throws IOException {
//     //     this.clientSocket = clientSocket;
//     // }

//     public ThreadRecepcao(Player p) throws IOException {
//         this.p = p;
//     }


//     @Override
//     public void run() {
//         ThreadSingle tSingle;
//       if(p.getNumbOfPlayers() == 2){
//         lobby.addPlayer(p);
//       }else{
//         tSingle = new ThreadSingle(p);
//         tSingle.run();
//       }
        
//     }

// }

