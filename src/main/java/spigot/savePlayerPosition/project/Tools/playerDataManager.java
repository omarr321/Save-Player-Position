package spigot.savePlayerPosition.project.Tools;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import spigot.savePlayerPosition.project.Main;

import java.io.*;

public class playerDataManager {
    private static File userData = new File(JavaPlugin.getPlugin(Main.class).getDataFolder(), "playerData");

    public static void enablePlayerMan() {
        userData.mkdir();
    }

    private static boolean checkPlayerFile(String uuid) {
        File tempUser = new File(userData, uuid + ".dat");
        if (!(tempUser.exists())) {
            try {
                return tempUser.createNewFile();
            } catch (IOException e) {
                sppDebugger.forceLog("Error: Issue creating new player file!", ChatColor.RED);
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    public static void saveData(String uuid, String key, double xValue, double yValue, double zValue) {
        checkPlayerFile(uuid);

        try {
            sppDebugger.log("Checking file for world data of \"" + key + "\"...");
            BufferedReader file = new BufferedReader(new FileReader(new File(userData, uuid + ".dat")));
            String line = file.readLine();
            StringBuilder input = new StringBuilder();
            File tempUser = new File(userData, uuid + ".dat.tmp");
            tempUser.createNewFile();
            BufferedWriter tempFile = new BufferedWriter(new FileWriter(tempUser));
            boolean found = false;

            while (line != null) {
                String currWorld = line.split(":")[0];
                if (currWorld.equals(key)) {
                    sppDebugger.log("Found data for world \"" + key + "\"");
                    input.append(key + ":" + xValue + "," + yValue + "," + zValue).append('\n');
                    found = true;
                } else {
                    input.append(line).append('\n');
                }
                sppDebugger.log("Writing \"" + input.toString() + "\" to temp file");
                line = file.readLine();
            }
            tempFile.append(input.toString());
            if (!(found)) {
                sppDebugger.log("Appending world data to end of file");
                tempFile.append(key + ":" + xValue + "," + yValue + "," + zValue + '\n');
            }

            file.close();
            tempFile.close();
            new File(userData, uuid + ".dat").delete();
            new File(userData, uuid + ".dat.tmp").renameTo(new File(userData, uuid + ".dat"));
        } catch (FileNotFoundException e) {
            sppDebugger.forceLog("Error: Issue finding player file!", ChatColor.RED);
            throw new RuntimeException(e);
        } catch (IOException e) {
            sppDebugger.forceLog("Error: Issue loading player file!", ChatColor.RED);
            throw new RuntimeException(e);
        }
    }

    public static double[] getData(String uuid, String key) {
        checkPlayerFile(uuid);
        try {
            sppDebugger.log("Checking file for world data of \"" + key + "\"...");
            BufferedReader file = new BufferedReader(new FileReader(new File(userData, uuid + ".dat")));
            String line = file.readLine();
            while (line != null) {
                String currWorld = line.split(":")[0];
                sppDebugger.log("Checking key against \"" + currWorld + "\"");
                if (currWorld.equals(key)) {
                    String value = line.split(":")[1];
                    String[] cords = value.split(",");
                    double[] tempReturn = {Double.parseDouble(cords[0]), Double.parseDouble(cords[1]), Double.parseDouble(cords[2])};
                    file.close();
                    return tempReturn;
                }
                line = file.readLine();
            }
            file.close();
        } catch (FileNotFoundException e) {
            sppDebugger.forceLog("Error: Issue finding player file!", ChatColor.RED);
            throw new RuntimeException(e);
        } catch (IOException e) {
            sppDebugger.forceLog("Error: Issue loading player file!", ChatColor.RED);
            throw new RuntimeException(e);
        }
        return null;
    }
}
