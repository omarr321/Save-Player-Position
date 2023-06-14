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
            if (args[0].equalsIgnoreCase("help")) {
                list.add("temp1");
                list.add("temp2");
            } else {
                list.add("help");
                list.add("version");
                list.add("reload");
            }
            return list;
        } else {
            return null;
        }
    }
}
