package spigot.savePlayerPosition.project;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            if (player.hasPermission("spp.command.*") || player.hasPermission("spp.command.version")) {
                player.sendMessage("Save Player Position v0.0.1");
            } else {
                player.sendMessage("You do not have permission: spp.command.version");
            }
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (player.hasPermission("spp.command.*") || player.hasPermission("spp.command.reload")) {
                player.sendMessage("ERROR: NOT IMPLEMENTED!");
            } else {
                player.sendMessage("You do not have permission: spp.command.reload");
            }
        } else {
            player.sendMessage("Error: Unknown command!");
        }
        return true;
    }

    private void showHelp(Player player) {
        player.sendMessage("/spp help    - Shows this page");
        player.sendMessage("/spp version - Shows the plugin version");
        player.sendMessage("/spp reload  - Reloads the config");
    }
}
