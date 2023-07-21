package dev.risas.autobrewer.utilities;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ChatUtil {

    public String NORMAL_LINE = "&7&m-----------------------------";

    public String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public String[] translate(String[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = translate(array[i]);
        }
        return array;
    }

    public List<String> translate(List<String> list) {
        return list.stream().map(ChatUtil::translate).collect(Collectors.toList());
    }

    public void sendMessage(CommandSender sender, String text) {
        if (text.isEmpty()) return;
        sender.sendMessage(translate(text));
    }

    public void sendMessage(CommandSender sender, String[] array) {
        if (array.length == 0) return;
        sender.sendMessage(translate(array));
    }
}
