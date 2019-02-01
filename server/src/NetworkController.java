import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class NetworkController implements Runnable{
    public static void main(String[] args) {
        NetworkController nw = new NetworkController();
        new Thread(nw).start();
    }

    public void run() {
        int portNumber = 5555;

        System.out.println("Launching server...");

        try(
            //DatagramSocket serverSocket = new DatagramSocket(portNumber);
            ServerSocket serverSocket = new ServerSocket(portNumber)
        ){
            // Connect to player1
            System.out.println("Waiting for player 1...");
            Socket p1Socket = serverSocket.accept();
            ClientHandler p1Handler = new ClientHandler(p1Socket);
            Thread p1Thread = new Thread(p1Handler);
            p1Thread.start();

            System.out.println("Player 1 connected!");
            System.out.println("Waiting for player 2..");

            // Connect to player2
            Socket p2Socket = serverSocket.accept();
            System.out.println("Player 2 connected!");
            ClientHandler p2Handler = new ClientHandler(p2Socket);
            Thread p2Thread = new Thread(p2Handler);
            p2Thread.start();

            // Main server function
            // Send and retrieve coordinates
            while(true){
                String p1message = p1Handler.getPlayerMessage();
                String p2message = p2Handler.getPlayerMessage();
                p1Handler.setEnemyMessage(p2message);
                p2Handler.setEnemyMessage(p1message);

                TimeUnit.MILLISECONDS.sleep(10); // Simulate delay
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
