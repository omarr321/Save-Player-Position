package spigot.savePlayerPosition.project.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.ChatPaginator;
import spigot.savePlayerPosition.project.Main;
import spigot.savePlayerPosition.project.sppDebugger;

public class CommandSpp implements CommandExecutor {
    private String titleLabel = ChatColor.DARK_AQUA + "[SPP]" + ": ";
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
                player.sendMessage(titleLabel + ChatColor.DARK_AQUA + "---------------------------");
                player.sendMessage(titleLabel + ChatColor.DARK_AQUA + "Save Player Position v0.0.5");
                player.sendMessage(titleLabel + ChatColor.DARK_AQUA + "---------------------------");
            } else {
                player.sendMessage(titleLabel + ChatColor.RED + "You do not have permission:" + ChatColor.YELLOW + " spp.command.version");
            }
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (player.hasPermission("spp.*") || player.hasPermission("spp.command.*") || player.hasPermission("spp.command.reload")) {
                reloadConfig();
            } else {
                player.sendMessage(titleLabel + ChatColor.RED + "You do not have permission:" + ChatColor.YELLOW + " spp.command.reload");
            }
        } else if (args[0].equalsIgnoreCase("setdebug")){
            if(player.hasPermission("spp.*") || player.hasPermission("spp.command.*") || player.hasPermission("spp.command.setdebug")) {
                if (args[1].equalsIgnoreCase("true")) {
                    player.sendMessage(titleLabel + ChatColor.RESET + "Setting debug to true");
                    saveConfig(true);
                } else if (args[1].equalsIgnoreCase("false")) {
                    player.sendMessage(titleLabel + ChatColor.RESET + "Setting debug to false");
                    saveConfig(false);
                } else {
                    player.sendMessage(titleLabel + ChatColor.RED + "You do not have permission:" + ChatColor.YELLOW + " spp.command.setdebug");
                }
            }
        } else {
            player.sendMessage(titleLabel + ChatColor.RED + "Error: Unknown command!");
        }
        return true;
    }


    private void saveConfig(Boolean debug) {
        JavaPlugin.getPlugin(Main.class).getConfig().set("debug", debug);
        JavaPlugin.getPlugin(Main.class).saveConfig();
        reloadConfig();
    }
    private void reloadConfig() {
        sppDebugger.log("Reloading config...");
        JavaPlugin.getPlugin(Main.class).reloadConfig();
        sppDebugger.setDebug(JavaPlugin.getPlugin(Main.class).getConfig().getBoolean("debug"));
    }

    private void showHelp(Player player) {
        player.sendMessage(titleLabel + ChatColor.DARK_AQUA + "---" + ChatColor.RED + "Save Player Position" + ChatColor.DARK_AQUA + "---");
        player.sendMessage(titleLabel + ChatColor.GREEN + "/spp help " + ChatColor.RESET + "- Shows this page");
        player.sendMessage(titleLabel + ChatColor.GREEN + "/spp version " + ChatColor.RESET + "- Shows the plugin version");
        player.sendMessage(titleLabel + ChatColor.GREEN + "/spp reload " + ChatColor.RESET + "- Reloads the config");
        player.sendMessage(titleLabel + ChatColor.GREEN + "/spp setdebug " + "<bool> " + ChatColor.RESET + "- Sets the debug value");
        player.sendMessage(titleLabel + ChatColor.DARK_AQUA + "---" + ChatColor.RED + "Page 1 of 1" + ChatColor.DARK_AQUA + "---");
    }
}
