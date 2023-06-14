package spigot.savePlayerPosition.project.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import spigot.savePlayerPosition.project.Main;

public class leaveServerListener implements Listener {
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Bukkit.getLogger().info(JavaPlugin.getPlugin(Main.class).getName() + ": " + event.getPlayer().getDisplayName() + " has left the server from world " + "\"" + event.getPlayer().getWorld().getName() + "\"");
    }
}