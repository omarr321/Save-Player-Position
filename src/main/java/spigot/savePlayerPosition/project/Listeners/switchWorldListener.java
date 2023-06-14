package spigot.savePlayerPosition.project.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import spigot.savePlayerPosition.project.sppDebugger;

public class switchWorldListener implements Listener {

    @EventHandler
    public void onPlayerWorldSwitch(PlayerChangedWorldEvent event) {
        sppDebugger.log(event.getPlayer().getDisplayName() + " moved from \"" + event.getFrom().getName() + "\" to \"" + event.getPlayer().getWorld().getName() + "\"");
    }
}
