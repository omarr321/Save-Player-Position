package spigot.savePlayerPosition.project;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import spigot.savePlayerPosition.project.Commands.CommandSpp;
import spigot.savePlayerPosition.project.Listeners.joinServerListener;
import spigot.savePlayerPosition.project.Listeners.leaveServerListener;
import spigot.savePlayerPosition.project.Listeners.switchWorldListener;
import spigot.savePlayerPosition.project.TabCompletions.SppTabCompletion;

import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main extends JavaPlugin{
    @Override
    public void onEnable() {
        this.getConfig().set("debug", true);
        this.getConfig().set("world.groups", "");
        this.getConfig().set("world.blacklist", "");
        Bukkit.getLogger().info(this.getName() + ": " + ChatColor.GREEN + "Enabling " + this.getName());
        Bukkit.getLogger().info(this.getName() + ": " + "Enabling commands...");
        this.getCommand("spp").setExecutor(new CommandSpp());
        Bukkit.getLogger().info(this.getName() + ": " + "Enabling tab completion...");
        this.getCommand("spp").setTabCompleter(new SppTabCompletion());
        Bukkit.getLogger().info(this.getName() + ": " + "Enabling listeners...");
        getServer().getPluginManager().registerEvents(new leaveServerListener(), this);
        getServer().getPluginManager().registerEvents(new switchWorldListener(), this);
        getServer().getPluginManager().registerEvents(new joinServerListener(), this);
        Bukkit.getLogger().info(this.getName() + ": " + ChatColor.GREEN + this.getName() + " has been enabled");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(this.getName() + ": " + ChatColor.RED + "Disabled " + this.getName());
    }
}