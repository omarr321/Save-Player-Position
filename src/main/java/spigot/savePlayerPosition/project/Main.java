package spigot.savePlayerPosition.project;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import spigot.savePlayerPosition.project.Commands.CommandSpp;
import spigot.savePlayerPosition.project.Listeners.joinServerListener;
import spigot.savePlayerPosition.project.Listeners.leaveServerListener;
import spigot.savePlayerPosition.project.Listeners.playerTeleportListener;
import spigot.savePlayerPosition.project.TabCompletions.SppTabCompletion;
import spigot.savePlayerPosition.project.Tools.playerDataManager;
import spigot.savePlayerPosition.project.Tools.sppDebugger;
import spigot.savePlayerPosition.project.Tools.worldManager;

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
        this.getCommand("spp").setTabCompleter(new SppTabCompletion());
        sppDebugger.log("Enabling listeners...");
        getServer().getPluginManager().registerEvents(new leaveServerListener(), this);
        getServer().getPluginManager().registerEvents(new playerTeleportListener(), this);
        getServer().getPluginManager().registerEvents(new joinServerListener(), this);
        sppDebugger.forceLog(this.getName() + " has been enabled", ChatColor.GREEN);
    }

    @Override
    public void onDisable() {
        sppDebugger.forceLog("Disabled " + this.getName(), ChatColor.RED);
    }
}