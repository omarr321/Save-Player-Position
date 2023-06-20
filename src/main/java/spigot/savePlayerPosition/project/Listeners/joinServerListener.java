package spigot.savePlayerPosition.project.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import spigot.savePlayerPosition.project.Tools.sppDebugger;
import spigot.savePlayerPosition.project.Tools.playerDataManager;
import spigot.savePlayerPosition.project.Tools.worldManager;

public class joinServerListener implements Listener {
    private final String strClass = "JoinServerListener";
    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        String strMethod = "onPlayerJoin";
        sppDebugger.log(strClass,strMethod,event.getPlayer().getDisplayName() + "(" + event.getPlayer().getUniqueId() + ")" + " has joined the server in world \"" + event.getPlayer().getWorld().getName() + "\"");
        double[] cords = playerDataManager.getWorldData(event.getPlayer().getUniqueId().toString(), event.getPlayer().getWorld().getName());
        if (cords != null) {
            if (worldManager.checkBlacklist(event.getPlayer().getWorld().getName())) {
                sppDebugger.log(strClass, strMethod, "World \"" + event.getPlayer().getWorld().getName() + "\" is blacklisted, skipping");
            } else {
                sppDebugger.log(strClass, strMethod, event.getPlayer().getDisplayName() + " cords: " + cords[0] + ". " + cords[1] + ", " + cords[2]);
                String group = worldManager.getGroupWorldIsPartOf(event.getPlayer().getWorld().getName());
                if (group == null) {
                    Location loc = new Location(event.getPlayer().getWorld(), cords[0], cords[1], cords[2]);
                    event.getPlayer().teleport(loc);
                } else {
                    if (playerDataManager.getGroupData(event.getPlayer().getUniqueId().toString(), group) != null && playerDataManager.getGroupData(event.getPlayer().getUniqueId().toString(), group).equals(event.getPlayer().getWorld().getName())) {
                        Location loc = new Location(event.getPlayer().getWorld(), cords[0], cords[1], cords[2]);
                        event.getPlayer().teleport(loc);
                    } else {
                        String world = playerDataManager.getGroupData(event.getPlayer().getUniqueId().toString(), group);
                        cords = playerDataManager.getWorldData(event.getPlayer().getUniqueId().toString(), world);
                        Location loc = new Location(Bukkit.getWorld(world), cords[0],cords[1], cords[2]);
                        event.getPlayer().teleport(loc);
                    }
                }
            }
        } else {
            sppDebugger.log(strClass, strMethod, event.getPlayer().getDisplayName() + " cords: NO CORDS SAVED");
        }
    }
}
