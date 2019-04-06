package me.xbones.reportplus.bungee.events;

import me.xbones.reportplus.api.IRPlayer;
import me.xbones.reportplus.core.Core;
import me.xbones.reportplus.core.RPlayer;
import me.xbones.reportplus.api.ReportType;
import me.xbones.reportplus.core.events.PlayerReportEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

public class BungeePlayerReportEvent extends Event implements Cancellable, PlayerReportEvent {
    private ProxiedPlayer player;
    private String reported;
    private String report;
    private ReportType type;
    private boolean isCancelled;
    private Core core;

    public BungeePlayerReportEvent(Core core, IRPlayer player, String reported, String report, ReportType type) {
        this.core=core;
        ProxiedPlayer p = (ProxiedPlayer)core.getReportPlus().getPlayer(player.getUniqueId());
        this.player = p;
        this.report = report;
        this.reported = reported;
        this.type =type;
        this.isCancelled = false;
    }

    public ProxiedPlayer getPlayer() {
        return this.player;
    }

    public String getReported() {
        return reported;
    }

    public String getReport() {
        return report;
    }

    public ReportType getType() {
        return type;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean arg0) {
        this.isCancelled = arg0;
    }

    public void call(){
        core.getReportPlus().callEvent(this);
    }
}
