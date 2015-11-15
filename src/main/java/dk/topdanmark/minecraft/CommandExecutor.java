package dk.topdanmark.minecraft;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Map;

/**
 * Created by zapp on 15/11/15.
 */
public class CommandExecutor implements Runnable {

    private final Socket clientSocket;
    private final Player player;

    public CommandExecutor(Socket clientSocket, Player player) {
        this.clientSocket = clientSocket;
        this.player = player;
    }

    public void run() {
        System.out.println("Got a client !");

        try {

            Location location = player.getLocation();
            location.setZ(location.getZ() + 5);
            location.setY(location.getY() + 10);
            player.getWorld().spawn(location, Sheep.class);

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String command = in.readLine();
            System.out.println("command = " + command);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
