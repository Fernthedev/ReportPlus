package me.xbones.reportplus.bungee.config;

import me.xbones.reportplus.core.IConfig;
import net.md_5.bungee.config.Configuration;

public class BungeeConfig implements IConfig {

    private Configuration config;

    public BungeeConfig(Object config){
        this.config=(Configuration)config;
    }

    public  Object get(String path) {
        return config.get(path);
    }

    public void set(String path, Object value){
        config.set(path,value);
    }
}
