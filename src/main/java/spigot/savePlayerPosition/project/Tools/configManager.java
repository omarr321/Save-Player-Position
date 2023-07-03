package spigot.savePlayerPosition.project.Tools;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import spigot.savePlayerPosition.project.Main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A class that manages the world data and group data
 */
public class configManager {
    private static final String strClass = "WorldManager";
    private static JavaPlugin plugin = JavaPlugin.getPlugin(Main.class);
    private static FileConfiguration config = plugin.getConfig();
    public static void enableConfigMan() {
        plugin.saveDefaultConfig();
        sppDebugger.setDebug(config.getBoolean("debug"));
    }

    public static boolean checkBlacklist(String name) {
        ArrayList<String> worlds = getBlacklist();

        for (String world : worlds) {
            if (world.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<String> getBlacklist() {
        List<String> list = (List<String>) config.getList("world.blacklist");
        ArrayList<String> worlds = null;
        if (list != null) {
            worlds = new ArrayList<String>((Collection<? extends String>) config.getList("world.blacklist"));
        }
        if (worlds == null) {
            worlds = new ArrayList<>();
        }

        return worlds;
    }

    public static void addBlacklistWorld(String name, Player player) {
        if(checkBlacklist(name)) {
            sppMessager.sendMessage(player, "World \"" + name + "\" is already in the blacklist");
            return;
        }
        List<String> list = (List<String>) config.getList("world.blacklist");
        ArrayList<String> worlds = null;
        if (list != null) {
            worlds = new ArrayList<String>((Collection<? extends String>) config.getList("world.blacklist"));
        }
        worlds.add(name);
        config.set("world.blacklist", worlds.stream().toList());
        plugin.saveConfig();
        sppMessager.sendMessage(player, "World \"" + name + "\" was added to the blacklist");
    }

    public static void removeBlacklistWorld(String name, Player player) {
        if (!(checkBlacklist(name))) {
            sppMessager.sendMessage(player, "World \"" + name + "\" is not on the blacklist");
            return;
        }
        List<String> list = (List<String>) config.getList("world.blacklist");
        ArrayList<String> worlds = null;
        if (list != null) {
            worlds = new ArrayList<>(list);
        }
        if (worlds == null) {
            worlds = new ArrayList<>();
        }
        try {
            worlds.remove(name);
            config.set("world.blacklist", worlds.stream().toList());
            plugin.saveConfig();
            sppMessager.sendMessage(player,  "World \"" + name + "\" was removed from the blacklist");
        } catch (Exception e) {
            sppMessager.sendMessage(player, "World \"" + name + "\" is not on the blacklist");
        }
    }
    public static void createGroup(String name, Player player) {
        String strMethod = "createGroup";
        sppDebugger.log(strClass, strMethod, "Attempting to create group \"" + name + "\"...");
        if (groupExists(name)) {
            sppMessager.sendMessage(player, "Group \"" + name + "\" already exists");
            sppDebugger.log(strClass, strMethod, "Group \"" + name + "\" already exists");
            return;
        }
        config.set("world.group." + name, new ArrayList<String>());
        plugin.saveConfig();
        sppDebugger.log(strClass, strMethod, "Group \"" + name + "\" has been created");
        sppMessager.sendMessage(player, "Group \"" + name + "\" has been created");
    }

    public static void deleteGroup(String name, Player player) {
        String strMethod = "deleteGroup";
        sppDebugger.log(strClass, strMethod, "Attempting to delete group \"" + name + "\"...");
        if (!(groupExists(name))) {
            sppMessager.sendMessage(player, "Group \"" + name + "\" does not exist");
            sppDebugger.log(strClass, strMethod, "Group \"" + name + "\" does not exist");
            return;
        }
        config.set("world.group." + name, null);
        plugin.saveConfig();
        sppMessager.sendMessage(player, "Group \"" + name + "\" has been deleted");
        sppDebugger.log(strClass, strMethod, "Group \"" + name + "\" has been deleted");
    }

    public static ArrayList<String> getWorldsInGroup(String name) {
        //sppDebugger.log("Getting group \"" + name + "\"...");
        ArrayList<String> worlds = (ArrayList<String>) config.getList("world.group." + name);
        return worlds;
    }

    public static ArrayList<String> getAllGroups() {
        //sppDebugger.log("Getting all groups...");
        ArrayList<String> groups = new ArrayList<>();
        if (config.getConfigurationSection("world.group").getKeys(false) != null) {
            groups = new ArrayList<>(config.getConfigurationSection("world.group").getKeys(false));
        }
        //sppDebugger.log("Groups: " + groups.toString());
        return groups;
    }

    public static boolean groupExists(String name) {
        //sppDebugger.log("Checking if group \"" + name + "\" exists...");
        for(String group : getAllGroups()) {
            if (name.equals(group)) {
                //sppDebugger.log("Group was found, return true...");
                return true;
            }
        }
        //sppDebugger.log("Group was not found, return false...");
        return false;
    }

    public static boolean worldExistsInGroup(String groupName, String worldName) {
        //sppDebugger.log("Checking if world \"" + worldName + "\" exists in group \"" + groupName + "\"...");
        if (!(groupExists(groupName))) {
            //sppDebugger.log("Group does not exist, returning false...");
            return false;
        }
        ArrayList<String> worlds = getWorldsInGroup(groupName);
        for(String world : worlds) {
            if (world.equals(worldName)) {
                //sppDebugger.log("World found in group, return true...");
                return true;
            }
        }
        //sppDebugger.log("World not found in group, returning false...");
        return false;
    }
    public static void addWorldToGroup(String groupName, String worldName, Player player) {
        String strMethod = "addWorldToGroup";
        sppDebugger.log(strClass, strMethod, "Attempting to add world \"" + worldName + "\" to group \"" + groupName + "\"");
        if (!(groupExists(groupName))) {
            sppDebugger.log(strClass, strMethod, "Group \"" + groupName + "\" does not exist");
            sppMessager.sendMessage(player, "Group \"" + groupName + "\" does not exist");
            return;
        }
        if (worldExistsInGroup(groupName, worldName)) {
            sppDebugger.log(strClass, strMethod, "World \"" + worldName + "\" is already in group \"" + groupName + "\"");
            sppMessager.sendMessage(player, "World \"" + worldName + "\" is already in group \"" + groupName + "\"");
            return;
        }
        ArrayList<String> worlds = getWorldsInGroup(groupName);
        worlds.add(worldName);
        config.set("world.group." + groupName, worlds);
        plugin.saveConfig();
        sppDebugger.log(strClass, strMethod, "World \"" + worldName + "\" was added to group \"" + groupName + "\"");
        sppMessager.sendMessage(player, "World \"" + worldName + "\" was added to group \"" + groupName + "\"");
    }

    public static void removeWorldFromGroup(String groupName, String worldName, Player player) {
        String strMethod = "removeWorldFromGroup";
        sppDebugger.log(strClass, strMethod, "Attempting to remove world \"" + worldName + "\" from group \"" + groupName + "\"...");
        if (!(groupExists(groupName))) {
            sppDebugger.log(strClass, strMethod, "Group \"" + groupName + "\" does not exist");
            sppMessager.sendMessage(player, "Group \"" + groupName + "\" does not exist");
            return;
        }
        if (!(worldExistsInGroup(groupName, worldName))) {
            sppDebugger.log(strClass, strMethod, "World \"" + worldName + "\" is not in \"" + groupName + "\"");
            sppMessager.sendMessage(player, "World \"" + worldName + "\" is not in \"" + groupName + "\"");
            return;
        }
        ArrayList<String> worlds = getWorldsInGroup(groupName);
        ArrayList<String> temp = new ArrayList<>();
        for(String world : worlds) {
            if (!(world.equals(worldName))) {
                temp.add(world);
            }
        }
        config.set("world.group." + groupName, temp);
        plugin.saveConfig();
        sppDebugger.log(strClass, strMethod,"World \"" + worldName + "\" was removed from group \"" + groupName + "\"");
        sppMessager.sendMessage(player, "World \"" + worldName + "\" was removed from group \"" + groupName + "\"");
    }

    public static ArrayList<String> getWorldsInAllGroups() {
        ArrayList<String> worlds = new ArrayList<>();
        ArrayList<String> groups = configManager.getAllGroups();
        for(String group : groups) {
            worlds.addAll(configManager.getWorldsInGroup(group));
        }
        return worlds;
    }

    public static ArrayList<String> getAllWorldsNotInGroupOrBlacklist() {
        ArrayList<String> groupWorlds = getWorldsInAllGroups();
        ArrayList<String> blacklistedWorlds = configManager.getBlacklist();
        ArrayList<String> temp = new ArrayList<>();
        List<World> allWorld = Bukkit.getWorlds();

        for(World world : allWorld) {
            if (!(groupWorlds.contains(world.getName()) || blacklistedWorlds.contains(world.getName()))) {
                temp.add(world.getName());
            }
        }
        return temp;
    }

    public static String getGroupWorldIsPartOf(String worldName) {
        for(String group : getAllGroups()) {
            for (String world : getWorldsInGroup(group)) {
                if (world.equals(worldName)) {
                    return group;
                }
            }
        }
        return null;
    }

    public static boolean getTeleport(String teleportKey) {
        String strMethod = "getTeleport";
        Boolean value = config.getBoolean(teleportKey);
        sppDebugger.log(strClass, strMethod, "Teleport \"" + teleportKey + "\" value is \"" + value + "\"");
        return value;
    }

    public static void setTeleport(String teleportKey, Boolean value) {
        String strMethod = "setTeleport";
        sppDebugger.log(strClass, strMethod, "Setting \"" + teleportKey + "\" value to \"" + value + "\"");
        config.set(teleportKey, value);
        plugin.saveConfig();
    }
}
