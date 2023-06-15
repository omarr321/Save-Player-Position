package spigot.savePlayerPosition.project.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import spigot.savePlayerPosition.project.sppDebugger;

public class leaveServerListener implements Listener {
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        sppDebugger.log(event.getPlayer().getDisplayName() + "(" + event.getPlayer().getUniqueId() + ")" + " has left the server from world " + "\"" + event.getPlayer().getWorld().getName() + "\"");
    }
}