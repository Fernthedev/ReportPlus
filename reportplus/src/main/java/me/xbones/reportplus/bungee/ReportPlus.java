package me.xbones.reportplus.bungee;

import com.github.fernthedev.config.common.Config;
import com.github.fernthedev.fernapi.server.bungee.FernBungeeAPI;
import com.github.fernthedev.fernapi.universal.Universal;
import com.github.fernthedev.fernapi.universal.api.IFPlayer;
import com.github.fernthedev.fernapi.universal.data.chat.TextMessage;
import lombok.Getter;
import me.xbones.reportplus.api.IRPlayer;
import me.xbones.reportplus.api.Report;
import me.xbones.reportplus.api.ReportType;
import me.xbones.reportplus.bungee.Bstats.Metrics;
import me.xbones.reportplus.bungee.chatcomponentapi.ChatComponentMessage;
import me.xbones.reportplus.bungee.config.BungeeConfig;
import me.xbones.reportplus.bungee.events.BungeePlayerReportEvent;
import me.xbones.reportplus.bungee.listeners.*;
import me.xbones.reportplus.core.Core;
import me.xbones.reportplus.core.IReportPlus;
import me.xbones.reportplus.core.MySQL.MySQLManager;
import me.xbones.reportplus.core.ReportPlusAPIHandler;
import me.xbones.reportplus.core.Utils;
import me.xbones.reportplus.core.commands.*;
import me.xbones.reportplus.core.configuration.ConfigurationManager;
import me.xbones.reportplus.core.gson.LangConfig;
import me.xbones.reportplus.core.universalcommands.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ReportPlus extends FernBungeeAPI implements IReportPlus {

    // VARIABLES //
    private Core core;
    private String TOKEN;
    private String cmdPrefix;
    private String reportChannelID, CMDChannelID, MCChannelID;

    private Set<String> minecraftChosen = new HashSet<>();
    private Set<String> discordChosen = new HashSet<>();
    private Set<String> bothChosen = new HashSet<>();
    private Set<String> sendingMessage = new HashSet<>();
    private Map<ProxiedPlayer, ProxiedPlayer> reporting = new HashMap<>();
    private String prefix;
    private MySQLManager sqlManager;
    private List<Report> reportsList;
    private BungeeUtils utils;
    private List<String> messagesList;
    private String lastMessage = "";
    private ReportPlus main;
    private Map<String,Report> selectedReports;
    private CommandSender console;
    private static final java.util.logging.Logger logger = ProxyServer.getInstance().getLogger();
    private Configuration config;
    // VARIABLES //

    @Getter
    private static boolean disable;

    @Override
    public void onEnable(){
        super.onEnable();
        core = new Core();
        main = this;
        new Metrics(this);
        new ConfigurationManager(true);
        initConfig();
        ConfigurationManager.SetConfig(new BungeeConfig(getConfig()));
        core.setBungeecord(true);
        console = getProxy().getConsole();
        console.sendMessage(new TextComponent(Utils.CCT("&c--- &6REPORTPLUS BUNGEE &c---")));
        console.sendMessage(new TextComponent(Utils.CCT("&c     &7LOADING...     ")));

        checkDependencies();

        initializeVariables();



        try {
            core.initializeBot(this, TOKEN  , cmdPrefix);
        }catch(Exception ex){
            console.sendMessage(new TextComponent(Utils.CCT("&c ERROR INITIALIZING BOT  ")));
            ex.printStackTrace();
        }

        try{
            reportsList = new ArrayList<>();
            sqlManager = new MySQLManager(core);

            if(getConfig().getBoolean("Enabled-Modules.MySQL.Enabled"))
            {
                reportsList = sqlManager.getReports();
            }
            else
                utils.createReportsYML();



        }catch(Exception ex){
            console.sendMessage(new TextComponent(Utils.CCT("&c ERROR INITIALIZING MYSQL  ")));
            ex.printStackTrace();
        }
        utils.createLangConfig();
        utils.createMessagesYML();

        if(this.getConfig().getBoolean("Enabled-Modules.Console")) {

            LogAppender handler = new LogAppender(core);

            logger.addHandler(handler);

        }

        if (this.getConfig().getBoolean("Enabled-Modules.Announcements"))
            startAnnouncing();
        initializeCommands();
        initializeEvents();
        getProxy().getScheduler().schedule(this, () -> {
            if(main.getConfig().getBoolean("Enabled-Modules.Server-Stop-Start"))
                main.core.getJda().getTextChannelById(main.getConfig().getString("Server-Stop-Start-Channel")).sendMessage(main.getUtils().getMessagesConfig().getString("Server-Start-Message")).queue();

        }, 1, TimeUnit.SECONDS);
        console.sendMessage(new TextComponent(Utils.CCT("&c   &7PLUGIN LOADED.   ")));
        console.sendMessage(new TextComponent(Utils.CCT("&c--- &6REPORTPLUS BUNGEE &c---")));

    }


    @Override
    public void onDisable() {
        disable = true;
        super.onDisable();
        console.sendMessage(new TextComponent(Utils.CCT("&c--- &6REPORTPLUS BUNGEE &c---")));
        core.disconnectBot();
        console.sendMessage(new TextComponent(Utils.CCT("&c    PLUGIN DISABLED   ")));
        console.sendMessage(new TextComponent(Utils.CCT("&c--- &6REPORTPLUS BUNGEE &c---")));
        if(main.getConfig().getBoolean("Enabled-Modules.Server-Stop-Start"))
            core.getJda().getTextChannelById(this.getConfig().getString("Server-Stop-Start-Channel")).sendMessage(main.getUtils().getMessagesConfig().getString("Server-Stop-Message")).queue();


    }

    public void initConfig(){
        try {

            if (!getDataFolder().exists())
                getDataFolder().mkdir();

            File file = new File(getDataFolder(), "config.yml");


            if (!file.exists()) {
                try (InputStream in = getResourceAsStream("bungee config.yml")) {
                    Files.copy(in, file.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

             config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public Configuration getConfig() {
       return config;
    }

    public void saveConfig(){
        try{
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(getConfig(), new File(getDataFolder(), "config.yml"));

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void checkDependencies() {
        /*
        if (!getProxy().getPluginManager().isPluginEnabled("TitleAPI")) {
            console.sendMessage(Utils.CCT("&c TITLE API NOT FOUND  "));
        }else {
            console.sendMessage(Utils.CCT("   &a TITLE API FOUND   "));
        }

        if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            console.sendMessage(Utils.CCT("&c VAULT NOT FOUND  "));
        }else {
            console.sendMessage(Utils.CCT("   &a VAULT FOUND   "));
        }*/
    }

    public void initializeVariables() {
        TOKEN = this.getConfig().getString("Discord-Bot-Token");
        cmdPrefix = this.getConfig().getString("Discord-Bot-Command-Prefix");
        reportChannelID = this.getConfig().getString("Discord-Channel-ID");
        prefix = this.getConfig().getString("Prefix");
        CMDChannelID = this.getConfig().getString("Discord-CMD-Channel-ID");
        MCChannelID = this.getConfig().getString("Discord-MC-Channel-ID");
        utils = new BungeeUtils(this);

        messagesList = this.getConfig().getStringList("Announcements");

        selectedReports = new HashMap<>();
        // api = new ReportPlusAPIHandler(this);
    }

    public ReportPlusAPIHandler getAPI(){
        return core.getApi();
    }

    public void initializeEvents(){
        PluginManager manager = getProxy().getPluginManager();
        manager.registerListener(this, new PlayerChatListener(core));
        manager.registerListener(this, new PlayerCommandListener(core, this));
        manager.registerListener(this,new PlayerJoinListener(core,this));
        manager.registerListener(this,new PlayerLeaveListener(core,this));
        manager.registerListener(this, new PlayerReportListener(this));
    }

    public Core getCore() {
        return core;
    }

    @Override
    public void NoPerm(IFPlayer<?> p) {
        p.sendMessage(new TextMessage(ChatColor.translateAlternateColorCodes('&',
                prefix + " " + utils.getMessagesConfig().getString("No-Permission"))));
    }

    public void listReports(ProxiedPlayer p, int pageNumber){
        int pageIndex = pageNumber - 1;
        if(reportsList.size() < 1){
            ChatComponentMessage message = new ChatComponentMessage(ChatColor.translateAlternateColorCodes('&',
                    "&cThere are no reports."));

            p.sendMessage(message.getComponent());
            return;
        }
        List<List<Report>> pages = chopped(reportsList, 5);
        List<Report> page = pages.get(pageIndex);
        for(Report r : page)
        {
            ChatComponentMessage message = new ChatComponentMessage(ChatColor.translateAlternateColorCodes('&', "&7Report &b#&c" + r.getReportId() +" &7- By &c" + r.getReporter() + " &7 - Reported: &c" + r.getReported() + " &7- Server: &c" + r.getServer()));

            message.addHover(ChatColor.translateAlternateColorCodes('&', "&6" + r.getReportContent()));
            message.addClick(ClickEvent.Action.SUGGEST_COMMAND, "/closereport " + r.getReportId() + " Your Message here");
            p.sendMessage(message.getComponent());
        }

        try {
            pages.get(pageNumber);
            ChatComponentMessage message = new ChatComponentMessage(ChatColor.translateAlternateColorCodes('&',
                    "&bTo view the next page, use /reports &c" +(pageNumber + 1)));

            p.sendMessage(message.getComponent());

        }catch(Exception ex){}
    }

    public void initializeCommands() {
        Universal.getCommandHandler().registerCommand(new ReportCommand(this));
        Universal.getCommandHandler().registerCommand(new TXTCmd(this));
        Universal.getCommandHandler().registerCommand(new CmdCMD(this));
        Universal.getCommandHandler().registerCommand(new ListReportsCMD(this));
        Universal.getCommandHandler().registerCommand(new ReportPlusCommand(this));
        Universal.getCommandHandler().registerCommand(new CloseReportCMD(this));
        core.addCommand(new ReloadCommand(core));
        core.addCommand(new AddAnnouncementCommand(core));
        core.addCommand(new ListAnnouncementsCommand(core));
        core.addCommand(new HelpCommand(core));
        core.addCommand(new DelAnnouncementCommand(core));
        core.addCommand(new CloseReportWithMessage(core));
        if (this.getConfig().getSection("Cmds") != null) {
            Collection<String> Cmds = this.getConfig().getSection("Cmds").getKeys();
            for (String cmd : Cmds) {
                core.addCommand(new CustomCMD(core, cmd));
            }
        }

        if (this.getConfig().getSection("TXTCmds") != null) {
            Collection<String> Cmds = this.getConfig().getSection("TXTCmds").getKeys();
            for (String cmd : Cmds) {
                core.addCommand(new CustomTXTCMD(core, cmd));

            }
        }


    }

    static <T> List<List<T>> chopped(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<List<T>>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<T>(
                    list.subList(i, Math.min(N, i + L)))
            );
        }
        return parts;
    }

    public void startAnnouncing() {
        getProxy().getScheduler().schedule(this, new Runnable() {
            @Override
            public void run() {

                Random random = new Random();
                int index = random.nextInt(messagesList.size());
                if (messagesList.get(index).equals(lastMessage))
                    index = random.nextInt(messagesList.size());
                lastMessage = messagesList.get(index);
                core.getJda().getTextChannelById(main.getConfig().getString("Discord-Announcements-Channel-ID"))
                        .sendMessage("```" + messagesList.get(index) + "```").queue();

            }
        },(long)this.getConfig().getInt("Interval"), TimeUnit.SECONDS);
    }

    public BungeeUtils getUtils() {
        return utils;
    }

    public String getCMDChannelID() {
        return CMDChannelID;
    }

    public void setReportsList(List<Report> newList){
        reportsList = newList;
    }

    @Override
    public Config<LangConfig> getLangConfig() {
        return utils.getLangConfig();
    }

    @Override
    public void setGame(JDA jda) {
        jda.getPresence().setActivity(Activity.playing(((String)ConfigurationManager.get("Game")).replace("%players%",  String.valueOf(getProxy().getOnlineCount()))));

        if(getConfig().getBoolean("Auto-Refresh-Game")){
            getProxy().getScheduler().schedule(this, () -> jda.getPresence().setActivity(Activity.playing(((String)ConfigurationManager.get("Game")).replace("%players%",  String.valueOf(getProxy().getOnlineCount())))), 1L, 10, TimeUnit.SECONDS);
        }
    }

    @Override
    public String getMCChannelID() {
        return MCChannelID;
    }

    @Override
    public String getMessage(String path) {
        return utils.getMessagesConfig().getString(path);
    }

    @Override
    public void broadcast(String message) {
        getProxy().broadcast(new TextComponent(message));
    }

    @Override
    public void broadcastTitle(String title, String subtitle, String permission) {
        // Not implemented in Bungeecord.
    }

    @Override
    public void broadcastNewReport(IRPlayer player, String title, String subtitle, String reported, String message) {
        for (ProxiedPlayer p : getProxy().getPlayers()) {
            if (p.hasPermission("reportplus.receive")) {
                for (String s : main.getUtils().getMessagesConfig().getStringList("Minecraft-Report-Format")) {
                    p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', s.replace("%reporter%", player.getName()).replace("%reported%", reported).replace("%reportcontent%", message).replace("%server%", getServerName(player)))));

                }
            }
        }
    }

    @Override
    public void dispatchCommand(String command) {
        main.getProxy().getPluginManager().dispatchCommand(main.getProxy().getConsole(), command);
    }

    @Override
    public void AddTextCMD(Object obj, String cmd, String text) {
        CommandSender p = (CommandSender)obj;
        main.getConfig().set("TXTCmds." + cmd + ".text", text);
        main.getConfig().set("TXTCmds." + cmd + ".description", "My cmd! (Configure this in the config.yml)");
        main.saveConfig();
        main.reloadPluginConfig();

        core.addCommand(new CustomTXTCMD(core, cmd));
        p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &aSuccess!")));

    }

    @Override
    public void AddCMDCMD(Object obj, String cmd, String cmdtobeexecuted) {
        CommandSender p = (CommandSender)obj;

        main.getConfig().set("Cmds." + cmd + ".targetcmd", cmdtobeexecuted);
        main.getConfig().set("Cmds." + cmd + ".description", "My cmd! (Configure this in the config.yml)");
        main.getConfig().set("Cmds." + cmd + ".say", "My cmd executed! (Configure this in the config.yml)");
        main.saveConfig();
        main.reloadPluginConfig();

        core.addCommand(new CustomCMD(core, cmd));
        p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &aSuccess!")));

    }

    @Override
    public void log(String text) {
        console.sendMessage(new TextComponent(Utils.CCT(text)));
    }

    @Override
    public void addAnnouncement(String announcement) {
        messagesList.add(announcement);
        main.getConfig().set("Announcements", messagesList);
        main.saveConfig();
        main.reloadPluginConfig();
    }

    @Override
    public List<Report> getReports() {
        return reportsList;
    }

    @Override
    public void closeReport(String name, Report report, boolean discord, String Message) {
       closeReport(name, report, discord);

        ProxiedPlayer reportOwner = getProxy().getPlayer(report.getReporter());
        if(reportOwner != null){
            reportOwner.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', main.getUtils().getMessagesConfig().getString("Message-Notification-Format").replace("%sender%", name).replace("%message%", Message))));
        }else{

            List<String> messages = new ArrayList<>();

            if(main.getConfig().contains("User-Notifications." + report.getReporter()))
                messages = main.getConfig().getStringList("User-Notifications." + report.getReporter());
            messages.add(ChatColor.translateAlternateColorCodes('&', main.getUtils().getMessagesConfig().getString("Message-Notification-Format").replace("%sender%", name).replace("%message%", Message)));
            main.getConfig().set("User-Notifications." +report.getReporter(), messages);
            main.saveConfig();
        }
    }

    public void closeReport(String closer, Report r, boolean discord){

        if(getProxy().getPlayer(r.getReporter()) != null)
            getProxy().getPlayer(r.getReporter()).sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', main.getUtils().getMessagesConfig().getString("Report-Closed-Message").replace("%id%", String.valueOf(r.getReportId())))));
        else {
            if(main.getConfig().getStringList("User-Notifications." +r.getReporter()) != null){
                List<String> notifications = main.getConfig().getStringList("User-Notifications." + r.getReporter());
                notifications.add(ChatColor.translateAlternateColorCodes('&', main.getUtils().getMessagesConfig().getString("Report-Closed-Message").replace("%id%", String.valueOf(r.getReportId()))));
                main.getConfig().set("User-Notifications." + r.getReporter(), notifications);
                main.saveConfig();
                main.reloadPluginConfig();
            }
        }
        for (ProxiedPlayer p : main.getProxy().getPlayers()) {
            if (p.hasPermission("reportplus.receive")) {
                if (p.hasPermission("reportplus.receive")) {
                    p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&8&m          I         ")));
                    p.sendMessage(new TextComponent(""));
                    p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "          &6&lReport &c&lClosed!         ")));
                    p.sendMessage(new TextComponent(""));
                    p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "          &6Closer: &c" +closer + "          ")));
                    p.sendMessage(new TextComponent(""));
                    p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "          &6Report ID: &c" + r.getReportId() + "          ")));
                    p.sendMessage(new TextComponent(""));
                    p.sendMessage(new TextComponent(
                            ChatColor.translateAlternateColorCodes('&', "          &6Report reason: &c" + r.getReportContent() + "          ")));
                    p.sendMessage(new TextComponent(""));
                    p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&8&m          I         ")));
                }
            }
        }
        reportsList.remove(r);
        if(main.getConfig().getBoolean("Enabled-Modules.MySQL.Enabled"))
            main.getSqlManager().removeReportFromDatabase(r);
        else {
            main.getUtils().getReportsConfig().set("Reports.Report" + r.getReportId(), null);

            getProxy().getScheduler().schedule(main, () -> main.getUtils().saveReportsToConfig(), 2, TimeUnit.SECONDS);
        }
    }

    @Override
    public List<String> getMessages() {
        return null;
    }

    @Override
    public void deleteAnnouncement(int id) {
        main.getMessages().remove(id - 1);
        main.getConfig().set("Announcements", main.getMessages());
        main.saveConfig();
        main.reloadPluginConfig();
    }

    @Override
    public void addCustomCommandsToEmbed(EmbedBuilder builder) {
        if (main.getConfig().getSection("TXTCmds") != null) {
            Collection<String> Cmds = main.getConfig().getSection("TXTCmds").getKeys();
            for(String cmd : Cmds){
                builder.addField("**" + cmd + "**", main.getConfig().getString("TXTCmds." + cmd + ".description"), false);
            }
        }

        if (main.getConfig().getSection("Cmds") != null) {
            Collection<String> Cmds = main.getConfig().getSection("Cmds").getKeys();
            for (String cmd : Cmds) {
                builder.addField("**" + cmd + "**", main.getConfig().getString("Cmds." + cmd + ".description"), false);

            }
        }
    }

    @Override
    public void reloadPluginConfig() {
        initConfig();
    }

    @Override
    public boolean isOnline(UUID uuid) {
        return getProxy().getPlayer(uuid) != null;
    }

    @Override
    public String getReportsChannelID() {
        return reportChannelID ;
    }

    @Override
    public String getServerName(IRPlayer p) {
        return getProxy().getPlayer(p.getUniqueId()).getServer().getInfo().getName();
    }

    @Override
    public void sendMessage(UUID uuid, String message) {
        getProxy().getPlayer(uuid).sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', message)));
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public MySQLManager getSqlManager() {
        return sqlManager;
    }

    @Override
    public void saveReportsToConfig() {
utils.saveReportsToConfig();
    }

    @Override
    public Object getPlayer(UUID uuid) {
        return getProxy().getPlayer(uuid);
    }

    @Override
    public void callEvent(Object event) {
        Event e = (Event)event;
        getProxy().getPluginManager().callEvent(e);
    }

    @Override
    public void sendConsole(String message){
        console.sendMessage(new TextComponent(message));
    }

    @Override
    public boolean callReportEvent(IRPlayer player, String reported, String report, ReportType type) {
        BungeePlayerReportEvent event = new BungeePlayerReportEvent(core,player,reported,report,type);
        ProxyServer.getInstance().getPluginManager().callEvent(event);
        return event.isCancelled();
    }

    @Override
    public String getStringFromMessages(String s) {
        return getUtils().getMessagesConfig().getString(s);
    }

    @Override
    public void listReports(IFPlayer p, int page) {
        listReports((ProxiedPlayer) Universal.getMethods().convertFPlayerToPlayer(p), page);
    }

    @Override
    public boolean getBooleanFromConfig(String path) {
        return config.getBoolean(path);
    }

    @Override
    public int getIntFromConfig(String path) {
        return config.getInt(path);
    }

    @Override
    public String getVersion() {
        return getDescription().getVersion();
    }
}
