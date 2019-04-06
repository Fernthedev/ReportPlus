package me.xbones.reportplus.api;

import java.util.UUID;

public interface IRPlayer {
    public UUID getUniqueId();

    public String getName();

    public boolean isOnline();

    public void sendMessage(String message);
}
