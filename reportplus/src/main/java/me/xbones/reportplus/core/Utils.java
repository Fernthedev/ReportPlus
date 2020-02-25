package me.xbones.reportplus.core;


import com.github.fernthedev.config.common.Config;
import com.github.fernthedev.fernapi.universal.data.chat.ChatColor;
import me.xbones.reportplus.core.gson.LangConfig;

public abstract class Utils {

    public static String CCT(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public abstract Config<LangConfig> getLanguageConfig();
}
