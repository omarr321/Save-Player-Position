package spigot.savePlayerPosition.project.Listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import spigot.savePlayerPosition.project.Tools.sppDebugger;
import spigot.savePlayerPosition.project.Tools.playerDataManager;
import spigot.savePlayerPosition.project.Tools.configManager;

/**
 * @author Omar Radwan
 * @version 1.0.0
 * @about Listens for when a player leaves a server
 */
public class leaveServerListener implements Listener {
    private final String strClass = "LeaveServerListener";
    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerLeave(PlayerQuitEvent event) {
        String strMethod = "onPlayerLeave";
        sppDebugger.log(strClass, strMethod, event.getPlayer().getDisplayName() + "(" + event.getPlayer().getUniqueId() + ")" + " has left the server from world " + "\"" + event.getPlayer().getWorld().getName() + "\"");
        Location tempLoc = event.getPlayer().getLocation();
        sppDebugger.log(strClass, strMethod, event.getPlayer().getDisplayName() + " cords: " + tempLoc.getX() + ", " + tempLoc.getY() + ", " + tempLoc.getZ() + "; (" + tempLoc.getYaw() + ", " + tempLoc.getPitch() + ")");
        playerDataManager.saveWorldData(event.getPlayer().getUniqueId().toString(), event.getPlayer().getWorld().getName(), tempLoc.getX(), tempLoc.getY(), tempLoc.getZ(), tempLoc.getYaw(), tempLoc.getPitch());
        String groupName = configManager.getGroupWorldIsPartOf(event.getPlayer().getWorld().getName());
        if (groupName != null) {
            playerDataManager.saveGroupData(event.getPlayer().getUniqueId().toString(), groupName, event.getPlayer().getWorld().getName());
        }
    }
}