package spigot.savePlayerPosition.project.Tools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import spigot.savePlayerPosition.project.Main;

public class sppDebugger {
    private static boolean debug = false;

    public static void setDebug(boolean de) {
        sppDebugger.debug = de;
    }

    public static void log(String log) {
        if (debug) {
            Bukkit.getLogger().info(JavaPlugin.getPlugin(Main.class).getName() + ": " + log);
        }
    }

    public static void log(String log, ChatColor color) {
        if (debug) {
            Bukkit.getLogger().info(JavaPlugin.getPlugin(Main.class).getName() + ": " + color + log);
        }
    }

    public static void forceLog(String log) {
        Bukkit.getLogger().info(JavaPlugin.getPlugin(Main.class).getName() + ": " + log);
    }

    public static void forceLog(String log, ChatColor color) {
        Bukkit.getLogger().info(JavaPlugin.getPlugin(Main.class).getName() + ": " + color + log);
    }
}
