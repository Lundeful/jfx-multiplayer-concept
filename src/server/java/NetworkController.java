package server.java;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class NetworkController implements Runnable{
    public static void main(String[] args) {
        NetworkController nw = new NetworkController();
        new Thread(nw).start();
    }
    private Socket p1Socket;
    private Socket p2Socket;
    private String p1message, p2message;

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

            // Main server function
            // Send and retrieve coordinates
            while(true){
                // Get values from both clients
                p1message = p1Handler.getPlayerMessage();
                p2message = p2Handler.getPlayerMessage();
                p1Handler.setEnemyMessage(p2message);
                p2Handler.setEnemyMessage(p1message);

                //System.out.println("P1 message: " + p1message);
                //System.out.println("P2 message: " + p2message);

                TimeUnit.MILLISECONDS.sleep(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
