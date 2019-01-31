package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client implements Runnable {
    private String playerMessage, enemyMessage;

    private BufferedReader br;
    private PrintWriter out;
    private boolean ready = false;
    Socket clientSocket;


    @Override
    public void run() {
        String hostName;

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter server IP address or press enter to use 127.0.0.1");
        String input = sc.nextLine();

        if (input.equals("")) hostName = input;
        else hostName = "127.0.0.1";

        int portNumber = 5555; // Default port

        try{
                // create TCP socket for the given hostName, remote port PortNumber
                clientSocket = new Socket(hostName, portNumber);
                // Stream writer to the socket
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                // Stream reader from the socket
                br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Ready to roll
                ready = true;
            while (true) {
                out.println(playerMessage);
                enemyMessage = br.readLine();
                TimeUnit.MILLISECONDS.sleep(10);
            }

        } catch (UnknownHostException e) {
            System.err.println("Unknown host " + hostName+"\n"+e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("IOException at " + hostName + "\n"+e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPlayerMessage(String playerMessage) {
        this.playerMessage = playerMessage;
    }

    public String getEnemyMessage() {
        return enemyMessage;
    }

    public boolean isReady() {
        return ready;
    }
}
