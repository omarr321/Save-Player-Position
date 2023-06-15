package spigot.savePlayerPosition.project.Tools;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import spigot.savePlayerPosition.project.Main;

import java.util.ArrayList;
import java.util.List;

public class worldManager {
    private static String titleLabel = ChatColor.DARK_AQUA + "[SPP]" + ": ";
    private static JavaPlugin plugin = JavaPlugin.getPlugin(Main.class);
    private static FileConfiguration config = plugin.getConfig();
    public static void enableWorldMan() {
        plugin.saveDefaultConfig();
        sppDebugger.setDebug(config.getBoolean("debug"));
    }

    public static void addBlacklistWorld(String name, Player player) {
        List<String> worlds = (List<String>) config.getList("world.blacklist");
        if (worlds == null) {
            worlds = new ArrayList<String>();
        }
        worlds.add(name);
        config.set("world.blacklist", worlds.stream().toList());
        plugin.saveConfig();
        player.sendMessage(titleLabel + ChatColor.RESET + " World \"" + name + "\" was added to the blacklist");
    }

    public static void removeBlacklistWorld(String name, Player player) {
        List<String> worlds = (List<String>) config.getList("world.blacklist");
        if (worlds == null) {
            worlds = new ArrayList<String>();
        }
        try {
            worlds.remove(name);
            config.set("world.blacklist", worlds.stream().toList());
            plugin.saveConfig();
            player.sendMessage(titleLabel + ChatColor.RESET + " World \"" + name + "\" was removed from the blacklist");
        } catch (Exception e) {
            player.sendMessage(titleLabel + ChatColor.RESET + " World \"" + name + "\" is not on the blacklist");
        }
    }
}
