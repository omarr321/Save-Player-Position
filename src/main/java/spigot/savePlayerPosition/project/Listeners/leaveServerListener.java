package spigot.savePlayerPosition.project.Listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import spigot.savePlayerPosition.project.Tools.sppDebugger;
import spigot.savePlayerPosition.project.Tools.playerDataManager;

public class leaveServerListener implements Listener {
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        sppDebugger.log(event.getPlayer().getDisplayName() + "(" + event.getPlayer().getUniqueId() + ")" + " has left the server from world " + "\"" + event.getPlayer().getWorld().getName() + "\"");
        Location tempLoc = event.getPlayer().getLocation();
        sppDebugger.log(event.getPlayer().getDisplayName() + " cords: " + tempLoc.getX() + ", " + tempLoc.getY() + ", " + tempLoc.getZ());
        playerDataManager.saveData(event.getPlayer().getUniqueId().toString(), event.getPlayer().getWorld().getName(), tempLoc.getX(), tempLoc.getY(), tempLoc.getZ());
    }
}