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

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main extends JavaPlugin{
    @Override
    public void onEnable() {
        sppDebugger.forceLog("Enabling " + this.getName(), ChatColor.GREEN);
        sppDebugger.log("Enabling config...");
        worldManager.enableWorldMan();
        sppDebugger.log("Enabling playerData folder...");
        playerDataManager.enablePlayerMan();
        sppDebugger.log("Enabling commands...");
        this.getCommand("spp").setExecutor(new CommandSpp());
        sppDebugger.log("Enabling tab completion...");
        this.getCommand("spp").setTabCompleter(new sppTabCompletion());
        sppDebugger.log("Enabling listeners...");
        this.getServer().getPluginManager().registerEvents(new leaveServerListener(), this);
        this.getServer().getPluginManager().registerEvents(new playerTeleportListener(), this);
        this.getServer().getPluginManager().registerEvents(new joinServerListener(), this);
        this.getServer().getPluginManager().registerEvents(new playerChangedWorldListener(), this);
        sppDebugger.forceLog(this.getName() + " has been enabled", ChatColor.GREEN);
    }

    @Override
    public void onDisable() {
        sppDebugger.forceLog("Disabling " + this.getName(), ChatColor.RED);
        List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
        if (players == null) {
            sppDebugger.log("No players on the server");
        } else {
            for (Player player: players) {
                sppDebugger.log("Saving location data for \"" + player.getName() + "\"");
                playerDataManager.saveWorldData(player.getUniqueId().toString(), player.getLocation().getWorld().getName(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
            }
        }
        sppDebugger.forceLog(this.getName() + " has been disabled", ChatColor.RED);
    }
}