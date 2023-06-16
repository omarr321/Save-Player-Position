package spigot.savePlayerPosition.project.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import spigot.savePlayerPosition.project.Main;
import spigot.savePlayerPosition.project.Tools.*;

public class CommandSpp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command!");
            return false;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            showHelp(player);
        } else if (args[0].equalsIgnoreCase("help")) {
            if (args.length != 1) {
                sppMessager.sendMessage(player, "Error: Incorrect args amount!", ChatColor.RED);
                return true;
            }
            showHelp(player);
        } else if (args[0].equalsIgnoreCase("version")) {
            if (!(player.hasPermission("spp.*") || player.hasPermission("spp.command.*") || player.hasPermission("spp.command.version"))) {
                sppMessager.sendMessage(player, "You do not have permission: ", ChatColor.RED, "spp.command.version", ChatColor.YELLOW);
                return true;
            }
            if (args.length != 1) {
                sppMessager.sendMessage(player, "Error: Incorrect args amount!", ChatColor.RED);
                return true;
            }
            sppMessager.sendMessage(player, "---------------------------", ChatColor.DARK_AQUA);
            sppMessager.sendMessage(player, "Save Player Position v0.1.0", ChatColor.GREEN);
            sppMessager.sendMessage(player, "---------------------------", ChatColor.DARK_AQUA);
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (!(player.hasPermission("spp.*") || player.hasPermission("spp.command.*") || player.hasPermission("spp.command.reload"))) {
                sppMessager.sendMessage(player, "You do not have permission: ", ChatColor.RED, "spp.command.reload", ChatColor.YELLOW);
                return true;
            }
            if (args.length != 1) {
                sppMessager.sendMessage(player, "Error: Incorrect args amount!", ChatColor.RED);
                return true;
            }

            reloadConfig();
            sppMessager.sendMessage(player, "Reloaded the config");
        } else if (args[0].equalsIgnoreCase("setdebug")){
            if (!(player.hasPermission("spp.*") || player.hasPermission("spp.command.*") || player.hasPermission("spp.command.setdebug"))) {
                sppMessager.sendMessage(player, "You do not have permission: ", ChatColor.RED, "spp.command.setdebug", ChatColor.YELLOW);
                return true;
            }
            if (args.length != 2) {
                sppMessager.sendMessage(player, "Error: Incorrect args amount!", ChatColor.RED);
                return true;
            }

            if (args[1].equalsIgnoreCase("true")) {
                sppMessager.sendMessage(player, "Setting debug to true");
                saveConfig(true);
            } else if (args[1].equalsIgnoreCase("false")) {
                sppMessager.sendMessage(player, "Setting debug to false");
                saveConfig(false);
            } else {
                sppMessager.sendMessage(player, "Error: Unknown value!", ChatColor.RED);
            }
        } else if (args[0].equalsIgnoreCase("blacklist")) {
            if (!(player.hasPermission("spp.*") || player.hasPermission("spp.admin.*") || player.hasPermission("spp.admin.blacklist"))) {
                sppMessager.sendMessage(player, "You do not have permission: ", ChatColor.RED, "spp.admin.blacklist", ChatColor.YELLOW);
                return true;
            }
            if (args.length != 3) {
                sppMessager.sendMessage(player, "Error: Incorrect args amount!", ChatColor.RED);
                return true;
            }

            if (args[1].equalsIgnoreCase("add")) {
                worldManager.addBlacklistWorld(args[2], player);
            } else if (args[1].equalsIgnoreCase("remove")) {
                worldManager.removeBlacklistWorld(args[2], player);
            } else {
                sppMessager.sendMessage(player, "Error: Unknown value!", ChatColor.RED);
            }
        } else  if (args[0].equalsIgnoreCase("group")){
            if (!(player.hasPermission("spp.*") || player.hasPermission("spp.admin.*") || player.hasPermission("spp.admin.group.*")))
            if (!(args.length >= 2)) {
                sppMessager.sendMessage(player, "Error: Incorrect args amount!", ChatColor.RED);
                return true;
            }
            if (args[1].equalsIgnoreCase("create") || args[1].equalsIgnoreCase("delete")) {
                if (!(player.hasPermission("spp.*") || player.hasPermission("spp.admin.*") || player.hasPermission("spp.admin.group.*") || player.hasPermission("spp.admin.group.worlds"))) {
                    sppMessager.sendMessage(player, "You do not have permission: ", ChatColor.RED, "spp.admin.group.groups", ChatColor.YELLOW);
                    return true;
                }
                if (args.length != 3) {
                    sppMessager.sendMessage(player, "Error: Incorrect args amount!", ChatColor.RED);
                    return true;
                }

                if (args[1].equalsIgnoreCase("create")) {
                    worldManager.createGroup(args[2], player);
                } else {
                    worldManager.deleteGroup(args[2], player);
                }
            } else if (args[1].equalsIgnoreCase("addWorld") || args[1].equalsIgnoreCase("removeWorld")) {
                if (!(player.hasPermission("spp.*") || player.hasPermission("spp.admin.*") || player.hasPermission("spp.admin.group.*") || player.hasPermission("spp.admin.group.worlds"))) {
                    sppMessager.sendMessage(player, "You do not have permission: ", ChatColor.RED, "spp.admin.group.worlds", ChatColor.YELLOW);
                    return true;
                }
                if (args.length != 4) {
                    sppMessager.sendMessage(player, "Error: Incorrect args amount!", ChatColor.RED);
                    return true;
                }
            } else {
                sppMessager.sendMessage(player, "Error: Unknown command!", ChatColor.RED);
            }
        } else {
            sppMessager.sendMessage(player, "Error: Unknown command!", ChatColor.RED);
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
        sppMessager.sendMessage(player, ChatColor.DARK_AQUA + "---" + ChatColor.RED + "Save Player Position" + ChatColor.DARK_AQUA + "---");
        sppMessager.sendMessage(player, "/spp help ", ChatColor.GREEN, "- Shows this page", ChatColor.RESET);
        sppMessager.sendMessage(player, "/spp version ", ChatColor.GREEN, "- Shows the plugin version", ChatColor.RESET);
        sppMessager.sendMessage(player, "/spp reload ", ChatColor.GREEN, "- Reloads the config", ChatColor.RESET);
        sppMessager.sendMessage(player, "/spp setdebug <bool> ", ChatColor.GREEN, "- Sets the debug value", ChatColor.RESET);
        sppMessager.sendMessage(player, "/spp blacklist [add/remove] <world> ", ChatColor.GREEN, "- Adds/Removes a world from the blacklist", ChatColor.RESET);
        sppMessager.sendMessage(player, "/spp group [create/delete] <group>" , ChatColor.GREEN, "- Creates/Deletes groups from the config", ChatColor.RESET);
        sppMessager.sendMessage(player, ".spp group [addWorld/removeWorld] <group> <world> ", ChatColor.GREEN, "- Adds/Removes worlds from a group", ChatColor.RESET);
        sppMessager.sendMessage(player, ChatColor.DARK_AQUA + "---" + ChatColor.RED + "Page 1 of 1" + ChatColor.DARK_AQUA + "---");
    }
}
