package spigot.savePlayerPosition.project.Tools;

import java.util.HashMap;

public class lastCommand {
    public static HashMap<String, String> lastMessage = new HashMap<String, String>();

    public static void setLastMessage(String uuid, String message) {
        lastMessage.put(uuid, message);
    }

    public static String getLastMessage(String uuid) {
        return lastMessage.get(uuid);
    }

}
