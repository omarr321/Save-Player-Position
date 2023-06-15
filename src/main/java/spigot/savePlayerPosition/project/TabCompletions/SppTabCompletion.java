package spigot.savePlayerPosition.project.TabCompletions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
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
                    return list;
                case 2:
                    if (args[0].equalsIgnoreCase("setdebug")) {
                        list.add("true");
                        list.add("false");
                    }
                    return list;
                default:
                    return list;
            }
        } else {
            return null;
        }
    }
}
