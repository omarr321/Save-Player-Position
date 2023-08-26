package spigot.savePlayerPosition.project.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import spigot.savePlayerPosition.project.Tools.sppDebugger;
import spigot.savePlayerPosition.project.Tools.playerDataManager;
import spigot.savePlayerPosition.project.Tools.configManager;
import spigot.savePlayerPosition.project.Tools.lastCommand;

/**
 * @author Omar Radwan
 * @version 1.0.0
 * @about Listens for when a player teleports
 */
public class playerTeleportListener implements Listener {
    private static final String strClass = "PlayerTeleportListener";

    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        String strMethod = "onPlayerTeleport";
        String toWorldGroup = configManager.getGroupWorldIsPartOf(event.getTo().getWorld().getName());
        String fromWorldGroup = configManager.getGroupWorldIsPartOf(event.getFrom().getWorld().getName());
        if (!(event.getFrom().getWorld().equals(event.getTo().getWorld()))) {
            sppDebugger.log(strClass, strMethod, event.getPlayer().getDisplayName() + " switched worlds from \"" + event.getFrom().getWorld().getName() + "\" to \"" + event.getTo().getWorld().getName() + "\"");
            playerDataManager.saveWorldData(event.getPlayer().getUniqueId().toString(), event.getFrom().getWorld().getName(), event.getFrom().getX(), event.getFrom().getY(), event.getFrom().getZ(), event.getFrom().getYaw(), event.getFrom().getPitch());
            if (fromWorldGroup != null) {
                if (!(fromWorldGroup.equals(toWorldGroup))) {
                    sppDebugger.log(strClass, strMethod, event.getPlayer().getDisplayName() + " left group \"" + fromWorldGroup + "\"");
                    playerDataManager.saveGroupData(event.getPlayer().getUniqueId().toString(), fromWorldGroup, event.getFrom().getWorld().getName());
                } else {
                    sppDebugger.log(strClass, strMethod, event.getPlayer().getDisplayName() + " has teleported in the same group, updating world");
                    playerDataManager.saveGroupData(event.getPlayer().getUniqueId().toString(), fromWorldGroup, event.getTo().getWorld().getName());
                }
            }
            if (configManager.getTeleport("commandTeleport") == false) {
                sppDebugger.log(strClass, strMethod, event.getPlayer().getDisplayName() + " has ran the command: " + lastCommand.getLastMessage(event.getPlayer().getUniqueId().toString()));
                if (configManager.checkFullCommand(lastCommand.getLastMessage(event.getPlayer().getUniqueId().toString()))) {
                    sppDebugger.log(strClass, strMethod, "Command is on the whitelist, not teleporting player");
                    playerDataManager.removeWorldData(event.getPlayer().getUniqueId().toString(), event.getTo().getWorld().getName());
                    playerDataManager.removeGroupData(event.getPlayer().getUniqueId().toString(), toWorldGroup);
                } else {
                    sppDebugger.log(strClass, strMethod, "Command is not on the whitelist, teleporting player");
                }
                lastCommand.setLastMessage(event.getPlayer().getUniqueId().toString(), null);
            }
            if ((event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) && (configManager.getTeleport("netherPortalTeleport") == false)) {
                sppDebugger.log(strClass, strMethod, event.getPlayer().getDisplayName() + " has teleported with a nether portal, deleting world data for \"" + event.getTo().getWorld().getName() + "\"");
                playerDataManager.removeWorldData(event.getPlayer().getUniqueId().toString(), event.getTo().getWorld().getName());
            } else if ((event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) && (configManager.getTeleport("endPortalTeleport") == false)) {
                sppDebugger.log(strClass, strMethod, event.getPlayer().getDisplayName() + " has teleported with an end portal, deleting world data for \"" + event.getTo().getWorld().getName() + "\"");
                playerDataManager.removeWorldData(event.getPlayer().getUniqueId().toString(), event.getTo().getWorld().getName());
            } else if ((event.getCause() == PlayerTeleportEvent.TeleportCause.END_GATEWAY) && (configManager.getTeleport("endGateTeleport") == false)) {
                sppDebugger.log(strClass, strMethod, event.getPlayer().getDisplayName() + " has teleported with an end gate portal, deleting world data for \"" + event.getTo().getWorld().getName() + "\"");
                playerDataManager.removeWorldData(event.getPlayer().getUniqueId().toString(), event.getTo().getWorld().getName());
            }
        }
    }
}