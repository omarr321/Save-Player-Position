package spigot.savePlayerPosition.project.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import spigot.savePlayerPosition.project.Tools.sppDebugger;
import spigot.savePlayerPosition.project.Tools.playerDataManager;
import spigot.savePlayerPosition.project.Tools.worldManager;

public class playerTeleportListener implements Listener {
    private static final String strClass = "PlayerTeleportListener";
    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        String strMethod = "onPlayerTeleport";
        String toWorldGroup = worldManager.getGroupWorldIsPartOf(event.getTo().getWorld().getName());
        String fromWorldGroup = worldManager.getGroupWorldIsPartOf(event.getFrom().getWorld().getName());
        if (!(event.getFrom().getWorld().equals(event.getTo().getWorld()))) {
            sppDebugger.log(strClass, strMethod, event.getPlayer().getDisplayName() + " switched worlds from \"" + event.getFrom().getWorld().getName() + "\" to \"" + event.getTo().getWorld().getName() + "\"");
            playerDataManager.saveWorldData(event.getPlayer().getUniqueId().toString(), event.getFrom().getWorld().getName(), event.getFrom().getX(), event.getFrom().getY(), event.getFrom().getZ());
            if (fromWorldGroup != null) {
                if (!(fromWorldGroup.equals(toWorldGroup))) {
                    sppDebugger.log(strClass, strMethod, event.getPlayer().getDisplayName() + " left group \"" + fromWorldGroup + "\"");
                    playerDataManager.saveGroupData(event.getPlayer().getUniqueId().toString(), fromWorldGroup, event.getFrom().getWorld().getName());
                } else {
                    sppDebugger.log(strClass, strMethod, event.getPlayer().getDisplayName() + " has teleported in the same group, updating world");
                    playerDataManager.saveGroupData(event.getPlayer().getUniqueId().toString(), fromWorldGroup, event.getTo().getWorld().getName());
                }
            }
        }
    }
}