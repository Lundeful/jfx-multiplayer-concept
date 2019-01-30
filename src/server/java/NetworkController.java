package server.java;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class NetworkController {
    public static void main(String[] args) {
        NetworkController nw = new NetworkController();
        nw.run();
    }
    private Socket p1Socket;
    private Socket p2Socket;
    private int p1x, p1y, p2x, p2y; // Player mouse positions

    public void run() {
        int portNumber = 5555;

        System.out.println("Launching server...");

        try(
            //DatagramSocket serverSocket = new DatagramSocket(portNumber);
            ServerSocket serverSocket = new ServerSocket(portNumber)
        ){
            // Connect to player1
            System.out.println("Waiting for player 1...");
            p1Socket = serverSocket.accept();
            ClientHandler p1Handler = new ClientHandler(p1Socket);
            Thread p1Thread = new Thread(p1Handler);
            p1Thread.start();

            System.out.println("Player 1 connected!");
            System.out.println("Waiting for player 2..");

            // Connect to player2
            p2Socket = serverSocket.accept();
            System.out.println("Player 2 connected!");
            ClientHandler p2Handler = new ClientHandler(p2Socket);
            Thread p2Thread = new Thread(p2Handler);
            p2Thread.start();

            // game.game.Main server function
            // Send and retrieve coordinates
            while(true){
                this.p1x = p1Handler.getPlayerX();
                this.p1y = p1Handler.getPlayerY();

                this.p2x = p2Handler.getPlayerX();
                this.p2y = p2Handler.getPlayerY();

                System.out.println("P1: " + p1x + " " + p1y);
                System.out.println("P1: " + p2x + " " + p2y);
                TimeUnit.MILLISECONDS.sleep(50);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
