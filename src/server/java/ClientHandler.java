package server.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
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
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            // Reader to the socket
            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // game.game.Client IP address and port
            InetAddress clientAddr = clientSocket.getInetAddress();
            int clientPort = clientSocket.getPort();

            String line;
            while (true) {
                playerMessage = br.readLine();
                out.println(enemyMessage);
                TimeUnit.MILLISECONDS.sleep(10);
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

