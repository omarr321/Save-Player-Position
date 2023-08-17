package spigot.savePlayerPosition.project.TabCompletions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import spigot.savePlayerPosition.project.Tools.configManager;

/**
 * @author Omar Radwan
 * @version 1.0.0
 * @about Figures out the tab completion for the commands
 */
public class sppTabCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            List<String> list = new ArrayList<>();
            switch(args.length) {
                case 0:
                case 1:
                    list.add("help");
                    list.add("version");
                    list.add("reload");
                    list.add("setdebug");
                    list.add("blacklist");
                    list.add("group");
                    list.add("clean");
                    list.add("setOnTeleport");
                    return list;
                case 2:
                    switch(args[0]) {
                        case "setdebug":
                            list.add("true");
                            list.add("false");
                            break;
                        case "blacklist":
                            list.add("add");
                            list.add("remove");
                            list.add("list");
                            break;
                        case "group":
                            list.add("create");
                            list.add("delete");
                            list.add("addWorld");
                            list.add("removeWorld");
                            list.add("list");
                            break;
                        case "setOnTeleport":
                            list.add("netherPortal");
                            list.add("endPortal");
                            list.add("endGateway");
                            break;
                    }
                    return list;
                case 3:
                    switch(args[1]) {
                        case "add":
                            list.addAll(configManager.getAllWorldsNotInGroupOrBlacklist());
                            break;
                        case "remove":
                            list.addAll(configManager.getBlacklist());
                            break;
                        case "delete":
                        case "removeWorld":
                        case "addWorld":
                            list.addAll(configManager.getAllGroups());
                            break;
                        case "netherPortal":
                        case "endPortal":
                        case "endGateway":
                            list.add("true");
                            list.add("false");
                    }
                    return list;
                case 4:
                    switch(args[1]) {
                        case "removeWorld":
                            list.addAll(configManager.getWorldsInGroup(args[2]));
                            break;
                        case "addWorld":
                            list.addAll(configManager.getAllWorldsNotInGroupOrBlacklist());
                            break;
                    }
                default:
                    return list;
            }
        } else {
            return null;
        }
    }
}
