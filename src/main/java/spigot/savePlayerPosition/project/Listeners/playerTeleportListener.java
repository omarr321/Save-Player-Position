package spigot.savePlayerPosition.project.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import spigot.savePlayerPosition.project.Tools.sppDebugger;
import spigot.savePlayerPosition.project.Tools.playerDataManager;

public class playerTeleportListener implements Listener {
    @EventHandler (priority = EventPriority.LOW)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (!(event.getFrom().getWorld().equals(event.getTo().getWorld()))) {
            sppDebugger.log(event.getPlayer().getDisplayName() + "(" + event.getPlayer().getUniqueId() + ")" + " moved from \"" + event.getFrom().getWorld().getName() + "\" to \"" + event.getTo().getWorld().getName() + "\"");
            sppDebugger.log("Saving " + event.getPlayer().getName() + " position of " + event.getFrom().getX() + ", " + event.getFrom().getY() + ", " + event.getFrom().getZ() + " from world \"" + event.getFrom().getWorld().getName() + "\"");
            playerDataManager.saveData(event.getPlayer().getUniqueId().toString(), event.getFrom().getWorld().getName(), event.getFrom().getX(), event.getFrom().getY(), event.getFrom().getZ());
            double[] cords = playerDataManager.getData(event.getPlayer().getUniqueId().toString(), event.getTo().getWorld().getName());
            if (cords != null) {
                event.getTo().setX(cords[0]);
                event.getTo().setY(cords[1]);
                event.getTo().setZ(cords[2]);
            }
        }
    }
}