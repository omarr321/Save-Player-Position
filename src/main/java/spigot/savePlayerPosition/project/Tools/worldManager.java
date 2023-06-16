package spigot.savePlayerPosition.project.Tools;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import spigot.savePlayerPosition.project.Main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class worldManager {
    private static String titleLabel = ChatColor.DARK_AQUA + "[SPP]" + ": ";
    private static JavaPlugin plugin = JavaPlugin.getPlugin(Main.class);
    private static FileConfiguration config = plugin.getConfig();
    public static void enableWorldMan() {
        plugin.saveDefaultConfig();
        sppDebugger.setDebug(config.getBoolean("debug"));
    }

    public static boolean checkBlacklist(String name) {
        List<String> list = (List<String>) config.getList("world.blacklist");
        ArrayList<String> worlds = null;
        if (list != null) {
            worlds = new ArrayList<String>((Collection<? extends String>) config.getList("world.blacklist"));
        }
        if (worlds == null) {
            return false;
        }

        for (String world : worlds) {
         if (world.equalsIgnoreCase(name)) {
             return true;
         }
        }

        return false;
    }

    public static void addBlacklistWorld(String name, Player player) {
        List<String> list = (List<String>) config.getList("world.blacklist");
        ArrayList<String> worlds = null;
        if (list != null) {
            worlds = new ArrayList<String>((Collection<? extends String>) config.getList("world.blacklist"));
        }
        if (worlds == null) {
            worlds = new ArrayList<String>();
        }
        for ( String world: worlds) {
            if (world.equals(name)) {
                player.sendMessage(titleLabel + ChatColor.RESET + " World \"" + name + "\" is already in the blacklist");
                return;
            }
        }
        worlds.add(name);
        config.set("world.blacklist", worlds.stream().toList());
        plugin.saveConfig();
        player.sendMessage(titleLabel + ChatColor.RESET + " World \"" + name + "\" was added to the blacklist");
    }

    public static void removeBlacklistWorld(String name, Player player) {
        List<String> list = (List<String>) config.getList("world.blacklist");
        ArrayList<String> worlds = null;
        if (list != null) {
            worlds = new ArrayList<String>((Collection<? extends String>) config.getList("world.blacklist"));
        }
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

    public static void createGroup(String name) {

    }

    public static void deleteGroup(String name) {

    }

    public static void getGroup(String name) {

    }
    public static void addWorldToGroup(String groupName, String worldName) {

    }

    public static void removeWorldFromGroup(String groupName, String worldName) {

    }
}
