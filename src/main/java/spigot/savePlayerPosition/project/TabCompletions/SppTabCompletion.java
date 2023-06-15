package spigot.savePlayerPosition.project.TabCompletions;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

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
                    return list;
                case 2:
                    if (args[0].equalsIgnoreCase("setdebug")) {
                        list.add("true");
                        list.add("false");
                    } else if (args[0].equalsIgnoreCase("blacklist")) {
                        list.add("add");
                        list.add("remove");
                    }
                    return list;
                case 3:
                    if (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("remove")) {
                        List<World> tempList = Bukkit.getWorlds();
                        for (World wor : tempList ) {
                            list.add(wor.getName());
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
