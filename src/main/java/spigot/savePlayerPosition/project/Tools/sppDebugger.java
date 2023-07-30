package spigot.savePlayerPosition.project.Tools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import spigot.savePlayerPosition.project.Main;

/**
 * A debug class that sends debug to the console
 */
public class sppDebugger {
    private static boolean debug = false;
    private static final String title = "[" + JavaPlugin.getPlugin(Main.class).getName() + "]";

    /**
     * Sets the debug value for the debugger
     * @param de - The bool value for debug, true to log debug messages, false if not
     */
    public static void setDebug(boolean de) {
        sppDebugger.debug = de;
    }

    public static void log(String strClass, String strMethod, String log) {
        if (debug) {
            Bukkit.getLogger().info(buildFullTitle(strClass, strMethod) + log);
        }
    }

    public static void log(String strClass, String strMethod, String log, ChatColor color) {
        if (debug) {
            Bukkit.getLogger().info(buildFullTitle(strClass, strMethod) + color + log);
        }
    }

    public static void forceLog(String strClass, String strMethod, String log) {
        Bukkit.getLogger().info(buildFullTitle(strClass, strMethod) + log);
    }

    public static void forceLog(String strClass, String strMethod, String log, ChatColor color) {
        Bukkit.getLogger().info(buildFullTitle(strClass, strMethod) + color + log);
    }

    private static String buildFullTitle(String strClass, String strMethod) {
        return title + "-(" + strClass + "." + strMethod + "): ";
    }
}
