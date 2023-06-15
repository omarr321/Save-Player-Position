package spigot.savePlayerPosition.project;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import spigot.savePlayerPosition.project.Commands.CommandSpp;
import spigot.savePlayerPosition.project.Listeners.joinServerListener;
import spigot.savePlayerPosition.project.Listeners.leaveServerListener;
import spigot.savePlayerPosition.project.Listeners.switchWorldListener;
import spigot.savePlayerPosition.project.TabCompletions.SppTabCompletion;

import java.io.File;
import java.nio.file.Path;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main extends JavaPlugin{
    private File userData = new File(this.getDataFolder(), "playerData");
    @Override
    public void onEnable() {

        sppDebugger.forceLog("Enabling " + this.getName(), ChatColor.GREEN);
        sppDebugger.log("Enabling config...");
        this.saveDefaultConfig();
        sppDebugger.setDebug(this.getConfig().getBoolean("debug"));
        sppDebugger.log("Enabling playerData folder...");
        if (!(userData.exists())) {
            userData.mkdir();
        }
        sppDebugger.log("Enabling commands...");
        this.getCommand("spp").setExecutor(new CommandSpp());
        sppDebugger.log("Enabling tab completion...");
        this.getCommand("spp").setTabCompleter(new SppTabCompletion());
        sppDebugger.log("Enabling listeners...");
        getServer().getPluginManager().registerEvents(new leaveServerListener(), this);
        getServer().getPluginManager().registerEvents(new switchWorldListener(), this);
        getServer().getPluginManager().registerEvents(new joinServerListener(), this);
        sppDebugger.forceLog(this.getName() + " has been enabled", ChatColor.GREEN);
    }

    @Override
    public void onDisable() {
        sppDebugger.forceLog("Disabled " + this.getName(), ChatColor.RED);
    }
}