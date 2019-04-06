package me.xbones.reportplus.core;

import me.xbones.reportplus.api.IRPlayer;
import me.xbones.reportplus.api.Report;
import me.xbones.reportplus.api.ReportType;
import me.xbones.reportplus.core.MySQL.MySQLManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;

import java.util.List;
import java.util.UUID;

public interface IReportPlus {

    void setGame(JDA jda);

    String getMCChannelID();

    String getMessage(String path);

    void broadcast(String message);

    void broadcastTitle(String title,String subtitle, String permission);

    void broadcastNewReport(IRPlayer player, String title, String subtitle, String reported, String message);

    void dispatchCommand(String command);

    void AddTextCMD(Object obj, String cmd, String text);

    void AddCMDCMD(Object obj, String cmd, String cmdtobeexecuted);

    void log(String text);

    void addAnnouncement(String announcement);

    List<Report> getReports();

    void closeReport(String name, Report report, boolean discord, String Message);

    List<String> getMessages();

    void deleteAnnouncement(int id);

    void addCustomCommandsToEmbed(EmbedBuilder builder);

    void reloadPluginConfig();

    boolean isOnline(UUID uuid);

    String getReportsChannelID();

    String getServerName(IRPlayer p);

    void sendMessage(UUID uuid, String message);

    String getPrefix();

    MySQLManager getSqlManager();

    void saveReportsToConfig();

    Object getPlayer(UUID uuid);

    void callEvent(Object event);

    void sendConsole(String message);

    boolean callReportEvent(IRPlayer player, String reported, String report, ReportType type);
}
