package spigot.savePlayerPosition.project.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import spigot.savePlayerPosition.project.Tools.playerDataManager;
import spigot.savePlayerPosition.project.Tools.sppDebugger;
import spigot.savePlayerPosition.project.Tools.worldManager;

/**
 * @author Omar Radwan
 * @version 1.0.0
 * @about Listens for when the player changes worlds on the server
 */
public class playerChangedWorldListener implements Listener {
    private static final String strClass = "PlayerChangedWorldListener";
    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerChangeWorldEvent(PlayerChangedWorldEvent event) {
        String strMethod = "onPlayerChangeWorldEvent";
        sppDebugger.log(strClass, strMethod, event.getPlayer().getDisplayName() + "(" + event.getPlayer().getUniqueId() + ")" + " has joined the world \"" + event.getPlayer().getWorld().getName() + "\"");
        teleportPlayer(event.getPlayer(), event.getPlayer().getWorld().getName());

        String toWorldGroup = worldManager.getGroupWorldIsPartOf(event.getPlayer().getWorld().getName());
        if (toWorldGroup != null) {
            sppDebugger.log(strClass, strMethod, event.getPlayer().getDisplayName() + " has joined group \"" + toWorldGroup + "\"");
            String currGroupWorld = playerDataManager.getGroupData(event.getPlayer().getUniqueId().toString(), toWorldGroup);
            if (!(currGroupWorld.equals(event.getPlayer().getWorld().getName()))) {
                sppDebugger.log(strClass, strMethod, event.getPlayer().getDisplayName() + " is being teleport to \"" + currGroupWorld + "\" in group \"" + toWorldGroup);
                teleportPlayer(event.getPlayer(), currGroupWorld);
            }
        }
    }

    private void teleportPlayer(Player player, String world) {
        String strMethod = "teleportPlayer";
        double[] cords = playerDataManager.getWorldData(player.getUniqueId().toString(), world);
        if (cords != null) {
            sppDebugger.log(strClass, strMethod, player.getDisplayName() + " saved cords for \"" + world + "\": " + cords[0] + ". " + cords[1] + ", " + cords[2]);
            if (worldManager.checkBlacklist(world)) {
                sppDebugger.log(strClass, strMethod, "World \"" + world + "\" is blacklisted, skipping");
            } else {
                Location loc = new Location(Bukkit.getWorld(world), cords[0], cords[1], cords[2]);
                player.teleport(loc);
            }
        } else {
            sppDebugger.log(strClass, strMethod, player.getDisplayName() + " saved cords for \"" + world + "\": NO CORDS SAVED");
        }
    }
}
