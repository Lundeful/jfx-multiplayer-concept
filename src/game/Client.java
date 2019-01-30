package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class Client implements Runnable {
    private int p1x;
    private int p1y;
    private int p2x;
    private int p2y;

    @Override
    public void run() {
        String hostName = "127.0.0.1";
        int portNumber = 5555; // Default port

        try(
                // create TCP socket for the given hostName, remote port PortNumber
                Socket clientSocket = new Socket(hostName, portNumber);
                // Stream writer to the socket
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                // Stream reader from the socket
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ){

            while (true) {
                out.println(p1x);
                out.println(p1y);
                p2x = in.read();
                p2y = in.read();
                TimeUnit.SECONDS.sleep(1);
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

    public int getP1x() {
        return p1x;
    }

    public int getP1y() {
        return p1x;
    }

    public int getP2x() {
        return p2x;
    }

    public int getP2y() {
        return p2y;
    }

    public void setP1x(int p1x) {
        this.p1x = p1x;
    }

    public void setP1y(int p1y) {
        this.p1y = p1y;
    }

    public void setP2x(int p2x) {
        this.p2x = p2x;
    }

    public void setP2y(int p2y) {
        this.p2y = p2y;
    }
}
