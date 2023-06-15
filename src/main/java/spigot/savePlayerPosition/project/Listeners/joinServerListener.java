package spigot.savePlayerPosition.project.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import spigot.savePlayerPosition.project.Tools.sppDebugger;
import spigot.savePlayerPosition.project.Tools.playerDataManager;

public class joinServerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        sppDebugger.log(event.getPlayer().getDisplayName() + "(" + event.getPlayer().getUniqueId() + ")" + " has joined the server in world \"" + event.getPlayer().getWorld().getName() + "\"");
        double[] cords = playerDataManager.getData(event.getPlayer().getUniqueId().toString(), event.getPlayer().getWorld().getName());
        if (cords != null) {
            sppDebugger.log(event.getPlayer().getDisplayName() + " cords: " + cords[0] + ". " + cords[1] + ", " + cords[2]);
            event.getPlayer().getLocation().setX(cords[0]);
            event.getPlayer().getLocation().setY(cords[0]);
            event.getPlayer().getLocation().setZ(cords[0]);

        } else {
            sppDebugger.log(event.getPlayer().getDisplayName() + " cords: NO CORDS SAVED");
        }
    }
}
