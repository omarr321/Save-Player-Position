package spigot.savePlayerPosition.project.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import spigot.savePlayerPosition.project.sppDebugger;

public class joinServerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        sppDebugger.log(event.getPlayer().getDisplayName() + "(" + event.getPlayer().getUniqueId() + ")" + " has joined the server in world \"" + event.getPlayer().getWorld().getName() + "\"");
    }
}
