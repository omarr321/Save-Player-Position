package spigot.savePlayerPosition.project;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            List<String> list = new ArrayList<>();
            list.add("help");
            list.add("version");
            list.add("reload");
            return list;
        } else {
            return null;
        }
    }
}
