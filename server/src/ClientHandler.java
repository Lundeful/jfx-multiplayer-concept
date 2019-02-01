import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;


public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private String playerMessage, enemyMessage;

    public ClientHandler(Socket socket) {
        clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            // Printer to the client socket
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            // Reader to the client socket
            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while (true) {
                playerMessage = br.readLine();
                out.println(enemyMessage);
                TimeUnit.MILLISECONDS.sleep(10); // Simulate delay
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getPlayerMessage() {
        return playerMessage;
    }

    public void setEnemyMessage(String enemyMessage) {
        this.enemyMessage = enemyMessage;
    }
}

