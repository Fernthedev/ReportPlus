package me.xbones.reportplus.core;


import com.github.fernthedev.fernapi.universal.data.chat.ChatColor;

public class Utils {

    public static String CCT(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
