package spigot.savePlayerPosition.project.Tools;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import spigot.savePlayerPosition.project.Main;

import java.util.ArrayList;
import java.util.List;

public class worldManager {
    private static JavaPlugin plugin = JavaPlugin.getPlugin(Main.class);
    private static FileConfiguration config = plugin.getConfig();
    public static void enableWorldMan() {
        plugin.saveDefaultConfig();
        sppDebugger.setDebug(config.getBoolean("debug"));
    }

    public static void addBlacklistWorld(String name) {
        List<String> worlds = (List<String>) config.getList("world.blacklist");
        if (worlds == null) {
            worlds = new ArrayList<String>();
        }
        worlds.add(name);
        config.set("world.blacklist", worlds.stream().toList());
        plugin.saveConfig();
    }

    public static void removeBlacklistWorld(String name) {
        List<String> worlds = (List<String>) config.getList("world.blacklist");
        if (worlds == null) {
            worlds = new ArrayList<String>();
        }
        worlds.remove(name);
        config.set("world.blacklist", worlds.stream().toList());
        plugin.saveConfig();
    }
}
