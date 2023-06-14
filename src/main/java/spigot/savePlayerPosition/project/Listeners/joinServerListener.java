package spigot.savePlayerPosition.project.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import spigot.savePlayerPosition.project.Main;

public class joinServerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.getLogger().info(JavaPlugin.getPlugin(Main.class).getName() + ": " + event.getPlayer().getDisplayName() + " has joined the server in world \"" + event.getPlayer().getWorld().getName() + "\"");
    }
}
