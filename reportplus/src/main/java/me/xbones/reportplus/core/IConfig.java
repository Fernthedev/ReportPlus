package me.xbones.reportplus.core;

public interface IConfig {
    Object get(String path);

    void set(String path, Object value);
}
