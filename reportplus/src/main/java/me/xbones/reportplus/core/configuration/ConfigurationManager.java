package me.xbones.reportplus.core.configuration;


import me.xbones.reportplus.core.IConfig;

public class ConfigurationManager {

    private static boolean Bungeecord;
    private static IConfig config;

    public ConfigurationManager(boolean Bungeecord){
        this.Bungeecord=Bungeecord;
    }

    public static void SetConfig(IConfig object){
        config = object;
    }

    public static Object get(String path) {
        return config.get(path);
    }

    public static void set(String path, Object value){
        config.set(path,value);
    }
}
