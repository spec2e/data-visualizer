package dk.topdanmark.minecraft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by zapp on 15/11/15.
 */
public class CommandExecutor implements Runnable {

    private final Socket clientSocket;

    public CommandExecutor(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        System.out.println("Got a client !");

        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String command = in.readLine();
            System.out.println("command = " + command);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
