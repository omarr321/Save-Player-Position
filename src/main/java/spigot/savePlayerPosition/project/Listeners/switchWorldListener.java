package spigot.savePlayerPosition.project.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.java.JavaPlugin;
import spigot.savePlayerPosition.project.Main;

public class switchWorldListener implements Listener {

    @EventHandler
    public void onPlayerWorldSwitch(PlayerChangedWorldEvent event) {
        Bukkit.getLogger().info(JavaPlugin.getPlugin(Main.class).getName() + ": " + event.getPlayer().getDisplayName() + " moved from \"" + event.getFrom().getName() + "\" to \"" + event.getPlayer().getWorld().getName() + "\"");
    }
}
