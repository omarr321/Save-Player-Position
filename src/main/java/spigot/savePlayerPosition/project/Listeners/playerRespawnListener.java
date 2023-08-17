package spigot.savePlayerPosition.project.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import spigot.savePlayerPosition.project.Tools.sppDebugger;
import spigot.savePlayerPosition.project.Tools.sppMessager;
import spigot.savePlayerPosition.project.Tools.configManager;
import spigot.savePlayerPosition.project.Tools.playerDataManager;

/**
 * @author Omar Radwan
 * @version 1.0.0
 * @about Listens for when a player dies
 */
public class playerRespawnListener implements Listener {
    private static final String strClass = "PlayerRespawnListener";

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        final String strMethod = "onPlayerRespawn";
        sppDebugger.log(strClass, strMethod, "Player \"" + event.getPlayer().getName() + "\" has respawned");
        String currWorld = event.getRespawnLocation().getWorld().getName();
        String worldGroup = configManager.getGroupWorldIsPartOf(currWorld);
        if (worldGroup == null) {
            sppDebugger.log(strClass, strMethod, "\"" + currWorld + "\" is not in a group");
        } else {
            sppDebugger.log(strClass, strMethod, "Deleting group data for group \"" + worldGroup + "\"");
            playerDataManager.removeGroupData(event.getPlayer().getUniqueId().toString(), worldGroup);
        }
    }
}
