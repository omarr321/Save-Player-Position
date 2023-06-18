package spigot.savePlayerPosition.project.Listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import spigot.savePlayerPosition.project.Tools.sppDebugger;
import spigot.savePlayerPosition.project.Tools.playerDataManager;
import spigot.savePlayerPosition.project.Tools.worldManager;

public class leaveServerListener implements Listener {
    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerLeave(PlayerQuitEvent event) {
        sppDebugger.log(event.getPlayer().getDisplayName() + "(" + event.getPlayer().getUniqueId() + ")" + " has left the server from world " + "\"" + event.getPlayer().getWorld().getName() + "\"");
        Location tempLoc = event.getPlayer().getLocation();
        sppDebugger.log(event.getPlayer().getDisplayName() + " cords: " + tempLoc.getX() + ", " + tempLoc.getY() + ", " + tempLoc.getZ());
        playerDataManager.saveWorldData(event.getPlayer().getUniqueId().toString(), event.getPlayer().getWorld().getName(), tempLoc.getX(), tempLoc.getY(), tempLoc.getZ());
        String groupName = worldManager.getGroupWorldIsPartOf(event.getPlayer().getWorld().getName());
        if (groupName != null) {
            playerDataManager.saveGroupData(event.getPlayer().getUniqueId().toString(), groupName, event.getPlayer().getWorld().getName());
        }
    }
}