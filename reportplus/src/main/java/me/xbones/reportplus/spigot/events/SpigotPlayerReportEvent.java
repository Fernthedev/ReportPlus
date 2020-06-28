package me.xbones.reportplus.spigot.events;

import me.xbones.reportplus.api.IRPlayer;
import me.xbones.reportplus.api.ReportType;
import me.xbones.reportplus.core.events.PlayerReportEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SpigotPlayerReportEvent extends Event implements Cancellable, PlayerReportEvent {

    private Player player;
    private static final HandlerList handlers = new HandlerList();
    private String reported;
    private String report;
    private ReportType type;
    private boolean isCancelled;

    public SpigotPlayerReportEvent(IRPlayer player, String reported, String report, ReportType type) {
        super(true);
        Player p = Bukkit.getPlayer(player.getUniqueId());
        this.player = p;
        this.report = report;
        this.reported = reported;
        this.type =type;
        this.isCancelled = false;
    }

    public Player getPlayer() {
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

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public void call(){
        Bukkit.getPluginManager().callEvent(this);
    }
}
