package spigot.savePlayerPosition.project.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import spigot.savePlayerPosition.project.Main;
import spigot.savePlayerPosition.project.Tools.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/** @author Omar Radwan
 * @version 1.0.0
 * @about Gets a command string and figures out what to do
 */
public class CommandSpp implements CommandExecutor {
    private static final String strClass = "CommandSpp";
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command!");
            return false;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            showHelp(player);
            return true;
        }
        switch(args[0]) {
            case "clean":
                if (!(player.hasPermission("spp.*") || player.hasPermission("spp.admin.*") || player.hasPermission("spp.admin.clean"))) {
                    sppMessager.sendMessage(player, "You do not have permission: ", ChatColor.RED, "spp.admin.clean", ChatColor.YELLOW);
                    return true;
                }
                if (args.length != 1) {
                    sppMessager.sendMessage(player, "Error: Incorrect args amount!", ChatColor.RED);
                    return true;
                }
                clean(player);
                break;
            case "help":
                if (args.length != 1) {
                    sppMessager.sendMessage(player, "Error: Incorrect args amount!", ChatColor.RED);
                    return true;
                }
                showHelp(player);
                break;
            case "version":
                if (!(player.hasPermission("spp.*") || player.hasPermission("spp.command.*") || player.hasPermission("spp.command.version"))) {
                    sppMessager.sendMessage(player, "You do not have permission: ", ChatColor.RED, "spp.command.version", ChatColor.YELLOW);
                    return true;
                }
                if (args.length != 1) {
                    sppMessager.sendMessage(player, "Error: Incorrect args amount!", ChatColor.RED);
                    return true;
                }
                sppMessager.sendMessage(player, "---------------------------", ChatColor.DARK_AQUA);
                sppMessager.sendMessage(player, "Save Player Position v1.3.1", ChatColor.GREEN);
                sppMessager.sendMessage(player, "Minecraft Native version: 1.19", ChatColor.GREEN);
                sppMessager.sendMessage(player, "---------------------------", ChatColor.DARK_AQUA);
                break;
            case "reload":
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
                break;
            case "setdebug":
                if (!(player.hasPermission("spp.*") || player.hasPermission("spp.command.*") || player.hasPermission("spp.command.setdebug"))) {
                    sppMessager.sendMessage(player, "You do not have permission: ", ChatColor.RED, "spp.command.setdebug", ChatColor.YELLOW);
                    return true;
                }
                if (args.length != 2) {
                    sppMessager.sendMessage(player, "Error: Incorrect args amount!", ChatColor.RED);
                    return true;
                }
                switch (args[1]) {
                    case "true":
                        sppMessager.sendMessage(player, "Setting debug to true");
                        saveConfig(true);
                        break;
                    case "false":
                        sppMessager.sendMessage(player, "Setting debug to false");
                        saveConfig(false);
                        break;
                    default:
                        sppMessager.sendMessage(player, "Error: Unknown value!", ChatColor.RED);
                }
                break;
            case "blacklist":
                if (!(player.hasPermission("spp.*") || player.hasPermission("spp.admin.*") || player.hasPermission("spp.admin.blacklist"))) {
                    sppMessager.sendMessage(player, "You do not have permission: ", ChatColor.RED, "spp.admin.blacklist", ChatColor.YELLOW);
                    return true;
                }
                if (args.length == 2) {
                    switch (args[1]) {
                        case "list":
                            sppMessager.sendMessage(player, ChatColor.DARK_AQUA + "---" + ChatColor.RED + "Black List" + ChatColor.DARK_AQUA + "---");
                            if (configManager.getBlacklist().toString().length() == 2) {
                                sppMessager.sendMessage(player, "NONE", ChatColor.YELLOW);
                            } else {
                                for(String world : configManager.getBlacklist()) {
                                    sppMessager.sendMessage(player, "- ", ChatColor.RESET, world, ChatColor.GREEN);
                                }
                            }
                            sppMessager.sendMessage(player, ChatColor.DARK_AQUA + "---" + ChatColor.RED + "Black List" + ChatColor.DARK_AQUA + "---");
                            break;
                        default:
                            sppMessager.sendMessage(player, "Error: Unknown command!", ChatColor.RED);
                    }
                } else {
                    if (args.length != 3) {
                        sppMessager.sendMessage(player, "Error: Incorrect args amount!", ChatColor.RED);
                        return true;
                    }
                    switch (args[1]) {
                        case "add":
                            for(String world : configManager.getWorldsInAllGroups()) {
                                if (world.equals(args[2])) {
                                    sppMessager.sendMessage(player, "That world is in a group and can not be added to the blacklist");
                                    return true;
                                }
                            }
                            configManager.addBlacklistWorld(args[2], player);
                            break;
                        case "remove":
                            configManager.removeBlacklistWorld(args[2], player);
                            break;
                        default:
                            sppMessager.sendMessage(player, "Error: Unknown value!", ChatColor.RED);
                    }
                }
                break;
            case "group":
                if (!(args.length >= 2)) {
                    sppMessager.sendMessage(player, "Error: Incorrect args amount!", ChatColor.RED);
                    return true;
                }
                switch(args[1]) {
                    case "create":
                    case "delete":
                        if (!(player.hasPermission("spp.*") || player.hasPermission("spp.admin.*") || player.hasPermission("spp.admin.group.*") || player.hasPermission("spp.admin.group.groups"))) {
                            sppMessager.sendMessage(player, "You do not have permission: ", ChatColor.RED, "spp.admin.group.groups", ChatColor.YELLOW);
                            return true;
                        }
                        if (args.length != 3) {
                            sppMessager.sendMessage(player, "Error: Incorrect args amount!", ChatColor.RED);
                            return true;
                        }
                        switch (args[1]) {
                            case "create":
                                configManager.createGroup(args[2], player);
                                break;
                            case "delete":
                                configManager.deleteGroup(args[2], player);
                        }
                        break;
                    case "addWorld":
                    case "removeWorld":
                        if (!(player.hasPermission("spp.*") || player.hasPermission("spp.admin.*") || player.hasPermission("spp.admin.group.*") || player.hasPermission("spp.admin.group.worlds"))) {
                            sppMessager.sendMessage(player, "You do not have permission: ", ChatColor.RED, "spp.admin.group.worlds", ChatColor.YELLOW);
                            return true;
                        }
                        if (args.length != 4) {
                            sppMessager.sendMessage(player, "Error: Incorrect args amount!", ChatColor.RED);
                            return true;
                        }
                        switch (args[1]) {
                            case "addWorld":
                                for(String world : configManager.getBlacklist()) {
                                    if (world.equals(args[3])) {
                                        sppMessager.sendMessage(player, "That world is in the blacklist and can not be added to a group");
                                        return true;
                                    }
                                }
                                for(String world : configManager.getWorldsInAllGroups()) {
                                    if (world.equals(args[3])) {
                                        sppMessager.sendMessage(player, "That world is in a group and can not be added");
                                        return true;
                                    }
                                }
                                configManager.addWorldToGroup(args[2], args[3], player);
                                break;
                            case "removeWorld":
                                configManager.removeWorldFromGroup(args[2], args[3], player);
                        }
                        break;
                    case "list":
                        if (!(player.hasPermission("spp.*") || player.hasPermission("spp.admin.*") || player.hasPermission("spp.admin.group.*") || player.hasPermission("spp.admin.group.groups"))) {
                            sppMessager.sendMessage(player, "You do not have permission: ", ChatColor.RED, "spp.admin.group.groups", ChatColor.YELLOW);
                            return true;
                        }
                        sppMessager.sendMessage(player, ChatColor.DARK_AQUA + "---" + ChatColor.RED + "Groups" + ChatColor.DARK_AQUA + "---");
                        if (configManager.getAllGroups().toString().length() == 2) {
                            sppMessager.sendMessage(player, "NONE", ChatColor.YELLOW);
                        } else {
                            for(String group : configManager.getAllGroups()) {
                                sppMessager.sendMessage(player, "- ", ChatColor.RESET, group, ChatColor.GREEN);
                                StringBuilder temp = new StringBuilder();
                                for(String world : configManager.getWorldsInGroup(group)) {
                                    temp.append(world + ", ");
                                }
                                if (temp.length() == 0) {
                                    sppMessager.sendMessage(player, "NONE", ChatColor.YELLOW);
                                } else {
                                    sppMessager.sendMessage(player, temp.substring(0, temp.length()-2).toString(), ChatColor.YELLOW);
                                }
                            }
                        }
                        sppMessager.sendMessage(player, ChatColor.DARK_AQUA + "---" + ChatColor.RED + "Groups" + ChatColor.DARK_AQUA + "---");
                        break;
                    default:
                        sppMessager.sendMessage(player, "Error: Unknown command!", ChatColor.RED);
                }
                break;
            case "setOnTeleport":
                if (!(player.hasPermission("spp.*") || player.hasPermission("spp.admin.*") || player.hasPermission("spp.admin.onTeleport"))) {
                    sppMessager.sendMessage(player, "You do not have permission: ", ChatColor.RED, "spp.admin.onTeleport", ChatColor.YELLOW);
                    return true;
                }
                if (args.length != 3) {
                    sppMessager.sendMessage(player, "Error: Incorrect args amount!", ChatColor.RED);
                    return true;
                }
                switch (args[1]) {
                    case "netherPortal":
                        switch (args[2]) {
                            case "true":
                                configManager.setTeleport("netherPortalTeleport", "true");
                                break;
                            case "false":
                                configManager.setTeleport("netherPortalTeleport", "false");
                                break;
                            default:
                                sppMessager.sendMessage(player, "Error: Not a boolean!", ChatColor.RED);
                        }
                        break;
                    case "endPortal":
                        switch (args[2]) {
                            case "true":
                                configManager.setTeleport("endPortalTeleport", "true");
                                break;
                            case "false":
                                configManager.setTeleport("endPortalTeleport", "false");
                                break;
                            default:
                                sppMessager.sendMessage(player, "Error: Not a boolean!", ChatColor.RED);
                        }
                        break;
                    case "endGateway":
                        switch (args[2]) {
                            case "true":
                                configManager.setTeleport("endGateTeleport", "true");
                                break;
                            case "false":
                                configManager.setTeleport("endGateTeleport", "false");
                                break;
                            default:
                                sppMessager.sendMessage(player, "Error: Not a boolean!", ChatColor.RED);
                        }
                        break;
                    default:
                        sppMessager.sendMessage(player, "Error: Unknown teleport type!", ChatColor.RED);
                }
                break;
            default:
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
        String strMethod = "reloadConfig";
        sppDebugger.log(strClass, strMethod, "Reloading config...");
        JavaPlugin.getPlugin(Main.class).reloadConfig();
        sppDebugger.setDebug(JavaPlugin.getPlugin(Main.class).getConfig().getBoolean("debug"));
    }

    private void showHelp(Player player) {
        sppMessager.sendMessage(player, ChatColor.DARK_AQUA + "---" + ChatColor.RED + "Save Player Position" + ChatColor.DARK_AQUA + "---");
        sppMessager.sendMessage(player, "/spp help ", ChatColor.GREEN, "- Shows this page", ChatColor.RESET);
        sppMessager.sendMessage(player, "/spp version ", ChatColor.GREEN, "- Shows the plugin version", ChatColor.RESET);
        sppMessager.sendMessage(player, "/spp reload ", ChatColor.GREEN, "- Reloads the config", ChatColor.RESET);
        sppMessager.sendMessage(player, "/spp clean ", ChatColor.GREEN, "- Cleans up old data for the config and players", ChatColor.RESET);
        sppMessager.sendMessage(player, "/spp setdebug <bool> ", ChatColor.GREEN, "- Sets the debug value", ChatColor.RESET);
        sppMessager.sendMessage(player, "/spp blacklist [add/remove] <world> ", ChatColor.GREEN, "- Adds/Removes a world from the blacklist", ChatColor.RESET);
        sppMessager.sendMessage(player, "/spp blacklist list ", ChatColor.GREEN, "- Lists all the blacklisted worlds", ChatColor.RESET);
        sppMessager.sendMessage(player, "/spp group [create/delete] <group> " , ChatColor.GREEN, "- Creates/Deletes groups from the config", ChatColor.RESET);
        sppMessager.sendMessage(player, "/spp group [addWorld/removeWorld] <group> <world> ", ChatColor.GREEN, "- Adds/Removes worlds from a group", ChatColor.RESET);
        sppMessager.sendMessage(player, "/spp group list ", ChatColor.GREEN, "- Lists all the groups and what worlds are in them", ChatColor.RESET);
        sppMessager.sendMessage(player, "/spp setOnTeleport <teleportType> <bool> ",ChatColor.GREEN, "- Sets if the plugin should works on different type of teleports", ChatColor.RESET);
        sppMessager.sendMessage(player, ChatColor.DARK_AQUA + "---" + ChatColor.RED + "Page 1 of 1" + ChatColor.DARK_AQUA + "---");
    }
    private void clean(Player player) {
        sppMessager.sendMessage(player, "Starting cleaning process...");
        String[] groups = configManager.getAllGroups().toArray(new String[0]);
        String[] blacklisted = configManager.getBlacklist().toArray(new String[0]);
        List<World> allWorldList = Bukkit.getWorlds();
        ArrayList<String> tempList = new ArrayList<>();
        for (World world : allWorldList) {
            tempList.add(world.getName());
        }
        String[] allWorld = tempList.toArray(new String[0]);

        sppMessager.sendMessage(player, "Cleaning up old group data...");
        for(String group : groups) {
            String[] currGroupWorlds = configManager.getWorldsInGroup(group).toArray(new String[0]);
            for(String world : currGroupWorlds) {
                boolean found = false;
                for(String temp : allWorld) {
                    if (temp.equals(world)) {
                        found = true;
                    }
                }
                if (!(found)) {
                    configManager.removeWorldFromGroup(group, world, player);
                }
            }
        }

        sppMessager.sendMessage(player, "Cleaning up blacklist...");
        for (String blacklist : blacklisted) {
            boolean found = false;
            for (String temp : allWorld) {
                if (temp.equals(blacklist)) {
                    found = true;
                }
            }
            if (!(found)) {
                configManager.removeBlacklistWorld(blacklist, player);
            }
        }

        sppMessager.sendMessage(player, "Cleaning up old player group data...");
        File userGroupFolder = playerDataManager.getUserGroupDataFolder();
        for(File file : userGroupFolder.listFiles()) {
            //sppMessager.sendMessage(player, "Cleaning group data for " + file.getName().toString().split("\\.")[0]);
            String[] groupData = playerDataManager.getGroupList(file.getName().toString().split("\\.")[0]);
            for(String group : groupData) {
                boolean found = false;
                for(String currGroup : groups) {
                    if (currGroup.equals(group)) {
                        found = true;
                    }
                }
                if (!(found)) {
                    sppMessager.sendMessage(player, "Deleting group \"" + group + "\"");
                    playerDataManager.removeGroupData(file.getName().toString().split("\\.")[0], group);
                }
            }
        }

        sppMessager.sendMessage(player, "Cleaning up old player world data...");
        File userWorldFolder = playerDataManager.getUserWorldDataFolder();
        for(File file : userWorldFolder.listFiles()) {
            //sppMessager.sendMessage(player, "Cleaning world data for " + file.getName().toString().split("\\.")[0]);
            String[] worldData = playerDataManager.getWorldList(file.getName().toString().split("\\.")[0]);
            for(String world : worldData) {
                boolean found = false;
                for(String currWorld : allWorld) {
                    if (currWorld.equals(world)) {
                        found = true;
                    }
                }
                if (!(found)) {
                    sppMessager.sendMessage(player, "Deleting world \"" + world + "\"");
                    playerDataManager.removeWorldData(file.getName().toString().split("\\.")[0], world);
                }
            }
        }

        sppMessager.sendMessage(player, "All old data has been cleaned up!");
    }
}