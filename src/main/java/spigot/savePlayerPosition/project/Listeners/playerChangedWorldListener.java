package spigot.savePlayerPosition.project.Listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import spigot.savePlayerPosition.project.Tools.playerDataManager;
import spigot.savePlayerPosition.project.Tools.sppDebugger;
import spigot.savePlayerPosition.project.Tools.worldManager;

public class playerChangedWorldListener implements Listener {
    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerChangeWorldEvent(PlayerChangedWorldEvent event) {
        sppDebugger.log(event.getPlayer().getDisplayName() + "(" + event.getPlayer().getUniqueId() + ")" + " has joined the world \"" + event.getPlayer().getWorld().getName() + "\"");
        double[] cords = playerDataManager.getWorldData(event.getPlayer().getUniqueId().toString(), event.getPlayer().getWorld().getName());
        if (cords != null) {
            sppDebugger.log(event.getPlayer().getDisplayName() + " cords: " + cords[0] + ". " + cords[1] + ", " + cords[2]);
            if (worldManager.checkBlacklist(event.getPlayer().getWorld().getName())) {
                sppDebugger.log("World \"" + event.getPlayer().getWorld().getName() + "\" is blacklisted, skipping");
            } else {
                Location loc = new Location(event.getPlayer().getWorld(), cords[0], cords[1], cords[2]);
                event.getPlayer().teleport(loc);
            }
        } else {
            sppDebugger.log(event.getPlayer().getDisplayName() + " cords: NO CORDS SAVED");
        }
    }
}
