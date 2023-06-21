package spigot.savePlayerPosition.project.Tools;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * A class that sends messages to a player
 */
public class sppMessager {
    private static String titleLabel = ChatColor.DARK_AQUA + "[SPP]" + ": ";
    public static void sendMessage(Player player, String message) {
        player.sendMessage(titleLabel + ChatColor.RESET + message);
    }

    public static void sendMessage(Player player, String message, ChatColor color) {
        player.sendMessage(titleLabel + color + message);
    }

    public static void sendMessage(Player player, String messagePt1, ChatColor colorPt1, String messagePt2, ChatColor colorPt2) {
        player.sendMessage(titleLabel + colorPt1 + messagePt1 + colorPt2 + messagePt2);
    }
}
