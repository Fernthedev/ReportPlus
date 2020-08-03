package me.xbones.reportplus.bungee;

import com.github.fernthedev.config.common.Config;
import com.github.fernthedev.config.gson.GsonConfig;
import lombok.SneakyThrows;
import me.xbones.reportplus.api.Report;
import me.xbones.reportplus.api.ReportType;
import me.xbones.reportplus.core.gson.LangConfig;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BungeeUtils {

    private ReportPlus main;
    private GsonConfig<LangConfig> langConfig;

    public BungeeUtils(ReportPlus main){
        this.main=main;
    }

    private Configuration messagesConfig;
    private Configuration reportsConfig;


    public void createReportsYML() {
        File userdata = new File(main.getProxy().getPluginManager().getPlugin("ReportPlus").getDataFolder(),
                File.separator + "data");
        File f = new File(userdata, File.separator + "reports.yml");

        if (!f.exists()) {
            try {
                userdata.mkdirs();
                f.createNewFile();
                reportsConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(f);

                reportsConfig.set("Reports", null); // Arrays.asList(arg0)(gangs));
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(reportsConfig,f);
            } catch (IOException exception) {

                exception.printStackTrace();
            }
        } else {
            try {
                reportsConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(f);

                if (reportsConfig.getSection("Reports") != null) {
                    for (String report : reportsConfig.getSection("Reports").getKeys()) {
                        int id = reportsConfig.getInt("Reports." + report + ".id");
                        String reporter = reportsConfig.getString("Reports." + report + ".reporter");
                        String content = reportsConfig.getString("Reports." + report + ".report");
                        String reported = reportsConfig.getString("Reports." + report + ".reported");
                        String date = reportsConfig.getString("Reports." + report + ".date");
                        String server = reportsConfig.getString("Reports." + report + ".server");
                        ReportType type = ReportType.OUTDATED;
                        if (reportsConfig.getString("Reports." + report + ".type") != null)
                            type = ReportType.valueOf(reportsConfig.getString("Reports." + report + ".type"));

                        Report r = new Report(id, reporter, reported, content, type, server);
                        r.setDate(date);
                        main.getReports().add(r);
                    }
                }
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }

    }

    public void SetIfNotExists( String key, Object value){
        Configuration config = messagesConfig;
        if(!config.contains(key))
            config.set(key, value);

    }

    @SneakyThrows
    public void createLangConfig() {
        File f = new File(main.getProxy().getPluginManager().getPlugin("ReportPlus").getDataFolder(), File.separator + "language.lang");
        langConfig = new GsonConfig<>(new LangConfig(), f);

    }

    public void createMessagesYML() {
        File f = new File(main.getProxy().getPluginManager().getPlugin("ReportPlus").getDataFolder(), File.separator + "messages.yml");
        try {
            if(f.exists())
            messagesConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(f);
        else{
            f.createNewFile();
                messagesConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(f);

            }
            SetIfNotExists("Report-Closed-Message", "&cYour report with the id %id% has been closed!");
            SetIfNotExists("Minecraft-Report-Title-Message.Title", "&aNew Report!");
            SetIfNotExists("Minecraft-Report-Title-Message.Subtitle", "&e%player% has made a new report");
            SetIfNotExists("Discord-Report-Title-Message.Title", "&aNew Report! Check your Discord!");
            SetIfNotExists("Discord-Report-Title-Message.Subtitle", "&e%player% has made a new report");
            SetIfNotExists("Minecraft-Chat-Format", "[%server%] %player% -> %message%");
            SetIfNotExists("Command-log-Format", "[%server%] %player% -> %cmd%");

            SetIfNotExists("Discord-Chat-Format", "&7[Discord] &aUser %user% -> %message%");
            SetIfNotExists("Button-Click-Message", "&6What would you like to report about this player?");
            SetIfNotExists("Discord-Join-Message", ":heavy_plus_sign: Player %player% has joined the server!");
            SetIfNotExists("Discord-Leave-Message", ":heavy_minus_sign: Player %player% has left the server!");
            SetIfNotExists("Server-Start-Message", ":white_check_mark: Server has started!");
            SetIfNotExists("Server-Stop-Message", ":skull_crossbones: Server has stopped!");
            SetIfNotExists("Discord-Report-Embed.Title", "New report");
            SetIfNotExists("Discord-Report-Embed.Description", "You have received a new report! Information:");
            SetIfNotExists("Discord-Report-Embed.Fields.Reporter", "%reporter%");
            SetIfNotExists("Discord-Report-Embed.Fields.Reported", "%reported%");
            SetIfNotExists("Discord-Report-Embed.Fields.Server", "%server%");
            SetIfNotExists("Discord-Report-Embed.Fields.Report-ID", "%reportid%");
            SetIfNotExists("Discord-Report-Embed.Fields.Report-Content", "%reportcontent%");
            SetIfNotExists("Success-Report", "&aSucessfully reported! &cYour report id is &b#%id%'");
            SetIfNotExists("Enter-Message", "&aPlease enter the message to send.");
            SetIfNotExists("Message-Notification-Format", "&c%sender% &7-> &a%message%");

            List<String> minecraftReport = new ArrayList<>();
            minecraftReport.add(" ");
            minecraftReport.add("&8&m          I         ");
            minecraftReport.add(" ");
            minecraftReport.add("          &6&lNew &c&lReport!         ");
            minecraftReport.add(" ");
            minecraftReport.add("          &6Reporter: &c%reporter%          ");
            minecraftReport.add(" ");
            minecraftReport.add("          &6Reported: &c%reported%          ");
            minecraftReport.add(" ");
            minecraftReport.add("          &6Server: &c%server%          ");
            minecraftReport.add(" ");
            minecraftReport.add("          &6Reason: &c%reportcontent%          ");
            minecraftReport.add(" ");
            minecraftReport.add("&8&m          I         ");

            SetIfNotExists("Minecraft-Report-Format", minecraftReport);

            SetIfNotExists("No-Permission", "&cYou don't have access to that command!");
            SetIfNotExists("Chat-Sync-Banned-Word", "&c&lDo not ping everyone!");
            SetIfNotExists("Not-Enough-Args", "&cNot enough arguments! Use /rp for help!");
            SetIfNotExists("Cant-Report-Self", "&c&lYou cannot report yourself!");
            SetIfNotExists("Success-Close-Report", "&aSuccessfully closed report &c#%id%");
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(messagesConfig,f);
        } catch (IOException exception) {

            exception.printStackTrace();
        }

    }


    public void saveReports() {
        File userdata = new File(main.getProxy().getPluginManager().getPlugin("ReportPlus").getDataFolder(),
                File.separator + "data");
        File f = new File(userdata, File.separator + "reports.yml");
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(reportsConfig,f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Configuration getMessagesConfig() {
        return messagesConfig;
    }

    public Configuration getReportsConfig() {
        return reportsConfig;
    }

    public void saveReportsToConfig() {
        for (Report report : main.getReports()) {
            reportsConfig.set("Reports." + "Report" + report.getReportId() + ".id", report.getReportId());
            reportsConfig.set("Reports." + "Report" + report.getReportId() + ".reporter", report.getReporter());
            reportsConfig.set("Reports." + "Report" + report.getReportId() + ".reported", report.getReported());
            reportsConfig.set("Reports." + "Report" + report.getReportId() + ".report", report.getReportContent());
            reportsConfig.set("Reports." + "Report" + report.getReportId() + ".date", report.getDate());
            reportsConfig.set("Reports." + "Report" + report.getReportId() + ".type", report.getType().toString());
            reportsConfig.set("Reports." + "Report" + report.getReportId() + ".server", report.getServer());
            saveReports();
        }

    }

    public static String removeColorCodes(String inputString){
        return inputString.replaceAll("&.", "");
    }

    public Config<LangConfig> getLangConfig() {
        return langConfig;
    }
}
