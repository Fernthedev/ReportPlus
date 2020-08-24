package me.xbones.reportplus.spigot;

import com.github.fernthedev.config.common.Config;
import com.github.fernthedev.config.gson.GsonConfig;
import lombok.SneakyThrows;
import me.xbones.reportplus.api.Report;
import me.xbones.reportplus.api.ReportType;
import me.xbones.reportplus.core.Utils;
import me.xbones.reportplus.core.gson.LangConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SpigotUtils extends Utils {
    private ReportPlus main;

    private FileConfiguration reportsConfig;
    private Config<LangConfig> langConfig;

    public SpigotUtils(ReportPlus main){
        this.main=main;
    }

    public void createReportsYML() {
        File userdata = new File(Bukkit.getServer().getPluginManager().getPlugin("ReportPlus").getDataFolder(),
                File.separator + "data");
        File f = new File(userdata, File.separator + "reports.yml");
        reportsConfig = YamlConfiguration.loadConfiguration(f);
        if (!f.exists()) {
            try {

                reportsConfig.set("Reports", null); // Arrays.asList(arg0)(gangs));

                reportsConfig.save(f);

                if (main.getReports() == null) main.setReportsList(new ArrayList<>());
            } catch (IOException exception) {

                exception.printStackTrace();
            }
        } else {
            if (reportsConfig.getConfigurationSection("Reports") != null) {
                for (String report : reportsConfig.getConfigurationSection("Reports").getKeys(false)) {
                    int id = reportsConfig.getInt("Reports." + report + ".id");
                    String reporter = reportsConfig.getString("Reports." + report + ".reporter");
                    String content = reportsConfig.getString("Reports." + report + ".report");
                    String reported = reportsConfig.getString("Reports." + report + ".reported");
                    String date = reportsConfig.getString("Reports." + report +".date");
                    String server = reportsConfig.getString("Reports." + report + ".server");
                    ReportType type = ReportType.OUTDATED;
                    if(reportsConfig.getString("Reports." + report +".type") != null)
                        type = ReportType.valueOf(reportsConfig.getString("Reports." + report +".type"));

                    Report r = new Report(id, reporter, reported, content, type, server);
                    r.setDate(date);

                    main.getReportsList().add(r);
                }
            }
        }

    }

    @SneakyThrows
    public void createLangJSON() {
        File f = new File(Bukkit.getServer().getPluginManager().getPlugin("ReportPlus").getDataFolder(), File.separator + "language.lang");
        langConfig = new GsonConfig<>(new LangConfig(), f);
        langConfig.load();
    }


    public void saveReports() {
        File userdata = new File(Bukkit.getServer().getPluginManager().getPlugin("ReportPlus").getDataFolder(),
                File.separator + "data");
        File f = new File(userdata, File.separator + "reports.yml");
        try {
            reportsConfig.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getReportsConfig() {
        return reportsConfig;
    }

    public void saveReportsToConfig() {
        for (Report report : main.getReportsList()) {
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

    @Override
    public Config<LangConfig> getLanguageConfig() {
        return langConfig;
    }
}
