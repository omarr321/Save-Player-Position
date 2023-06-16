package spigot.savePlayerPosition.project.TabCompletions;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import spigot.savePlayerPosition.project.Tools.worldManager;

public class SppTabCompletion implements TabCompleter {
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
                    } else if (args[0].equalsIgnoreCase("group")) {
                        list.add("create");
                        list.add("delete");
                        list.add("addWorld");
                        list.add("removeWorld");
                    }
                    return list;
                case 3:
                    if (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("remove")) {
                        List<World> tempList = Bukkit.getWorlds();
                        for (World wor : tempList ) {
                            list.add(wor.getName());
                        }
                    } else if (args[1].equalsIgnoreCase("delete") || args[1].equalsIgnoreCase("removeWorld") || args[1].equalsIgnoreCase("addWorld")) {
                        ArrayList<String> groups = worldManager.getAllGroups();
                        for (String group : groups) {
                            list.add(group);
                        }
                    }
                    return list;
                case 4:
                    if (args[1].equalsIgnoreCase("removeWorld")) {
                        ArrayList<String> worlds = worldManager.getGroup(args[2]);
                        for (String world : worlds) {
                            list.add(world);
                        }
                    }
                default:
                    return list;
            }
        } else {
            return null;
        }
    }
}
