package spigot.savePlayerPosition.project;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main extends JavaPlugin{
    @Override
    public void onEnable() {
        this.getCommand("spp").setExecutor(new CommandSpp());
        this.getCommand("spp").setTabCompleter(new TabCompletion());
        Bukkit.getLogger().info(ChatColor.GREEN + "Enabled " + this.getName());
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ChatColor.RED + "Disabled " + this.getName());
    }
}