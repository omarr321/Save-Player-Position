package spigot.savePlayerPosition.project.TabCompletions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import spigot.savePlayerPosition.project.Tools.worldManager;

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
                    return list;
                case 2:
                    if (args[0].equalsIgnoreCase("setdebug")) {
                        list.add("true");
                        list.add("false");
                    } else if (args[0].equalsIgnoreCase("blacklist")) {
                        list.add("add");
                        list.add("remove");
                        list.add("list");
                    } else if (args[0].equalsIgnoreCase("group")) {
                        list.add("create");
                        list.add("delete");
                        list.add("addWorld");
                        list.add("removeWorld");
                        list.add("list");
                    }
                    return list;
                case 3:
                    switch(args[1]) {
                        case "add":
                            list.addAll(worldManager.getAllWorldsNotInGroupOrBlacklist());
                            break;
                        case "remove":
                            list.addAll(worldManager.getBlacklist());
                            break;
                        case "delete":
                        case "removeWorld":
                        case "addWorld":
                            list.addAll(worldManager.getAllGroups());
                    }
                    return list;
                case 4:
                    if (args[1].equalsIgnoreCase("removeWorld")) {
                        list.addAll(worldManager.getWorldsInGroup(args[2]));
                    } else if (args[1].equalsIgnoreCase("addWorld")) {
                        list.addAll(worldManager.getAllWorldsNotInGroupOrBlacklist());
                    }
                default:
                    return list;
            }
        } else {
            return null;
        }
    }
}
