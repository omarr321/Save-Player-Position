package spigot.savePlayerPosition.project.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import spigot.savePlayerPosition.project.Tools.sppDebugger;
import spigot.savePlayerPosition.project.Tools.playerDataManager;
import spigot.savePlayerPosition.project.Tools.worldManager;

public class playerTeleportListener implements Listener {
    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (!(event.getFrom().getWorld().equals(event.getTo().getWorld()))) {
            sppDebugger.log(event.getPlayer().getDisplayName() + "(" + event.getPlayer().getUniqueId() + ")" + " moved from \"" + event.getFrom().getWorld().getName() + "\" to \"" + event.getTo().getWorld().getName() + "\"");
            sppDebugger.log("Saving " + event.getPlayer().getName() + " position of " + event.getFrom().getX() + ", " + event.getFrom().getY() + ", " + event.getFrom().getZ() + " from world \"" + event.getFrom().getWorld().getName() + "\"");
            playerDataManager.saveWorldData(event.getPlayer().getUniqueId().toString(), event.getFrom().getWorld().getName(), event.getFrom().getX(), event.getFrom().getY(), event.getFrom().getZ());

            String fromGroup = worldManager.getGroupWorldIsPartOf(event.getFrom().getWorld().getName());
            String toGroup = worldManager.getGroupWorldIsPartOf(event.getTo().getWorld().getName());
            if (!(fromGroup.equals(toGroup))) {
                playerDataManager.saveGroupData(event.getPlayer().getUniqueId().toString(), fromGroup, event.getFrom().getWorld().getName());
            } else {
                playerDataManager.saveGroupData(event.getPlayer().getUniqueId().toString(), fromGroup, event.getTo().getWorld().getName());
            }
        }
    }
}