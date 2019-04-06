package me.xbones.reportplus.core.events;

import me.xbones.reportplus.api.ReportType;

public interface PlayerReportEvent {
    public Object getPlayer();

    public String getReported();

    public String getReport();

    public ReportType getType();


    public boolean isCancelled();


    public void setCancelled(boolean arg0);

    public void call();
}
