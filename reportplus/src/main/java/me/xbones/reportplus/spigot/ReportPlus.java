package me.xbones.reportplus.spigot;

import com.connorlinfoot.titleapi.TitleAPI;
import com.github.fernthedev.config.common.Config;
import com.github.fernthedev.fernapi.server.spigot.FernSpigotAPI;
import com.github.fernthedev.fernapi.universal.Universal;
import com.github.fernthedev.fernapi.universal.api.IFPlayer;
import com.github.fernthedev.fernapi.universal.data.chat.TextMessage;
import me.xbones.reportplus.api.IRPlayer;
import me.xbones.reportplus.api.Report;
import me.xbones.reportplus.api.ReportType;
import me.xbones.reportplus.core.Core;
import me.xbones.reportplus.core.IReportPlus;
import me.xbones.reportplus.core.MySQL.MySQLManager;
import me.xbones.reportplus.core.ReportPlusAPIHandler;
import me.xbones.reportplus.core.Utils;
import me.xbones.reportplus.core.commands.*;
import me.xbones.reportplus.core.configuration.ConfigurationManager;
import me.xbones.reportplus.core.gson.LangConfig;
import me.xbones.reportplus.core.universalcommands.*;
import me.xbones.reportplus.spigot.Bstats.Metrics;
import me.xbones.reportplus.spigot.chatcomponentapi.ChatComponentMessage;
import me.xbones.reportplus.spigot.config.SpigotConfig;
import me.xbones.reportplus.spigot.events.SpigotPlayerReportEvent;
import me.xbones.reportplus.spigot.inventories.InventoryManager;
import me.xbones.reportplus.spigot.listeners.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.md_5.bungee.api.chat.ClickEvent;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.apache.logging.log4j.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.*;


public class ReportPlus extends FernSpigotAPI implements IReportPlus {

    // VARIABLES //
    private ConsoleCommandSender console;
    private Core core;
    private String TOKEN;
    private String cmdPrefix;
    private String reportChannelID, CMDChannelID, MCChannelID;

    private Set<String> minecraftChosen = new HashSet<>();
    private Set<String> discordChosen = new HashSet<>();
    private Set<String> bothChosen = new HashSet<>();
    private Set<String> sendingMessage = new HashSet<>();
    private Map<Player, Player> reporting = new HashMap<>();
    private String prefix;
    private MySQLManager sqlManager;
    private List<Report> reportsList;
    private SpigotUtils utils;
    private List<String> messagesList;
    private String lastMessage = "";
    private ReportPlus main;
    private InventoryManager iManager;
    private Map<String,Report> selectedReports;
    private InventoryClickListener inventoryClickListener;

    private Chat chat = null;
    private net.milkbowl.vault.permission.Permission permission = null;
    // VARIABLES //

    @Override
    public void onEnable() {
        super.onEnable();
        Universal.setDebug(true);

        core = new Core();
        console = Bukkit.getConsoleSender();
        main = this;
        new Metrics(this);
        new ConfigurationManager(false);
        ConfigurationManager.SetConfig(new SpigotConfig(getConfig()));

        this.getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        console.sendMessage(Utils.CCT("&c--- &6REPORTPLUS &c---"));
        console.sendMessage(Utils.CCT("&c     &7LOADING...     "));

        checkDependencies();

        initializeVariables();

        try {
            core.initializeBot(this, TOKEN, cmdPrefix);
        }catch(Exception ex){
            console.sendMessage(Utils.CCT("&c ERROR INITIALIZING BOT  "));
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

            utils.createLangJSON();

            utils.createMessagesYML();

            iManager.initializeList();
            for(Player p : Bukkit.getOnlinePlayers())
                iManager.initializeReports(p);

        }catch(Exception ex){
            console.sendMessage(Utils.CCT("&c ERROR INITIALIZING MYSQL  "));
ex.printStackTrace();
        }

        if(this.getConfig().getBoolean("Enabled-Modules.Console")) {

            LogAppender appender = new LogAppender(core);
            ((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).addAppender(appender);

        }

        if (this.getConfig().getBoolean("Enabled-Modules.Announcements"))
            startAnnouncing();
        initializeCommands();
        initializeEvents();
        setupChat();
        setupPermissions();
        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
            public void run(){
                if(main.getConfig().getBoolean("Enabled-Modules.Server-Stop-Start"))
                    main.core.getJda().getTextChannelById(main.getConfig().getString("Server-Stop-Start-Channel")).sendMessage(main.getUtils().getMessagesConfig().getString("Server-Start-Message")).queue();

            }
        });
        console.sendMessage(Utils.CCT("&c   &7PLUGIN LOADED.   "));
        console.sendMessage(Utils.CCT("&c--- &6REPORTPLUS &c---"));


    }

    public void checkDependencies() {
        if (!Bukkit.getPluginManager().isPluginEnabled("TitleAPI")) {
            console.sendMessage(Utils.CCT("&c TITLE API NOT FOUND  "));
        }else {
            console.sendMessage(Utils.CCT("   &a TITLE API FOUND   "));
        }

        if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            console.sendMessage(Utils.CCT("&c VAULT NOT FOUND  "));
        }else {
            console.sendMessage(Utils.CCT("   &a VAULT FOUND   "));
        }
    }

    public void initializeVariables() {
        TOKEN = this.getConfig().getString("Discord-Bot-Token");
        cmdPrefix = this.getConfig().getString("Discord-Bot-Command-Prefix");
        reportChannelID = this.getConfig().getString("Discord-Channel-ID");
        prefix = this.getConfig().getString("Prefix");
        CMDChannelID = this.getConfig().getString("Discord-CMD-Channel-ID");
        MCChannelID = this.getConfig().getString("Discord-MC-Channel-ID");
        utils = new SpigotUtils(this);

        messagesList = this.getConfig().getStringList("Announcements");

        iManager = new InventoryManager(this);
        selectedReports = new HashMap<>();
       // api = new ReportPlusAPIHandler(this);
    }

    public ReportPlusAPIHandler getAPI(){
        return core.getApi();
    }

    public void initializeEvents(){
        PluginManager manager = getServer().getPluginManager();
        inventoryClickListener = new InventoryClickListener(this);
        manager.registerEvents(inventoryClickListener, this);
        manager.registerEvents(new PlayerChatListener(this), this);
        manager.registerEvents(new PlayerCommandListener(this), this);
        manager.registerEvents(new PlayerDeathListener(this), this);
        manager.registerEvents(new PlayerJoinListener(this), this);
        manager.registerEvents(new PlayerLeaveListener(this), this);
        manager.registerEvents(new PlayerReportListener(this),this);
    }

    private boolean setupChat(){
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }
    private boolean setupPermissions(){
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    public InventoryClickListener getInventoryClickListener() {
        return inventoryClickListener;
    }

    public void startAnnouncing() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                Random random = new Random();
                int index = random.nextInt(messagesList.size());
                if (messagesList.get(index).equals(lastMessage))
                    index = random.nextInt(messagesList.size());
                lastMessage = messagesList.get(index);
                core.getJda().getTextChannelById(main.getConfig().getString("Discord-Announcements-Channel-ID"))
                        .sendMessage("```" + messagesList.get(index) + "```").queue();
            }

        },0L, (long)this.getConfig().getInt("Interval") * 20);
    }

    public Map<Player, Player> getReporting() {
        return reporting;
    }

    public Set<String> getBothChosen() {
        return bothChosen;
    }

    public Set<String> getSendingMessage() {
        return sendingMessage;
    }

    public Set<String> getDiscordChosen() {
        return discordChosen;
    }

    public Set<String> getMinecraftChosen() {
        return minecraftChosen;
    }

    public String getCMDChannelID() {
        return CMDChannelID;
    }

    public SpigotUtils getUtils(){return utils;}

    public void initializeCommands() {
        Universal.getCommandHandler().registerCommand(new ReportCommand(this));
        Universal.getCommandHandler().registerCommand(new TXTCmd(this));
        Universal.getCommandHandler().registerCommand(new CmdCMD(this));
        Universal.getCommandHandler().registerCommand(new ListReportsCMD(this));
        Universal.getCommandHandler().registerCommand(new ReportPlusCommand(this));
        core.addCommand(new ReloadCommand(core));
        core.addCommand(new AddAnnouncementCommand(core));
        core.addCommand(new ListAnnouncementsCommand(core));
        core.addCommand(new HelpCommand(core));
        core.addCommand(new DelAnnouncementCommand(core));
        core.addCommand(new CloseReportWithMessage(core));
        if (this.getConfig().getConfigurationSection("Cmds") != null) {
            Set<String> Cmds;
            Cmds = this.getConfig().getConfigurationSection("Cmds").getKeys(false);
            for (String cmd : Cmds) {
                core.addCommand(new CustomCMD(core, cmd));
            }
        }

        if (this.getConfig().getConfigurationSection("TXTCmds") != null) {
            Set<String> Cmds = new HashSet<>();
            Cmds = this.getConfig().getConfigurationSection("TXTCmds").getKeys(false);
            for (String cmd : Cmds) {
                core.addCommand(new CustomTXTCMD(core, cmd));

            }
        }


    }



    public void setReportsList(List<Report> reports){
        reportsList=reports;
    }

    @Override
    public Config<LangConfig> getLangConfig() {
        return utils.getLanguageConfig();
    }

    public InventoryManager getRPInventoryManager() {
        return iManager;
    }
    public Map<String, Report> getSelectedReports() {
        return selectedReports;
    }

    public void NoPerm(Player p) {
        p.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',
                prefix + " " + utils.getMessagesConfig().getString("No-Permission")));
    }

    public void showGUI(Player p) {
        iManager.initializeReports(p);
        p.openInventory(iManager.getReportInventory().getInventory());

    }

    @Override
    public void AddTextCMD(Object obj, String cmd, String text) {

        CommandSender p = (CommandSender)obj;
        main.getConfig().set("TXTCmds." + cmd + ".text", text);
        main.getConfig().set("TXTCmds." + cmd + ".description", "My cmd! (Configure this in the config.yml)");
        main.saveConfig();
        main.reloadPluginConfig();

        core.addCommand(new CustomTXTCMD(core, cmd));
        p.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &aSuccess!"));
    }

    @Override
    public void log(String text){
        console.sendMessage(text);
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
        p.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &aSuccess!"));
    }

    @Override
    public void onDisable() {
        super.onDisable();
        console.sendMessage(Utils.CCT("&c--- &6REPORTPLUS &c---"));
        core.disconnectBot();
        console.sendMessage(Utils.CCT("&c    PLUGIN DISABLED   "));
        console.sendMessage(Utils.CCT("&c--- &6REPORTPLUS &c---"));
        if(main.getConfig().getBoolean("Enabled-Modules.Server-Stop-Start"))
            core.getJda().getTextChannelById(this.getConfig().getString("Server-Stop-Start-Channel")).sendMessage(main.getUtils().getMessagesConfig().getString("Server-Stop-Message")).queue();

    }

    public List<Report> getReportsList() {
        return reportsList;
    }

    @Override
    public void setGame(JDA jda) {
        jda.getPresence().setActivity(Activity.playing(((String)ConfigurationManager.get("Game")).replace("%players%",  String.valueOf(Bukkit.getOnlinePlayers().size()))));

        if(getConfig().getBoolean("Auto-Refresh-Game")){
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    jda.getPresence().setActivity(Activity.playing(((String)ConfigurationManager.get("Game")).replace("%players%",  String.valueOf(Bukkit.getOnlinePlayers().size()))));
                }
            }, 1L, 10 * 20);
        }
    }

    @Override
    public String getMCChannelID(){
        return MCChannelID;
    }

    @Override
    public String getMessage(String path){
        return utils.getMessagesConfig().getString(path);
    }

    @Override
    public void broadcast(String message){
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    @Override
    public void dispatchCommand(String command){
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    @Override
    public void addAnnouncement(String text){
        messagesList.add(text);
        main.getConfig().set("Announcements", messagesList);
        main.saveConfig();
        main.reloadPluginConfig();
    }

    @Override
    public void closeReport(String name, Report r, boolean discord, String Message){
        inventoryClickListener.CloseReport(name, r, discord);

        Player reportOwner = Bukkit.getPlayer(r.getReporter());
        if(reportOwner != null){
            reportOwner.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getUtils().getMessagesConfig().getString("Message-Notification-Format").replace("%sender%", name).replace("%message%", Message)));
        }else{

            List<String> messages = new ArrayList<>();

            if(main.getConfig().contains("User-Notifications." + r.getReporter()))
                messages = main.getConfig().getStringList("User-Notifications." + r.getReporter());
            messages.add(ChatColor.translateAlternateColorCodes('&', main.getUtils().getMessagesConfig().getString("Message-Notification-Format").replace("%sender%", name).replace("%message%", Message)));
            main.getConfig().set("User-Notifications." +r.getReporter(), messages);
            main.saveConfig();
        }
    }

    @Override
    public List<Report> getReports(){
        return reportsList;
    }

    @Override
    public void deleteAnnouncement(int id){
        main.getMessages().remove(id - 1);
        main.getConfig().set("Announcements", main.getMessages());
        main.saveConfig();
        main.reloadPluginConfig();
    }

    @Override
    public void addCustomCommandsToEmbed(EmbedBuilder builder){

        if (main.getConfig().getConfigurationSection("TXTCmds") != null) {
            Set<String> Cmds = new HashSet<>();
            Cmds = main.getConfig().getConfigurationSection("TXTCmds").getKeys(false);
            for(String cmd : Cmds){
                builder.addField("**" + cmd + "**", main.getConfig().getString("TXTCmds." + cmd + ".description"), false);
            }
        }

        if (main.getConfig().getConfigurationSection("Cmds") != null) {
            Set<String> Cmds;
            Cmds = main.getConfig().getConfigurationSection("Cmds").getKeys(false);
            for (String cmd : Cmds) {
                builder.addField("**" + cmd + "**", main.getConfig().getString("Cmds." + cmd + ".description"), false);

            }
        }
    }

    @Override
    public void reloadPluginConfig(){
        this.reloadConfig();
    }

    @Override
    public List<String> getMessages() {
        return messagesList;
    }

    @Override
    public boolean isOnline(UUID uuid){
        return Bukkit.getPlayer(uuid) != null;
    }

    @Override
    public String getReportsChannelID(){
        return reportChannelID;
    }

    @Override
    public String getServerName(IRPlayer p){
        return Bukkit.getServer().getName();
    }

    @Override
    public void sendMessage(UUID uuid, String message){
        Bukkit.getPlayer(uuid).sendMessage(message);
    }

    @Override
    public String getPrefix(){
        return prefix;
    }

    @Override
    public MySQLManager getSqlManager(){
        return sqlManager;
    }

    @Override
    public void saveReportsToConfig(){
        utils.saveReportsToConfig();
    }

    @Override
    public void broadcastTitle(String title, String subtitle, String permission){
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(permission) || player.isOp()) {
                if (Bukkit.getPluginManager().isPluginEnabled("TitleAPI")) {
                    TitleAPI.sendTitle(player, 10, 10, 10, ChatColor.translateAlternateColorCodes('&', title),ChatColor.translateAlternateColorCodes('&', subtitle));

                    //ChatColor.translateAlternateColorCodes('&', subtitle));
                }
            }
        }
    }

    @Override
    public void broadcastNewReport(IRPlayer player, String title, String subtitle, String reported, String message){
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("reportplus.receive") || p.isOp()) {
                for (String s : main.getUtils().getMessagesConfig().getStringList("Minecraft-Report-Format")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', s.replace("%reporter%", player.getName()).replace("%reported%", reported).replace("%reportcontent%", message)));

                }
                if (Bukkit.getPluginManager().isPluginEnabled("TitleAPI")) {

                    TitleAPI.sendTitle(p, 10, 10, 10, ChatColor.translateAlternateColorCodes('&', title),ChatColor.translateAlternateColorCodes('&', subtitle));
                    //ChatColor.translateAlternateColorCodes('&', subtitle));

                }
            }
        }
    }

    public Core getCore() {
        return core;
    }

    @Override
    public void NoPerm(IFPlayer<?> p) {
        p.sendMessage(new TextMessage(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&',
                prefix + " " + utils.getMessagesConfig().getString("No-Permission"))));
    }

    @Override
    public String getVersion() {
        return getDescription().getVersion();
    }


    public Permission getPermission() {
        return permission;
    }

    public Chat getChat() {
        return chat;
    }

    public Object getPlayer(UUID uuid){return Bukkit.getPlayer(uuid);}

    public void callEvent(Object event){
        Event e = (Event) event;
        Bukkit.getPluginManager().callEvent(e);
    }

    @Override
    public void sendConsole(String message){console.sendMessage(message);}

    @Override
    public boolean callReportEvent(IRPlayer player, String reported, String report, ReportType type) {
        SpigotPlayerReportEvent event = new SpigotPlayerReportEvent(player,reported,report,type);
//        Bukkit.getScheduler().callSyncMethod(this, () -> {
        Bukkit.getPluginManager().callEvent(event);

//            return null;
//        });
        return event.isCancelled();
    }


    @Override
    public String getStringFromMessages(String path) {
        return getUtils().getMessagesConfig().getString(path);
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

    public void listReports(Player p, int pageNumber){
        int pageIndex = pageNumber - 1;
        if(reportsList.size() < 1){
            ChatComponentMessage message = new ChatComponentMessage(ChatColor.translateAlternateColorCodes('&',
                    "&cThere are no reports."));

            p.spigot().sendMessage(message.getComponent());
            return;
        }
        List<List<Report>> pages = chopped(reportsList, 5);
        List<Report> page = pages.get(pageIndex);
        for(Report r : page)
        {
            ChatComponentMessage message = new ChatComponentMessage(ChatColor.translateAlternateColorCodes('&', "&7Report &b#&c" + r.getReportId() +" &7- By &c" + r.getReporter() + " &7 - Reported: &c" + r.getReported() + " &7- Server: &c" + r.getServer()));

            message.addHover(ChatColor.translateAlternateColorCodes('&', "&6" + r.getReportContent()));
            message.addClick(ClickEvent.Action.SUGGEST_COMMAND, "/closereport " + r.getReportId() + " Your Message here");
            p.spigot().sendMessage(message.getComponent());
        }

        try {
            pages.get(pageNumber);
            ChatComponentMessage message = new ChatComponentMessage(ChatColor.translateAlternateColorCodes('&',
                    "&bTo view the next page, use /reports &c" +(pageNumber + 1)));

            p.spigot().sendMessage(message.getComponent());

        }catch(Exception ex){}
    }

    @Override
    public void listReports(IFPlayer<?> p, int page) {
        listReports((Player) p.getPlayer(), page);
    }

    @Override
    public boolean getBooleanFromConfig(String path) {
        return getConfig().getBoolean(path);
    }

    @Override
    public int getIntFromConfig(String path) {
        return getConfig().getInt(path);
    }


}

