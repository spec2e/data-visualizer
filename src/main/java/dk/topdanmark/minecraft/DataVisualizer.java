package dk.topdanmark.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataVisualizer extends JavaPlugin implements Listener {

    private static Map<String, Player> playerList = new HashMap();

    private static ServerSocket socket;

    @Override
    public void onDisable() {
        getLogger().info("onDisable has been invoked!");
    }

    @Override
    public void onEnable() {
        getLogger().info("onEnable has been invoked!");
        getServer().getPluginManager().registerEvents(this, this);
        startServer();
    }

    private void startServer() {
        final ExecutorService commandProcessingPool = Executors.newFixedThreadPool(10);

        Runnable serverTask = new Runnable() {

            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(32990);
                    System.out.println("Waiting for clients to connect...");
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        commandProcessingPool.submit(new CommandExecutor(clientSocket));
                    }
                } catch (IOException e) {
                    System.err.println("Unable to process client request");
                    e.printStackTrace();
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("basic")) {
            getLogger().info("/basic command was entered!");
            return true;
        }
        return false;
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        getLogger().info("DataVisualizer.onPlayerMove");
        // Get the player's location.
        Location loc = event.getPlayer().getLocation();
        // Sets loc to five above where it used to be. Note that this doesn't change the player's position.
        loc.setZ(loc.getBlockZ() + 5);
        // Gets the block at the new location.
        Block b = loc.getBlock();
        // Sets the block to type id 1 (stone).
        b.setType(Material.STONE);
    }
}
