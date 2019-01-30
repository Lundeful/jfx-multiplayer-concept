package server.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private int playerX;
    private int playerY;
    private int enemyX;
    private int enemyY;

    public ClientHandler(Socket socket) {
        clientSocket = socket;
        this.playerX = 0;
        this.playerY = 0;
        this.enemyX = 0;
        this.enemyY = 0;
    }

    @Override
    public void run() {
        try {
            // Printer to the socket with charset unavailable in java 8
            //PrintWriter printOut = new PrintWriter(clientSocket.getOutputStream(), true, StandardCharsets.UTF_8);
            PrintWriter printOut = new PrintWriter(clientSocket.getOutputStream(), true);
            // Reader to the socket
            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // game.game.Client IP address and port
            InetAddress clientAddr = clientSocket.getInetAddress();
            int clientPort = clientSocket.getPort();

            String line;
            while (true) {
                // Get player position
                playerX = Integer.parseInt(br.readLine());
                playerY = Integer.parseInt(br.readLine());

                // Send enemy position
                printOut.println(enemyX);
                printOut.println(enemyY);

                // Wait a bit
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public int getEnemyX() {
        return enemyX;
    }

    public int getEnemyY() {
        return enemyY;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public void setEnemyX(int enemyX) {
        this.enemyX = enemyX;
    }

    public void setEnemyY(int enemyY) {
        this.enemyY = enemyY;
    }
}
