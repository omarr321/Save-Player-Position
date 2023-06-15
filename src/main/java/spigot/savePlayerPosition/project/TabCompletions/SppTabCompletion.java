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
            if (args[0].isEmpty()) {
                list.add("help");
                list.add("version");
                list.add("reload");
                list.add("setDebug");
            } else if (args[0].equalsIgnoreCase("setDebug ")) {
                list.add("true");
                list.add("false");
            } else {
                list.add("");
            }

            if (!(args[1].isEmpty())) {
                list = new ArrayList<>();
                list.add("");
            }
            return list;
        } else {
            return null;
        }
    }
}
