package me.xbones.reportplus.core;

import com.github.fernthedev.fernapi.universal.data.chat.ChatColor;
import com.github.fernthedev.fernapi.universal.util.UUIDFetcher;
import lombok.NonNull;
import me.xbones.reportplus.api.IRPlayer;
import me.xbones.reportplus.api.Report;
import me.xbones.reportplus.api.ReportPlusAPI;
import me.xbones.reportplus.api.ReportType;
import me.xbones.reportplus.core.commands.Command;
import me.xbones.reportplus.core.configuration.ConfigurationManager;
import me.xbones.reportplus.core.eventlisteners.MessageCreatedListener;
import me.xbones.reportplus.core.eventlisteners.ReadyEventListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Core {

    private JDA jda;
    private ReportPlusAPIHandler api;
    private IReportPlus reportPlus;
   private String commandPrefix;
   private boolean Bungeecord = false;

    public void initializeBot(IReportPlus reportPlus, String token, String prefix) throws LoginException, InterruptedException
    {
        this.reportPlus=reportPlus;

        this.commandPrefix = prefix;
            jda = new JDABuilder(token)
                .addEventListeners(new MessageCreatedListener(this), new ReadyEventListener(this))
                .build();

        jda.awaitReady();

        if ((boolean)ConfigurationManager.get("Change-Game")) {
            reportPlus.setGame(jda);
        }

        api = new ReportPlusAPIHandler(this);
        ReportPlusAPI.getInstance().setup(api);

    }

    public ReportPlusAPIHandler getApi() {
        return api;
    }

    public void addCommand(@NonNull Command command){
       jda.addEventListener(command);
    }

    public String getCommandPrefix() {
        return commandPrefix;
    }

    public JDA getJda() {
        return jda;
    }

    public IReportPlus getReportPlus() {
        return reportPlus;
    }

    public void disconnectBot(){
        jda.shutdown();
    }

    public void setBungeecord(boolean bungee){
        this.Bungeecord = bungee;
    }

    public void reportToDiscord(IRPlayer p, String reported, String Message) {
            boolean cancelled = reportPlus.callReportEvent(p,reported,Message, ReportType.DISCORD);
            if(cancelled) return;

        String title = getReportPlus().getLangData().getDiscordReportTitleMessageTitle().replace("%player%", p.getName())
                .replace("%report%", Message);
        String subtitle = getReportPlus().getLangData().getDiscordReportTitleMessageSubtitle()
                .replace("%player%",p.getName()).replace("%report%", Message);

        String imageLink = "https://cdn.discordapp.com/embed/avatars/0.png";

        if (p.isOnline()) {
            imageLink = "https://crafatar.com/avatars/" + UUIDFetcher.getUUID(p.getName());
        }
        int reportID;
        try {
           reportID = reportPlus.getReports().get(reportPlus.getReports().size() - 1).getReportId() + 1;
        } catch(Exception ex) {
            reportID = 1;
        }
        api.sendMessageToChannel(jda.getTextChannelById(getReportPlus().getReportsChannelID()).getId(),
                new EmbedBuilder().setDescription(getReportPlus().getLangData().getDiscordReportEmbed().getDescription())
                        .setColor(new Color(16711682))
                        .setThumbnail(imageLink)
                        .setTitle(getReportPlus().getLangData().getDiscordReportEmbed().getTitle())
                        .addField("Reporter", getReportPlus().getLangData().getDiscordReportEmbed().getReporter().replace("%reporter%", p.getName()), false)
                        .addField("Reported", getReportPlus().getLangData().getDiscordReportEmbed().getReported().replace("%reported%", reported), false)
                        .addField("Server", getReportPlus().getLangData().getDiscordReportEmbed().getServer().replace("%server%", reportPlus.getServerName(p)),false)
                        .addField("Report ID", getReportPlus().getLangData().getDiscordReportEmbed().getReportID().replace("%reportid%", Integer.toString(reportID)), false)
                        .addField("Report Content", getReportPlus().getLangData().getDiscordReportEmbed().getReportContent().replace("%reportcontent%", Message), false));
        Report r = new Report(reportPlus.getReports().get(reportPlus.getReports().size() - 1).getReportId()+ 1, p.getName(), reported, Message, ReportType.DISCORD, getReportPlus().getServerName(p));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        r.setDate(dtf.format(now));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', reportPlus.getPrefix() + " " + reportPlus.getLangData().getSuccessReport()).replace("%id%", String.valueOf(r.getReportId())));
        reportPlus.getSqlManager().addReportToDatabase(r);
        reportPlus.getReports().add(r);
        if(!(boolean)ConfigurationManager.get("Enabled-Modules.MySQL.Enabled"))
            reportPlus.saveReportsToConfig();
        reportPlus.broadcastTitle(title,subtitle,"reportplus.receive");
    }

    public void reportToStaff(IRPlayer player, String reported, String Message) {

        boolean cancelled = reportPlus.callReportEvent(player, reported, Message, ReportType.MINECRAFT);
        if (cancelled) return;

        String title = reportPlus.getLangData().getMinecraftReportTitleMessageTitle()
                .replace("%player%", player.getName()).replace("%report%", Message);
        String subtitle = reportPlus.getLangData().getDiscordReportTitleMessageSubtitle()
                .replace("%player%", player.getName()).replace("%report%", Message);


        reportPlus.broadcastNewReport(player, title, subtitle, reported, Message);

        int reportID;
        try {
            reportID = reportPlus.getReports().get(reportPlus.getReports().size() - 1).getReportId() + 1;
        } catch (Exception ex) {
            reportID = 1;
        }
        Report r = new Report(reportID, player.getName(), reported, Message, ReportType.MINECRAFT, getReportPlus().getServerName(player));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        r.setDate(dtf.format(now));
        reportPlus.getSqlManager().addReportToDatabase(r);
        reportPlus.getReports().add(r);
        if (!(Boolean) ConfigurationManager.get("Enabled-Modules.MySQL.Enabled"))
            reportPlus.saveReportsToConfig();
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', reportPlus.getPrefix() + " " + reportPlus.getLangData().getSuccessReport()).replace("%id%", String.valueOf(r.getReportId())));
    }

    public void reportToBoth(IRPlayer p, String reported, String Message) {
        boolean cancelled = reportPlus.callReportEvent(p,reported,Message,ReportType.BOTH);

        if(cancelled) return;

        String title = reportPlus.getLangData().getMinecraftReportTitleMessageTitle().replace("%player%", p.getName())
                .replace("%report%", Message);
        String subtitle = reportPlus.getLangData().getDiscordReportTitleMessageSubtitle()
                .replace("%player%", p.getName()).replace("%report%", Message);

        String imageLink = "https://cdn.discordapp.com/embed/avatars/0.png";

        if (p.isOnline()) {
            imageLink = "https://crafatar.com/avatars/" + UUIDFetcher.getUUID(p.getName());
        }
        int reportID;
        try {
            reportID = reportPlus.getReports().get(reportPlus.getReports().size() - 1).getReportId() + 1;
        } catch(Exception ex) {
            reportID = 1;
        }
        api.sendMessageToChannel(jda.getTextChannelById(getReportPlus().getReportsChannelID()).getId(),
                new EmbedBuilder().setDescription(getReportPlus().getLangData().getDiscordReportEmbed().getDescription())
                        .setColor(new Color(16711682))
                        .setThumbnail(imageLink)
                        .setTitle(getReportPlus().getLangData().getDiscordReportEmbed().getTitle())
                        .addField("Reporter", getReportPlus().getLangData().getDiscordReportEmbed().getReporter().replace("%reporter%", p.getName()), false)
                        .addField("Reported", getReportPlus().getLangData().getDiscordReportEmbed().getReported().replace("%reported%", reported), false)
                        .addField("Server", getReportPlus().getLangData().getDiscordReportEmbed().getServer().replace("%server%", reportPlus.getServerName(p)),false)
                        .addField("Report ID", getReportPlus().getLangData().getDiscordReportEmbed().getReportID().replace("%reportid%", Integer.toString(reportID)), false)
                        .addField("Report Content", getReportPlus().getLangData().getDiscordReportEmbed().getReportContent().replace("%reportcontent%", Message), false));

        reportPlus.broadcastNewReport(p,title,subtitle,reported,Message);

        Report r = new Report(reportID, p.getName(), reported, Message, ReportType.BOTH, getReportPlus().getServerName(p));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        r.setDate(dtf.format(now));
        reportPlus.getSqlManager().addReportToDatabase(r);
        reportPlus.getReports().add(r);
        if(!(Boolean)ConfigurationManager.get("Enabled-Modules.MySQL.Enabled"))
            reportPlus.saveReportsToConfig();
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', reportPlus.getPrefix() + " " + reportPlus.getLangData().getSuccessReport()).replace("%id%", String.valueOf(r.getReportId())));

    }
}
