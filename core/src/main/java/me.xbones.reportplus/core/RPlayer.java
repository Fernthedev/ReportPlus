package me.xbones.reportplus.core;

import me.xbones.reportplus.api.IRPlayer;

import java.util.UUID;

public class RPlayer implements IRPlayer {

    private String name;
    private UUID uuid;
    private boolean isOnline;
    private Core core;
    public RPlayer(Core core, String name, UUID uuid){
        this.name=name;
        this.uuid=uuid;
this.core=core;
        isOnline = core.getReportPlus().isOnline(uuid);
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void sendMessage(String message){
        core.getReportPlus().sendMessage(uuid, message);
    }
}
