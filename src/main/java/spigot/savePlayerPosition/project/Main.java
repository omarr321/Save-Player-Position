package spigot.savePlayerPosition.project;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import spigot.savePlayerPosition.project.Commands.CommandSpp;
import spigot.savePlayerPosition.project.Listeners.joinServerListener;
import spigot.savePlayerPosition.project.Listeners.leaveServerListener;
import spigot.savePlayerPosition.project.Listeners.playerChangedWorldListener;
import spigot.savePlayerPosition.project.Listeners.playerTeleportListener;
import spigot.savePlayerPosition.project.TabCompletions.sppTabCompletion;
import spigot.savePlayerPosition.project.Tools.playerDataManager;
import spigot.savePlayerPosition.project.Tools.sppDebugger;
import spigot.savePlayerPosition.project.Tools.worldManager;
import java.util.List;

/**
 * The main class for the java plugin
 */
public class Main extends JavaPlugin{
    private static final String strClass = "Main";
    @Override
    public void onEnable() {
        String strMethod = "onEnable";
        sppDebugger.forceLog(strClass, strMethod, "Enabling " + this.getName(), ChatColor.GREEN);
        sppDebugger.log(strClass, strMethod, "Enabling config...");
        worldManager.enableWorldMan();
        sppDebugger.log(strClass, strMethod, "Enabling playerData folder...");
        playerDataManager.enablePlayerMan();
        sppDebugger.log(strClass, strMethod, "Enabling commands...");
        this.getCommand("spp").setExecutor(new CommandSpp());
        sppDebugger.log(strClass, strMethod, "Enabling tab completion...");
        this.getCommand("spp").setTabCompleter(new sppTabCompletion());
        sppDebugger.log(strClass, strMethod, "Enabling listeners...");
        this.getServer().getPluginManager().registerEvents(new leaveServerListener(), this);
        this.getServer().getPluginManager().registerEvents(new playerTeleportListener(), this);
        this.getServer().getPluginManager().registerEvents(new joinServerListener(), this);
        this.getServer().getPluginManager().registerEvents(new playerChangedWorldListener(), this);
        sppDebugger.forceLog(strClass, strMethod, this.getName() + " has been enabled", ChatColor.GREEN);
    }

    @Override
    public void onDisable() {
        String strMethod = "onDisable";
        sppDebugger.forceLog(strClass, strMethod, "Disabling " + this.getName(), ChatColor.RED);
        List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
        if (players == null) {
            sppDebugger.log(strClass, strMethod, "No players on the server");
        } else {
            for (Player player: players) {
                sppDebugger.log(strClass, strMethod, "Saving location data for \"" + player.getName() + "\"");
                playerDataManager.saveWorldData(player.getUniqueId().toString(), player.getLocation().getWorld().getName(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
            }
        }
        sppDebugger.forceLog(strClass, strMethod, this.getName() + " has been disabled", ChatColor.RED);
    }
}