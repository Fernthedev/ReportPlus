package me.xbones.reportplus.bungee.listeners;

import me.xbones.reportplus.bungee.ReportPlus;
import me.xbones.reportplus.bungee.events.BungeePlayerReportEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class PlayerReportListener implements Listener {

    private ReportPlus main;

    public PlayerReportListener(ReportPlus main)
    {
        this.main=main;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onReport(BungeePlayerReportEvent e)
    {
        if(main.getConfig().getBoolean("Enabled-Modules.MySQL.Enabled"))
        main.setReportsList(main.getSqlManager().getReports());
    }
}
