package spigot.savePlayerPosition.project.Tools;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import spigot.savePlayerPosition.project.Main;

import java.io.*;
import java.util.ArrayList;

/**
 * @author Omar Radwan
 * @version 1.0.0
 * @about Handles all calls to player data files
 */
public class playerDataManager {
    private static final File userData = new File(JavaPlugin.getPlugin(Main.class).getDataFolder(), "playerData");
    private static final File userWorldData = new File(userData, "worldData");
    private static final File userGroupData = new File(userData, "groupData");
    private static final String strClass = "PlayerDataManager";

    public static void enablePlayerMan() {
        userData.mkdir();
        userWorldData.mkdir();
        userGroupData.mkdir();
    }

    public static File getUserGroupDataFolder() {
        return userGroupData;
    }

    public static File getUserWorldDataFolder() {
        return userWorldData;
    }

    /**
     * Checks for a player data file and creates one if none is found
     * @param uuid - The uuid of the player
     * @return true
     */
    private static boolean checkPlayerWorldFile(String uuid) {
        File tempUser = new File(userWorldData, uuid + ".dat");
        if (!(tempUser.exists())) {
            try {
                return tempUser.createNewFile();
            } catch (IOException e) {
                sppDebugger.forceLog(strClass, "checkPlayerWorldFile","Error: Issue creating new player file!", ChatColor.RED);
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    /**
     * Saves the player x,y,z and rotation data for a world
     * @param uuid - The uuid of the player
     * @param key - The world the x,y,z data is for
     * @param xValue
     * @param yValue
     * @param zValue
     */
    public static void saveWorldData(String uuid, String key, double xValue, double yValue, double zValue) {
        saveWorldData(uuid, key, xValue, yValue, zValue, 0, 180);
    }

    /**
     * Saves the player x,y,z and rotation data for a world
     * @param uuid - The uuid of the player
     * @param key - The world the x,y,z data is for
     * @param xValue
     * @param yValue
     * @param zValue
     * @param xRot
     * @param yRot
     * @param zRot
     */
    public static void saveWorldData(String uuid, String key, double xValue, double yValue, double zValue, float yaw, float pitch) {
        checkPlayerWorldFile(uuid);
        String strMethod = "saveWorldData";
        try {
            sppDebugger.log(strClass, strMethod,"Checking file for world data of \"" + key + "\"...");
            BufferedReader file = new BufferedReader(new FileReader(new File(userWorldData, uuid + ".dat")));
            String line = file.readLine();
            StringBuilder input = new StringBuilder();
            File tempUser = new File(userWorldData, uuid + ".dat.tmp");
            tempUser.createNewFile();
            BufferedWriter tempFile = new BufferedWriter(new FileWriter(tempUser));
            boolean found = false;

            String posStr = key + ":" + xValue + "," + yValue + "," + zValue + "," + yaw + "," + pitch;
            while (line != null) {
                String currWorld = line.split(":")[0];
                if (currWorld.equals(key)) {
                    sppDebugger.log(strClass, strMethod, "Found data for world \"" + key + "\"");
                    input.append(posStr).append('\n');
                    found = true;
                } else {
                    input.append(line).append('\n');
                }
                line = file.readLine();
            }
            sppDebugger.log(strClass, strMethod,"Writing data to temp file");
            tempFile.append(input.toString());
            if (!(found)) {
                sppDebugger.log(strClass, strMethod, "Appending world data to end of file");
                tempFile.append(posStr).append('\n');
            }

            file.close();
            tempFile.close();
            new File(userWorldData, uuid + ".dat").delete();
            new File(userWorldData, uuid + ".dat.tmp").renameTo(new File(userWorldData, uuid + ".dat"));
        } catch (FileNotFoundException e) {
            sppDebugger.forceLog(strClass,strMethod, "Error: Issue finding player file!", ChatColor.RED);
            throw new RuntimeException(e);
        } catch (IOException e) {
            sppDebugger.forceLog(strClass, strMethod, "Error: Issue loading player file!", ChatColor.RED);
            throw new RuntimeException(e);
        }
    }

    /**
     * Removes world data from a player data file
     * @param uuid - The uuid of the player
     * @param key - The key of the data to remove
     */
    public static void removeWorldData(String uuid, String key) {
        checkPlayerWorldFile(uuid);
        String strMethod = "removeWorldData";
        try {
            sppDebugger.log(strClass, strMethod,"Checking file for world data of \"" + key + "\"...");
            BufferedReader file = new BufferedReader(new FileReader(new File(userWorldData, uuid + ".dat")));
            String line = file.readLine();
            StringBuilder input = new StringBuilder();
            File tempUser = new File(userWorldData, uuid + ".dat.tmp");
            tempUser.createNewFile();
            BufferedWriter tempFile = new BufferedWriter(new FileWriter(tempUser));

            while (line != null) {
                String currWorld = line.split(":")[0];
                if (!(currWorld.equals(key))) {
                    input.append(line).append('\n');
                } else {
                    sppDebugger.log(strClass, strMethod, "Removing world data for \"" + key + "\"...");
                }
                line = file.readLine();
            }
            sppDebugger.log(strClass, strMethod,"Writing data to temp file");
            tempFile.append(input.toString());

            file.close();
            tempFile.close();
            new File(userWorldData, uuid + ".dat").delete();
            new File(userWorldData, uuid + ".dat.tmp").renameTo(new File(userWorldData, uuid + ".dat"));
        } catch (FileNotFoundException e) {
            sppDebugger.forceLog(strClass,strMethod, "Error: Issue finding player file!", ChatColor.RED);
            throw new RuntimeException(e);
        } catch (IOException e) {
            sppDebugger.forceLog(strClass, strMethod, "Error: Issue loading player file!", ChatColor.RED);
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets a list of all world data a player has
     * @param uuid - The uuid of the player
     * @return - A list of all world data in the players save file
     */
    public static String[] getWorldList(String uuid) {
        checkPlayerWorldFile(uuid);
        String strMethod = "getWorldList";

        ArrayList<String> worldList = new ArrayList<>();
        try {
            BufferedReader file = new BufferedReader(new FileReader(new File(userWorldData, uuid + ".dat")));
            String line = file.readLine();

            while (line != null) {
                String currWorld = line.split(":")[0];
                worldList.add(currWorld);
                line = file.readLine();
            }
            file.close();

            return worldList.toArray(new String[0]);
        } catch (FileNotFoundException e) {
            sppDebugger.forceLog(strClass,strMethod, "Error: Issue finding player file!", ChatColor.RED);
            throw new RuntimeException(e);
        } catch (IOException e) {
            sppDebugger.forceLog(strClass, strMethod, "Error: Issue loading player file!", ChatColor.RED);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns The x,y,z of a world
     * @param uuid - The uuid of the player
     * @param key - The world to get x,y,z from
     * @return - The x,y,z in a double array
     */
    public static double[] getWorldData(String uuid, String key) {
        checkPlayerWorldFile(uuid);
        String strMethod = "getWorldData";
        try {
            sppDebugger.log(strClass, strMethod, "Checking file for world data of \"" + key + "\"...");
            BufferedReader file = new BufferedReader(new FileReader(new File(userWorldData, uuid + ".dat")));
            String line = file.readLine();
            while (line != null) {
                String currWorld = line.split(":")[0];
                //sppDebugger.log("Checking key against \"" + currWorld + "\"");
                if (currWorld.equals(key)) {
                    String value = line.split(":")[1];
                    String[] cords = value.split(",");
                    double[] tempReturn;
                    try {
                        String _N = cords[3];
                        tempReturn = new double[]{Double.parseDouble(cords[0]), Double.parseDouble(cords[1]), Double.parseDouble(cords[2]), Double.parseDouble(cords[3]), Double.parseDouble(cords[4])};
                    } catch (IndexOutOfBoundsException e) {
                        sppDebugger.log(strClass, strMethod, "Player pos value has no rotational data, return zeros for rotational");
                        tempReturn = new double[]{Double.parseDouble(cords[0]), Double.parseDouble(cords[1]), Double.parseDouble(cords[2]), 0, 180};
                    }
                    file.close();
                    sppDebugger.log(strClass, strMethod, "Found world data");
                    return tempReturn;
                }
                line = file.readLine();
            }
            file.close();
        } catch (FileNotFoundException e) {
            sppDebugger.forceLog(strClass, strMethod, "Error: Issue finding player file!", ChatColor.RED);
            throw new RuntimeException(e);
        } catch (IOException e) {
            sppDebugger.forceLog(strClass, strMethod, "Error: Issue loading player file!", ChatColor.RED);
            throw new RuntimeException(e);
        }
        sppDebugger.log(strClass, strMethod, "Did not find world data");
        return null;
    }

    /**
     * Checks a player's group data file and creates on if none is found
     * @param uuid - The uuid of the player
     * @return - True
     */
    private static boolean checkPlayerGroupFile(String uuid) {
        File tempUser = new File(userGroupData, uuid + ".dat");
        if (!(tempUser.exists())) {
            try {
                return tempUser.createNewFile();
            } catch (IOException e) {
                sppDebugger.forceLog(strClass, "checkPlayerGroupFile", "Error: Issue creating new player file!", ChatColor.RED);
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    /**
     * Gets the last world the player was in for a group
     * @param uuid - The uuid of the player
     * @param key - The group to get the last world for
     * @return - The world name or null if none was found
     */
    public static String getGroupData(String uuid, String key) {
        checkPlayerGroupFile(uuid);
        String strMethod = "getGroupData";
        try {
            sppDebugger.log(strClass, strMethod, "Checking file for group data of \"" + key + "\"...");
            BufferedReader file = new BufferedReader(new FileReader(new File(userGroupData, uuid + ".dat")));
            String line = file.readLine();
            while (line != null) {
                String currWorld = line.split(":")[0];
                //sppDebugger.log("Checking key against \"" + currWorld + "\"");
                if (currWorld.equals(key)) {
                    String value = line.split(":")[1];
                    file.close();
                    sppDebugger.log(strClass, strMethod, "Found group data");
                    return value;
                }
                line = file.readLine();
            }
            file.close();
        } catch (FileNotFoundException e) {
            sppDebugger.forceLog(strClass, strMethod, "Error: Issue finding player file!", ChatColor.RED);
            throw new RuntimeException(e);
        } catch (IOException e) {
            sppDebugger.forceLog(strClass, strMethod, "Error: Issue loading player file!", ChatColor.RED);
            throw new RuntimeException(e);
        }
        sppDebugger.log(strClass, strMethod, "Did not find group data");
        return null;
    }

    /**
     * Saves the player last world for a group
     * @param uuid - The uuid of the player
     * @param key - The group that need to be saved
     * @param value - The world that they were last in for this group
     */
    public static void saveGroupData(String uuid, String key, String value) {
        String strMethod = "saveGroupData";
        checkPlayerGroupFile(uuid);
        try {
            sppDebugger.log(strClass, strMethod, "Checking file for group data of \"" + key + "\"...");
            BufferedReader file = new BufferedReader(new FileReader(new File(userGroupData, uuid + ".dat")));
            String line = file.readLine();
            StringBuilder input = new StringBuilder();
            File tempUser = new File(userGroupData, uuid + ".dat.tmp");
            tempUser.createNewFile();
            BufferedWriter tempFile = new BufferedWriter(new FileWriter(tempUser));
            boolean found = false;

            while (line != null) {
                String currWorld = line.split(":")[0];
                if (currWorld.equals(key)) {
                    sppDebugger.log(strClass, strMethod, "Found data for group \"" + key + "\"");
                    input.append(key + ":" + value).append('\n');
                    found = true;
                } else {
                    input.append(line).append('\n');
                }
                line = file.readLine();
            }
            sppDebugger.log(strClass, strMethod, "Writing data to temp file");
            tempFile.append(input.toString());
            if (!(found)) {
                sppDebugger.log(strClass, strMethod, "Appending group data to end of file");
                tempFile.append(key + ":" + value + '\n');
            }

            file.close();
            tempFile.close();
            new File(userGroupData, uuid + ".dat").delete();
            new File(userGroupData, uuid + ".dat.tmp").renameTo(new File(userGroupData, uuid + ".dat"));
        } catch (FileNotFoundException e) {
            sppDebugger.forceLog(strClass, strMethod, "Error: Issue finding player file!", ChatColor.RED);
            throw new RuntimeException(e);
        } catch (IOException e) {
            sppDebugger.forceLog(strClass, strMethod, "Error: Issue loading player file!", ChatColor.RED);
            throw new RuntimeException(e);
        }
    }

    public static void removeGroupData(String uuid, String key) {
        checkPlayerWorldFile(uuid);
        String strMethod = "removeGroupData";
        try {
            sppDebugger.log(strClass, strMethod,"Checking file for group data of \"" + key + "\"...");
            BufferedReader file = new BufferedReader(new FileReader(new File(userGroupData, uuid + ".dat")));
            String line = file.readLine();
            StringBuilder input = new StringBuilder();
            File tempUser = new File(userGroupData, uuid + ".dat.tmp");
            tempUser.createNewFile();
            BufferedWriter tempFile = new BufferedWriter(new FileWriter(tempUser));

            while (line != null) {
                String currWorld = line.split(":")[0];
                if (!(currWorld.equals(key))) {
                    input.append(line).append('\n');
                } else {
                    sppDebugger.log(strClass, strMethod, "Removing group data for \"" + key + "\"...");
                }
                line = file.readLine();
            }
            sppDebugger.log(strClass, strMethod,"Writing data to temp file");
            tempFile.append(input.toString());

            file.close();
            tempFile.close();
            new File(userGroupData, uuid + ".dat").delete();
            new File(userGroupData, uuid + ".dat.tmp").renameTo(new File(userGroupData, uuid + ".dat"));
        } catch (FileNotFoundException e) {
            sppDebugger.forceLog(strClass,strMethod, "Error: Issue finding player file!", ChatColor.RED);
            throw new RuntimeException(e);
        } catch (IOException e) {
            sppDebugger.forceLog(strClass, strMethod, "Error: Issue loading player file!", ChatColor.RED);
            throw new RuntimeException(e);
        }
    }

    public static String[] getGroupList(String uuid) {
        checkPlayerWorldFile(uuid);
        String strMethod = "getGroupList";

        ArrayList<String> worldList = new ArrayList<>();
        try {
            BufferedReader file = new BufferedReader(new FileReader(new File(userGroupData, uuid + ".dat")));
            String line = file.readLine();

            while (line != null) {
                String currWorld = line.split(":")[0];
                worldList.add(currWorld);
                line = file.readLine();
            }
            file.close();

            return worldList.toArray(new String[0]);
        } catch (FileNotFoundException e) {
            sppDebugger.forceLog(strClass,strMethod, "Error: Issue finding player file!", ChatColor.RED);
            throw new RuntimeException(e);
        } catch (IOException e) {
            sppDebugger.forceLog(strClass, strMethod, "Error: Issue loading player file!", ChatColor.RED);
            throw new RuntimeException(e);
        }
    }
}
