package spigot.savePlayerPosition.project.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import spigot.savePlayerPosition.project.Tools.sppDebugger;
import spigot.savePlayerPosition.project.Tools.lastCommand;

/**
 * @author Omar Radwan
 * @version 1.0.0
 * @about Stores the last chat message a player sent
 */
public class chatListener implements Listener {
    private final String strClass = "chatListener";

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        //sppDebugger.log(strClass, "onCommand", event.getPlayer().getDisplayName() + " ran: " + event.getMessage());
        lastCommand.setLastMessage(event.getPlayer().getUniqueId().toString(), event.getMessage());
    }
}
