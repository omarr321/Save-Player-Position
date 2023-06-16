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
            worlds = new ArrayList<>(list);
        }
        if (worlds == null) {
            worlds = new ArrayList<>();
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
            worlds = new ArrayList<>(list);
        }
        if (worlds == null) {
            worlds = new ArrayList<>();
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
    public static void createGroup(String name, Player player) {
        if (!(getGroup(name).isEmpty())) {
            player.sendMessage(titleLabel + ChatColor.RESET + " Group \"" + name + "\" already exists");
            return;
        }
        config.set("world.group." + name, "");
        plugin.saveConfig();
        player.sendMessage(titleLabel + ChatColor.RESET + " Group \"" + name + "\" has been created");
    }

    public static void deleteGroup(String name, Player player) {
        config.set("world.group." + name, null);
        plugin.saveConfig();
        player.sendMessage(titleLabel + ChatColor.RESET + " Group \"" + name + "\" has been deleted");
    }

    public static ArrayList<String> getGroup(String name) {
        ArrayList<String> worlds = (ArrayList<String>) config.getList("world.group." + name);
        if (worlds == null) {
            worlds = new ArrayList<>();
        }
        return worlds;
    }

    public static ArrayList<String> getAllGroups() {
        //ppDebugger.log(config.getConfigurationSection("world.group").getKeys(false).toString());
        ArrayList<String> groups = new ArrayList<>(config.getConfigurationSection("world.group").getKeys(false));
        if (groups == null) {
            groups = new ArrayList<>();
        }
        return groups;
    }
    public static void addWorldToGroup(String groupName, String worldName, Player player) {

    }

    public static void removeWorldFromGroup(String groupName, String worldName, Player player) {

    }
}
