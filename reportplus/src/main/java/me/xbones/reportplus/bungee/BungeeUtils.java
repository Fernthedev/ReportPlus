package me.xbones.reportplus.bungee;


import com.github.fernthedev.config.common.Config;
import com.github.fernthedev.config.gson.GsonConfig;
import com.github.fernthedev.fernapi.universal.Universal;
import lombok.SneakyThrows;
import me.xbones.reportplus.api.Report;
import me.xbones.reportplus.api.ReportType;
import me.xbones.reportplus.core.gson.LangConfig;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class BungeeUtils {

    private ReportPlus main;
    private GsonConfig<LangConfig> langConfig;

    public BungeeUtils(ReportPlus main){
        this.main=main;
    }

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


    @SneakyThrows
    public void createLangConfig() {
        File f = new File(main.getProxy().getPluginManager().getPlugin("ReportPlus").getDataFolder(), File.separator + "language.lang");
        langConfig = new GsonConfig<>(new LangConfig(), f);
        langConfig.load();
        Universal.getLogger().info("Loaded language.lang");
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
