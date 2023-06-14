package spigot.savePlayerPosition.project.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import spigot.savePlayerPosition.project.Main;
import spigot.savePlayerPosition.project.sppDebugger;

public class CommandSpp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command !");
            return false;
        }
        Player player = (Player) sender;
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            showHelp(player);
        } else if (args[0].equalsIgnoreCase("version")) {
            if (player.hasPermission("spp.*") || player.hasPermission("spp.command.*") || player.hasPermission("spp.command.version")) {
                player.sendMessage(ChatColor.DARK_AQUA + "---------------------------");
                player.sendMessage(ChatColor.DARK_AQUA + "Save Player Position v0.0.1");
                player.sendMessage(ChatColor.DARK_AQUA + "---------------------------");
            } else {
                player.sendMessage(ChatColor.RED + "You do not have permission:" + ChatColor.YELLOW + " spp.command.version");
            }
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (player.hasPermission("spp.*") || player.hasPermission("spp.command.*") || player.hasPermission("spp.command.reload")) {
                sppDebugger.log("Reloading config...");
                JavaPlugin.getPlugin(Main.class).getConfig().getBoolean("debug");
            } else {
                player.sendMessage(ChatColor.RED + "You do not have permission:" + ChatColor.YELLOW + " spp.command.reload");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Error: Unknown command!");
        }
        return true;
    }

    private void showHelp(Player player) {
        player.sendMessage(ChatColor.DARK_AQUA + "---" + ChatColor.RED + "Save Player Position" + ChatColor.DARK_AQUA + "---");
        player.sendMessage(ChatColor.GREEN + "/spp help" + ChatColor.RESET + "    - Shows this page");
        player.sendMessage(ChatColor.GREEN + "/spp version" + ChatColor.RESET + " - Shows the plugin version");
        player.sendMessage(ChatColor.GREEN + "/spp reload" + ChatColor.RESET + "  - Reloads the config");
        player.sendMessage(ChatColor.DARK_AQUA + "-----------" + ChatColor.RED + "Page 1 of 1" + ChatColor.DARK_AQUA + "---");
    }
}
